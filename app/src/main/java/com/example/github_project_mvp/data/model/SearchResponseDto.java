package com.example.github_project_mvp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponseDto {
    @SerializedName("items")
    public List<RepoDto> items;

}
