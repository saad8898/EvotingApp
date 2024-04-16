package com.example.evoting500;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    private boolean passwordshow = false, conPassShow = false;
    EditText password, conPassword, fullname, cnic, email, phone;
    ImageView passShowhide, conPassShowhide;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        password = findViewById(R.id.passwordet);
        conPassword = findViewById(R.id.conPasswordet);
        passShowhide = findViewById(R.id.showhidebtn);
        conPassShowhide = findViewById(R.id.conPassShowbtn);
        fullname = findViewById(R.id.fullnameEt);
        cnic = findViewById(R.id.cnicEt);
        email = findViewById(R.id.emailEt);
        phone = findViewById(R.id.phoneEt);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("UsersDB");

    }

    public void signup(View view) {
        Intent intent = new Intent(Signup.this, Login.class);
        startActivity(intent);
    }

    public void showHidePass(View view) {
        if(passwordshow)
        {
            passwordshow = false;
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passShowhide.setImageResource(R.drawable.show_password);

        }
        else
        {
            passwordshow = true;
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passShowhide.setImageResource(R.drawable.hide_password);
        }
        password.setSelection(password.length());
    }

    public void showHideConPass(View view) {
        if(conPassShow)
        {
            conPassShow = false;
            conPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            conPassShowhide.setImageResource(R.drawable.show_password);

        }
        else
        {
            conPassShow = true;
            conPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            conPassShowhide.setImageResource(R.drawable.hide_password);
        }
        conPassword.setSelection(conPassword.length());
    }


    public void register(View view) {
        if(fullname.getText().toString().trim().length() == 0)
        {
            fullname.setError("You must enter your name");
        }

        else if(cnic.getText().toString().trim().length() == 0)
        {
            cnic.setError("CNIC required");
        }
        else if(cnic.getText().toString().trim().length() < 13)
        {
            cnic.setError("invalid length");
        }

        else if(email.getText().toString().trim().length() == 0)
        {
            email.setError("Email required");
        }

        else if(phone.getText().toString().trim().length() == 0)
        {
            phone.setError("Phone number required");
        }

        else if(password.getText().toString().trim().length() == 0)
        {
            password.setError("Password required");
        }

        else if(conPassword.getText().toString().trim().length() == 0)
        {
            conPassword.setError("Enter your confirmation password");

        }
        else if(!conPassword.getText().toString().equals(password.getText().toString()))
        {
            Toast.makeText(Signup.this, "passwords do not match", Toast.LENGTH_SHORT).show();
        }

        else
        {
            String name = fullname.getText().toString();
            String cnicNo = cnic.getText().toString();
            String userEmail = email.getText().toString();
            String userPhone = phone.getText().toString();
            String userPassword = password.getText().toString();
            createUser(userEmail, userPassword, name, cnicNo, userPhone);
            Toast.makeText(this, "Request Submitted", Toast.LENGTH_SHORT).show();
        }

    }

    private void createUser(String email, String password, String name, String cninNo, String userPhone) {

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(Signup.this,
                                                "User Created, please verify your email.", Toast.LENGTH_SHORT).show();
                                        saveUserDataToDatabase(user.getUid(),name, cninNo, email, userPhone);
                                        Intent intent = new Intent(Signup.this, Login.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(Signup.this,
                                                "Failed to send email verification.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(Signup.this, "Failed to create user: "
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void saveUserDataToDatabase(String userId, String name, String cnic, String email, String phone) {
        UserData userData = new UserData(name, cnic, email, phone);
        userData.setRequestStatus("Pending");
        userData.setHasVoted("false");
        mDatabase.child(userId).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Signup.this, "Data saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(Signup.this, "not saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}