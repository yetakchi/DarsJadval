package uz.anvar.darsjadvali.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import uz.anvar.darsjadvali.R;
import uz.anvar.darsjadvali.adapter.OnItemClickListener;
import uz.anvar.darsjadvali.model.WeekDay;


public class WeekDaysView {

    private final TextView day;
    private final LinearLayout weekDayLayout;

    public View view;

    public WeekDaysView(@NonNull View itemView, WeekDay weekDay, final OnItemClickListener onItemClickListener) {
        TextView day_name = itemView.findViewById(R.id.day_name);
        day = itemView.findViewById(R.id.day);
        weekDayLayout = itemView.findViewById(R.id.week_day_layout);

        day_name.setText(weekDay.getDayName());
        day.setText(weekDay.getDay());

        view = itemView;

        if (weekDay.isActive())
            setActive();
    }

    public void setActive() {
        day.setTextColor(day.getContext().getResources().getColor(R.color.inactive_text));
        weekDayLayout.setBackgroundResource(R.drawable.dr_active_text); // context.getResources().getDrawable(R.drawable.dr_active_text)
    }
}
