package com.example.dapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {

    Button Add , Logout , DelDoctor , AddType , DelType;
    private FirebaseAuth mAuth;

    DatabaseReference TypeRef , TRef , DoctorRef ;

    private ArrayList<NameIdClass> TypeNames = new ArrayList<>() ;
    private ArrayList<String> TypeNamesString = new ArrayList<>() ;

    private ArrayList<NameIdClass> DoctorNames = new ArrayList<>() ;
    private ArrayList<String> DoctorNamesString = new ArrayList<>() ;

    private ArrayAdapter<String> adapterSpinnerType ;
    private ArrayAdapter<String> adapterSpinnerDoctors ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        Utils.getDatabase();

        Add = (Button)findViewById(R.id.SupBtn);
        DelDoctor = (Button)findViewById(R.id.DelBtn);
        AddType = (Button)findViewById(R.id.AddTypeBtn);
        DelType = (Button)findViewById(R.id.DelTypeBtn);
        Logout = (Button)findViewById(R.id.LogOut);
        mAuth = FirebaseAuth.getInstance();

        adapterSpinnerType = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , TypeNamesString);
        adapterSpinnerDoctors = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , DoctorNamesString);



        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AdminActivity.this , "Sign Out" , Toast.LENGTH_SHORT ).show();
                mAuth.signOut();
                Intent mainIntent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);

            }
        });


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(AdminActivity.this, CreateNewDoctors.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);

            }
        });


        DelDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdminActivity.this)
                        .setCancelable(false);
                View mView = getLayoutInflater().inflate(R.layout.dialog_box_delete_doctor, null);

                final Spinner Sp2 = (Spinner) mView.findViewById(R.id.spinner2);
                final Button canc1 = (Button) mView.findViewById(R.id.button19);
                final Button ok1 = (Button) mView.findViewById(R.id.button20);

                Sp2.setAdapter(adapterSpinnerDoctors);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                ok1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int ps = Sp2.getSelectedItemPosition();

                        if(ps != -1){

                            String Id = DoctorNames.get(ps).getId();
                            String Uid = DoctorNames.get(ps).getName();
                            DoctorRef = FirebaseDatabase.getInstance().getReference("Doctor/" + Id ) ;
                            DoctorRef.removeValue();
                            DoctorRef = FirebaseDatabase.getInstance().getReference("Users/Doctor/" + Uid ) ;
                            DoctorRef.removeValue();
                            adapterSpinnerDoctors.notifyDataSetChanged();

                        }
                    }
                });

                canc1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });








            }
        });


        AddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdminActivity.this)
                        .setCancelable(false);
                View mView = getLayoutInflater().inflate(R.layout.dialog_box_add_type, null);

                final EditText Name = (EditText) mView.findViewById(R.id.NameTxtType);
                final Button canc1 = (Button) mView.findViewById(R.id.button19);
                final Button ok1 = (Button) mView.findViewById(R.id.button20);


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                ok1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if( !Name.getText().toString().isEmpty()){

                            Date date = new Date();  // to get the date
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); // getting date in this format
                            final String formattedDate = df.format(date.getTime());
                            TypeRef = FirebaseDatabase.getInstance().getReference("Types/" + formattedDate ) ;
                            HashMap<String,String> dataMap = new HashMap<String, String>();
                            dataMap.put("Name" , Name.getText().toString().trim().toUpperCase());
                            TypeRef.setValue(dataMap);
                            Toast.makeText(AdminActivity.this , "Type : " + Name.getText().toString() + " Added" , Toast.LENGTH_SHORT).show();

                        }else{

                            Toast.makeText(AdminActivity.this , "No School Added" , Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                canc1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });



            }
        });


        DelType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdminActivity.this)
                        .setCancelable(false);
                View mView = getLayoutInflater().inflate(R.layout.dialog_box_delete_type, null);

                final Spinner Sp2 = (Spinner) mView.findViewById(R.id.spinner2);
                final Button canc1 = (Button) mView.findViewById(R.id.button19);
                final Button ok1 = (Button) mView.findViewById(R.id.button20);


                Sp2.setAdapter(adapterSpinnerType);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                ok1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int ps = Sp2.getSelectedItemPosition();

                        if(ps != -1){

                            String Id = TypeNames.get(ps).getId();
                            TypeRef = FirebaseDatabase.getInstance().getReference("Types/" + Id ) ;
                            TypeRef.removeValue();
                            adapterSpinnerType.notifyDataSetChanged();

                        }
                    }
                });

                canc1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });


            }
        });


        TRef = FirebaseDatabase.getInstance().getReference("Types/") ;
        TRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TypeNames.clear();
                TypeNamesString.clear();

                adapterSpinnerType.notifyDataSetChanged();

                if(dataSnapshot.exists()){

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                        String id = dataSnapshot1.getKey();
                        String Name = dataSnapshot1.child("Name").getValue().toString();
                        TypeNames.add(new NameIdClass(Name , id));
                        TypeNamesString.add(Name);
                        adapterSpinnerType.notifyDataSetChanged();

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DoctorRef = FirebaseDatabase.getInstance().getReference("Doctor/") ;
        DoctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DoctorNames.clear();
                DoctorNamesString.clear();
                adapterSpinnerDoctors.notifyDataSetChanged();

                if(dataSnapshot.exists()){

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                        String id = dataSnapshot1.getKey();
                        String Name = dataSnapshot1.child("Name").getValue().toString();
                        String Uid = dataSnapshot1.child("Uid").getValue().toString();
                        DoctorNames.add(new NameIdClass(Uid , id));
                        DoctorNamesString.add(Name);
                        adapterSpinnerDoctors.notifyDataSetChanged();

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }


}
