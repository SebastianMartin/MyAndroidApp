package com.path.home.thepathmosttravelled;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class myTours extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;
    private Button newTour;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.user){
            Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(startIntent);
        }
        else if(item.getItemId()==R.id.setting){
            Intent startIntent = new Intent(getApplicationContext(), ERROR_PAGE.class);
            startIntent.putExtra("PASSED_ERROR", "SETTINGS PAGE");
            startActivity(startIntent);
        }
        else if(item.getItemId()==R.id.about){
            Intent startIntent = new Intent(getApplicationContext(), ERROR_PAGE.class);
            startIntent.putExtra("PASSED_ERROR", "ABOUT PAGE ");
            startActivity(startIntent);
        }
        else if(item.getItemId()==R.id.logOut){
            FirebaseAuth.getInstance().signOut();
            Intent startIntent2 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(startIntent2);
        }
        return super.onOptionsItemSelected(item);
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tours);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent startIntent = new Intent(getApplicationContext(), Welcome_Page.class);
            startActivity(startIntent);
        }
        Firebase.setAndroidContext(this);

        Spinner dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"one", "two", "three","4","5","6","7","8","9","10","11"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        newTour = findViewById(R.id.newTour);
        newTour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startIntent = new Intent(getApplicationContext(), newTour.class);
                startActivity(startIntent);
            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position >0){
            Intent startIntent = new Intent(getApplicationContext(), ERROR_PAGE.class);
            startIntent.putExtra("PASSED_ERROR", Integer.toString(position+1));
            startActivity(startIntent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
