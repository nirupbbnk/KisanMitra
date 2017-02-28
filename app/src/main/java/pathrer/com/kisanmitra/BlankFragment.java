package pathrer.com.kisanmitra;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
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

import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    String TAG = "Log";
    MqttAndroidClient client;
    String msg = "70,30,50";
    static  String topicStr = "Muttu068@github/";
    Integer n,ph,k;
    TextView p;
    PieChart mPieChart;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_blank, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // String npk = getArguments().getString("Vicasin");

        //Toast.makeText(getContext(),npk,Toast.LENGTH_LONG).show();
        //Button btn = (Button) view.findViewById(R.id.trt);
        Button btn1 = (Button)view.findViewById(R.id.fetch);
        p = (TextView)view.findViewById(R.id.piechart1);
        mPieChart = (PieChart)view.findViewById(R.id.piechart);
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(getContext(),"tcp://iot.eclipse.org:1883",
                clientId);
        MqttConnectOptions options = new MqttConnectOptions();


        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(getContext(),"COnnected",Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(getContext(),"Failure",Toast.LENGTH_LONG);
                    Log.d(TAG, "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
/*        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*String topic = topicStr;

                String payload = "return NPK values:"  ;
                try{
                    client.publish(topic,payload.getBytes(),0,false);


                }catch (MqttException e){

                }*//*
            }
        });*/
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //msg = "0,0,0";
                Thread t = new Thread(new Sub());
                //Toast.makeText(getApplicationContext(),"Subscribe intiated",Toast.LENGTH_SHORT).show();

                t.start();
                p.setText(msg);
                String[] npks = msg.split(",");
                String len = String.valueOf(npks.length);
                int a[] = new int[3];
                int i=0;
                for(String s: npks){
                    Log.d("npks:",s);
                    a[i++] = Integer.parseInt(s);
                    Log.d("npki:",String.valueOf(a[i-1]));
                }

                //List<String> npks = Arrays.asList(msg.split(","));

          /*      ph = Integer.parseInt(npks.get(1));
                k = Integer.parseInt(npks.get(2));*/
                mPieChart.clearChart();
                mPieChart.addPieSlice(new PieModel("NITROGEN", a[0], Color.parseColor("#FE6DA8")));
                mPieChart.addPieSlice(new PieModel("POTASSIUM", a[1], Color.parseColor("#56B7F1")));
                mPieChart.addPieSlice(new PieModel("PHOSPHOROUS",a[2], Color.parseColor("#CDA67F")));



                mPieChart.startAnimation();
                //sub(v);
            }
        });





    }
/*    public void sub(View view){
        Thread t = new Thread(new Sub());
        //Toast.makeText(getApplicationContext(),"Subscribe intiated",Toast.LENGTH_SHORT).show();
        t.start();
        //Toast.makeText(getApplicationContext(),"Subscribe Competed",Toast.LENGTH_SHORT).show();
       // p.setText(msg);
    }*/
    private class Sub implements Runnable{

        @Override
        public void run() {
            try {
                Log.d(TAG,"INSIDE RUN 1");
                subscribe();
                Log.d(TAG,"INSIDE RUN 2");
                // System.out.println("msg: " + msg);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        public void subscribe() throws MqttException {
            Log.d(TAG,"INSIDE SuB");
            String topic        = "rambopin";
            int qos             = 1;
            String broker       = "tcp://iot.eclipse.org:1883";
            String clientId     = MqttClient.generateClientId();
            String message = "";
            MemoryPersistence persistence = new MemoryPersistence();

            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            Log.d(TAG,"INSIDE SETCALL");
            sampleClient.setCallback(new SubscribeCallBack());
            Log.d(TAG,"INSIDE SETCALL END");
            sampleClient.connect();
            sampleClient.subscribe(topic, qos);



        }
        class SubscribeCallBack implements MqttCallback {

            @Override
            public void connectionLost(Throwable cause) {

                Toast.makeText(getContext(),"Connection Lost",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d(TAG,"INSIDE SETCALL");
                msg=message.toString();
                Toast.makeText(getContext(),"Connection Established",Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
                //Log.e("Subscribe", " " + message.toString());
                //tv.setText("Temperature: "+message.toString());
                /*if(!msg.equals("the payload"))
                {
                    if(!msg.equals("00000000"))
                        Toast.makeText(getApplicationContext(),"Connection Established",Toast.LENGTH_SHORT).show();
                }
                System.out.println("Data" + msg.toString());*/
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Toast.makeText(getContext(),"Got It",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
