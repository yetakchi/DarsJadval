package uz.anvar.darsjadvali;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uz.anvar.darsjadvali.adapter.LessonAdapter;
import uz.anvar.darsjadvali.model.Lesson;
import uz.anvar.darsjadvali.request.LessonsLoader;
import uz.anvar.darsjadvali.request.TodayDateLoader;
import uz.anvar.darsjadvali.request.WeekDaysLoader;

public class LessonActivity extends AppCompatActivity {

    public RecyclerView lessonsRecycler, week_days;
    public TextView today;

    @SuppressLint("StaticFieldLeak")
    public static ProgressBar lessons_loader;
    @SuppressLint("StaticFieldLeak")
    public static LessonAdapter lessonAdapter;
    public static String url = "http://192.168.1." + 4 + ":5000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        week_days = findViewById(R.id.week_days);
        lessons_loader = findViewById(R.id.lessons_loader);
        lessonsRecycler = findViewById(R.id.lessons);
        today = findViewById(R.id.today);

        setWeekDaysRecycler(week_days);
        setLessonsRecycler();
        setTodayDate();
    }

    private void setTodayDate() {
        new TodayDateLoader(today).execute(url + "/today");
    }

    private void setLessonsRecycler() {
        ArrayList<Lesson> lessonArrayList = new ArrayList<>();
        lessonAdapter = new LessonAdapter(this, lessonArrayList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);

        lessonsRecycler.setLayoutManager(manager);
        lessonsRecycler.setAdapter(lessonAdapter);

        new LessonsLoader().execute(url + "/lessons/auto");
    }

    private void setWeekDaysRecycler(RecyclerView week_days) {
        new WeekDaysLoader(this, week_days).execute(url + "/days");
    }
}