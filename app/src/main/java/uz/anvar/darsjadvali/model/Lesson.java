package uz.anvar.darsjadvali.model;

import uz.anvar.darsjadvali.R;

public class Lesson {

    private final int id, teach_form;
    private final String subject, teacher, form, start_time, end_time, img_src, room_number;
    private final boolean active;

    public Lesson(int id, String subject, String form, String teacher, int teach_form, String start_time, String end_time, String img_src, String number, boolean active) {
        this.id = id;
        this.subject = subject;
        this.form = form;
        this.teacher = teacher;
        this.teach_form = teach_form;
        this.start_time = start_time;
        this.end_time = end_time;
        this.img_src = img_src;
        this.room_number = number;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getForm() {
        return form.concat(" darsi");
    }

    public String getTeacher() {
        return teacher;
    }

    public String getTeachForm() {
        return TeachForm.NAME(teach_form);
    }

    public String getStartTime() {
        return start_time;
    }

    public String getEndTime() {
        return end_time;
    }

    public String getImageSource() {
        return "ic_" + img_src + "_icon";
    }

    public int getImageResource() {
        return TeachForm.IMG(teach_form);
    }

    public String getRoomNumber() {
        return room_number;
    }

    public boolean isActive() {
        return active;
    }


    private static class TeachForm {

        private static String NAME(int number) {
            switch (number) {
                case 2:
                    return "Zoom";

                case 3:
                    return "Google Meet";

                default:
                    return "Sinfxona";
            }
        }

        private static int IMG(int teach_form) {
            switch (teach_form) {
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
