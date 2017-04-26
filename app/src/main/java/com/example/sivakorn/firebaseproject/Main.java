package com.example.sivakorn.firebaseproject;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Main extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private  EditText u,p;
    private TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        u = (EditText)findViewById(R.id.user);
        p =(EditText)findViewById(R.id.pass);
        t = (TextView)findViewById(R.id.textView);
        Button c = (Button)findViewById(R.id.Create);
        Button l = (Button)findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = u.getText().toString();
                String password = p.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Main.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("login", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    t.setText(user.getUid());
                                    Intent intent = new Intent(Main.this, Testwrite.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Login", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Main.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = u.getText().toString();
                String password = p.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Main.this, new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Main.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Log.d("Auth", "finish");
                                    Toast.makeText(Main.this,"finish", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Auth", "signed_in:" + user.getUid());
                    Toast.makeText(Main.this,"signed_in:" +user.getUid(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Auth", "signed_out");
                    Toast.makeText(Main.this,"signed_out", Toast.LENGTH_SHORT).show();
                }

            }
        };


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
