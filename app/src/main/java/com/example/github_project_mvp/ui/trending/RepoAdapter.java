package com.example.github_project_mvp.ui.trending;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.github_project_mvp.R;
import com.example.github_project_mvp.data.model.RepoDto;

import java.util.ArrayList;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoVH> {

    private final List<RepoDto> items = new ArrayList<>();

    public void setItems(List<RepoDto> newItems) {
        items.clear();
        if (newItems != null) items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RepoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repo, parent, false);
        return new RepoVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoVH holder, int position) {
        RepoDto repo = items.get(position);

        holder.repoName.setText(repo.name != null ? repo.name : "");

        holder.description.setText(
                repo.description != null ? repo.description : ""
        );

        String owner = (repo.owner != null && repo.owner.login != null)
                ? repo.owner.login
                : "";

        holder.ownerName.setText(
                holder.itemView.getContext()
                        .getString(R.string.by_owner, owner)
        );

        holder.stars.setText(
                holder.itemView.getContext()
                        .getString(R.string.stars_format, repo.stars)
        );

        String avatarUrl = (repo.owner != null) ? repo.owner.avatarUrl : null;

        Glide.with(holder.avatar.getContext())
                .load(avatarUrl)
                .circleCrop()
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class RepoVH extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView repoName;
        TextView ownerName;
        TextView description;
        TextView stars;

        RepoVH(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            repoName = itemView.findViewById(R.id.repoName);
            ownerName = itemView.findViewById(R.id.ownerName);
            description = itemView.findViewById(R.id.description);
            stars = itemView.findViewById(R.id.stars);
        }
    }
}