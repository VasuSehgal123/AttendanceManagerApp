package com.example.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login2 extends AppCompatActivity {
public String category;
 public String br;
    private FirebaseAuth mAuth;

TextInputLayout user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.textView2);
        Login1 obj=new Login1();
        category= Login1.category;
        textView.setText(category);
        user=findViewById(R.id.username);
        pass=findViewById(R.id.password);
     //  boolean check1= email();
       //boolean check2=password();


    }
    public boolean email()
    {
    String val=user.getEditText().getText().toString();
    if(val.isEmpty()) {
        user.setError("Field cannot be empty");
        return false;
    }
    else
        {
            user.setError(null);
            return true;
        }

    }
    public boolean password()
    {
        String val=pass.getEditText().getText().toString();
        if(val.isEmpty()) {
            pass.setError("Field cannot be empty");
            return false;
        }
        else {
            pass.setError(null);
            return true;
        }

    }

    public void signin(View view) {
        boolean c1=email(),c2=password();
        if(!c1 || !c2)
            return;
        String email=user.getEditText().getText().toString();
        String password=pass.getEditText().getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            //Toast.makeText(Login2.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                            if(category.equalsIgnoreCase("Admin")) {
                                Intent intent = new Intent(Login2.this, AddStudent.class);

                                startActivity(intent);
                            }
                            else if(category.equalsIgnoreCase("Faculty"))
                            { Intent intent = new Intent(Login2.this, Attendance.class);

                                startActivity(intent);

                            }
                            else
                            {Intent intent = new Intent(Login2.this, StudentActivity.class);

                                startActivity(intent);

                            }


                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login2.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                          //  updateUI(null);
                        }

                        // ...
                    }
                });


    }
}