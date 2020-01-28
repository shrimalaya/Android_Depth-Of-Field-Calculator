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
import android.widget.Toast;

import ca.programDemo.model.Lens;
import ca.programDemo.model.LensManager;

/**
 * Add Lens activity implementation
 * Inherited from Main Activity
 *
 * Makes changes to Singleton LensManager object and updates
 *  list view through encapsulated intents
 *
 * Takes in the Make, MaxAperture, and Focal length of a lens
 *  and adds it to the list of lenses.
 */

public class AddLens extends AppCompatActivity {
    String make;
    double aperture;
    int focalDistance;
    private LensManager manager;

    EditText editMake, editAperture, editFocalLen;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //input
        editMake = (EditText) findViewById(R.id.editMake2);
        editAperture = (EditText) findViewById(R.id.editAperture2);
        editFocalLen = (EditText) findViewById(R.id.editFocalLen2);

        // Handle the extra
        Intent i = getIntent();
        String message = i.getStringExtra(EXTRA_MESSAGE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_addlens, menu);
        return true;
    }

    /**
     * Method to register interface with menu options from
     *  the App Bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Citation: referenced from https://www.youtube.com/watch?v=GqsQLoQ-6MI
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()) {
            case R.id.menuSave:
                if(!(editAperture.getText().toString().isEmpty() ||
                        editFocalLen.getText().toString().isEmpty() ||
                        editMake.getText().toString().isEmpty()) ) {

                    make = editMake.getText().toString();
                    aperture = Double.valueOf(editAperture.getText().toString());
                    focalDistance = Integer.valueOf(editFocalLen.getText().toString());

                    boolean valid = true;
                    if (make.length() <= 0 || aperture < 1.4 || focalDistance <= 0) {
                        valid = false;

                        showToast("Required valid values:" +
                                "\nMake length  > 0" +
                                "\nFocal length > 0" +
                                "\nAperture (F) >= 1.4");

                        make = editMake.getText().toString();
                        aperture = Double.valueOf(editAperture.getText().toString());
                        focalDistance = Integer.valueOf(editFocalLen.getText().toString());
                    }

                    if (valid) {
                        Lens lens = new Lens(make, aperture, focalDistance);
                        manager = LensManager.getInstance();
                        manager.add(lens);

                        Intent intent = new Intent();
                        intent.putExtra("result", 1);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                        return true;
                    } else
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
        Toast.makeText(AddLens.this, text, Toast.LENGTH_LONG).show();
    }

}
