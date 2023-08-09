package uz.anvar.darsjadvali.ui.lesson;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import uz.anvar.darsjadvali.R;
import uz.anvar.darsjadvali.app.model.WeekDay;
import uz.anvar.darsjadvali.data.mapper.WeekDayMapper;
import uz.anvar.darsjadvali.data.rest.NetworkConstants;
import uz.anvar.darsjadvali.data.rest.request.WeekDaysLoader;
import uz.anvar.darsjadvali.ui.adapters.WeekDaysAdapter;


public class LessonActivity extends AppCompatActivity {

    private TabLayout weekDays;
    private ViewPager lessonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        setContentActivity();
    }

    @SuppressWarnings("deprecation")
    private void setContentActivity() {
        weekDays = findViewById(R.id.tab_layout_week_days);
        lessonLayout = findViewById(R.id.view_pager_lessons);

        TextView today = findViewById(R.id.today);
        today.setText(getString(R.string.today));

        new WeekDaysLoader(LessonActivity.this::setWeekDays)
                .execute(NetworkConstants.API_URL + "/days");
    }

    private void setWeekDays(String json) {
        if (json == null || json.isEmpty())
            return;

        List<WeekDay> dayList = new WeekDayMapper().invoke(json);
        onWeekDaysLoad(dayList);
    }

    private void onWeekDaysLoad(List<WeekDay> list) {
        WeekDaysAdapter adapter = new WeekDaysAdapter(this, list, getSupportFragmentManager());

        lessonLayout.setAdapter(adapter);
        weekDays.setupWithViewPager(lessonLayout);

        adapter.setTabLayoutTabs(weekDays, list);
    }
}