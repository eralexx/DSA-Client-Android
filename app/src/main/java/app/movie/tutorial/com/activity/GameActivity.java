package app.movie.tutorial.com.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.model.Cell;
import app.movie.tutorial.com.model.CellAdapter;
import app.movie.tutorial.com.model.Game;
import app.movie.tutorial.com.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameActivity extends AppCompatActivity {
    String userEmail= "";
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://10.0.2.2:8081/rest/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
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

        Call<Game> call = ApiService.GetTestGame();
        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                try {
                    Game retrievedGame = response.body();
                    DrawBoard(retrievedGame.getBoard().getAndroidCells());
                    DrawFinish(retrievedGame.getBoard().getWinningCell());
                    DrawUsers(retrievedGame.getBoard().getPositions());
                    TextViewInitialMessage();

                    Log.i("response", String.valueOf(response));
                    Log.i("responsebody:    ",String.valueOf(response.body()));

                } catch (Exception ex) {
                    Log.d("1",ex.getMessage());
                    showAlertDialog("Warning", "Login error.");
                }
            }
            @Override
            public void onFailure(Call<Game> call, Throwable throwable) {
                Log.d("1",throwable.getMessage());
                Log.d("2", call.toString());
                showAlertDialog("Warning", "Conectivity error.");
                finish();
            }
        });
    }

    private void TextViewInitialMessage() {


    }

    private void DrawUsers(List<Cell> positions) {
        String VIEW_NAME ="";
        TextView currentTV;
        boolean p1 =false;
        for(Cell cell: positions) {

            VIEW_NAME = "tv" + String.valueOf(cell.getPosX()+1) + String.valueOf(cell.getPosY()+1);
            currentTV = (TextView) findViewById(getResources().getIdentifier(VIEW_NAME, "id", getPackageName()));
            if (p1 == false) {
                currentTV.setBackgroundResource(R.drawable.p1);
                p1=true;
            }
            else{
                currentTV.setBackgroundResource(R.drawable.p2);
            }
        }
    }

    private void DrawFinish(Cell winningCell) {

        String VIEW_NAME="tv"+String.valueOf(winningCell.getPosX()+1)+String.valueOf(winningCell.getPosY()+1);
        TextView currentTV = (TextView)findViewById(getResources().getIdentifier(VIEW_NAME, "id", getPackageName()));
        currentTV.setBackgroundResource(R.drawable.finsih);

    }

    private void DrawBoard(CellAdapter[] androidCells) {
        TextView currentTV;
        String VIEW_NAME="";

        for (int i=0; i<androidCells.length; i++){
            for (int j=0; j< androidCells[i].getCells().length; j++){

                Cell currentCell = androidCells[i].getCells()[j];

                if (currentCell.getMoves().contains('E')){
                    VIEW_NAME="v" + String.valueOf(i+1) + String.valueOf(j+1);
                    currentTV = (TextView)findViewById(getResources().getIdentifier(VIEW_NAME, "id", getPackageName()));
                    if (currentTV != null) {
                        currentTV.setBackgroundResource(R.color.colorWhite);
                    }
                }

                if (currentCell.getMoves().contains('S')){
                    VIEW_NAME="h" + String.valueOf(i+1) + String.valueOf(j+1);
                    currentTV = (TextView)findViewById(getResources().getIdentifier(VIEW_NAME, "id", getPackageName()));
                    if (currentTV != null) {
                        currentTV.setBackgroundResource(R.color.colorWhite);
                    }
                }
            }
        }
    }

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
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
