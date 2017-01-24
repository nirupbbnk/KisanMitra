package pathrer.com.kisanmitra;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Fertilizer_Add extends AppCompatActivity {

    private ImageButton mselectimage;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private EditText mtitle;
    private EditText mpost;
    private Button msubitb;
    private Uri imageuri = null;

    private static final int GALLERY_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer__add);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");

        mselectimage = (ImageButton) findViewById(R.id.imageSelect);
        mtitle = (EditText) findViewById(R.id.titleField);
        mpost = (EditText) findViewById(R.id.descField);
        msubitb =(Button) findViewById(R.id.submit);


        mselectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallerintent = new Intent(Intent.ACTION_GET_CONTENT);
                gallerintent.setType("image/*");
                startActivityForResult(gallerintent,GALLERY_REQUEST);
            }
        });

        msubitb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String title_val = mtitle.getText().toString();
                final String desc_val = mpost.getText().toString();

                StorageReference filepath = mStorage.child("BlogImages").child(imageuri.getLastPathSegment());
                filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloaduri = taskSnapshot.getDownloadUrl();
                        DatabaseReference newPost = mDatabase.push();

                        newPost.child("title").setValue(title_val);
                        newPost.child("desc").setValue(desc_val);
                        newPost.child("image").setValue(downloaduri.toString());

                        Toast.makeText(Fertilizer_Add.this,"Upload done",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK);
         imageuri = data.getData();
        mselectimage.setImageURI(imageuri);

       /* StorageReference filepath = mStorage.child("Photos").child(imageuri.getLastPathSegment());
        filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Fertilizer_Add.this,"Upload done",Toast.LENGTH_LONG).show();
            }
        });*/
    }
}
