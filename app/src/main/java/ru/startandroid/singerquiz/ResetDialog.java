package ru.startandroid.singerquiz;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class ResetDialog extends DialogFragment{

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage("Start again with the new settings?")
                .setPositiveButton("Yes", prepodListener)
                .setNegativeButton("No", prepodListener)
                .create();
    }

    DialogInterface.OnClickListener prepodListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which){
                case Dialog.BUTTON_NEGATIVE:
                    getActivity().setResult(Activity.RESULT_CANCELED);
                    ((SettingsFragment)getTargetFragment()).deleteListener();
                    getActivity().finish();
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    getActivity().setResult(Activity.RESULT_OK);
                    ((SettingsFragment)getTargetFragment()).deleteListener();
                    getActivity().finish();
                    break;
            }
        }
    };

}
