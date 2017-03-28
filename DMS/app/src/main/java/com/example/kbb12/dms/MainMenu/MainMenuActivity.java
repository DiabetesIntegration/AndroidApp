package com.example.kbb12.dms.mainMenu;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.bluetooth.BluetoothService;
import com.example.kbb12.dms.nfc.ReadNfcTask;
import com.example.kbb12.dms.startUp.ModelHolder;
import com.example.kbb12.dms.takeInsulin.TakeInsulin;

public class MainMenuActivity extends AppCompatActivity {


    private NfcAdapter mAdapter;
    private static final String TAG = "MainMenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ImageButton takeInsulinButton = (ImageButton) findViewById(R.id.takeInsulinButton);
        takeInsulinButton.setOnClickListener(new TakeInsulinLauncher(this));
        ModelHolder.model.logModels();
        if(getIntent().getBooleanExtra("NotificationLaunch",false)){
            Intent nextIntent = new Intent(this, TakeInsulin.class);
            startActivity(nextIntent);
        }
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        //Intent intent = new Intent(this, BluetoothService.class);
        //startService(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupForegroundDispatch(this, mAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopForegroundDispatch(this, mAdapter);
    }

    /**
     * Stops foreground dispatch of NFC intents
     *
     * @param activity The Activity requesting the foreground dispatch.
     * @param adapter  The NfcAdapter used for the foreground dispatch.
     */
    public void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    /**
     * Sets up foreground dispatch for NFC intents for the Activity
     *
     * @param activity The corresponding Activity requesting the foreground dispatch.
     * @param adapter  The NfcAdapter used for the foreground dispatch.
     */
    public void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{new String[]{NfcV.class.getName()}};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        //ToDo: Read NFC in another task
        String action = intent.getAction();
        Log.d(TAG, "Tech Discovered");
        if (intent.getAction().equals(NfcAdapter.ACTION_TECH_DISCOVERED)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            ReadNfcTask readNfcTask = new ReadNfcTask(this);
            readNfcTask.execute(tag);
        }
    }
}
