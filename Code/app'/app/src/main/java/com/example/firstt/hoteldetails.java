package com.example.firstt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link hoteldetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class hoteldetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public hoteldetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment hoteldetails.
     */
    // TODO: Rename and change types and number of parameters
    public static hoteldetails newInstance(String param1, String param2) {
        hoteldetails fragment = new hoteldetails();
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
        return inflater.inflate(R.layout.fragment_hoteldetails, container, false);
    }
    ImageButton b1;
    TextToSpeech t1;
    String tts="";
    int talk=0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        ImageButton backBtn_hot = view.findViewById(R.id.backBtn_hot);
        backBtn_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotelFragment.fltr=1;
                navController.navigate(R.id.action_hoteldetails2_to_hotelResultsFragment2);
            }
        });
        HotelFragment.fltr=1;
        ImageButton backhome = view.findViewById(R.id.backHome);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_hoteldetails2_to_home);
            }
        });

        TextView vname = view.findViewById(R.id.hotel_name);
        TextView vrate = view.findViewById(R.id.rating);
        RatingBar vstars = view.findViewById(R.id.stars);
        TextView vprops = view.findViewById(R.id.props);
        TextView vprop = view.findViewById(R.id.prop);
        TextView vabout = view.findViewById(R.id.description);
        TextView vaddress = view.findViewById(R.id.haddress);
        TextView vcontact = view.findViewById(R.id.hcontact);
        TextView vtype = view.findViewById(R.id.htype);
        ImageView vimg = view.findViewById(R.id.hotel_img);
        TextView vnear_att = view.findViewById(R.id.HNear_att);
        TextView vmap = view.findViewById(R.id.hgps);
        vname.setText(hotelResultsFragment.hdetails.getName());


        t1=new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        b1=view.findViewById(R.id.btts);
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
                //String toSpeak = "fd gh dfh ";
                //Toast.makeText(getContext(), tts,Toast.LENGTH_SHORT).show();

            }
        });

        if(!hotelResultsFragment.hdetails.getStars().equals("unclassified")) {
            vstars.setRating(Float.parseFloat(hotelResultsFragment.hdetails.getStars()));
            vrate.setText(hotelResultsFragment.hdetails.getStars()+" Stars");
            tts=hotelResultsFragment.hdetails.getName()+".."+hotelResultsFragment.hdetails.getStars()+" Stars"+".";
        }
        else {
            vstars.setVisibility(View.INVISIBLE);
            vrate.setText(hotelResultsFragment.hdetails.getStars());
        }

        String abt="";
        if (!hotelResultsFragment.hdetails.getDescription().equals(" ")){
            abt="About :\n "+hotelResultsFragment.hdetails.getDescription();
            tts+=hotelResultsFragment.hdetails.getDescription()+".";
        }
        vabout.setText(abt);

        String prop="";
        if (!hotelResultsFragment.hdetails.getProps().equals(" ")){
            String p=hotelResultsFragment.hdetails.getProps();
            String [] p1 = p.split(",");
            String pf ="";
            for(int i = 0 ;i<p1.length;i++) {
                if (!(i % 5 == 0)) {
                    pf += p1[i] + "     -     ";
                } else {
                    pf += "\n" + p1[i];
                }
            }
            prop=pf;
            vprop.setText("Properties :");
            vprops.setText(prop);
            tts+="Its Properties are : ."+prop+".";
        }
        else{
            vprop.setVisibility(View.GONE);
            vprops.setVisibility(View.GONE);
        }

        String web="";
        if (!hotelResultsFragment.hdetails.getWebsite().equals(" ")){
             web="\nWebsite :"+hotelResultsFragment.hdetails.getWebsite();
        }
        String tele="";
        if (!hotelResultsFragment.hdetails.getPhone().equals(" ")){
            tele="Phone :"+hotelResultsFragment.hdetails.getPhone();
        }
        vcontact.setText(tele+web);

        if (!hotelResultsFragment.hdetails.getAddress().equals(" ")){
            vaddress.setText(hotelResultsFragment.hdetails.getAddress());
           /* if (!hotelResultsFragment.hdetails.getGps().equals(" ") && !hotelResultsFragment.hdetails.getGps().equals("")){
            vaddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            Intent viewIntent =
                                    new Intent("android.intent.action.VIEW",
                                            Uri.parse("https://www.google.com"+hotelResultsFragment.hdetails.getGps()));
                            startActivity(viewIntent);

                }
            });
            }*/
        }
        else{
            vaddress.setVisibility(View.GONE);
        }
        if (!hotelResultsFragment.hdetails.getGps().equals(" ") && !hotelResultsFragment.hdetails.getGps().equals("")){
            vmap.setText("Map Location : "+"https://www.google.com"+hotelResultsFragment.hdetails.getGps());
        }
        else{
            vmap.setVisibility(View.GONE);
        }
        vtype.setText(hotelResultsFragment.hdetails.getType());

        Drawable drawable = getActivity().getResources().getDrawable(
                getActivity().getResources().getIdentifier(hotelResultsFragment.hdetails.getImg(),"drawable", getActivity().getPackageName()));
        vimg.setBackground(drawable);


        if (!hotelResultsFragment.hdetails.getNear_att().equals(" ") && !hotelResultsFragment.hdetails.getNear_att().equals("") && !hotelResultsFragment.hdetails.getNear_att().equals("['']")){
            String nh=hotelResultsFragment.hdetails.getNear_att();
            String [] nh1 = nh.split(",");
            String nhf ="Nearby Attractions :";
            for(int i = 0 ;i<nh1.length-1;i++) {
                nhf+="\n • "+nh1[i];
            }
            vnear_att.setText(nhf);
            tts+=nhf+".";
        } else{vnear_att.setVisibility(View.GONE);}


    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}