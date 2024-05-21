package com.example.cuocduakythu;

import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    TextView tvTienCuoc;
    Button btnStart;
    CheckBox checkBox, checkBox2, checkBox3;
    SeekBar seekBar, seekBar2, seekBar3;
    EditText edtTienCuoc, edtTienCuoc2, edtTienCuoc3;

    int tienCuoc = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        MediaPlayer theme = MediaPlayer.create(PlayActivity.this, R.raw.theme);
        MediaPlayer countdownSound = MediaPlayer.create(PlayActivity.this, R.raw.countdown);
        MediaPlayer startSound = MediaPlayer.create(PlayActivity.this, R.raw.start);

        theme.setLooping(true);
        theme.start();

        mapping();
        disableSeekBar();
        tvTienCuoc.setText(String.valueOf(tienCuoc));

        CountDownTimer countDownTimer = new CountDownTimer(60000, 200) {
            @Override
            public void onTick(long l) {
                int number = 3;
                Random random = new Random();
                int randomOne = random.nextInt(number) + 1;
                int randomTwo = random.nextInt(number) + 1;
                int randomThree = random.nextInt(number) + 1;

                int progressOne = seekBar.getProgress() + randomOne;
                int progressTwo = seekBar2.getProgress() + randomTwo;
                int progressThree = seekBar3.getProgress() + randomThree;

                animateSeekBar(seekBar, progressOne);
                animateSeekBar(seekBar2, progressTwo);
                animateSeekBar(seekBar3, progressThree);

                if (seekBar.getProgress() >= seekBar.getMax() || seekBar2.getProgress() >= seekBar2.getMax() || seekBar3.getProgress() >= seekBar3.getMax()) {
                    this.cancel();

                    if (seekBar.getProgress() >= seekBar.getMax()) {
                        Toast.makeText(PlayActivity.this, "Thú 1 thắng", Toast.LENGTH_SHORT).show();

                        if (checkBox.isChecked()) {
                            tienCuoc += Integer.parseInt(edtTienCuoc.getText().toString()) * 2;
                            tvTienCuoc.setText(String.valueOf(tienCuoc));
                        }
                    }
                    if (seekBar2.getProgress() >= seekBar2.getMax()) {
                        Toast.makeText(PlayActivity.this, "Thú 2 thắng", Toast.LENGTH_SHORT).show();

                        if (checkBox2.isChecked()) {
                            tienCuoc += Integer.parseInt(edtTienCuoc2.getText().toString()) * 2;
                            tvTienCuoc.setText(String.valueOf(tienCuoc));
                        }
                    }
                    if (seekBar3.getProgress() >= seekBar3.getMax()) {
                        Toast.makeText(PlayActivity.this, "Thú 3 thắng", Toast.LENGTH_SHORT).show();

                        if (checkBox3.isChecked()) {
                            tienCuoc += Integer.parseInt(edtTienCuoc3.getText().toString()) * 2;
                            tvTienCuoc.setText(String.valueOf(tienCuoc));
                        }
                    }

                    startSound.stop();
                    startSound.prepareAsync();
                    theme.start();
                    enableCheckBox();
                    enableEditTienCuoc();
                    btnStart.setEnabled(true);
                }
            }

            @Override
            public void onFinish() {

            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalTienCuoc = 0;
                boolean isChecked = true;

                if (checkBox.isChecked() || checkBox2.isChecked() || checkBox3.isChecked()) {
                    seekBar.setProgress(0);
                    seekBar2.setProgress(0);
                    seekBar3.setProgress(0);

                    if (checkBox.isChecked()) {
                        if (!edtTienCuoc.getText().toString().isEmpty()) {
                            int tienCuoc1 = Integer.parseInt(edtTienCuoc.getText().toString());
                            totalTienCuoc += tienCuoc1;
                        }
                        else {
                            Toast.makeText(PlayActivity.this, "Vui lòng nhập số tiền đặt cược!", Toast.LENGTH_SHORT).show();
                            isChecked = false;
                        }
                    }
                    if (checkBox2.isChecked()) {
                        if (!edtTienCuoc2.getText().toString().isEmpty()) {
                            int tienCuoc2 = Integer.parseInt(edtTienCuoc2.getText().toString());
                            totalTienCuoc += tienCuoc2;
                        }
                        else {
                            Toast.makeText(PlayActivity.this, "Vui lòng nhập số tiền đặt cược!", Toast.LENGTH_SHORT).show();
                            isChecked = false;
                        }
                    }
                    if (checkBox3.isChecked()) {
                        if (!edtTienCuoc3.getText().toString().isEmpty()) {
                            int tienCuoc3 = Integer.parseInt(edtTienCuoc3.getText().toString());
                            totalTienCuoc += tienCuoc3;
                        }
                        else {
                            Toast.makeText(PlayActivity.this, "Vui lòng nhập số tiền đặt cược!", Toast.LENGTH_SHORT).show();
                            isChecked = false;
                        }
                    }

                    if (tienCuoc >= totalTienCuoc && isChecked) {
                        disableCheckBox();
                        disableEditTienCuoc();
                        btnStart.setEnabled(false);
                        tienCuoc -= totalTienCuoc;
                        tvTienCuoc.setText(String.valueOf(tienCuoc));
                        theme.pause();
                        countdownSound.start();

                        countdownSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                startSound.start();
                                countDownTimer.start();
                            }
                        });
                    }
                    else {
                        Toast.makeText(PlayActivity.this, "Không đủ tiền đặt cược", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    Toast.makeText(PlayActivity.this, "Vui lòng đặt cược!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void enableCheckBox() {
        checkBox.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);

        checkBox.setEnabled(true);
        checkBox2.setEnabled(true);
        checkBox3.setEnabled(true);
    }

    private void disableCheckBox() {
        checkBox.setEnabled(false);
        checkBox2.setEnabled(false);
        checkBox3.setEnabled(false);
    }

    private void enableEditTienCuoc() {
        edtTienCuoc.setText("");
        edtTienCuoc2.setText("");
        edtTienCuoc3.setText("");

        edtTienCuoc.setEnabled(true);
        edtTienCuoc2.setEnabled(true);
        edtTienCuoc3.setEnabled(true);
    }

    private void disableEditTienCuoc() {
        edtTienCuoc.setEnabled(false);
        edtTienCuoc2.setEnabled(false);
        edtTienCuoc3.setEnabled(false);
    }

    private void disableSeekBar() {
        seekBar.setEnabled(false);
        seekBar2.setEnabled(false);
        seekBar3.setEnabled(false);
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
        edtTienCuoc = findViewById(R.id.editTextNumber);
        edtTienCuoc2 = findViewById(R.id.editTextNumber2);
        edtTienCuoc3 = findViewById(R.id.editTextNumber3);
    }

    private void animateSeekBar(final SeekBar seekBar, int newProgress) {
        ValueAnimator animator = ValueAnimator.ofInt(seekBar.getProgress(), newProgress);
        animator.setDuration(40);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                int progress = (int) valueAnimator.getAnimatedValue();
                seekBar.setProgress(progress);
            }
        });

        animator.start();
    }
}
