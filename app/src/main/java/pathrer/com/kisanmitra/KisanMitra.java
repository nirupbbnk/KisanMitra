package pathrer.com.kisanmitra;

import android.app.Application;

import com.digits.sdk.android.Digits;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

import static com.firebase.client.FirebaseApp.*;
import static com.google.firebase.FirebaseApp.getApps;

/**
 * Created by Pathrer on 21-01-2017.
 */
public class KisanMitra extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "0lXVWEsPBhy6Owewl0qZ4qtX0";
    private static final String TWITTER_SECRET = "VGFKiEcxPiNorJMMsyFZ9ibTdA0OUGfg6fkhJTuvvRho1Y1fuQ";


    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());

        //Firebase.setAndroidContext(this);
        if(!getApps(this).isEmpty()){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}
