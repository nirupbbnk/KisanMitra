package pathrer.com.kisanmitra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Register extends AppCompatActivity {
    private  SharedPreferences.Editor editor;
    boolean  firstTime;
    private IntentIntegrator qrScan;
    protected String uid,name,gender,yearOfBirth,careOf,villageTehsil,postOffice,district,state,postCode,rawString;
    private DatabaseReference mDatabase;
// ...
public String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SharedPreferences  sharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        Button scan = (Button)findViewById(R.id.adhar_scan);

        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // TODO: associate the session userID with your user model
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();
                phone = phoneNumber;

            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });
        qrScan = new IntentIntegrator(this);
        qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        qrScan.setPrompt("Scan a Aadharcard QR Code");

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                String x = result.getContents();
                Toast.makeText(getApplicationContext(),x,Toast.LENGTH_LONG).show();

                processString(x);
                DatabaseReference newPost = mDatabase.push();
                newPost.child("auid").setValue(uid);
                newPost.child("phone").setValue(phone);
                newPost.child("dist").setValue(district);
                newPost.child("Fuid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                Intent i = new Intent(Register.this,SelectionActivity.class);
                Bundle b=new Bundle();
                /*b.putString("uid",uid);
                b.putString("phone",phone);
                b.putString("dist",district);
                i.putExtras(b);*/
                Constans.uid = uid;
                Constans.phone = phone;
                Constans.dist = district;
                startActivity(i);
                editor.putBoolean("first",false);
                editor.apply();
                finish();


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    protected void processString(String input){
        rawString = input;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document dom;
        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            if (input.startsWith("</?")) {
                input = input.replaceFirst("</\\?", "<?");
            }
            // Replace <?xml...?"> with <?xml..."?>
            input = input.replaceFirst("^<\\?xml ([^>]+)\\?\">", "<?xml $1\"?>");
            //parse using builder to get DOM representation of the XML file
            dom = db.parse(new ByteArrayInputStream(input.getBytes("UTF-8")));

        }catch (ParserConfigurationException | SAXException | IOException e) {
            dom = null;
        }
        if(dom != null){
            Node node = dom.getChildNodes().item(0);
            NamedNodeMap attributes = node.getAttributes();

            uid = getAttributeOrEmptyString(attributes, "uid");
            name = getAttributeOrEmptyString(attributes, "name");
            gender = getAttributeOrEmptyString(attributes,"vtc");
            postCode = getAttributeOrEmptyString(attributes,"pc");
            district = getAttributeOrEmptyString(attributes,"dist");

        }


    }
    private String getAttributeOrEmptyString(NamedNodeMap attributes, String attributeName) {
        Node node = attributes.getNamedItem(attributeName);
        if (node != null) {
            return node.getTextContent();
        } else {
            return "";
        }
    }



}
