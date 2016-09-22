package com.example.priceshoes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout.LayoutParams;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;

public class Resumen extends Activity implements OnItemClickListener{

	ListView Lista_Resumen_Cambios;
	ListView Lista_Resumen_Compras;
	private ArrayList<String> PedidosCliente = new ArrayList<String>();
	private ArrayList<Integer> totalventas = new ArrayList<Integer>();
	private ArrayList<Integer> totalcompras = new ArrayList<Integer>();
	private ArrayList<Integer> totalabonos = new ArrayList<Integer>();
	private ArrayList<String> ComprasClientes = new ArrayList<String>();
	TextView lbl_Total_Ventas;
	TextView lbl_Total_Compras;
	TextView lbl_Ganancia;
	TextView lbl_MontoAbonado;
	TextView lbl_Dialogo_Marca;
	TextView lbl_Dialogo_Numero;
	TextView lbl_Dialogo_ID;
	TextView lbl_Dialogo_Costo;
	TextView lbl_Dialogo_Precio;
	TextView lbl_Resumen_TotalCompras_Total;
	TextView lbl_Resumen_Cambios;
	TextView lbl_Resumen_Total_Ventas;
	TextView lbl_RCliente_Monto_Pendiente_Total;
	TextView lbl_Pagos_Pagos_General;
	TextView lbl_Dialogo_MarcaMostrar;
	ImageView img_Price;
	EditText txt_Marca_Dialogo;
	EditText txt_ID_Dialogo;
	EditText txt_Numero_Dialogo;
	EditText txt_Precio_Dialogo;
	EditText txt_Costo_Dialogo;
	TextView lbl_Cliente_Dialogo;
	TextView lbl_Compras_Monto_Comprar;
	TextView lbl_Compras_Numero_Pendientes;
	TextView lbl_Cambios_Numero_Pendientes;
	TextView lbl_Resumen_Compras;
	private DrawerLayout mDrawer;
	private ListView mDrawerOptions;
	Dialog DialogoResumen;
	public int montocomprar = 0;
	public String pathBDVentas;
	public String pathBDPagos;
	public String pathBDClientes;
	Util oper = new Util();
	private static final String[] values = {"Clientes" , "Pagos"};
	public Context contexto = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resumen);
		setActionBar("Resumen");
		
		
		Typeface font = Typeface.createFromAsset(getAssets(), "gloriahallelujah.ttf");
		
		DialogoResumen = new Dialog(this,R.style.Theme_Dialog_Translucent);
		DialogoResumen .requestWindowFeature(Window.FEATURE_NO_TITLE);
		DialogoResumen.setCancelable(false);
		DialogoResumen.setContentView(R.layout.dialogo_resumen_pedido);
		ImageButton btn = (ImageButton) DialogoResumen.findViewById(R.id.btn_Dialogo);
		txt_Marca_Dialogo = (EditText) DialogoResumen.findViewById(R.id.txt_Dialogo_Marca);
		txt_ID_Dialogo = (EditText) DialogoResumen.findViewById(R.id.txt_Dialogo_ID);
		txt_Numero_Dialogo = (EditText) DialogoResumen.findViewById(R.id.txt_Dialogo_Numero);
		txt_Precio_Dialogo = (EditText) DialogoResumen.findViewById(R.id.txt_Dialogo_Precio);
		txt_Costo_Dialogo = (EditText) DialogoResumen.findViewById(R.id.txt_Dialogo_Costo);
		lbl_Cliente_Dialogo = (TextView) DialogoResumen.findViewById(R.id.lbl_Dialogo_Cliente);
		lbl_Dialogo_Marca = (TextView) DialogoResumen.findViewById(R.id.lbl_Dialogo_MarcaMostrar);
		lbl_Dialogo_Numero = (TextView) DialogoResumen.findViewById(R.id.lbl_Dialogo_NumeroMostrar);
		lbl_Dialogo_ID = (TextView) DialogoResumen.findViewById(R.id.lbl_Dialogo_MostrarID);
		lbl_Dialogo_Costo = (TextView) DialogoResumen.findViewById(R.id.lbl_Dialogo_MostrarCosto);
		lbl_Dialogo_Precio = (TextView) DialogoResumen.findViewById(R.id.lbl_Dialogo_MostrarPrecio);
		
		txt_Marca_Dialogo.setEnabled(false);
		txt_Numero_Dialogo.setEnabled(false);
		txt_ID_Dialogo.setEnabled(false);
		txt_Costo_Dialogo.setEnabled(false);
		txt_Precio_Dialogo.setEnabled(false);
		
		lbl_Cliente_Dialogo.setTypeface(font);
		lbl_Dialogo_Marca.setTypeface(font);
		lbl_Dialogo_Numero.setTypeface(font);
		lbl_Dialogo_ID.setTypeface(font);
		lbl_Dialogo_Costo.setTypeface(font);
		lbl_Dialogo_Precio.setTypeface(font);
		
		
		
		mDrawerOptions = (ListView) findViewById(R.id.left_drawer);
		mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerOptions.setAdapter(new CustomAdapterLateralMenu(getApplicationContext(), values));
		mDrawerOptions.setOnItemClickListener(this);
		
		Lista_Resumen_Cambios = (ListView) findViewById(R.id.Lista_Resumen_Cambios);
		Lista_Resumen_Compras = (ListView) findViewById(R.id.Lista_Resumen_Compras);
		lbl_Total_Ventas = (TextView) findViewById(R.id.lbl_Resumen_MontoVentas);
		lbl_Total_Compras = (TextView) findViewById(R.id.lbl_Resumen_TotalCompras);
		lbl_Ganancia = (TextView) findViewById(R.id.lbl_Resumen_Ganancia);
		lbl_MontoAbonado = (TextView) findViewById(R.id.lbl_Resumen_MontoAbonado);
		lbl_Compras_Monto_Comprar = (TextView) findViewById(R.id.lbl_Resumen_Total_Compras);
		lbl_Compras_Numero_Pendientes = (TextView) findViewById(R.id.txt_Resumen_Numero_Compras);
		lbl_Cambios_Numero_Pendientes = (TextView) findViewById(R.id.txt_Resumen_Numero_Cambios);
		lbl_Resumen_Compras = (TextView) findViewById(R.id.lbl_Resumen_Compras);
		lbl_Resumen_TotalCompras_Total = (TextView) findViewById(R.id.lbl_Resumen_TotalCompras_Total);
		lbl_Resumen_Cambios = (TextView) findViewById(R.id.lbl_Resumen_Cambios);
		lbl_Resumen_Total_Ventas =  (TextView) findViewById(R.id.lbl_Resumen_Total_Ventas);
		lbl_RCliente_Monto_Pendiente_Total =  (TextView) findViewById(R.id.lbl_RCliente_Monto_Pendiente_Total);
		lbl_Pagos_Pagos_General = (TextView) findViewById(R.id.lbl_Pagos_Pagos_General);
		lbl_Dialogo_MarcaMostrar = (TextView) findViewById(R.id.lbl_Dialogo_MarcaMostrar);
		
		
		lbl_Resumen_Compras.setTypeface(font);
		lbl_Cambios_Numero_Pendientes.setTypeface(font);
		lbl_Compras_Monto_Comprar.setTypeface(font);
		lbl_Compras_Numero_Pendientes.setTypeface(font);
		lbl_Ganancia.setTypeface(font);
		lbl_MontoAbonado.setTypeface(font);
		lbl_Resumen_Compras.setTypeface(font);
		lbl_Total_Compras.setTypeface(font);
		lbl_Total_Ventas.setTypeface(font);
		lbl_Resumen_TotalCompras_Total.setTypeface(font);
		lbl_Resumen_Cambios.setTypeface(font);
		lbl_Resumen_Total_Ventas.setTypeface(font);
		lbl_RCliente_Monto_Pendiente_Total.setTypeface(font);
		lbl_Pagos_Pagos_General.setTypeface(font);
		lbl_Dialogo_MarcaMostrar.setTypeface(font);
		
		try
		{
			
			btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					txt_Marca_Dialogo.setText("");
					txt_ID_Dialogo.setText("");
					txt_Numero_Dialogo.setText("");
					txt_Precio_Dialogo.setText("");
					txt_Costo_Dialogo.setText("");
					lbl_Cliente_Dialogo.setText("");
					DialogoResumen.dismiss();
					
				}
			});
			
			Lista_Resumen_Compras.setOnItemClickListener(new OnItemClickListener() {

				@SuppressWarnings("deprecation")
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					String registro = Lista_Resumen_Compras.getItemAtPosition(position).toString();
					BaseDatosVentas BDCompras = new BaseDatosVentas(getApplicationContext(), "Ventas", null, 1);
					SQLiteDatabase Compras = BDCompras.getWritableDatabase();
					Cursor compras = Compras.rawQuery("SELECT ID, Catalogo, Pagina, Marca, Precio, Costo, Cliente, Numero FROM Ventas WHERE Entregado = 'Sin Entregar' AND IDREG = " + registro.substring(0,registro.indexOf("|")), null);
					if(compras.moveToFirst())
					{
						do
						{
							
							txt_Marca_Dialogo.setText((compras.getString(3)));
							txt_ID_Dialogo.setText(compras.getString(0));
							txt_Numero_Dialogo.setText(compras.getString(7));
							txt_Precio_Dialogo.setText("$ " + compras.getString(4));
							txt_Costo_Dialogo.setText("$ " + compras.getString(5));
							lbl_Cliente_Dialogo.setText((compras.getString(6)));
						}while(compras.moveToNext());
					}
					compras.close();
					BDCompras.close();
					DialogoResumen.show();	
					Window window = DialogoResumen.getWindow();
					window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				}
			});
			
			Lista_Resumen_Cambios.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					String registro = Lista_Resumen_Cambios.getItemAtPosition(position).toString();
					BaseDatosVentas BDVentas = new BaseDatosVentas(getApplicationContext(), "Ventas", null, 1);
					SQLiteDatabase Cambios = BDVentas.getWritableDatabase();
					Cursor fila = Cambios.rawQuery("SELECT ID, Catalogo, Pagina, Marca, Precio, Costo, Cliente, Numero FROM Ventas WHERE Entregado = 'Cambio' AND IDREG = " + registro.substring(0,registro.indexOf("|")), null);
					if(fila.moveToFirst())
					{
						do
						{
							txt_Marca_Dialogo.setText((fila.getString(3)));
							txt_ID_Dialogo.setText(fila.getString(0));
							txt_Numero_Dialogo.setText(fila.getString(7));
							txt_Precio_Dialogo.setText("$ " + fila.getString(4));
							txt_Costo_Dialogo.setText("$ " + fila.getString(5));
							lbl_Cliente_Dialogo.setText((fila.getString(6)));
						}while(fila.moveToNext());
					}
					fila.close();
					BDVentas.close();
					DialogoResumen.show();	
					Window window = DialogoResumen.getWindow();
					window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				}
			});
			
		BaseDatosVentas BDCambios = new BaseDatosVentas(this, "Ventas", null, 1);
		pathBDVentas = BDCambios.path();		
		SQLiteDatabase Cambios = BDCambios.getWritableDatabase();
		Cursor fila = Cambios.rawQuery("SELECT IDREG, ID, Cliente, Precio, Costo FROM Ventas WHERE Entregado = 'Cambio'", null);
		PedidosCliente.clear();
		int numcambios = 0;
		if(fila.moveToFirst())
		{
			do
			{
				numcambios ++;
				PedidosCliente.add(fila.getString(0) + "| " + fila.getString(1) + " - " + fila.getString(2));
			}while(fila.moveToNext());
		}
		fila.close();
		BDCambios.close();
		Lista_Resumen_Cambios.setAdapter(new CustomAdapterListViews(getApplicationContext(), PedidosCliente));
		lbl_Cambios_Numero_Pendientes.setText(String.valueOf(numcambios));
		
		BaseDatosVentas BDMontos = new BaseDatosVentas(this, "Ventas", null, 1);
		SQLiteDatabase Montos = BDMontos.getWritableDatabase();
		Cursor montos = Montos.rawQuery("SELECT Precio, Costo FROM Ventas", null);	
		if(montos.moveToFirst())
		{
			do
			{
				totalventas.add(Integer.parseInt(montos.getString(0)));
				totalcompras.add(Integer.parseInt(montos.getString(1)));
			}while(montos.moveToNext());
		}
		montos.close();
		BDMontos.close();
		
		int totalventaslbl = 0;
		for (int x=0; x < totalventas.size(); x++)
		{
			totalventaslbl += totalventas.get(x);
		}
		int totalcompraslbl = 0;
		for (int x=0; x < totalcompras.size(); x++)
		{
			totalcompraslbl += totalcompras.get(x);
		}
		lbl_Total_Ventas.setText("$ " + oper.comas(totalventaslbl));
		lbl_Total_Compras.setText("$ " + oper.comas(totalcompraslbl));
		int gan = 0;
		gan = totalventaslbl - totalcompraslbl;
		lbl_Ganancia.setText("$ " + oper.comas(gan));
		
		montocomprar = 0;
		BaseDatosVentas BDCompras = new BaseDatosVentas(this, "Ventas", null, 1);
		SQLiteDatabase Compras = BDCompras.getWritableDatabase();
		Cursor compras = Compras.rawQuery("SELECT IDREG, ID, Cliente, Costo FROM Ventas WHERE Entregado = 'Sin Entregar'", null);
		ComprasClientes.clear();
		int numcompras = 0;
		if(compras.moveToFirst())
		{
			do
			{
				numcompras ++;
				montocomprar += Integer.parseInt(compras.getString(3));
				ComprasClientes.add(compras.getString(0) + "| " + compras.getString(1) + " - " + compras.getString(2));//"ID: " +  compras.getString(0) + " Catalogo: " + compras.getString(1) + " Pagina: " + compras.getString(2) +  " Marca: " + compras.getString(3) + " Precio: " + compras.getString(4) + " Costo: " + compras.getString(5) + " Cliente: " + compras.getString(6) + " Numero: " + compras.getString(7));
			}while(compras.moveToNext());
		}
		compras.close();
		BDCompras.close();
		Lista_Resumen_Compras.setAdapter(new CustomAdapterListViews(getApplicationContext(), ComprasClientes));
		lbl_Compras_Monto_Comprar.setText("$ " + oper.comas(montocomprar));
		lbl_Compras_Numero_Pendientes.setText(String.valueOf(numcompras));
		
		BaseDatosPagos BDPagos = new BaseDatosPagos(this, "Pagos", null, 1);
		pathBDPagos = BDPagos.path();
		SQLiteDatabase Pagos = BDPagos.getWritableDatabase();		
		Cursor c = Pagos.rawQuery("SELECT Monto FROM Pagos", null);
		if(c.moveToFirst())
		{
			do
			{
				totalabonos.add(Integer.parseInt(c.getString(0)));				
			}while(c.moveToNext());
		}
		c.close();
		BDPagos.close();
		int abonos = 0;
		for (int x=0; x < totalabonos.size(); x++)
		{
			abonos += totalabonos.get(x);
		}
		lbl_MontoAbonado.setText("$ " + oper.comas(abonos));
		
		BaseDatosClientes BDClientes = new BaseDatosClientes(this, "Clientes", null,1);
		pathBDClientes = BDClientes.path();
		}
		
		catch(Exception ex)
		{
			Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
		}
		
	}		


	/*@Override
	protected void onResume() {
		super.onResume();
		
		Calendar expirationDate = Calendar.getInstance();
	    expirationDate.set(2016,9,30); //Vence el 20 de Septiembre
	    Calendar t = Calendar.getInstance();
	    
	    int result = t.compareTo(expirationDate);
	    
	    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
		builder.setMessage("Tiempo de prueba expirado")
		        .setTitle("Aviso")
		        .setCancelable(false)
		        .setPositiveButton("Aceptar",
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int id) {
		                    	finish();
		                    }
		                });
		AlertDialog alert = builder.create();
	    if (result >= 0)
	    {
	    	alert.show();
	    	if(!alert.isShowing())
	    	finish();
	    }
	}*/
	
	public boolean onCreateOptionsMenu(android.view.Menu menu){
		getMenuInflater().inflate(R.menu.resumen, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId())
		{
		case R.id.MenuClientes:
		{
			Intent i = new Intent(this, Clientes.class);
			startActivity(i);
			overridePendingTransition(R.anim.left_in, R.anim.left_out);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}		
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(values[position] == "Clientes")
		{
		Intent i = new Intent(this, Clientes.class);
		startActivity(i);
		overridePendingTransition(R.anim.left_in, R.anim.left_out);
		}
		if(values[position] == "BackupBD")
		{
			try
			{
				File sd = Environment.getExternalStorageDirectory();
				
				if(sd.canWrite())
				{
					String PathSaveVentas = "Ventas";
					String PathSaveClientes = "Clientes";
					String PathSavePagos = "Pagos";
					File CurrentBDVentas = new File(pathBDVentas);
					File CurrentBDClientes = new File(pathBDClientes);
					File CurrentBDPagos = new File(pathBDPagos);
					File BackupBDVentas = new File(sd,PathSaveVentas);
					File BackupBDClientes = new File(sd,PathSaveClientes);
					File BackupBDPagos = new File(sd,PathSavePagos);
					
					if(CurrentBDVentas.exists())
					{
						FileChannel srcVentas = new FileInputStream(CurrentBDVentas).getChannel();
						FileChannel dstVentas = new FileOutputStream(BackupBDVentas).getChannel();
						
						dstVentas.transferFrom(srcVentas,0, srcVentas.size());
						srcVentas.close();
						dstVentas.close();
					}
					if(CurrentBDClientes.exists())
					{
						FileChannel srcClientes = new FileInputStream(CurrentBDClientes).getChannel();
						FileChannel dstClientes = new FileOutputStream(BackupBDClientes).getChannel();
						
						dstClientes.transferFrom(srcClientes,0, srcClientes.size());
						srcClientes.close();
						dstClientes.close();
					}
					if(CurrentBDPagos.exists())
					{
						FileChannel srcPagos = new FileInputStream(CurrentBDPagos).getChannel();
						FileChannel dstPagos = new FileOutputStream(BackupBDPagos).getChannel();
						
						dstPagos.transferFrom(srcPagos,0, srcPagos.size());
						srcPagos.close();
						dstPagos.close();
					}
				}
				Toast.makeText(getApplicationContext(), "Bases de datos guardadas", Toast.LENGTH_SHORT).show();
			}
			catch(Exception ex)
			{
				Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
			}
		}
		if(values[position] == "RestoreBD")
		{
			try
			{
				File sd = Environment.getExternalStorageDirectory();
				
				if(sd.canWrite())
				{
					String PathSaveVentas = "Ventas";
					String PathSaveClientes = "Clientes";
					String PathSavePagos = "Pagos";
					File CurrentBDVentas = new File(pathBDVentas);
					File CurrentBDClientes = new File(pathBDClientes);
					File CurrentBDPagos = new File(pathBDPagos);
					File BackupBDVentas = new File(sd,PathSaveVentas);
					File BackupBDClientes = new File(sd,PathSaveClientes);
					File BackupBDPagos = new File(sd,PathSavePagos);
					
					if(CurrentBDVentas.exists())
					{
						FileChannel srcVentas = new FileInputStream(BackupBDVentas).getChannel();
						FileChannel dstVentas = new FileOutputStream(CurrentBDVentas).getChannel();
						
						dstVentas.transferFrom(srcVentas,0, srcVentas.size());
						srcVentas.close();
						dstVentas.close();
					}
					if(CurrentBDClientes.exists())
					{
						FileChannel srcClientes = new FileInputStream(BackupBDClientes).getChannel();
						FileChannel dstClientes = new FileOutputStream(CurrentBDClientes).getChannel();
						
						dstClientes.transferFrom(srcClientes,0, srcClientes.size());
						srcClientes.close();
						dstClientes.close();
					}
					if(CurrentBDPagos.exists())
					{
						FileChannel srcPagos = new FileInputStream(BackupBDPagos).getChannel();
						FileChannel dstPagos = new FileOutputStream(CurrentBDPagos).getChannel();
						
						dstPagos.transferFrom(srcPagos,0, srcPagos.size());
						srcPagos.close();
						dstPagos.close();
					}
				}
				Toast.makeText(getApplicationContext(), "Bases de datos restauradas", Toast.LENGTH_SHORT).show();
			}
			catch(Exception ex)
			{
				Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
			}
		}
		if(values[position] == "Pagos")
		{
			Intent i = new Intent(this, Pagos.class);
			startActivity(i);
			overridePendingTransition(R.anim.left_in, R.anim.left_out);
		}
		mDrawer.closeDrawers();	
		
	}

}
	

