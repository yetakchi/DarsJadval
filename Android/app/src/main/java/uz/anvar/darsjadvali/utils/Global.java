package uz.anvar.darsjadvali.utils;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import uz.anvar.darsjadvali.model.Lesson;


public class Global {

    public static List<Lesson> lessons = Collections.emptyList();

    public static String ReadStream(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                // if (reader.ready()) // builder.append('\n');
            }
            reader.close();

            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }

    public static void dialog(Context context, Activity activity) {
        new AlertDialog.Builder(context)
                .setTitle("Dasturdan chiqish")
                .setMessage("Dasturdan chiqmoqchimisiz?")
                .setPositiveButton("Ha", (dialog, which) -> {
                    activity.finish(); // Agar birinchi ekran bo'lsa
                    // finishAffinity(); or System.exit(0); // Har qanday o'tishlardan ham keyin
                })
                .setNegativeButton("Yo\u2018q", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
