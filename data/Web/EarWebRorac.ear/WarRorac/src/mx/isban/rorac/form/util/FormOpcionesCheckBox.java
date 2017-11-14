package mx.isban.rorac.form.util;

import java.util.Arrays;

public class FormOpcionesCheckBox {
	
	/**
	 * Lista de opciones.
	 */
	private String[] listaOpciones;
	/**
	 * IdFiltro
	 */
	private String idFiltro;

	/**
	 * @return the listaOpciones
	 */
	public String[] getListaOpciones() {
		if(listaOpciones != null){
			return Arrays.copyOf(listaOpciones, listaOpciones.length);
		}else{
			return null;
		}
	}

	/**
	 * @param listaOpciones the listaOpciones to set
	 */
	public void setListaOpciones(String[] listaOpciones) {
		this.listaOpciones = listaOpciones;
	}

	/**
	 * @return the idFiltro
	 */
	public String getIdFiltro() {
		return idFiltro;
	}

	/**
	 * @param idFiltro the idFiltro to set
	 */
	public void setIdFiltro(String idFiltro) {
		this.idFiltro = idFiltro;
	}
}
