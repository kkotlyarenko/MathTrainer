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

public class UmnHard extends AppCompatActivity {
    Button otvet, sled, back;
    private Activity mActivity;
    Random randint = new Random();
    int our1RandomNumber = randint.nextInt( 200) + 30;
    int our2RandomNumber = randint.nextInt(200) + 30;
    DatabaseReference reff;
    String uid;
    private FirebaseAuth mAuth;
    int righti, wrongi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umn_hard);
        otvet = findViewById(R.id.btn_otvet);
        sled = findViewById(R.id.btn_next);
        back = findViewById(R.id.btn_nazad2);
        mActivity = UmnHard.this;
        TextView result = (TextView) findViewById(R.id.textprem);
        result.setText(our1RandomNumber + " * " + our2RandomNumber + " = ");

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String right = dataSnapshot.child("right").getValue().toString();
                String wrong = dataSnapshot.child("wrong").getValue().toString();
                righti = Integer.parseInt(right);
                wrongi = Integer.parseInt(wrong);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        otvet.setOnClickListener(new View.OnClickListener() {
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
                    int result1 = our1RandomNumber * our2RandomNumber;
                    if (result1 == otvet) {
                        verno.setTextColor(GREEN);
                        verno.setText("Верно!");
                        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("right").setValue(righti + 1);
                    } else {
                        verno.setTextColor(RED);
                        verno.setText("Неверно.");
                        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("wrong").setValue(wrongi + 1);
                    }
                }
            }
        });
        sled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UmnHard.this, UmnHard.class));
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