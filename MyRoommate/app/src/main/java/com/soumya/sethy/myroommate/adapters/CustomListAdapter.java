/**
 *
 */
package com.soumya.sethy.myroommate.adapters;

/**
 * @author soumya
 */

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.soumya.sethy.myroommate.R;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    public static HashMap<String, String> data = new HashMap<String, String>();

    public CustomListAdapter(Activity context, String[] itemname) {
        super(context, R.layout.popup_layout, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemname = itemname;

    }

    public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.popup_layout, null, true);

            //TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);
            final MaterialEditText extratxt = (MaterialEditText) rowView
                    .findViewById(R.id.editText1);

            //txtTitle.setText(itemname[position]);
            extratxt.setHint("Enter paid by " + itemname[position]);
            extratxt.setFloatingLabelText("Amount given by " + itemname[position] + ":");
            extratxt.setFloatingLabel((int) 1L);
            //extratxt.setFloatingLabelAlwaysShown(true);
            extratxt.setTag(itemname[position]);

        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.SHOW_IMPLICIT);

        if (itemname[position].equalsIgnoreCase("Multiple People")) {extratxt.setVisibility(View.GONE);}

            extratxt.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if (extratxt.getText().toString().trim().length() > 0) {
                        data.put(extratxt.getTag().toString(), s.toString());

                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                }
            });

        return rowView;

    }

}