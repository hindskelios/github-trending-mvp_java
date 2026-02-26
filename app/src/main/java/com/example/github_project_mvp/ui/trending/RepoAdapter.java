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

public class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    private final List<RepoDto> items = new ArrayList<>();
    private boolean isLoadingAdded = false;

    public void setItems(List<RepoDto> newItems) {
        items.clear();
        if (newItems != null) items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void addItems(List<RepoDto> newItems) {
        if (newItems == null) return;
        int startPosition = items.size();
        items.addAll(newItems);
        notifyItemRangeInserted(startPosition, newItems.size());
    }

    public void addLoadingFooter() {
        if (isLoadingAdded) return;
        isLoadingAdded = true;
        // Adding a null item to represent the loading footer
        items.add(null);
        notifyItemInserted(items.size() - 1);
    }

    public void removeLoadingFooter() {
        if (!isLoadingAdded) return;
        isLoadingAdded = false;
        int position = items.size() - 1;
        if (position >= 0 && items.get(position) == null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_repo, parent, false);
            return new RepoVH(view);
        }
        // (viewType == VIEW_TYPE_LOADING)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_loading, parent, false);
        return new LoadingVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RepoVH) {
            RepoDto repo = items.get(position);
            RepoVH repoVH = (RepoVH) holder;

            repoVH.repoName.setText(repo.name != null ? repo.name : "");
            repoVH.description.setText(repo.description != null ? repo.description : "");
            String owner = (repo.owner != null && repo.owner.login != null) ? repo.owner.login : "";
            repoVH.ownerName.setText(repoVH.itemView.getContext().getString(R.string.by_owner, owner));
            repoVH.stars.setText(repoVH.itemView.getContext().getString(R.string.stars_format, repo.stars));
            String avatarUrl = (repo.owner != null) ? repo.owner.avatarUrl : null;
            Glide.with(repoVH.avatar.getContext()).load(avatarUrl).circleCrop().into(repoVH.avatar);
        }
        // No binding needed for LoadingVH
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder for Repo item
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

    // ViewHolder for Loading item
    static class LoadingVH extends RecyclerView.ViewHolder {
        LoadingVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}