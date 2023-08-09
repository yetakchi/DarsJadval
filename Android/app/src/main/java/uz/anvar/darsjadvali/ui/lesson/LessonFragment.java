package uz.anvar.darsjadvali.ui.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import uz.anvar.darsjadvali.R;
import uz.anvar.darsjadvali.app.model.Lesson;
import uz.anvar.darsjadvali.data.rest.NetworkConstants;
import uz.anvar.darsjadvali.ui.adapters.LessonAdapter;


public class LessonFragment extends Fragment implements Callback {

    private final Gson gson = new Gson();
    private final OkHttpClient client = new OkHttpClient();

    private final ArrayList<Lesson> dataset = new ArrayList<>();

    private LessonAdapter lessonAdapter;

    private ProgressBar loader;

    private final int position;

    public LessonFragment(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);
        // Inflate the layout for this fragment
        setContentFragment(view);
        return view;
    }

    private void setContentFragment(View view) {
        RecyclerView lessonRecycler = view.findViewById(R.id.recycler);
        loader = view.findViewById(R.id.lessons_loader);

        lessonAdapter = new LessonAdapter(getContext(), dataset);
        lessonRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        lessonRecycler.setAdapter(lessonAdapter);

        onPostExecute(String.format("/lessons/%o", position + 1));
    }

    public void onLessonListLoad(List<Lesson> lessonsList) {
        loader.setVisibility(View.GONE);

        dataset.clear();
        dataset.addAll(lessonsList);
        lessonAdapter.updateUI();
    }

    public void onPostExecute(String path) {
        Request request = new Request.Builder()
                .url(NetworkConstants.API_URL + path)
                .build();
        client.newCall(request).enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        try (ResponseBody responseBody = response.body()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

                /*
                *
                Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                 */

            assert responseBody != null;
            String body = responseBody.string();
            if (body.isEmpty())
                return;

            TypeToken<ArrayList<Lesson>> typeToken = new TypeToken<ArrayList<Lesson>>() {
            };
            final Type type = typeToken.getType();
            List<Lesson> lessons = gson.fromJson(body, type);
            requireActivity().runOnUiThread(() -> onLessonListLoad(lessons));
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        e.printStackTrace();
    }
}