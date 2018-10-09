package com.path.home.thepathmosttravelled;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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

    private Button Login_Btn;
    private Button Barcode;
    private Button Registration_Btn;
    private EditText Username;
    private EditText Password;
    public int CheckUser(String userName, String password) {
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent startIntent = new Intent(getApplicationContext(), Welcome_Page.class);
                            startActivity(startIntent);
                        } else {
                            invalidCredentials();
                        }
                    }
                });
        return 1;
    }

    public int invalidCredentials() {
        final EditText mTextView = (EditText) findViewById(R.id.usernameField);
        mTextView.setHighlightColor(2);
        mTextView.setBackgroundColor(Color.parseColor("#e55b72"));
        mTextView.setText("");
        mTextView.setHint("Invalid Credential User");
        final EditText mTextView2 = (EditText) findViewById(R.id.passwordField);
        mTextView2.setHighlightColor(2);
        mTextView2.setBackgroundColor(Color.parseColor("#e55b72"));
        mTextView2.setText("");
        mTextView2.setHint("Invalid Credentials Pass");
        return 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent startIntent = new Intent(getApplicationContext(), Welcome_Page.class);
            startActivity(startIntent);
        }
        Firebase.setAndroidContext(this);


        Login_Btn = (Button) findViewById(R.id.login_btn);
        Registration_Btn = (Button) findViewById(R.id.register_btn);

        Username = findViewById(R.id.usernameField);
        Password = findViewById(R.id.passwordField);
        //Login Button
        Login_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(Username.getText().toString().isEmpty()){
                    final EditText mTextView = (EditText) findViewById(R.id.usernameField);
                    mTextView.setHighlightColor(2);
                    mTextView.setBackgroundColor(Color.parseColor("#e55b72"));
                    mTextView.setHint("UserName Required");
                }
                else{
                    final EditText mTextView = (EditText) findViewById(R.id.usernameField);
                    mTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mTextView.setHint("UserName");
                }
                if(Password.getText().toString().isEmpty()){
                    final EditText mTextView = (EditText) findViewById(R.id.passwordField);
                    mTextView.setHighlightColor(2);
                    mTextView.setBackgroundColor(Color.parseColor("#e55b72"));
                    mTextView.setHint("Password Required");
                }
                else{
                    final EditText mTextView = (EditText) findViewById(R.id.passwordField);
                    mTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mTextView.setHint("Password");
                }
                if(!Username.getText().toString().isEmpty()&&!Password.getText().toString().isEmpty()){
                    CheckUser(Username.getText().toString(),Password.getText().toString());
                }

            }
        });
        //goes to Registration Page
        Registration_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startIntent = new Intent(getApplicationContext(), Sample.class);
                startActivity(startIntent);
            }
        });
    }

}
