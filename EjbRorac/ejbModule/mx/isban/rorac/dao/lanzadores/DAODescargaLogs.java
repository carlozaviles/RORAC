package mx.isban.rorac.dao.lanzadores;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.interfaces.BeanResultBO;
import mx.isban.rorac.bean.lanzadores.BeanListaLogsDAO;
import mx.isban.rorac.bean.lanzadores.BeanParametrosLogs;

@Local
public interface DAODescargaLogs {

	/**
	 * Realiza la consulta de estatus de los logs.
	 *
	 * @param beanParametros
	 *            Parametros de busqueda que determinan si la busqueda es para
	 *            logs de errores, o logs de validaciones.
	 * @param sessionBean
	 *            Componente de la arquitectura Agave.
	 * @return BeanListaLogsDAO
	 */
	public BeanListaLogsDAO consultaEstatusLogs(
			BeanParametrosLogs beanParametros, ArchitechSessionBean sessionBean);

	/**
	 * Se registra un log para que se ejecute la operacion de descarga.
	 *
	 * @param idLog
	 *            Id del log al que se le colocara un estatus de descarga.
	 * @param codigoRegistro
	 *            Codigo de registro que indica que un log tiene que ser
	 *            descargado.
	 * @param bean
	 *            Objeto de la arquitectura Agave.
	 * @return BeanResultBO
	 */
	public BeanResultBO registraLogParaDescarga(String idLog,
			String codigoRegistro, ArchitechSessionBean bean);

	/**
	 * Realiza la consulta de estatus de los logs de validacion para Restateo.
	 *
	 * @param idProceso
	 *            Proceso para validar los Logs.
	 * @param sessionBean
	 *            Componente de la arquitectura Agave.
	 * @return BeanListaLogsDAO el resultado de la consulta.
	 */
	public BeanListaLogsDAO consultaEstatusLogsRestateo(String idProceso,
			ArchitechSessionBean sessionBean);
}
