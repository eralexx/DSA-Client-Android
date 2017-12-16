package app.movie.tutorial.com.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import app.movie.tutorial.com.R;

public class SearchActivity extends AppCompatActivity {

    private EditText mEdit;


    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("The search input is empty.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void ExecuteSearch(View view) {
        mEdit   = (EditText)findViewById(R.id.SearchText);
        String SearchQuery = mEdit.getText().toString();
        if (SearchQuery.isEmpty()) {
            showAlertDialog();
        }
        else {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra("GET_SEARCH_QUERY", SearchQuery);
            startActivity(intent);
        }
    }
}
