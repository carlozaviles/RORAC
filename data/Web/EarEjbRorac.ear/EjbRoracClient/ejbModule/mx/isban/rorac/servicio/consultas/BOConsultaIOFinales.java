package mx.isban.rorac.servicio.consultas;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;

@Remote
public interface BOConsultaIOFinales {

	/**
	 * Realiza la comunicacion con la capa de acceso a datos para ejecutar la
	 * consulta por contrato.
	 *
	 * @param contrato
	 *            Numero de contrato que se utiliza como parametro para la
	 *            consulta.
	 * @param tipoInterfaz
	 *            La interfaz que sera consultada.
	 * @param sessionBean
	 *            Objeto de arquitectura Agave.
	 * @return Map<String, Object> Resultado de consulta.
	 * @throws BusinessException
	 *             Excepcion.
	 */
	public List<HashMap<String, Object>> llamaConsultaIOFinales(
			String contrato, String tipoInterfaz, String anio, String mes,
			ArchitechSessionBean sessionBean) throws BusinessException;
}
