package com.example.a2_cmpt276;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Scanner;

import ca.programDemo.model.Lens;
import ca.programDemo.model.LensManager;


public class MainActivity extends AppCompatActivity {

    private static final double COC = 0.029;    // "Circle of Confusion" for a "Full Frame" camera
    private LensManager manager;
    private int size = 0;
    private String []myLenses = new String[size];
    private Scanner in = new Scanner(System.in);// Read from keyboard


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateListView();
        registerClickCallback();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = AddLens.makeLaunchIntent(MainActivity.this, "Add Lens!");
            startActivityForResult(i, 42);
        });
    }

    private void populateListView() {
        // Create list of items
       /* manager.add(new Lens("Canon", 1.8, 50));
        manager.add(new Lens("Tamron", 2.8, 90));
        manager.add(new Lens("Sigma", 2.8, 200));
        manager.add(new Lens("Nikon", 4, 200));
        */
        manager = LensManager.getInstance();
        size = manager.getManagerSize();
        myLenses = new String[size];

        int i=0;
        for(Lens lens: manager)
        {
            myLenses[i++] = lens.toString();
        }

        // Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (
                this,           // Context for view
                R.layout.lens_list,     // Layout to use
                myLenses);               // Items to be displayed

        // Configure the list view
        ListView list = (ListView) findViewById(R.id.listViewMain);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.listViewMain);
        list.setOnItemClickListener((parent, view, position, id) -> {
            TextView textView = (TextView) view;
            String message = textView.getText().toString();
            //Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            Intent intent = CalculateDoF.makeLaunchIntent(MainActivity.this, "CalculateDoF");
            intent.putExtra("Extra", message);
            startActivityForResult(intent, 43);
        });
    }

    // gets called when the activity started, finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 42:
                int answer = data.getIntExtra("result", 0);
                if(answer == 1)
                    populateListView();
                break;
            case 43:
                int answer2 = data.getIntExtra("result", 1);
                if(answer2 == 1)
                    populateListView();
                break;
        }
    }
}
