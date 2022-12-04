package uz.anvar.darsjadvali.request;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import uz.anvar.darsjadvali.adapter.WeekDaysAdapter;
import uz.anvar.darsjadvali.model.WeekDay;

public class WeekDaysLoader extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    Context context;
    @SuppressLint("StaticFieldLeak")
    RecyclerView daysRecycler;

    public WeekDaysLoader(Context context, RecyclerView daysRecycler) {
        super();
        this.context = context;
        this.daysRecycler = daysRecycler;
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

        setWeekDays(s);
    }

    private void setWeekDays(String string) {
        List<WeekDay> daysList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(string);
            JSONObject json;
            for (int i = 0; i < jsonArray.length(); i ++) {
                json = jsonArray.getJSONObject(i);
                WeekDay day = new WeekDay(
                        json.getInt("id"),
                        json.getString("day"),
                        json.getString("day_name"),
                        json.getBoolean("active")
                );

                daysList.add(day);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setRecycler(daysList);
    }

    private void setRecycler(List<WeekDay> daysList) {
        WeekDaysAdapter adapter = new WeekDaysAdapter(context, daysList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context,
                RecyclerView.HORIZONTAL, false);

        daysRecycler.setLayoutManager(manager);
        daysRecycler.setAdapter(adapter);
    }
}
