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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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
 * Use the {@link Reviews#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reviews extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Reviews() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reviews.
     */
    // TODO: Rename and change types and number of parameters
    public static Reviews newInstance(String param1, String param2) {
        Reviews fragment = new Reviews();
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
        return inflater.inflate(R.layout.reviews, container, false);
    }
    private float userRate = 0;
    boolean connection;
    protected  AlertDialog.Builder dialogBuilder ;
    protected  AlertDialog dialog ;
    protected  View review;
    EditText username ;
    EditText reviewtxt;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connection = true;
        } else
            connection = false;
        ImageButton backBtn = view.findViewById(R.id.backBtn_review);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_review_to_home);
            }
        });

        dialogBuilder = new AlertDialog.Builder(getContext());
        review = getLayoutInflater().inflate(R.layout.review_name,null);
        username=review.findViewById(R.id.userName);
        reviewtxt=review.findViewById(R.id.reviewtxt);

        dialogBuilder.setView(review);
        dialog = dialogBuilder.create();

        Button ReviewDone = review.findViewById(R.id.ReviewDone);

        ReviewDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("Rate", String.valueOf(userRate))
                        .addFormDataPart("username", String.valueOf(username.getText()))
                            .addFormDataPart("review", String.valueOf(reviewtxt.getText()))
                        .build();
                Request request = new Request.Builder().url("http://" + MainActivity.server + ":5000/rate").post(formBody).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                                //tst.setText("error connecting to the server");
                                dialog.hide();
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(), "server down", Toast.LENGTH_SHORT).show();
                                //tst.setText("error connecting to the server");
                            }
                        });
                    }
                });
            }
        });


        Button rateBtn = view.findViewById(R.id.rateNowBtn);
        RatingBar ratingBar = view.findViewById(R.id.appRate);
        ImageView ratingImg = view.findViewById(R.id.ratingImg);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating <=1){
                    ratingImg.setImageResource(R.drawable.one_star);
                }
                else if(rating <=2){
                    ratingImg.setImageResource(R.drawable.two_star);
                }
                else if(rating <=3){
                    ratingImg.setImageResource(R.drawable.three_star);
                }
                else if(rating <=4){
                    ratingImg.setImageResource(R.drawable.four_star);
                }
                else if(rating <=5){
                    ratingImg.setImageResource(R.drawable.five_star);
                }
                animateImg(ratingImg);

                userRate=rating;
            }
        });

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connection) {
                    if(MainActivity.server.equals("")){
                        Toast.makeText(getActivity().getApplicationContext(), "You need to enter an ip address", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                        Intent intent = new Intent(getContext(),MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        dialog.show();
                    }}
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "No Connection internet", Toast.LENGTH_SHORT).show();

                }
            }
        });
          }

    private void animateImg(ImageView ratingImage){
        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1f,0,1f, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.setAnimation(scaleAnimation);
    }
}