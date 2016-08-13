package com.soumya.sethy.myroommate.recycleView;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.soumya.sethy.myroommate.R;
import com.soumya.sethy.myroommate.models.FlowLayout;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movie> moviesList;
    public Button btn;
    Context ctx;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year;
        public FlowLayout flow_layout;



        public MyViewHolder(View view,Context context) {
            super(view);
            ctx = context;
            title = (TextView) view.findViewById(R.id.title);
            year = (TextView) view.findViewById(R.id.year);
            flow_layout = (FlowLayout) view.findViewById(R.id.flow_layout);
        }
    }


    public MoviesAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView,parent.getContext());
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());
        addChildTo(holder.flow_layout,movie.getNameSpilt());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
    private void addChildTo(final FlowLayout flowLayout, String alldata) {
        flowLayout.removeAllViews();
        final String[] temp_list = alldata.split(",");
        for (int j = 0; j < temp_list.length; j++) {

            btn = new Button(ctx, null, android.R.attr.buttonStyleSmall);
            btn.setHeight(dp2px(18));
            btn.setTextSize(10);
            btn.setTextColor(Color.BLACK);
            // btn.setTextColor(getResources().getColorStateList(R.color.checkable_text_color));
            // btn.setBackgroundResource(R.drawable.checkable_background);
            btn.setText(temp_list[j]);
            btn.setTag(temp_list[j]);
            flowLayout.addView(btn);

            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // v.setTextColor(Color.WHITE);


                }
            });

        }
    }

    public int dp2px(int dpValue) {
        return (int) (dpValue * ctx.getResources().getDisplayMetrics().density);
    }

}
