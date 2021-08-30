package com.example.education;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class SubmittionActivity extends AppCompatActivity {
TextView topic,description,marks;
String url;
    String userId;
ImageView upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submittion);

        topic = findViewById(R.id.topic);
        description = findViewById(R.id.description);
        upload = findViewById(R.id.upload);
        marks=findViewById(R.id.marks);
userId=FirebaseAuth.getInstance().getCurrentUser().getUid();





FirebaseDatabase.getInstance().getReference("Classwork").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

            Log.i("bitch",ClassWorkAdapter.id);
        Classwork classwork=dataSnapshot.getValue(Classwork.class);
        topic.setText(classwork.getTopic());
        description.setText(classwork.getDescription());
        marks.setText(classwork.getMarks());
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});







        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,1212);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1212:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();

                    final ProgressDialog pd = new ProgressDialog(this);
                    pd.setMessage("Uploading");
                    pd.setCanceledOnTouchOutside(false);
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd.setProgress(0);

                    pd.show();
                    final int totalProgressTime = 100;



                    if (uri != null){
Log.i("mary",uri.toString());
                        final String timeStamp = ""+System.currentTimeMillis();
                        final StorageReference filePath = FirebaseStorage.getInstance().getReference("Sumbmittion").child(System.currentTimeMillis() + "." + "pdf");
//            final StorageReference filePath = FirebaseStorage.getInstance().getReference("VideoPosts").child(System.currentTimeMillis() + ".mp4");


                        StorageTask uploadtask = filePath.putFile(uri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                                pd.setProgress((int) progress);
                            }
                        });
                        uploadtask.continueWithTask(new Continuation() {
                            @Override
                            public Object then(@NonNull Task task) throws Exception {
                                if (!task.isSuccessful()){
                                    throw task.getException();
                                }

                                return filePath.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Uri downloadUri = task.getResult();
                                url = downloadUri.toString();
                                FirebaseDatabase.getInstance().getReference("Users").child(userId).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                       User user=snapshot.getValue(User.class);
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Classwork").child(ClassWorkAdapter.id).child("Submitttion").child(userId);
Answer answer=new Answer(user.getUsername(),url);
                                        ref.setValue(answer);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });







//

                                pd.dismiss();

                            }
                        });
                    } else {
                        Toast.makeText(this, "No pdf was selected!", Toast.LENGTH_SHORT).show();
                    }









                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SubmittionActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}