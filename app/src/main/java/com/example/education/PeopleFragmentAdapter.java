package com.example.education;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PeopleFragmentAdapter  extends RecyclerView.Adapter<PeopleFragmentAdapter.Viewholder>   {
    Context mContext;
    List<String> Student;

    public PeopleFragmentAdapter(Context mContext, List<String>  Student) {
        this.mContext = mContext;
        this.Student = Student;
    }
    @NonNull
    @Override
    public PeopleFragmentAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_people_item, parent, false);

        return new PeopleFragmentAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleFragmentAdapter.Viewholder holder, int position) {
        String user=Student.get(position);
holder.name.setText(user);
    }

    @Override
    public int getItemCount() {
        return Student.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView name;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
        }
    }


}
