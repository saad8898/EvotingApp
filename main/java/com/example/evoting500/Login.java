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

public class Login extends AppCompatActivity {

    private boolean passwordshow = false;
    EditText password, email;
    ImageView showhide;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password = findViewById(R.id.passwordet);
        email = findViewById(R.id.useremailet);
        showhide = findViewById(R.id.showhidebtn);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signup(View view) {
        Intent intent = new Intent(Login.this, Signup.class);
        startActivity(intent);
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

    public void login(View view) {

        if(email.getText().toString().trim().length() == 0)
        {
            email.setError("email required");
        }
        else if(password.getText().toString().trim().length() == 0)
        {
            password.setError("Password required");
        }

        else
        {
            mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        if(mAuth.getCurrentUser().isEmailVerified())
                        {
                            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, UserDashboard.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}