package com.tonghs.opomodoro;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tonghs.opomodoro.util.AlertDialog;
import com.tonghs.opomodoro.util.SettingUtil;
import com.tonghs.opomodoro.util.TipHelper;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActionBarActivity {
    TextView lbl_clock;
    Timer timer;

    final int MIN = 25;
    final int SEC = 0;
    final String SPLIT = ":";
    final int STOPPED = 0;
    final int STARTING = 1;
    int min = MIN;
    int sec = SEC;

    MediaPlayer mMediaPlayer;
    MediaPlayer mClock;
    MediaPlayer mRing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set font family
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/SourceSansPro.ttf");
        lbl_clock = (TextView)findViewById(R.id.lbl_clock);
        lbl_clock.setTypeface(tf);

        mMediaPlayer = MediaPlayer.create(this, R.raw.btn);//初始化MediaPlayer
        mClock = MediaPlayer.create(this, R.raw.tick);//初始化MediaPlayer
        mRing = MediaPlayer.create(this, R.raw.ring);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void btn_start_stop(View v){
        ImageButton btn = (ImageButton)v;
        if (SettingUtil.getSetting(this, SettingUtil.BTN_SOUND)){
            mMediaPlayer.start();//播放声音
        }
        //if stopped
        if (v.getTag().equals("0")){
            //start
            timer = new Timer(true);
            timer.schedule(new MyTimerTask(), 0, 1000);
            btn.setTag("1");
            btn.setImageDrawable(getResources().getDrawable(R.drawable.stop));

        } else { //if started
            //stop
            reset(R.drawable.play);
            lbl_clock.setText(String.format("%02d%s%02d", min, SPLIT, sec));
        }
    }

    public void btn_settingClick(View v){
        if (SettingUtil.getSetting(this, SettingUtil.BTN_SOUND)){
            mMediaPlayer.start();//播放声音
        }
        Intent intent = new Intent();
        intent.setClass(this, SettingsActivity.class);
        this.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.freeze);
    }

    public void btn_aboutClick(View v){
        if (SettingUtil.getSetting(this, SettingUtil.BTN_SOUND)){
            mMediaPlayer.start();//播放声音
        }
        Intent intent = new Intent();
        intent.setClass(this, AboutActivity.class);
        this.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.freeze);
    }

    public void reset(int imgRes){
        timer.cancel();
        min = MIN;
        sec = SEC;
        ImageButton btn = (ImageButton)findViewById(R.id.btn_start_stop);
        btn.setTag("0");
        btn.setImageDrawable(getResources().getDrawable(imgRes));
    }

    final Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            String text = msg.getData().getString("clock");
            lbl_clock.setText(text);

            if (SettingUtil.getSetting(MainActivity.this, SettingUtil.CLOCK_TICK)){
                mClock.start();//播放声音
            }

            if (msg.what == STOPPED){

                reset(R.drawable.restart);
                AlertDialog dialog = new AlertDialog(MainActivity.this);
                dialog.show();

                // vibrate at end
                if (SettingUtil.getSetting(MainActivity.this, SettingUtil.VIBRATE_AT_END)){
                    TipHelper.Vibrate(MainActivity.this, new long[]{0, 80, 80, 80}, false);
                }

                // ring at end
                if (SettingUtil.getSetting(MainActivity.this, SettingUtil.RING_AT_END)){
                    mRing.start();
                }
            }

            super.handleMessage(msg);
        }
    };

    class MyTimerTask extends TimerTask{
        public void run() {
            Message msg = new Message();
            msg.what = getClockText();

            String clockText = String.format("%02d%s%02d", min, SPLIT, sec);
            Bundle bundle = new Bundle();
            bundle.putString("clock", clockText);
            msg.setData(bundle);

            handler.sendMessage(msg);
        }

        public int getClockText(){
            String clockText = null;

            if (sec == 0){
                if (min == 0){
                    return STOPPED;
                } else {
                    sec = 59;
                    min -= 1;
                }
            } else {
                sec -= 1;
            }

            return STARTING;
        }
    }
}
