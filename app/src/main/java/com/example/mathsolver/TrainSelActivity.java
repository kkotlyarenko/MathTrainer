package com.example.mathsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TrainSelActivity extends AppCompatActivity {
Button sloj, vich, umn, delen, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_sel);
        sloj = findViewById(R.id.btn_sloj);
        vich = findViewById(R.id.btn_vich);
        umn = findViewById(R.id.btn_umn);
        delen = findViewById(R.id.btn_delen);
        back = findViewById(R.id.btn_nazad);

        sloj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainSelActivity.this, TrainSlojLevelSel.class));
            }
        });
        vich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainSelActivity.this, TrainVichLevelSel.class));
            }
        });
        umn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainSelActivity.this, TrainUmnLevelSel.class));
            }
        });
        delen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainSelActivity.this, TrainDelLevelSel.class));
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