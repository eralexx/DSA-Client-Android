package app.movie.tutorial.com.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.model.GitHubUserResponse;
import app.movie.tutorial.com.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity{

    public static final String BASE_URL = "https://api.github.com/";
    private static Retrofit retrofit = null;

    private final static String API_KEY = "1b806865a8feb915157882d555f4dcb1";
    String query= "ad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        query = getIntent().getStringExtra("Username");
        //recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        connectAndGetApiData();
    }
    // This method create an instance of Retrofit
    // set the base url
    public void connectAndGetApiData(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiService ApiService = retrofit.create(ApiService.class);

        Call<GitHubUserResponse> call = ApiService.getUser(query);
        call.enqueue(new Callback<GitHubUserResponse>() {
            @Override
            public void onResponse(Call<GitHubUserResponse> call, Response<GitHubUserResponse> response) {
                try {
                    String avatar_url = response.body().getAvatar_url();
                    String login = response.body().getLogin();
                    int repos = response.body().getPublic_repos();
                    int following = response.body().getFollowing();
                    ProgressBar progBar = (ProgressBar) findViewById(R.id.progressBar1);
                    progBar.setVisibility(View.GONE);
                    if (login.isEmpty()) {
                        showAlertDialog("Warning","User not found");
                    } else {
                        ImageView avatar = (ImageView) findViewById(R.id.user_image);
                        Picasso.with(getApplicationContext()).load(avatar_url).into(avatar);
                        TextView tv1 = (TextView) findViewById(R.id.repositories);
                        tv1.setText("Repositories: " + repos);
                        TextView tv2 = (TextView) findViewById(R.id.following);
                        tv2.setText("Following: " + following);
                    }
                }
                catch(Exception ex){
                    showAlertDialog("Warning","Search error.");

                }
            }
            @Override
            public void onFailure(Call<GitHubUserResponse> call, Throwable throwable) {
                showAlertDialog("Warning","Conectivity error.");
                finish();
            }
        });

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
    public void GameHistory(View view) {

        Intent intent = new Intent(getBaseContext(), GameHistoryActivity.class);
        intent.putExtra("Username", query);
        startActivity(intent);
    }
    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
