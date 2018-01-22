package app.movie.tutorial.com.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.model.CustomLayout;
import app.movie.tutorial.com.model.User;
import app.movie.tutorial.com.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class StatsActivity extends AppCompatActivity {
    String userEmail = "";
    private final String BACKGROUND_URL="http://ak0.picdn.net/shutterstock/videos/4946630/thumb/7.jpg";
    private static Retrofit retrofit = null;
    public  final String BASE_URL = "http://2.152.165.114:80/rest/";
    String gamesPlayedString = "";
    String userName = "";
    List<String> gamesPlayedList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        userEmail = getIntent().getStringExtra("userEmail");
        SetBackgroundImage();
        ExecuteAPICall();
    }
   
    private void getList() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ApiService ApiService = retrofit.create(ApiService.class);
        Call<String> call1 = ApiService.GetUserGames(userEmail);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    gamesPlayedString = response.body();
                    gamesPlayedList = Arrays.asList(gamesPlayedString.split(";"));
                    int gamesPlayed= gamesPlayedList.size();
                    Collections.reverse(gamesPlayedList);
                    int gamesWon= 0;
                    for(String game: gamesPlayedList){
                         if (game.contains(userEmail)){

                             gamesWon++;                         
                         }
                        printWonGame(game);
                         //else printLostGame(game);
                    }              
                    printNumbers(gamesPlayed,gamesWon);
                } catch (Exception ex) {
                    Log.d("1", ex.getMessage());
                    showAlertDialog("Warning", "Login error.");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.d("1", throwable.getMessage());
                Log.d("2", call.toString());
                showAlertDialog("Warning", "Conectivity error.");

            }
        });
    }
    private void SetBackgroundImage() {
        CustomLayout mCustomLayout = (CustomLayout)findViewById(R.id.statslayout);
        Picasso.with(this).load(BACKGROUND_URL).into(mCustomLayout);
    }

    private void printNumbers(int gamesPlayed, int gamesWon) {
        TextView tv =  (TextView) findViewById(R.id.gamesplayed);
        tv.setText(tv.getText() +String.valueOf(gamesPlayed));
        tv =  (TextView) findViewById(R.id.gameswon);
        tv.setText(tv.getText() +String.valueOf(gamesWon));
        tv = (TextView) findViewById(R.id.winrate);

        DecimalFormat numberFormat = new DecimalFormat("#.00");
        tv.setText(tv.getText() + String.valueOf(numberFormat.format(((double)gamesWon/gamesPlayed)*100))+ "%");
    }

    private void printWonGame(String game) {
        TextView tv =  (TextView) findViewById(R.id.recentlyplayed);
        tv.setText(tv.getText()+"\n" +game);
    }
    private void printLostGame(String game) {
        TextView tv =  (TextView) findViewById(R.id.recentlyplayed);
        tv.setText(tv.getText()+"\n" +game);
    }

    private void ExecuteAPICall() {
         if (retrofit == null) {
             retrofit = new Retrofit.Builder()
                     .baseUrl(BASE_URL)
                     .addConverterFactory(ScalarsConverterFactory.create())
                     .addConverterFactory(GsonConverterFactory.create())
                     .build();
         }
        ApiService ApiService = retrofit.create(ApiService.class);
        Call<User> call2 = ApiService.GetUserData(userEmail);
        call2.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    userName = response.body().getUserName();
                    getList();

                } catch (Exception ex) {
                    Log.d("1", ex.getMessage());
                    showAlertDialog("Warning", "Login error.");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Log.d("1", throwable.getMessage());
                Log.d("2", call.toString());
                showAlertDialog("Warning", "Conectivity error.");

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
