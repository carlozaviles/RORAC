/**
 *
 */
package mx.isban.rorac.util.general;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author everis
 *
 */
public final class UtileriasFront {

	/**
	 * Construtor privado que evita que esta clase sea instansiada.
	 */
	private UtileriasFront() {
	}

	/**
	 * Crea un mapa con una serie de valores que representan los anios que el
	 * usuario podra elegir en el menu de lanzador de reprocesos.
	 * 
	 * @return Map<String, String>
	 */
	public static Map<String, String> establecerAniosDisponibles() {
		final Calendar calendario = Calendar.getInstance();
		calendario.setTime(new Date());
		final int anioActual = calendario.get(Calendar.YEAR);
		final Map<String, String> anios = new LinkedHashMap<String, String>();
		anios.put("NONE", "--Seleccione--");
		for (int i = anioActual - 5; i <= anioActual; i++) {
			final String cadenaAnio = String.valueOf(i);
			anios.put(cadenaAnio, cadenaAnio);
		}
		return anios;
	}

	/**
	 * Guarda el archivo en la ruta indicada.
	 * 
	 * @param nombreArchivo
	 *            Nombre con el que se guarda el archivo.
	 * @param contenido
	 *            Cadena de bytes con el contenido del archivo.
	 * @throws IOException
	 *             Excepcion lanzada en caso de error al guardar el archivo.
	 */
	public static void guardaArchivo(final String nombreArchivo,
			final byte[] contenido) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(nombreArchivo);
			fos.write(contenido);
			fos.flush();
		} finally {
			fos.close();
		}
	}
}
