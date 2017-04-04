package pathrer.com.kisanmitra;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class CropFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference mdatabase;
    private FloatingActionButton floatingActionButton;

    public CropFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crop, container, false);



        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("CropsP");

        recyclerView = (RecyclerView) view.findViewById(R.id.crop_recycle);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.crop_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropAddFragment cropAddFragment = new CropAddFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,cropAddFragment,"fragment");
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Crop,CropVIewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Crop, CropVIewHolder>(
                Crop.class,
                R.layout.crop_row,
                CropVIewHolder.class,
                mdatabase
        ) {
            @Override
            protected void populateViewHolder(CropVIewHolder viewHolder, Crop model, int position) {
                final String post_key = getRef(position).getKey().toString();
                viewHolder.setCropName(model.getCropname());
                Log.d("CropName",model.getCropname() + "\niurl: " + model.getImageurl());
                viewHolder.setPhn(model.getPhno());
                viewHolder.setPlace(model.getPlace());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getActivity().getApplicationContext(),model.getImageurl());
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment mp = new DetailFragment();
                        Bundle args = new Bundle();
                        args.putString("fragment","CropsP");
                        args.putString("Vuc",post_key);
                        mp.setArguments(args);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,mp,"fragment");
                        fragmentTransaction.commit();
                        Toast.makeText(getContext(),post_key,Toast.LENGTH_LONG).show();
                    }
                });

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }




    public static class CropVIewHolder extends RecyclerView.ViewHolder{
        View mview;
        Context context;
        final CharSequence[] items = {" Phone "};
        String phno;
        String key;
        public CropVIewHolder(View itemView) {

            super(itemView);
            mview = itemView;
            context = itemView.getContext();

        }
        public void setCropName(String name){
            TextView na = (TextView)mview.findViewById(R.id.postcropname);
            na.setText(name);
        }
        public void setPrice(String price){
            TextView na = (TextView)mview.findViewById(R.id.cropprice);
            na.setText(price);
        }
        public void setPhn(String phn){
            phno = phn;
            TextView na = (TextView)mview.findViewById(R.id.cropph);
            na.setText(phn);
        }
        public void setPlace(String plcae){
            TextView na = (TextView)mview.findViewById(R.id.cropplace);
            na.setText(plcae);
        }
        public void setImage(Context ctx, String image){
            ImageView postimg = (ImageView) mview.findViewById(R.id.postcropimage);
            Picasso.with(ctx).load(image).into(postimg);
        }
        public void setUID(String uid){

        }
    }

}
