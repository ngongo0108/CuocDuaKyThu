package com.example.cuocduakythu;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    TextView tvTienCuoc;
    Button btnStart;
    CheckBox checkBox, checkBox2, checkBox3;
    SeekBar seekBar, seekBar2, seekBar3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mapping();

        CountDownTimer countDownTimer = new CountDownTimer(60000, 300) {
            @Override
            public void onTick(long l) {
                int number = 5;
                Random random = new Random();
                int randomOne = random.nextInt(number);
                int randomTwo = random.nextInt(number);
                int randomThree = random.nextInt(number);

                seekBar.setProgress(seekBar.getProgress() + randomOne);
                seekBar2.setProgress(seekBar2.getProgress() + randomTwo);
                seekBar3.setProgress(seekBar3.getProgress() + randomThree);

                if (seekBar.getProgress() >= seekBar.getMax()) {
                    this.cancel();
                    Toast.makeText(PlayActivity.this, "Thú 1 thắng", Toast.LENGTH_SHORT).show();
                }
                if (seekBar2.getProgress() >= seekBar2.getMax()) {
                    this.cancel();
                    Toast.makeText(PlayActivity.this, "Thú 2 thắng", Toast.LENGTH_SHORT).show();
                }
                if (seekBar3.getProgress() >= seekBar3.getMax()) {
                    this.cancel();
                    Toast.makeText(PlayActivity.this, "Thú 3 thắng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFinish() {

            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setProgress(0);
                seekBar2.setProgress(0);
                seekBar3.setProgress(0);

                countDownTimer.start();
            }
        });
    }

    private void mapping() {
        tvTienCuoc = findViewById(R.id.tvTienCuoc);
        btnStart = findViewById(R.id.btnStart);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        seekBar = findViewById(R.id.seekBar);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);
    }
}
