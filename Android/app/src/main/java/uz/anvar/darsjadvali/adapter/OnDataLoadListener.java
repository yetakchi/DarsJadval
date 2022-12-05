package uz.anvar.darsjadvali.adapter;

import java.util.List;

import uz.anvar.darsjadvali.model.Lesson;
import uz.anvar.darsjadvali.model.WeekDay;

public interface OnDataLoadListener {
    default void onLessonsLoad(List<Lesson> lessonList) {

    }

    default void onWeekDaysLoad(List<WeekDay> weekDayList) {

    }
}
