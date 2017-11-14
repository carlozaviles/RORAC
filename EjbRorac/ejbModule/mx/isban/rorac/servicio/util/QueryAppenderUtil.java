package mx.isban.rorac.servicio.util;

public class QueryAppenderUtil {

	public static String buildInParameterFromList(final String[] grupos) {
		String fullQuery = "";
		if (grupos.length == 1) {
			fullQuery += "('" + grupos[0] + "')";
		} else {
			for (int i = 0; i < grupos.length; i++) {
				if (i == 0) {
					fullQuery += "('" + grupos[i] + "',";
				} else if (i == grupos.length - 1) {
					fullQuery += "'" + grupos[i] + "')";
				} else {
					fullQuery += "'" + grupos[i] + "',";
				}
			}
		}
		return fullQuery;
	}
}
