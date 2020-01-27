package com.example.a2_cmpt276;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Scanner;

import ca.programDemo.model.Lens;
import ca.programDemo.model.LensManager;
import ca.programDemo.ui.CameraTextUI;

/*


public class Main {
    public static void main(String args[]) {
        //Initialize collection of Lens objects
        LensManager manager = new LensManager();
        //Initiate UI
        CameraTextUI ui = new CameraTextUI(manager);
        //Display UI
        ui.show();
    }
}
*/



public class MainActivity extends AppCompatActivity {

    private static final double COC = 0.029;    // "Circle of Confusion" for a "Full Frame" camera
    private LensManager manager = new LensManager();
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
            startActivity(i);
        });
    }

    private void populateListView() {
        // Create list of items
        manager.add(new Lens("Canon", 1.8, 50));
        manager.add(new Lens("Tamron", 2.8, 90));
        manager.add(new Lens("Sigma", 2.8, 200));
        manager.add(new Lens("Nikon", 4, 200));
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
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String message = "You clicked # " + position
                                + ", which is strong: " + textView.getText().toString();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
