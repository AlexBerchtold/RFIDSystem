package py.edu.facitec.rfidsystem.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

public class FechaUtil {
	private static MaskFormatter formatter;
	private static DateFormat DATE_FORTMAT = new SimpleDateFormat("dd/MM/yyyy");
	
	//Metodo para pasar el formato al jtext
	public static MaskFormatter getFormato() {
		try {
			formatter = new MaskFormatter("##/##/####");
			formatter.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatter;
	}

	//Metodo que convierte datos string a formato Date del paquete java.util
	public static Date stringAFecha(String text) {
		DATE_FORTMAT.setLenient(false);
		try {
			return DATE_FORTMAT.parse(text);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Formato de fecha invalido");
			return null;
		}
	}

	//Metodo que convierte datos del tipo Date a String
	public static String fechaAString(Date fecha) {
		return DATE_FORTMAT.format(fecha);
	}
	
	
	//Metodo que convierte datos del tipo string a Date del paquete java.sql
	public static java.sql.Date dSql(String text){
		java.sql.Date data;
		try {
			data = new java.sql.Date(DATE_FORTMAT.parse(text).getTime());
			return data;
		} catch (ParseException e) {
			return null;
		}
	}
	
}
