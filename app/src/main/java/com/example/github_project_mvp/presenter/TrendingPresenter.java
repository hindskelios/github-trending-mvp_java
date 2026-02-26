package com.example.github_project_mvp.presenter;

import com.example.github_project_mvp.data.model.RepoDto;
import com.example.github_project_mvp.data.repository.RepoCallback;
import com.example.github_project_mvp.data.repository.TrendingRepo;

import java.util.List;

import javax.inject.Inject;

public class TrendingPresenter implements TrendingContract.Presenter {

    private TrendingContract.View view;
    private final TrendingRepo trendingRepo;

    private boolean isLoading = false;
    private int currentPage = 1;

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
        this.view = null; // Prevent memory leaks
    }

    @Override
    public void loadFirstPage() {
        if (isLoading) return;
        currentPage = 1;
        loadRepos(currentPage);
    }

    @Override
    public void loadNextPage() {
        if (isLoading) return;
        currentPage++;
        loadRepos(currentPage);
    }

    private void loadRepos(final int page) {
        if (view == null) return;

        isLoading = true;
        if (page == 1) {
            view.showLoading();
        } else {
            view.showPaginationLoading();
        }

        trendingRepo.getTrendingRepos(page, new RepoCallback<List<RepoDto>>() {
            @Override
            public void onSuccess(List<RepoDto> repos) {
                if (view == null) return;
                isLoading = false;

                if (page == 1) {
                    view.hideLoading();
                    view.showRepos(repos);
                } else {
                    view.hidePaginationLoading();
                    view.addRepos(repos);
                }
            }

            @Override
            public void onError(Throwable t) {
                if (view == null) return;
                isLoading = false;

                if (page == 1) {
                    view.hideLoading();
                    view.showError(t.getMessage());
                } else {
                    // Revert page increment on failure for next pages
                    currentPage--;
                    view.hidePaginationLoading();
                    view.showPageLoadError(t.getMessage());
                }
            }
        });
    }
}
