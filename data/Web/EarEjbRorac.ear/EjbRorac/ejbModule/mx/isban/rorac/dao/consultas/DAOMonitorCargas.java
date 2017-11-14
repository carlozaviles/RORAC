package mx.isban.rorac.dao.consultas;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.rorac.bean.consultas.BeanConsultaMonitor;
import mx.isban.rorac.bean.consultas.BeanListaMonitorCargasDAO;
import mx.isban.rorac.bean.lanzadores.BeanResultBOListasMonitor;

@Local
public interface DAOMonitorCargas {

	/**
	 * Ejecuta la consulta de Cargas Manuales.
	 *
	 * @param beanConsulta
	 *            Contiene los parametros para realizar la consulta.
	 * @param sessionBean
	 *            Objeto de la Arquitectura Agave.
	 * @return BeanListaMonitorCargasDAO
	 */
	public BeanListaMonitorCargasDAO consultaCargasManuales(
			BeanConsultaMonitor beanConsulta, ArchitechSessionBean sessionBean);

	/**
	 * Ejecuta la consulta de Insumos Iniciales.
	 *
	 * @param beanConsulta
	 *            Contiene los parametros para realizar la consulta de Insumos
	 *            Iniciales.
	 * @param sessionBean
	 *            Objeto de la Arquitectura Agave.
	 * @return BeanListaMonitorCargasDAO
	 */
	public BeanListaMonitorCargasDAO consultaInsumosIniciales(
			BeanConsultaMonitor beanConsulta, ArchitechSessionBean sessionBean);

	/**
	 * Ejecuta la consulta del estatus de los Inputs/Outputs Finales.
	 *
	 * @param beanConsulta
	 *            Contiene los parametros para realizar la consulta.
	 * @param sessionBean
	 *            Objeto de la arquitectura Agave.
	 * @return BeanListaMonitorCargasDAO
	 */
	public BeanListaMonitorCargasDAO consultaEstatusInterfacesFinales(
			BeanConsultaMonitor beanConsulta, ArchitechSessionBean sessionBean);

	/**
	 * Metodo para monitorear los insumos de un proceso
	 * 
	 * @param idProceso
	 *            identificador del proceso a monitorear
	 * @param sessionBean
	 *            el objeto sesion
	 * @return la lista de insumos con estatus
	 */
	public BeanResultBOListasMonitor consultaEstatusInsumos(String idProceso,
			ArchitechSessionBean sessionBean);
}
