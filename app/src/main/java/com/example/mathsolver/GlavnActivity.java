package com.example.mathsolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;


public class GlavnActivity extends AppCompatActivity {
    TextView uimya, pochta, right, wrong, rating;
    Button razlog, change, back_btn, chanhename;
    DatabaseReference reff;
    String uid, pass;
    LinearLayout glavn_root;
    RelativeLayout root;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavn);
        glavn_root = findViewById(R.id.root_glavn);
        uimya = findViewById(R.id.textname);
        pochta = findViewById(R.id.textpocht);
        right = findViewById(R.id.textright);
        wrong = findViewById(R.id.textwrong);
        rating = findViewById(R.id.textrate);
        razlog = findViewById(R.id.razlog);
        change = findViewById(R.id.change);
        chanhename = findViewById(R.id.changename);
        back_btn = findViewById(R.id.back1);
        root = findViewById(R.id.root_element);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String rightt = dataSnapshot.child("right").getValue().toString();
                String wrongt = dataSnapshot.child("wrong").getValue().toString();
                String ratingg = dataSnapshot.child("rating").getValue().toString();
                pass = dataSnapshot.child("pass").getValue().toString();
                uimya.setText("Моё имя: "+name);
                rating.setText("Мой рейтинг: " + ratingg);
                pochta.setText("Моя почта: "+email);
                right.setText("Решено правильно: " + rightt);
                wrong.setText("Решено неправильно: " + wrongt);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        razlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ralogin();
            }
            });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassChangeWindow();
                

            }
        });
        chanhename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changename();
            }
        });
    }

    private void changename() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Изменить имя");
        dialog.setMessage("Введите новое имя");

        LayoutInflater inflater = LayoutInflater.from(this);
        View newpass_window = inflater.inflate(R.layout.namechange, null);
        dialog.setView(newpass_window);
        final MaterialEditText changename = newpass_window.findViewById(R.id.namechange);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String newname = changename.getText().toString();
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("name").setValue(newname);
                Toast.makeText(getApplicationContext(), "Ваше имя успешно изменено", Toast.LENGTH_LONG);
                startActivity(new Intent(GlavnActivity.this, GlavnActivity.class));
                finish();
            }
        });

        dialog.show();
    }

    private void ralogin() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выйти из профиля?");
        builder.setMessage("");

        // add the buttons
        builder.setPositiveButton("Выйти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(GlavnActivity.this, MainActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showPassChangeWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Изменить пароль");
        dialog.setMessage("Введите данные для смены пароля");

        LayoutInflater inflater = LayoutInflater.from(this);
        View newpass_window = inflater.inflate(R.layout.changepasswordwindow, null);
        dialog.setView(newpass_window);
        final MaterialEditText oldpass = newpass_window.findViewById(R.id.oldpass);
        final MaterialEditText newpass = newpass_window.findViewById(R.id.newpass);
        final MaterialEditText newpass2 = newpass_window.findViewById(R.id.newpass2);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String olpass = oldpass.getText().toString();
                final String npass = newpass.getText().toString();
                final String npass2 = newpass2.getText().toString();

                if(TextUtils.isEmpty(olpass)) {
                    Snackbar.make(glavn_root, "Введите ваш старый пароль", Snackbar.LENGTH_LONG).show();
                    return;
                }
                else{
                    if(TextUtils.equals(olpass, pass)) {
                        if (npass.length() < 5) {
                            Snackbar.make(glavn_root, "Введите пароль в котором больше 5 символов", Snackbar.LENGTH_LONG).show();
                            return;
                        } else {
                            if (TextUtils.equals(npass, npass2)) {
                                //Смена пароля
                                FirebaseUser userp = FirebaseAuth.getInstance().getCurrentUser();
                                if (userp != null)
                                {
                                    userp.updatePassword(newpass2.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful()) {
                                               Snackbar.make(root,"Ваш новый пароль: " + npass2, Snackbar.LENGTH_LONG);
                                               FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("pass").setValue(npass2);
                                               mAuth.signOut();
                                               finish();
                                               Intent i = new Intent(GlavnActivity.this, MainActivity.class);
                                               startActivity(i);
                                           }
                                            else{
                                               Snackbar.make(glavn_root, "Ваш пароль не может быть изменен", Snackbar.LENGTH_LONG).show();
                                           }
                                       }
                                   });
                                }
                            } else {
                                Snackbar.make(glavn_root, "Новые пароли не совпадают!", Snackbar.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }
                    else {
                        Snackbar.make(glavn_root, "Вы ввели неверный пароль!", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });

        dialog.show();
    }
}