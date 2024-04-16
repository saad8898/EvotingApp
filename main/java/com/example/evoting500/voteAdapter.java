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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class voteAdapter extends RecyclerView.Adapter<VoteViewHolder> {
    private Context context;
    private List<voteData> dataList;
    private DatabaseReference databaseReference;
    public voteAdapter(Context context, List<voteData> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("PartyVotes");
    }
    @NonNull
    @Override
    public VoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_votes, parent, false);
        return new VoteViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull VoteViewHolder holder, int position) {
        voteData data = dataList.get(position);
        String partyName = data.getName();
        int votesInt = data.getVotes();

        holder.recName.setText(partyName);
        holder.recVotes.setText(String.valueOf(votesInt));
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
class VoteViewHolder extends RecyclerView.ViewHolder{
    TextView recVotes, recName;
    CardView recCard;
    public VoteViewHolder(@NonNull View itemView) {
        super(itemView);
        recCard = itemView.findViewById(R.id.recCard);
        recVotes = itemView.findViewById(R.id.recVotes);
        recName = itemView.findViewById(R.id.recName);
    }
}
