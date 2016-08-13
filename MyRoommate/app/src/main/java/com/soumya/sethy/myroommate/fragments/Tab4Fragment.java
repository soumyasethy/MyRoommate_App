package com.soumya.sethy.myroommate.fragments;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.soumya.sethy.myroommate.Pojo.DataObject_CardsView;
import com.soumya.sethy.myroommate.R;
import com.soumya.sethy.myroommate.adapters.MyRecyclerViewAdapter;
import com.soumya.sethy.myroommate.db.DbHelper;

public class Tab4Fragment extends Fragment {
	DbHelper dbhelper;
	ListView result;
	DbHelper db;
	HashMap hm;
	List printBill;
	ArrayAdapter<String> listAdapter;
	Cursor c1 = null;
	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String> billReview;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.tab4fragment, container, false);
		result = (ListView) v.findViewById(R.id.payment);

		db = new DbHelper(getActivity());
		printBill = new ArrayList();
		billReview = new ArrayList<String>();
		listAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, billReview);

		try {
			c1 = db.getExpense();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		hm = new HashMap();

		if (c1 != null) {
			c1.moveToFirst();
			if (c1.getCount() != 0) {

				for (int i = 0; i < c1.getCount(); i++) {
					String Name = (c1.getString(c1.getColumnIndex("_id")));
					String Amount = (c1.getString(c1.getColumnIndex("AMOUNT")));
					// Adding values to the ArrayList
					hm.put(Name, Double.parseDouble(Amount));
					c1.moveToNext();
				}
				/*hm.put("A", -5.0);
				hm.put("B", 25.0);
				hm.put("C", -20.0);*/
				/*hm.put("D", 25.0);
				hm.put("E", -20.0);
				hm.put("F", -5.0);*/
				findPath(hm);

                billReview.addAll(printBill);


                result.setAdapter(listAdapter);
			}
		}

		mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
		mRecyclerView.setHasFixedSize(true);
		mLayoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mAdapter);
		/*((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
				.MyClickListener() {
			@Override
			public void onItemClick(int position, View v) {
				Toast.makeText(getActivity(),position,1000).show();
			}
		});*/

		return v;
	}

	public void findPath(HashMap details){

		Double Max_Value = (Double)Collections.max(details.values());;
		Double Min_Value = (Double)Collections.min(details.values());
		if(Max_Value !=Min_Value){
		String Max_Key =	getKeyFromValue(details, Max_Value).toString();
		String Min_Key =	getKeyFromValue(details,Min_Value).toString();
		Double result = Max_Value +Min_Value;
		result = round(result,1);
		/*double zero = result-Double.valueOf(Max_Value);
		zero = round(zero,1);*/
			if ((result >=0.0)/*&&!(zero==0.0)*/){
				printBill.add(Min_Key + " needs to pay "+ Max_Key + ":"	+ round(Math.abs(Min_Value), 2));
				details.remove(Max_Key);
				details.remove(Min_Key);
				details.put(Max_Key, result);
				details.put(Min_Key, 0.0);
			}
			/*else if (zero==0.0){
				double amt =round(Math.abs(Max_Value/details.size()), 2);
				printBill.add(Min_Key + " needs to pay "+ Max_Key + ": Rs."	+amt );
				details.remove(Max_Key);
				details.remove(Min_Key);
				double newVal = Double.valueOf(Max_Value)-amt;
				newVal=round(newVal,2);
				//details.put(Max_Key, result);
				details.put(Max_Key, newVal);
				//details.put(Min_Key, 0.0);

			}*/
			else {
				printBill.add(Min_Key + " needs to pay "+ Max_Key + ":"	+ round(Math.abs(Max_Value), 2));
				details.remove(Max_Key);
				details.remove(Min_Key);
				details.put(Max_Key,0.0 );
				details.put(Min_Key, result);
			}
			findPath(details);
		}

		}
	public static Object getKeyFromValue(HashMap hm, Double value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
	return  null;}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	@Override
	public void onResume() {
		Log.e("DEBUG", "Resume Tab4");
		super.onResume();
		if (c1!=null){
			listAdapter.notifyDataSetChanged();}

	}

    private ArrayList<DataObject_CardsView> getDataSet() {
        ArrayList results = new ArrayList<DataObject_CardsView>();
        for (int index = 0; index < billReview.size(); index++) {
            String str = billReview.get(index);
            String[] rand_str = str.split(":");
            DataObject_CardsView obj = new DataObject_CardsView(rand_str[0],rand_str[1]);
            results.add(index, obj);
        }
        return results;
    }
}
