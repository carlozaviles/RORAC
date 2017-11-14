import java.io.FileOutputStream;
import java.io.IOException;


public class TestIO {
	public static void main (String args []){
		String nombreArchivo = "/plnroracint/procesos/rorac/interfaces/PAR_RORAC_1_20160331.txt";
		byte[] contenido = new byte [100];
		try {
			guardaArchivo(nombreArchivo, contenido);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void guardaArchivo(final String nombreArchivo,final byte[] contenido) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(nombreArchivo);
			fos.write(contenido);
			fos.flush();
			System.out.println("Se Guardo el Archivo");
		} finally {
			fos.close();
		}
	}

}
