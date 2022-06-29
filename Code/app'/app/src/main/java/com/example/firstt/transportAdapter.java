package com.example.firstt;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.speech.tts.TextToSpeech;
import java.util.List;
import java.util.Locale;

public class transportAdapter extends RecyclerView.Adapter<transportAdapter.MyViewHolder> {
    List<transportModelClass> tData;
    LayoutInflater inflater;
    Context tContext ;

    public transportAdapter(List<transportModelClass> tData, Context tContext) {
        this.tData = tData;
        this.tContext=tContext;
        this.inflater = LayoutInflater.from(tContext);
    }
    @NonNull
    @Override
    public transportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.transport_items,parent,false);
        return new transportAdapter.MyViewHolder(view);
    }
    TextToSpeech t1;
    String tts="";
    int talk=0;
    @Override
    public void onBindViewHolder(@NonNull transportAdapter.MyViewHolder holder, int position) {
        holder.name.setText(tData.get(position).getName());
        holder.desc.setText(tData.get(position).getDesc());
        holder.price.setText(tData.get(position).getPrice());
        // holder.img.setBackground(Drawable.createFromPath("drawable/resbtn"));
        Drawable drawable = tContext.getResources().getDrawable(
                tContext.getResources().getIdentifier(tData.get(position).getImg(),"drawable", tContext.getPackageName()));
        holder.img.setBackground(drawable);

        t1=new TextToSpeech(inflater.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        holder.tts=tData.get(position).getTts();

        holder.btts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(talk%2==0){
                    t1.speak(tData.get(position).getTts(), TextToSpeech.QUEUE_FLUSH, null,null);
                }
                else{
                    t1.stop();
                }
                talk+=1;
            }
        });

    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }

    }
    @Override
    public int getItemCount() {
        return tData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name ;
        TextView desc ;
        ImageView img ;
        TextView price;
        ImageButton btts;
        String tts;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.tran_ame);
            this.price = itemView.findViewById(R.id.tran_price);
            this.desc = itemView.findViewById(R.id.tran_desc);
            this.img = itemView.findViewById(R.id.Tran_img);
            this.btts=itemView.findViewById(R.id.tbtts);
            this.tts="";
        }
    }
}
