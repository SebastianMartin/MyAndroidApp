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
import android.widget.Button;
import android.widget.EditText;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Registration_Page extends AppCompatActivity {
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
            Intent startIntent2 = new Intent(getApplicationContext(), AddImage.class);
            startActivity(startIntent2);
        }
        return super.onOptionsItemSelected(item);
    }
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private Button registration_btn;
    private Button back_btn;

    private EditText Username;
    private EditText Password;
    private EditText CPassword;
    private EditText FirstName;
    private EditText LastName;
    private EditText Email;
    public int RegisterUser(final String userName, final String firstName, final String lastName, final String password, final String email, final String strDate) {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            writeNewUser(userName,firstName,lastName,userName,password,email,strDate);

                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(firstName+" "+lastName)
                                    .setPhotoUri(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/c/c9/Moon.jpg"))
                                    .build();

                            currentUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Intent startIntent = new Intent(getApplicationContext(), ERROR_PAGE.class);
                                                startActivity(startIntent);
                                            }
                                        }
                                    });


                        }
                        else{
                            writeNewUser(userName,firstName,lastName,userName,password,email,strDate);

                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(firstName+" "+lastName)
                                    .setPhotoUri(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/c/c9/Moon.jpg"))
                                    .build();

                            currentUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Intent startIntent = new Intent(getApplicationContext(), AddImage.class);
                                                startActivity(startIntent);
                                            }
                                        }
                                    });


                        }
                    }
                });
        return 1;
    }
    private void writeNewUser(String userId, String firstName, String lastName, String username,String password, String email,String Date) {
        User user = new User(firstName,lastName,username,password, email,true,Date,Date,0.0);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(email.replace(".", ",")).setValue(user);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        back_btn = findViewById(R.id.back_btn);
        registration_btn = findViewById(R.id.RegButton);

        Username = findViewById(R.id.RegUsername);
        Password = findViewById(R.id.RegPassword);
        FirstName = findViewById(R.id.RegFirstName);
        LastName =  findViewById(R.id.RegLastName);
        CPassword = findViewById(R.id.PasswordConfirm);
        Email = findViewById(R.id.emailAddress);


        back_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });
        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = FirstName.getText().toString();
                String lastName = LastName.getText().toString();
                String username = Username.getText().toString();
                String password = Password.getText().toString();
                String cPassword = CPassword.getText().toString();
                String email = Email.getText().toString();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss");
                String strDate = mdformat.format(calendar.getTime());

                if (firstName.isEmpty()){
                    final EditText mTextView = (EditText) findViewById(R.id.RegFirstName);
                    mTextView.setBackgroundColor(Color.parseColor("#e55b72"));
                    mTextView.setHint("First Name Required");
                }
                else{
                    final EditText mTextView = (EditText) findViewById(R.id.RegFirstName);
                    mTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mTextView.setHint("First Name");
                }
                if(lastName.isEmpty()){
                    final EditText mTextView = (EditText) findViewById(R.id.RegLastName);
                    mTextView.setBackgroundColor(Color.parseColor("#e55b72"));
                    mTextView.setHint("Last Name Required");
                }
                else{
                    final EditText mTextView = (EditText) findViewById(R.id.RegLastName);
                    mTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mTextView.setHint("Last Name");
                }
                if (username.isEmpty()){
                    final EditText mTextView = (EditText) findViewById(R.id.RegUsername);
                    mTextView.setBackgroundColor(Color.parseColor("#e55b72"));
                    mTextView.setHint("UserName Required");

                }
                else{
                    final EditText mTextView = (EditText) findViewById(R.id.RegUsername);
                    mTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mTextView.setHint("UserName");
                }
                if (password.isEmpty()){
                    final EditText mTextView = (EditText) findViewById(R.id.RegPassword);
                    mTextView.setBackgroundColor(Color.parseColor("#e55b72"));
                    mTextView.setHint("Password Required");

                }
                else{
                    final EditText mTextView = (EditText) findViewById(R.id.RegPassword);
                    mTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mTextView.setHint("Password");
                }
                if (email.isEmpty()){
                    final EditText mTextView = (EditText) findViewById(R.id.emailAddress);
                    mTextView.setBackgroundColor(Color.parseColor("#e55b72"));
                    mTextView.setHint("E-Mail Required");

                }
                else{
                    final EditText mTextView = (EditText) findViewById(R.id.emailAddress);
                    mTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mTextView.setHint("E-Mail");
                }
                if (cPassword.isEmpty()){
                    final EditText mTextView = (EditText) findViewById(R.id.PasswordConfirm);
                    mTextView.setBackgroundColor(Color.parseColor("#e55b72"));
                    mTextView.setHint("Password Required");
                }
                else{
                    final EditText mTextView = (EditText) findViewById(R.id.PasswordConfirm);
                    mTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mTextView.setHint("Confirm Password");
                }
                if(!cPassword.equals(password)){
                    final EditText mTextView = (EditText) findViewById(R.id.RegPassword);
                    mTextView.setText("");
                    mTextView.setBackgroundColor(Color.parseColor("#e55b72"));
                    mTextView.setHint("Password don't match");
                    final EditText mTextView2 = (EditText) findViewById(R.id.PasswordConfirm);
                    mTextView2.setText("");
                    mTextView2.setBackgroundColor(Color.parseColor("#e55b72"));
                    mTextView2.setHint("Password don't match");
                }


                else if(!firstName.isEmpty()&&!lastName.isEmpty()&&!username.isEmpty()&&!password.isEmpty()&&!cPassword.isEmpty()) {
                    //writeNewUser(username,firstName,lastName,username,password,email,strDate);
                    RegisterUser(username,firstName,lastName,password,email,strDate);
                    //Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(startIntent);
                }


            }
        });




    }
}
