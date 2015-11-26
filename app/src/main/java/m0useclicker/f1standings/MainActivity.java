package m0useclicker.f1standings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;

public class MainActivity extends android.support.v4.app.FragmentActivity {
    StandingsPagerAdapter standingsPageAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        standingsPageAdapter.notifyDataSetChanged();
    }

    private void setContent(){
        setContentView(R.layout.activity_main);

        if (!isConnected()) {
            showNetworkRequiredDialog();
        } else {
            setView();
        }
    }

    protected void showNetworkRequiredDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unableToConnectMessage)
                .setTitle(R.string.unableToConnectTitle)
                .setCancelable(false)
                .setPositiveButton(R.string.mobileDataButtonText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(i);
                                setView();
                            }
                        }
                )
                .setNeutralButton(R.string.wifiButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(i);
                        setView();
                    }
                })
                .setNegativeButton(R.string.cancelButtonText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private void setView() {
        standingsPageAdapter = new StandingsPagerAdapter(getSupportFragmentManager(), this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(standingsPageAdapter);
        PagerTitleStrip pagerTitleStrip = (PagerTitleStrip) viewPager.findViewById(R.id.pager_title_strip);
        pagerTitleStrip.setBackgroundColor(Color.BLACK);
    }
}
