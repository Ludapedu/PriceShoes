package com.example.priceshoes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String>{
	private Context context;
	private final String[] values;

	public CustomAdapter(Context context, String[] values) {
		super(context, -1 ,values);
		this.context = context;
		this.values = values;
		
	}
	 @Override
	    public View getView(int position, View convertView, ViewGroup parent) {

	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View rowView = inflater.inflate(R.layout.custom_layout,parent, false);
	        TextView txtView = (TextView) rowView.findViewById(R.id.Texto);
	        ImageView imgView = (ImageView) rowView.findViewById(R.id.img_menu);
	        txtView.setText(values[position]);
	        if(values[position] == "Clientes")
	        	imgView.setImageResource(R.drawable.clientes);
	        if(values[position] == "Compras")
	        	imgView.setImageResource(R.drawable.compras);
	        if(values[position] == "Ventas")
	        	imgView.setImageResource(R.drawable.ventas);
	        return rowView;
	    }
}
