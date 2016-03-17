package hu.com.android.util;

import android.content.Context;

import jgabrielfreitas.sdialog.dialog.SimpleDialog;
import jgabrielfreitas.sdialog.interfaces.DialogCallback;

/**
 * Created by JGabrielFreitas on 17/03/16.
 */
public class Utils {

    public static final String SELECTED_ID = "selected_id";

    public static void feedback(Context context, String message, DialogCallback  dialogCallback) {
        new SimpleDialog(context).setMessage(message).setDialogCallback(dialogCallback)
                .setPositiveButtonText("Fechar").create();
    }

    public static void feedback(Context context, String message) {
        new SimpleDialog(context).setMessage(message).setDialogCallback(new DialogCallback() {
            public void onPositiveButton() {}
            public void onNegativeButton() {}
            public void onNeutralButton() {}
        }).setPositiveButtonText("Fechar").create();
    }
}
