package com.example.interviewtest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private String name;

    private String avatarUrl;

    private String username;
    private String description;
    private int stars;
    private String htmlUrl;

    public Repository(String name, String avatarUrl, String username, String description, int stars, String htmlUrl) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.username = username;
        this.description = description;
        this.stars = stars;
        this.htmlUrl = htmlUrl;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getAuthor() {
        return username;
    }
    public String getDescription() {
        return description;
    }

    public int getStars() {
        return stars;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public static List<Repository> fromJsonResponse(JSONObject response) {
        List<Repository> repositories = new ArrayList<>();
        try {
            JSONArray itemsArray = response.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject item = itemsArray.getJSONObject(i);
                String name = item.getString("name");
                String avatarUrl = item.getString("avatar_url");
                String username = item.getString("username");
                String description = item.optString("description", "");
                int stars = item.getInt("stargazers_count");
                String htmlUrl = item.getString("html_url");
                Repository repository = new Repository(name, avatarUrl, username, description, stars, htmlUrl);
                repositories.add(repository);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return repositories;
    }
}
