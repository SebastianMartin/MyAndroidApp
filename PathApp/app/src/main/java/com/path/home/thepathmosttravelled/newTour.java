package com.path.home.thepathmosttravelled;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class newTour extends AppCompatActivity {
    private FirebaseAuth mAuth;


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
        setContentView(R.layout.new_tour);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent startIntent = new Intent(getApplicationContext(), Welcome_Page.class);
            startActivity(startIntent);
        }
        Firebase.setAndroidContext(this);
    }
}
