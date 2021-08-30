package com.example.education;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.getInstance;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StreamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StreamFragment extends Fragment {
FloatingActionButton add;
String userId;
List<Lecture> lectures;
RecyclerView recyclerView;
streamAdapter streamAdapter1;
    private   CalendarView calender;
    int dateOfMonth1,year1,month1;
    String testdate="";
TextView addEvent;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StreamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LectureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StreamFragment newInstance(String param1, String param2) {
        StreamFragment fragment = new StreamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_steam, container, false);
        add=view.findViewById(R.id.add);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        getDesignation();
        addEvent=view.findViewById(R.id.AddEvent);
        recyclerView=view.findViewById(R.id.video);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);


        final Date currentTime = getInstance().getTime();
        final long date = currentTime.getTime();
        calender =  view.findViewById(R.id.calender);

        calender.setDate(date);
        calender
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override

                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth)
                            {

                                dateOfMonth1= dayOfMonth;
                                year1=year;
                                month1=month;
                                testdate=testdate+dateOfMonth1+" "+year1+" "+(month1+1);
                                Toast.makeText(getContext(),testdate,Toast.LENGTH_SHORT).show();
                                testdate="";

                            }
                        });



        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (Build.VERSION.SDK_INT >= 14) {
                    Calendar beginTime = Calendar.getInstance();
                    Calendar endTime = Calendar.getInstance();

                    beginTime.set(year1,month1,dateOfMonth1);
                    endTime.set(year1,month1,dateOfMonth1+1);
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, "")
                            .putExtra(CalendarContract.Events.DESCRIPTION, "")
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, "")
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                            .putExtra(Intent.EXTRA_EMAIL, "Add Your EmailAdress");
                    startActivity(intent);
                }

                else {
                    Calendar cal = Calendar.getInstance();
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra("BeginTime", cal.getTimeInMillis());
                    intent.putExtra("AllDay", true);
                    intent.putExtra("Rule", "FREQ=YEARLY");
                    intent.putExtra("EndTime", cal.getTimeInMillis()+60*60*1000);
                    intent.putExtra("Title", "A Test Event from android app");
                    startActivity(intent);
                }




            }
        });




        lectures=new ArrayList<>();
        getLectures();
        recyclerView.setLayoutManager(linearLayoutManager);
        streamAdapter1=new streamAdapter(getContext(),lectures);
        recyclerView.setAdapter(streamAdapter1);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateLecture.class);
                getContext().startActivity(intent);
            }
        });
        return  view;
    }


    public void getDesignation(){
        FirebaseDatabase.getInstance().getReference("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                if(user.getChoice().equals("Teacher"))
                add.setVisibility(View.VISIBLE);
                else
                    add.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

 public void getLectures()
    {
        FirebaseDatabase.getInstance().getReference("Lectures").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
Lecture lecture=dataSnapshot.getValue(Lecture.class);
lectures.add(lecture);
streamAdapter1.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}