package com.path.home.thepathmosttravelled;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ERROR_PAGE extends AppCompatActivity {
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
    private Button back_btn;
    private TextView errorBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error__page);

        String passedError= getIntent().getStringExtra("PASSED_ERROR");

        errorBox = findViewById(R.id.textView4);
        final TextView mTextView = (TextView) findViewById(R.id.textView4);
        mTextView.setText("error\n"+ passedError);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
