package mx.isban.rorac.dao.lanzadores;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.interfaces.BeanResultBO;
import mx.isban.rorac.bean.lanzadores.BeanLanzamientoMotorRorac;
import mx.isban.rorac.bean.lanzadores.BeanLanzamientoOperacion;

@Local
public interface DAOLanzadorOperaciones {

	/**
	 * Se realiza la insercion de un registro para indicar que se ejecute la
	 * carga de un archivo de Inputs/Outputs.
	 *
	 * @param beanCarga
	 *            Contiene la informacion para realizar la insercion de un
	 *            registro de operacion.
	 * @param sessionBean
	 *            Objeto de la arquitectura Agave.
	 * @return BeanResultBO
	 */
	public BeanResultBO registraOperacion(
			final BeanLanzamientoOperacion beanCarga,
			final ArchitechSessionBean sessionBean);

	/**
	 * Metodo que devuelve el numero de registros que se encuentran en una
	 * peticion vigente.
	 *
	 * @param idOperacion
	 *            Identificador de la operacion
	 * @param ano
	 *            Año de ejecucion
	 * @param mes
	 *            Mes de ejecucion
	 * @return El numero de registros que se encuentran actualmente con peticion
	 *         vigente
	 */
	public BeanResultBO obtenerNumeroPeticionesAbiertas(
			final String idOperacion, String ano, String mes);

	/**
	 * Metodo para consultar en BD si existe una ejecucion actual del motor
	 *
	 * @return Si existe una ejecucion del motor
	 */
	public BeanResultBO validaEjecucion(final String idProceso);

	/**
	 * Metodo para registrar en BD la validacion de insmos del motor
	 *
	 * @param beanForm
	 *            datos del archivo carga.txt
	 *
	 * @param idProceso
	 *            El id Proceso de la Operacion
	 * @param idEstatus
	 *            el id de estatus a actualizar
	 * @param sessionBean
	 *            bean se session de AGAVE
	 * @return BeanResultBO con la respuesta de la ejecucion
	 */
	public BeanResultBO updateRegistroOperacionMotor(final String idProceso,
			final String idEstatus, BeanLanzamientoMotorRorac beanForm,
			ArchitechSessionBean sessionBean);

	/**
	 * Se realiza la insercion de un registro para indicar que se ejecute la
	 * carga de un archivo de Inputs/Outputs.
	 *
	 * @param datosAprov
	 *            Contiene la informacion para realizar la insercion de un
	 *            registro de operacion.
	 * @param sessionBean
	 *            Objeto de la arquitectura Agave.
	 * @return BeanResultBO
	 */
	public BeanResultBO registraOperacionAprov(
			BeanLanzamientoOperacion datosAprov,
			ArchitechSessionBean sessionBean);
}
