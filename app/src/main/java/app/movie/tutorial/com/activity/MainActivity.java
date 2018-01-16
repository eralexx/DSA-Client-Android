package app.movie.tutorial.com.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.squareup.picasso.Picasso;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.model.CustomLayout;


public class MainActivity extends AppCompatActivity{

    String userEmail= "";
    private final String BACKGROUND_URL="https://i.pinimg.com/originals/1d/b4/7d/1db47d885bd31d1f96a5bb590f850284.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userEmail = getIntent().getStringExtra("userEmail");
        SetBackgroundImage();

    }

    private void SetBackgroundImage() {
        CustomLayout mCustomLayout = (CustomLayout)findViewById(R.id.main_layout);
        Picasso.with(this).load(BACKGROUND_URL).into(mCustomLayout);
    }

    public void GameHistory(View view) {

        Intent intent = new Intent(getBaseContext(), StatsActivity.class);
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
