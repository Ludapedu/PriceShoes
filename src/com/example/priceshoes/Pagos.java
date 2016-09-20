package com.example.priceshoes;

import java.util.ArrayList;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Pagos extends Activity {

	ListView ListaPagos;
	ImageButton btn_Pagos_Pendientes;
	ImageButton btn_Pagos_Realizados;
	TextView lbl_MontoMostrar;
	TextView lbl_LeyendaMostrar;
	TextView lbl_Pagos_Pagos_General;
	private ArrayList<String> PagosTotal = new ArrayList<String>();
	private ArrayList<String> PagosPendiente = new ArrayList<String>();
	int MontoTotalPagado = 0;
	int MontoTotalSinCobrar = 0;
	Util oper = new Util();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBar("");
		setContentView(R.layout.activity_pagos);	
		
		Typeface font = Typeface.createFromAsset(getAssets(), "gloriahallelujah.ttf");
		
		ListaPagos = (ListView) findViewById(R.id.lista_Pagos_Todos);
		btn_Pagos_Realizados = (ImageButton) findViewById(R.id.btn_Pagos_Realizados);
		btn_Pagos_Pendientes = (ImageButton) findViewById(R.id.btn_Pagos_Pendientes);
		lbl_MontoMostrar = (TextView) findViewById(R.id.lbl_Dialogo_MarcaMostrar);
		lbl_LeyendaMostrar = (TextView) findViewById(R.id.txt_Pagos_LeyendaMostrar);
		lbl_Pagos_Pagos_General = (TextView) findViewById(R.id.lbl_Pagos_Pagos_General);
		
		lbl_MontoMostrar.setTypeface(font);
		lbl_LeyendaMostrar.setTypeface(font);
		lbl_Pagos_Pagos_General.setTypeface(font);
		
		BaseDatosPagos BDPagos = new BaseDatosPagos(this, "Pagos", null, 1);
		SQLiteDatabase Pagos = BDPagos.getWritableDatabase();		
		Cursor c = Pagos.rawQuery("SELECT Cliente, Fecha, Monto FROM Pagos", null);
		MontoTotalPagado = 0;
		if(c.moveToFirst())
		{
			do
			{
				PagosTotal.add(c.getString(0) + " - " + c.getString(1) + " - $" + c.getString(2));
				MontoTotalPagado += Integer.parseInt(c.getString(2));
			}while(c.moveToNext());
		}
		c.close();
		BDPagos.close();
		
		ListaPagos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PagosTotal));
		lbl_MontoMostrar.setText("$ " + oper.comas(MontoTotalPagado));
		
		btn_Pagos_Pendientes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int MontoPendiente = 0;
				int MontoAbonado = 0;
				int Total = 0;
				lbl_LeyendaMostrar.setText("Pendiente por Cobrar");
				MontoTotalSinCobrar = 0;
				try
				{
				PagosPendiente.clear();
				BaseDatosPagos BDPagos = new BaseDatosPagos(getApplicationContext(), "Pagos", null, 1);
				BaseDatosClientes BDClientes = new BaseDatosClientes(getApplicationContext(), "Clientes", null, 1);
				BaseDatosVentas BDVentas = new BaseDatosVentas(getApplicationContext(), "Ventas", null, 1);
				SQLiteDatabase Pagos = BDPagos.getWritableDatabase();		
				SQLiteDatabase Clientes = BDClientes.getWritableDatabase();		
				SQLiteDatabase Ventas = BDVentas.getWritableDatabase();	
				Cursor clientes = Clientes.rawQuery("SELECT Nombre FROM Clientes", null);
				if(clientes.moveToFirst())
				{
					do
					{
						Cursor pagos = Pagos.rawQuery("SELECT Monto FROM Pagos WHERE Cliente = '" + clientes.getString(0) + "'" , null);	
						if(pagos.moveToFirst())
						{
							do
							{
								MontoAbonado += Integer.parseInt(pagos.getString(0));			
							}while(pagos.moveToNext());
						}
						pagos.close();
						Cursor ventas = Ventas.rawQuery("SELECT Precio FROM Ventas WHERE Cliente = '" + clientes.getString(0) + "'", null);
						if(ventas.moveToFirst())
						{
							do
							{
								MontoPendiente += Integer.parseInt(ventas.getString(0));			
							}while(ventas.moveToNext());
						}
						ventas.close();
						Total = MontoPendiente - MontoAbonado;
						MontoTotalSinCobrar += Total;
						if (Total != 0)
						PagosPendiente.add(clientes.getString(0) + "  $" + String.valueOf(Total));
						MontoPendiente = 0;
						MontoAbonado = 0;
						Total = 0;
					}while(clientes.moveToNext());
					
				}
				clientes.close();
				BDClientes.close();
				BDVentas.close();
				BDPagos.close();
				ListaPagos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PagosPendiente));
				btn_Pagos_Pendientes.setVisibility(View.INVISIBLE);			
				btn_Pagos_Realizados.setVisibility(View.VISIBLE);
				lbl_MontoMostrar.setText("$ " + oper.comas(MontoTotalSinCobrar));
				}
				catch(Exception ex)
				{
					Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
				}
				
				
			}
		});
		
		btn_Pagos_Realizados.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lbl_LeyendaMostrar.setText("Monto Abonado");
				try
				{
					MontoTotalPagado = 0;
					BaseDatosPagos BDPagos = new BaseDatosPagos(getApplicationContext(), "Pagos", null, 1);
					SQLiteDatabase Pagos = BDPagos.getWritableDatabase();	
					PagosTotal.clear();
					Cursor c = Pagos.rawQuery("SELECT Cliente, Fecha, Monto FROM Pagos", null);
					if(c.moveToFirst())
					{
						do
						{
							PagosTotal.add(c.getString(0) + " - " + c.getString(1) + " - $" + c.getString(2));		
							MontoTotalPagado += Integer.parseInt(c.getString(2));
						}while(c.moveToNext());
					}
					c.close();
					BDPagos.close();
					
					ListaPagos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PagosTotal));
					lbl_MontoMostrar.setText("$ " + oper.comas(MontoTotalPagado));
					btn_Pagos_Pendientes.setVisibility(View.VISIBLE);			
					btn_Pagos_Realizados.setVisibility(View.INVISIBLE);
				}
				catch(Exception ex)
				{
					Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
				}
				
				
			}
		});
	}
	
	public void setActionBar(String heading) {
	    // TODO Auto-generated method stub

	    ActionBar actionBar = getActionBar();
	    actionBar.setHomeButtonEnabled(false);
	    actionBar.setDisplayHomeAsUpEnabled(false);
	    actionBar.setDisplayShowTitleEnabled(false);
	    actionBar.setDisplayShowHomeEnabled(false);
	    //actionBar.setTitle(heading);
	    actionBar.show();
		
		 /*ActionBar actionBar = getActionBar();
		    actionBar.setDisplayOptions(actionBar.getDisplayOptions()
		            | ActionBar.DISPLAY_SHOW_CUSTOM);
		    ImageView imageView = new ImageView(actionBar.getThemedContext());
		    imageView.setScaleType(ImageView.ScaleType.CENTER);
		    imageView.setImageResource(R.drawable.clients);
		    ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
		            ActionBar.LayoutParams.WRAP_CONTENT,
		            ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
		                    | Gravity.CENTER_VERTICAL);
		    layoutParams.rightMargin = 40;
		    imageView.setLayoutParams(layoutParams);
		    actionBar.setCustomView(imageView);*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pagos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.MenuClientes:
		{
			Intent i = new Intent(this, Clientes.class);
			startActivity(i);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}
		case R.id.MenuResumen:
		{
			Intent i = new Intent(this, Resumen.class);
			startActivity(i);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
