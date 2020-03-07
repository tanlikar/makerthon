package com.example.makerthon;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class viewDialog {

    public void showDialogError(Activity activity, String msg, String product){
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
                Intent intent = new Intent(activity, displayFact.class);
                if(product.equals("apple")) {
                    intent.putExtra("picture", R.drawable.nutritionfacts_apple);
                }else if(product.equals("orange")){
                    intent.putExtra("picture", R.drawable.nutrition_fact_orange);
                }
                activity.startActivity(intent);
            }
        });

        dialog.show();

    }

    public void showDialogCorrect(Activity activity, String msg, String product){
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
                Intent intent = new Intent(activity, displayFact.class);
                if(product.equals("apple")) {
                    intent.putExtra("picture", R.drawable.nutritionfacts_apple);
                }else if(product.equals("orange")){
                    intent.putExtra("picture", R.drawable.nutrition_fact_orange);
                }
                activity.startActivity(intent);

            }
        });

        dialog.show();

    }

}
