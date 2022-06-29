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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.google.android.material.chip.Chip;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link attractions_results#newInstance} factory method to
 * create an instance of this fragment.
 */
public class attractions_results extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecyclerView recyclerView ;
    public static List<AttractionModelClass> aData;

    AttractionsAdapter adapter;
    List<PyObject> res;
    String res2;
    String query;
    boolean connection;
    public static AttractionModelClass adetails;

    public attractions_results() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment attractions_results.
     */
    // TODO: Rename and change types and number of parameters
    public static attractions_results newInstance(String param1, String param2) {
        attractions_results fragment = new attractions_results();
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
        return inflater.inflate(R.layout.fragment_attractions_results, container, false);
    }
    private AlertDialog.Builder dialogBuilder = attractions.AdialogBuilder ;
    private AlertDialog dialog = attractions.Adialog;
    private ImageButton filter;
    private Button closeF;
    final View FilterPopup = attractions.AFilterPopup;
    private NavController navController;
    public AttractionsAdapter adapter2;
    private Chip ac1,ac2,ac3,ac4,ac5;
    public static String fcat = "";
    public static List<AttractionModelClass> tempData=new ArrayList<>();
    String alogha;
    public static List<AttractionModelClass> aData2=new ArrayList<>();

    public static List<AttractionModelClass> aData3=new ArrayList<>();
    public void createFilteaDialog(){

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connection = true;
        } else
            connection = false;

        ImageButton backBtn = view.findViewById(R.id.backBtn_att2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_attractions_results_to_attractions);
            }
        });
        recyclerView = view.findViewById(R.id.recycler_att);
        filter = view.findViewById(R.id.AFilter);
        closeF = FilterPopup.findViewById(R.id.AFilter_close);


        ac1 = FilterPopup.findViewById(R.id.ac1);
        ac2 = FilterPopup.findViewById(R.id.ac2);
        ac3 = FilterPopup.findViewById(R.id.ac3);
        ac4 = FilterPopup.findViewById(R.id.ac4);
        ac5 = FilterPopup.findViewById(R.id.ac5);

        CompoundButton.OnCheckedChangeListener catChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tempData = new ArrayList<>(aData3);
                    aData3=new ArrayList<>();
                    fcat= buttonView.getText().toString();
                    for (AttractionModelClass data:tempData) {
                        if(data.getCategory().toLowerCase().contains(fcat.toLowerCase())){
                            aData3.add(data);
                        }
                    }
                }
                else{
                    aData3=new ArrayList<>(tempData);
                    }
                }

        };

        ac1.setOnCheckedChangeListener(catChangeListener);
        ac2.setOnCheckedChangeListener(catChangeListener);
        ac3.setOnCheckedChangeListener(catChangeListener);
        ac4.setOnCheckedChangeListener(catChangeListener);
        ac5.setOnCheckedChangeListener(catChangeListener);

        closeF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aData = new ArrayList<>();

                for(AttractionModelClass data : aData3){
                    aData.add(data);
                }

                adapter2 = new AttractionsAdapter(aData3, getActivity().getApplicationContext());
                recyclerView.swapAdapter(adapter2, false);
                dialog.hide();

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        if(attractions.fltr==1){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            adapter = new AttractionsAdapter(attractions_results.aData, getActivity().getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        ImageButton backhome = view.findViewById(R.id.backHomeA2);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_attractions_results_to_home);
            }
        });

        query = attractions.aquery;
        alogha=attractions.alogha;
        if(query.equals("")){ query="jemaa elfna";}

        if(attractions.fltr==0){

        if (connection) {
            String hi = " hi";
            OkHttpClient okHttpClient = new OkHttpClient();
            aData2= new ArrayList<>();
            aData=new ArrayList<>();
            if(MainActivity.server.equals("")){
                Toast.makeText(getActivity().getApplicationContext(), "You need to enter an ip address", Toast.LENGTH_LONG).show();
                getActivity().finish();
                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else{
            // building a request
                RequestBody formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("message", query)
                        .addFormDataPart("logha", alogha)
                        .build();
            Request request = new Request.Builder().url("http://"+MainActivity.server+":5000/searchAttractions").post(formBody).build();

            // making call asynchronously
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                // called if server is unreachable
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(), "server down", Toast.LENGTH_SHORT).show();
                            //tst.setText("error connecting to the server");
                        }
                    });
                }

                String dd ="";

                @Override
                // called if we get a
                // response from the server
                public void onResponse(
                        @NotNull Call call,
                        @NotNull Response response)
                        throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    } else {
                        res2 = response.body().string();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Python py = Python.getInstance();
                                res = py.getModule("my script").callAttr("attractions_index", res2).asList();
                                getData();
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                                adapter = new AttractionsAdapter(aData, getActivity().getApplicationContext());
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }
                }
            });}
        } else {
            Python py = Python.getInstance();
            if(attractions.name_search==0) {
                res = py.getModule("my script").callAttr("attractions", query).asList();
            }
            else{
                res = py.getModule("my script").callAttr("attraction_results", query).asList();
            }
            getData();
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            adapter = new AttractionsAdapter(aData, getActivity().getApplicationContext());
            recyclerView.setAdapter(adapter);


        } }
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                adetails = aData.get(position);
                navController.navigate(R.id.action_attractions_results_to_attraction_details);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void getData(){
        aData2=new ArrayList<>();
        aData=new ArrayList<>();
        for(int i=0;i<58;i++) {

            String[] rr=res.get(i).toJava(String[].class);
            AttractionModelClass data = new AttractionModelClass();

            data.setName(rr[0]);
            data.setCategory(rr[1]);
            data.setAdress(rr[2]);
            data.setImg("a"+rr[9]);
            data.setDescription(rr[3]);
            data.setOpen_dur(rr[6]);
            data.setSug_dur(rr[5]);
            data.setWebsite(rr[4]);
            data.setNear_res(rr[7]);
            data.setNear_att(rr[8]);
            data.setGps(rr[10]);
            aData2.add(data);

        }
        for(int j=0;j<30;j++){
            aData.add(attractions_results.aData2.get(j));
        }
        aData3 = new ArrayList<>(aData2);
    }
}