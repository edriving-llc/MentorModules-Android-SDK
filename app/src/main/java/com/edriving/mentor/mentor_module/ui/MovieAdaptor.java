package com.edriving.mentor.mentor_module.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edriving.mentor.mentor_module.R;
import com.edriving.mentor.modulessdk.network.model.MentorModule;

import java.util.List;

import static com.edriving.mentor.modulessdk.util.Utility.getDateFromTimeStamp;


/**
 * Created by Arsalan Bahojb on 2019-08-15.
 * eDriving Ltd
 * Arsalan.Bahojb@edriving.com
 */
public class MovieAdaptor extends RecyclerView.Adapter<MovieAdaptor.MovieViewHolder> {


    private List<MentorModule> courseList;

    MovieAdaptor(List<MentorModule> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item_view, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.title.setText(courseList.get(position).getName());
        holder.category.setText(courseList.get(position).getCategory());
        holder.expiry.setText( getDateFromTimeStamp(courseList.get(position).getLinkExpireOn()));
        holder.link.setText(courseList.get(position).getLink());

    }


    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView category;
        TextView link;
        TextView expiry;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_name);
            category = itemView.findViewById(R.id.category_name);
            expiry = itemView.findViewById(R.id.expire_date);
            link = itemView.findViewById(R.id.movie_link);
            link.setOnClickListener(view -> {
                String expiryTimeStamp = expiry.getText().toString();
                if(expiryTimeStamp.compareTo(getDateFromTimeStamp(System.currentTimeMillis()))<0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.getText().toString()));
                    itemView.getContext().startActivity(intent);
                } else {
                    Toast.makeText(itemView.getContext(),"The links have been expired, Please refresh the list ",Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}
