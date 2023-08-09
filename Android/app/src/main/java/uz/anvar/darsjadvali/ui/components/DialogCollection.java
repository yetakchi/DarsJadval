package uz.anvar.darsjadvali.ui.components;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;


public class DialogCollection {

    public void show(Context context, OnDialogButtonClick onButtonClick) {
        new AlertDialog.Builder(context)
                .setTitle("Dasturdan chiqish")
                .setMessage("Dasturdan chiqmoqchimisiz?")
                .setPositiveButton("Ha", (dialog, which) -> onButtonClick.onSuccessClick())
                .setNegativeButton("Yo\u2018q", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public interface OnDialogButtonClick {
        void onSuccessClick();
    }
}
