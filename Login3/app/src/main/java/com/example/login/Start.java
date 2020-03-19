package com.example.login;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Start extends AppCompatActivity {
    boolean wifi =false;
    boolean mobiledata=false;
    int counter=0;
    private Button button;
    private ProgressBar progressBar;
     ImageView image;
    AnimationDrawable animation;
    TextView editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        image=findViewById(R.id.connection);
        image.setBackgroundResource(R.drawable.anime);

        animation=(AnimationDrawable) image.getBackground();

        progressBar=findViewById(R.id.progressBar);
        editText=findViewById(R.id.tconnection);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                connection();
                if (wifi == true || mobiledata == true) {
                     editText.setText("Connected");
                     progressBar.setVisibility(View.INVISIBLE);
                     image.setBackgroundResource(R.drawable.s5);
                    final Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(Start.this, MainActivity.class));

                        }
                    },4000);


                } else {
                    image.setBackgroundResource(R.drawable.nowifi);

                    editText.setText("Try again");
                    findViewById(R.id.refresh).setVisibility(View.VISIBLE);
                    findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Start.this, Start.class));


                        }
                    });
                    Snackbar.make(findViewById(R.id.myCoordinatorLayout), "NO CONNECTION FOUND ",
                            5000)
                            .show();

                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        }, 9000);
    }


    public void connection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos)
        {
            if(info.getTypeName().equalsIgnoreCase("wifi")){
                if(info.isConnected()) wifi=true;
            }
            if(info.getTypeName().equalsIgnoreCase("Mobile")){
                if (info.isConnected()) mobiledata=true;
            }


        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animation.start();
    }
}
