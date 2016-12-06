package com.matejhacin.roomies.utils;

import android.content.Context;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.matejhacin.roomies.R;

/**
 * Created by Domen Lanišnik on 6. 12. 2016.
 */
public class GeneralUtil {
    public static String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static boolean isEmpty(EditText editText) {
        return getText(editText).isEmpty();
    }

    public static MaterialDialog getLoadingDialog(Context context) {
        return new MaterialDialog.Builder(context)
                .content(R.string.loading_message)
                .progress(true, 0)
                .cancelable(false)
                .build();
    }
}
