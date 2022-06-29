package com.example.firstt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link attractions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class attractions extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public attractions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment attractions.
     */
    // TODO: Rename and change types and number of parameters
    public static attractions newInstance(String param1, String param2) {
        attractions fragment = new attractions();
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
        return inflater.inflate(R.layout.fragment_attractions, container, false);
    }
    private Spinner aspinner;
    protected static String aquery;
    protected static String alogha;
    private TextView resultView;
    protected static AlertDialog.Builder AdialogBuilder ;
    protected static AlertDialog Adialog ;
    CheckBox searchByName;
    boolean connection;
    protected static View AFilterPopup;
    protected static int fltr ;
    protected static int name_search;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

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


        aspinner = view.findViewById(R.id.alanguages);
        String [] languages = getResources().getStringArray(R.array.languages);
        ArrayAdapter arrayAdapter;
        arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext()
                ,android.R.layout.simple_spinner_dropdown_item,languages);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aspinner.setAdapter(arrayAdapter);
        aspinner.setSelection(0);
        aspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getId()==R.id.alanguages){
                    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to a network
                        connection = true;
                    } else
                        connection = false;
                    if(!connection){
                        aspinner.setSelection(0);
                        aspinner.setEnabled(false);
                        Toast.makeText(getActivity().getApplicationContext(), "You need Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        resultView = view.findViewById(R.id.result_textA);
        searchByName=view.findViewById(R.id.asearchname);
        AdialogBuilder = new AlertDialog.Builder(getContext());
        AFilterPopup = getLayoutInflater().inflate(R.layout.att_filter,null);

        AdialogBuilder.setView(AFilterPopup);
        Adialog = AdialogBuilder.create();

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

        FloatingActionButton micon = view.findViewById(R.id.MicOnA);
        micon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        resultView.setHint("Search for Attractions...");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        //finger is on the button
                        alogha=aspinner.getSelectedItem().toString();
                        if(alogha.equals("English")){
                            mSpeechRecognizer.startListening(mSpeechRecognizerIntent_EN);
                        }
                        else if(alogha.equals("French")) {
                            mSpeechRecognizer.startListening(mSpeechRecognizerIntent_FR);

                        }
                        else if(alogha.equals("Arabic")) {
                            mSpeechRecognizer.startListening(mSpeechRecognizerIntent_AR);

                        }
                        else if(alogha.equals("Spanish")) {
                            mSpeechRecognizer.startListening(mSpeechRecognizerIntent_ES);

                        }
                        resultView.setText("");
                        resultView.setHint("Listening...");
                        break;
                }
                return false;
            }
        });

        ImageButton backhome = view.findViewById(R.id.backHomeA);
        backhome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_attractions_to_home);
            }
        });
        Button search = view.findViewById(R.id.search_att);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fltr =0;

                if(searchByName.isChecked()){ name_search=1;}
                else { name_search=0;}
                alogha=aspinner.getSelectedItem().toString();
                aquery = resultView.getText().toString();
                navController.navigate(R.id.action_attractions_to_attractions_results);
            }
        });
        ImageButton backBtn = view.findViewById(R.id.backBtn_att);
        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_attractions_to_home);
            }
        });
    }
}