package com.example.github_project_mvp.di;

import com.example.github_project_mvp.ui.trending.TrendingActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkDi.class, RepoDi.class, PresenterDi.class})
public interface AppComponent {
    void inject(TrendingActivity trendingActivity);

}
