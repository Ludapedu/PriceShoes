package com.example.priceshoes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatosVentas extends SQLiteOpenHelper{
	
	public String path;
	String sqlCreate = "CREATE TABLE Ventas (IDREG INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,Cliente text, Catalogo text, Pagina int, Marca text, ID int, Numero int, Costo int, Precio int, Entregado text)";
	public BaseDatosVentas(Context context, String nombre, CursorFactory factory, int version) {
		super(context, nombre, factory, version);
		path = context.getDatabasePath(nombre).getPath();
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlCreate);		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Ventas");
	}
	
	public String path()
	{
		return path;
	}

}
