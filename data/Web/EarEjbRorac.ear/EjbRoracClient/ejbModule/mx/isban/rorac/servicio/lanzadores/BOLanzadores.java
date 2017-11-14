package mx.isban.rorac.servicio.lanzadores;

import java.io.IOException;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.lanzadores.BeanLanzamientoMotorRorac;
import mx.isban.rorac.bean.lanzadores.BeanLanzamientoOperacion;

@Remote
public interface BOLanzadores {

	/**
	 * Crea el registro para que se realice una operacion de actualizacion o
	 * eliminacion sobre el archivo indicado.
	 *
	 * @param idArchivo
	 *            Id del Archivo sobre el que se realizara la operacion.
	 * @param idOperacion
	 *            Id de la operacion a realizar, Actualizacion o Eliminacion.
	 * @param sessionBean
	 *            Objeto de la arquitectura Agave.
	 * @throws BusinessException
	 *             Exception.
	 */
	public void registraCargaArchivo(String idArchivo, String idOperacion,
			ArchitechSessionBean sessionBean) throws BusinessException;

	/**
	 * Registra la operacion de lanzamiento de motor Rorac.
	 *
	 * @param sessionBean
	 *            Objeto de Arquitectura Agave.
	 * @throws BusinessException
	 *             Exception.
	 */
	public void lanzaMotorRorac(ArchitechSessionBean sessionBean)
			throws BusinessException;

	/**
	 * Registra los datos necesarios para el lanzamiento de Reproceso.
	 *
	 * @param datosReproceso
	 *            Datos para realizar el reproceso.
	 * @param sessionBean
	 *            Objeto de la Arquitectura Agave.
	 * @throws BusinessException
	 *             Exception
	 */
	public void lanzaReproceso(BeanLanzamientoOperacion datosReproceso,
			ArchitechSessionBean sessionBean) throws BusinessException;

	/**
	 * Registra la operacion de lanzamiento de Aprovisionamiento Historico.
	 *
	 * @param datosAprov
	 *            Datos para lanzar el Aprovisionamiento Historico.
	 * @param sessionBean
	 *            Objeto de Arquitectura Agave.
	 * @throws BusinessException
	 *             Exception.
	 */
	public void lanzaAprovisionamiento(BeanLanzamientoOperacion datosAprov,
			ArchitechSessionBean sessionBean) throws BusinessException;

	/**
	 * Verifica las peticiones actuales en la BD
	 *
	 * @param architechSessionBean
	 *            Bean de Session
	 *
	 * @return true si no hay peticion actual, false en otro caso
	 */
	public boolean validaPeticion(ArchitechSessionBean architechSessionBean)
			throws BusinessException;

	/**
	 * Metodo encargado de generar el archivo carga.txt
	 *
	 * @param beanForm
	 *            objeto con la informacion necesaria para crear el archivo
	 * @return true si el archivo fue generado, false en otros casos
	 */
	public void executeValidacionMotor(
			ArchitechSessionBean architechSessionBean,
			BeanLanzamientoMotorRorac beanForm) throws BusinessException,
			IOException;

	/**
	 * Metodo usado para validar que no existan ejecuciones actuales del motor
	 *
	 * @param architechBean
	 *            el objeto architech de AGAVE.}
	 * @return true si hay ejecucion del motor actual, false en otros casos
	 */
	public boolean hayEjecucionEnProgreso(ArchitechSessionBean architechBean)
			throws BusinessException;
}
