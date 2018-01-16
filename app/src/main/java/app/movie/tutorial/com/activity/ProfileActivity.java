package app.movie.tutorial.com.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.model.User;
import app.movie.tutorial.com.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {
    String userEmail= "";
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://10.0.2.2:8081/rest/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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

        Call<User> call = ApiService.GetUserData(userEmail);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    Log.i("response", String.valueOf(response));
                    Log.i("responsebody:    ", String.valueOf(response.body()));
                    User retrievedUser = response.body();
                    String userName = retrievedUser.getUserName();
                    if (userName!= null) {
                        String Email = retrievedUser.getEmail();
                        String Password = retrievedUser.getPassword();
                        String imagePath = retrievedUser.getImagePath();
                        int internalId = retrievedUser.getId();
                        if (imagePath.equals("")){
                            imagePath = "http://www.free-icons-download.net/images/anonymous-user-icon-80332.png";
                        }
                        TextView usernameTv =  (TextView) findViewById(R.id.userNameTV);
                        TextView emailTv =  (TextView) findViewById(R.id.emailTV);
                        TextView passwordTv =  (TextView) findViewById(R.id.passwordTV);
                        TextView idTv =  (TextView) findViewById(R.id.idTV);
                        ImageView imageView = (ImageView) findViewById(R.id.imageView);

                        usernameTv.setText(userName);
                        emailTv.setText("Email:  " +Email);
                        passwordTv.setText("Current password:  "+Password);
                        idTv.setText("Internal server Id:  "+String.valueOf(internalId));
                        Picasso.with(ProfileActivity.this).load("http://www.free-icons-download.net/images/anonymous-user-icon-80332.png").into(imageView);
                    }
                } catch (Exception ex) {
                    Log.d("1",ex.getMessage());
                    showAlertDialog("Warning", "Login error.");
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Log.d("1",throwable.getMessage());
                Log.d("2", call.toString());
                showAlertDialog("Warning", "Conectivity error.");
            }
        });
    }

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
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
