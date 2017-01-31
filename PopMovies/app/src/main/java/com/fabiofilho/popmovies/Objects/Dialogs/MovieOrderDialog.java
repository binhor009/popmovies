package com.fabiofilho.popmovies.Objects.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.fabiofilho.popmovies.R;

/**
 * Created by dialam on 31/01/17.
 */

public class MovieOrderDialog extends DialogFragment{

    private static int sItemChecked = 0, sOldItemChecked = 0;

    public Dialog onCreateDialog(Activity activity, final DialogInterface.OnClickListener mPositiveOnClick) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Set the dialog title
        builder.setTitle(R.string.dialog_movie_order_title)
                .setSingleChoiceItems(R.array.dialog_movie_order_content, sItemChecked, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sItemChecked = which;

                    }
                }).setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(sItemChecked!=sOldItemChecked) mPositiveOnClick.onClick(dialog, sItemChecked);
                        sOldItemChecked = sItemChecked;
                    }
                }

                ).setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sItemChecked = sOldItemChecked;
                    }
        });

        return builder.create();
    }
}
