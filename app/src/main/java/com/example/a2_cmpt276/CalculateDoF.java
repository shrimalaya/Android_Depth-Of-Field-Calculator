package com.example.a2_cmpt276;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;

import ca.programDemo.model.DepthOfFieldCalc;
import ca.programDemo.model.Lens;
import ca.programDemo.model.LensManager;

public class CalculateDoF extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "Extra";
    Lens lens = null;

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

        // Handle the extra
        Intent i = getIntent();
        String message = i.getStringExtra(EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.txtPrintLens);
        textView.setText(message);

        LensManager manager = LensManager.getInstance();

        for(Lens temp: manager) {
            if((temp.toString()).equals(message)) {
                lens = temp;
            }
        }

        initInput();


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Distance = 1000*Double.valueOf(editDistance.getText().toString());
                COC = Double.valueOf(editCOC.getText().toString());
                F = fetchAperture();

                if(F == -1) {
                    showToast("ERROR: Input a smaller F-number");
                    setOutputInvalid();
                }
                else {

                    DepthOfFieldCalc dofObject = new DepthOfFieldCalc(lens, Distance, F, COC);
                    calculateText(dofObject);
                }
            }
        });
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

    private void setOutputInvalid() {
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

}
