package com.example.education;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassWorkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassWorkFragment extends Fragment {

    FloatingActionButton add;
public  static  String  title;
RecyclerView recyclerView;
List<Classwork> classworks;
ClassWorkAdapter classWorkAdapter;
    String userId;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClassWorkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssigmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassWorkFragment newInstance(String param1, String param2) {
        ClassWorkFragment fragment = new ClassWorkFragment();
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
        View view= inflater.inflate(R.layout.fragment_classwork, container, false);
        add=view.findViewById(R.id.add);
      userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
getDesignation();
        recyclerView=view.findViewById(R.id.work_recycleView);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        classworks=new ArrayList<>();
        getClasswork();
        recyclerView.setLayoutManager(linearLayoutManager);
       classWorkAdapter=new ClassWorkAdapter(getContext(),classworks);
        recyclerView.setAdapter(classWorkAdapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(getContext(), add);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.classwork_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        switch (menuItem.getItemId()) {
                            case R.id.assignmet:
                                Intent intent = new Intent(getContext(), CreateClasswork.class);
                                title="Assignment";
                                getContext().startActivity(intent);
                                return true;
                            case R.id.test:
                                Intent intent2 = new Intent(getContext(), CreateClasswork.class);
                                title="Test";
                                getContext().startActivity(intent2);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }

        });
    return view;
    }


    public void getDesignation(){
        FirebaseDatabase.getInstance().getReference("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                if(user.getChoice().equals("Teacher"))
                add.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    public void getClasswork(){
        FirebaseDatabase.getInstance().getReference("Classwork").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    {
                        Classwork classwork = dataSnapshot.getValue(Classwork.class);

                        Log.i("mary", classwork.getMarks());
                        classworks.add(classwork);
                        classWorkAdapter.notifyDataSetChanged();
                    }


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}