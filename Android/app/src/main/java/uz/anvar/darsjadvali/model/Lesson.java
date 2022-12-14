package uz.anvar.darsjadvali.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;

import uz.anvar.darsjadvali.R;


public class Lesson {

    private final int id;
    @SerializedName("teach_form")
    private final String teachForm;
    private final String subject, teacher, form;
    @SerializedName("start_time")
    private final String startTime;
    @SerializedName("end_time")
    private final String endTime;

    @SerializedName("img_src")
    private final String imgSrc;
    @SerializedName("room")
    private final int roomNumber;
    private final boolean active;

    public Lesson(int id, String subject, String form, String teacher, String teachForm, String startTime, String endTime, String imgSrc, int number, boolean active) {
        this.id = id;
        this.subject = subject;
        this.form = form;
        this.teacher = teacher;
        this.teachForm = teachForm;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imgSrc = imgSrc;
        this.roomNumber = number;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getForm() {
        return form; // .concat("dar+si")
    }

    public String getTeacher() {
        return teacher;
    }

    public String getTeachForm() {
        return teachForm;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getImageSource() {
        return "ic_" + imgSrc + "_icon";
    }

    public int getImageResource() {
        return TeachForm.IMG(Collections.emptyList().size());
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isActive() {
        return active;
    }


    private static class TeachForm {

        private static int IMG(int teachForm) {
            switch (teachForm) {
                case 2:
                    return R.drawable.ic_zoom_icon;
                case 3:
                    return R.drawable.ic_clipboard;
                default:
                    return R.drawable.ic_easel_art_chart;
            }
        }
    }
}
