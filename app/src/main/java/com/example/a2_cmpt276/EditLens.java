package com.example.a2_cmpt276;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.programDemo.model.Lens;
import ca.programDemo.model.LensManager;

public class EditLens extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "Extra";
    private Lens lens;
    LensManager manager;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_editlens, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Citation: referenced from https://www.youtube.com/watch?v=GqsQLoQ-6MI
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.menuSaveChanges:
                if(!(editAperture2.getText().toString().isEmpty() ||
                        editMake2.getText().toString().isEmpty() ||
                        editFocalLength2.getText().toString().isEmpty()) ) {

                    make = editMake2.getText().toString();
                    aperture = Double.valueOf(editAperture2.getText().toString());
                    focalLength = Integer.valueOf(editFocalLength2.getText().toString());

                    boolean valid = true;
                    if (make.length() <= 0 || aperture < 1.4 || focalLength <= 0) {
                        valid = false;

                        showToast("Required valid values:" +
                                "\nMake length  > 0" +
                                "\nFocal length > 0" +
                                "\nAperture (F) >= 1.4");

                        make = editMake2.getText().toString();
                        aperture = Double.valueOf(editAperture2.getText().toString());
                        focalLength = Integer.valueOf(editFocalLength2.getText().toString());
                    }

                    if (valid) {
                        manager = LensManager.getInstance();
                        Lens newLens = new Lens(make, aperture, focalLength);
                        manager.add(newLens);
                        manager.delete(lens);

                        Intent intent = new Intent();
                        intent.putExtra("Extra", newLens.toString());
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                        return true;
                    }
                    else
                        return false;
                }
                else {
                    showToast("Required valid values:" +
                            "\nMake length  > 0" +
                            "\nFocal length > 0" +
                            "\nAperture (F) >= 1.4");

                    return false;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showToast(String text) {
        Toast.makeText(EditLens.this, text, Toast.LENGTH_LONG).show();
    }
}
