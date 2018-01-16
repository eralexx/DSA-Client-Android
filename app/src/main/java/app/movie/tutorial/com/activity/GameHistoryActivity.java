package app.movie.tutorial.com.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.movie.tutorial.com.R;

public class GameHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);
    }
    /* Call<List<GitHubUserResponse>> call2 = gitHubApiService.getUserFollowers(query);
        call2.enqueue(new Callback<List<GitHubUserResponse>>() {
            @Override
            public void onResponse(Call<List<GitHubUserResponse>> call, Response<List<GitHubUserResponse>> response) {
                try {
                    ProgressBar progBar = (ProgressBar) findViewById(R.id.progressBar1);
                    progBar.setVisibility(View.GONE);

                    if (response.body().size() > 0) {
                        recyclerView.setAdapter(new GitHubFollowersAdapter(response.body(), R.layout.list_item_movie, getApplicationContext()));
                    }
                }
                catch(Exception ex){
                    showAlertDialog("Warning","Search error.");
                }
            }
            @Override
            public void onFailure(Call<List<GitHubUserResponse>> call, Throwable throwable) {
                showAlertDialog("Warning","Conectivity error.");
                finish();
            }

        });*/
}
