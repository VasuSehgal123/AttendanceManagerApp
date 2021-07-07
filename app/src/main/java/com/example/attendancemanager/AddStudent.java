package com.example.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddStudent extends AppCompatActivity {
    private TextInputLayout et_user_id,et_name,et_dept,et_phone,et_email,et_password;

    Button b5;
    FirebaseAuth firebaseAuth,mAuth2;
    DatabaseReference databaseReference;
    CheckBox cb1,cb2,cb3,cb4;
    FirebaseDatabase firebaseDatabase;
    static Map<String,StudentInformation> studentList= new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Intent i= getIntent();
        et_user_id=findViewById(R.id.reg_userid);
        et_name=findViewById(R.id.reg_name);

        et_dept=findViewById(R.id.branch22);
        et_phone=findViewById(R.id.reg_phoneNo);
        et_email=findViewById(R.id.reg_email);
        et_password=findViewById(R.id.reg_password);
        b5=(Button)findViewById(R.id.b5);




       FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://attendance-manager-13103.firebaseio.com/")
                .setApiKey("AIzaSyCx7RYGrp5W17NaR-8q5lit2exvi37wpyk")
                .setApplicationId("1:427126601247:android:26cee2af897b0469c6c72a").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "Attendance Manager");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("Attendance Manager"));
        }




        cb1=(CheckBox)findViewById(R.id.cb1);
        cb2=(CheckBox)findViewById(R.id.cb2);
        cb3=(CheckBox)findViewById(R.id.cb3);
        cb4=(CheckBox)findViewById(R.id.cb4);


    }

    public void fnRegister(View view) {



        Log.e("Entering ","In fn");

        final String userid=et_user_id.getEditText().getText().toString().trim();
        String name=et_name.getEditText().getText().toString().trim();
        final String dept=et_dept.getEditText().getText().toString().trim();
        String phone=et_phone.getEditText().getText().toString().trim();
        String email=et_email.getEditText().getText().toString().trim();
        String password=et_password.getEditText().getText().toString().trim();
int f=1;
        if(userid.isEmpty()) {
            et_user_id.setError("field cannot be empty");
            f=0;

        }
        if(name.isEmpty()) {
            et_name.setError("field cannot be empty");
            f=0;
        }

            if(dept.isEmpty()){
                et_dept.setError("field cannot be empty");
                f=0;
            }
                if(phone.isEmpty()){
                    et_phone.setError("field cannot be empty");
                    f=0;
                }

                    if(email.isEmpty()){
                        et_email.setError("field cannot be empty");
                        f=0;
                    }

                        if(password.isEmpty()){
                            et_password.setError("field cannot be empty");
                            f=0;
                        }
                        if(f==0)
                            return;
        //checking checkboxes

        ArrayList<Integer> subject=new ArrayList<Integer>(4);

        if(!cb1.isChecked())
            subject.add(0,-1);
        else  subject.add(0,0);
        if(!cb2.isChecked())
            subject.add(1,-1);
        else  subject.add(1,0);
        if(!cb3.isChecked())
            subject.add(2,-1);
        else  subject.add(2,0);
        if(!cb4.isChecked())
            subject.add(3,-1);
        else  subject.add(3,0);


        int sum=0;
        for(int i=0;i<4;i++)
            sum=sum+subject.get(i);

        if(sum==-4){

            Toast.makeText(AddStudent.this, "Please Select subjects", Toast.LENGTH_SHORT).show();
            return ;
        }




        Log.e("Entering ", "In fn2");
        Toast.makeText(AddStudent.this, "Validation Successful", Toast.LENGTH_SHORT).show();

   /*     pd.setMessage("Registering User...");
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();*/

      Log.e("Entering ", "In fn3");
        try{
            final StudentInformation stdinfo=new StudentInformation(userid,name,dept,phone,email,password,subject);
            mAuth2.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(AddStudent.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();

                       saveInformation(stdinfo,userid,dept);
                        mAuth2.signOut();
                        Intent replyIntent = new Intent();
                        finish();
                       // pd.dismiss();
                    } else {
                        Toast.makeText(AddStudent.this, "Unable to register", Toast.LENGTH_SHORT).show();
                        Log.e("Exception is", task.getException().toString());
                       // pd.dismiss();
                    }
                }
            });
        }catch(Exception e){
            Log.e("Exception is ",e.toString());
        }


    }

    public void saveInformation(final StudentInformation stdinfo , final String userid, String dept){

        try {
            final FirebaseUser user = mAuth2.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("users").child(user.getUid()).setValue(stdinfo);

            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("branch").child(dept);
            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                       dataSnapshot.getRef().child(user.getUid()).setValue(userid);
                                                       studentList.put(user.getUid(),stdinfo);
                                                   }

                                                   @Override
                                                   public void onCancelled(@NonNull DatabaseError databaseError) {
                                                       throw databaseError.toException();
                                                   }
                                               });


            //Toast.makeText(AddStudentActivity.this, "Information Stored", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("Exception is",e.toString());
        }

        
        //Toast.makeText(AddStudentActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
        //Log.e("Signed","out");

    }
}