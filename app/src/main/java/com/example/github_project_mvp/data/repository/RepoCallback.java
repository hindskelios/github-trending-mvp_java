package com.example.github_project_mvp.data.repository;

public interface RepoCallback <T>{
    void onSuccess(T data);
    void onError(Throwable t);

}
