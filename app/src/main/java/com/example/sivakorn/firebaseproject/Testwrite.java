package com.example.sivakorn.firebaseproject;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Testwrite extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference  mRootRef;
    private EditText input;
    private Button logout,write;
    private TextView uidshow,panal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testwrite);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        input = (EditText)findViewById(R.id.edit1) ;
        logout = (Button)findViewById(R.id.Logout);
        write = (Button)findViewById(R.id.write);
        uidshow = (TextView)findViewById(R.id.Showuid);
        panal = (TextView)findViewById(R.id.panal);

        FirebaseUser user = mAuth.getCurrentUser();
        uidshow.setText("UID:"+user.getUid());
        final String uer = user.getUid();
        mRootRef.child(user.getUid());
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data user = new Data(input.getText().toString(),"email");
                mRootRef.child(uer).push();
            }
        });

        
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });























        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Auth", "signed_in:" + user.getUid());
                    Toast.makeText(Testwrite.this,"signed_in:" +user.getUid(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Auth", "signed_out");
                    Toast.makeText(Testwrite.this,"signed_out", Toast.LENGTH_SHORT).show();
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
