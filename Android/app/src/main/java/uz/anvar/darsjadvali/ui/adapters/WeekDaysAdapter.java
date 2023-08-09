package uz.anvar.darsjadvali.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import uz.anvar.darsjadvali.R;
import uz.anvar.darsjadvali.app.model.WeekDay;
import uz.anvar.darsjadvali.ui.adapters.holders.WeekDaysView;
import uz.anvar.darsjadvali.ui.lesson.LessonFragment;


@SuppressWarnings("deprecation")
public class WeekDaysAdapter extends FragmentPagerAdapter {

    private final Context context;

    private final ArrayList<WeekDay> days = new ArrayList<>();

    public WeekDaysAdapter(Context context, List<WeekDay> days, FragmentManager fm) {
        //noinspection deprecation
        super(fm, FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);

        this.context = context;
        this.days.addAll(days);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new LessonFragment(position);
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @NonNull
    public View onCreateViewHolder(ViewGroup parent, WeekDay day) {
        View view = LayoutInflater.from(context).inflate(R.layout.week_day, parent, false);
        WeekDaysView holder = new WeekDaysView(view, day);

        return holder.view;
    }

    public void setTabLayoutTabs(TabLayout tabLayout, List<WeekDay> list) {
        TabLayout.Tab tab;
        for (int i = 0; i < getCount(); i++) {
            tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(onCreateViewHolder(null, list.get(i)));
            }
        }
    }
}
