package pathrer.com.kisanmitra;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SelectionActivity extends AppCompatActivity {

    protected Button ferti;
    private Button logout;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthlistner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        ferti = (Button) findViewById(R.id.fertilizer);
        ferti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectionActivity.this, Fertilizer.class));
            }
        });

        mauth = FirebaseAuth.getInstance();
        mauthlistner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(SelectionActivity.this, MainActivity.class));
                }
            }
        };

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mauth.signOut();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mauthlistner);
    }
}
