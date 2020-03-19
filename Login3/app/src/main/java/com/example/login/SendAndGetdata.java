package com.example.login;

import android.net.http.RequestQueue;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
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
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class SendAndGetdata extends AppCompatActivity {
    TextView textView,tv1,tv2;
    List<AsyncTask> tasks=new ArrayList<>();
    public static final String url_show="http://tabeshma.000webhostapp.com/mysites/showparams.php";
    Button post,get ;
    EditText plainText;
    String http_url="http://tabeshma.000webhostapp.com/mysites/passuser.json";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_and_getdata);
        plainText =findViewById(R.id.editText);

        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread=new Thread(new Runnable() {
                    Handler handler =new Handler()
                    {
                        @Override
                        public void handleMessage(@NonNull Message msg) {

                            super.handleMessage(msg);
                            String content =  (String) msg.getData().get("content");
                            try {
                                final JSONObject obj = new JSONObject(content);
                                final JSONArray geodata = obj.getJSONArray("MEMBERS");
                                final JSONObject person = geodata.getJSONObject(0);
                                plainText.setText(person.getString("user"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    };
                    @Override
                    public void run() {
                        String content =getdata();
                        android.os.Message message=new Message();
                        Bundle bundle=new Bundle();
                        bundle.putString ("content",content);
                        message.setData(bundle);
                        handler.sendMessage(message);

                    }
                });
                thread.start();
            }
        });


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
