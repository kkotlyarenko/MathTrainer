package com.example.mathsolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class DelLeg extends AppCompatActivity {
    Button otvetb, sled, back;
    final Random randint = new Random();
    int our1RandomNumber;
    int our2RandomNumber;

    DatabaseReference reff;
    String uid;
    private FirebaseAuth mAuth;
    int righti, wrongi, ratingi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_leg);
        otvetb = findViewById(R.id.btn_otvet);
        sled = findViewById(R.id.btn_next);
        back = findViewById(R.id.btn_nazad2);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String right = dataSnapshot.child("right").getValue().toString();
                String wrong = dataSnapshot.child("wrong").getValue().toString();
                String rating = dataSnapshot.child("rating").getValue().toString();
                righti = Integer.parseInt(right);
                wrongi = Integer.parseInt(wrong);
                ratingi = Integer.parseInt(rating);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        our1RandomNumber = (int) ( Math.random() * 35 ) + 2;
        our2RandomNumber = (int) ( Math.random() * 35 ) + 2;
        while (true) {
            if((our2RandomNumber < our1RandomNumber ) && (our1RandomNumber%our2RandomNumber == 0))
                break;
            our1RandomNumber = (int) ( Math.random() * 35 ) + 2;
            our2RandomNumber = (int) ( Math.random() * 35 ) + 2;
        }

        TextView result = (TextView) findViewById(R.id.textprem);
        result.setText(our1RandomNumber + " ÷ " + our2RandomNumber + " = ");

        otvetb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText vvodd = findViewById(R.id.otvet);
                TextView verno = (TextView) findViewById(R.id.result);
                if (vvodd.getText().length() == 0) {
                    verno.setTextColor(BLACK);
                    verno.setText("Вы не ввели ответ...");
                }
                else {
                    int otvet = Integer.parseInt(((EditText) findViewById(R.id.otvet)).getText().toString());
                    int result1 = our1RandomNumber / our2RandomNumber;
                    if (result1 == otvet) {
                        verno.setTextColor(GREEN);
                        verno.setText("Верно!\n+ 5 рейтинга!");
                        otvetb.setVisibility(View.GONE);
                        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("right").setValue(righti + 1);
                        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("rating").setValue(ratingi + 5);
                    } else {
                        verno.setTextColor(RED);
                        verno.setText("Неверно!\n- 5 рейтинга!");
                        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("wrong").setValue(wrongi + 1);
                        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("rating").setValue(ratingi -5);
                    }
                }
            }
        });
        sled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DelLeg.this, DelLeg.class));
                finish();
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