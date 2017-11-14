/**
 *
 */
package mx.isban.rorac.utilerias.general;

import java.util.Calendar;
import java.util.Date;

/**
 * @author everis
 *
 */
public final class UtileriasNegocio {

	/**
	 * Valor en entero de Enero
	 */
	private static final int ENERO = 0;

	/**
	 * Constructor privado que evita que esta clase sea instanciada.
	 */
	private UtileriasNegocio() {
	}

	/**
	 * Obtiene la representacion en dos digitos del mes actual.
	 *
	 * @return String
	 */
	public static String obtenerMesProceso() {
		final Calendar calendario = Calendar.getInstance();
		calendario.setTime(new Date());
		final int numeroMes = calendario.get(Calendar.MONTH);
		return (numeroMes <= 9) ? "0" + numeroMes : String.valueOf(numeroMes);
	}

	/**
	 * Devuelve una cadena que representa el anio en curso.
	 *
	 * @return String
	 */
	public static String obtenerAnio() {
		final Calendar calendario = Calendar.getInstance();
		calendario.setTime(new Date());
		System.out.println(calendario.getTime());
		final int numeroMes = calendario.get(Calendar.MONTH);
		System.out.println(numeroMes);
		if (numeroMes == ENERO) {
			return String.valueOf(calendario.get(Calendar.YEAR) - 1);
		} else {
			return String.valueOf(calendario.get(Calendar.YEAR));
		}
	}

	/**
	 * Devuelve una cadena que representa el anio en curso.
	 *
	 * @return Ultimo dia del mes
	 */
	public static String obtenerUltimoDiaDelMes(final String year,
			final String month) {
		final Calendar calendario = Calendar.getInstance();
		calendario.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);
		return String.valueOf(calendario
				.getActualMaximum(Calendar.DAY_OF_MONTH));
	}

	/**
	 * Metodo que normaliza el texto, eliminando acentos y caracteres
	 * especiales, ademas de regresar la cadena en mayusculas.
	 *
	 * @param texto
	 *            El texto a normalizar
	 * @return El texto normalizado
	 */
	public static String normalizarTexto(final String texto) {
		String textoNormalizado = java.text.Normalizer.normalize(texto,
				java.text.Normalizer.Form.NFD);
		textoNormalizado = textoNormalizado.replaceAll("[^\\p{ASCII}]", "");
		textoNormalizado = textoNormalizado.toUpperCase();
		return textoNormalizado;
	}
}
