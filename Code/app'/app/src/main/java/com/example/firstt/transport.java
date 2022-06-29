package com.example.firstt;

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
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link transport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class transport extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public transport() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment transport.
     */
    // TODO: Rename and change types and number of parameters
    public static transport newInstance(String param1, String param2) {
        transport fragment = new transport();
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
        return inflater.inflate(R.layout.fragment_transport, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView;
        transportAdapter adapter ;
        List<transportModelClass> tData=new ArrayList<>();
        transportModelClass bigtaxi = new transportModelClass();
        transportModelClass smalltaxi = new transportModelClass();
        transportModelClass greentaxi = new transportModelClass();
        transportModelClass Kutchi = new transportModelClass();
        transportModelClass tuktuk = new transportModelClass();
        transportModelClass bus = new transportModelClass();
        NavController navController = Navigation.findNavController(view);

        ImageButton backBtn_tr = view.findViewById(R.id.backBtn_tr);
        backBtn_tr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_transport_to_home);
            }
        });

        bigtaxi.setName("Big Taxi");
        bigtaxi.setPrice("Price : From 5 MAD inside the city \n From 8 MAD outside the city");
        bigtaxi.setImg("kbir");
        bigtaxi.setDesc("In town a grand taxi (old Mercedes) is much more expensive than a small taxi (unless you share it on frequent routes, such as from the bus station to the train station). They are themselves authorized to leave the agglomeration and do not have a meter. So negotiate the race BEFORE it starts. For a race within a radius of 15 to 20 km from the city center, count 120 Dh minimum.");
        bigtaxi.setTts("Big Taxi"+"."+"Price is : From 5 MAD inside the city \n From 8 MAD outside the city"+"..In town a grand taxi (old Mercedes) is much more expensive than a small taxi (unless you share it on frequent routes, such as from the bus station to the train station). They are themselves authorized to leave the agglomeration and do not have a meter. So negotiate the race BEFORE it starts. For a race within a radius of 15 to 20 km from the city center, count 120 Dh minimum.");

        smalltaxi.setName("Small Taxi");
        smalltaxi.setPrice("Price :  From 5 MAD");
        smalltaxi.setImg("petit");
        smalltaxi.setDesc("They can be found almost everywhere, near Jemaa-el-Fna square (cars are prohibited from 1 p.m.), along Foucauld square, at the bus station and on Mohammed-V avenue. Small taxis are in the majority. They cannot leave Marrakech.");
        smalltaxi.setTts("Small Taxi. Price :  From 5 MAD .. They can be found almost everywhere, near Jemaa-el-Fna square (cars are prohibited from 1 p.m.), along Foucauld square, at the bus station and on Mohammed-V avenue. Small taxis are in the majority. They cannot leave Marrakech.");

        greentaxi.setName("Green Taxi");
        greentaxi.setDesc("Price is fixed in advance on the basis of the metered rate plus 10 Dh during the day and 15 Dh in the evening (double for the palm grove). The taxi then picks you up at the agreed time and place (some punctuality concerns to report). For outlying journeys (beyond the palm grove and on the road to Fez, for example), fixed rate of 100 Dh (increased in the evening).");
       // greentaxi.setPrice("");
        greentaxi.setImg("green");
        greentaxi.setTts("Green Taxi. Price is fixed in advance on the basis of the metered rate plus 10 Dh during the day and 15 Dh in the evening (double for the palm grove). The taxi then picks you up at the agreed time and place (some punctuality concerns to report). For outlying journeys (beyond the palm grove and on the road to Fez, for example), fixed rate of 100 Dh (increased in the evening).");

        Kutchi.setName("Kutchi");
        Kutchi.setPrice("Price : From 5 MAD");
        Kutchi.setDesc("Marrakech is one of the few cities in Morocco to have been able to keep this mode of transport, which is full of charm. For the nostalgic, a great way to get around town (you can go up to 4-5 people), provided you discuss the price well and set the amount before departure.");
        Kutchi.setImg("kutchi");
        Kutchi.setTts("Kutchi. Price : From 5 MAD..Marrakech is one of the few cities in Morocco to have been able to keep this mode of transport, which is full of charm. For the nostalgic, a great way to get around town (you can go up to 4-5 people), provided you discuss the price well and set the amount before departure.");

        tuktuk.setName("Tuk Tuk");
        tuktuk.setPrice("Price : From 5 MAD");
        tuktuk.setDesc("Mode of transport imported from Asia launched in Marrakech by an association which helps people with reduced mobility by allowing them to reintegrate through work. They circulate only in the medina (practical, therefore) and are not insured outside.");
        tuktuk.setImg("tuk");
        tuktuk.setTts("Tuk TUk. Price : From 5 MAD..Mode of transport imported from Asia launched in Marrakech by an association which helps people with reduced mobility by allowing them to reintegrate through work. They circulate only in the medina (practical, therefore) and are not insured outside. ");

        bus.setName("Bus");
        bus.setPrice("Price : 4 MAD inside the City \n From 5MAD Outside the city\n");
        bus.setDesc("Thirty lines share the network. Tickets are taken on board. Price: 4 Dh for a journey downtown. They are comfortable and sometimes air-conditioned For a stay of at least 5 days and intensive use of this means of transport, there is an Ikhlass magnetic card at 15 Dh which can be bought on all buses (it must then be loaded, the journey then being 3.50 Dhs).");
        bus.setImg("bus");
        bus.setTts("Bus. Price: 4 MAD inside the City , From 5MAD Outside the city..Thirty lines share the network. Tickets are taken on board. Price: 4 Dh for a journey downtown. They are comfortable and sometimes air-conditioned For a stay of at least 5 days and intensive use of this means of transport, there is an Ikhlass magnetic card at 15 Dh which can be bought on all buses (it must then be loaded, the journey then being 3.50 Dhs).");

        tData.add(bigtaxi);
        tData.add(smalltaxi);
        tData.add(bus);
        tData.add(greentaxi);
        tData.add(Kutchi);
        tData.add(tuktuk);

        recyclerView=view.findViewById(R.id.recycler_tran);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter = new transportAdapter(tData, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}