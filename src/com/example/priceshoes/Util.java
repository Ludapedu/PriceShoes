package com.example.priceshoes;

public class Util {

	public String comas(int monto)
	{
		String result = "";
		int miles = 0;
		int centenas = 0;
		miles = monto /1000;
		if(miles == 0)
		{
			result = String.valueOf(monto);
			return result;
		}
		centenas = monto - (miles * 1000);
		if(centenas < 10)
		{
			result = String.valueOf(miles) + ",00" + String.valueOf(centenas);
			return result;
		}
		if(centenas < 100)
		{
			result = String.valueOf(miles) + ",0" + String.valueOf(centenas);
			return result;
		}
		if(centenas > 100)
		{
			result = String.valueOf(miles) + "," + String.valueOf(centenas);
			return result;
		}
		return result;
	}
}
