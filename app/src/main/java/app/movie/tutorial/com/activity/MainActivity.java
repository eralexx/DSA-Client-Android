package app.movie.tutorial.com.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import app.movie.tutorial.com.R;



public class MainActivity extends AppCompatActivity{

    String userEmail= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userEmail = getIntent().getStringExtra("userEmail");

    }

    public void GameHistory(View view) {

        Intent intent = new Intent(getBaseContext(), GameHistoryActivity.class);
        intent.putExtra("userEmail", userEmail);
        startActivity(intent);
    }
    public void Logout(View view) {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void Profile(View view) {
        Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
        intent.putExtra("userEmail", userEmail);
        startActivity(intent);
    }

    public void FindGame(View view) {
        Intent intent = new Intent(getBaseContext(), GameActivity.class);
        intent.putExtra("userEmail", userEmail);
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
