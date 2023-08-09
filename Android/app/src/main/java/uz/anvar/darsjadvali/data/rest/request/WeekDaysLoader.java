package uz.anvar.darsjadvali.data.rest.request;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import uz.anvar.darsjadvali.app.manager.StreamManager;


public class WeekDaysLoader extends AsyncTask<String, String, String> {

    private final StreamManager streamManager;

    private final OnDataLoadListener loadListener;

    public WeekDaysLoader(OnDataLoadListener listener) {
        //noinspection deprecation
        super();

        this.streamManager = new StreamManager();
        this.loadListener = listener;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream input = connection.getInputStream();
            String result = streamManager.ReadStream(input);
            connection.disconnect();
            input.close();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        loadListener.onWeekDaysLoad(s);
    }

    public interface OnDataLoadListener {
        void onWeekDaysLoad(String json);
    }
}
