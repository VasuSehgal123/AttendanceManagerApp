package com.example.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

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

public class UserListActivity extends AppCompatActivity {
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Intent i=getIntent();
        final ArrayList<Contact> contacts=new ArrayList<>();
        final RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        final Map<String, Integer> map=new HashMap<>();
        spinner = (ProgressBar)findViewById(R.id.progressBar2);

        // Initialize contacts
        //contacts.add(new Contact("person",10,6));
      //  contacts.add(new Contact("person",11,3));

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("subjects");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    final String sub=snapshot1.getKey();
                    final Integer present= snapshot1.getValue(Integer.class);
                    assert present!=null; assert sub != null;
                    if(present!=-1)
                        map.put(sub,present);
                }
                DatabaseReference count = FirebaseDatabase.getInstance().getReference().child("total");
                count.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                        for(DataSnapshot snapshot1:snapshot2.getChildren())
                        {
                            final Integer total = snapshot1.getValue(Integer.class);
                            final String sub2=snapshot1.getKey();
                            assert total != null;
                           if(map.containsKey(sub2))
                            contacts.add(
                                    new Contact(sub2, total, map.get(sub2)));
                        }
                        ContactsAdapter adapter = new ContactsAdapter(contacts);
                        // Attach the adapter to the recyclerview to populate items
                        rvContacts.setAdapter(adapter);
                        // Set layout manager to position the items
                        rvContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        spinner.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        // Create adapter passing in the sample user data

    }
}