package uz.anvar.darsjadvali;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uz.anvar.darsjadvali.adapter.LessonAdapter;
import uz.anvar.darsjadvali.adapter.OnDataLoadListener;
import uz.anvar.darsjadvali.adapter.WeekDaysAdapter;
import uz.anvar.darsjadvali.model.Lesson;
import uz.anvar.darsjadvali.model.WeekDay;
import uz.anvar.darsjadvali.request.Constants;
import uz.anvar.darsjadvali.request.LessonsLoader;
import uz.anvar.darsjadvali.request.TodayDateLoader;
import uz.anvar.darsjadvali.request.WeekDaysLoader;


public class LessonActivity extends AppCompatActivity {

    public RecyclerView lessonsRecycler, weekDaysRecycler;
    public TextView today;

    @SuppressLint("StaticFieldLeak")
    public static ProgressBar loader;

    public LessonAdapter lessonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        setContentActivity();
    }

    private void setContentActivity() {
        weekDaysRecycler = findViewById(R.id.week_days);
        lessonsRecycler = findViewById(R.id.lessons);
        loader = findViewById(R.id.lessons_loader);
        today = findViewById(R.id.today);

        setUpRecycler();
        setTodayDate();
    }

    private void setUpRecycler() {
        WeekDaysAdapter adapter = new WeekDaysAdapter(LessonActivity.this, Collections.emptyList(), id -> {
            LessonActivity.loader.setVisibility(View.VISIBLE);
            LessonActivity.this.lessonLoader("/lessons/" + id);
        });

        RecyclerView.LayoutManager manager1 = new LinearLayoutManager(LessonActivity.this, RecyclerView.HORIZONTAL, false);
        weekDaysRecycler.setLayoutManager(manager1);
        weekDaysRecycler.setAdapter(adapter);

        new WeekDaysLoader(new OnDataLoadListener() {
            @Override
            public void onWeekDaysLoad(List<WeekDay> list) {
                adapter.setList(list);
            }
        }).execute(Constants.url + "/days");

        lessonAdapter = new LessonAdapter(this, new ArrayList<>());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        lessonsRecycler.setLayoutManager(manager);
        lessonsRecycler.setAdapter(lessonAdapter);

        lessonLoader("/lessons/auto");
    }

    private void lessonLoader(String path) {
        new LessonsLoader(new OnDataLoadListener() {
            @Override
            public void onLessonsLoad(List<Lesson> list) {
                LessonActivity.loader.setVisibility(View.GONE);
                lessonAdapter.setLessonsList(list);
            }
        }).execute(Constants.url + path);
    }

    private void setTodayDate() {
        new TodayDateLoader(today).execute(Constants.url + "/today");
    }
}