package ch.berufsbildungscenter.weathermasters.Alert;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by zmartl on 03.07.2015.
 * Version 1
 */
public class CustomDialog {

    private Dialog dialog;

    public void displayProgrammDialog(Context context, String title, String message){
        dialog = ProgressDialog.show(context, title, message);
    }

    public void stopDisplayDialog(){
        dialog.dismiss();
    }
}
