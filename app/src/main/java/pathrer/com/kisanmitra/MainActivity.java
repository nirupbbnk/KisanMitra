package pathrer.com.kisanmitra;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    protected Button login;
    private SignInButton goolebutton;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    protected ProgressDialog pdia;
    private AVLoadingIndicatorView avi;
    CoordinatorLayout rootLayout;
    Button mySnackbar;
    DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListner;
    String uid,dist,ph;
    Query query;
    private  SharedPreferences.Editor editor;
    boolean  firstTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // progressDialog = new ProgressDialog(getApplicationContext());
        SharedPreferences  sharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
        Intent i = getIntent();
        Bundle b = i.getExtras();
         uid = Constans.uid;

         dist = Constans.dist;

         ph = Constans.phone;
        editor=sharedPreferences.edit();
        firstTime =sharedPreferences.getBoolean("first", true);
       // mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("auid");
        //query= mDatabase.orderByKey().equalTo(uid);
       // DatabaseReference db = query.getRef();

        rootLayout = (CoordinatorLayout) findViewById(R.id.main);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
        } else {
            Snackbar snackbar1 = Snackbar
                    .make(findViewById(R.id.main), "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        }
                    });
            snackbar1.show();
        }

        mAuth = FirebaseAuth.getInstance() ;
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    if(firstTime) {

                        Intent intent = new Intent(MainActivity.this, Register.class);
                        startActivity(intent);
                    }
                    else
                    {
                        startActivity(new Intent(MainActivity.this, SelectionActivity.class));
                        finish();
                    }

                    return;
                }
            }
        };
        goolebutton = (SignInButton) findViewById(R.id.googlebutton);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this,"signin failed",Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        goolebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // progressDialog.show();

                signIn();
            }
        });
/*        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectionActivity.class));
            }
        });*/

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // MainActivity.this.pdia = ProgressDialog.show(MainActivity.this, null, "Signing in.... ");


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase


                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {

                // Google Sign In failed, update UI appropriately
                Toast.makeText(MainActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        //progressDialog.dismiss();
                        //writeNewPost(uid,dist,ph,FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });

    }


}
