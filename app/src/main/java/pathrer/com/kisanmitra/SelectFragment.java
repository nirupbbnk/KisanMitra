package pathrer.com.kisanmitra;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectFragment extends Fragment {
  // protected Button ferti,soil,myf;
    String TAG = "Log";
    //private Button crop;
    static  String topicStr = "Muttu068@github/";
    String msg = "500,100,200";
    MqttAndroidClient client;
    public   Integer x=0;



    protected ListView list;
    protected ImageView img;
    protected RecyclerView mRecyclerView;
    protected CardView ferti,soil,crop;
    /*ListItems list3 = new ListItems("Soil Test");
    ListItems list1 = new ListItems("Crop");
    ListItems list2 = new ListItems("Fertilizer");*/

    public SelectFragment() {
        // Required empty public constructor
        //dbObjects.clear();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_select, container, false);
        soil = (CardView)rootView.findViewById(R.id.soil_test);
        crop = (CardView)rootView.findViewById(R.id.crop_card);
        ferti = (CardView)rootView.findViewById(R.id.Fert_Card);
        soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragment cp = new BlankFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,cp,"fragment");
                fragmentTransaction.addToBackStack("fragment");
                fragmentTransaction.commit();

            }
        });
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropFragment bp = new CropFragment();
                FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.frame,bp,"fragment");
                fragmentTransaction1.addToBackStack("fragment");
                fragmentTransaction1.commit();
            }
        });
        ferti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FertilizerFragment fp = new FertilizerFragment();
                FragmentTransaction fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.frame,fp,"fragmentferti");
                fragmentTransaction2.addToBackStack("fragmentferti");
                fragmentTransaction2.commit();
                /*startActivity(new Intent(getActivity(), Fertilizer.class));
                getActivity().finish();*/
            }
        });
       /* mRecyclerView = (RecyclerView) rootView.findViewById(R.id.sel_rec);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.activity_venues, container, false);
        //layout
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CustomAdapter(dbObjects);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                switch (position){
                    case 0:BlankFragment cp = new BlankFragment();
                        dbObjects.clear();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame,cp,"fragment");
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        dbObjects.clear();
                        CropFragment bp = new CropFragment();
                        FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.frame,bp,"fragment");
                        fragmentTransaction1.commit();
                        break;
                    case 2:
                        dbObjects.clear();
                        startActivity(new Intent(getActivity(), Fertilizer.class));
                        getActivity().finish();


                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/

       // mRecyclerView.

/*        ferti = (Button) v.findViewById(R.id.fertilizer);
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
        });*/

        return  rootView;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }



}
