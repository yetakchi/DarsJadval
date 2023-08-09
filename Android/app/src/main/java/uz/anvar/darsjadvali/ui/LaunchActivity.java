package uz.anvar.darsjadvali.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import uz.anvar.darsjadvali.R;
import uz.anvar.darsjadvali.ui.components.DialogCollection;
import uz.anvar.darsjadvali.ui.lesson.LessonActivity;


@SuppressLint("CustomSplashScreen")
public class LaunchActivity extends AppCompatActivity {

    private final DialogCollection alert = new DialogCollection();

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
        // Agar birinchi ekran bo'lsa finish()
        // Har qanday o'tishlardan ham keyin finishAffinity(); or System.exit(0);
        alert.show(this, this::finish);
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