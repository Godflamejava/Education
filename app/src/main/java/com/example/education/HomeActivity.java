package com.example.education;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference1,reference2;
    String userID;
    RecyclerView recyclerView;
    ClassroomAdapter classroomAdapter;
   private List<Classroom> classrooms;
  private  List<String> classroomId2;
    GoogleSignInClient mGoogleSignInClient;

    FloatingActionButton createClassroom,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
user=FirebaseAuth.getInstance().getCurrentUser();
        reference1= FirebaseDatabase.getInstance().getReference("Classrooms");
        reference2= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        recyclerView=findViewById(R.id.classroom);
        classrooms=new ArrayList<>();
        classroomId2=new ArrayList<>();
      //  getClassroomsId();
        getClassrooms();
         getrecycle();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

createClassroom=(FloatingActionButton)findViewById(R.id.floatingActionButton) ;
        logout=(FloatingActionButton)findViewById(R.id.logout) ;
logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
Intent intent = new Intent(HomeActivity.this,MainActivity.class);
startActivity(intent);
finish();
    }
});
        createClassroom.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent(HomeActivity.this, CreateClassroom.class);
startActivity(myIntent);
finish();
    }
});

    }


   public void getClassrooms() {
   //     for (int i = 0; i < classroomId2.size(); i++) {


            reference2.child(userID).child("classroom").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Classroom classroom = new Classroom();
                        classroom = dataSnapshot.getValue(Classroom.class);
                     //   Log.i("mary",classroom.getId() );
                       // Log.i("mary","ritik");

                        classrooms.add(classroom);
                        classroomAdapter.notifyDataSetChanged();

                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    //    }


    }


 public void getrecycle(){
     recyclerView.setLayoutManager(new LinearLayoutManager(this));
     classroomAdapter = new ClassroomAdapter(this, classrooms);
     recyclerView.setAdapter(classroomAdapter);

 }



}