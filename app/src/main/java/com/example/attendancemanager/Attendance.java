package com.example.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String branch="a",uid;
  int sub;
    GridLayout grid;
    DatabaseReference ref;
    TextView tv;
    String dateText,subText;
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    Button date;
   Map<String,String> list=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Intent intent = getIntent();
        tv=findViewById(R.id.textView22);


        date=findViewById(R.id.button22);
      Calendar calendar=Calendar.getInstance();
      final int Day =calendar.get(Calendar.DAY_OF_MONTH);
      final int Month= calendar.get(Calendar.MONTH);
        final int Year= calendar.get(Calendar.YEAR);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog=DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String str=dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
                        tv.setText(str);
                        dateText=str;
                    }
                },Year,Month,Day);
                dialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.subject, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.branch, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);




        uid=user.getUid();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner) {
             subText=parent.getItemAtPosition(position).toString().trim();
            if(!subText.equalsIgnoreCase("SelectSubject"))

            sub=Integer.parseInt(subText);



        }
        else if (parent.getId() == R.id.spinner1) {
            branch = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), branch, Toast.LENGTH_SHORT).show();
        grid = findViewById(R.id.gridLayout);
        if (!branch.equalsIgnoreCase("selectbranch")) {

            ref = FirebaseDatabase.getInstance().getReference().child("branch").child(branch);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    int i = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();

                        String b = snapshot.getValue(String.class);

                        list.put(b, key);
                        ToggleButton ed = (ToggleButton) grid.getChildAt(i);
                        ed.setText(b);
                        ed.setTextOff(b);
                        ed.setTextOn(b);


                        ed.setBackgroundResource(R.drawable.toggle_btn);

                        i++;
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void update(View view) {

    increasetotal();

        DatabaseReference  ref1=FirebaseDatabase.getInstance().getReference().child("branch").child(branch);
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0,t;
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    final String uid = snapshot1.getKey();

                    ToggleButton ed = (ToggleButton) grid.getChildAt(i);

                  //  StudentInformation obj = AddStudent.studentList.get(uid);
                     //tv1.setText(uid);

                   if (!ed.isChecked())
                      t=1;
                   else
                        t=0;

                    assert uid != null;
                    DatabaseReference  ref2=FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("subjects").child(subText);
                    final int finalT = t;
                    ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           Integer c=snapshot.getValue(Integer.class);
                           int cn= finalT;
                           if(c!=null)
                               cn=finalT+c;

                           snapshot.getRef().setValue(cn);

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });







                  i++;

                }
                Toast.makeText(Attendance.this, "Attendance Updated!", Toast.LENGTH_SHORT).show();
                Intent replyIntent = new Intent();
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
    public void increasetotal()
    {
        DatabaseReference  ref1=FirebaseDatabase.getInstance().getReference().child("total").child(subText);
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer t=snapshot.getValue(Integer.class);
                assert t!=null;
                int to=t+1;
                snapshot.getRef().setValue(to);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}