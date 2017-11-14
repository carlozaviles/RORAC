package mx.isban.rorac.bean.consultas;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import mx.isban.agave.commons.interfaces.BeanResultBO;

public class BeanConsultaIOFinalesDAO implements BeanResultBO, Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -3008524347373058120L;
	/**
	 * Codigo de Error resutado de la consulta de Inpus/Outpus Finales.
	 */
	private String codError;
	/**
	 * Mensaje de Error resultado de la consulta de Inputs/Outputs Finales.
	 */
	private String msgError;
	/**
	 * Contiene los resultados de la consulta de Inputs/Outputs Finales.
	 */
	private List<HashMap<String, Object>> resultadosConsulta;

	/**
	 * Obtiene el campo resultadosConsulta.
	 *
	 * @return Map<String, String>
	 */
	public List<HashMap<String, Object>> getResultadosConsulta() {
		return resultadosConsulta;
	}

	/**
	 * Establece el valor del campo resultadosConsulta.
	 *
	 * @param resultadosConsulta
	 *            Valor que es colocado en el campo resultadosConsulta.
	 */
	public void setResultadosConsulta(final List<HashMap<String, Object>> list) {
		this.resultadosConsulta = list;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.isban.agave.commons.interfaces.BeanResultBO#getCodError()
	 */
	@Override
	public String getCodError() {
		return this.codError;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.isban.agave.commons.interfaces.BeanResultBO#getMsgError()
	 */
	@Override
	public String getMsgError() {
		return this.msgError;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.agave.commons.interfaces.BeanResultBO#setCodError(java.lang.
	 * String)
	 */
	@Override
	public void setCodError(final String codError) {
		this.codError = codError;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.agave.commons.interfaces.BeanResultBO#setMsgError(java.lang.
	 * String)
	 */
	@Override
	public void setMsgError(final String msgError) {
		this.msgError = msgError;
	}
}
