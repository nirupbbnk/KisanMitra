package pathrer.com.kisanmitra;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;



public class MyPostFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference mdatabase;
    private Query query;
    private RecyclerView mRecycler;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String mypo;

   //  private OnFragmentInteractionListener mListener;

    public MyPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPostFragment newInstance(String param1, String param2) {
        MyPostFragment fragment = new MyPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //mypo = savedInstanceState.getString("Vuc");
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Fertilizer1");
        String usr = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        query = mdatabase.orderByChild("usrid").equalTo(usr);
        View rootView = inflater.inflate(R.layout.fragment_my_post, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.Myfert_recycle);
        mRecycler.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //mypo = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        //Toast.makeText(getContext(),mypo,Toast.LENGTH_LONG).show();
       // query = mdatabase.child("Fertilizer1").child("usrid").equalTo(mypo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog,MyFerViewholder> firebaseRecyclerAdapterMyFert = new FirebaseRecyclerAdapter<Blog, MyFerViewholder>(
                Blog.class,
                R.layout.row,
                MyFerViewholder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(MyFerViewholder viewHolder, Blog model, int position) {
                mypo = model.getUsrid();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setPhno(model.getPhno());
                viewHolder.setPlace(model.getPlace());
                viewHolder.setImage(getContext(),model.getImage());
               /* if(mypo.equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())){

                } else{
                    Toast.makeText(getContext(),"u Don't have any posts",Toast.LENGTH_LONG).show();
                }*/

                //viewHolder.setEmail(model.getUsrid());
            }
        };

        mRecycler.setAdapter(firebaseRecyclerAdapterMyFert);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public  static class MyFerViewholder extends RecyclerView.ViewHolder{
        View mView;
        String phno;
        public MyFerViewholder(View itemView) {

            super(itemView);
            mView = itemView;
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
