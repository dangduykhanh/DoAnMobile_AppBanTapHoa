package com.example.doan_ltddnc_appbantaphoa;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

public class LoadingDialog {
    Context context ;
    Dialog dialog ;
    public LoadingDialog(Context context) {
        this.context = context;
    }
    public void ShowDialog (String title){
        dialog =new Dialog(context) ;
        dialog.setContentView(R.layout.loading_dialog_custom);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView textView =dialog.findViewById(R.id.tvload) ;
        textView.setText(title);
        dialog.create();
        dialog.show();
    }
    public void HideDialog(){
        dialog.dismiss();

    }
}
