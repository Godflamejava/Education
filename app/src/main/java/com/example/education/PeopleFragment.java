package com.example.education;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PeopleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeopleFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference1,reference2;
    String userID;
    RecyclerView recyclerView;
PeopleFragmentAdapter peopleFragmentAdapter;
private List<String> nameList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PeopleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PeopleFragment newInstance(String param1, String param2) {
        PeopleFragment fragment = new PeopleFragment();
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
      View view =  inflater.inflate(R.layout.fragment_people, container, false);
recyclerView=view.findViewById(R.id.online_student);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        nameList=new ArrayList<>();
        getNameList();
        recyclerView.setLayoutManager(linearLayoutManager);
        peopleFragmentAdapter=new PeopleFragmentAdapter(getContext(),nameList);
        recyclerView.setAdapter(peopleFragmentAdapter);


        return view;
    }

  public void getNameList(){
        Log.i("mary",ClassroomAdapter.classroomId);
      FirebaseDatabase.getInstance().getReference("Classrooms").child(ClassroomAdapter.classroomId).child("user").addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              nameList.clear();
              for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            User user=new User();
                  user = dataSnapshot.getValue(User.class);
              //    Log.i("mary",classroom.getId() );
              //    Log.i("mary","ritik");

                  nameList.add(user.getUsername());
                  peopleFragmentAdapter.notifyDataSetChanged();

              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

  }


}