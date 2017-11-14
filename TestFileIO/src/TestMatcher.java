import java.util.regex.Pattern;


public class TestMatcher {
	public static void main (String args []){
		String mascara = "ROR_CONTINGENTES_\\d{4}(0[1-9]|1[012])\\.txt";
		String nombreArchivo = "ROR_CONTINGENTES_201604.txt";
		boolean validacionInterfaz = false;
		
		validacionInterfaz = Pattern.matches(mascara, nombreArchivo);
		if (validacionInterfaz){
			System.out.println("Coincide");
		}else{
			System.out.println("No coincide");
		}
		
	}
}
