package pathrer.com.kisanmitra;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class SelectionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public FirebaseAuth mauth;
    public FirebaseAuth.AuthStateListener mauthlistner;
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private  TextView Name,Email;
    boolean doubleBack = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,mdrawerLayout,R.string.open,R.string.close);
        mdrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView =(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header =  navigationView.getHeaderView(0);
        ImageView im = (ImageView)header.findViewById(R.id.imageViewNav);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Image",Toast.LENGTH_LONG).show();
            }
        });


        mauth = FirebaseAuth.getInstance();
        mauthlistner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(SelectionActivity.this, MainActivity.class));
                    //finish();
                }
            }
        };
        Name = (TextView)header.findViewById(R.id.Name_nav);
        Email = (TextView)header.findViewById(R.id.email_nav);
        Name.setText(mauth.getCurrentUser().getDisplayName());
        Email.setText(mauth.getCurrentUser().getEmail());
        String imu = mauth.getCurrentUser().getPhotoUrl().toString();
        Picasso.with(getApplicationContext()).load(imu).into(im);
        SelectFragment fragment = new SelectFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment,"fragment");
        fragmentTransaction.commit();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mauthlistner);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
       // getMenuInflater().inflate(R.menu.nav_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.logout){
            mauth.signOut();
        }
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        if(item.getItemId() == R.id.account){
           /* MyPostFragment mp = new MyPostFragment();
*//*            Bundle args = new Bundle();
            args.putString("Vuc",auth);
            mp.setArguments(args);*//*
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,mp,"fragment");
            fragmentTransaction.commit();*/
            //String auth = mauth.getCurrentUser().getUid().toString();
            Toast.makeText(getApplicationContext(),"kkk",Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
      switch (id){
          case R.id.account:
              MyPostFragment mp = new MyPostFragment();
              FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
              fragmentTransaction.replace(R.id.frame,mp,"fragment");
              fragmentTransaction.commit();
              break;
          case R.id.price_nav:
              PriceFragment pf = new PriceFragment();
              FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
              fragmentTransaction1.replace(R.id.frame,pf,"fragment");
              fragmentTransaction1.commit();
              break;
          case R.id.home_nav:
              SelectFragment fragment = new SelectFragment();
              FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
              fragmentTransaction2.replace(R.id.frame,fragment,"fragment");
              fragmentTransaction2.commit();


      }
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(doubleBack){
            super.onBackPressed();
            return;
        }
        this.doubleBack=true;
        Toast.makeText(this,"Click back again to exit",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBack=false;
            }
        },2000);
    }
}
