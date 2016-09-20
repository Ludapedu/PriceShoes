package com.example.priceshoes;

import java.util.ArrayList;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Clientes extends Activity {

	private ImageButton btn_Anadir;
	private ImageButton btn_Cancelar;
	private ImageButton btn_Guardar;
	private ImageButton btn_Borrar;
	private ImageButton btn_Editar;
	private EditText txt_Cliente;
	private EditText txt_Buscar_Clientes;
	private ListView Lista_Clientes;
	private TextView lbl_Cliente;
	TextView lbl_Clientes_Clientes;
	private ArrayList<ListClientes> NombresClientes = new ArrayList<ListClientes>();
	ListClientes Cliente;
	private String NombreClienteSeleccionado;
	private int IDClienteSeleccionado;
	public Context contexto = this;
	private searchableAdapter clientesadapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clientes);
		setActionBar("");
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "gloriahallelujah.ttf");
		
		btn_Anadir = (ImageButton) findViewById(R.id.btn_Clientes_Anadir);
		btn_Cancelar = (ImageButton) findViewById(R.id.btn_Clientes_Cancelar);
		btn_Guardar = (ImageButton) findViewById(R.id.btn_Clientes_Guardar);
		btn_Borrar = (ImageButton) findViewById(R.id.btn_Clientes_Borrar);
		btn_Editar = (ImageButton) findViewById(R.id.btn_Clientes_Editar);
		txt_Cliente = (EditText) findViewById(R.id.txt_Clientes_Cliente);
		Lista_Clientes = (ListView) findViewById(R.id.Lista_Clientes);
		lbl_Cliente = (TextView) findViewById(R.id.txt_Clientes_NombreCliente);
		txt_Buscar_Clientes = (EditText)findViewById(R.id.txt_Clientes_Buscar);
		lbl_Clientes_Clientes = (TextView) findViewById(R.id.lbl_Clientes_Clientes); 
		
		
		txt_Buscar_Clientes.setTypeface(font);
		lbl_Clientes_Clientes.setTypeface(font);
		
		BaseDatosClientes BDCLientes = new BaseDatosClientes(this, "Clientes", null, 1);
		SQLiteDatabase Clientes = BDCLientes.getWritableDatabase();
		Cursor fila = Clientes.rawQuery("SELECT IDREG, Nombre FROM Clientes", null);
		NombresClientes.clear();
		if(fila.moveToFirst())
		{
			do
			{
				//NombresClientes.add(fila.getString(0) + "| " + fila.getString(1));
				Cliente = new ListClientes();
				Cliente.setName(fila.getString(1));
				Cliente.setID(Integer.parseInt(fila.getString(0)));
				NombresClientes.add(Cliente);
			}while(fila.moveToNext());
		}
		fila.close();
		clientesadapter = new searchableAdapter(getApplicationContext(), NombresClientes);
		Lista_Clientes.setAdapter(clientesadapter);
		
		txt_Buscar_Clientes.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				clientesadapter.getFilter().filter(s.toString());	
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		btn_Anadir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
				String Client = txt_Cliente.getText().toString();
				if(Client.matches(""))
				{
					Toast.makeText(getApplicationContext(), "El cliente no puede ser vacio", Toast.LENGTH_LONG).show();
					return;
				}
				BaseDatosClientes BDCLientes = new BaseDatosClientes(getApplicationContext(), "Clientes", null, 1);
				SQLiteDatabase Clientes = BDCLientes.getWritableDatabase();
				ContentValues valores = new ContentValues();
				valores.put("Nombre", txt_Cliente.getText().toString());
				Clientes.insert("Clientes", null, valores);
				Cursor fila = Clientes.rawQuery("SELECT IDREG, Nombre FROM Clientes", null);
				NombresClientes.clear();
				if(fila.moveToFirst())
				{
					do
					{
						//NombresClientes.add(fila.getString(0) + "| " + fila.getString(1));
						Cliente = new ListClientes();
						Cliente.setName(fila.getString(1));
						Cliente.setID(Integer.parseInt(fila.getString(0)));
						NombresClientes.add(Cliente);
					}while(fila.moveToNext());
				}
				fila.close();
				clientesadapter = new searchableAdapter(getApplicationContext(), NombresClientes);
				Lista_Clientes.setAdapter(clientesadapter);
				Toast.makeText(getApplicationContext(), "Cliente " + Client + " agregado correctamente", Toast.LENGTH_LONG).show();
				txt_Cliente.setText("");
				lbl_Cliente.setVisibility(View.INVISIBLE);
				txt_Cliente.setVisibility(View.INVISIBLE);
				btn_Anadir.setVisibility(View.INVISIBLE);
				btn_Cancelar.setVisibility(View.INVISIBLE);
				}			
				catch(Exception ex)
				{
					Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
		Lista_Clientes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try
				{
					ListClientes registro = clientesadapter.getItem(position);
					BaseDatosClientes BDCLientes = new BaseDatosClientes(getApplicationContext(), "Clientes", null, 1);
					SQLiteDatabase Clientes = BDCLientes.getWritableDatabase();
					Cursor fila = Clientes.rawQuery("SELECT Nombre, IDREG FROM Clientes WHERE IDREG = " + registro.getID(), null);
					if(fila.moveToFirst())
					{
						do
						{
							NombreClienteSeleccionado = fila.getString(0);
							IDClienteSeleccionado = Integer.parseInt(fila.getString(1));
						}while(fila.moveToNext());
					}
					fila.close();
					Intent i = new Intent(getBaseContext(), Resumen_Cliente.class);
					i.putExtra("NombreClienteMostrar", NombreClienteSeleccionado);
					i.putExtra("IDClienteMostrar", IDClienteSeleccionado);
					startActivity(i);
					overridePendingTransition(R.anim.left_in, R.anim.left_out);
				}
				catch(Exception ex)
				{
					Toast.makeText(getApplicationContext(),ex.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});	
		
		Lista_Clientes.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				try
				{
				ListClientes registro = (ListClientes)Lista_Clientes.getItemAtPosition(position);	
				btn_Anadir.setVisibility(View.INVISIBLE);
				btn_Cancelar.setVisibility(View.VISIBLE);
				btn_Guardar.setVisibility(View.VISIBLE);
				btn_Borrar.setVisibility(View.VISIBLE);
				txt_Cliente.setVisibility(View.VISIBLE);
				BaseDatosClientes BDCLientes = new BaseDatosClientes(getApplicationContext(), "Clientes", null, 1);
				SQLiteDatabase Clientes = BDCLientes.getWritableDatabase();
				Cursor fila = Clientes.rawQuery("SELECT Nombre, IDREG FROM Clientes WHERE IDREG = " + registro.getID(), null);
				if(fila.moveToFirst())
				{
					do
					{
						NombreClienteSeleccionado = fila.getString(0);
						IDClienteSeleccionado = Integer.parseInt(fila.getString(1));
						txt_Cliente.setText(fila.getString(0));
					}while(fila.moveToNext());
				}
				fila.close();
				}
				catch(Exception ex)
				{
					Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
				}
				return true;
			}
		});
		
		
		btn_Borrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
					builder.setMessage("Desea eliminar el registro?")
					        .setTitle("Confirmar")
					        .setCancelable(false)
					        .setNegativeButton("Cancelar",
					                new DialogInterface.OnClickListener() {
					                    public void onClick(DialogInterface dialog, int id) {
					                    	btn_Cancelar.setVisibility(View.INVISIBLE);
					        				btn_Guardar.setVisibility(View.INVISIBLE);
					        				btn_Borrar.setVisibility(View.INVISIBLE);
					        				btn_Anadir.setVisibility(View.INVISIBLE);
					        				txt_Cliente.setText("");
					        				txt_Cliente.setVisibility(View.INVISIBLE);
					                        dialog.cancel();
					                    }
					                })
					        .setPositiveButton("Confirmar",
					                new DialogInterface.OnClickListener() {
					                    public void onClick(DialogInterface dialog, int id) {
					                    	BaseDatosClientes BDCLientes = new BaseDatosClientes(getApplicationContext(), "Clientes", null, 1);
					        				BaseDatosVentas BDVentas = new BaseDatosVentas(getApplicationContext(), "Ventas", null, 1);
					        				BaseDatosPagos BDPagos = new BaseDatosPagos(getApplicationContext(), "Pagos", null, 1);
					        				SQLiteDatabase Clientes = BDCLientes.getWritableDatabase();
					        				SQLiteDatabase Ventas = BDVentas.getWritableDatabase();
					        				SQLiteDatabase Pagos = BDPagos.getWritableDatabase();
					        				Clientes.execSQL("DELETE FROM Clientes WHERE IDREG = " + IDClienteSeleccionado);
					        				Ventas.execSQL("DELETE FROM Ventas WHERE Cliente = '" + NombreClienteSeleccionado + "'");
					        				Pagos.execSQL("DELETE FROM Pagos WHERE Cliente = '" + NombreClienteSeleccionado + "'");
					        				Cursor fila = Clientes.rawQuery("SELECT IDREG, Nombre FROM Clientes", null);
					        				NombresClientes.clear();
					        				if(fila.moveToFirst())
					        				{
					        					do
					        					{
					        						//NombresClientes.add(fila.getString(0) + "| " + fila.getString(1));
					        						Cliente.setName(fila.getString(1));
					        						Cliente.setID(Integer.parseInt(fila.getString(0)));
					        						NombresClientes.add(Cliente);
					        					}while(fila.moveToNext());
					        				}
					        				fila.close();
					        				clientesadapter = new searchableAdapter(getApplicationContext(), NombresClientes);
					        				Lista_Clientes.setAdapter(clientesadapter);					        				
					        				Toast.makeText(getApplicationContext(),"Cliente " + NombreClienteSeleccionado + " borrado", Toast.LENGTH_SHORT).show();
					        				btn_Cancelar.setVisibility(View.INVISIBLE);
					        				btn_Guardar.setVisibility(View.INVISIBLE);
					        				btn_Borrar.setVisibility(View.INVISIBLE);
					        				btn_Anadir.setVisibility(View.INVISIBLE);
					        				txt_Cliente.setVisibility(View.INVISIBLE);
					        				txt_Cliente.setText("");
					                    }
					                });
					AlertDialog alert = builder.create();
					alert.show();
				}
				catch(Exception ex)
				{
					Toast.makeText(getApplicationContext(),ex.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
				
		btn_Guardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
				InputMethodManager inputmetod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputmetod.hideSoftInputFromWindow(txt_Cliente.getWindowToken(),0);
				
				BaseDatosClientes BDCLientes = new BaseDatosClientes(getApplicationContext(), "Clientes", null, 1);
				BaseDatosVentas BDVentas = new BaseDatosVentas(getApplicationContext(), "Ventas", null, 1);
				BaseDatosPagos BDPagos = new BaseDatosPagos(getApplicationContext(), "Pagos", null, 1);
				SQLiteDatabase Clientes = BDCLientes.getWritableDatabase();
				SQLiteDatabase Ventas = BDVentas.getWritableDatabase();
				SQLiteDatabase Pagos = BDPagos.getWritableDatabase();
				String NombreClientaActualizar = txt_Cliente.getText().toString();
				Clientes.execSQL("UPDATE Clientes SET Nombre = '" + NombreClientaActualizar + "' WHERE IDREG = " + IDClienteSeleccionado);
				Ventas.execSQL("UPDATE Ventas SET Cliente = '" + NombreClientaActualizar + "' WHERE Cliente = '" + NombreClienteSeleccionado + "'");
				Pagos.execSQL("UPDATE Pagos SET Cliente = '" + NombreClientaActualizar + "' WHERE Cliente = '" + NombreClienteSeleccionado + "'");
				Cursor fila = Clientes.rawQuery("SELECT IDREG, Nombre FROM Clientes", null);
				NombresClientes.clear();
				if(fila.moveToFirst())
				{
					do
					{
						//NombresClientes.add(fila.getString(0) + "| " + fila.getString(1));
						Cliente.setName(fila.getString(1));
						Cliente.setID(Integer.parseInt(fila.getString(0)));
						NombresClientes.add(Cliente);
					}while(fila.moveToNext());
				}
				fila.close();
				clientesadapter = new searchableAdapter(getApplicationContext(), NombresClientes);
				Lista_Clientes.setAdapter(clientesadapter);
				Toast.makeText(getApplicationContext(),"Cliente " + NombreClienteSeleccionado + " actualizado por " + NombreClientaActualizar, Toast.LENGTH_SHORT).show();
				btn_Cancelar.setVisibility(View.INVISIBLE);
				btn_Guardar.setVisibility(View.INVISIBLE);
				btn_Borrar.setVisibility(View.INVISIBLE);
				btn_Anadir.setVisibility(View.INVISIBLE);
				txt_Cliente.setVisibility(View.INVISIBLE);
				txt_Cliente.setText("");	
				
				}
				catch(Exception ex)
				{
					Toast.makeText(getApplicationContext(),ex.toString(), Toast.LENGTH_SHORT).show();
				}	
			}
		});
		
		btn_Cancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
				InputMethodManager inputmetod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputmetod.hideSoftInputFromWindow(txt_Cliente.getWindowToken(),0);
				btn_Cancelar.setVisibility(View.INVISIBLE);
				btn_Guardar.setVisibility(View.INVISIBLE);
				btn_Borrar.setVisibility(View.INVISIBLE);
				btn_Anadir.setVisibility(View.INVISIBLE);
				btn_Editar.setVisibility(View.INVISIBLE);
				txt_Cliente.setText("");
				txt_Cliente.setVisibility(View.INVISIBLE);
				lbl_Cliente.setVisibility(View.INVISIBLE);
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
	public String getNombreCliente() {
			return NombreClienteSeleccionado;	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clientes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.MenuResumen:
		{
			Intent i = new Intent(this, Resumen.class);
			startActivity(i);
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
			return true;
		}
		case R.id.AgregarCliente:
		{
			lbl_Cliente.setVisibility(View.VISIBLE);
			txt_Cliente.setVisibility(View.VISIBLE);
			btn_Anadir.setVisibility(View.VISIBLE);
			btn_Cancelar.setVisibility(View.VISIBLE);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}	
	}
	@Override
	public void onBackPressed()
	{
		Intent i = new Intent(this, Resumen.class);
		startActivity(i);
		overridePendingTransition(R.anim.right_in, R.anim.right_out);	    
	    super.onBackPressed();
	}
	
	private int getItemPositionByAdapterId(long id)
	{
	    for (int i = 0; i < clientesadapter.getCount(); i++)
	    {
	        if (clientesadapter.getItemId(i) == id)
	            return i;
	    }
	    return -1;
	}
}