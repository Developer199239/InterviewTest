package com.example.interviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    private Context context;
    private ArrayList<Repository> repos;

    public RepoAdapter(Context context) {
        this.context = context;
        this.repos = new ArrayList<>();
    }

    public void setRepos(ArrayList<Repository> repos) {
        this.repos = repos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_repo, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        Repository repo = repos.get(position);

        holder.nameTextView.setText(repo.getName());
        holder.descriptionTextView.setText(repo.getDescription());
        holder.starsTextView.setText(String.valueOf(repo.getStars()));
        holder.authorTextView.setText(repo.getAuthor());
        Picasso.get().load(repo.getAvatarUrl()).into(holder.avatarImageView);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView starsTextView;
        public TextView authorTextView;
        public ImageView avatarImageView;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            starsTextView = itemView.findViewById(R.id.starsTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
        }
    }
}