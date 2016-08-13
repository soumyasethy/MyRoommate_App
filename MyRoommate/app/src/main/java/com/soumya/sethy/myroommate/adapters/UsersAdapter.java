package com.soumya.sethy.myroommate.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.soumya.sethy.myroommate.Pojo.UserBillDetails;
import com.soumya.sethy.myroommate.R;

public class UsersAdapter extends ArrayAdapter<UserBillDetails> {
    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView amount;
    }

    public UsersAdapter(Context context, ArrayList<UserBillDetails> users) {
       super(context, R.layout.list, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       UserBillDetails userBillDetails = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       ViewHolder viewHolder; // view lookup cache stored in tag
       if (convertView == null) {
          viewHolder = new ViewHolder();
          LayoutInflater inflater = LayoutInflater.from(getContext());
          convertView = inflater.inflate(R.layout.list, parent, false);
          viewHolder.name = (TextView) convertView.findViewById(R.id.name_et);
          viewHolder.amount = (TextView) convertView.findViewById(R.id.amout_et);
          convertView.setTag(viewHolder);
       } else {
           viewHolder = (ViewHolder) convertView.getTag();
       }
       // Populate the data into the template view using the data object
       viewHolder.name.setText(userBillDetails.name);
       viewHolder.amount.setText(userBillDetails.amount);
       // Return the completed view to render on screen
       return convertView;
   }
}
