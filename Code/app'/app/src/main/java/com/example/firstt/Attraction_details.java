package com.example.firstt;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Attraction_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Attraction_details extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Attraction_details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Attraction_details.
     */
    // TODO: Rename and change types and number of parameters
    public static Attraction_details newInstance(String param1, String param2) {
        Attraction_details fragment = new Attraction_details();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attraction_details, container, false);
    }

    ImageButton b1;
    TextToSpeech t1;
    String tts="";
    int talk=0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        ImageButton backBtn = view.findViewById(R.id.backBtn_att22);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attractions.fltr=1;
                navController.navigate(R.id.action_attraction_details_to_attractions_results);
            }
        });
        ImageButton backhome = view.findViewById(R.id.backHomeA22);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_attraction_details_to_home);
            }
        });
        attractions.fltr=1;

        TextView vname = view.findViewById(R.id.att_name2);
        ImageView vimg = view.findViewById(R.id.att_img2);
        TextView vadress = view.findViewById(R.id.att_adress2);
        TextView vcontact = view.findViewById(R.id.Acontact);
        TextView vcategory = view.findViewById(R.id.Acat);
        TextView vsug_time = view.findViewById(R.id.sug_dur);
        TextView vopen_dur = view.findViewById(R.id.open_time);
        TextView vnear_res = view.findViewById(R.id.Anear_res);
        TextView vnear_att = view.findViewById(R.id.Anear_att);
        TextView vdesc = view.findViewById(R.id.Adesc);
        TextView vmap = view.findViewById(R.id.agps);


        t1=new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        b1=view.findViewById(R.id.abtts);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(talk%2==0){
                    t1.speak(tts, TextToSpeech.QUEUE_FLUSH, null,null);
                }
                else{
                    t1.stop();
                }
                talk+=1;
            }
        });

        vname.setText(attractions_results.adetails.getName());
        tts+=attractions_results.adetails.getName()+".";
        vcategory.setText(attractions_results.adetails.getCategory());
        tts+=attractions_results.adetails.getCategory()+"..";
        if (!attractions_results.adetails.getAdress().equals(" ")){
            vadress.setText(attractions_results.adetails.getAdress());


        }
        else{
            vadress.setVisibility(View.GONE);
        }

        if (!attractions_results.adetails.getGps().equals(" ") && !attractions_results.adetails.getGps().equals("")){
            vmap.setText("Map Location : "+"https://www.google.com"+attractions_results.adetails.getGps());
        }
        else{
            vmap.setVisibility(View.GONE);
        }

        if (!attractions_results.adetails.getWebsite().equals(" ")){
            vcontact.setText("Website : "+attractions_results.adetails.getWebsite());
        }
        else{
            vcontact.setVisibility(View.GONE);
        }

        if (!attractions_results.adetails.getDescription().equals(" ")){
            vdesc.setText("About :\n"+attractions_results.adetails.getDescription());
            tts+=attractions_results.adetails.getDescription()+".";
        }
        else{
            vdesc.setVisibility(View.GONE);
        }

        if (!attractions_results.adetails.getSug_dur().equals(" ")){
            vsug_time.setText("Suggested duration : "+attractions_results.adetails.getSug_dur());
            tts+="Suggested duration is : "+attractions_results.adetails.getSug_dur()+".";
        }
        else{
            vsug_time.setVisibility(View.GONE);
        }

        if (!attractions_results.adetails.getOpen_dur().equals(" ")){
            vopen_dur.setText("Open Duration : "+attractions_results.adetails.getOpen_dur());
            tts+="Its Open Duration : "+attractions_results.adetails.getOpen_dur()+".";
        }
        else{
            vopen_dur.setVisibility(View.GONE);
        }

        if (!attractions_results.adetails.getNear_res().equals(" ")){
            String nh=attractions_results.adetails.getNear_res();
            String [] nh1 = nh.split(",");
            String nhf ="Nearby Restaurants :";
            for(int i = 0 ;i<nh1.length-1;i++) {
                nhf+="\n • "+nh1[i];
            }
            vnear_res.setText(nhf);
            tts+=nhf+".";

        } else{vnear_res.setVisibility(View.GONE);}

        if (!attractions_results.adetails.getNear_att().equals(" ")){
            String nh=attractions_results.adetails.getNear_att();
            String [] nh1 = nh.split(",");
            String nhf ="Nearby Attractions :";
            for(int i = 0 ;i<nh1.length-1;i++) {
                nhf+="\n • "+nh1[i];
            }
            vnear_att.setText(nhf);
            tts+=nhf+".";
        } else{vnear_att.setVisibility(View.GONE);}

        Drawable drawable = getActivity().getResources().getDrawable(
                getActivity().getResources().getIdentifier(attractions_results.adetails.getImg(),"drawable", getActivity().getPackageName()));
        vimg.setBackground(drawable);


    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}