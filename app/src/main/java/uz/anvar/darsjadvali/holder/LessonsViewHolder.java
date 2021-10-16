package uz.anvar.darsjadvali.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import uz.anvar.darsjadvali.R;

public class LessonsViewHolder extends RecyclerView.ViewHolder {

    TextView start_time, end_time, colon;
    TextView subject, teacher, theme, teach_form;
    ImageView lesson_img, subject_line;
    CardView lesson_bg;
    RadioButton active_subject;

    public LessonsViewHolder(@NonNull View itemView) {
        super(itemView);

        start_time = itemView.findViewById(R.id.start_time);
        end_time = itemView.findViewById(R.id.end_time);
        subject = itemView.findViewById(R.id.subject);
        teacher = itemView.findViewById(R.id.teacher);
        theme = itemView.findViewById(R.id.theme);
        teach_form = itemView.findViewById(R.id.teach_form);
        lesson_img = itemView.findViewById(R.id.lesson_img);
        lesson_bg = itemView.findViewById(R.id.lesson_bg);
        colon = itemView.findViewById(R.id.colon);
        active_subject = itemView.findViewById(R.id.active_subject);
        subject_line = itemView.findViewById(R.id.subject_line);
    }

    public void setSubject(String name) {
        subject.setText(name);
    }

    public void setTeacher(String name) {
        teacher.setText(name);
    }

    public void setForm(String name) {
        theme.setText(name);
    }

    public void setTeachForm(String format) {
        teach_form.setText(format);
    }

    public void setStartTime(String time) {
        start_time.setText(time);
    }

    public void setEndTime(String time) {
        end_time.setText(time);
    }

    public void setLessonImage(String src) {
        System.out.println(src);
//        lesson_img.setImageResource();
    }

    public void addOnClickListener(Context context, String number) {
        lesson_bg.setOnClickListener(v ->
                Toast.makeText(context, number, Toast.LENGTH_SHORT).show()
        );
    }

    public void setActive(boolean isActive) {
        int bg, color;
        if (isActive) {
            color = Color.WHITE;
            bg = lesson_bg.getResources().getColor(R.color.active_card);

            lesson_img.setVisibility(View.VISIBLE);
            subject.setTextColor(color);
        } else {
            color = lesson_bg.getResources().getColor(R.color.inactive_text);
            bg = lesson_bg.getResources().getColor(R.color.inactive_card);

            lesson_img.setVisibility(View.GONE);
            subject.setTextColor(Color.BLACK);
        }

        setTextColor(color);
        active_subject.setChecked(isActive);
        lesson_bg.setCardBackgroundColor(bg);
    }

    private void setTextColor(int color) {
        theme.setTextColor(color);
        teacher.setTextColor(color);
        teach_form.setTextColor(color);
        colon.setTextColor(color);
    }

    public void Invisible(int size, int id) {
        if (size == id && size > 1) {
            subject_line.setVisibility(View.INVISIBLE);
            subject_line.setAlpha(0f);
        } else {
            subject_line.setVisibility(View.VISIBLE);
            subject_line.setAlpha(1f);
        }
    }

    public void setTextDrawables(int res) {
        teach_form.setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
    }

    public void setAnimation(int delay) {
        Animation animation = AnimationUtils.loadAnimation(lesson_bg.getContext(), R.anim.fade);
        animation.setStartOffset(delay * 500 + 100);

        lesson_bg.setAnimation(animation);
        start_time.setAnimation(animation);
        end_time.setAnimation(animation);
        active_subject.setAnimation(animation);

        Animation line = AnimationUtils.loadAnimation(subject_line.getContext(), R.anim.line);
        line.setStartOffset(delay * 1000);
        subject_line.setAnimation(line);
    }
}
