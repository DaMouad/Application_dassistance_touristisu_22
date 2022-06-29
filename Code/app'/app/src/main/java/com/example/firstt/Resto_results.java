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

import android.speech.RecognitionService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
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
 * Use the {@link Resto_results#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Resto_results extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecyclerView  recyclerView ;
    public static List<RestoModelClass> rData= new ArrayList<>();

    RestoAdapter adapter;
    List<PyObject> res;
    String res2;
    String query;
    boolean connection;
    public static List<RestoModelClass>  rData2= new ArrayList<>();

    public static RestoModelClass rdetails;

    public Resto_results() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Resto_results.
     */
    // TODO: Rename and change types and number of parameters
    public static Resto_results newInstance(String param1, String param2) {
        Resto_results fragment = new Resto_results();
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
        return inflater.inflate(R.layout.fragment_resto_results, container, false);
    }

    String dd;
    private AlertDialog.Builder dialogBuilder = RestaurantFragment.RdialogBuilder ;
    private AlertDialog dialog = RestaurantFragment.Rdialog;
    private ImageButton filter;
    private Button closeF;
    final View FilterPopup = RestaurantFragment.RFilterPopup;
    private NavController navController;
    public RestoAdapter adapter2;
    private Chip rf1,rf2,rf3,rf4,rf5,rk1,rk2,rk3,rk4,rk5;
    public String fkitchen = "";
    public String ffeat = "";
    public static List<RestoModelClass> rData3=new ArrayList<>();
    public static List<RestoModelClass> tempData1=new ArrayList<>();
    public static List<RestoModelClass> tempData2=new ArrayList<>();
    private List<RestoModelClass> tempData3=new ArrayList<>();
    String rlogha;
    public void createFilterDialog(){

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connection = true;
        }
        else{
            connection = false;}
        ImageButton backBtn = view.findViewById(R.id.backtores2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_resto_results_to_restos);
            }
        });

        filter = view.findViewById(R.id.RFilter);
        TextView tst = view.findViewById(R.id.testview);
        closeF = FilterPopup.findViewById(R.id.RFilter_close);

        rf1 = FilterPopup.findViewById(R.id.rf1);
        rf2 = FilterPopup.findViewById(R.id.rf2);
        rf3 = FilterPopup.findViewById(R.id.rf3);
        rf4 = FilterPopup.findViewById(R.id.rf4);
        rf5 = FilterPopup.findViewById(R.id.rf5);
        rk1 = FilterPopup.findViewById(R.id.rk1);
        rk2 = FilterPopup.findViewById(R.id.rk2);
        rk3 = FilterPopup.findViewById(R.id.rk3);
        rk4 = FilterPopup.findViewById(R.id.rk4);
        rk5 = FilterPopup.findViewById(R.id.rk5);

        CompoundButton.OnCheckedChangeListener kitchenChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tempData1=new ArrayList<>(rData3);
                    rData3=new ArrayList<>();
                    fkitchen= buttonView.getText().toString();
                    for (RestoModelClass data:tempData1) {
                        if(data.getKitchen().toLowerCase().contains(fkitchen.toLowerCase())){
                            rData3.add(data);
                        }
                    }
                }else{
                    rData3=new ArrayList<>(tempData1);
                    if(rf1.isChecked()){  tempData2=new ArrayList<>(rData3);
                        rData3=new ArrayList<>();
                        ffeat= rf1.getText().toString();
                        for (RestoModelClass data:tempData2) {
                            if(data.getKitchen().toLowerCase().contains(ffeat.toLowerCase())){
                                rData3.add(data);
                            }
                        }  }
                    if(rf2.isChecked()){  tempData2=new ArrayList<>(rData3);
                        rData3=new ArrayList<>();
                        ffeat= rf2.getText().toString();
                        for (RestoModelClass data:tempData2) {
                            if(data.getKitchen().toLowerCase().contains(ffeat.toLowerCase())){
                                rData3.add(data);
                            }
                        }  }
                    if(rf3.isChecked()){  tempData2=new ArrayList<>(rData3);
                        rData3=new ArrayList<>();
                        ffeat= rf3.getText().toString();
                        for (RestoModelClass data:tempData2) {
                            if(data.getKitchen().toLowerCase().contains(ffeat.toLowerCase())){
                                rData3.add(data);
                            }
                        }  }
                    if(rf4.isChecked()){  tempData2=new ArrayList<>(rData3);
                        rData3=new ArrayList<>();
                        ffeat= rf4.getText().toString();
                        for (RestoModelClass data:tempData2) {
                            if(data.getKitchen().toLowerCase().contains(ffeat.toLowerCase())){
                                rData3.add(data);
                            }
                        }  }
                    if(rf5.isChecked()){  tempData2=new ArrayList<>(rData3);
                        rData3=new ArrayList<>();
                        ffeat= rf5.getText().toString();
                        for (RestoModelClass data:tempData2) {
                            if(data.getKitchen().toLowerCase().contains(ffeat.toLowerCase())){
                                rData3.add(data);
                            }
                        }  }

                }

            }
        };

        CompoundButton.OnCheckedChangeListener featsChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    tempData2=new ArrayList<>(rData3);
                    rData3=new ArrayList<>();
                    ffeat= buttonView.getText().toString();
                    for (RestoModelClass data:tempData2) {
                        if(data.getFeats().toLowerCase().contains(ffeat.toLowerCase())){
                            rData3.add(data);
                        }
                    }
                }else{
                    rData3=new ArrayList<>(tempData2);
                    if(rk1.isChecked()){  tempData1=new ArrayList<>(rData3);
                        rData3=new ArrayList<>();
                        fkitchen= rk1.getText().toString();
                        for (RestoModelClass data:tempData1) {
                            if(data.getKitchen().toLowerCase().contains(fkitchen.toLowerCase())){
                                rData3.add(data);
                            }
                        }  }
                    if(rk2.isChecked()){  tempData1=new ArrayList<>(rData3);
                        rData3=new ArrayList<>();
                        fkitchen= rk2.getText().toString();
                        for (RestoModelClass data:tempData1) {
                            if(data.getKitchen().toLowerCase().contains(fkitchen.toLowerCase())){
                                rData3.add(data);
                            }
                        }  }
                    if(rk3.isChecked()){  tempData1=new ArrayList<>(rData3);
                        rData3=new ArrayList<>();
                        fkitchen= rk3.getText().toString();
                        for (RestoModelClass data:tempData1) {
                            if(data.getKitchen().toLowerCase().contains(fkitchen.toLowerCase())){
                                rData3.add(data);
                            }
                        }  }
                    if(rk4.isChecked()){  tempData1=new ArrayList<>(rData3);
                        rData3=new ArrayList<>();
                        fkitchen= rk4.getText().toString();
                        for (RestoModelClass data:tempData1) {
                            if(data.getKitchen().toLowerCase().contains(fkitchen.toLowerCase())){
                                rData3.add(data);
                            }
                        }  }
                    if(rk5.isChecked()){  tempData1=new ArrayList<>(rData3);
                        rData3=new ArrayList<>();
                        fkitchen= rk5.getText().toString();
                        for (RestoModelClass data:tempData1) {
                            if(data.getKitchen().toLowerCase().contains(fkitchen.toLowerCase())){
                                rData3.add(data);
                            }
                        }  }


                }

            }
        };
        rk1.setOnCheckedChangeListener(kitchenChangeListener);
        rk2.setOnCheckedChangeListener(kitchenChangeListener);
        rk3.setOnCheckedChangeListener(kitchenChangeListener);
        rk4.setOnCheckedChangeListener(kitchenChangeListener);
        rk5.setOnCheckedChangeListener(kitchenChangeListener);

        rf1.setOnCheckedChangeListener(featsChangeListener);
        rf2.setOnCheckedChangeListener(featsChangeListener);
        rf3.setOnCheckedChangeListener(featsChangeListener);
        rf4.setOnCheckedChangeListener(featsChangeListener);
        rf5.setOnCheckedChangeListener(featsChangeListener);

        recyclerView = view.findViewById(R.id.recycler_res);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        closeF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rData=new ArrayList<>();
                for(RestoModelClass data:rData3){
                    rData.add(data);
                }
                adapter2 = new RestoAdapter(rData, getActivity().getApplicationContext());
                recyclerView.swapAdapter(adapter2, true);
                dialog.hide();

            }
        });

        if(RestaurantFragment.fltr==1){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            adapter = new RestoAdapter(Resto_results.rData, getActivity().getApplicationContext());
            recyclerView.setAdapter(adapter);
            rData3=new ArrayList<>(Resto_results.rData);
        }

        ImageButton backhome = view.findViewById(R.id.backHome);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_resto_results_to_home);
            }
        });

        query = RestaurantFragment.rquery;
        rlogha=RestaurantFragment.rlogha;
        if(query.equals("")){ query="restaurant morocco";}
        if(RestaurantFragment.fltr==0){

        if (connection) {
            String hi = " hi";
            OkHttpClient okHttpClient = new OkHttpClient();
            rData2= new ArrayList<>();
            rData=new ArrayList<>();
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
                        .addFormDataPart("logha", rlogha)
                        .build();
            Request request = new Request.Builder().url("http://"+MainActivity.server+":5000/searchRestaurants").post(formBody).build();


            // making call asynchronously
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                // called if server is unreachable
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(), "server down", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity().getApplicationContext(), "error connecting to the server", Toast.LENGTH_LONG).show();
                            //tst.setText("error connecting to the server");
                        }
                    });
                }

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
                            public void run() {Python py = Python.getInstance();
                                res = py.getModule("my script").callAttr("restos_index", res2).asList();
                                getData();
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                                adapter = new RestoAdapter(rData, getActivity().getApplicationContext());
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }
                }
            });}
        } else {
            Python py = Python.getInstance();
            if(RestaurantFragment.name_search==0) {
                res = py.getModule("my script").callAttr("restos", query).asList();
            }
            if(RestaurantFragment.name_search==1) {
                res = py.getModule("my script").callAttr("resto_names", query).asList();
            }
            getData();
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            adapter = new RestoAdapter(rData, getActivity().getApplicationContext());
            recyclerView.setAdapter(adapter);

        }}
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                rdetails = rData.get(position);
                navController.navigate(R.id.action_resto_results_to_restoDetails);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }
    private void getData(){
        rData2= new ArrayList<>();
        rData= new ArrayList<>();
        for(int i=0;i<1000;i++) {

            String[] rr=res.get(i).toJava(String[].class);
            RestoModelClass data = new RestoModelClass();

            data.setName(rr[0]);
            data.setAddress(rr[1]);
            data.setDescription(rr[2]);
            data.setPhone(rr[3]);
            data.setWebsite(rr[4]);
            data.setKitchen(rr[5]);
            data.setFeats(rr[6]);
            data.setMeals(rr[7]);
            data.setPrice(rr[8]);

            data.setNear_att(rr[10]);
            data.setNear_hot(rr[11]);

            data.setSpec_diets(rr[13]);
            data.setImg("r"+rr[14]);
            data.setGps(rr[15]);
            rData2.add(data);

        }
        for(int j=0;j<30;j++){
            rData.add(Resto_results.rData2.get(j));
        }
        rData3=new ArrayList<>(rData2);
    }
}