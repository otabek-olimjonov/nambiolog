package com.example.nambiolog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private Button website, chennel, group, robot, video, exit;
    private AdView mAdView;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (haveNetwork()){
            Toast.makeText(MainActivity.this, "Internet tarmoq ulangan", Toast.LENGTH_SHORT).show();
        } else if (!haveNetwork()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Ushbu ilova internet tarmog'isiz ishlamaydi. Iltimos internet tarmoqni ulang...")
                    .setCancelable(false)
                    .setPositiveButton("ilovada qolish", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setNegativeButton("Ilovadan chiqish", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        MobileAds.initialize(this, "ca-app-pub-9735085940544704~7613617827");
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();




        website = (Button) findViewById(R.id.site);
        chennel = (Button) findViewById(R.id.chennel);
        group = (Button) findViewById(R.id.group);
        robot = (Button) findViewById(R.id.robot);
        video = (Button) findViewById(R.id.video);
        exit = (Button) findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siteactivity();
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                    loadRewardedVideoAd();
                }
            }
        });

        chennel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chennelactivity();
            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupactivity();
            }
        });

        robot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robotactivity();
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoactivity();
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                    loadRewardedVideoAd();
                }
            }
        });


    }
    public void siteactivity(){
            String url = "http://nambiolog.zn.uz/";
            Intent intent = new Intent(this, web.class);
            intent.putExtra("sending", url);
            startActivity(intent);
        }



    public void chennelactivity() {
            String url = "https://t.me/nambiolog/";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }


        public void groupactivity() {
            String url = "https://t.me/nambiolog_group";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }


        public void robotactivity() {
            String url = "https://t.me/nambiolog_bot";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }



        public void videoactivity() {
            String url = "https://mover.uz/channel/biolog/";
            Intent intent = new Intent(this, web.class);
            intent.putExtra("sending", url);
            startActivity(intent);
        }


    private boolean haveNetwork(){
        boolean have_WIFI= false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI"))if (info.isConnected())have_WIFI=true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE DATA"))if (info.isConnected())have_MobileData=true;
        }
        return have_WIFI||have_MobileData;
    }

    @Override
    public void onBackPressed() {
        exit();
    }


    public void exit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Dasturdan chiqib ketmoqchimisiz?")
                .setCancelable(false)
                .setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (mRewardedVideoAd.isLoaded()) {
                            mRewardedVideoAd.show();
                        }
                        finish();
                    }
                })
                .setNegativeButton("Yo'q", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-9735085940544704/6229411635",
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}