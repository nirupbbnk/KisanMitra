package pathrer.com.kisanmitra;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class CropAddFragment extends Fragment {

    private ImageButton mselectimage;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private EditText mtitle;
    private EditText mpost;
    private EditText mprice;
    private EditText mphmo;
    private Button msubitb;
    private Uri imageuri = null;

    private static final int GALLERY_REQUEST = 1;
    public CropAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crop_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStorage = FirebaseStorage.getInstance().getReference();
        // mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("CropsP");

        mselectimage = (ImageButton) view.findViewById(R.id.imageSelect_c);
        mtitle = (EditText)view. findViewById(R.id.titleField_c);
        mpost = (EditText) view.findViewById(R.id.descField_c);
        mprice = (EditText)view. findViewById(R.id.price_c);
        mphmo = (EditText)view. findViewById(R.id.pro_c);
        msubitb =(Button) view.findViewById(R.id.submit_c);


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
                final String price = mprice.getText().toString();
                final String ph  = mphmo.getText().toString();

                StorageReference filepath = mStorage.child("CropImages").child(imageuri.getLastPathSegment());
                filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloaduri = taskSnapshot.getDownloadUrl();
                        DatabaseReference newPost = mDatabase.push();

                        newPost.child("cropname").setValue(title_val);
                        newPost.child("place").setValue(desc_val);
                        newPost.child("price").setValue(price);
                        newPost.child("phno").setValue(ph);

                        newPost.child("imageurl").setValue(downloaduri.toString());


                        Toast.makeText(getActivity(),"Upload done",Toast.LENGTH_LONG).show();
                        CropFragment cp = new CropFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,cp,"fragment");
                        fragmentTransaction.commit();


                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == getActivity().RESULT_OK);
        imageuri = data.getData();
        mselectimage.setImageURI(imageuri);
    }
}
