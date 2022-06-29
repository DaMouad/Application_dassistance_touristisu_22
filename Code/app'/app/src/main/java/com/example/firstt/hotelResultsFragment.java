package com.example.firstt;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

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
 * Use the {@link hotelResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class hotelResultsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecyclerView  recyclerView ;
    public static List<HotelModelClass> hData=new ArrayList<>();
    public static List<HotelModelClass> hData2= new ArrayList<>();
    public static TextView tst ;
    public HotelAdapter adapter;
    List<PyObject> res;
    String res2;
    String query;
    String logha;
    boolean connection;
    public static HotelModelClass hdetails;


    public hotelResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment hotelResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static hotelResultsFragment newInstance(String param1, String param2) {
        hotelResultsFragment fragment = new hotelResultsFragment();
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
        return inflater.inflate(R.layout.fragment_hotel_results, container, false);
    }
    String dd;

    private AlertDialog.Builder dialogBuilder = HotelFragment.HdialogBuilder ;
    private AlertDialog dialog = HotelFragment.Hdialog;
    private ImageButton filter;
    private Button closeF;
    final View FilterPopup = HotelFragment.HFilterPopup;
    private NavController navController;


    private Chip hs1,hs2,hs3,hs4,hs5,ht1,ht2,ht3,ht4,ht5,ht6,he1,he2,he3,he4,he5,he6,he7,he8,hn1,hn2,hn3;
    public String fstar = "";
    public static String ftype = "";
    public static String fequip = "";
    public static String fnearby = "";
    public static List<HotelModelClass> hData3;
    private List<HotelModelClass> tempDatas=new ArrayList<>();
    private List<HotelModelClass> tempDatat=new ArrayList<>();
    private List<HotelModelClass> tempDatae=new ArrayList<>();
    private List<HotelModelClass> tempDatan=new ArrayList<>();
    public HotelAdapter adapter2;
    public void createFilterDialog(){



    }
    ChipGroup GStars ;
    ChipGroup GType ;
    ChipGroup GEquip ;
    ChipGroup GNear ;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tst = view.findViewById(R.id.testview);
        navController = Navigation.findNavController(view);
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connection = true;
        } else
            connection = false;
        ImageButton backBtn = view.findViewById(R.id.backtohot);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_hotelResultsFragment2_to_hotelFragment22);
            }
        });
        filter = view.findViewById(R.id.HFilter);
        closeF = FilterPopup.findViewById(R.id.HFilter_close);

        GStars = FilterPopup.findViewById(R.id.Fstars);
        GType = FilterPopup.findViewById(R.id.Htype);
        GEquip = FilterPopup.findViewById(R.id.Hequip);
        GNear = FilterPopup.findViewById(R.id.Hnearby);


        hs1 = FilterPopup.findViewById(R.id.hs1);
        hs2 = FilterPopup.findViewById(R.id.hs2);
        hs3 = FilterPopup.findViewById(R.id.hs3);
        hs4 = FilterPopup.findViewById(R.id.hs4);
        hs5 = FilterPopup.findViewById(R.id.hs5);
        ht2 = FilterPopup.findViewById(R.id.ht2);
        ht1 = FilterPopup.findViewById(R.id.ht1);
        ht3 = FilterPopup.findViewById(R.id.ht3);
        ht4 = FilterPopup.findViewById(R.id.ht4);
        ht5 = FilterPopup.findViewById(R.id.ht5);
        ht6 = FilterPopup.findViewById(R.id.ht6);
        he1 = FilterPopup.findViewById(R.id.he1);
        he2 = FilterPopup.findViewById(R.id.he2);
        he3 = FilterPopup.findViewById(R.id.he3);
        he4 = FilterPopup.findViewById(R.id.he4);
        he5 = FilterPopup.findViewById(R.id.he5);
        he6 = FilterPopup.findViewById(R.id.he6);
        he7 = FilterPopup.findViewById(R.id.he7);
        he8 = FilterPopup.findViewById(R.id.he8);
        hn1 = FilterPopup.findViewById(R.id.hn1);
        hn2 = FilterPopup.findViewById(R.id.hn2);
        hn3 = FilterPopup.findViewById(R.id.hn3);


        CompoundButton.OnCheckedChangeListener starsListener = new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tempDatas=new ArrayList<>(hData3);
                    hData3=new ArrayList<>();

                    fstar= buttonView.getText().toString();
                    for (HotelModelClass data:tempDatas) {
                        if(data.getStars().contains(buttonView.getText().toString().toLowerCase())){
                            hData3.add(data);
                        }

                    }
                }
                else{
                  /*  fstar= buttonView.getText().toString();
                    for (HotelModelClass data:tempDatas) {
                        if(!data.getStars().contains(buttonView.getText().toString().toLowerCase())){
                            hData3.add(data);}}*/
                    hData3=new ArrayList<>(tempDatas);
                            if(ht1.isChecked()){ tempDatat=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                ftype= ht1.getText().toString();
                                for (HotelModelClass data:tempDatat) {
                                    if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                        hData3.add(data);
                                    }
                                } }
                            if(ht2.isChecked()){ tempDatat=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                ftype= ht2.getText().toString();
                                for (HotelModelClass data:tempDatat) {
                                    if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                        hData3.add(data);
                                    }
                                } }
                            if(ht3.isChecked()){ tempDatat=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                ftype= ht3.getText().toString();
                                for (HotelModelClass data:tempDatat) {
                                    if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                        hData3.add(data);
                                    }
                                }}
                            if(ht4.isChecked()){ tempDatat=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                ftype= ht4.getText().toString();
                                for (HotelModelClass data:tempDatat) {
                                    if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                        hData3.add(data);
                                    }
                                } }
                            if(ht5.isChecked()){ tempDatat=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                ftype= ht5.getText().toString();
                                for (HotelModelClass data:tempDatat) {
                                    if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                        hData3.add(data);
                                    }
                                } }
                            if(ht6.isChecked()){tempDatat=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                ftype= ht6.getText().toString();
                                for (HotelModelClass data:tempDatat) {
                                    if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                        hData3.add(data);
                                    }
                                } }
                            if(he1.isChecked()){  tempDatae=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                fequip= he1.getText().toString();
                                for (HotelModelClass data:tempDatae) {
                                    if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                        hData3.add(data);
                                    }
                                } }
                            if(he1.isChecked()){  tempDatae=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                fequip= he1.getText().toString();
                                for (HotelModelClass data:tempDatae) {
                                    if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                        hData3.add(data);
                                    }
                                } }
                            if(he2.isChecked()){ tempDatae=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                fequip= he2.getText().toString();
                                for (HotelModelClass data:tempDatae) {
                                    if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                        hData3.add(data);
                                    }
                                }  }
                            if(he3.isChecked()){  tempDatae=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                fequip= he3.getText().toString();
                                for (HotelModelClass data:tempDatae) {
                                    if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                        hData3.add(data);
                                    }
                                } }
                            if(he4.isChecked()){  tempDatae=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                fequip= he4.getText().toString();
                                for (HotelModelClass data:tempDatae) {
                                    if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                        hData3.add(data);
                                    }
                                } }
                            if(he5.isChecked()){  tempDatae=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                fequip= he5.getText().toString();
                                for (HotelModelClass data:tempDatae) {
                                    if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                        hData3.add(data);
                                    }
                                } }
                            if(he6.isChecked()){  tempDatae=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                fequip= he6.getText().toString();
                                for (HotelModelClass data:tempDatae) {
                                    if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                        hData3.add(data);
                                    }
                                } }
                            if(he7.isChecked()){ tempDatae=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                fequip= he7.getText().toString();
                                for (HotelModelClass data:tempDatae) {
                                    if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                        hData3.add(data);
                                    }
                                }  }
                            if(he8.isChecked()){  tempDatae=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                fequip= he8.getText().toString();
                                for (HotelModelClass data:tempDatae) {
                                    if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                        hData3.add(data);
                                    }
                                } }
                            if(hn1.isChecked()){  tempDatan=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                fnearby= hn1.getText().toString();
                                for (HotelModelClass data:tempDatan) {
                                    if(data.getNear_att().toLowerCase().contains(fnearby.toLowerCase())){
                                        hData3.add(data);
                                    }
                                }  }
                            if(hn2.isChecked()){ tempDatan=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                fnearby= hn2.getText().toString();
                                for (HotelModelClass data:tempDatan) {
                                    if(data.getNear_att().toLowerCase().contains(fnearby.toLowerCase())){
                                        hData3.add(data);
                                    }
                                }  }
                            if(hn3.isChecked()){ tempDatan=new ArrayList<>(hData3);
                                hData3=new ArrayList<>();
                                fnearby= hn3.getText().toString();
                                for (HotelModelClass data:tempDatan) {
                                    if(data.getNear_att().toLowerCase().contains(fnearby.toLowerCase())){
                                        hData3.add(data);
                                    }
                                }  }

                    }
            }
        };

        CompoundButton.OnCheckedChangeListener nearListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tempDatan=new ArrayList<>(hData3);
                    hData3=new ArrayList<>();
                    fnearby= buttonView.getText().toString();
                    for (HotelModelClass data:tempDatan) {
                        if(data.getNear_att().toLowerCase().contains(fnearby.toLowerCase()) || data.getDescription().toLowerCase().contains(fnearby.toLowerCase())){
                            hData3.add(data);
                        }
                    }
                }
                else{
                    hData3=new ArrayList<>(tempDatan);
                    if(ht1.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht1.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(ht2.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht2.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(ht3.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht3.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        }}
                    if(ht4.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht4.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(ht5.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht5.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(ht6.isChecked()){tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht6.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he1.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he1.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he1.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he1.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he2.isChecked()){ tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he2.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(he3.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he3.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he4.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he4.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he5.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he5.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he6.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he6.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he7.isChecked()){ tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he7.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(he8.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he8.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(hs1.isChecked()){  tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs1.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hs2.isChecked()){  tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs2.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hs3.isChecked()){tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs3.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hs4.isChecked()){tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs4.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hs5.isChecked()){tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs5.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                }
            }
        };

        CompoundButton.OnCheckedChangeListener typeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tempDatat=new ArrayList<>(hData3);
                    hData3=new ArrayList<>();
                    ftype= buttonView.getText().toString();
                    for (HotelModelClass data:tempDatat) {
                        if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                            hData3.add(data);
                        }
                    }
                }
                else{
                    hData3=new ArrayList<>(tempDatat);
                    if(he1.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he1.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he1.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he1.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he2.isChecked()){ tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he2.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(he3.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he3.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he4.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he4.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he5.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he5.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he6.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he6.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(he7.isChecked()){ tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he7.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(he8.isChecked()){  tempDatae=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fequip= he8.getText().toString();
                        for (HotelModelClass data:tempDatae) {
                            if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(hs1.isChecked()){  tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs1.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hs2.isChecked()){  tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs2.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hs3.isChecked()){tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs3.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hs4.isChecked()){tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs4.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hs5.isChecked()){tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs5.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hn1.isChecked()){  tempDatan=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fnearby= hn1.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getNear_att().toLowerCase().contains(fnearby.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hn2.isChecked()){ tempDatan=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fnearby= hn2.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getNear_att().toLowerCase().contains(fnearby.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hn3.isChecked()){ tempDatan=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fnearby= hn3.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getNear_att().toLowerCase().contains(fnearby.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(ht1.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht1.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(ht2.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht2.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(ht3.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht3.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        }}
                    if(ht4.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht4.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(ht5.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht5.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(ht6.isChecked()){tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht6.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                }
            }
        };

        CompoundButton.OnCheckedChangeListener equipListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tempDatae=new ArrayList<>(hData3);
                    hData3=new ArrayList<>();
                    fequip= buttonView.getText().toString();
                    for (HotelModelClass data:tempDatae) {
                        if(data.getProps().toLowerCase().contains(fequip.toLowerCase())){
                            hData3.add(data);
                        }
                    }
                }
                else{
                    hData3=new ArrayList<>(tempDatae);
                    if(ht1.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht1.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(ht2.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht2.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(ht3.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht3.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        }}
                    if(ht4.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht4.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(ht5.isChecked()){ tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht5.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(ht6.isChecked()){tempDatat=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        ftype= ht6.getText().toString();
                        for (HotelModelClass data:tempDatat) {
                            if(data.getType().toLowerCase().contains(ftype.toLowerCase())){
                                hData3.add(data);
                            }
                        } }
                    if(hs1.isChecked()){  tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs1.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }

                    if(hs2.isChecked()){  tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs2.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hs3.isChecked()){tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs3.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hs4.isChecked()){tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs4.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hs5.isChecked()){tempDatas=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fstar= hs5.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getStars().toLowerCase().contains(fstar.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hn1.isChecked()){  tempDatan=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fnearby= hn1.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getNear_att().toLowerCase().contains(fnearby.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hn2.isChecked()){ tempDatan=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fnearby= hn2.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getNear_att().toLowerCase().contains(fnearby.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                    if(hn3.isChecked()){ tempDatan=new ArrayList<>(hData3);
                        hData3=new ArrayList<>();
                        fnearby= hn3.getText().toString();
                        for (HotelModelClass data:tempDatan) {
                            if(data.getNear_att().toLowerCase().contains(fnearby.toLowerCase())){
                                hData3.add(data);
                            }
                        }  }
                }
            }
        };

        hs1.setOnCheckedChangeListener(starsListener);
        hs2.setOnCheckedChangeListener(starsListener);
        hs3.setOnCheckedChangeListener(starsListener);
        hs4.setOnCheckedChangeListener(starsListener);
        hs5.setOnCheckedChangeListener(starsListener);
        ht1.setOnCheckedChangeListener(typeListener);
        ht2.setOnCheckedChangeListener(typeListener);
        ht3.setOnCheckedChangeListener(typeListener);
        ht4.setOnCheckedChangeListener(typeListener);
        ht5.setOnCheckedChangeListener(typeListener);
        ht6.setOnCheckedChangeListener(typeListener);
        he1.setOnCheckedChangeListener(equipListener);
        he1.setOnCheckedChangeListener(equipListener);
        he2.setOnCheckedChangeListener(equipListener);
        he3.setOnCheckedChangeListener(equipListener);
        he4.setOnCheckedChangeListener(equipListener);
        he5.setOnCheckedChangeListener(equipListener);
        he6.setOnCheckedChangeListener(equipListener);
        he7.setOnCheckedChangeListener(equipListener);
        he8.setOnCheckedChangeListener(equipListener);
        hn1.setOnCheckedChangeListener(nearListener);
        hn2.setOnCheckedChangeListener(nearListener);
        hn3.setOnCheckedChangeListener(nearListener);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        closeF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hData=new ArrayList<>();
                for(HotelModelClass data:hData3){
                    hData.add(data);
                }
                adapter2 = new HotelAdapter(hData, getActivity().getApplicationContext());
                recyclerView.swapAdapter(adapter2, false);
                RestaurantFragment.fltr=1;
               // navController.navigate(R.id.action_hotelResultsFragment2_self);

                dialog.hide();
            }
        });
        if(HotelFragment.fltr==1){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            adapter = new HotelAdapter(hotelResultsFragment.hData, getActivity().getApplicationContext());
            recyclerView.setAdapter(adapter);
            hData=new ArrayList<>(hotelResultsFragment.hData);
        }

            //adapter2 = new HotelAdapter(hotelResultsFragment.hData3, getActivity().getApplicationContext());
            //recyclerView.swapAdapter(adapter2, false);

        ImageButton backhome = view.findViewById(R.id.backHome1);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_hotelResultsFragment2_to_homeFragment);
            }
        });
        query = HotelFragment.hquery;
        logha=HotelFragment.logha;
        if(query.equals("")){ query="five";}
        if(HotelFragment.fltr==0){

            if (connection) {
                OkHttpClient okHttpClient = new OkHttpClient();
                // building a request
                RequestBody formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("message", query)
                        .addFormDataPart("logha", logha)
                        .build();
                if(MainActivity.server.equals("")){
                    Toast.makeText(getActivity().getApplicationContext(), "You need to enter an ip address", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    Intent intent = new Intent(getContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Request request = null;
                if(HotelFragment.name_search==0) {
                     request = new Request.Builder().url("http://" + MainActivity.server + ":5000/searchHotel").post(formBody).build();
                }
                else if(HotelFragment.name_search==1) {
                     request = new Request.Builder().url("http://" + MainActivity.server + ":5000/searchHotel").post(formBody).build();
                }

                hData2= new ArrayList<>();
                hData=new ArrayList<>();
                // making call asynchronously
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    // called if server is unreachable
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(), "server down", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity().getApplicationContext(), "Error connectiong to the server", Toast.LENGTH_LONG).show();
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
                                public void run() {
                                    Python py = Python.getInstance();
                                    if(HotelFragment.name_search==0) {
                                        res = py.getModule("my script").callAttr("hotels_index", res2).asList();
                                        getData();
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                                        adapter = new HotelAdapter(hData, getActivity().getApplicationContext());
                                        recyclerView.setAdapter(adapter);
                                    }
                                }
                            });
                        }
                    }
                }); }

            } else {
                Python py = Python.getInstance();
                if(HotelFragment.name_search==0) {
                    res = py.getModule("my script").callAttr("hotels", query).asList();
                }
                else if(HotelFragment.name_search==1){
                    res = py.getModule("my script").callAttr("hotel_names", query).asList();
                }
                getData();
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                adapter = new HotelAdapter(hData, getActivity().getApplicationContext());
                recyclerView.setAdapter(adapter);
            }}
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    hdetails = hData.get(position);
                    navController.navigate(R.id.action_hotelResultsFragment2_to_hoteldetails2);
                }
                @Override
                public void onLongClick(View view, int position) {

                }
            }));

    }
    private void getData(){
        hData2= new ArrayList<>();
        hData=new ArrayList<>();
        for(int i=0;i<1000;i++) {
            /* 0:Name1:Type 2:Stars  3:Description 4:address 5:Tel
            6:website 7:near_res 8:near_att 9:Properties 10:Styles 11:img*/
            String[] rr=res.get(i).toJava(String[].class);
            HotelModelClass data = new HotelModelClass();
            data.setName(rr[0]);
            data.setType(rr[1]);
           // data.setStars("4");
            data.setStars(rr[2]);
            data.setDescription(rr[3]);
            data.setProps(rr[9]);
            data.setAddress(rr[4]);
            data.setPhone(rr[5]);
            data.setWebsite(rr[6]);
            data.setNear_att(rr[8]);
            data.setNear_res(rr[7]);
            data.setImg("h"+rr[11]);
            data.setGps(rr[12]);
            hData2.add(data);

        }
        for(int j=0;j<30;j++){
            hData.add(hData2.get(j));
        }
        hData3=new ArrayList<>(hData2);

    }

}