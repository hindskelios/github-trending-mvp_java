package com.example.github_project_mvp.di;

import com.example.github_project_mvp.presenter.TrendingContract;
import com.example.github_project_mvp.presenter.TrendingPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PresenterDi {
    @Binds
    abstract TrendingContract.Presenter bindTrendingPresenter(TrendingPresenter impl);
}
