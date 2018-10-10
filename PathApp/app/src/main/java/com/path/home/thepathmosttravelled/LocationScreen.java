package com.path.home.thepathmosttravelled;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LocationScreen extends AppCompatActivity {
    private ImageView PopMenu;
    private boolean isOpen = false ;
    private ConstraintSet layout1,layout2;
    private ConstraintLayout constraintLayout ;
    private ImageView imageViewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_screen);

        // changing the status bar color to transparent

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();
        imageViewPhoto = findViewById(R.id.cover);
        constraintLayout = findViewById(R.id.constraint_layout);
        layout2.clone(this,R.layout.location_expanded);
        layout1.clone(constraintLayout);

        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isOpen) {
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout2.applyTo(constraintLayout);
                    isOpen = !isOpen ;
                }

                else {

                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout1.applyTo(constraintLayout);
                    isOpen = !isOpen ;

                }
            }
        });

        PopMenu = findViewById(R.id.popMenu);
        PopMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final PopupMenu popmenu = new PopupMenu(LocationScreen.this,PopMenu );
                popmenu.getMenuInflater().inflate(R.menu.popup,popmenu.getMenu());
                popmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
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
                        return false;
                    }
                });
                popmenu.show();

            }
        });




    }
}

