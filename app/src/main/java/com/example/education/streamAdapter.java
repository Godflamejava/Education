package com.example.education;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class streamAdapter extends RecyclerView.Adapter<streamAdapter.Viewholder> {
    Context mContext;
    List<Lecture> lectures;
    public streamAdapter(Context mContext, List<Lecture>  lectures) {
        this.mContext = mContext;
        this.lectures = lectures;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.link_item, parent, false);

        return new streamAdapter.Viewholder(view);    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
Lecture lecture=lectures.get(position);
holder.link.setText(lecture.getLink());
holder.time.setText(lecture.getTime());
    }

    @Override
    public int getItemCount() {
        return lectures.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView link,time;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            link=itemView.findViewById(R.id.link);
            time=itemView.findViewById(R.id.time);
        }
    }
}
