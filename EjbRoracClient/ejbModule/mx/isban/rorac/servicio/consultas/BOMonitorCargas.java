package mx.isban.rorac.servicio.consultas;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.consultas.BeanMonitorCargas;
import mx.isban.rorac.bean.consultas.BeanMonitorCargasRestateo;

@Remote
public interface BOMonitorCargas {

	/**
	 * Regresa el estatus de las cargas realizadas por el usuario.
	 *
	 * @param sessionBean
	 *            Objeto de la Arquitectura Agave.
	 * @return BeanMonitorCargas.
	 * @throws BusinessException
	 *             Exception
	 */
	public BeanMonitorCargas obtenerEstatusCargas(
			ArchitechSessionBean sessionBean) throws BusinessException;

	/**
	 * Regresa el estatus de las cargas para el proceso restateo realizadas por
	 * el usuario.
	 *
	 * @param sessionBean
	 *            Objeto de la Arquitectura Agave.
	 * @return BeanMonitorCargas objeto que contiene el estatus de los insumos
	 * @throws BusinessException
	 *             Exception
	 */
	public BeanMonitorCargasRestateo obtenerEstatusCargasRestateo(
			ArchitechSessionBean sessionBean) throws BusinessException;
}
