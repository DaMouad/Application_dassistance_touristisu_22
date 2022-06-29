package com.example.firstt;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HotelFragment extends Fragment {

    private TextView resultView;


    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    protected static String hquery;
    protected static String logha;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HotelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HotelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotelFragment newInstance(String param1, String param2) {
        HotelFragment fragment = new HotelFragment();
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
        return inflater.inflate(R.layout.fragment_hotel, container, false);


    }

    protected static AlertDialog.Builder HdialogBuilder ;
    protected static AlertDialog Hdialog ;
    protected static View HFilterPopup;
    protected static int fltr ;
    protected static int name_search ;
    private Spinner hspinner;
    CheckBox searchByName;
    boolean connection;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tst = view.findViewById(R.id.testview222);

         SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());

         Intent mSpeechRecognizerIntent_EN = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent_EN.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"en-US");
            mSpeechRecognizerIntent_EN.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US");

        Intent mSpeechRecognizerIntent_FR = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        mSpeechRecognizerIntent_FR.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "fr-FR");
            mSpeechRecognizerIntent_FR.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR");

        Intent mSpeechRecognizerIntent_AR = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        mSpeechRecognizerIntent_AR.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "ar-SA");
            mSpeechRecognizerIntent_AR.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-SA");

        Intent mSpeechRecognizerIntent_ES = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        mSpeechRecognizerIntent_ES.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-ES");
            mSpeechRecognizerIntent_ES.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");

        hspinner = view.findViewById(R.id.hlanguages);
        String [] languages = getResources().getStringArray(R.array.languages);
        ArrayAdapter arrayAdapter;
        arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext()
                ,android.R.layout.simple_spinner_dropdown_item,languages);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hspinner.setAdapter(arrayAdapter);
        hspinner.setSelection(0);
        hspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getId()==R.id.hlanguages){
                    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to a network
                        connection = true;
                    } else
                        connection = false;
                    if(!connection){
                        hspinner.setSelection(0);
                        hspinner.setEnabled(false);
                        Toast.makeText(getActivity().getApplicationContext(), "You need Internet Connection", Toast.LENGTH_SHORT).show();

                    }

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        searchByName=view.findViewById(R.id.hsearchname);

        HdialogBuilder = new AlertDialog.Builder(getContext());
        HFilterPopup = getLayoutInflater().inflate(R.layout.hotel_filter,null);

        HdialogBuilder.setView(HFilterPopup);
        Hdialog = HdialogBuilder.create();


        resultView = view.findViewById(R.id.result_text);
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null)
                    resultView.setText(matches.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
        // Check if user has given permission to record audio, init the model after permission is granted

       /* autoCompleteTxt = view.findViewById(R.id.langs);
        adapterItems= new ArrayAdapter<String>(this,R.layout.dropdown_langs,languages);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item"+item)
            }
        });
       */

        FloatingActionButton micon = view.findViewById(R.id.MicOn);
        micon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_UP) {
                    mSpeechRecognizer.stopListening();
                    resultView.setHint("Search for Hotels");

                } else if (action == MotionEvent.ACTION_DOWN) {//finger is on the button
                    logha=hspinner.getSelectedItem().toString();
                    if(logha.equals("English")){
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent_EN);
                    }
                    else if(logha.equals("French")) {
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent_FR);

                    }
                    else if(logha.equals("Arabic")) {
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent_AR);

                    }
                    else if(logha.equals("Spanish")) {
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent_ES);

                    }
                    resultView.setText("");
                    resultView.setHint("Listening...");
                }
                return false;
            }
        });



        NavController navController = Navigation.findNavController(view);

        ImageButton backBtn_hot = view.findViewById(R.id.backBtn_hot);
        backBtn_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_hotelFragment2_to_homeFragment);
            }
        });

        Button search_hot = view.findViewById(R.id.search_hot);
        search_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hquery = resultView.getText().toString();
                logha=hspinner.getSelectedItem().toString();
                if(searchByName.isChecked()){ name_search=1;}
                else { name_search=0;}
                fltr =0;
                navController.navigate(R.id.action_hotelFragment2_to_hotelResultsFragment2);
            }
        });
        ImageButton backhome = view.findViewById(R.id.backHomee);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_hotelFragment2_to_homeFragment);
            }
        });
    }


}