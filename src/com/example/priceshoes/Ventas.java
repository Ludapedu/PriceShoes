package com.example.priceshoes;

import java.util.ArrayList;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Ventas extends Activity {

	private String Cliente, Catalogo, Marca, Entregado;
	private int Pagina, ID, Costo, Precio, IDREG;
	private float Numero;
	TextView lbl_Venta_Cliente;
	Spinner spinner_Venta_Catalogos;
	Button btn_Venta_Pedido;
	Button btn_Venta_Cancelar;
	EditText txt_Venta_Pagina;
	EditText txt_Venta_Numero;
	EditText txt_Venta_Marca;
	EditText txt_Venta_ID;
	EditText txt_Venta_Costo;
	EditText txt_Venta_Precio;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ventas);
		Bundle bundle = getIntent().getExtras();
		lbl_Venta_Cliente = (TextView) findViewById(R.id.lbl_Ventas_Cliente);
		spinner_Venta_Catalogos = (Spinner) findViewById(R.id.spinner_Ventas_Catalogos);
		Cliente = bundle.getString("ClienteVenta");
		lbl_Venta_Cliente.setText(Cliente);
		ArrayList<String> catalogos = new ArrayList<String>();
		catalogos.add("Vestir Caballeros");
		catalogos.add("Vestir Dama");
		catalogos.add("Botas Dama");
		catalogos.add("Comfort");
		catalogos.add("Infantiles");
		catalogos.add("Importados");
		catalogos.add("Ropa Caballeros");
		catalogos.add("Ropa Ninos");
		
		txt_Venta_Numero = (EditText) findViewById(R.id.txt_Ventas_Numero);
		txt_Venta_Pagina = (EditText) findViewById(R.id.txt_Ventas_Pagina);
		txt_Venta_Marca = (EditText) findViewById(R.id.txt_Ventas_Marca);
		txt_Venta_ID = (EditText) findViewById(R.id.txt_Ventas_ID);
		txt_Venta_Costo = (EditText) findViewById(R.id.txt_Ventas_Costo);
		txt_Venta_Precio = (EditText) findViewById(R.id.txt_Ventas_Precio);
		
		txt_Venta_Pagina.requestFocus();
		
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, catalogos);
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_Venta_Catalogos.setAdapter(adaptador);
		
		btn_Venta_Pedido = (Button) findViewById(R.id.btn_Ventas_Completar);
		btn_Venta_Pedido.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
				/*	if(String.valueOf(Pagina).matches(""))
				{
					Toast.makeText(getApplicationContext(), "La Pagina no puede esta vacia", Toast.LENGTH_LONG).show();
					return;
				}*/
				Pagina = Integer.parseInt(txt_Venta_Pagina.getText().toString());
				Catalogo = spinner_Venta_Catalogos.getSelectedItem().toString();
				Marca = txt_Venta_Marca.getText().toString();
				ID = Integer.parseInt(txt_Venta_ID.getText().toString());
				Numero = Float.parseFloat(txt_Venta_Numero.getText().toString());
				Costo = Integer.parseInt(txt_Venta_Costo.getText().toString());
				Precio = Integer.parseInt(txt_Venta_Precio.getText().toString());
				Entregado = "Sin Entregar";
				
				BaseDatosVentas BDVentas = new BaseDatosVentas(getApplicationContext(), "Ventas", null, 1);
				SQLiteDatabase Ventas = BDVentas.getWritableDatabase();
				Ventas.execSQL("INSERT INTO Ventas(Cliente,Catalogo,Pagina,Marca,ID,Numero,Costo,Precio,Entregado,IDREG) "
						+ "VALUES ('"+ Cliente + "','" + Catalogo + "'," + Pagina + ",'" + Marca + "'," + ID + "," + Numero + "," + Costo + "," + Precio + ",'" +Entregado + "',NULL)");
				Intent i = new Intent(getBaseContext(), Resumen_Cliente.class);
				i.putExtra("NombreClienteMostrar", Cliente);
				startActivity(i);
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
				}			
				catch(Exception ex)
				{
					Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ventas, menu);
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
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}
		case R.id.MenuClientes:
		{
			Intent i = new Intent(this, Clientes.class);
			startActivity(i);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}		
	}
}
