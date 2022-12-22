package uz.anvar.darsjadvali.request;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import uz.anvar.darsjadvali.adapter.OnDataLoadListener;
import uz.anvar.darsjadvali.model.Lesson;


public class LessonsLoader {

    private final OnDataLoadListener loadListener;

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    private final Type type = new TypeToken<ArrayList<Lesson>>() {
    }.getType();

    public LessonsLoader(OnDataLoadListener listener) {
        loadListener = listener;
    }

    public void onPostExecute(String path) {
        Request request = new Request.Builder()
                .url(Constants.API_URL + path)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

//                    Headers responseHeaders = response.headers();
//                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
//                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                    }

                    assert responseBody != null;
                    String body = responseBody.string();
                    if (body.isEmpty())
                        return;

                    List<Lesson> lessons = gson.fromJson(body, type);
                    loadListener.onLessonsLoad(lessons);
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }
}
