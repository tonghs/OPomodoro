package com.tonghs.opomodoro;

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
import android.widget.CompoundButton;
import android.widget.Switch;

import com.tonghs.opomodoro.util.SettingUtil;

public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Switch s_btn_sound = (Switch)findViewById(R.id.s_btn_sound);
        s_btn_sound.setTag(SettingUtil.BTN_SOUND);
        s_btn_sound.setOnCheckedChangeListener(switchHandler);

        Switch s_ring = (Switch)findViewById(R.id.s_ring);
        s_ring.setTag(SettingUtil.RING_AT_END);
        s_ring.setOnCheckedChangeListener(switchHandler);

        Switch s_vibrate = (Switch)findViewById(R.id.s_vibrate);
        s_vibrate.setTag(SettingUtil.VIBRATE_AT_END);
        s_vibrate.setOnCheckedChangeListener(switchHandler);

        Switch s_tick = (Switch)findViewById(R.id.s_tick);
        s_tick.setTag(SettingUtil.CLOCK_TICK);
        s_tick.setOnCheckedChangeListener(switchHandler);

        s_btn_sound.setChecked(SettingUtil.getSetting(this, SettingUtil.BTN_SOUND));
        s_ring.setChecked(SettingUtil.getSetting(this, SettingUtil.RING_AT_END));
        s_vibrate.setChecked(SettingUtil.getSetting(this, SettingUtil.VIBRATE_AT_END));
        s_tick.setChecked(SettingUtil.getSetting(this, SettingUtil.CLOCK_TICK));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public CompoundButton.OnCheckedChangeListener switchHandler = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String name = buttonView.getTag().toString();
            SettingUtil.setSetting(SettingsActivity.this, name, isChecked);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
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
            View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
            return rootView;
        }
    }

    public void btn_returnClick(View v){
        this.finish();
        overridePendingTransition(R.anim.freeze, R.anim.slide_out_right);
    }

}
