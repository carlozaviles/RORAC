package mx.isban.rorac.servicio.lanzadores;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.lanzadores.BeanMonitorMotor;

@Remote
public interface BOMonitorMotor {

	/**
	 * Regresa el estatus de los insumos para el motor RORAC.
	 *
	 * @param sessionBean
	 *            Objeto de la Arquitectura Agave.
	 * @return BeanMonitorCargas.
	 * @throws BusinessException
	 *             Exception
	 */
	public BeanMonitorMotor obtenerEstatusMotor(
			final ArchitechSessionBean sessionBean) throws BusinessException;
}
