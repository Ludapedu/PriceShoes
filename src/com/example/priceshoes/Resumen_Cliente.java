package com.example.priceshoes;

import java.util.ArrayList;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout.LayoutParams;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Resumen_Cliente extends Activity {

	private String Cliente;
	Button btn_Cancelar;	
	Button btn_Borrar;
	Button btn_Entregar;
	ListView Lista_Cliente_Pedidos;
	ListView Lista_Cliente_Pagos;
	TextView MontoPendiente, MontoAbonado;
	String RegistroBorrar, RegistroEntregar, FechaBorrar, PagoEditar, IDEditarPedido, IDEditarPago;
	Button btn_Borrar_Pago;
	Button btn_Editar_Pago;
	Button btn_Cancelar_Pago;
	Button btn_Cambiar_Pedido;
	public int montopendiente = 0;
	public int montoabonado = 0;
	int MontoPago;
	private ArrayList<String> PedidosCliente = new ArrayList<String>();
	private ArrayList<String> PagosCliente = new ArrayList<String>();
	private ArrayList<String> ListaMarcas = new ArrayList<String>();
	Dialog DialogoDetalleCliente;
	Dialog DialogoDetallePago;
	Dialog DialogoAltaVenta;
	EditText txt_Marca_Dialogo_Clientes;
	AutoCompleteTextView txt_ID_Dialogo_Clientes;
	EditText txt_Numero_Dialogo_Clientes;
	EditText txt_Precio_Dialogo_Clientes;
	TextView txt_Costo_Dialogo_Clientes;
	TextView lbl_Cliente_Dialogo_Clientes;
	TextView lbl_Compras_Monto_Comprar;
	EditText txt_Dialogo_Catalogo_Clientes;
	EditText txt_Dialogo_Pagina_Clientes;
	ImageButton btn_Cerrar_Clientes, btn_Cerrar_Pago;
	Button btn_Dialogo_Editar;
	Button btn_Dialogo_Guardar;
	Button btn_Dialogo_Cancelar;
	
	EditText txt_Dialogo_Pago_Monto;
	Button btn_Dialogo_Pago_Editar_Cancelar;
	Button btn_Dialogo_Pago_Editar_Guardar;
	Button btn_Dialogo_Pago_Guardar;
	Button btn_Dialogo_Pago_Cancelar;
	DatePicker datePicker_Dialogo_Pago;
	
	TextView lbl_Venta_Cliente;
	Spinner spinner_Venta_Catalogos;
	Button btn_Venta_Pedido;
	Button btn_Venta_Cancelar;
	EditText txt_Venta_Pagina;
	EditText txt_Venta_Numero;
	AutoCompleteTextView txt_Venta_Marca;
	EditText txt_Venta_ID;
	EditText txt_Venta_Costo;
	EditText txt_Venta_Precio;
	TextView txt_Monto_Abonado;
	TextView lbl_RCliente_Pedidos;
	TextView lbl_Pagos;
	TextView lbl_RCliente_MontoPendienteTotal;
	public ArrayList<String> catalogos = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBar("");
		setContentView(R.layout.activity_resumen__cliente);
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "gloriahallelujah.ttf");
		
		catalogos.add("Vestir Caballeros");
		catalogos.add("Vestir Dama");
		catalogos.add("Botas Dama");
		catalogos.add("Confort");
		catalogos.add("Infantiles");
		catalogos.add("Escolares");
		catalogos.add("Todo en Uno");
		catalogos.add("Sandalias");
		catalogos.add("Vanguardia");
		catalogos.add("Importados");
		catalogos.add("Ropa Caballeros");
		catalogos.add("Ropa Ninos");
		
		Bundle bundle = getIntent().getExtras();
		Cliente = bundle.getString("NombreClienteMostrar");
		setActionBar(Cliente);
		
		DialogoDetalleCliente = new Dialog(this,R.style.Theme_Dialog_Translucent);
		DialogoDetalleCliente .requestWindowFeature(Window.FEATURE_NO_TITLE);
		DialogoDetalleCliente.setCancelable(false);
		DialogoDetalleCliente.setContentView(R.layout.dialogo_clientes_detalle);
		btn_Cerrar_Clientes = (ImageButton) DialogoDetalleCliente.findViewById(R.id.btn_Dialogo_Clientes);
		txt_Marca_Dialogo_Clientes = (EditText) DialogoDetalleCliente.findViewById(R.id.txt_Dialogo_Marca_Clientes);
		txt_ID_Dialogo_Clientes = (AutoCompleteTextView) DialogoDetalleCliente.findViewById(R.id.txt_Dialogo_ID_Clientes);
		txt_Dialogo_Pagina_Clientes = (EditText) DialogoDetalleCliente.findViewById(R.id.txt_Dialogo_Pagina_Clientes);
		txt_Numero_Dialogo_Clientes = (EditText) DialogoDetalleCliente.findViewById(R.id.txt_Dialogo_Numero_Clientes);
		txt_Precio_Dialogo_Clientes = (EditText) DialogoDetalleCliente.findViewById(R.id.txt_Dialogo_Precio_Clientes);
		txt_Costo_Dialogo_Clientes = (EditText) DialogoDetalleCliente.findViewById(R.id.txt_Dialogo_Costo_Clientes);
		txt_Dialogo_Catalogo_Clientes = (EditText) DialogoDetalleCliente.findViewById(R.id.txt_Dialogo_Catalogo_Clientes);
		lbl_Cliente_Dialogo_Clientes = (TextView) DialogoDetalleCliente.findViewById(R.id.lbl_Pagos_Pagos_General);
		btn_Dialogo_Editar = (Button) DialogoDetalleCliente.findViewById(R.id.btn_Dialogo_Clientes_Editar);
		btn_Dialogo_Guardar = (Button) DialogoDetalleCliente.findViewById(R.id.btn_Dialogo_Clientes_Guardar);
		btn_Dialogo_Cancelar = (Button) DialogoDetalleCliente.findViewById(R.id.btn_Dialogo_Clientes_Cancelar);
		
		txt_Marca_Dialogo_Clientes.setTypeface(font);
		txt_ID_Dialogo_Clientes.setTypeface(font);
		txt_Dialogo_Pagina_Clientes.setTypeface(font);
		txt_Numero_Dialogo_Clientes.setTypeface(font);
		txt_Precio_Dialogo_Clientes.setTypeface(font);
		txt_Costo_Dialogo_Clientes.setTypeface(font);
		lbl_Cliente_Dialogo_Clientes.setTypeface(font);
		txt_Dialogo_Catalogo_Clientes.setTypeface(font);
		
		
		DialogoDetallePago = new Dialog(this,R.style.Theme_Dialog_Translucent);
		DialogoDetallePago.requestWindowFeature(Window.FEATURE_NO_TITLE);
		DialogoDetallePago.setCancelable(false);
		DialogoDetallePago.setContentView(R.layout.dialogo_resumen_pago);
		btn_Cerrar_Pago = (ImageButton) DialogoDetallePago.findViewById(R.id.btn_Dialogo_Resumen_Pago);
		btn_Dialogo_Pago_Editar_Cancelar = (Button) DialogoDetallePago.findViewById(R.id.btn_Dialogo_Pago_Editar_Cancelar);
		btn_Dialogo_Pago_Guardar = (Button) DialogoDetallePago.findViewById(R.id.btn_Dialogo_Pago_Guardar);
		btn_Dialogo_Pago_Cancelar = (Button) DialogoDetallePago.findViewById(R.id.btn_Dialogo_Pago_Cancelar);
		btn_Dialogo_Pago_Editar_Guardar = (Button) DialogoDetallePago.findViewById(R.id.btn_Dialogo_Pago_Editar_Guardar);
		datePicker_Dialogo_Pago = (DatePicker) DialogoDetallePago.findViewById(R.id.Dialogo_datePicker_Pago);
		txt_Dialogo_Pago_Monto = (EditText) DialogoDetallePago.findViewById(R.id.txt_Dialogo_Resumen_Pago_Monto);
		
		DialogoAltaVenta = new Dialog(this, R.style.Theme_Dialog_Translucent);
		DialogoAltaVenta.requestWindowFeature(Window.FEATURE_NO_TITLE);
		DialogoAltaVenta.setCancelable(false);
		DialogoAltaVenta.setContentView(R.layout.activity_ventas);
		lbl_Venta_Cliente = (TextView) DialogoAltaVenta.findViewById(R.id.lbl_Ventas_Cliente);
		txt_Venta_Numero = (EditText) DialogoAltaVenta.findViewById(R.id.txt_Ventas_Numero);
		txt_Venta_Pagina = (EditText) DialogoAltaVenta.findViewById(R.id.txt_Ventas_Pagina);
		txt_Venta_Marca = (AutoCompleteTextView) DialogoAltaVenta.findViewById(R.id.txt_Ventas_Marca);
		txt_Venta_ID = (EditText) DialogoAltaVenta.findViewById(R.id.txt_Ventas_ID);
		txt_Venta_Costo = (EditText) DialogoAltaVenta.findViewById(R.id.txt_Ventas_Costo);
		txt_Venta_Precio = (EditText) DialogoAltaVenta.findViewById(R.id.txt_Ventas_Precio);
		btn_Venta_Pedido = (Button) DialogoAltaVenta.findViewById(R.id.btn_Ventas_Completar);
		btn_Venta_Cancelar = (Button) DialogoAltaVenta.findViewById(R.id.btn_Ventas_Cancelar);
		spinner_Venta_Catalogos = (Spinner) DialogoAltaVenta.findViewById(R.id.spinner_Ventas_Catalogos);
		
		ArrayAdapter<String> AdaptSpinner = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, catalogos);
		AdaptSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_Venta_Catalogos.setAdapter(AdaptSpinner);
		
		Lista_Cliente_Pedidos = (ListView) findViewById(R.id.Lista_RCliente_Pedidos);
		Lista_Cliente_Pagos = (ListView) findViewById(R.id.Lista_RCLientes_Pagos);	
		btn_Cancelar = (Button) findViewById(R.id.btn_RCliente_Cancelar);		
		btn_Borrar = (Button) findViewById(R.id.btn_RCliente_Borrar);
		btn_Borrar_Pago = (Button) findViewById(R.id.btn_RCliente_ElimPago);
		btn_Editar_Pago = (Button) findViewById(R.id.btn_RCliente_EditarPago);
		btn_Cancelar_Pago = (Button) findViewById(R.id.btn_RCliente_CancelarPago);
		btn_Entregar = (Button) findViewById(R.id.btn_RCliente_Entregar);
		btn_Cambiar_Pedido = (Button) findViewById(R.id.btn_RCliente_Cambiar);
		MontoPendiente = (TextView) findViewById(R.id.lbl_RCliente_Monto_Pendiente_Total);
		MontoAbonado = (TextView) findViewById(R.id.lbl_RCliente_Monto_Abonado_Total);
		lbl_RCliente_Pedidos =  (TextView) findViewById(R.id.lbl_RCliente_Pedidos);
		txt_Monto_Abonado = (TextView) findViewById(R.id.txt_Monto_Abonado);
		lbl_Pagos = (TextView) findViewById(R.id.lbl_Pagos);
		lbl_RCliente_MontoPendienteTotal = (TextView) findViewById(R.id.lbl_RCliente_MontoPendienteTotal);
		
		MontoPendiente.setTypeface(font);
		MontoAbonado.setTypeface(font);
		lbl_RCliente_Pedidos.setTypeface(font);
		txt_Monto_Abonado.setTypeface(font);
		lbl_Pagos.setTypeface(font);
		lbl_RCliente_MontoPendienteTotal.setTypeface(font);
		
		
		BaseDatosVentas BDVentas = new BaseDatosVentas(this, "Ventas", null, 1);
		SQLiteDatabase Ventas = BDVentas.getWritableDatabase();
		Cursor fila = Ventas.rawQuery("SELECT IDREG, ID, Marca, Entregado, Precio  FROM Ventas WHERE Cliente = '" + Cliente + "'" , null);
		PedidosCliente.clear();
		if(fila.moveToFirst())
		{
			do
			{
				PedidosCliente.add(fila.getString(0) + "| " + fila.getString(1) + " - " + fila.getString(2) + " - " + fila.getString(3));//" Catalogo: " + fila.getString(1) + " Pagina: " + fila.getString(2) +  " Marca: " + fila.getString(3) + " Precio: " + fila.getString(4) + " Costo: " + fila.getString(5) + " Numero: " + fila.getString(6));
				montopendiente += Integer.parseInt(fila.getString(4));
			}while(fila.moveToNext());
		}
		fila.close();
		BDVentas.close();
		Lista_Cliente_Pedidos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PedidosCliente));
		
		
		BaseDatosPagos BDPagos = new BaseDatosPagos(this, "Pagos", null, 1);
		SQLiteDatabase Pagos = BDPagos.getWritableDatabase();
		Cursor pago = Pagos.rawQuery("SELECT IDREG, Fecha, Monto FROM Pagos WHERE Cliente = '" + Cliente + "'", null);
		PagosCliente.clear();
		if(pago.moveToFirst())
		{
			do
			{
				PagosCliente.add(pago.getString(0) + "| " + pago.getString(1) + " $ " + pago.getString(2));
				montoabonado += Integer.parseInt(pago.getString(2));
			}while(pago.moveToNext());
		}
		pago.close();
		BDPagos.close();
		Lista_Cliente_Pagos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PagosCliente));
		MontoAbonado.setText("$ " + String.valueOf(montoabonado));
		montopendiente = montopendiente - montoabonado;
		MontoPendiente.setText("$ " + String.valueOf(montopendiente));
		
		
		btn_Entregar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
				BaseDatosVentas BDVentasUpdate = new BaseDatosVentas(getBaseContext(), "Ventas", null, 1);
				SQLiteDatabase Update = BDVentasUpdate.getWritableDatabase();
				int Registro = Integer.parseInt(RegistroEntregar.substring(0,RegistroBorrar.indexOf("|")));
				Update.execSQL("UPDATE Ventas SET Entregado = 'Entregado' WHERE IDREG = " + Registro);
				Toast.makeText(getApplicationContext(), "Pedido entregado con el IDREG: " + Registro + " para el cliente " + Cliente, Toast.LENGTH_SHORT).show();
				btn_Entregar.setVisibility(View.INVISIBLE);
				btn_Cambiar_Pedido.setVisibility(View.INVISIBLE);
				btn_Cancelar.setVisibility(View.INVISIBLE);
				btn_Borrar.setVisibility(View.INVISIBLE);
				Cursor fila = Update.rawQuery("SELECT IDREG, ID, Marca, Entregado  FROM Ventas WHERE Cliente = '" + Cliente + "'" , null);
				PedidosCliente.clear();
				if(fila.moveToFirst())
				{
					do
					{
						PedidosCliente.add(fila.getString(0) + "| " + fila.getString(1) + " - " + fila.getString(2) + " - " + fila.getString(3));
					}while(fila.moveToNext());
				}
				fila.close();
				BDVentasUpdate.close();
				Lista_Cliente_Pedidos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PedidosCliente));
				}
				catch(Exception ex)
				{
					Toast.makeText(getBaseContext(), ex.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn_Cambiar_Pedido.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
				BaseDatosVentas BDVentasUpdate = new BaseDatosVentas(getBaseContext(), "Ventas", null, 1);
				SQLiteDatabase Update = BDVentasUpdate.getWritableDatabase();
				int Registro = Integer.parseInt(RegistroEntregar.substring(0,RegistroEntregar.indexOf("|")));
				Update.execSQL("UPDATE Ventas SET Entregado = 'Cambio' WHERE IDREG = " + Registro);
				Toast.makeText(getApplicationContext(), "Pedido puesto par acambiar con el IDREG: " + Registro + " para el cliente " + Cliente, Toast.LENGTH_SHORT).show();
				btn_Entregar.setVisibility(View.INVISIBLE);
				btn_Cambiar_Pedido.setVisibility(View.INVISIBLE);
				btn_Cancelar.setVisibility(View.INVISIBLE);
				btn_Borrar.setVisibility(View.INVISIBLE);
				Cursor fila = Update.rawQuery("SELECT IDREG, ID, Marca, Entregado  FROM Ventas WHERE Cliente = '" + Cliente + "'" , null);
				PedidosCliente.clear();
				if(fila.moveToFirst())
				{
					do
					{
						PedidosCliente.add(fila.getString(0) + "| " + fila.getString(1) + " - " + fila.getString(2) + " - " + fila.getString(3));
					}while(fila.moveToNext());
				}
				fila.close();
				BDVentasUpdate.close();
				Lista_Cliente_Pedidos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PedidosCliente));
				}
				catch(Exception ex)
				{
					Toast.makeText(getBaseContext(), ex.toString(), Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		btn_Cerrar_Clientes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogoDetalleCliente.dismiss();
				
			}
		});
		
		btn_Cerrar_Pago.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogoDetallePago.dismiss();
				
			}
		});
		
		Lista_Cliente_Pedidos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				String registro = Lista_Cliente_Pedidos.getItemAtPosition(position).toString();
				IDEditarPedido = registro.substring(0,registro.indexOf("|"));
				BaseDatosVentas BDVentas = new BaseDatosVentas(getApplicationContext(), "Ventas", null, 1);
				SQLiteDatabase Ventas = BDVentas.getWritableDatabase();
				Cursor fila = Ventas.rawQuery("SELECT ID, Catalogo, Pagina, Marca, Precio, Costo, Cliente, Numero FROM Ventas WHERE IDREG = " + registro.substring(0,registro.indexOf("|"))  , null);
				if(fila.moveToFirst())
				{
					do
					{
						txt_Dialogo_Pagina_Clientes.setText(fila.getString(2));
						txt_Marca_Dialogo_Clientes.setText((fila.getString(3)));
						txt_ID_Dialogo_Clientes.setText(fila.getString(0));
						txt_Numero_Dialogo_Clientes.setText(fila.getString(7));
						txt_Precio_Dialogo_Clientes.setText("$ " + fila.getString(4));
						txt_Costo_Dialogo_Clientes.setText("$ " + fila.getString(5));
						lbl_Cliente_Dialogo_Clientes.setText((fila.getString(6)));
						txt_Dialogo_Catalogo_Clientes.setText(fila.getString(1));
					}while(fila.moveToNext());
				}
				DialogoDetalleCliente.show();
				Window window = DialogoDetalleCliente.getWindow();
				window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				fila.close();
				BDVentas.close();
				RegistroEntregar = Lista_Cliente_Pedidos.getItemAtPosition(position).toString();
				
			}
		});
		
		Lista_Cliente_Pedidos.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				btn_Entregar.setVisibility(View.VISIBLE);
				btn_Cambiar_Pedido.setVisibility(View.VISIBLE);
				btn_Cancelar.setVisibility(View.VISIBLE);
				btn_Borrar.setVisibility(View.VISIBLE);
				RegistroBorrar = Lista_Cliente_Pedidos.getItemAtPosition(position).toString();
				RegistroEntregar = Lista_Cliente_Pedidos.getItemAtPosition(position).toString();
				return true;
			}
		});
		
		Lista_Cliente_Pagos.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				
				btn_Borrar_Pago.setVisibility(View.VISIBLE);
				btn_Editar_Pago.setVisibility(View.VISIBLE);
				btn_Cancelar_Pago.setVisibility(View.VISIBLE);
				FechaBorrar = Lista_Cliente_Pagos.getItemAtPosition(position).toString();
				PagoEditar = Lista_Cliente_Pagos.getItemAtPosition(position).toString();
				return true;
			}
		});
		btn_Cancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn_Cancelar.setVisibility(View.INVISIBLE);				
				btn_Borrar.setVisibility(View.INVISIBLE);
				btn_Entregar.setVisibility(View.INVISIBLE);
				btn_Cambiar_Pedido.setVisibility(View.INVISIBLE);
				
			}
		});
		
		
		btn_Borrar_Pago.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
				montoabonado = 0;	
				montopendiente = 0;
				BaseDatosPagos BDPagos = new BaseDatosPagos(getBaseContext(), "Pagos", null, 1);
				SQLiteDatabase Pagos = BDPagos.getWritableDatabase();
				String IDBorrar = FechaBorrar.substring(0,FechaBorrar.indexOf("|"));
				Pagos.execSQL("DELETE FROM Pagos WHERE IDREG = " + IDBorrar);
				Toast.makeText(getApplicationContext(), "Registro borrado con el IDREG: " + IDBorrar + " para el cliente " + Cliente, Toast.LENGTH_SHORT).show();
				Cursor cursor = Pagos.rawQuery("SELECT IDREG, Fecha, Monto FROM Pagos WHERE Cliente = '" + Cliente + "'", null);
				PagosCliente.clear();
				if(cursor.moveToFirst())
				{
					do
					{
						PagosCliente.add(cursor.getString(0) + "| " + cursor.getString(1) + " $ " + cursor.getString(2));
						montoabonado += Integer.parseInt(cursor.getString(2));
					}while(cursor.moveToNext());
				}
				cursor.close();
				Lista_Cliente_Pagos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PagosCliente));
				btn_Borrar_Pago.setVisibility(View.INVISIBLE);
				btn_Editar_Pago.setVisibility(View.INVISIBLE);
				btn_Cancelar_Pago.setVisibility(View.INVISIBLE);
				BaseDatosVentas BDVentas = new BaseDatosVentas(getApplicationContext(), "Ventas", null, 1);
				SQLiteDatabase Ventas = BDVentas.getWritableDatabase();
				Cursor fila = Ventas.rawQuery("SELECT IDREG, ID, Marca, Entregado, Precio  FROM Ventas WHERE Cliente = '" + Cliente + "'" , null);
				PedidosCliente.clear();
				if(fila.moveToFirst())
				{
					do
					{
						PedidosCliente.add(fila.getString(0) + "|  " + fila.getString(1) + " - " + fila.getString(2) + " - " + fila.getString(3));//" Catalogo: " + fila.getString(1) + " Pagina: " + fila.getString(2) +  " Marca: " + fila.getString(3) + " Precio: " + fila.getString(4) + " Costo: " + fila.getString(5) + " Numero: " + fila.getString(6));
						montopendiente += Integer.parseInt(fila.getString(4));
					}while(fila.moveToNext());
				}
				fila.close();
				BDVentas.close();
				Lista_Cliente_Pedidos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PedidosCliente));
				
				MontoAbonado.setText("$ " + String.valueOf(montoabonado));
				montopendiente = montopendiente - montoabonado;
				MontoPendiente.setText("$ " + String.valueOf(montopendiente));
				}
				catch(Exception ex)
				{
				Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		btn_Editar_Pago.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
				DialogoDetallePago.show();
				Window window = DialogoDetallePago.getWindow();
				window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				datePicker_Dialogo_Pago.setVisibility(View.VISIBLE);
				btn_Editar_Pago.setVisibility(View.INVISIBLE);
				btn_Borrar_Pago.setVisibility(View.INVISIBLE);
				btn_Cancelar_Pago.setVisibility(View.INVISIBLE);
				btn_Dialogo_Pago_Guardar.setVisibility(View.INVISIBLE);
				btn_Dialogo_Pago_Cancelar.setVisibility(View.INVISIBLE);
				btn_Dialogo_Pago_Editar_Cancelar.setVisibility(View.VISIBLE);
				btn_Dialogo_Pago_Editar_Guardar.setVisibility(View.VISIBLE);
				
				String pago = PagoEditar.substring(PagoEditar.indexOf("$") + 2);
				txt_Dialogo_Pago_Monto.setText(pago);
				int DiaSelect = Integer.parseInt(PagoEditar.substring(PagoEditar.indexOf("|") + 2,PagoEditar.indexOf("|") + 4));
				int MesSelect = Integer.parseInt(PagoEditar.substring(PagoEditar.indexOf("|") + 5,PagoEditar.indexOf("|") + 6)) - 1;
				int AnoSelect = Integer.parseInt(PagoEditar.substring(PagoEditar.indexOf("|") + 7,PagoEditar.indexOf("|") + 11));
				datePicker_Dialogo_Pago.updateDate(AnoSelect,MesSelect,DiaSelect);
			}
			catch(Exception ex)
			{
			Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
			}
				
			}
		});
		
		btn_Cancelar_Pago.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn_Borrar_Pago.setVisibility(View.INVISIBLE);
				btn_Editar_Pago.setVisibility(View.INVISIBLE);
				btn_Cancelar_Pago.setVisibility(View.INVISIBLE);
				
			}
		});
		
		btn_Dialogo_Pago_Guardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
					if(txt_Dialogo_Pago_Monto.getText().toString().matches(""))
					{
						Toast.makeText(getApplicationContext(), "El monto no puede ser vacio", Toast.LENGTH_LONG).show();
						return;
					}
				montoabonado = 0;	
				montopendiente = 0;
				BaseDatosPagos BDPagos = new BaseDatosPagos(getApplicationContext(), "Pagos", null, 1);
				SQLiteDatabase Pagos = BDPagos.getWritableDatabase();
				String Dia = String.valueOf(datePicker_Dialogo_Pago.getDayOfMonth());
				String Mes = String.valueOf(datePicker_Dialogo_Pago.getMonth() + 1);
				String Ano = String.valueOf(datePicker_Dialogo_Pago.getYear());
				String fecha = Dia + "/" + Mes + "/" + Ano;
				int monto = Integer.parseInt(txt_Dialogo_Pago_Monto.getText().toString());
				Pagos.execSQL("INSERT INTO Pagos(Cliente,Fecha,Monto) VALUES ('" + Cliente + "','" + fecha + "'," + monto +")");
				Toast.makeText(getApplicationContext(), "Pago agregado correctamente", Toast.LENGTH_SHORT).show();
				
				BaseDatosVentas BDVentas = new BaseDatosVentas(getApplicationContext(), "Ventas", null, 1);
				SQLiteDatabase Ventas = BDVentas.getWritableDatabase();
				Cursor fila = Ventas.rawQuery("SELECT IDREG, ID, Marca, Entregado, Precio  FROM Ventas WHERE Cliente = '" + Cliente + "'" , null);
				PedidosCliente.clear();
				if(fila.moveToFirst())
				{
					do
					{
						PedidosCliente.add(fila.getString(0) +"| " + fila.getString(1) + " - " + fila.getString(2) + " - " + fila.getString(3));//" Catalogo: " + fila.getString(1) + " Pagina: " + fila.getString(2) +  " Marca: " + fila.getString(3) + " Precio: " + fila.getString(4) + " Costo: " + fila.getString(5) + " Numero: " + fila.getString(6));
						montopendiente += Integer.parseInt(fila.getString(4));
					}while(fila.moveToNext());
				}
				fila.close();
				BDVentas.close();
				Lista_Cliente_Pedidos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PedidosCliente));
				
				Cursor pago = Pagos.rawQuery("SELECT IDREG, Fecha, Monto FROM Pagos WHERE Cliente = '" + Cliente + "'", null);
				PagosCliente.clear();
				if(pago.moveToFirst())
				{
					do
					{
						PagosCliente.add(pago.getString(0) + "| " + pago.getString(1) + " $ " + pago.getString(2));
						montoabonado += Integer.parseInt(pago.getString(2));
					}while(pago.moveToNext());
				}
				pago.close();
				BDPagos.close();
				Lista_Cliente_Pagos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PagosCliente));
				MontoAbonado.setText("$ " + String.valueOf(montoabonado));
				montopendiente = montopendiente - montoabonado;
				MontoPendiente.setText("$ " + String.valueOf(montopendiente));
				
				DialogoDetallePago.dismiss();
				txt_Dialogo_Pago_Monto.setText("");
				}
				catch (Exception ex)
				{
					Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		btn_Dialogo_Pago_Cancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				txt_Dialogo_Pago_Monto.setText("");
				DialogoDetallePago.dismiss();
			}
		});
		
		btn_Dialogo_Pago_Editar_Guardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
					if(txt_Dialogo_Pago_Monto.getText().toString().matches(""))
					{
						Toast.makeText(getApplicationContext(), "El monto no puede ser vacio", Toast.LENGTH_LONG).show();
						return;
					}
					
				montoabonado = 0;	
				montopendiente = 0;
				int Monto = Integer.parseInt(txt_Dialogo_Pago_Monto.getText().toString());
				int IDPagoEditar = Integer.parseInt(PagoEditar.substring(0,PagoEditar.indexOf("|")));
				BaseDatosPagos BDPagos = new BaseDatosPagos(getApplicationContext(), "Pagos", null, 1);
				SQLiteDatabase Pagos = BDPagos.getWritableDatabase();
				String Dia = String.valueOf(datePicker_Dialogo_Pago.getDayOfMonth());
				String Mes = String.valueOf(datePicker_Dialogo_Pago.getMonth() + 1);
				String Ano = String.valueOf(datePicker_Dialogo_Pago.getYear());
				String fecha = Dia + "/" + Mes + "/" + Ano;
				Pagos.execSQL("UPDATE Pagos SET Fecha = '" + fecha + "', Monto = " + Monto + " WHERE IDREG = " + IDPagoEditar);
				Cursor cursor = Pagos.rawQuery("SELECT IDREG, Fecha, Monto FROM Pagos WHERE Cliente = '" + Cliente + "'", null);
				PagosCliente.clear();
				if(cursor.moveToFirst())
				{
					do
					{
						PagosCliente.add(cursor.getString(0) + "| " + cursor.getString(1) + " $ " + cursor.getString(2));
						montoabonado += Integer.parseInt(cursor.getString(2));
					}while(cursor.moveToNext());
				}
				cursor.close();
				Lista_Cliente_Pagos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PagosCliente));
				btn_Borrar_Pago.setVisibility(View.INVISIBLE);
				
				BaseDatosVentas BDVentas = new BaseDatosVentas(getApplicationContext(), "Ventas", null, 1);
				SQLiteDatabase Ventas = BDVentas.getWritableDatabase();
				Cursor fila = Ventas.rawQuery("SELECT IDREG, ID, Marca, Entregado, Precio  FROM Ventas WHERE Cliente = '" + Cliente + "'" , null);
				PedidosCliente.clear();
				if(fila.moveToFirst())
				{
					do
					{
						PedidosCliente.add(fila.getString(0) + "| " + fila.getString(1) + " - " + fila.getString(2) + " - " + fila.getString(3));//" Catalogo: " + fila.getString(1) + " Pagina: " + fila.getString(2) +  " Marca: " + fila.getString(3) + " Precio: " + fila.getString(4) + " Costo: " + fila.getString(5) + " Numero: " + fila.getString(6));
						montopendiente += Integer.parseInt(fila.getString(4));
					}while(fila.moveToNext());
				}
				fila.close();
				BDVentas.close();
				Lista_Cliente_Pedidos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PedidosCliente));
				
				MontoAbonado.setText("$ " + String.valueOf(montoabonado));
				montopendiente = montopendiente - montoabonado;
				MontoPendiente.setText("$ " + String.valueOf(montopendiente));
				Toast.makeText(getApplicationContext(), "Pago editado correctamente", Toast.LENGTH_SHORT).show();
				
				DialogoDetallePago.dismiss();
				txt_Dialogo_Pago_Monto.setText("");
			}
			catch (Exception ex)
			{
				Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
			}
			}
		});	
		
		btn_Dialogo_Pago_Editar_Cancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				txt_Dialogo_Pago_Monto.setText("");
				DialogoDetallePago.dismiss();
				
			}
		});
		
		btn_Borrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
					montoabonado = 0;	
					montopendiente = 0;
				BaseDatosVentas BDVentas = new BaseDatosVentas(getBaseContext(), "Ventas", null, 1);
				SQLiteDatabase Ventas = BDVentas.getWritableDatabase();
				int IDBorrar = Integer.parseInt(RegistroBorrar.substring(0,RegistroBorrar.indexOf("|")));
				Ventas.execSQL("DELETE FROM Ventas WHERE IDREG = " + IDBorrar);
				Toast.makeText(getApplicationContext(), "Registro borrado con el IDREG: " + IDBorrar + " para el cliente " + Cliente, Toast.LENGTH_SHORT).show();
				btn_Cancelar.setVisibility(View.INVISIBLE);				
				btn_Borrar.setVisibility(View.INVISIBLE);
				btn_Entregar.setVisibility(View.INVISIBLE);
				btn_Cambiar_Pedido.setVisibility(View.INVISIBLE);
				montopendiente = montopendiente - montoabonado;
				MontoPendiente.setText(String.valueOf(montopendiente));
				
			
				BaseDatosPagos BDPagos = new BaseDatosPagos(getBaseContext(), "Pagos", null, 1);
				SQLiteDatabase Pagos = BDPagos.getWritableDatabase();
				Cursor cursor = Pagos.rawQuery("SELECT IDREG, Fecha, Monto FROM Pagos WHERE Cliente = '" + Cliente + "'", null);
				PagosCliente.clear();
				if(cursor.moveToFirst())
				{
					do
					{
						PagosCliente.add(cursor.getString(0) + "| " + cursor.getString(1) + " $ " + cursor.getString(2));
						montoabonado += Integer.parseInt(cursor.getString(2));
					}while(cursor.moveToNext());
				}
				cursor.close();
				Lista_Cliente_Pagos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PagosCliente));
				btn_Borrar_Pago.setVisibility(View.INVISIBLE);
				btn_Editar_Pago.setVisibility(View.INVISIBLE);
				btn_Cancelar_Pago.setVisibility(View.INVISIBLE);
				Cursor fila = Ventas.rawQuery("SELECT IDREG, ID, Marca, Entregado, Precio  FROM Ventas WHERE Cliente = '" + Cliente + "'" , null);
				PedidosCliente.clear();
				if(fila.moveToFirst())
				{
					do
					{
						PedidosCliente.add(fila.getString(0) + "| " + fila.getString(1) + " - " + fila.getString(2) + " - " + fila.getString(3));//" Catalogo: " + fila.getString(1) + " Pagina: " + fila.getString(2) +  " Marca: " + fila.getString(3) + " Precio: " + fila.getString(4) + " Costo: " + fila.getString(5) + " Numero: " + fila.getString(6));
						montopendiente += Integer.parseInt(fila.getString(4));
					}while(fila.moveToNext());
				}
				fila.close();
				BDVentas.close();
				Lista_Cliente_Pedidos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PedidosCliente));
				
				MontoAbonado.setText("$ " + String.valueOf(montoabonado));
				montopendiente = montopendiente - montoabonado;
				MontoPendiente.setText("$ " + String.valueOf(montopendiente));
				}
				catch(Exception ex)
				{
				Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn_Dialogo_Editar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn_Dialogo_Editar.setVisibility(View.INVISIBLE);
				btn_Dialogo_Guardar.setVisibility(View.VISIBLE);
				btn_Dialogo_Cancelar.setVisibility(View.VISIBLE);
				
				txt_Dialogo_Pagina_Clientes.setEnabled(true);
				txt_Marca_Dialogo_Clientes.setEnabled(true);
				txt_ID_Dialogo_Clientes.setEnabled(true);
				txt_Numero_Dialogo_Clientes.setEnabled(true);
				txt_Precio_Dialogo_Clientes.setEnabled(true);
				txt_Costo_Dialogo_Clientes.setEnabled(true);
				lbl_Cliente_Dialogo_Clientes.setEnabled(true);
				txt_Dialogo_Catalogo_Clientes.setEnabled(true);
				txt_Precio_Dialogo_Clientes.setText(txt_Precio_Dialogo_Clientes.getText().toString().substring(2));
				txt_Costo_Dialogo_Clientes.setText(txt_Costo_Dialogo_Clientes.getText().toString().substring(2));
			}
		});
		
		btn_Dialogo_Guardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
					montoabonado = 0;	
					montopendiente = 0;
				BaseDatosVentas BDVentasActualizar = new BaseDatosVentas(getBaseContext(), "Ventas", null, 1);
				SQLiteDatabase Actualizar = BDVentasActualizar.getWritableDatabase();				
				
				Actualizar.execSQL("UPDATE Ventas "
						+ "SET Catalogo = '" + txt_Dialogo_Catalogo_Clientes.getText().toString() + 
							"' , Marca = '" + txt_Marca_Dialogo_Clientes.getText().toString() +
							"' , Pagina = " + Integer.parseInt(txt_Dialogo_Pagina_Clientes.getText().toString()) +
							" , ID = " + Integer.parseInt(txt_ID_Dialogo_Clientes.getText().toString()) +
							" , Numero = " + Float.parseFloat((txt_Numero_Dialogo_Clientes.getText().toString())) +
							" , Costo = " + Integer.parseInt(txt_Costo_Dialogo_Clientes.getText().toString()) +
							" , Precio = " + Integer.parseInt(txt_Precio_Dialogo_Clientes.getText().toString()) +
							" WHERE IDREG = " + IDEditarPedido);
				Toast.makeText(getApplicationContext(), "Registro actualizado con el IDREG = " + IDEditarPedido, Toast.LENGTH_SHORT).show();
				Cursor fila = Actualizar.rawQuery("SELECT IDREG, ID, Marca, Entregado, Precio  FROM Ventas WHERE Cliente = '" + Cliente + "'" , null);
				PedidosCliente.clear();
				if(fila.moveToFirst())
				{
					do
					{
						PedidosCliente.add(fila.getString(0) + "| " + fila.getString(1) + " - " + fila.getString(2)  + " - " + fila.getString(3));
						montopendiente += Integer.parseInt(fila.getString(4));
					}while(fila.moveToNext());
				}
				fila.close();
				BDVentasActualizar.close();
				Lista_Cliente_Pedidos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PedidosCliente));
				
				BaseDatosPagos BDPagos = new BaseDatosPagos(getBaseContext(), "Pagos", null, 1);
				SQLiteDatabase Pagos = BDPagos.getWritableDatabase();
				Cursor cursor = Pagos.rawQuery("SELECT IDREG, Fecha, Monto FROM Pagos WHERE Cliente = '" + Cliente + "'", null);
				PagosCliente.clear();
				if(cursor.moveToFirst())
				{
					do
					{
						PagosCliente.add(cursor.getString(0) + "| " + cursor.getString(1) + " $ " + cursor.getString(2));
						montoabonado += Integer.parseInt(cursor.getString(2));
					}while(cursor.moveToNext());
				}
				cursor.close();
				Lista_Cliente_Pagos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PagosCliente));
				
				MontoAbonado.setText("$ " + String.valueOf(montoabonado));
				montopendiente = montopendiente - montoabonado;
				MontoPendiente.setText("$ " + String.valueOf(montopendiente));
				
				btn_Dialogo_Editar.setVisibility(View.VISIBLE);
				btn_Dialogo_Guardar.setVisibility(View.INVISIBLE);
				btn_Dialogo_Cancelar.setVisibility(View.INVISIBLE);
				
				txt_Dialogo_Pagina_Clientes.setEnabled(false);
				txt_Marca_Dialogo_Clientes.setEnabled(false);
				txt_ID_Dialogo_Clientes.setEnabled(false);
				txt_Numero_Dialogo_Clientes.setEnabled(false);
				txt_Precio_Dialogo_Clientes.setEnabled(false);
				txt_Costo_Dialogo_Clientes.setEnabled(false);
				lbl_Cliente_Dialogo_Clientes.setEnabled(false);
				txt_Dialogo_Catalogo_Clientes.setEnabled(false);
				
				DialogoDetalleCliente.dismiss();
				}
				catch(Exception ex)
				{
					Toast.makeText(getBaseContext(), ex.toString(), Toast.LENGTH_SHORT).show();
				}				
			}
		});
		
		btn_Dialogo_Cancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn_Dialogo_Editar.setVisibility(View.VISIBLE);
				btn_Dialogo_Guardar.setVisibility(View.INVISIBLE);
				btn_Dialogo_Cancelar.setVisibility(View.INVISIBLE);
				
				txt_Dialogo_Pagina_Clientes.setEnabled(false);
				txt_Marca_Dialogo_Clientes.setEnabled(false);
				txt_ID_Dialogo_Clientes.setEnabled(false);
				txt_Numero_Dialogo_Clientes.setEnabled(false);
				txt_Precio_Dialogo_Clientes.setEnabled(false);
				txt_Costo_Dialogo_Clientes.setEnabled(false);
				lbl_Cliente_Dialogo_Clientes.setEnabled(false);
				txt_Dialogo_Catalogo_Clientes.setEnabled(false);
				DialogoDetalleCliente.dismiss();
			}
		});
		
		btn_Venta_Pedido.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{
					montoabonado = 0;	
					montopendiente = 0;
					if(txt_Venta_ID.getText().toString().matches(""))
					{
						Toast.makeText(getApplicationContext(), "El ID no puede ser vacio", Toast.LENGTH_LONG).show();
						return;
					}
					if(txt_Venta_Pagina.getText().toString().matches(""))
					{
						txt_Venta_Pagina.setText("0");
					}
					if(txt_Venta_Numero.getText().toString().matches(""))
					{
						txt_Venta_Numero.setText("0");
					}
					if(txt_Venta_Costo.getText().toString().matches(""))
					{
						txt_Venta_Costo.setText("0");
					}
					if(txt_Venta_Precio.getText().toString().matches(""))
					{
						txt_Venta_Precio.setText("0");
					}
					int Pagina = Integer.parseInt(txt_Venta_Pagina.getText().toString());
					String Catalogo = spinner_Venta_Catalogos.getSelectedItem().toString();
					String Marca = txt_Venta_Marca.getText().toString();
					if(!ListaMarcas.contains(Marca))
					{
						BaseDatosMarcas BDMarcas = new BaseDatosMarcas(getApplicationContext(), "Marcas", null, 1);
						SQLiteDatabase Marcas = BDMarcas.getWritableDatabase();
						Marcas.execSQL("INSERT INTO Marcas(Marca) VALUES(" + "\"" + Marca + "\"" +")");
						BDMarcas.close();
						
					}
					int ID = Integer.parseInt(txt_Venta_ID.getText().toString());
					float Numero = Float.parseFloat(txt_Venta_Numero.getText().toString());
					int Costo = Integer.parseInt(txt_Venta_Costo.getText().toString());
					int Precio = Integer.parseInt(txt_Venta_Precio.getText().toString());
					String Entregado = "Sin Entregar";					
					
					BaseDatosVentas BDVentas = new BaseDatosVentas(getApplicationContext(), "Ventas", null, 1);
					SQLiteDatabase Ventas = BDVentas.getWritableDatabase();
					Ventas.execSQL("INSERT INTO Ventas(Cliente,Catalogo,Pagina,Marca,ID,Numero,Costo,Precio,Entregado,IDREG) "
							+ "VALUES ('"+ Cliente + "','" + Catalogo + "'," + Pagina + ",'" + Marca + "'," + ID + "," + Numero + "," + Costo + "," + Precio + ",'" +Entregado + "',NULL)");
					Cursor fila = Ventas.rawQuery("SELECT IDREG, ID, Marca, Entregado, Precio  FROM Ventas WHERE Cliente = '" + Cliente + "'" , null);
					PedidosCliente.clear();
					if(fila.moveToFirst())
					{
						do
						{
							PedidosCliente.add(fila.getString(0) + "| " + fila.getString(1) + " - " + fila.getString(2) + " - " + fila.getString(3));//" Catalogo: " + fila.getString(1) + " Pagina: " + fila.getString(2) +  " Marca: " + fila.getString(3) + " Precio: " + fila.getString(4) + " Costo: " + fila.getString(5) + " Numero: " + fila.getString(6));
							montopendiente += Integer.parseInt(fila.getString(4));
						}while(fila.moveToNext());
					}
					fila.close();
					BDVentas.close();
					Lista_Cliente_Pedidos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PedidosCliente));

				BaseDatosPagos BDPagos = new BaseDatosPagos(getBaseContext(), "Pagos", null, 1);
				SQLiteDatabase Pagos = BDPagos.getWritableDatabase();
				Cursor cursor = Pagos.rawQuery("SELECT IDREG, Fecha, Monto FROM Pagos WHERE Cliente = '" + Cliente + "'", null);
				PagosCliente.clear();
				if(cursor.moveToFirst())
				{
					do
					{
						PagosCliente.add(cursor.getString(0) + "| " + cursor.getString(1) + " $ "+ cursor.getString(2));
						montoabonado += Integer.parseInt(cursor.getString(2));
					}while(cursor.moveToNext());
				}
				cursor.close();
				Lista_Cliente_Pagos.setAdapter(new CustomAdapterListViews(getApplicationContext(), PagosCliente));
				
				MontoAbonado.setText("$ " + String.valueOf(montoabonado));
				montopendiente = montopendiente - montoabonado;
				MontoPendiente.setText("$ " + String.valueOf(montopendiente));
				
				DialogoAltaVenta.dismiss();
				txt_Venta_Pagina.setText("");
				txt_Venta_Numero.setText("");
				txt_Venta_Marca.setText("");
				txt_Venta_ID.setText("");
				txt_Venta_Costo.setText("");
				txt_Venta_Precio.setText("");
				}
				catch(Exception ex)
				{
					Toast.makeText(getApplication(), ex.toString(), Toast.LENGTH_LONG).show();
					
				}
			}
		});
		
		btn_Venta_Cancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogoAltaVenta.dismiss();
				txt_Venta_Pagina.setText("");
				txt_Venta_Numero.setText("");
				txt_Venta_Marca.setText("");
				txt_Venta_ID.setText("");
				txt_Venta_Costo.setText("");
				txt_Venta_Precio.setText("");
			}
		});
		
	}

	protected void CargarMarcas() 
	{
			BaseDatosMarcas BDMarcas = new BaseDatosMarcas(getApplicationContext(), "Marcas", null, 1);
			SQLiteDatabase Marcas = BDMarcas.getWritableDatabase();
			Cursor filaMarcas = Marcas.rawQuery("SELECT Marca FROM Marcas",null);
			ListaMarcas.clear();
			if(filaMarcas.moveToFirst())
			{
				do
				{
					ListaMarcas.add(filaMarcas.getString(0));
				}while(filaMarcas.moveToNext());
			}
			filaMarcas.close();
			BDMarcas.close();	
			
			ArrayAdapter<String> adaptadorMarcas = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ListaMarcas);
			
			txt_Venta_Marca.setAdapter(adaptadorMarcas);
			txt_Venta_Marca.setThreshold(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.resumen__cliente, menu);
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
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
			return true;
		}
		case R.id.MenuResumen:
		{
			Intent i = new Intent(this, Resumen.class);
			startActivity(i);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}
		case R.id.AgregarPedido:
		{
			CargarMarcas();
			lbl_Venta_Cliente.setText(Cliente);
			DialogoAltaVenta.show();
			Window window = DialogoAltaVenta.getWindow();
			window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			return true;
		}
		case R.id.AgregarPago:
		{
			datePicker_Dialogo_Pago.setVisibility(View.VISIBLE);
			btn_Dialogo_Pago_Guardar.setVisibility(View.VISIBLE);
			btn_Dialogo_Pago_Cancelar.setVisibility(View.VISIBLE);
			btn_Dialogo_Pago_Editar_Cancelar.setVisibility(View.INVISIBLE);
			btn_Dialogo_Pago_Editar_Guardar.setVisibility(View.INVISIBLE);
			DialogoDetallePago.show();
			Window window = DialogoDetallePago.getWindow();
			window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
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
}
