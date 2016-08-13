package com.soumya.sethy.myroommate.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soumya.sethy.myroommate.Pojo.DataObject_CardsView;
import com.soumya.sethy.myroommate.R;

import java.util.ArrayList;

/**
 * Created by soumya on 7/17/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject_CardsView> mDataset;
    private static MyClickListener myClickListener;
Context ctx;
    public static class DataObjectHolder extends RecyclerView.ViewHolder
           /* implements View
            .OnClickListener*/ {
        TextView label;
        TextView dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            //itemView.setOnClickListener(this);
        }

        //@Override
      /* public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }*/
    }

   /* public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }*/

    public MyRecyclerViewAdapter(ArrayList<DataObject_CardsView> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recycle, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }



    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getmText1());

        holder.dateTime.setText("Rs."+mDataset.get(position).getmText2());
        double var=0;
        try
        {
            var =Double.valueOf(mDataset.get(position).getmText2());}
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (var>0)
        {
            holder.dateTime.setTextColor(ContextCompat.getColor(ctx, R.color.green));
        }
        else
        {
            holder.dateTime.setTextColor(ContextCompat.getColor(ctx, R.color.red));
        }
    }

    public void addItem(DataObject_CardsView dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
