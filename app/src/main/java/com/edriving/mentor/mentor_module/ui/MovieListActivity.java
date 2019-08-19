package com.edriving.mentor.mentor_module.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edriving.mentor.mentor_module.R;
import com.edriving.mentor.modulessdk.network.model.MentorModule;

import java.util.ArrayList;

/**
 * Created by Arsalan Bahojb on 2019-08-15.
 * eDriving Ltd
 * Arsalan.Bahojb@edriving.com
 */
public class MovieListActivity extends AppCompatActivity {
    private RecyclerView movieListRecyclerView;
    private ProgressBar progressBar;
    private TextView emptyListMessage;
    private MovieAdaptor adaptor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        movieListRecyclerView = findViewById(R.id.movie_list_view);
        progressBar = findViewById(R.id.progress_bar);
        emptyListMessage = findViewById(R.id.list_empty_message);
        if (getIntent().hasExtra("movie-list")) {
            ArrayList<MentorModule> mentorModules = (ArrayList<MentorModule>) getIntent().getSerializableExtra("movie-list");
            if (mentorModules != null) {
                if (mentorModules.size() > 0) {
                    adaptor = new MovieAdaptor(mentorModules);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    movieListRecyclerView.setLayoutManager(layoutManager);
                    movieListRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    movieListRecyclerView.setAdapter(adaptor);
                    DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(movieListRecyclerView.getContext(), LinearLayout.VERTICAL);
                    mDividerItemDecoration.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.divider));
                    movieListRecyclerView.addItemDecoration(mDividerItemDecoration);
                } else {
                    emptyListMessage.setVisibility(View.VISIBLE);
                }
            }
        }

    }

}
