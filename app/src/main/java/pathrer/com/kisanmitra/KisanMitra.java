package pathrer.com.kisanmitra;

import android.app.Application;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import static com.firebase.client.FirebaseApp.*;
import static com.google.firebase.FirebaseApp.getApps;

/**
 * Created by Pathrer on 21-01-2017.
 */
public class KisanMitra extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Firebase.setAndroidContext(this);
        if(!getApps(this).isEmpty()){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}
