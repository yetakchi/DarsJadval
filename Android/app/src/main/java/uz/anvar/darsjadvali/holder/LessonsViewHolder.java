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

    TextView startTime, endTime, colon;
    TextView subject, teacher, theme, teachForm;
    ImageView lessonImg, subjectLine;
    CardView lessonCard;
    RadioButton activeSubject;

    public LessonsViewHolder(@NonNull View itemView) {
        super(itemView);

        startTime = itemView.findViewById(R.id.start_time);
        endTime = itemView.findViewById(R.id.end_time);
        subject = itemView.findViewById(R.id.subject);
        teacher = itemView.findViewById(R.id.teacher);
        theme = itemView.findViewById(R.id.theme);
        teachForm = itemView.findViewById(R.id.teach_form);
        lessonImg = itemView.findViewById(R.id.lesson_img);
        lessonCard = itemView.findViewById(R.id.lesson_card);
        colon = itemView.findViewById(R.id.colon);
        activeSubject = itemView.findViewById(R.id.active_subject);
        subjectLine = itemView.findViewById(R.id.subject_line);
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
        teachForm.setText(format);
    }

    public void setStartTime(String time) {
        startTime.setText(time);
    }

    public void setEndTime(String time) {
        endTime.setText(time);
    }

    public void setLessonImage(String src) {
        // lesson_img.setImageResource();
    }

    public void addOnClickListener(Context context, String number) {
        lessonCard.setOnClickListener(v -> Toast.makeText(context, number, Toast.LENGTH_SHORT).show());
    }

    public void setActive(boolean isActive) {
        int bg, color;
        if (isActive) {
            color = Color.WHITE;
            bg = lessonCard.getResources().getColor(R.color.active_card);

            lessonImg.setVisibility(View.VISIBLE);
            subject.setTextColor(color);
        } else {
            color = lessonCard.getResources().getColor(R.color.inactive_text);
            bg = lessonCard.getResources().getColor(R.color.inactive_card);

            lessonImg.setVisibility(View.GONE);
            subject.setTextColor(Color.BLACK);
        }

        setTextColor(color);
        activeSubject.setChecked(isActive);
        lessonCard.setCardBackgroundColor(bg);
    }

    private void setTextColor(int color) {
        theme.setTextColor(color);
        teacher.setTextColor(color);
        teachForm.setTextColor(color);
        colon.setTextColor(color);
    }

    public void setVisibility(int size, int id) {
        if (size == id && size > 1) {
            subjectLine.setVisibility(View.INVISIBLE);
            subjectLine.setAlpha(0f);
        } else {
            subjectLine.setVisibility(View.VISIBLE);
            subjectLine.setAlpha(1f);
        }
    }

    public void setTextDrawables(int res) {
        teachForm.setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
    }

    public void setAnimation(int position) {
        Animation animation = AnimationUtils.loadAnimation(lessonCard.getContext(), R.anim.card_fade);
        animation.setStartOffset(position * 500L + 100);

        lessonCard.setAnimation(animation);
        startTime.setAnimation(animation);
        endTime.setAnimation(animation);
        activeSubject.setAnimation(animation);

        Animation line = AnimationUtils.loadAnimation(subjectLine.getContext(), R.anim.line);
        line.setStartOffset(position * 1000L);
        subjectLine.setAnimation(line);
    }
}
