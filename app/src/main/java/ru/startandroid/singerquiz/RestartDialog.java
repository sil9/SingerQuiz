package ru.startandroid.singerquiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class RestartDialog extends DialogFragment{

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure you want to start over? The progress will be lost.")
                .setPositiveButton("Yes", prepodListener)
                .setNegativeButton("No", prepodListener)
                .create();
    }

    DialogInterface.OnClickListener prepodListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which){
                case Dialog.BUTTON_NEGATIVE:
                    dialog.cancel();
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    ((SingerFragment)getTargetFragment()).loadPrepods();
                    ((SingerFragment)getTargetFragment()).loadNextPrepodQuestion();
                    break;
            }
        }
    };

}
