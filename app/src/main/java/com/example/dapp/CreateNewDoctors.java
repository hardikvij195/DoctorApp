package com.example.dapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CreateNewDoctors extends AppCompatActivity {


    Button Create_btn ;
    EditText Name , Email , Password , PhoneNumber , Address;
    Spinner Type ;

    private FirebaseAuth mAuth;
    private DatabaseReference ClassRef , TRef;
    private ProgressDialog mLoginProgress;

    private ArrayList<NameIdClass> TypeNames = new ArrayList<>() ;
    private ArrayList<String> TypeNamesString = new ArrayList<>() ;
    private ArrayAdapter<String> adapterSpinnerType ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_doctors);




        mLoginProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        ClassRef = FirebaseDatabase.getInstance().getReference("Users/") ;

        Create_btn = (Button)findViewById(R.id.Create_Btn_Create_Teacher);
        Name = (EditText)findViewById(R.id.NameTxt_Create_Teacher);
        Email = (EditText)findViewById(R.id.EmailTxt_Create_Teacher);
        Password = (EditText)findViewById(R.id.PasswordTxt_Create_Teacher);
        PhoneNumber = (EditText)findViewById(R.id.PhoneNumberTxt_Create_Teacher);
        Address = (EditText)findViewById(R.id.Address);

        Type = (Spinner)findViewById(R.id.TypeSpinner);
        adapterSpinnerType = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , TypeNamesString);
        Type.setAdapter(adapterSpinnerType);


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


        Create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mLoginProgress.setTitle("Creating Account");
                mLoginProgress.setMessage("Please Wait While We Create New Account");
                mLoginProgress.setCanceledOnTouchOutside(false);
                mLoginProgress.show();


                final String EmailOfUser = Email.getText().toString().trim();
                final String PassOfUser = Password.getText().toString().trim();
                final String NameOfUser = Name.getText().toString().trim().toUpperCase();
                final String PhoneOfUser = PhoneNumber.getText().toString().trim();

                final int T = Type.getSelectedItemPosition();

                if( !EmailOfUser.isEmpty() && !PassOfUser.isEmpty()&& !NameOfUser.isEmpty()&& !PhoneOfUser.isEmpty() && T != -1 && !Address.getText().toString().isEmpty()){


                    mAuth.createUserWithEmailAndPassword(EmailOfUser,PassOfUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){


                                final FirebaseUser useremail = task.getResult().getUser();
                                final String user_id = task.getResult().getUser().getUid();
                                useremail.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            Toast.makeText(CreateNewDoctors.this, "Email Verification Sent" , Toast.LENGTH_SHORT).show();


                                            //TIME ID ---

                                            String UserUid = user_id;
                                            Date date = new Date();  // to get the date
                                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); // getting date in this format
                                            final String formattedDate = df.format(date.getTime());



                                            ClassRef = FirebaseDatabase.getInstance().getReference("Users/Doctor/"+ UserUid) ;
                                            HashMap<String,String> dataMap2 = new HashMap<String, String>();
                                            dataMap2.put("Name" , NameOfUser);
                                            dataMap2.put("Email" , EmailOfUser);
                                            dataMap2.put("Phone" , PhoneOfUser);
                                            dataMap2.put("Address" , Address.getText().toString().trim());
                                            dataMap2.put("Type" , TypeNamesString.get(T));
                                            dataMap2.put("Uid" , UserUid);
                                            dataMap2.put("Id" , formattedDate);
                                            ClassRef.setValue(dataMap2);

                                            ClassRef = FirebaseDatabase.getInstance().getReference("Doctor/"+ formattedDate) ;
                                            ClassRef.setValue(dataMap2);

                                            Name.setText("");
                                            Email.setText("");
                                            Password.setText("");
                                            PhoneNumber.setText("");


                                        }else {

                                            Toast.makeText(CreateNewDoctors.this, "Email Does Not Exist" , Toast.LENGTH_SHORT).show();


                                        }


                                    }
                                });



                                mAuth.signOut();
                                SharedPreferences userinformation = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
                                String email = userinformation.getString("EMAIL" ,"");
                                String pass = userinformation.getString("PASSWORD" , "");
                                mAuth.signInWithEmailAndPassword(email ,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if(task.isSuccessful()){

                                            Toast.makeText(CreateNewDoctors.this, "Account Successfully Created" , Toast.LENGTH_SHORT).show();
                                            mLoginProgress.dismiss();

                                        }else {


                                            Toast.makeText(CreateNewDoctors.this, "Account Successfully Created Errorrr" , Toast.LENGTH_SHORT). show();
                                            mLoginProgress.dismiss();


                                        }


                                    }
                                });




                            }else{

                                try
                                {
                                    throw task.getException();
                                }
                                // if user enters wrong email.
                                catch (FirebaseAuthWeakPasswordException weakPassword)
                                {
                                    Toast.makeText(CreateNewDoctors.this, "Weak Password" ,
                                            Toast.LENGTH_SHORT).show();
                                    mLoginProgress.dismiss();


                                }
                                // if user enters wrong password.
                                catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                {
                                    Toast.makeText(CreateNewDoctors.this, "Malformed Email",
                                            Toast.LENGTH_SHORT).show();
                                    mLoginProgress.dismiss();



                                }
                                catch (FirebaseAuthUserCollisionException existEmail)
                                {
                                    Toast.makeText(CreateNewDoctors.this, "Email Already Exist" ,
                                            Toast.LENGTH_SHORT).show();
                                    mLoginProgress.dismiss();


                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(CreateNewDoctors.this,  e.getMessage() ,
                                            Toast.LENGTH_SHORT).show();
                                    mLoginProgress.dismiss();

                                }

                            }


                        }
                    });

                    //mLoginProgress.dismiss();


                }else{

                    Toast.makeText(CreateNewDoctors.this , "Every Field Needs To Be Filled" , Toast.LENGTH_SHORT).show();
                    mLoginProgress.dismiss();
                }


            }
        });





    }
}
