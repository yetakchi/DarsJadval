package uz.anvar.darsjadvali.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uz.anvar.darsjadvali.LessonActivity;
import uz.anvar.darsjadvali.R;
import uz.anvar.darsjadvali.holder.WeekDaysViewHolder;
import uz.anvar.darsjadvali.model.WeekDay;
import uz.anvar.darsjadvali.request.LessonsLoader;

public class WeekDaysAdapter extends RecyclerView.Adapter<WeekDaysViewHolder> {

    Context context;
    List<WeekDay> days;

    public WeekDaysAdapter(Context context, List<WeekDay> days) {
        this.context = context;
        this.days = days;
    }

    @NonNull
    @Override
    public WeekDaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.week_day, parent, false);
        return new WeekDaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekDaysViewHolder holder, int position) {
        holder.setDayName(days.get(position).getDayName());
        holder.setDay(days.get(position).getDay());

        holder.itemView.setOnClickListener(v -> {
            LessonActivity.lessons_loader.setVisibility(View.VISIBLE);
            new LessonsLoader().execute(LessonActivity.url + "/lessons/" + days.get(position).getId());
        });

        if (days.get(position).isActive())
            holder.setActive();
    }

    @Override
    public int getItemCount() {
        return days.size();
    }
}
