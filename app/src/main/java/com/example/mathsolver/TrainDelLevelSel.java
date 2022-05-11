package com.example.mathsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TrainDelLevelSel extends AppCompatActivity {
    Button easy, med, hard, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_del_level_sel);
        easy = findViewById(R.id.btn_slojeasy);
        med = findViewById(R.id.btn_slojmed);
        hard = findViewById(R.id.btn_slojhard);
        back = findViewById(R.id.btn_nazad1);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainDelLevelSel.this, DelLeg.class));
            }
        });
        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainDelLevelSel.this, DelMid.class));

            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainDelLevelSel.this, DelHard.class));

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}