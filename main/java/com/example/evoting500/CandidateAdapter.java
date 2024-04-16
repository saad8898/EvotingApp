package com.example.evoting500;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
public class CandidateAdapter extends RecyclerView.Adapter<CandidateViewHolder> {
    private Context context;
    private List<CandidatesData> dataList;
    public CandidateAdapter(Context context, List<CandidatesData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_candidates, parent, false);
        return new CandidateViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CandidateViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getPartyLogo()).into(holder.recImage);
        holder.recName.setText(dataList.get(position).getPartyName());
        holder.recHead.setText(dataList.get(position).getPartyHead());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CandidateDetails.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getPartyLogo());
                intent.putExtra("Head", dataList.get(holder.getAdapterPosition()).getPartyHead());
                intent.putExtra("Name", dataList.get(holder.getAdapterPosition()).getPartyName());
                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
class CandidateViewHolder extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recHead, recName;
    CardView recCard;
    public CandidateViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recHead = itemView.findViewById(R.id.recHead);
        recName = itemView.findViewById(R.id.recName);
    }
}
