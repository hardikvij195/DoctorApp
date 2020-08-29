package com.example.dapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class DoctorAppointments extends AppCompatActivity {


    ListView DocApp ;
    private DatabaseReference DRef ;

    private ArrayList<DoctorAppClass> UserNames = new ArrayList<>() ;
    AppListAdapter adap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointments);



        DocApp = (ListView)findViewById(R.id.ListViewDocApp);
        adap = new AppListAdapter(getApplicationContext() , UserNames);
        DocApp.setAdapter(adap);

        SharedPreferences sharedPrefs = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
        String id = sharedPrefs.getString("ID" , "" );

        DRef = FirebaseDatabase.getInstance().getReference("Appointments/" + id) ;
        DRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserNames.clear();
                adap.notifyDataSetChanged();

                if(dataSnapshot.exists()){

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                        String Name = dataSnapshot1.child("Name").getValue().toString();
                        String Phone = dataSnapshot1.child("Num").getValue().toString();
                        String Date = dataSnapshot1.child("Date").getValue().toString();

                        UserNames.add(new DoctorAppClass(Name , Phone ,Date ));
                        adap.notifyDataSetChanged();
                    }

                    Collections.reverse(UserNames);
                    adap.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
