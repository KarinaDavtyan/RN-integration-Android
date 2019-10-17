package com.example.appwithbutton;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import com.facebook.soloader.SoLoader;

public class MainActivity extends Activity {

    private final int OVERLAY_PERMISSION_REQ_CODE = 1;  // any value can be chosen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SoLoader.init(this, /* native exopackage */ false);

        setContentView(R.layout.activity_main);



        // we want to display RN dev errors above other windows and in the Android M API user needs to approve it
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }

        Button clickButton = findViewById(R.id.button);
        clickButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReactActivity.class);
                startActivity(intent);
            }
        });
    }

    // we need this to to handle the permission Accepted or Denied cases for consistent UX.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // had to be added after warning
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted
                }
            }
        }
//        // for integrating Native Modules which use startActivityForResult,
//        // we need to pass the result to the onActivityResult method of our ReactInstanceManager instance
//        mReactInstanceManager.onActivityResult( this, requestCode, resultCode, data );
    }


}
