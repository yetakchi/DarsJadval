package uz.anvar.darsjadvali;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import uz.anvar.darsjadvali.adapter.OnDataLoadListener;
import uz.anvar.darsjadvali.adapter.WeekDaysAdapter;
import uz.anvar.darsjadvali.model.WeekDay;
import uz.anvar.darsjadvali.request.Constants;
import uz.anvar.darsjadvali.request.WeekDaysLoader;


public class LessonActivity extends AppCompatActivity {

    private TabLayout weekDays;
    private ViewPager lessonLayout;

    @SuppressLint("StaticFieldLeak")
    private TextView today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        setContentActivity();
    }

    private void setContentActivity() {
        weekDays = findViewById(R.id.tab_layout_week_days);
        lessonLayout = findViewById(R.id.view_pager_lessons);
        today = findViewById(R.id.today);

        today.setText(getString(R.string.today));

        new WeekDaysLoader(new OnDataLoadListener() {
            @Override
            public void onWeekDaysLoad(List<WeekDay> list) {
                LessonActivity.this.onWeekDaysLoad(list);
            }
        }).execute(Constants.API_URL + "/days");
    }

    private void onWeekDaysLoad(List<WeekDay> list) {
        WeekDaysAdapter adapter = new WeekDaysAdapter(this, weekDays, list, getSupportFragmentManager());

        lessonLayout.setAdapter(adapter);
        weekDays.setupWithViewPager(lessonLayout);

        adapter.setTabLayoutTabs(list);
    }
}