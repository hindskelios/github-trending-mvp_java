package com.example.github_project_mvp.data.repository;

import com.example.github_project_mvp.data.api.GitHubApi;
import com.example.github_project_mvp.data.model.RepoDto;
import com.example.github_project_mvp.data.model.SearchResponseDto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingRepoImpl implements TrendingRepo {

    private final GitHubApi gitHubApi;

    @Inject
    public TrendingRepoImpl(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public void getTrendingRepos(int page, RepoCallback<List<RepoDto>> callback) {
        if (callback == null) return;

        String since = getDateMinusDays(30); // YYYY-MM-DD
        String query = "created:>" + since;

        Call<SearchResponseDto> call = gitHubApi.searchRepositories(query, "stars", "desc", page);

        call.enqueue(new Callback<SearchResponseDto>() {
            @Override
            public void onResponse(Call<SearchResponseDto> call, Response<SearchResponseDto> response) {
                if (!response.isSuccessful()) {
                    callback.onError(new RuntimeException(buildHttpErrorMessage(response)));
                    return;
                }

                SearchResponseDto body = response.body();
                if (body == null || body.items == null) {
                    callback.onSuccess(Collections.emptyList());
                    return;
                }

                callback.onSuccess(body.items);
            }

            @Override
            public void onFailure(Call<SearchResponseDto> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    private String getDateMinusDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -days);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return sdf.format(cal.getTime());
    }

    private String buildHttpErrorMessage(Response<?> response) {
        String errorBody = null;
        try {
            if (response.errorBody() != null) {
                errorBody = response.errorBody().string();
            }
        } catch (Exception ignored) {
        }

        String msg = "HTTP " + response.code() + " " + response.message();
        if (errorBody != null && !errorBody.trim().isEmpty()) {
            msg += " | " + errorBody;
        }
        return msg;
    }
}