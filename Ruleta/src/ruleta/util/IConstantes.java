package ruleta.util;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * CLASE QUE REPRESENTA LAS CONSTANTES DEL SISTEMA
 * @author tec_juanm
 *
 */
public class IConstantes  {
	
	public static ArrayList<String> ZONAS_PUBLICA = new ArrayList<String>();
	public static ArrayList<String> ZONAS_PRIVADA_GENERICA = new ArrayList<String>();
	public static final String POOL_CONEXION = "jdbc/recreacion2";	
	//public static final String POOL_CONEXION = "java:jboss/datasources/recreacion"; 
	public static final String CONFA_AWS_IDENTIFICAR = "https://identidad.confa.co/confaAWS/rest/identificacionFacial/identificar2";
	public static String RUTA_VALIDACION_TOKENS="";
	public static boolean INCLUIR_PROXY=false;
	
	static{
		try {
			ResourceBundle bundle = null;
			bundle = ResourceBundle.getBundle("zonaPublica");
			String zonasTexto = bundle.getString("zona.publica");
			String zonasPrivadas = bundle.getString("zona.privada.generica");
			String [] zonas= zonasTexto.split(",");
			String [] zonasP= zonasPrivadas.split(",");
			for (int i = 0; i < zonas.length; i++) {
				ZONAS_PUBLICA.add(zonas[i]);
			}
			for (int i = 0; i < zonasP.length; i++) {
				ZONAS_PRIVADA_GENERICA.add(zonasP[i]);
			}
			ResourceBundle bundle2 = null;
			bundle2 = ResourceBundle.getBundle("sistema");
			RUTA_VALIDACION_TOKENS=bundle2.getString("RUTA_VALIDACION_TOKENS");
			INCLUIR_PROXY=Boolean.parseBoolean(bundle2.getString("INCLUIR_PROXY"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
