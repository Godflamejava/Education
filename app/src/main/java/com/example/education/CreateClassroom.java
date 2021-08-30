package com.example.education;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.HashMap;

public class CreateClassroom extends AppCompatActivity {
EditText classroomId,subject,section,teacher;
Button create,join;
EditText newClassroomId;
    private DatabaseReference reference1,reference2;
    private FirebaseUser user;
    private String userID;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_classrom);
classroomId=findViewById(R.id.classroon_id);
subject=findViewById(R.id.subject);
section=findViewById(R.id.section);
teacher=findViewById(R.id.teacher);
create=(Button)findViewById(R.id.create);
join=(Button)findViewById(R.id.join);
        userID=FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference1= FirebaseDatabase.getInstance().getReference("Classrooms");
        reference2= FirebaseDatabase.getInstance().getReference("Users");
newClassroomId=findViewById(R.id.idToshare);





create.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



id = reference1.push().getKey();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("teacher", teacher.getText().toString());
        map.put("subject", subject.getText().toString());
        map.put("section",section.getText().toString());
        map.put("user",null);
teacher.setText("");
subject.setText("");
section.setText("");

reference1.child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {

reference2.child(userID).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
User user=new User();
user=snapshot.getValue(User.class);
        reference1.child(id).child("user").child(reference1.push().getKey()).setValue(user);
        newClassroomId.setText(id);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
        reference2.child(userID).child("classroom").child(reference2.push().getKey()).setValue(map);

        Toast.makeText(CreateClassroom.this,"Done",Toast.LENGTH_LONG);

       }
});


    }
});




join.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.i("bitch",classroomId.getText().toString());
        if(classroomId.getText().toString().equals(""))
            Toast.makeText(CreateClassroom.this,"Check the ClassroomId",Toast.LENGTH_LONG);
else {

    reference1.child(classroomId.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists())
            { Classroom classroom=new Classroom();
            classroom=snapshot.getValue(Classroom.class);
                Classroom finalClassroom = classroom;
                reference2.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user=new User();
                        user=snapshot.getValue(User.class);
                        reference1.child(classroomId.getText().toString()).child("user").child(reference1.push().getKey()).setValue(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                reference2.child(userID).child("classroom").child(reference2.push().getKey()).setValue(finalClassroom);

            }
            else
                Toast.makeText(CreateClassroom.this, "ClassroomId is Wrong", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
            Intent myIntent = new Intent(CreateClassroom.this, HomeActivity.class);
            startActivity(myIntent);
            finish();
        }

    }
});






    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(CreateClassroom.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }





}