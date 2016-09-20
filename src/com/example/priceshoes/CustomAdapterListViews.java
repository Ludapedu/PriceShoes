package com.example.priceshoes;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

public class CustomAdapterListViews extends ArrayAdapter<String> implements Filterable{
	private Context context;
	private final ArrayList<String> values;
	Typeface font = Typeface.createFromAsset(getContext().getAssets(), "gloriahallelujah.ttf");

	public CustomAdapterListViews(Context context, ArrayList<String> values) {
		super(context, -1 ,values);
		this.context = context;
		this.values = values;
		
	}
	 @Override
	    public View getView(int position, View convertView, ViewGroup parent) {

	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View rowView = inflater.inflate(R.layout.layout_list_views,parent, false);
	        TextView txtView = (TextView) rowView.findViewById(R.id.TextoListViews);
	        txtView.setTypeface(font);
	        txtView.setText(values.get(position));
	        return rowView;
	    }
}
