package com.fabiofilho.popmovies.Objects.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.fabiofilho.popmovies.R;


/**
 * Created by dialam on 10/02/17.
 */

public class SimpleDialog extends DialogFragment {


    public Dialog onCreateDialog(Activity activity, int message, final DialogInterface.OnClickListener positiveOnClickListener,
                                 final DialogInterface.OnClickListener negativeOnClickListener) {
        return onCreateDialog(activity, getResources().getString(message), positiveOnClickListener, negativeOnClickListener);
    }


    public Dialog onCreateDialog(Activity activity, String message, final DialogInterface.OnClickListener positiveOnClickListener,
                                 final DialogInterface.OnClickListener negativeOnClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        
        builder.setMessage(message).setPositiveButton(R.string.dialog_ok, positiveOnClickListener);
        builder.setCancelable(true);

        if(negativeOnClickListener!=null){
            builder.setNegativeButton(R.string.dialog_cancel, negativeOnClickListener);
        }
                
        return builder.create();
    }
}
