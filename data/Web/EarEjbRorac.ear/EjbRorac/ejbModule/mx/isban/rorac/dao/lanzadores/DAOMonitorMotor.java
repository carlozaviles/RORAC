package mx.isban.rorac.dao.lanzadores;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.rorac.bean.lanzadores.BeanResultBOListasMonitor;

@Local
public interface DAOMonitorMotor {

	/**
	 * Ejecuta la consulta de monitor para los insumos de motor RORAC.
	 *
	 * @param idProceso
	 *            Contiene el id proceso de los insumos a monitorear.
	 * @param sessionBean
	 *            Objeto de la Arquitectura Agave.
	 * @return BeanResultBOListasMonitorMotor el objeto resultado de con los
	 *         estatus de los insumos del motor RORAC
	 */
	public BeanResultBOListasMonitor consultaEstatusInsumosMotor(
			String idProceso, ArchitechSessionBean sessionBean);
}
