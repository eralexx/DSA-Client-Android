package app.movie.tutorial.com.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.model.Cell;
import app.movie.tutorial.com.model.CellAdapter;
import app.movie.tutorial.com.model.Game;
import app.movie.tutorial.com.model.OnSwipeTouchListener;
import app.movie.tutorial.com.model.User;
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
    int playerPosition =-1;
    List<Character> posibleMoves=new ArrayList();

    public static final String BASE_URL = "http://2.152.165.114:80/rest/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isMyTurn=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        userEmail = getIntent().getStringExtra("userEmail");
        ExecuteInitialAPICall();

        new Thread(new Runnable() {
            public void run() {
                new Timer().scheduleAtFixedRate(new TimerTask(){
                    @Override
                    public void run(){
                        if (isMyTurn!=true) {
                            RefreshGameAPICall();
                        }
                    }
                },5000,1000);
            }
        }).start();
    }

    private void ExecuteFinalAPICall() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiService ApiService = retrofit.create(ApiService.class);

        Call<Integer> Call = ApiService.DestroyGame(userEmail);
        Call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                try {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("userEmail", userEmail);
                    startActivity(intent);
                } catch (Exception ex) {
                    Log.d("1",ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                Log.d("1",throwable.getMessage());
                Log.d("2", call.toString());
            }
        });
    }
    private void ExecuteInitialAPICall() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiService ApiService = retrofit.create(ApiService.class);

        Call<Integer> Call = ApiService.JoinQueue(userEmail);
        Call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                try {
                    if (response.body()==0){
                        TextViewInitialMessage();
                    }
                } catch (Exception ex) {
                    Log.d("1",ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                Log.d("1",throwable.getMessage());
                Log.d("2", call.toString());
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
                    isMyTurn = false;

                } catch (Exception ex) {
                    Log.d("1",ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<Game> call, Throwable throwable) {
                Log.d("1",throwable.getMessage());
                Log.d("2", call.toString());
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
                    LinearLayout jja = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
                    jja.setVisibility(View.GONE);
                    GridLayout xd= (GridLayout)findViewById(R.id.xd);
                    xd.setVisibility(View.VISIBLE);
                    isMyTurn=false;
                    ClearBoard();
                    Game retrievedGame = response.body();
                    DrawBoard(retrievedGame.getBoard().getAndroidCells());
                    DrawFinish(retrievedGame.getBoard().getWinningCell());
                    for(int i= 0; i<retrievedGame.getPlayers().size(); i++)
                    {
                        if (userEmail.equals(retrievedGame.getPlayers().get(i).getEmail())){
                            playerPosition= i;
                        }
                    }
                    posibleMoves= retrievedGame.getBoard().getPositions().get(playerPosition).getMoves();
                    DrawUsers(retrievedGame.getBoard().getPositions(), retrievedGame.getPlayers());
                    boolean gameOver = ManageWin(retrievedGame.getWinner());
                    if (gameOver==false) {
                        ManageTurn(retrievedGame.getPlayerTurn().getEmail());
                    }
                    SetListener();
                }
                catch (Exception ex) {
                    Log.d("1",ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<Game> call, Throwable throwable) {
                Log.d("1",throwable.getMessage());
                Log.d("2", call.toString());
            }
        });
    }

    private boolean ManageWin(String winner) {
        TextView infoTextView = (TextView) findViewById(R.id.textView400);
        if (winner !=null && !winner.isEmpty()){
            if (winner.equals(userEmail)){
                ClearBoard();
                infoTextView.setText("YOU WON!!!");
                infoTextView.setTextColor(getResources().getColor(R.color.green));
                infoTextView.setTextSize(35);
                ExecuteFinalAPICall();
                return true;
            }
            else{
                ClearBoard();
                infoTextView.setText("You lost... better luck next game :)");
                infoTextView.setTextColor(getResources().getColor(R.color.orange));
                infoTextView.setTextSize(25);
                return true;
            }
        }
        else return false;
    }

    private void SetListener() {
        View view = (LinearLayout) findViewById(R.id.gridLayout);
        view.setClickable(true);

        view.setOnTouchListener(new OnSwipeTouchListener(GameActivity.this) {
            @Override
            public void onSwipeRight() {
                TextView infoTextView = (TextView) findViewById(R.id.textView400);
                if (isMyTurn==true) {
                    if (posibleMoves.contains('E')) {
                        APIMoveCall('E');
                        infoTextView.setText("You moved Right. It's your opponent's turn");
                        infoTextView.setTextSize(18);
                    }
                    else{
                        infoTextView.setText("That move is invalid");
                        infoTextView.setTextSize(18);
                    }
                }
            }
            @Override
            public void onSwipeLeft() {
                TextView infoTextView = (TextView) findViewById(R.id.textView400);
                if (isMyTurn==true) {
                    if (posibleMoves.contains('W')) {
                        APIMoveCall('W');
                        infoTextView.setText("You moved Left. It's your opponent's turn");
                        infoTextView.setTextSize(18);
                    }
                    else{
                        infoTextView.setText("That move is invalid");
                        infoTextView.setTextSize(18);
                    }
                }
            }
            @Override
            public void onSwipeBottom() {
                TextView infoTextView = (TextView) findViewById(R.id.textView400);
                if (isMyTurn==true) {
                    if (posibleMoves.contains('S')) {
                        APIMoveCall('S');
                        infoTextView.setText("You moved Down. It's your opponent's turn");
                        infoTextView.setTextSize(18);
                    }
                    else{
                        infoTextView.setText("That move is invalid");
                        infoTextView.setTextSize(18);
                    }
                }
            }
            @Override
            public void onSwipeTop() {
                TextView infoTextView = (TextView) findViewById(R.id.textView400);
                if (isMyTurn==true) {
                    if (posibleMoves.contains('N')) {

                        APIMoveCall('N');
                        infoTextView.setText("You moved Up. It's your opponent's turn");
                        infoTextView.setTextSize(20);
                    }
                    else{
                        infoTextView.setText("That move is invalid");
                        infoTextView.setTextSize(20);
                    }
                }
            }

        });
    }


    private void ManageTurn(String email) {
        if( email.equals(userEmail)){
            this.isMyTurn=true;
            TextView infoTextView = (TextView) findViewById(R.id.textView400);
            infoTextView.setText("It's your time to move!");
            infoTextView.setTextSize(18);
        }
        else {
            this.isMyTurn=false;
            TextView infoTextView = (TextView) findViewById(R.id.textView400);
            infoTextView.setText("It's your opponent's turn!");
            infoTextView.setTextSize(15);
        }
    }

    private void TextViewInitialMessage() {
        TextView infoTextView = (TextView) findViewById(R.id.textView400);
        infoTextView.setText("Looking for a game...");
        infoTextView.setTextSize(20);
    }

    private void DrawUsers(List<Cell> positions, List<User> players) {
        String VIEW_NAME ="";
        TextView currentTV;
        boolean p1 =false;
        /*for(Cell cell: positions) {

            VIEW_NAME = "tv" + String.valueOf(cell.getPosX()+1) + String.valueOf(cell.getPosY()+1);
            currentTV = (TextView) findViewById(getResources().getIdentifier(VIEW_NAME, "id", getPackageName()));
            if (p1 == false) {
                currentTV.setBackgroundResource(R.drawable.p1);
                p1=true;
            }
            else{
                currentTV.setBackgroundResource(R.drawable.p2);
            }
        }*/
        for (int i=0; i<positions.size(); i++){
            VIEW_NAME = "tv" + String.valueOf(positions.get(i).getPosX()+1) + String.valueOf(positions.get(i).getPosY()+1);
            currentTV = (TextView) findViewById(getResources().getIdentifier(VIEW_NAME, "id", getPackageName()));
            setTVBackgroundFromUrl(currentTV, players.get(i).getImagePath());
        }
    }

    private void setTVBackgroundFromUrl(TextView currentTV, String imagePath) {
        Picasso.with(this).load(imagePath).into(new Target(){

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                currentTV.setBackground(new BitmapDrawable(bitmap));
            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        });

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
    private void ClearBoard() {
        TextView tv;
        tv=(TextView) findViewById(R.id.tv11);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv12);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv13);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv14);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv15);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv21);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv22);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv23);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv24);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv25);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv31);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv32);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv33);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv34);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv35);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv41);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv42);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv43);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv44);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv45);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv51);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv52);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv53);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv54);
        tv.setBackgroundResource(android.R.color.transparent);
        tv=(TextView) findViewById(R.id.tv55);
        tv.setBackgroundResource(android.R.color.transparent);


    }
}
