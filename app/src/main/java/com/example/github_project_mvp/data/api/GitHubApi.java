package com.example.github_project_mvp.data.api;

import com.example.github_project_mvp.data.model.SearchResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubApi {
    @GET("search/repositories")
    Call<SearchResponseDto> searchRepositories(
            @Query("q") String query,
            @Query("sort") String sort,
            @Query("order") String order,
            @Query("page") int page
    );
}
