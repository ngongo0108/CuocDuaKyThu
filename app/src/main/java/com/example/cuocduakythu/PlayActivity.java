package com.example.cuocduakythu;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    TextView tvTienCuoc;
    Button btnStart, btnActor, btnExit, btnReset ;
    CheckBox checkBox, checkBox2, checkBox3;
    SeekBar seekBar, seekBar2, seekBar3;
    EditText edtTienCuoc, edtTienCuoc2, edtTienCuoc3;

    Dialog menuItem, announce;

    int tienCuoc = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        MediaPlayer theme = MediaPlayer.create(PlayActivity.this, R.raw.theme);
        MediaPlayer countdownSound = MediaPlayer.create(PlayActivity.this, R.raw.countdown);
        MediaPlayer startSound = MediaPlayer.create(PlayActivity.this, R.raw.start);
        MediaPlayer announceSound = MediaPlayer.create(PlayActivity.this, R.raw.announce);

        theme.setLooping(true);
        theme.start();

        mapping();
        announce = new Dialog(this);

        disableSeekBar();
        tvTienCuoc.setText(String.valueOf(tienCuoc));

        CountDownTimer countDownTimer = new CountDownTimer(60000, 200) {
            @Override
            public void onTick(long l) {
                int number = 5;
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

                    announce.setContentView(R.layout.activity_announce);
                    TextView text ;
                    text = announce.findViewById(R.id.text);
                    announce.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    if (seekBar.getProgress() >= seekBar.getMax()) {
                        text.setText("Animal 1 has won!");
                    } else if (seekBar2.getProgress() >= seekBar2.getMax()) {
                        text.setText("Animal 2 has won!");
                    } else if (seekBar3.getProgress() >= seekBar3.getMax()) {
                        text.setText("Animal 3 has won!");
                    } else if (seekBar.getProgress() >= seekBar.getMax() && seekBar2.getProgress() >= seekBar2.getMax()) {
                        text.setText("Animal 1 and 2 have won!");
                    }
                    else if (seekBar.getProgress() >= seekBar.getMax() && seekBar3.getProgress() >= seekBar3.getMax()) {
                        text.setText("Animal 1 and 3 have won!");
                    }
                    else if (seekBar2.getProgress() >= seekBar2.getMax() && seekBar3.getProgress() >= seekBar3.getMax()) {
                        text.setText("Animal 2 and 3 have won!");
                    }
                    else {
                        text.setText("All won!");
                    }

                    startSound.stop();
                    startSound.prepareAsync();

                    announceSound.start();
                    announce.show();

                    if (seekBar.getProgress() >= seekBar.getMax()) {

                        if (checkBox.isChecked()) {
                            tienCuoc += Integer.parseInt(edtTienCuoc.getText().toString()) * 2;
                            tvTienCuoc.setText(String.valueOf(tienCuoc));
                        }
                    }
                    if (seekBar2.getProgress() >= seekBar2.getMax()) {
                        if (checkBox2.isChecked()) {
                            tienCuoc += Integer.parseInt(edtTienCuoc2.getText().toString()) * 2;
                            tvTienCuoc.setText(String.valueOf(tienCuoc));
                        }
                    }
                    if (seekBar3.getProgress() >= seekBar3.getMax()) {
                        if (checkBox3.isChecked()) {
                            tienCuoc += Integer.parseInt(edtTienCuoc3.getText().toString()) * 2;
                            tvTienCuoc.setText(String.valueOf(tienCuoc));
                        }
                    }

                    announceSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            announce.dismiss();
                            theme.start();
                            enableCheckBox();
                            enableEditTienCuoc();
                            btnExit.setEnabled(true);
                            btnReset.setEnabled(true);
                        }
                    });
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
                        btnExit.setEnabled(false);
                        btnReset.setEnabled(false);
                        btnActor.setEnabled(false);
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
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme.stop();
                theme.prepareAsync();
                homeForm();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(0);
                seekBar2.setProgress(0);
                seekBar3.setProgress(0);
                btnStart.setEnabled(true);
                btnActor.setEnabled(true);
            }
        });
        menuItem = new Dialog(this);

    }
    public void show_Dialog(View v) {
        TextView btnClose ;
        Button btnSubmit;
        menuItem.setContentView(R.layout.activity_actor_popup);
        btnClose = (TextView) menuItem.findViewById(R.id.btnClose);
        btnSubmit = (Button) menuItem.findViewById(R.id.btnSubmit);

        CheckBox cb1 = menuItem.findViewById(R.id.cb1);
        CheckBox cb2 = menuItem.findViewById(R.id.cb2);
        CheckBox cb3 = menuItem.findViewById(R.id.cb3);
        CheckBox cb4 = menuItem.findViewById(R.id.cb4);
        CheckBox cb5 = menuItem.findViewById(R.id.cb5);
        CheckBox cb6 = menuItem.findViewById(R.id.cb6);

        ImageView actor1 = menuItem.findViewById(R.id.actor1);
        ImageView actor2 = menuItem.findViewById(R.id.actor2);
        ImageView actor3 = menuItem.findViewById(R.id.actor3);
        ImageView actor4 = menuItem.findViewById(R.id.actor4);
        ImageView actor5 = menuItem.findViewById(R.id.actor5);
        ImageView actor6 = menuItem.findViewById(R.id.actor6);

        Drawable image1 = actor1.getDrawable();
        Drawable image2 = actor2.getDrawable();
        Drawable image3 = actor3.getDrawable();
        Drawable image4 = actor4.getDrawable();
        Drawable image5 = actor5.getDrawable();
        Drawable image6 = actor6.getDrawable();

        // Lưu các ImageView và CheckBox vào mảng
        CheckBox[] checkBoxes = {cb1, cb2, cb3, cb4, cb5, cb6};
        ImageView[] imageViews = {actor1, actor2, actor3, actor4, actor5, actor6};
        Drawable[] images = {image1, image2, image3, image4, image5, image6};

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countCheckbox = getCheckedCount(checkBoxes);

                if (countCheckbox >3 || countCheckbox <3) {
                    Toast.makeText(PlayActivity.this, "Please select exactly 3 actors.", Toast.LENGTH_SHORT).show();
                } else {

                    int[] checkedPositions = getCheckedPositions(checkBoxes);

                    // Cập nhật thumb của SeekBar với ảnh đã chọn
                    seekBar.setThumb(images[checkedPositions[0]]);
                    seekBar2.setThumb(images[checkedPositions[1]]);
                    seekBar3.setThumb(images[checkedPositions[2]]);
                    Toast.makeText(PlayActivity.this, "Changed successfully!", Toast.LENGTH_SHORT).show();
                    menuItem.dismiss();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuItem.dismiss();
            }
        });
        menuItem.show();
    }

    private int getCheckedCount(CheckBox[] checkBoxes) {
        int count = 0;
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                count++;
            }
        }
        return count;
    }

    private int[] getCheckedPositions(CheckBox[] checkBoxes) {
        ArrayList<Integer> checkedPositions = new ArrayList<>();
        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isChecked()) {
                checkedPositions.add(i);
            }
        }
        // Chuyển ArrayList thành mảng int[]
        int[] result = new int[checkedPositions.size()];
        for (int i = 0; i < checkedPositions.size(); i++) {
            result[i] = checkedPositions.get(i);
        }
        return result;
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
        seekBar = findViewById(R.id.seekBar1);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);
        edtTienCuoc = findViewById(R.id.editTextNumber);
        edtTienCuoc2 = findViewById(R.id.editTextNumber2);
        edtTienCuoc3 = findViewById(R.id.editTextNumber3);
        btnActor = findViewById(R.id.btnActor);
        btnExit = findViewById(R.id.btnExit);
        btnReset = findViewById(R.id.btnReset);
    }

    private void animateSeekBar(final SeekBar seekBar, int newProgress) {
        ValueAnimator animator = ValueAnimator.ofInt(seekBar.getProgress(), newProgress);
        animator.setDuration(30);
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
    private void homeForm(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish(); //close current activity
    }
}
