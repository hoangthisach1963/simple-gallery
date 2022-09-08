package com.simple.imagegallery;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.library.gallery.R;

public class SplashActivity extends AppCompatActivity {
    Button btnStart;
    ProgressBar progressBar;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private DialogPermission dialogPermission;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.btnStart = findViewById(R.id.btn_start);
        progressBar = findViewById(R.id.progress_bar);
        this.dialogPermission = new DialogPermission(this, new DialogPermission.ListenerPermission() {
            @Override
            public void onAllow() {
                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        });
        this.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMain();
            }
        });
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showButton();
            }
        }, 3000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            startMain();
            return;
        }

        for (int i = 0, len = permissions.length; i < len; i++) {
            String permission = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                if (!showRationale) {
                    this.showDialogPermissionSettings();
                    break;
                }
            }
        }
    }

    private void startMain() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                this.dialogPermission.show();
                return;
            }
        }

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void showButton() {
        try {
            btnStart.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AlertDialog alertDialogPermissionSetting;

    private void showDialogPermissionSettings() {
        if (alertDialogPermissionSetting == null) {
            View layoutPermission = View.inflate(this, R.layout.dialog_settings_permission, null);
            layoutPermission.findViewById(R.id.btn_settings).setOnClickListener(view -> {
                if (alertDialogPermissionSetting != null) {
                    alertDialogPermissionSetting.dismiss();
                }
                goAppSetting(this);
            });
            layoutPermission.findViewById(R.id.btn_cancel).setOnClickListener(view -> {
                if (alertDialogPermissionSetting != null) {
                    alertDialogPermissionSetting.dismiss();
                }
            });
            alertDialogPermissionSetting = new AlertDialog.Builder(this)
                    .setView(layoutPermission)
                    .setCancelable(false)
                    .create();
            alertDialogPermissionSetting.setCanceledOnTouchOutside(false);
        }
        alertDialogPermissionSetting.show();
    }


    private void goAppSetting(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
