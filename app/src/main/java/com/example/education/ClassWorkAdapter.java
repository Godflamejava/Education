package com.example.education;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ClassWorkAdapter  extends RecyclerView.Adapter<ClassWorkAdapter.Viewholder>  {
    Context mContext;
   static String id;
   public static String name;
    List<Classwork> classworks;
    public ClassWorkAdapter(Context mContext, List<Classwork> classworks) {
        this.mContext = mContext;
        this.classworks = classworks;
    }

    @NonNull
    @Override
    public ClassWorkAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.work_item, parent, false);

        return new ClassWorkAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Classwork classwork=classworks.get(position);
        id=classwork.getUid();

        holder.marks.setText(classwork.getMarks());
holder.time.setText(classwork.getDate());
holder.title.setText(classwork.getTitle());
holder.topic.setText(classwork.getTopic());

holder.classwork1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
     String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
     if(!userId.equals(classwork.getCid()))
        {
            Intent intent=new Intent(mContext,SubmittionActivity.class);
            id=classwork.getUid();
            mContext.startActivity(intent);
            ((Activity)mContext).finish();
        }
        else{
         Intent intent=new Intent(mContext,AnswersActivity.class);
         id=classwork.getUid();

         mContext.startActivity(intent);
         ((Activity)mContext).finish();
        }
    }
});

    }



    @Override
    public int getItemCount() {
        return classworks.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView marks,time,topic,title;
        CardView classwork1;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
          topic=itemView.findViewById(R.id.topic);
            marks=itemView.findViewById(R.id.marks);
            time=itemView.findViewById(R.id.date);
            title=itemView.findViewById(R.id.title);
            classwork1=itemView.findViewById(R.id.classwork1);

        }
    }
}
