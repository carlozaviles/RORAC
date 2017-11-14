package mx.isban.rorac.servicio.lanzadores;

import java.util.List;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.lanzadores.BeanEstatusLog;
import mx.isban.rorac.bean.lanzadores.BeanLogValidaciones;
import mx.isban.rorac.bean.lanzadores.BeanLogValidacionesRestateo;

/**
 * @author everis
 * @version 1.0
 * @see www.everis.com
 *
 */
@Remote
public interface BODescargaLogs {

	/**
	 * Obtiene la lista de logs de cruces de errores con sus respectivos
	 * estatus.
	 *
	 * @param bean
	 *            Componente de la arquitectura Agave.
	 * @return List
	 * @throws BusinessException
	 *             Exception
	 */
	public List<BeanEstatusLog> obtenerEstatusLogsCruces(
			ArchitechSessionBean bean) throws BusinessException;

	/**
	 * Obtiene la lista de logs de validaciones con sus respectivos estatus.
	 *
	 * @param bean
	 *            Componente de la arquitectura Agave.
	 * @return List
	 * @throws BusinessException
	 *             Exception
	 */
	public BeanLogValidaciones obtenerEstatusLogsValidaciones(
			ArchitechSessionBean bean) throws BusinessException;

	/**
	 * Lanza la descarga del log elegido por el usuario.
	 *
	 * @param nombreLog
	 *            Nombre de el log que sera descargado.
	 * @param bean
	 *            Componente de la arquitectura Agave.
	 * @throws BusinessException
	 *             Exception
	 */
	public void lanzaDescargaLog(String nombreLog, ArchitechSessionBean bean)
			throws BusinessException;

	/**
	 * Metodo encargado de obtenerel estatus de los logs de validacion para
	 * restateo
	 *
	 * @param architechBean
	 *            el objeto de session agave
	 * @return el estatus de los logs de validacion para restateo
	 * @throws BusinessException
	 *             Excepcion lanzada al ocurrir un error.
	 */
	public BeanLogValidacionesRestateo obtenerLogsValidacionesRestateo(
			ArchitechSessionBean architechBean) throws BusinessException;
}
