package com.example.dapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
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

public class UserMainPage extends AppCompatActivity {


    Button DoctrList , App , Profile , LogOut ;
    private FirebaseAuth mAuth;

    TextView txt ;
    DatabaseReference UserRef ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);



        txt = (TextView)findViewById(R.id.textViewWelcomeUser);
        DoctrList = (Button)findViewById(R.id.DoctorList);
        App = (Button)findViewById(R.id.App);
        Profile = (Button)findViewById(R.id.Profile);
        LogOut = (Button)findViewById(R.id.LogOut);
        mAuth = FirebaseAuth.getInstance();



        SharedPreferences sharedPrefs = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
        final String Uid = sharedPrefs.getString("USERID" , "");


        UserRef = FirebaseDatabase.getInstance().getReference("Users/Use/" + Uid + "/Name/") ;
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists()){


                    UserRef = FirebaseDatabase.getInstance().getReference("Users/Use/" + Uid + "/") ;

                    UserRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            if(dataSnapshot.exists()) {

                                String Name = dataSnapshot.child("Name").getValue().toString();
                                txt.setText("Welcome, " + Name);


                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        DoctrList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(UserMainPage.this, UserDoctorDetails.class);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });


        App.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(UserMainPage.this, UserAppointments.class);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });


        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(UserMainPage.this, UserProfilePage.class);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(UserMainPage.this , "Sign Out" , Toast.LENGTH_SHORT ).show();
                mAuth.signOut();
                Intent mainIntent = new Intent(UserMainPage.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });


    }
}
