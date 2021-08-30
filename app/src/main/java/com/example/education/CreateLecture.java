package com.example.education;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateLecture extends AppCompatActivity {
EditText time,link;
Button create;
DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lecture);
        link=findViewById(R.id.link);
        time=findViewById(R.id.time);
        create=(Button)findViewById(R.id.create);
        ref=  FirebaseDatabase.getInstance().getReference("Lectures");

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lecture lecture=new Lecture(link.getText().toString(),time.getText().toString());
              String id = ref.push().getKey();
                ref.child(id).setValue(lecture);
link.setText("");
time.setText("");

            }
        });
    }
}