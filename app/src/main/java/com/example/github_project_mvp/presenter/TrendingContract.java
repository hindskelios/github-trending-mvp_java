package com.example.github_project_mvp.presenter;

import com.example.github_project_mvp.data.model.RepoDto;
import java.util.List;

public interface TrendingContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showRepos(List<RepoDto> repos);
        void addRepos(List<RepoDto> repos); // Pour les pages suivantes
        void showError(String message);
        void showPageLoadError(String message);
    }

    interface Presenter {
        void attach(View view);
        void detach();
        void loadFirstPage();
        void loadNextPage();
    }
}
