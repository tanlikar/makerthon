package com.example.makerthon;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class viewDialog {

    public void showDialogError(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_dialog_error);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.btn_dialog_ok);
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button dialogButtonInfo = dialog.findViewById(R.id.dialog_info);
        dialogButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add intent for display info
            }
        });

        dialog.show();

    }

    public void showDialogCorrect(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_alert_dialog_correct);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog_correct);
        text.setText(msg);

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.btn_dialog_ok_correct);
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button dialogButtonInfo = dialog.findViewById(R.id.dialog_info_correct);
        dialogButtonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO add intent for display info
            }
        });

        dialog.show();

    }
}
