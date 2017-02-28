package pathrer.com.kisanmitra;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectFragment extends Fragment {
   protected Button ferti,soil,myf;
    String TAG = "Log";
    private Button crop;
    static  String topicStr = "Muttu068@github/";
    String msg = "500,100,200";
    MqttAndroidClient client;
    public   Integer x=0;

    public SelectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_select, container, false);
        ferti = (Button) v.findViewById(R.id.fertilizer);
        crop = (Button) v.findViewById(R.id.crop);
        soil = (Button) v.findViewById(R.id.soiltest) ;
        myf = (Button)v.findViewById(R.id.myfertpost);
        soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BlankFragment cp = new BlankFragment();

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,cp,"fragment");
                fragmentTransaction.commit();
            }
        });
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropFragment cp = new CropFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,cp,"fragment");
                fragmentTransaction.commit();
            }
        });
        ferti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Fertilizer.class));
                getActivity().finish();

            }
        });
        myf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPostFragment mp = new MyPostFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,mp,"fragment");
                fragmentTransaction.commit();

            }
        });

        return  v;
    }



}
