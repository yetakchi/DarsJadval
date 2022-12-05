package uz.anvar.darsjadvali.request;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import uz.anvar.darsjadvali.utils.Global;


public class TodayDateLoader extends AsyncTask<String, String, String> {

    TextView today;

    public TodayDateLoader(TextView today) {
        super();
        this.today = today;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            String result = Global.ReadStream(http.getInputStream());
            http.disconnect();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        today.setText(s);
    }
}
