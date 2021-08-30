package com.example.education;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.Viewholder> {
public static String classroomId;

    Context mContext;
    List<Classroom> classrooms;
    public ClassroomAdapter(){

    }

    public ClassroomAdapter(Context mContext, List<Classroom> classrooms) {
        this.mContext = mContext;
        this.classrooms = classrooms;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_classroom, parent, false);

        return new ClassroomAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomAdapter.Viewholder holder, int position) {
        Classroom classroom = classrooms.get(position);
        holder.section.setText(classroom.getSection());
        holder.teacher.setText(classroom.getTeacher());
        holder.subject.setText(classroom.getSubject());
holder.card.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        classroomId=classrooms.get(position).getId();
        Log.i("mary1",classroomId);

        Intent intent = new Intent(mContext,ClassroomActivity.class);
        mContext.startActivity(intent);
        ((Activity)mContext).finish();
    }
});

    }

    @Override
    public int getItemCount() {
        return classrooms.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView teacher, subject, section;
        ImageView more;
        CardView card;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            teacher = itemView.findViewById(R.id.teacher);
            subject = itemView.findViewById(R.id.subject);
            section = itemView.findViewById(R.id.section);
            card=itemView.findViewById(R.id.card);
            more = itemView.findViewById(R.id.more);
            more.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.more:
                    showPopupMenu(v);
                    break;
            }
        }


        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);


            popupMenu.inflate(R.menu.comment_options_menu);


            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.Unenroll:
                    return true;


                case R.id.edit:
                    return true;
                default:
                    return false;


            }
        }
    }
}