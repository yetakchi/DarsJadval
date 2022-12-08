package uz.anvar.darsjadvali.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uz.anvar.darsjadvali.R;
import uz.anvar.darsjadvali.holder.LessonsViewHolder;
import uz.anvar.darsjadvali.model.Lesson;
import uz.anvar.darsjadvali.utils.Global;


public class LessonAdapter extends RecyclerView.Adapter<LessonsViewHolder> {

    private final Context context;
    private List<Lesson> lessons;

    public LessonAdapter(Context context, List<Lesson> lessons) {
        this.context = context;
        this.lessons = lessons;
    }

    @NonNull
    @Override
    public LessonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.lesson_item, parent, false);
        return new LessonsViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonsViewHolder holder, int position) {
        setViewsAttribute(holder, position);
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    private void setViewsAttribute(LessonsViewHolder holder, int position) {
        // Add event listener
        holder.addOnClickListener(context, lessons.get(position).getRoomNumber());

        // Set parameters
        holder.setSubject(lessons.get(position).getSubject());
        holder.setTeacher(lessons.get(position).getTeacher());
        holder.setForm(lessons.get(position).getForm());
        holder.setTeachForm(lessons.get(position).getTeachForm());
        holder.setStartTime(lessons.get(position).getStartTime());
        holder.setEndTime(lessons.get(position).getEndTime());
        holder.setLessonImage(lessons.get(position).getImageSource());

        holder.setActive(lessons.get(position).isActive());
        holder.setVisibility(lessons.size(), lessons.get(position).getId());

        // Set TextView Drawables
        holder.setTextDrawables(lessons.get(position).getImageResource());

        holder.setAnimation(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setLessonsList(List<Lesson> lessonsList) {
        lessons = lessonsList;
        Global.lessons = lessonsList; // Global.lessons.addAll(lessons);
        this.notifyDataSetChanged();
    }
}
