package com.example.github_project_mvp.data.model;

import com.google.gson.annotations.SerializedName;

public class OwnerDto {
    @SerializedName("login")
    public String login;
    @SerializedName("avatar_url")
    public String avatarUrl;
}
