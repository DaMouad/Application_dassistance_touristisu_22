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

import org.w3c.dom.Text;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestoDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestoDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestoDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestoDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static RestoDetails newInstance(String param1, String param2) {
        RestoDetails fragment = new RestoDetails();
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
        return inflater.inflate(R.layout.fragment_resto_details, container, false);
    }
    ImageButton b1;
    TextToSpeech t1;
    String tts="";
    int talk=0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        ImageButton backBtn = view.findViewById(R.id.backBtn_res22);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantFragment.fltr=1;
                navController.navigate(R.id.action_restoDetails_to_resto_results2);
            }
        });
        ImageButton backhome = view.findViewById(R.id.backHomeR22);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_restoDetails_to_home);
            }
        });


        TextView vname = view.findViewById(R.id.resto_name);
        ImageView vimg = view.findViewById(R.id.resto_img);
        TextView vadress = view.findViewById(R.id.resto_adress);
        TextView vcontact = view.findViewById(R.id.Rcontact);
        TextView vkitchen = view.findViewById(R.id.kitchen);
        TextView vfeats = view.findViewById(R.id.feats);
        TextView vprice = view.findViewById(R.id.price);
        TextView vnear_hot = view.findViewById(R.id.Rnear_hot);
        TextView vnear_att = view.findViewById(R.id.Rnear_att);
        TextView vdesc = view.findViewById(R.id.Rdesc);
        TextView vmap = view.findViewById(R.id.rgps);
        vname.setText(Resto_results.rdetails.getName());
        tts+=Resto_results.rdetails.getName()+".";

        t1=new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        b1=view.findViewById(R.id.rbtts);
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


        String web="";
        if (!Resto_results.rdetails.getWebsite().equals(" ")){
            web="\nWebSite : "+Resto_results.rdetails.getWebsite();
        }
        String tele="";
        if (!Resto_results.rdetails.getPhone().equals(" ")){
            tele="Phone : "+Resto_results.rdetails.getPhone();
        }
        vcontact.setText(tele+web);

        if (!Resto_results.rdetails.getAddress().equals(" ")){
            vadress.setText(Resto_results.rdetails.getAddress());
            /*if (!Resto_results.rdetails.getGps().equals(" ") && !Resto_results.rdetails.getGps().equals("")){
                vadress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse("https://www.google.com"+Resto_results.rdetails.getGps()));
                        startActivity(viewIntent);
                    }
                });}*/

        }
        else{
            vadress.setVisibility(View.GONE);
        }

        if (!Resto_results.rdetails.getGps().equals(" ") && !Resto_results.rdetails.getGps().equals("")){
            vmap.setText("Map Location : "+"https://www.google.com"+Resto_results.rdetails.getGps());
        }
        else{
            vmap.setVisibility(View.GONE);
        }

        if (!Resto_results.rdetails.getKitchen().equals(" ")){
            String k=Resto_results.rdetails.getKitchen();
            String [] k1 = k.split(",");
            String kf ="";
            for(int i = 0 ;i<k1.length-1;i++) {
                kf+=k1[i]+" - ";
            }
            kf+=k1[k1.length-1];
            vkitchen.setText(kf);
            tts+=Resto_results.rdetails.getKitchen()+" Kitchen..";
        } else{vkitchen.setVisibility(View.GONE);}

        if (!Resto_results.rdetails.getDescription().equals(" ")){
            vdesc.setText("About :\n" + Resto_results.rdetails.getDescription());
            tts+=Resto_results.rdetails.getDescription()+".";
        } else{vdesc.setVisibility(View.GONE);}


        RestaurantFragment.fltr=1;
        if (!Resto_results.rdetails.getFeats().equals(" ")){
            String f=Resto_results.rdetails.getFeats();
            String [] f1 = f.split(",");
            String ff ="Services :";
            for(int i = 0 ;i<f1.length-1;i++) {
                ff+="\n • "+f1[i]+" •";
            }
            vfeats.setText(ff);
            tts+="its services are :"+ff+".";
        } else{vfeats.setVisibility(View.GONE);}

        if (!Resto_results.rdetails.getPrice().equals(" ")){
            vprice.setText("Price : "+Resto_results.rdetails.getPrice());
        }
        else{
            vprice.setVisibility(View.GONE);
        }

        if (!Resto_results.rdetails.getNear_att().equals(" ") && !Resto_results.rdetails.getNear_att().equals("")){
            String na=Resto_results.rdetails.getNear_att();
            String [] na1 = na.split(",");
            String nf ="Nearby Attractions :";
            for(int i = 0 ;i<na1.length-1;i++) {
                nf+="\n • "+na1[i];
            }
            vnear_att.setText(nf);
            tts+="."+nf+".";
        } else{vnear_att.setVisibility(View.GONE);}

        if (!Resto_results.rdetails.getNear_hot().equals(" ") && !Resto_results.rdetails.getNear_att().equals("")){
            String nh=Resto_results.rdetails.getNear_hot();
            String [] nh1 = nh.split(",");
            String nhf ="Nearby Hotels :";
            for(int i = 0 ;i<nh1.length-1;i++) {
                nhf+="\n • "+nh1[i];
            }
            vnear_hot.setText(nhf);
            tts+="."+nhf+".";
        } else{vnear_hot.setVisibility(View.GONE);}

        Drawable drawable = getActivity().getResources().getDrawable(
                getActivity().getResources().getIdentifier(Resto_results.rdetails.getImg(),"drawable", getActivity().getPackageName()));
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