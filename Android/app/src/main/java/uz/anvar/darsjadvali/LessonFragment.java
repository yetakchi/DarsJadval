package uz.anvar.darsjadvali;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uz.anvar.darsjadvali.adapter.LessonAdapter;
import uz.anvar.darsjadvali.adapter.OnDataLoadListener;
import uz.anvar.darsjadvali.model.Lesson;
import uz.anvar.darsjadvali.request.LessonsLoader;


public class LessonFragment extends Fragment {

    private LessonAdapter lessonAdapter;

    private final int position;

    private ProgressBar loader;

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
        RecyclerView lessonLayout = view.findViewById(R.id.recycler);
        loader = view.findViewById(R.id.lessons_loader);

        lessonLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        lessonAdapter = new LessonAdapter(getContext(), new ArrayList<>());
        lessonLayout.setAdapter(lessonAdapter);

        lessonLoader("/lessons/" + (position + 1));
    }

    private void lessonLoader(String path) {
        new LessonsLoader(new OnDataLoadListener() {
            @Override
            public void onLessonsLoad(List<Lesson> list) {
                getActivity().runOnUiThread(() -> {
                    loader.setVisibility(View.GONE);
                    lessonAdapter.setLessonsList(list);
                });
            }
        }).onPostExecute(path);
    }
}