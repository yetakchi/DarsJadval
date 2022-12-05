package uz.anvar.darsjadvali;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import uz.anvar.darsjadvali.utils.Global;


public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(LaunchActivity.this, LessonActivity.class);
            startActivity(intent);
        }, 400);
    }

    @Override
    public void onBackPressed() {
        Global.dialog(this, this);
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