package mx.isban.rorac.servicio.lanzadores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.BeanPistasAuditoria;
import mx.isban.rorac.bean.consultas.BeanEstatusCarga;
import mx.isban.rorac.bean.lanzadores.BeanMonitorMotor;
import mx.isban.rorac.bean.lanzadores.BeanResultBOListasMonitor;
import mx.isban.rorac.dao.lanzadores.DAOMonitorMotor;
import mx.isban.rorac.servicio.util.BOPistasAuditoria;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOMonitorMotorImpl extends Architech implements BOMonitorMotor {

	/**
	 * Parametro para traer la configuracion del lanzamiento de motor rorac
	 */
	private static final String LANZA_MOTOR = "PARAM_CONFIG_LANZAMIENTO_MOTOR_RORAC";

	/**
	 * Parametro para traer la configuracion de los insumos corporattivos
	 */
	private static final String INSUMOS_CORPORATIVOS = "PARAM_CONFIG_INSUMOS_CORPORATIVOS";

	/**
	 * Separador de configuraciones
	 */
	private static final String SEPARADOR_CONFIGS = ",";

	/**
	 * Parametro de configuracion que contiene la lista de los insumos para
	 * motor rorac
	 */
	private static final String INSUMOS_MOTOR = "PARAM_CONFIG_INSUMOS_MOTOR";

	/**
	 * Estatus de error en BD
	 */
	private static final String ESTATUS_23 = "23";
	/**
	 * Estatus de inicio
	 */
	private static final String ESTATUS_00 = "00";
	/**
	 * Estatus de solicitud de validacion
	 */
	private static final String ESTATUS_01 = "01";
	/**
	 * Estatus insumos no cargados
	 */
	private static final String ESTATUS_12 = "12";
	/**
	 * Estatus de solicitud de lanzamiento motor rorac
	 */
	private static final String ESTATUS_21 = "21";
	/**
	 * Estatus de lanzamiento motor exitoso
	 */
	private static final String ESTATUS_22 = "22";

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 3792536087600384755L;

	/**
	 * Identificador del proceso de lanzador motor
	 */
	private static final String PARAM_MONITOR_MOTOR_ID_OPERACION = "PARAM_CONFIG_MOTOR_RORAC_OPERACION";

	/**
	 * Estatus que indica que un insumo se cargo de manera correcta.
	 */
	private static final String DESC_OK = "OK";
	/**
	 * Estatus que indica que un insumo no ha sido cargado.
	 */
	private static final String DESC_NO_CARGADO = "NO CARGADO";
	/**
	 * Estatus que indica que un insumo fue cargado con error.
	 */
	private static final String DESC_ERROR = "ERROR";
	/**
	 * Estatus que indica que un insumo aun no ha iniciado su proceso.
	 */
	private static final String DESC_NO_INICIADO = "NO INICIADO";
	/**
	 * Estatus que indica que la validacion del insumo esta en proceso.
	 */
	private static final String DESC_EN_PROCESO = "EN PROCESO";
	/**
	 * Intancia del objeto de la capa de Negocio encargado de realizar las
	 * consultas de estatus de los insumos de motor rorac.
	 */
	@EJB
	private transient DAOMonitorMotor daoMonitorMotor;
	/**
	 * Manejador de Pistas de auditoria.
	 */
	@EJB
	private transient BOPistasAuditoria manejadorPistasAuditoria;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.servicio.lanzadores.BOMonitorMotor#obtenerEstatusMotor
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanMonitorMotor obtenerEstatusMotor(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "obtenerEstatusMotor()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		final BeanMonitorMotor monitorMotor = new BeanMonitorMotor();
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setCodigoOperacion(this.getConfigDeCmpAplicacion(PARAM_MONITOR_MOTOR_ID_OPERACION));
		pistaAuditoria.setNombreArchivo("No Aplica");
		try {
			List<BeanEstatusCarga> estatusInsumosMotor = consultaEstatusInsumosMotor(sessionBean);
			monitorMotor.setInsumosMotorRorac(extraeConfigsFromList(estatusInsumosMotor, INSUMOS_MOTOR));
			monitorMotor.setInsumosCorporativos(extraeConfigsFromList(estatusInsumosMotor, INSUMOS_CORPORATIVOS));
			monitorMotor.setLanzamientoMotorRorac(extraeConfigsFromList(estatusInsumosMotor, LANZA_MOTOR));
		} catch (BusinessException e) {
			pistaAuditoria.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,sessionBean);
			throw e;
		}
		pistaAuditoria.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,sessionBean);
		this.info("Finaliza la ejecucion del metodo: " + metodo);
		return monitorMotor;
	}

	/**
	 * Metodo para consultar el total de los insumos para motor rorac
	 *
	 * @param sessionBean
	 *            el objeto de session
	 * @return la lista de todos los insumos para motor rorac
	 * @throws BusinessException
	 *             excepcion lanzada al ocurrir un error
	 */
	private List<BeanEstatusCarga> consultaEstatusInsumosMotor(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "consultaEstatusInsumosMotor()";
		this.info("Inicia la ejecucion del metodo: " + metodo);
		final String idProceso = this
				.getConfigDeCmpAplicacion(PARAM_MONITOR_MOTOR_ID_OPERACION);
		final BeanResultBOListasMonitor estatusCargasManuales = daoMonitorMotor
				.consultaEstatusInsumosMotor(idProceso, sessionBean);
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatusCargasManuales
				.getCodError())) {
			this.info("Se obtuvo un codigo de error al ejecutar la consulta de Cargas Manuales: "
					+ estatusCargasManuales.getCodError());
			throw new BusinessException(estatusCargasManuales.getCodError());
		}
		for (BeanEstatusCarga estatus : estatusCargasManuales
				.getListaMonitorMotor()) {
			setCorrectEstatus(estatus);
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return estatusCargasManuales.getListaMonitorMotor();
	}

	/**
	 * Metodo que establece el correcto estatus para los insumos
	 *
	 * @param estatus
	 *            objeto checador de estatus
	 */
	private void setCorrectEstatus(final BeanEstatusCarga estatus) {
		if (estatus.getNombreInterfaz().replace(" ", "")
				.equalsIgnoreCase(this.getConfigDeCmpAplicacion(LANZA_MOTOR))) {
			estatusLanzamientoMotor(estatus);
		} else {
			estatusInsumosMotor(estatus);
		}
	}

	/**
	 * Se encarga de establecer el correcto estatus para los insumos del motor
	 *
	 * @param estatus
	 *            el objeto que contendra el estatus de los insumos.
	 */
	private void estatusInsumosMotor(final BeanEstatusCarga estatus) {
		if (estatus.getEstatus().equals(ESTATUS_00)) {
			estatus.setEstatus(DESC_NO_INICIADO);
		} else if (estatus.getEstatus().equals(ESTATUS_01)) {
			estatus.setEstatus(DESC_EN_PROCESO);
		} else if (estatus.getEstatus().equals(ESTATUS_12)) {
			estatus.setEstatus(DESC_NO_CARGADO);
		} else {
			estatus.setEstatus(DESC_OK);
		}
	}

	/**
	 * Se encarga de establecer el correcto estatus para el monitor del motor
	 *
	 * @param estatus
	 *            el objeto que contendra el estatus del lanzamiento de motor
	 */
	private void estatusLanzamientoMotor(final BeanEstatusCarga estatus) {
		if (estatus.getEstatus().equals(ESTATUS_21)) {
			estatus.setEstatus(DESC_EN_PROCESO);
		} else if (estatus.getEstatus().equals(ESTATUS_22)) {
			estatus.setEstatus(DESC_OK);
		} else if (estatus.getEstatus().equals(ESTATUS_23)) {
			estatus.setEstatus(DESC_ERROR);
		} else {
			estatus.setEstatus(DESC_NO_INICIADO);
			estatus.setOperacion(DESC_OK);
		}
	}

	/**
	 * Metodo que regresa la lista de insumos para el motor rorac
	 *
	 * @param totalInsumosMotor
	 *            total de los insumos del motor rorac
	 * @return la lista de insumo para lanzamiento motor rorac
	 */
	private List<BeanEstatusCarga> extraeConfigsFromList(
			final List<BeanEstatusCarga> totalInsumosMotor,
			final String configsToExtract) {
		List<BeanEstatusCarga> nuevaListaConfiguraciones = new ArrayList<BeanEstatusCarga>();
		List<String> insumosMotor = getListaFromConfig(this
				.getConfigDeCmpAplicacion(configsToExtract).replace(" ", ""));
		for (String config : insumosMotor) {
			for (BeanEstatusCarga insumos : totalInsumosMotor) {
				if (config.equalsIgnoreCase(insumos.getNombreInterfaz()
						.replace(" ", ""))) {
					nuevaListaConfiguraciones.add(insumos);
				}
			}
		}
		return nuevaListaConfiguraciones;
	}

	/**
	 * Metodo que separa las configuraciones por comas
	 *
	 * @param configs
	 *            String que contiene las configuraciones
	 * @return lista de las configuraciones
	 */
	private List<String> getListaFromConfig(final String configs) {
		return Arrays.asList(configs.split(SEPARADOR_CONFIGS));
	}
}
