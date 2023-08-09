package uz.anvar.darsjadvali.data.mapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uz.anvar.darsjadvali.app.model.WeekDay;


public class WeekDayMapper {

    public List<WeekDay> invoke(String data) {
        List<WeekDay> daysList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject json;
            for (int i = 0; i < jsonArray.length(); i++) {
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

        return daysList;
    }
}
