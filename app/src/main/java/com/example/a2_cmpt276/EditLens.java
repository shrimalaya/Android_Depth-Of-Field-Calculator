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
import android.widget.TextView;

import ca.programDemo.model.Lens;
import ca.programDemo.model.LensManager;

public class EditLens extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "Extra";
    private Lens lens;
    LensManager manager;

    Button save;

    // input fields
    EditText editMake2, editAperture2, editFocalLength2;

    // Output fields
    String make;
    double aperture;
    int focalLength;

    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, EditLens.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lens);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle the extra
        Intent i = getIntent();
        String message = i.getStringExtra(EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.txtLensString);
        textView.setText(message);

        manager = LensManager.getInstance();
        for(Lens temp: manager) {
            if((temp.toString()).equals(message)) {
                this.lens = temp;
                System.out.println("\n\n\n" + lens);
            }
        }

        //input
        editMake2 = (EditText) findViewById(R.id.editMake2);
        editAperture2 = (EditText) findViewById(R.id.editAperture3);
        editFocalLength2 = (EditText) findViewById(R.id.editFocalLen2);

        //previous values
        editMake2.setText(lens.getMake());
        editAperture2.setText(lens.getMaxAperture() + "");
        editFocalLength2.setText(lens.getFocalLengthInMM() + "");

        save = (Button) findViewById(R.id.btnSaveChanges);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                make = editMake2.getText().toString();
                aperture = Double.valueOf(editAperture2.getText().toString());
                focalLength = Integer.valueOf(editFocalLength2.getText().toString());

                manager = LensManager.getInstance();
                Lens newLens = new Lens(make, aperture, focalLength);
                manager.add(newLens);
                manager.delete(lens);

                Intent intent = new Intent();
                intent.putExtra("Extra", newLens.toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
