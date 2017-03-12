package com.fabiofilho.popmovies.objects.dialogs;

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

    private static int sItemIndexChecked = 0, sOldItemIndexChecked = 0;

    public Dialog onCreateDialog(Activity activity,
                                 final DialogInterface.OnClickListener positiveOnClickListener,
                                 int itemIndexChecked) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        sItemIndexChecked = itemIndexChecked;
        sOldItemIndexChecked = itemIndexChecked;

        builder.setTitle(R.string.dialog_movie_order_title)
                .setSingleChoiceItems(R.array.dialog_movie_order_content, sItemIndexChecked,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sItemIndexChecked = which;

                    }
                }).setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(sItemIndexChecked != sOldItemIndexChecked) {
                            positiveOnClickListener.onClick(dialog, sItemIndexChecked);
                        }
                        sOldItemIndexChecked = sItemIndexChecked;
                    }
                }

                ).setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sItemIndexChecked = sOldItemIndexChecked;
                    }
        });

        return builder.create();
    }

}
