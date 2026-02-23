package com.example.github_project_mvp.data.model;

import com.google.gson.annotations.SerializedName;

public class RepoDto {
    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("owner")
    public OwnerDto owner;
    @SerializedName("stargazers_count")
    public int stars;
}
