package com.example.a2_cmpt276;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

public class AddLens extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "Extra - message";

    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, AddLens.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lens);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Handle the extra
        Intent i = getIntent();
        String message = i.getStringExtra(EXTRA_MESSAGE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

}
