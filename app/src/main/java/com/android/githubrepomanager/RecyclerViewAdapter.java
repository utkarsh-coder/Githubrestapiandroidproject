package com.android.githubrepomanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Repo> repoArrayList;

    public RecyclerViewAdapter(Context context, ArrayList<Repo> repoArrayList) {
        this.context = context;
        this.repoArrayList = repoArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.repoNameTextView.setText(repoArrayList.get(holder.getAdapterPosition()).getRepoName());
        holder.descriptionTextView.setText(repoArrayList.get(holder.getAdapterPosition()).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(repoArrayList.get(holder.getAdapterPosition()).getUrl()));
                context.startActivity(browserIntent);
            }
        });

        holder.shareImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Repo Name: "+repoArrayList.get(holder.getAdapterPosition()).getRepoName()+"\nRepo URL: "+repoArrayList.get(holder.getAdapterPosition()).getUrl());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                context.startActivity(shareIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return repoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView repoNameTextView;
        TextView descriptionTextView;
        ImageView shareImageBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            repoNameTextView = itemView.findViewById(R.id.repo_name);
            descriptionTextView = itemView.findViewById(R.id.description);
            shareImageBtn = itemView.findViewById(R.id.share_icon);
        }
    }
}
