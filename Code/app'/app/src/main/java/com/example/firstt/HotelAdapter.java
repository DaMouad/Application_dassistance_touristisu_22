package com.example.firstt;

import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyViewHolder> {

   // private Context hContext;
   List<HotelModelClass> hData;
    LayoutInflater inflater;
    Context hContext ;
    public HotelAdapter(List<HotelModelClass> hData, Context hContext) {
        this.hData = hData;
        this.hContext=hContext;
        this.inflater = LayoutInflater.from(hContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.hotel_items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         holder.name.setText(hData.get(position).getName());
         holder.type.setText(hData.get(position).getType());

         //holder.img.setBackground(Drawable.createFromPath("drawable/logo1"));

        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = hContext.getResources().getDrawable(
                    hContext.getResources().getIdentifier(hData.get(position).getImg(),"drawable", hContext.getPackageName()));
         holder.img.setBackground(drawable);


         if(hData.get(position).getStars().equals("unclassified")) {
             holder.rate.setVisibility(View.INVISIBLE);
         }
         else {
             holder.rate.setRating(Float.parseFloat(hData.get(position).getStars()));
         }


    }

    @Override
    public int getItemCount() {
        return hData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name ;
        TextView type ;
        ImageView img ;
        RatingBar rate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.hot_name1);
            this.type = itemView.findViewById(R.id.type1);
            this.img = itemView.findViewById(R.id.hotel_img);
            this.rate = itemView.findViewById(R.id.ratingBar1);
        }
    }
}
