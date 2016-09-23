package com.example.priceshoes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class BaseDatosMarcas extends SQLiteOpenHelper{
	public String path;
	String sqlCreate = "CREATE TABLE Marcas (IDMARCA INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,Marca text)";
	public BaseDatosMarcas(Context context, String nombre, CursorFactory factory, int version) {
		super(context, nombre, factory, version);
		path = context.getDatabasePath(nombre).getPath();
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlCreate);		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Marcas");
	}
	
	public String path()
	{
		return path;
	}
}
