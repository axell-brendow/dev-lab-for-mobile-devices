package com.example.aula07fb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference.child("products").child("1").child("description").setValue("Caneta");
        reference.child("products").child("1").child("value").setValue("1");
        reference.child("products").child("2").child("description").setValue("Lápis");
        reference.child("products").child("2").child("value").setValue("2");

        reference.child("users").child("1").child("name").setValue("Maria");
        reference.child("users").child("1").child("age").setValue("33");
        reference.child("users").child("2").child("name").setValue("João");
        reference.child("users").child("2").child("age").setValue("23");
    }
}
