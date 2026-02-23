package com.example.github_project_mvp;

import android.app.Application;

import com.example.github_project_mvp.di.AppComponent;
import com.example.github_project_mvp.di.DaggerAppComponent;

public class GithubApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}