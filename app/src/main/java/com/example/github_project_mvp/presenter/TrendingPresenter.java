package com.example.github_project_mvp.presenter;

import com.example.github_project_mvp.data.model.RepoDto;
import com.example.github_project_mvp.data.repository.RepoCallback;
import com.example.github_project_mvp.data.repository.TrendingRepo;

import java.util.List;

import javax.inject.Inject;

public class TrendingPresenter implements TrendingContract.Presenter {

    private final TrendingRepo trendingRepo;
    private TrendingContract.View view;

    @Inject
    public TrendingPresenter(TrendingRepo trendingRepo) {
        this.trendingRepo = trendingRepo;
    }

    @Override
    public void attach(TrendingContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public void loadFirstPage() {
        if (view == null) return;

        trendingRepo.getTrendingRepos(1, new RepoCallback<List<RepoDto>>() {
            @Override
            public void onSuccess(List<RepoDto> data) {
                if (view == null) return;
                view.hideLoading();
                view.showRepos(data);
            }

            @Override
            public void onError(Throwable t) {
                if (view == null) return;
                view.hideLoading();
                view.showError(t == null ? "Unknown error" : t.getMessage());
            }
        });
    }
}