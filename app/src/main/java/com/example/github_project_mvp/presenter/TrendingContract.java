package com.example.github_project_mvp.presenter;

public interface TrendingContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showReposCount(int count);
        void showError(String message);
    }

    interface Presenter {
        void attach(View view);
        void detach();
        void loadFirstPage();
    }
}
