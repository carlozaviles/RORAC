package mx.isban.rorac.dao.consultas;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.rorac.bean.consultas.BeanConsultaIOFinalesDAO;

@Local
public interface DAOConsultaIOFinales {

	/**
	 * Metodo generico que se utiliza para realizar las consultas .
	 * 
	 * @param contrato
	 *            Se utiliza como parametro para realizar la consulta.
	 * @param consulta
	 *            Query a ejecutar.
	 * @param sessionBean
	 *            Objeto de la Arquitectura Agave.
	 * @return BeanConsultaIOFinalesDAO
	 */
	public BeanConsultaIOFinalesDAO ejecutaConsultaPorContrato(String contrato,
			String consulta, String fechaInicial, String fechaFin,
			ArchitechSessionBean sessionBean);
}
