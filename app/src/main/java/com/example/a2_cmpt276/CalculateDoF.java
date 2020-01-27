package com.example.a2_cmpt276;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ca.programDemo.model.Lens;

public class CalculateDoF extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "Extra";
    Lens lens = new Lens();

    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, CalculateDoF.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_dof);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Handle the extra
        Intent i = getIntent();
        String message = i.getStringExtra(EXTRA_MESSAGE);
        //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        TextView textView = findViewById(R.id.txtPrintLens);
        textView.setText(message);

    }

}
