package com.example.a2_cmpt276;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

import ca.programDemo.model.Lens;
import ca.programDemo.model.LensManager;

public class AddLens extends AppCompatActivity {
    String make;
    double aperture;
    double focalDistance;
    private LensManager manager;

    EditText editMake, editAperture, editFocalLen;

    Button saveButton, cancelButton;

    private static final String EXTRA_MESSAGE = "Extra-message";

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

        //input
        editMake = (EditText) findViewById(R.id.editMake);
        editAperture = (EditText) findViewById(R.id.editAperture);
        editFocalLen = (EditText) findViewById(R.id.editFocalLen);

        saveButton = (Button) findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                make = editMake.getText().toString();
                aperture = Double.valueOf(editAperture.getText().toString());
                focalDistance = Double.valueOf(editFocalLen.getText().toString());

                Lens lens = new Lens(make, aperture, focalDistance);
                manager = LensManager.getInstance();
                manager.add(lens);

                Intent intent = new Intent();
                intent.putExtra("result", 1);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        cancelButton = (Button) findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", 0);
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        // Handle the extra
        Intent i = getIntent();
        String message = i.getStringExtra(EXTRA_MESSAGE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    private void showToast(String text) {
        Toast.makeText(AddLens.this, text, Toast.LENGTH_LONG).show();
    }

}
