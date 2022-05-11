package com.example.mathsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

public class MenuActivity extends AppCompatActivity {
Button tren, chat, prof, instructions;
LinearLayout menu_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        tren = findViewById(R.id.btn_tren);
        chat = findViewById(R.id.btn_chat);
        prof = findViewById(R.id.btn_prof);
        instructions = findViewById(R.id.btn_instructions);
        menu_root = findViewById(R.id.menu_root);

        Snackbar.make(menu_root, "Вы успешно авторизовались", Snackbar.LENGTH_LONG).show();
        tren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, TrainSelActivity.class);
                startActivity(i);
            }
        });
        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, InstructActivity.class);
                startActivity(i);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, ChatActivity.class);
                startActivity(i);
            }
        });
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, GlavnActivity.class);
                startActivity(i);
            }
        });

    }
}