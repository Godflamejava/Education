package com.example.education;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.Viewholder> {

    Context mContext;
    List<Answer> answers;
    public AnswerAdapter(Context mContext, List<Answer> answers) {
        this.mContext = mContext;
        this.answers = answers;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.answer_item, parent, false);

        return new AnswerAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
Answer answer=answers.get(position);
holder.name.setText(answer.getSubmitter());
holder.score.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FirebaseDatabase.getInstance().getReference("Classwork").child(ClassWorkAdapter.id).child("marks").setValue(holder.marks.getText().toString());
    }
});

holder.download.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(answer.getLink()));
        mContext.startActivity(browserIntent);
    }
});
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView download;
        EditText marks;
        Button score;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            download=itemView.findViewById(R.id.download);
            marks=itemView.findViewById(R.id.marks);
            score=(Button)itemView.findViewById(R.id.score);

        }
    }
}
