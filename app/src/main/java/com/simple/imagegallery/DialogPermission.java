package com.simple.imagegallery;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.library.gallery.R;

public class DialogPermission extends AlertDialog {
    private ListenerPermission listenerPermission;

    public DialogPermission(@NonNull Context context) {
        super(context);
    }

    public DialogPermission(@NonNull Context context, ListenerPermission listenerPermission) {
        super(context);
        this.listenerPermission = listenerPermission;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_permission);
        findViewById(R.id.tv_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listenerPermission == null) {
                    return;
                }
                listenerPermission.onAllow();
                dismiss();
            }
        });
    }

    public interface ListenerPermission {
        void onAllow();
    }
}
