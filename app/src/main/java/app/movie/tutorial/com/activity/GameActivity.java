package app.movie.tutorial.com.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.model.Cell;
import app.movie.tutorial.com.model.CellAdapter;
import app.movie.tutorial.com.model.Game;
import app.movie.tutorial.com.model.OnSwipeTouchListener;
import app.movie.tutorial.com.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameActivity extends AppCompatActivity {
    String userEmail= "";
    private static Retrofit retrofit = null;
     boolean isMyTurn=false;
     boolean gameFound = false;
    public static final String BASE_URL = "http://2.152.165.114:80/rest/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        userEmail = getIntent().getStringExtra("userEmail");
        TextViewInitialMessage();
        ExecuteInitialAPICall();
    }

    private void ExecuteInitialAPICall() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiService ApiService = retrofit.create(ApiService.class);

        Call<Game> Call = ApiService.GetTestGame();
        Call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                try {
                    isMyTurn=false;
                    gameFound = true;
                    Game retrievedGame = response.body();
                    DrawBoard(retrievedGame.getBoard().getAndroidCells());
                    DrawFinish(retrievedGame.getBoard().getWinningCell());
                    DrawUsers(retrievedGame.getBoard().getPositions());
                    boolean gameOver = ManageWin(retrievedGame.getWinner());
                    if (gameOver==false) {
                        ManageTurn(retrievedGame.getPlayerTurn().getEmail());
                    }
                    else{
                        Thread.sleep(3000);
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("userEmail", userEmail);
                        startActivity(intent);
                    }

                    SetListener();
                    new Thread(new Runnable() {
                        public void run() {
                            new Timer().scheduleAtFixedRate(new TimerTask(){
                                @Override
                                public void run(){
                                    if (isMyTurn!=true) {
                                        RefreshGameAPICall();
                                    }
                                }
                            },0,2000);
                        }
                    }).start();

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
    private void APIMoveCall(char dir) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiService ApiService = retrofit.create(ApiService.class);

        Call<Game> Call = ApiService.Move(userEmail, dir);
        Call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                try {
                    isMyTurn=false;
                    Game retrievedGame = response.body();
                    DrawUsers(retrievedGame.getBoard().getPositions());
                    boolean gameOver = ManageWin(retrievedGame.getWinner());
                    if (gameOver==false) {
                        ManageTurn(retrievedGame.getPlayerTurn().getEmail());
                    }
                    else{
                        Thread.sleep(3000);
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("userEmail", userEmail);
                        startActivity(intent);
                    }
                    TextViewInitialMessage();
                    SetListener();


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
    private void RefreshGameAPICall() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiService ApiService = retrofit.create(ApiService.class);

        Call<Game> Call = ApiService.AttemptToGetGame(userEmail);
        Call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                try {
                    isMyTurn=false;
                    Game retrievedGame = response.body();
                    DrawUsers(retrievedGame.getBoard().getPositions());
                    boolean gameOver = ManageWin(retrievedGame.getWinner());
                    if (gameOver==false) {
                        ManageTurn(retrievedGame.getPlayerTurn().getEmail());
                    }
                    else{
                        Thread.sleep(3000);
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("userEmail", userEmail);
                        startActivity(intent);
                    }
                    TextViewInitialMessage();
                    SetListener();
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

    private boolean ManageWin(String winner) {
        TextView infoTextView = (TextView) findViewById(R.id.textView);
        if (winner !=null && !winner.isEmpty()){
            if (winner.equals(userEmail)){
                infoTextView.setText("YOU WON!!!");
                infoTextView.setTextColor(getResources().getColor(R.color.green));
                infoTextView.setTextSize(35);
            }
            else{
                infoTextView.setText("You lost...");
                infoTextView.setTextColor(getResources().getColor(R.color.orange));
                infoTextView.setTextSize(30);
            }
            return true;
        }
        else return false;
    }

    private void SetListener() {
        View view = (LinearLayout) findViewById(R.id.gridLayout);
        view.setClickable(true);


        view.setOnTouchListener(new OnSwipeTouchListener(GameActivity.this) {
            @Override
            public void onSwipeRight() {
                if (isMyTurn==true) {
                    APIMoveCall('E');
                    TextView infoTextView = (TextView) findViewById(R.id.textView);
                    infoTextView.setText("You moved Right. It's your opponent's turn");
                    infoTextView.setTextSize(20);
                }
            }
            @Override
            public void onSwipeLeft() {
                if (isMyTurn==true) {
                    APIMoveCall('W');
                    TextView infoTextView = (TextView) findViewById(R.id.textView);
                    infoTextView.setText("You moved Left. It's your opponent's turn");
                    infoTextView.setTextSize(20);
                }
            }
            @Override
            public void onSwipeBottom() {
                if (isMyTurn==true) {
                    APIMoveCall('S');
                    TextView infoTextView = (TextView) findViewById(R.id.textView);
                    infoTextView.setText("You moved Down. It's your opponent's turn");
                    infoTextView.setTextSize(20);
                }
            }
            @Override
            public void onSwipeTop() {
                if (isMyTurn==true) {
                    APIMoveCall('N');
                    TextView infoTextView = (TextView) findViewById(R.id.textView);
                    infoTextView.setText("You moved Up. It's your opponent's turn");
                    infoTextView.setTextSize(20);
                }
            }

        });
    }


    private void ManageTurn(String email) {
        if( email.equals(userEmail)){
            this.isMyTurn=true;
        }
        else this.isMyTurn=false;
    }

    private void TextViewInitialMessage() {
        TextView infoTextView = (TextView) findViewById(R.id.textView);
        infoTextView.setText("Looking for a game...");
        infoTextView.setTextSize(40);
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
