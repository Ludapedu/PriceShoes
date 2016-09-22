package com.example.priceshoes;

import java.util.ArrayList;



import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CustomAdapterClientes extends ArrayAdapter<ListClientes>{
	private Context context;
	private final ArrayList<ListClientes> values;
	private ClienteViewHolder clienteholder;
	Typeface font = Typeface.createFromAsset(getContext().getAssets(), "gloriahallelujah.ttf");
	
	private class ClienteViewHolder
	{
		TextView nombre;
		TextView pedidos;
	}

	public CustomAdapterClientes(Context context, ArrayList<ListClientes> values) {
		super(context, -1 ,values);
		this.context = context;
		this.values = values;
		
	}
	 @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
		 View v = convertView;
		 if(v == null)
		 {
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        v = inflater.inflate(R.layout.layout_list_clientes,null);
	        clienteholder = new ClienteViewHolder();
	        clienteholder.nombre = (TextView)v.findViewById(R.id.TextoListCliente);
	        clienteholder.pedidos = (TextView)v.findViewById(R.id.TextoListPedidos);
	        clienteholder.nombre.setTypeface(font);
	        clienteholder.pedidos.setTypeface(font);
	        v.setTag(clienteholder);
		 }
		 else clienteholder = (ClienteViewHolder)v.getTag();
		 ListClientes c = values.get(position);
		 if(c != null)
		 {
	        	clienteholder.nombre.setText(c.getName());
	        	clienteholder.pedidos.setText(c.getID());
		 }
	        return v;
	    }
}
