package uz.anvar.darsjadvali;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Loader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Loader.this, LessonActivity.class);
                startActivity(intent);
            }
        }, 400);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Dasturdan chiqish")
                .setMessage("Dasturdan chiqmoqchimisiz?")
                .setPositiveButton("Ha", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // Agar birinchi ekran bo'lsa
                        // finishAffinity(); // Har qanday o'tishlardan ham keyin
                        // System.exit(0);
                    }
                })
                .setNegativeButton("Yo\u2018q", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    //    ViewFlipper includes;
    //
    //    public void ClickButton(View v) {
    //        switch (v.getId()) {
    //            case R.id.kun1: includes.setDisplayedChild(0);
    //                break;
    //            case R.id.<n>: includes.setDisplayedChild(n - 1);
    //                break;
    //        }
    //    }
}