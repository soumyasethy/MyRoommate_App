package com.soumya.sethy.myroommate.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soumya.sethy.myroommate.R;
import com.tokenautocomplete.TokenCompleteTextView;

public class ContactsCompletionView extends TokenCompleteTextView<PersonAutoText> {
	public ContactsCompletionView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected View getViewForObject(PersonAutoText person) {

		LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		TextView view = (TextView) l.inflate(R.layout.contact_token, (ViewGroup) getParent(), false);
		view.setText(person.getName());

		return view;
	}

	@Override
	protected PersonAutoText defaultObject(String completionText) {
		//Stupid simple example of guessing if we have an email or not
		int index = completionText.indexOf(' ');/*@*/
		if (index == -1) {
			return new PersonAutoText(completionText/*, completionText.replace(" ", "") + "@example.com"*/);
		} else {
			return new PersonAutoText(completionText.substring(0, index)/*, completionText*/);
		}
	}
}