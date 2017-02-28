package pathrer.com.kisanmitra;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SelectionActivity extends AppCompatActivity {

    public FirebaseAuth mauth;
    public FirebaseAuth.AuthStateListener mauthlistner;
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,mdrawerLayout,R.string.open,R.string.close);
        mdrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        mauth = FirebaseAuth.getInstance();
        mauthlistner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(SelectionActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
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

        }
        return super.onOptionsItemSelected(item);


    }
}
