package com.example.a2_cmpt276;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;

import ca.programDemo.model.DepthOfFieldCalc;
import ca.programDemo.model.Lens;
import ca.programDemo.model.LensManager;

/**
 * CalculateDoF activity implementation with optional features
 * included.
 *
 * Allows user to input distance of object, aperture and Circle of confusion.
 *
 * Calculates Near focal point, far focal point,
 * hyperfocal distance and depth of field values
 *
 * Inherited from MainActivity
 *
 * Allows user to go to edit lens activity through menu
 * or delete the currently selected lens on 1 click
 *
 */

public class CalculateDoF extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "Extra";
    Lens lens = null;
    LensManager manager;

    // Input variables
    double COC, Distance, F;

    //EditText
    EditText editCOC, editDistance, editAperture;
    Button calculateButton;

    // Output (Text) Variables
    double NFD, FFD, DOF, HFD;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        runInterface();
    }

    private void runInterface() {
        // Handle the extra
        Intent i = getIntent();
        String message = i.getStringExtra(EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.txtPrintLens);
        textView.setText(message);

        manager = LensManager.getInstance();

        for(Lens temp: manager) {
            if((temp.toString()).equals(message)) {
                lens = temp;
            }
        }

        initInput();

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(editAperture.getText().toString().isEmpty() ||
                        editCOC.getText().toString().isEmpty() ||
                        editDistance.getText().toString().isEmpty()) ) {

                    Distance = 1000 * Double.valueOf(editDistance.getText().toString());
                    COC = Double.valueOf(editCOC.getText().toString());
                    F = fetchAperture();

                    if (F == -1) {
                        showToast("Required valid Aperture:" +
                                "\nSelect Aperture(F) >= 1.4" +
                                "\nAperture <= max Aperture of lens");
                        setInvalidF();
                    } else if (Distance <= 0 || COC <= 0) {
                        showToast("Required valid values:" +
                                "\nCircle of Confusion > 0" +
                                "\nDistance to subject > 0" +
                                "\nSelect Aperture(F) >= 1.4" +
                                "\nAperture <= max Aperture of lens");
                        setInvalidInput();
                    } else {
                        DepthOfFieldCalc dofObject = new DepthOfFieldCalc(lens, Distance, F, COC);
                        calculateText(dofObject);
                    }
                }
            }
        });

        Intent intent = new Intent();
        intent.putExtra("result", 1);
        setResult(Activity.RESULT_OK, intent);
    }

    private void initInput() {
        //input
        editCOC = (EditText) findViewById(R.id.editCOC);
        editDistance = (EditText) findViewById(R.id.editDistance);
        editAperture = (EditText) findViewById(R.id.editAperture);
        calculateButton =   (Button) findViewById(R.id.btnCalculateDOF);
    }

    private double fetchAperture() {
        double aperture = Double.valueOf(editAperture.getText().toString());

        if(aperture < lens.getMaxAperture())
            return -1;
        else
            return aperture;
    }

    private void setInvalidF() {
        TextView txtNFD = (TextView) findViewById(R.id.txtDisplayNFD);
        TextView txtFFD = (TextView) findViewById(R.id.txtDisplayFFD);
        TextView txtDOF = (TextView) findViewById(R.id.txtDisplayDOF);
        TextView txtHFD = (TextView) findViewById(R.id.txtDisplayHFD);

        txtNFD.setText("Invalid Aperture");
        txtFFD.setText("Invalid Aperture");
        txtDOF.setText("Invalid Aperture");
        txtHFD.setText("Invalid Aperture");

        return;
    }

    private void setInvalidInput() {
        TextView txtNFD = (TextView) findViewById(R.id.txtDisplayNFD);
        TextView txtFFD = (TextView) findViewById(R.id.txtDisplayFFD);
        TextView txtDOF = (TextView) findViewById(R.id.txtDisplayDOF);
        TextView txtHFD = (TextView) findViewById(R.id.txtDisplayHFD);

        txtNFD.setText("Enter valid values");
        txtFFD.setText("Enter valid values");
        txtDOF.setText("Enter valid values");
        txtHFD.setText("Enter valid values");

        return;
    }

    private void calculateText(DepthOfFieldCalc dofObject) {
        NFD = dofObject.getNearFocalPoint()/1000.0;
        FFD = dofObject.getFarFocalPoint()/1000.0;
        DOF = dofObject.getDepthOfFieldInMM()/1000.0;
        HFD = dofObject.getHyperFocalDistInMM()/1000.0;

        TextView txtNFD = (TextView) findViewById(R.id.txtDisplayNFD);
        TextView txtFFD = (TextView) findViewById(R.id.txtDisplayFFD);
        TextView txtDOF = (TextView) findViewById(R.id.txtDisplayDOF);
        TextView txtHFD = (TextView) findViewById(R.id.txtDisplayHFD);

        txtNFD.setText(formatM(NFD)+"m");
        txtFFD.setText(formatM(FFD)+"m");
        txtDOF.setText(formatM(DOF)+"m");
        txtHFD.setText(formatM(HFD)+"m");

        return;
    }

    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }


    private void showToast(String text) {
        Toast.makeText(CalculateDoF.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_calculatedof, menu);
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
            case R.id.menuEdit:
                Intent i = EditLens.makeLaunchIntent(CalculateDoF.this, "EditLens");
                i.putExtra("Extra", lens.toString());
                startActivityForResult(i, 31);
                finish();
                return true;
            case R.id.menuDelete:
                LensManager manager = LensManager.getInstance();
                manager.delete(lens);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *  Overridden function to work with startActivityForResult function
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 31:
                runInterface();
                break;
        }
    }
}
