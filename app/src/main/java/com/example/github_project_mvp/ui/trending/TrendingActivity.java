package com.example.github_project_mvp.ui.trending;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.github_project_mvp.GithubApp;
import com.example.github_project_mvp.R;
import com.example.github_project_mvp.presenter.TrendingContract;

import javax.inject.Inject;

public class TrendingActivity extends AppCompatActivity implements TrendingContract.View {

    @Inject
    TrendingContract.Presenter presenter;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        // IMPORTANT: l'id doit exister dans activity_trending.xml
        textView = findViewById(R.id.textView);

        ((GithubApp) getApplication()).getAppComponent().inject(this);

        presenter.attach(this);
        presenter.loadFirstPage();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        textView.setText("Loading...");
    }

    @Override
    public void hideLoading() {
        textView.setText("Loaded");
    }

    @Override
    public void showReposCount(int count) {
        textView.setText("Trending repositories count: " + count);
        Toast.makeText(this, "Received: " + count, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        String msg = (message == null || message.trim().isEmpty()) ? "Unknown error" : message;
        textView.setText(msg); // affiche l'erreur exacte sur l'écran
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}