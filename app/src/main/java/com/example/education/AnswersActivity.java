package com.example.education;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnswersActivity extends AppCompatActivity {
RecyclerView recyclerView;
List<Answer> answers;
AnswerAdapter answerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        recyclerView =findViewById(R.id.answer);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AnswersActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        answers=new ArrayList<>();
        getAnswers();
        recyclerView.setLayoutManager(linearLayoutManager);
        answerAdapter=new AnswerAdapter(AnswersActivity.this,answers);
        recyclerView.setAdapter(answerAdapter);

    }
public void getAnswers(){
    FirebaseDatabase.getInstance().getReference("Classwork").child(ClassWorkAdapter.id).child("Submitttion").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                Answer answer=dataSnapshot.getValue(Answer.class);

                answers.add(answer);
                answerAdapter.notifyDataSetChanged();}

        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(AnswersActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }


}