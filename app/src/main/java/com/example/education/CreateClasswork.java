package com.example.education;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateClasswork extends AppCompatActivity {
EditText time,topic,description,marks;
Button create;
TextView title;
String main;
String id;
    private DatabaseReference reference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_classwork);
        time=findViewById(R.id.time);
        topic=findViewById(R.id.topic);
        description=findViewById(R.id.description);
        marks=findViewById(R.id.marks);
        create=findViewById(R.id.create);
        title=findViewById(R.id.title);

       main= ClassWorkFragment.title;
       title.setText(main);
        reference1= FirebaseDatabase.getInstance().getReference("Classwork");
        String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = reference1.push().getKey();

                Classwork classwork = new Classwork(main,topic.getText().toString(),"Due Date: "+time.getText().toString(),"Marks ?/"+marks.getText().toString(),userId,description.getText().toString(),id);
                reference1.child(id).setValue(classwork);
                time.setText("");
                topic.setText("");
                description.setText("");
                marks.setText("");

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(CreateClasswork.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}