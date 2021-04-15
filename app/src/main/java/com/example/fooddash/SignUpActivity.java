package com.example.fooddash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fooddash.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference users;

    EditText usernameText, passwordText;
    Button loginButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //gets database to be able to be used
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        usernameText = (EditText)findViewById(R.id.usernameEditText);
        passwordText = (EditText)findViewById(R.id.passwordEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(usernameText.getText().toString(), passwordText.getText().toString());
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creates new user
                final User user = new User(usernameText.getText().toString(),passwordText.getText().toString(),"");

                //adjusts database to create the new user
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(user.getEmail()).exists())
                            Toast.makeText(SignUpActivity.this, "Username already signed up!", Toast.LENGTH_SHORT).show();
                        else{
                            users.child(user.getEmail()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void signIn(final String username, final String password){
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // if the user doesnt exist wrong info
                if(snapshot.child(username).exists()){
                    if(!username.isEmpty()){
                        User login = snapshot.child(username).getValue(User.class);

                        //if the user is an admin allows them to manipulate restaurant
                        if(login.getEmail().equals("admin"))
                        {
                            if(login.getPassword().equals("admin"))
                                openAdminActivity();
                        }
                        else if(login.getPassword().equals(password)){
                            Home.startup = true;
                            openHomeActivity(username);
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "Password is wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                    Toast.makeText(SignUpActivity.this, "Username not found!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openHomeActivity(String username){
        Intent intent = new Intent(this, Home.class);
        Home.currentUser = username;
        startActivity(intent);
    }
    private void openAdminActivity(){
        Intent intent = new Intent(this, AdminPanel.class);
        startActivity(intent);
    }

}