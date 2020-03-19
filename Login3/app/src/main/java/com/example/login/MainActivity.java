package com.example.login;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    EditText password;
    EditText user;
    CheckBox checkBox;
    List<AsyncTask> tasks=new ArrayList<>();
    public static final String url_show="http://tabeshma.000webhostapp.com/mysites/showparams.php";
    String http_url="http://tabeshma.000webhostapp.com/mysites/passuser.json";

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        password=findViewById(R.id.password);
        user=findViewById(R.id.User);
        checkBox=findViewById(R.id.checkBox);
        progressBar=findViewById(R.id.pb);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                {
                    if (isChecked) {
                        // show password
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        checkBox.setText("Hide");
                    } else {
                        // hide password
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        checkBox.setText("Show");

                    }
                }

            }
        });

        findViewById(R.id.sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(user.getText().equals("")||password.getText().equals("")){

                        Toast.makeText(getApplicationContext(),"You can not sign in with empty fields",Toast.LENGTH_LONG).show();



                    }
                    else{
                        utils.RequestData requestData1=new utils.RequestData(url_show,"POST");
                        requestData1.setParameter("username",user.getText().toString().trim());
                        requestData1.setParameter("password",password.getText().toString().trim());

                        new  Mytasks().execute(requestData1);

                    }
//                Thread thread=new Thread(new Runnable() {
//                    Handler handler =new Handler()
//                    {
//                        @Override
//                        public void handleMessage(@NonNull Message msg) {
//
//                            super.handleMessage(msg);
//                            String content =  (String) msg.getData().get("content");
//                            try {
//                                final JSONObject obj = new JSONObject(content);
//                                final JSONArray geodata = obj.getJSONArray("MEMBERS");
//                                final JSONObject person = geodata.getJSONObject(0);
//
//                                if(person.getString("user").equals("true")){
//                                    startActivity(new Intent(getApplicationContext(),Webview.class));
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//
//                        }
//                    };
//                    @Override
//                    public void run() {
//                        String content =getdata();
//                        android.os.Message message=new Message();
//                        Bundle bundle=new Bundle();
//                        bundle.putString ("content",content);
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//
//                    }
//                });
//                thread.start();





            }
        });
    }



    public class Mytasks extends AsyncTask<utils.RequestData,Void ,String>
    {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

            tasks.add(this);
        }

        @Override
        protected String doInBackground(utils.RequestData... requestData) {
            utils.RequestData  reqdata= requestData[0];
            return utils.getDataUrlConnection(reqdata);
        }

        @Override
        protected void onPostExecute(String s) {
            if(s==null ) s="null";
            progressBar.setVisibility(View.INVISIBLE);


            Toast.makeText(getApplicationContext(),s+"Secssusful",Toast.LENGTH_LONG).show();

            tasks.remove(this);
        }
    }




    public String getdata()
    {
        HttpClient client=new DefaultHttpClient();
        HttpGet method=new HttpGet(http_url);
        try
        {
            HttpResponse response=client.execute(method);
            String content=utils.inputStreemtoString(response.getEntity().getContent());
            Log.i("getData",content);
            return content;

        }catch (IOException e)
        {

        }
        return null;

    }



}











