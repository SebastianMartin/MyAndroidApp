package com.path.home.thepathmosttravelled;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ValidateEmail extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button resend_btn;
    private  Button validated_btn;


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
    private int checkVerified(){
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser.isEmailVerified() == true){
            Intent startIntent = new Intent(getApplicationContext(), Welcome_Page.class);
            startActivity(startIntent);
        }
        return 0;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validate_email);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent startIntent = new Intent(getApplicationContext(), Welcome_Page.class);
            startActivity(startIntent);
        }
        Firebase.setAndroidContext(this);
        currentUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //
                        }
                    }
                });
        resend_btn = findViewById(R.id.resend);
        resend_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentUser.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //checkVerified();
                                }
                            }
                        });
                //
            }
        });
        validated_btn = findViewById(R.id.verified);
        validated_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent startIntent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent2);

            }
        });



    }
}
