package com.example.dapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserDoctorDetails extends AppCompatActivity {


    ListView listViewDoc ;

    private ArrayList<DoctorInfoClass> DocNames = new ArrayList<>() ;

    DoctorListAdap adap;
    DatabaseReference DoctorRef ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_doctor_details);


        listViewDoc = (ListView)findViewById(R.id.listdoc);
        adap = new DoctorListAdap(getApplicationContext() , DocNames);
        listViewDoc.setAdapter(adap);

        DoctorRef = FirebaseDatabase.getInstance().getReference("Doctor/") ;
        DoctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DocNames.clear();
                adap.notifyDataSetChanged();

                if(dataSnapshot.exists()){

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                        String id = dataSnapshot1.getKey();
                        String Name = dataSnapshot1.child("Name").getValue().toString();
                        String Phone = dataSnapshot1.child("Phone").getValue().toString();
                        String Email = dataSnapshot1.child("Email").getValue().toString();
                        String Address = dataSnapshot1.child("Address").getValue().toString();
                        String Type = dataSnapshot1.child("Type").getValue().toString();

                        DocNames.add(new DoctorInfoClass(Name , Phone ,Email , Address , Type , id ));
                        adap.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        listViewDoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent mainIntent = new Intent(UserDoctorDetails.this, UserBookApp.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mainIntent.putExtra("DocId" , DocNames.get(position).getId() );
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);





            }
        });









    }
}
