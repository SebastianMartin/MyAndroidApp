package com.path.home.thepathmosttravelled;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Welcome_Page extends AppCompatActivity{
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
    private FirebaseAuth mAuth;
    private Button myTours;
    private  Button myOrgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //check if someone is already logged in.
        if(currentUser == null){
            Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(startIntent);
        }
        if(currentUser.isEmailVerified() == false){
            Intent startIntent = new Intent(getApplicationContext(), ValidateEmail.class);
            startActivity(startIntent);
        }











        final String currEmail = currentUser.getEmail().replace(".", ",");
        final String displayName = currentUser.getDisplayName();
        final TextView mTextView = (TextView) findViewById(R.id.FirstName);
        mTextView.setText(displayName);

        Uri userImage = currentUser.getPhotoUrl();
        //final ImageView userImageView = (ImageView) findViewById(R.id.imageView);
        //userImageView.setImageURI(userImage);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String url = "users/"+currEmail;
        DatabaseReference ref = database.getReference(url);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                //mTextView.setText("YES");
                final TextView mTextView2 = (TextView) findViewById(R.id.LastName);
                mTextView2.setText("Score\n"+ user.score);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




        myTours = findViewById(R.id.myTours);
        myTours.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startIntent = new Intent(getApplicationContext(), myTours.class);
                startActivity(startIntent);
            }
        });
        myOrgs = findViewById(R.id.myOrgs);
        myOrgs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startIntent = new Intent(getApplicationContext(), ERROR_PAGE.class);
                startIntent.putExtra("PASSED_ERROR", "myOrgs");
                startActivity(startIntent);
            }
        });

    }

}
