package app.movie.tutorial.com.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.adapter.CustomAdapter;
import app.movie.tutorial.com.model.Chat;
import app.movie.tutorial.com.model.DataModel;
import app.movie.tutorial.com.rest.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {
    String userEmail = " ";
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://2.152.165.114/rest/";
    public ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userEmail = getIntent().getStringExtra("userEmail");
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiService ApiService = retrofit.create(ApiService.class);

        Call<Chat> call = ApiService.GetAllMessages();

        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                try {
                    listView = (ListView) findViewById(R.id.Chatlistview);

                    dataModels = new ArrayList<>();

                    List<String> Messages = response.body() != null ? response.body().getMessages() : null;
                    Collections.reverse(Messages);
                    for (String Message : Messages) {
                        dataModels.add(new DataModel(Message));
                    }
                    adapter = new CustomAdapter(dataModels, getApplicationContext());
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            DataModel dataModel = dataModels.get(position);
                        }
                    });
                } catch (Exception ex) {
                    showAlertDialog("Warning", "Login error.");
                }
            }
            @Override
            public void onFailure(Call<Chat> call, Throwable throwable) {
                Log.d("1", "liada");
            }
        });
        TimerTask();
    }

    public void populateTable() {
        runOnUiThread(new Runnable() {
            public void run() {
                dataModels.clear();

                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
                ApiService ApiService = retrofit.create(ApiService.class);
                Call<Chat> call = ApiService.GetAllMessages();

                call.enqueue(new Callback<Chat>() {
                    @Override
                    public void onResponse(Call<Chat> call, Response<Chat> response) {
                        try {
                            List<String> Messages = response.body() != null ? response.body().getMessages() : null;
                            Collections.reverse(Messages);
                            for (String Message : Messages) {
                                dataModels.add(new DataModel(Message));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (Exception ex) {
                            Log.d("1", ex.getMessage());
                            showAlertDialog("Warning", "Login error.");
                        }
                    }
                    @Override
                    public void onFailure(Call<Chat> call, Throwable throwable) {
                        Log.d("1", "caca");

                    }
                });
            }
        });
    }
    public void TimerTask() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                populateTable();
            }
        }, 5000, 3000);
    }

    public void postMessage(View v) {
        EditText content = (EditText) findViewById(R.id.editText);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiService ApiService = retrofit.create(ApiService.class);

        Call<Integer> call = ApiService.AddMessage(userEmail, content.getText().toString());

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                try {
                    int postCheck = response.body() != null ? response.body().intValue() : -1;

                    if (postCheck == -1) {
                        showAlertDialog("Warning", "User not found");
                    }
                } catch (Exception ex) {
                    Log.d("1", ex.getMessage());
                    showAlertDialog("Warning", "Login error.");
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                Log.d("1", throwable.getMessage());
                Log.d("2", call.toString());
                showAlertDialog("Warning", "Conectivity error.");
                finish();
            }
        });
        content.setText("");
    }

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}