package com.example.github_project_mvp.di;


import com.example.github_project_mvp.data.repository.TrendingRepo;
import com.example.github_project_mvp.data.repository.TrendingRepoImpl;

import dagger.Binds;
import dagger.Module;
import javax.inject.Singleton;

@Module
public abstract class RepoDi {
    @Binds
    @Singleton
    abstract TrendingRepo bindTrendingRepo(TrendingRepoImpl impl);
}
