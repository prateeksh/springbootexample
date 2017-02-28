package com.example.prateek.springbootexample.WifiDirect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.prateek.springbootexample.R;

public class WifiDirectActivity extends AppCompatActivity implements DeviceListFragment.DeviceActionListener, WifiP2pManager.ChannelListener {

    private final IntentFilter intentFilter = new IntentFilter();

    public static final String TAG = "wifidirect";

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver = null;
    private boolean retryChannel = false;
    private boolean isWifiP2pEnabled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_direct);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //change in Wifi P2P status
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        //change in list of available peers
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        //State of the Wifi P2P connectivity has changed
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        //Device details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);

        mChannel = mManager.initialize(this, getMainLooper(), null);

        //mReceiver = new WifiDirectBroadcast(mManager, mChannel, this);

        Button button = (Button) findViewById(R.id.discover);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("P2P", "discovering device");
                resetData();
                searchDevices();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        mReceiver = new WifiDirectBroadcast(mManager, mChannel, this);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled){
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }

    public void resetData() {
        DeviceListFragment fragmentList = (DeviceListFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_list);

        DetailFragment fragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detail);
        if (fragmentList != null) {
            fragmentList.clearPeers();
        }
        if (fragment != null) {
            fragment.resetViews();
        }
    }

    public boolean searchDevices(){

        if(!isWifiP2pEnabled){
            Toast.makeText(this, "Error Devices", Toast.LENGTH_SHORT).show();
            return true;
        }

        final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_list);
        fragment.onInitiateDiscovery();
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.v("Success", "Device discovered");
                setData(true);
                Log.v("LOG", "SECOND");

            }

            @Override
            public void onFailure(int reason) {
                Log.v("p2p", "discovering unsuccesfull");
                setData(false);
            }
        });
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setData(boolean isTrue) {
        if (isTrue) {
            Toast.makeText(this, "Divice Found ", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Device Cannot Be Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showDetails(WifiP2pDevice device){
        DetailFragment fragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        fragment.showDetails(device);
    }

    @Override
    public void connect(WifiP2pConfig config){
        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(WifiDirectActivity.this, "Connecting...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i) {
                Toast.makeText(WifiDirectActivity.this, "Retry", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void disconnect(){
        final DetailFragment fragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        fragment.resetViews();
        mManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                fragment.getView().setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int i) {
                Log.v(TAG, "disconnect failed " + i);
            }
        });
    }

    @Override
    public void onChannelDisconnected() {
        // we will try once more
        if (mManager != null && !retryChannel) {
            Toast.makeText(this, "Channel lost. Trying again", Toast.LENGTH_LONG).show();
            resetData();
            retryChannel = true;
            mManager.initialize(this, getMainLooper(), this);
        } else {
            Toast.makeText(this,
                    "Severe! Channel is probably lost premanently. Try Disable/Re-Enable P2P.",
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void cancelDisconnect() {

        /*
         * A cancel abort request by user. Disconnect i.e. removeGroup if
         * already connected. Else, request WifiP2pManager to abort the ongoing
         * request
         */
        if (mManager != null) {
            final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()
                    .findFragmentById(R.id.fragment_list);
            if (fragment.getDevice() == null
                    || fragment.getDevice().status == WifiP2pDevice.CONNECTED) {
                disconnect();
            } else if (fragment.getDevice().status == WifiP2pDevice.AVAILABLE
                    || fragment.getDevice().status == WifiP2pDevice.INVITED) {

                mManager.cancelConnect(mChannel, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(WifiDirectActivity.this, "Aborting connection",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        Toast.makeText(WifiDirectActivity.this,
                                "Connect abort request failed. Reason Code: " + reasonCode,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }
}
