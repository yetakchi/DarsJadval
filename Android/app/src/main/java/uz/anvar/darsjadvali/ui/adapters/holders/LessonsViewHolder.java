package uz.anvar.darsjadvali.ui.adapters.holders;

import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import uz.anvar.darsjadvali.R;
import uz.anvar.darsjadvali.app.model.Lesson;


public class LessonsViewHolder extends RecyclerView.ViewHolder {

    private final TextView startTime, endTime, colon;
    private final TextView subject, teacher, theme, teachForm;
    private final ImageView lessonImg, subjectLine;
    private final CardView lessonCard;
    private final RadioButton activeSubject;

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

    public void setContent(Lesson lesson) {
        theme.setText(lesson.getForm());
        subject.setText(lesson.getSubject());
        teacher.setText(lesson.getTeacher());
        teachForm.setText(lesson.getTeachForm());
        startTime.setText(lesson.getStartTime());
        endTime.setText(lesson.getEndTime());

        // lesson_img.setImageResource(lesson.getImageSource());

        this.setActive(lesson.isActive());

        teachForm.setCompoundDrawablesWithIntrinsicBounds(lesson.getImageResource(), 0, 0, 0);
    }

    public void addOnClickListener(final OnItemClickListener listener) {
        lessonCard.setOnClickListener(v -> listener.onClick());
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

    public interface OnItemClickListener {
        void onClick();
    }
}
