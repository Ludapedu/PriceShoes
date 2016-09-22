package com.example.priceshoes;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterLateralMenu extends ArrayAdapter<String>{
	private Context context;
	private final String[] values;
	Typeface font = Typeface.createFromAsset(getContext().getAssets(), "gloriahallelujah.ttf");

	public CustomAdapterLateralMenu(Context context, String[] values) {
		super(context, -1 ,values);
		this.context = context;
		this.values = values;
		
	}
	 @Override
	    public View getView(int position, View convertView, ViewGroup parent) {

	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View rowView = inflater.inflate(R.layout.layout_menu_lateral,parent, false);
	        TextView txtView = (TextView) rowView.findViewById(R.id.Texto);
	        txtView.setTypeface(font);
	        ImageView imgView = (ImageView) rowView.findViewById(R.id.img_menu);
	        txtView.setText(values[position]);
	        if(values[position] == "Clientes")
	        	imgView.setImageResource(R.drawable.clientes);
	        if(values[position] == "Pagos")
	        	imgView.setImageResource(R.drawable.pagos);
	        if(values[position] == "Ventas")
	        	imgView.setImageResource(R.drawable.ventas);
	        if(values[position] == "BackupBD")
	        	imgView.setImageResource(R.drawable.backup);
	        if(values[position] == "RestoreBD")
	        	imgView.setImageResource(R.drawable.restore);
	        return rowView;
	    }
}
