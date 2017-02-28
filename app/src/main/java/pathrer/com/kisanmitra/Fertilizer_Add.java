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
import com.google.firebase.auth.FirebaseAuth;
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
    private EditText mplace;
    private EditText mprice;
    private EditText mphmo;
    private Button msubitb;
    private Uri imageuri = null;

    private static final int GALLERY_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer__add);

        mStorage = FirebaseStorage.getInstance().getReference();
       // mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Fertilizer1");

        mselectimage = (ImageButton) findViewById(R.id.imageSelect);
        mtitle = (EditText) findViewById(R.id.titleField);
        mprice = (EditText) findViewById(R.id.descField);
        mplace = (EditText)findViewById(R.id.FertPlace);
        mphmo = (EditText) findViewById(R.id.fertPHn);

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
                final String desc_val = mprice.getText().toString();
                final String place_val = mplace.getText().toString();
                final String pho_val = mphmo.getText().toString();


                StorageReference filepath = mStorage.child("BlogImages").child(imageuri.getLastPathSegment());
                filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloaduri = taskSnapshot.getDownloadUrl();
                        DatabaseReference newPost = mDatabase.push();

                        newPost.child("title").setValue(title_val);
                        newPost.child("desc").setValue(desc_val);
                        newPost.child("phno").setValue(place_val);
                        newPost.child("place").setValue(pho_val);
                        newPost.child("usrid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                        newPost.child("image").setValue(downloaduri.toString());


                        Toast.makeText(Fertilizer_Add.this,"Upload done",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Fertilizer_Add.this, Fertilizer.class));


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
