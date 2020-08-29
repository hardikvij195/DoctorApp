package com.example.dapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorActivity extends AppCompatActivity {



    Button App , LogOut ;
    private FirebaseAuth mAuth;
    TextView txt ;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);


        App = (Button)findViewById(R.id.App);
        LogOut = (Button)findViewById(R.id.LogOut);
        mAuth = FirebaseAuth.getInstance();
        txt = (TextView)findViewById(R.id.textView5);

        database = FirebaseDatabase.getInstance();

        SharedPreferences sharedPrefs = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
        //edit.putString("USERID" , user_id );

        String Uid = sharedPrefs.getString("USERID" , "");


        DatabaseReference Data = database.getReference("Users/Doctor/" + Uid);
        Data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists()){


                    String Name = dataSnapshot.child("Name").getValue().toString();
                    String Id = dataSnapshot.child("Id").getValue().toString();
                    SharedPreferences sharedPrefs = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPrefs.edit();
                    edit.putString("ID" , Id );
                    edit.apply();
                    txt.setText("Welcome, " + Name);


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        App.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(DoctorActivity.this, DoctorAppointments.class);
                startActivity(mainIntent);

            }
        });


        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(DoctorActivity.this , "Sign Out" , Toast.LENGTH_SHORT ).show();
                mAuth.signOut();
                Intent mainIntent = new Intent(DoctorActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);



            }
        });




    }
}
