package com.example.github_project_mvp.ui.trending;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github_project_mvp.GithubApp;
import com.example.github_project_mvp.R;
import com.example.github_project_mvp.data.model.RepoDto;
import com.example.github_project_mvp.presenter.TrendingContract;

import java.util.List;

import javax.inject.Inject;

public class TrendingActivity extends AppCompatActivity implements TrendingContract.View {

    @Inject
    TrendingContract.Presenter presenter;

    private ProgressBar progress;
    private RecyclerView recycler;
    private TextView errorText;
    private RepoAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        progress = findViewById(R.id.progress);
        recycler = findViewById(R.id.recycler);
        errorText = findViewById(R.id.errorText);

        layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        adapter = new RepoAdapter();
        recycler.setAdapter(adapter);

        int space = (int) (12 * getResources().getDisplayMetrics().density);
        recycler.addItemDecoration(new VerticalSpaceItemDecoration(space));

        addScrollListener();

        ((GithubApp) getApplication())
                .getAppComponent()
                .inject(this);

        presenter.attach(this);
        presenter.loadFirstPage();
    }

    private void addScrollListener() {
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    presenter.loadNextPage();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showRepos(List<RepoDto> repos) {
        recycler.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.GONE);
        adapter.setItems(repos);
    }

    @Override
    public void addRepos(List<RepoDto> repos) {
        adapter.addItems(repos);
    }

    @Override
    public void showError(String message) {
        recycler.setVisibility(View.GONE);
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(
                message != null ? message : getString(R.string.error_generic)
        );
    }

    @Override
    public void showPageLoadError(String message) {
        Toast.makeText(this, message != null ? message : getString(R.string.error_generic), Toast.LENGTH_SHORT).show();
    }
}
