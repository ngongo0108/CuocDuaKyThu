package com.example.cuocduakythu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    //Views
    private Button btnPlay, btnUse, btnExits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Reference from layout
        btnPlay = findViewById(R.id.btnPlay);
        btnUse = findViewById(R.id.btnUse);
        btnExits = findViewById(R.id.btnExits);

        //event
        btnPlay.setOnClickListener(this);
        btnUse.setOnClickListener(this);
        btnExits.setOnClickListener(this);
    }

    private void playForm(){
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
        finish(); //close current activity
    }

    private void useForm(){
        Intent intent = new Intent(this, UseActitity.class);
        startActivity(intent);
        finish(); //close current activity
    }

    private void loginForm(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); //close current activity
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPlay){
            playForm();
        }
        else if (v.getId() == R.id.btnUse) {
            useForm();
        }
        else if (v.getId() == R.id.btnExits) {
            loginForm();
        }
    }
}
