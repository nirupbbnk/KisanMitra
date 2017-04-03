package pathrer.com.kisanmitra;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class Fertilizer extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mdatabase;
    MaterialTapTargetPrompt mFabPrompt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Fertilizer1");

        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFabPrompt = new MaterialTapTargetPrompt.Builder(Fertilizer.this)
                .setTarget(findViewById(R.id.fab))
                .setPrimaryText("Fertilizer add option")
                .setSecondaryText("Tap to add your fertilizer and start selling online")
                .setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener()
                {
                    @Override
                    public void onHidePrompt(MotionEvent event, boolean tappedTarget)
                    {
                        mFabPrompt=null;
                        //Do something such as storing a value so that this prompt is never shown again
                    }

                    @Override
                    public void onHidePromptComplete()
                    {

                    }
                })
                .show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Fertilizer.this, Fertilizer_Add.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog , BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.row,
                BlogViewHolder.class,
                mdatabase
        ) {

            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                final String pos = getRef(position).getKey().toString();

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setPhno(model.getPhno());
                viewHolder.setPlace(model.getPlace());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setEmail(model.getUsrid());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Fragment mp = new DetailFragment();
                        Bundle args = new Bundle();
                        args.putString("fragment","CropsP");
                        args.putString("Vuc",pos);
                        mp.setArguments(args);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,mp,"fragment");
                        fragmentTransaction.commit();*/


                    }

                });
            }
        } ;
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public String email;
        Context context;
        final CharSequence[] items = {" Phone "};
        String phno;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            context = itemView.getContext();


        }

        public void setEmail(String email) {
            this.email = email;
        }

        public  void setTitle(String title){
            TextView posttitle= (TextView) mView.findViewById(R.id.posttitle);
            posttitle.setText(title);
        }

        public  void setDesc(String title){
            TextView postdesc= (TextView) mView.findViewById(R.id.posttext);
            postdesc.setText(title);
        }

        public void setImage(Context ctx,String image){
            ImageView postimg = (ImageView) mView.findViewById(R.id.postimage);
            Picasso.with(ctx).load(image).into(postimg);
        }

        public void setPhno(String title) {
            phno = title;
            TextView postdesc= (TextView) mView.findViewById(R.id.postphoneno);
            postdesc.setText(title);
        }
        public void setPlace(String title) {
            TextView postdesc= (TextView) mView.findViewById(R.id.postplace);
            postdesc.setText(title);
        }
    }
}
