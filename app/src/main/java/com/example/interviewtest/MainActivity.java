package com.example.interviewtest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Repository> repos = new ArrayList<>();
    private RecyclerView reposRecyclerView;
    private RepoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reposRecyclerView = findViewById(R.id.reposRecyclerView);
        adapter = new RepoAdapter(this);
        reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reposRecyclerView.setAdapter(adapter);
        fetchRepositories();
    }
    private void fetchRepositories() {
        String url = "https://api.github.com/search/repositories?q=created:>=" + getThirtyDaysAgoDate() + "&sort=stars&order=desc";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("est","test");
                        try {
                            JSONArray items = response.getJSONArray("items");
                            List<Repository> repositories = new ArrayList<>();
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject item = items.getJSONObject(i);
                                String name = item.getString("name");
                                String description = item.getString("description");
                                int stars = item.getInt("stargazers_count");
                                String username = item.getJSONObject("owner").getString("login");
                                String avatarUrl = item.getJSONObject("owner").getString("avatar_url");
                                String htmlUrl = item.getString("html_url");
                                Repository repo = new Repository( name,  avatarUrl,  username,  description,  stars,  htmlUrl);
                                repos.add(repo);
                            }
                            adapter.setRepos(repos);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/vnd.github.v3+json");
                headers.put("Authorization", "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");
                return headers;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private String getThirtyDaysAgoDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -30);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}