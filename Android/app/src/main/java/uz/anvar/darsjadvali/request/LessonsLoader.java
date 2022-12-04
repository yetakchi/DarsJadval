package uz.anvar.darsjadvali.request;

import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import uz.anvar.darsjadvali.LessonActivity;
import uz.anvar.darsjadvali.model.Lesson;


public class LessonsLoader extends AsyncTask<String, String, String> {

    public LessonsLoader() {
        super();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream input = connection.getInputStream();
            String result = Methods.ReadStream(input);
            connection.disconnect();
            input.close();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        setLessonsList(s);
    }

    private void setLessonsList(String string) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(string);
            JSONObject json;
            for (int i = 0; i < jsonArray.length(); i ++) {
                json = jsonArray.getJSONObject(i);
                lessons.add(new Lesson(
                        i + 1,
                        json.getString("subject"),
                        json.getString("form"),
                        json.getString("teacher"),
                        json.getInt("teach_form"),
                        json.getString("start_time"),
                        json.getString("end_time"),
                        "",
                        json.getString("room"),
                        json.getBoolean("active")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setRecycler(lessons);
    }

    private void setRecycler(List<Lesson> lessonsList) {
        LessonActivity.lessons_loader.setVisibility(View.GONE);

        LessonActivity.lessonAdapter.setLessonsList(lessonsList);
        LessonActivity.lessonAdapter.notifyDataSetChanged();
    }
}
