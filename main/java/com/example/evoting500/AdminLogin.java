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

public class AdminLogin extends AppCompatActivity {

    FirebaseAuth mAuth;
    private boolean passwordshow = false;
    EditText email, password;
    ImageView showhide;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.useremailet);
        password = findViewById(R.id.passwordet);
        showhide = findViewById(R.id.showhidebtn);
        mDatabase = FirebaseDatabase.getInstance().getReference("Admin");
    }

    public void showHide(View view) {
        if(passwordshow)
        {
            passwordshow = false;
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showhide.setImageResource(R.drawable.show_password);
        }
        else
        {
            passwordshow = true;
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showhide.setImageResource(R.drawable.hide_password);
        }
        password.setSelection(password.length());
    }

    public void verifyUser(View view) {
        String userEmail = email.getText().toString().trim();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AdminLogin.this, "Verification email sent to " + userEmail, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminLogin.this, "Failed to send verification email", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(AdminLogin.this, "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View view) {

        if (email.getText().toString().trim().length() == 0) {
            email.setError("email required");
        } else if (password.getText().toString().trim().length() == 0) {
            password.setError("Password required");
        } else {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (currentUser != null) {
                            String currentUserEmail = currentUser.getEmail();
                            if (currentUserEmail.equals("tallhashhahid@gmail.com")) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    Toast.makeText(AdminLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AdminLogin.this, AdminDashboard.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AdminLogin.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(AdminLogin.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AdminLogin.this, "No info available", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}