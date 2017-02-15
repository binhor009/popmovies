package com.fabiofilho.popmovies.Objects.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import com.fabiofilho.popmovies.R;


/**
 * Created by dialam on 10/02/17.
 */

public class SimpleDialog extends DialogFragment {


    public Dialog onCreateDialog(Activity activity, int message, final DialogInterface.OnClickListener positiveOnClickListener,
                                 @Nullable final DialogInterface.OnClickListener negativeOnClickListener) {

        return onCreateDialog(activity, activity.getResources().getString(message), positiveOnClickListener, negativeOnClickListener);
    }


    public Dialog onCreateDialog(Activity activity, String message, final DialogInterface.OnClickListener positiveOnClickListener,
                                 @Nullable final DialogInterface.OnClickListener negativeOnClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setCancelable(false);
        builder.setMessage(message).setPositiveButton(R.string.dialog_ok, positiveOnClickListener);
        builder.setNegativeButton(R.string.dialog_cancel, negativeOnClickListener);

        // Handles the key back event.
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {

                // Checks if the back button is pressed.
                if (i == KeyEvent.KEYCODE_BACK){

                    // Raise the button cancel event.
                    if(negativeOnClickListener!=null) negativeOnClickListener.onClick(dialogInterface, i);

                    return true;
                }

                return false;
            }
        });

        return builder.create();
    }
}
