package uz.anvar.darsjadvali.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uz.anvar.darsjadvali.R;
import uz.anvar.darsjadvali.app.model.Lesson;
import uz.anvar.darsjadvali.ui.adapters.holders.LessonsViewHolder;


public class LessonAdapter extends RecyclerView.Adapter<LessonsViewHolder> {

    private final Context context;
    private final List<Lesson> lessons; // Collections.emptyList();

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
        Lesson lesson = lessons.get(position);

        // Add event listener
        holder.addOnClickListener(() -> Toast.makeText(
                context,
                String.valueOf(lesson.getRoomNumber()),
                Toast.LENGTH_SHORT
        ).show());

        // Set parameters
        holder.setContent(lesson);

        // Set TextView Drawables
        holder.setVisibility(lessons.size(), lesson.getId());
        holder.setAnimation(position);

        int resId = context.getResources().getIdentifier(lesson.getImageSource(), "drawable", context.getPackageName());
        System.out.println(resId);
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public void updateUI() {
        notifyItemRangeChanged(0, lessons.size());
    }
}
