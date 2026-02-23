package com.example.github_project_mvp.data.repository;

import com.example.github_project_mvp.data.model.RepoDto;

import java.util.List;

public interface TrendingRepo {
    void getTrendingRepos(int page, RepoCallback<List<RepoDto>> callback);

}
