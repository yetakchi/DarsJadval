package uz.anvar.darsjadvali.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uz.anvar.darsjadvali.R;
import uz.anvar.darsjadvali.holder.WeekDaysViewHolder;
import uz.anvar.darsjadvali.model.WeekDay;


public class WeekDaysAdapter extends RecyclerView.Adapter<WeekDaysViewHolder> {

    private final Context context;

    private final OnItemClickListener onItemClickListener;
    private final ArrayList<WeekDay> days = new ArrayList<>();

    public WeekDaysAdapter(Context context, List<WeekDay> days, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.context = context;
        this.days.addAll(days);
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

        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(days.get(position).getId()));

        if (days.get(position).isActive())
            holder.setActive();
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<WeekDay> list) {
        this.days.clear();
        this.days.addAll(list);
        this.notifyDataSetChanged();
    }
}
