
package com.jishang.bimeng.utils.dialog;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtils {
    private static DialogUtils me = new DialogUtils();

    private ProgressDialog dialog;

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {
        return me;
    }

    public void createNotifier(Context mContext, String str) {
        dialog = new ProgressDialog(mContext);
        dialog.setMessage(str);
        dialog.show();
    }

    public void cancel() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

}
