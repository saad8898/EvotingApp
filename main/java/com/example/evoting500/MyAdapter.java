package com.example.evoting500;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<UserData> userDataList;
    private DatabaseReference databaseReference;

    public MyAdapter(Context context, List<UserData> userDataList) {
        this.context = context;
        this.userDataList = userDataList;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("UsersDB");
    }

    public void updateList(List<UserData> filteredList) {
        userDataList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recName.setText(userDataList.get(position).getFullname());
        holder.recCnic.setText(userDataList.get(position).getCnic());
        holder.recEmail.setText(userDataList.get(position).getEmail());
        holder.recPhone.setText(userDataList.get(position).getPhone());
        holder.recStatus.setText(userDataList.get(position).getRequestStatus());
        holder.hasVoted.setText(userDataList.get(position).getHasVoted());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("name", userDataList.get(holder.getAdapterPosition()).getFullname());
                intent.putExtra("cnic", userDataList.get(holder.getAdapterPosition()).getCnic());
                intent.putExtra("email", userDataList.get(holder.getAdapterPosition()).getEmail());
                intent.putExtra("phone", userDataList.get(holder.getAdapterPosition()).getPhone());
                intent.putExtra("status", userDataList.get(holder.getAdapterPosition()).getRequestStatus());
                intent.putExtra("hasVoted", userDataList.get(holder.getAdapterPosition()).getHasVoted());
                intent.putExtra("key", userDataList.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView recName, recCnic, recEmail, recPhone, recStatus, hasVoted;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recName = itemView.findViewById(R.id.recName);
        recCnic = itemView.findViewById(R.id.recCnic);
        recEmail = itemView.findViewById(R.id.recEmail);
        recPhone = itemView.findViewById(R.id.recPhone);
        recStatus = itemView.findViewById(R.id.recStatus);
        hasVoted = itemView.findViewById(R.id.hasVoted);
        recCard = itemView.findViewById(R.id.recCard);
    }
}
