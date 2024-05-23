package com.example.cuocduakythu;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.media.MediaPlayer;

public class EndActivity extends AppCompatActivity {

    private TextView textViewCountdown;
    private MediaPlayer countdownSound;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        textViewCountdown = findViewById(R.id.textViewCountdown);
        countdownSound = MediaPlayer.create(this, R.raw.hetgio);
        // Khởi tạo và chạy CountDownTimer
        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                textViewCountdown.setText("" + millisUntilFinished / 1000 + " s");

                if (millisUntilFinished / 300 == 10) {
                    countdownSound = MediaPlayer.create(EndActivity.this, R.raw.hetgio);
                    countdownSound.start();
                }
            }

            public void onFinish() {


                Intent intent = new Intent(EndActivity.this, PlayActivity.class);
                startActivity(intent);

                finish();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Giải phóng tài nguyên MediaPlayer khi hoạt động kết thúc
        if (countdownSound != null) {
            countdownSound.release();
            countdownSound = null;
        }
    }
}

