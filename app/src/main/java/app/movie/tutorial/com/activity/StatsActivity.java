package app.movie.tutorial.com.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatsActivity extends AppCompatActivity {
    String userEmail= "";
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://10.0.2.2:8081/rest/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        userEmail = getIntent().getStringExtra("userEmail");

        ExecuteAPICall();
    }
    private void ExecuteAPICall() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiService ApiService = retrofit.create(ApiService.class);

        Call<String> call1 = ApiService.GetUserGames(userEmail);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    String retrievedStringGames = response.body();
                    Log.i("response", String.valueOf(response));
                    Log.i("responsebody:    ",String.valueOf(response.body()));

                } catch (Exception ex) {
                    Log.d("1",ex.getMessage());
                    showAlertDialog("Warning", "Login error.");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.d("1",throwable.getMessage());
                Log.d("2", call.toString());
                showAlertDialog("Warning", "Conectivity error.");
                finish();
            }
        });
        Call<String> call2 = ApiService.GetUserWins(userEmail);
        call2.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    String retrievedStringWins = response.body();
                    Log.i("response", String.valueOf(response));
                    Log.i("responsebody:    ",String.valueOf(response.body()));

                } catch (Exception ex) {
                    Log.d("1",ex.getMessage());
                    showAlertDialog("Warning", "Login error.");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.d("1",throwable.getMessage());
                Log.d("2", call.toString());
                showAlertDialog("Warning", "Conectivity error.");
                finish();
            }
        });
    }
    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(StatsActivity.this);
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
