package mx.isban.rorac.servicio.consultas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.BeanPistasAuditoria;
import mx.isban.rorac.bean.consultas.BeanConsultaMonitor;
import mx.isban.rorac.bean.consultas.BeanEstatusCarga;
import mx.isban.rorac.bean.consultas.BeanListaMonitorCargasDAO;
import mx.isban.rorac.bean.consultas.BeanMonitorCargas;
import mx.isban.rorac.bean.consultas.BeanMonitorCargasRestateo;
import mx.isban.rorac.bean.lanzadores.BeanResultBOListasMonitor;
import mx.isban.rorac.dao.consultas.DAOMonitorCargas;
import mx.isban.rorac.servicio.util.BOPistasAuditoria;
import mx.isban.rorac.utilerias.general.ConstantesRorac;
import mx.isban.rorac.utilerias.general.UtileriasNegocio;

/**
 * Session Bean implementation class BOMonitorCargasImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOMonitorCargasImpl extends Architech implements BOMonitorCargas {
	/**
	 * Identificador de la operacion de carga
	 */
	private static final String ID_OPERACION_CARGA = "PARAM_CONFIG_CARGA_MANUAL_ID_OPERACION";
	/**
	 * Constanta que apunta hacia el valor de configuracion de la operacion de
	 * Carga de Inputs/Outputs finales.
	 */
	private static final String PARAM_CARGA_IO_ID_OPERACION = "PARAM_CONFG_CARGA_IO_ID_OPERACION";
	/**
	 * Estatus para insumos comisiones
	 */
	private static final String COMISIONES = "ROR_REST_COMISIONES";
	/**
	 * Estatus para insumos fondos
	 */
	private static final String FONDOS = "ROR_REST_FONDOS";
	/**
	 * Estatus para insumos contingentes
	 */
	private static final String CONTINGENTES = "ROR_REST_CONTINGENTES";
	/**
	 * Estatus para insumos pasivo
	 */
	private static final String PASIVO = "ROR_REST_PASIVO";
	/**
	 * Estatus para insumos Activo
	 */
	private static final String ACTIVO = "ROR_REST_ACTIVO";
	/**
	 * Estatus para insumos Activo
	 */
	private static final String CLIENTE = "CLIENTE_ADN";
	/**
	 * Configuracion para el id proceso restateo
	 */
	private static final String RESTATEO_ID_OPERACION = "PARAM_CONFIG_RESTATEO_ID_OPERACION";
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1702013970449540705L;
	/**
	 * Id de operacion de Monitor de Cargas.
	 */
	private static final String PARAM_MONITOR_CARGAS_ID_OPERACION = "PARAM_CONFIG_MONITOR_CARGAS_ID_OPERACION";
	/**
	 * Constante que apunta hacia el valor de configuracion que contiene el
	 * indice inicial de los insumos de cargas manuales.
	 */
	private static final String PARAM_CARGAS_MANUALES_INDICE_INICIAL = "PARAM_CONFIG_CARGAS_MANUALES_INDICE_INICIAL";
	/**
	 * Constante que apunta hacia el valor de configuracion que contiene el
	 * indice final de los insumos de Cargas Manuales.
	 */
	private static final String PARAM_CARGAS_MANUALES_INDICE_FINAL = "PARAM_CONFIG_CARGAS_MANUALES_INDICE_FINAL";
	/**
	 * Constante que apunta hacia el valor de configuracion que contiene el
	 * indice inicial de los insumos de Cargas Iniciales.
	 */
	private static final String PARAM_CARGAS_INICIALES_INDICE_INICIAL = "PARAM_CONFIG_CARGAS_INICIALES_INDICE_INICIAL";
	/**
	 * Constante que apunta hacia el valor de configuracion que contiene el
	 * indice final de los insumos de Cargas Iniciales.
	 */
	private static final String PARAM_CARGAS_INICIALES_INDICE_FINAL = "PARAM_CONFIG_CARGAS_INICIALES_INDICE_FINAL";
	/**
	 * Constante que apunta hacia el valor de configuracion que contiene el
	 * indice inicial de las interfaces finales.
	 */
	private static final String PARAM_INTERFACES_FINALES_INDICE_INICIAL = "PARAM_CONFIG_INTERFACES_FINALES_INDICE_INICIAL";
	/**
	 * Constante que apunta hacia el valor de configuracion que contiene el
	 * indice final de las interfaces finales.
	 */
	private static final String PARAM_INTERFACES_FINALES_INDICE_FINAL = "PARAM_CONFIG_INTERFACES_FINALES_INDICE_FINAL";
	/**
	 * Constante que apunta hacia el valor de configuracion que contiene el id
	 * de Operacion Actualizar.
	 */
	private static final String PARAM_CODIGO_ACTUALIZAR = "PARAM_CONFIG_CODIGO_ACTUALIZAR";
	/**
	 * Constante que apunta hacia el valor de configuracion que contiene el id
	 * de Operacion Eliminar.
	 */
	private static final String PARAM_CODIGO_ELIMINAR = "PARAM_CONFIG_CODIGO_ELIMINAR";

	/**
	 * Estatus que indica que un insumo se cargo de manera correcta.
	 */
	private static final String CARGA_INSUMO_ESTATUS_OK = "OK";
	/**
	 * Estatus que indica que un insumo no ha sido cargado.
	 */
	private static final String CARGA_INSUMO_NO_CARGADO = "NO CARGADO";
	/**
	 * Estatus que indica que un insumo fue cargado con error.
	 */
	private static final String CARGA_INSUMO_ESTATUS_ERROR = "ERROR";
	/**
	 * Intancia del objeto de la capa de Negocio encargado de realizar las
	 * consultas de estatus.
	 */
	@EJB
	private transient DAOMonitorCargas daoMonitor;
	/**
	 * Manejador de Pistas de auditoria.
	 */
	@EJB
	private transient BOPistasAuditoria manejadorPistasAuditoria;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.servicio.consultas.BOMonitorCargas#obtenerEstatusCargas
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanMonitorCargas obtenerEstatusCargas(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "obtenerEstatusCargas()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		final BeanMonitorCargas monitorCargas = new BeanMonitorCargas();
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setCodigoOperacion(this
				.getConfigDeCmpAplicacion(PARAM_MONITOR_CARGAS_ID_OPERACION));
		pistaAuditoria.setNombreArchivo("No Aplica");
		try {
			monitorCargas
					.setCargasManuales(llamaConsultaCargasManuales(sessionBean));
			monitorCargas
					.setCargasIniciales(llamaConsultaInsumosIniciales(sessionBean));
			monitorCargas
					.setInterfacesProcesadas(llamaConsultaInterfacesFinales(sessionBean));
		} catch (BusinessException e) {
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw e;
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Finaliza la ejecucion del metodo: " + metodo);
		return monitorCargas;
	}

	/**
	 * Ejecuta la consulta de Cargas Manuales y procesa los resultados.
	 *
	 * @param sessionBean
	 *            Objeto de la Arquitectura Agave.
	 * @return List<BeanEstatusCarga>
	 * @throws BusinessException
	 *             Exception
	 */
	private List<BeanEstatusCarga> llamaConsultaCargasManuales(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaConsultaCargasManuales()";
		this.info("Inicia la ejecucion del metodo: " + metodo);
		final BeanConsultaMonitor beanConsulta = new BeanConsultaMonitor();
		final String idProceso = this
				.getConfigDeCmpAplicacion(ID_OPERACION_CARGA);
		final String idInicial = this
				.getConfigDeCmpAplicacion(PARAM_CARGAS_MANUALES_INDICE_INICIAL);
		final String idFinal = this
				.getConfigDeCmpAplicacion(PARAM_CARGAS_MANUALES_INDICE_FINAL);

		if (idProceso == null || idInicial == null || idFinal == null) {
			this.info("Los parametros necesarios para llevar cabo la consulta de Cargas Manuales no fueron configurados de manera correcta.");
			throw new BusinessException(
					ConstantesRorac.ERROR_CONFIGURACION_MONITOR_CARGAS);
		}
		beanConsulta.setIdProceso(idProceso);
		beanConsulta.setIndiceInicialInsumo(idInicial);
		beanConsulta.setIndiceFinalInsumo(idFinal);
		beanConsulta.setMes(UtileriasNegocio.obtenerMesProceso());
		beanConsulta.setAnio(UtileriasNegocio.obtenerAnio());
		final BeanListaMonitorCargasDAO estatusCargasManuales = daoMonitor
				.consultaCargasManuales(beanConsulta, sessionBean);
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatusCargasManuales
				.getCodError())) {
			this.info("Se obtuvo un codigo de error al ejecutar la consulta de Cargas Manuales: "
					+ estatusCargasManuales.getCodError());
			throw new BusinessException(estatusCargasManuales.getCodError());
		}
		for (BeanEstatusCarga estatus : estatusCargasManuales.getListaMonitor()) {
			if ("10".equals(estatus.getEstatus())) {
				estatus.setEstatus(CARGA_INSUMO_ESTATUS_ERROR);
			} else if ("11".equals(estatus.getEstatus())) {
				estatus.setEstatus(CARGA_INSUMO_ESTATUS_OK);
			} else {
				estatus.setEstatus(CARGA_INSUMO_NO_CARGADO);
			}
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return estatusCargasManuales.getListaMonitor();
	}

	/**
	 * Ejecuta la consulta de Insumos Iniciales y Procesa los resultados.
	 *
	 * @param sessionBean
	 *            Objeto de la Arquitectura Agave.
	 * @return List<BeanEstatusCarga>
	 * @throws BusinessException
	 *             Exception
	 */
	private List<BeanEstatusCarga> llamaConsultaInsumosIniciales(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaConsultaInsumosIniciales()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final BeanConsultaMonitor beanConsulta = new BeanConsultaMonitor();
		final String idProceso = this
				.getConfigDeCmpAplicacion(ID_OPERACION_CARGA);
		final String idInicial = this
				.getConfigDeCmpAplicacion(PARAM_CARGAS_INICIALES_INDICE_INICIAL);
		final String idFinal = this
				.getConfigDeCmpAplicacion(PARAM_CARGAS_INICIALES_INDICE_FINAL);
		if (idProceso == null || idInicial == null || idFinal == null) {
			this.info("Los parametros necesarios para llevar a cabo la consulta de Insumos Iniciales no fueron configurados de manera correcta.");
			throw new BusinessException(
					ConstantesRorac.ERROR_CONFIGURACION_MONITOR_CARGAS);
		}
		beanConsulta.setIdProceso(idProceso);
		beanConsulta.setIndiceInicialInsumo(idInicial);
		beanConsulta.setIndiceFinalInsumo(idFinal);
		final BeanListaMonitorCargasDAO estatusCargasIniciales = daoMonitor
				.consultaInsumosIniciales(beanConsulta, sessionBean);
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatusCargasIniciales
				.getCodError())) {
			this.info("Se obtuvo un codigo de error al ejecutar la consulta de Insumos Iniciales: "
					+ estatusCargasIniciales.getCodError());
			throw new BusinessException(estatusCargasIniciales.getCodError());
		}
		Collections.sort(estatusCargasIniciales.getListaMonitor(),
				new Comparator<BeanEstatusCarga>() {
					@Override
					public int compare(final BeanEstatusCarga obj1,
							final BeanEstatusCarga obj2) {
						final int comparacionNombre = obj1.getNombreInterfaz()
								.compareTo(obj2.getNombreInterfaz());
						if (comparacionNombre == 0) {
							return obj2.getNombreInterfaz().compareTo(
									obj1.getNombreInterfaz());
						}
						return comparacionNombre;
					}
				});
		String nombreInterfaz = null;
		int indice = 0;
		// Este proceso sirve para dejar solo los registros del mes actual para
		// cada interfaz.
		while (indice < estatusCargasIniciales.getListaMonitor().size()) {
			if (nombreInterfaz == null) {
				nombreInterfaz = estatusCargasIniciales.getListaMonitor()
						.get(indice).getNombreInterfaz();
				indice++;
			} else if (nombreInterfaz.equals(estatusCargasIniciales
					.getListaMonitor().get(indice).getNombreInterfaz())) {
				estatusCargasIniciales.getListaMonitor().remove(indice);
			} else {
				nombreInterfaz = estatusCargasIniciales.getListaMonitor()
						.get(indice).getNombreInterfaz();
				indice++;
			}
		}
		for (BeanEstatusCarga estatus : estatusCargasIniciales
				.getListaMonitor()) {
			if ("10".equals(estatus.getEstatus())) {
				estatus.setEstatus(CARGA_INSUMO_ESTATUS_ERROR);
			} else if ("11".equals(estatus.getEstatus())) {
				estatus.setEstatus(CARGA_INSUMO_ESTATUS_OK);
			} else {
				estatus.setEstatus(CARGA_INSUMO_NO_CARGADO);
			}
		}

		this.info("Termina la ejecucion del metodo " + metodo);
		return estatusCargasIniciales.getListaMonitor();
	}

	/**
	 * Ejecuta la consulta de estatus de Interfaces Finales.
	 *
	 * @param sessionBean
	 *            Objeto de la arquitectura Agave.
	 * @return List<BeanEstatusCarga>
	 * @throws BusinessException
	 *             Exception
	 */
	private List<BeanEstatusCarga> llamaConsultaInterfacesFinales(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaConsultaInterfacesFinales()";
		this.info("Comienza la ejecucion del metodo: " + metodo);
		final BeanConsultaMonitor beanConsulta = new BeanConsultaMonitor();
		final String idProceso = this
				.getConfigDeCmpAplicacion(PARAM_CARGA_IO_ID_OPERACION);
		final String idInicial = this
				.getConfigDeCmpAplicacion(PARAM_INTERFACES_FINALES_INDICE_INICIAL);
		final String idFinal = this
				.getConfigDeCmpAplicacion(PARAM_INTERFACES_FINALES_INDICE_FINAL);
		final String idOperacionActualizar = this
				.getConfigDeCmpAplicacion(PARAM_CODIGO_ACTUALIZAR);
		final String idOperacionEliminar = this
				.getConfigDeCmpAplicacion(PARAM_CODIGO_ELIMINAR);
		if (idProceso == null || idInicial == null || idFinal == null
				|| idOperacionActualizar == null || idOperacionEliminar == null) {
			this.info("Los parametros necesarios para la ejecucion de la consulta de estatus de Interfaces "
					+ "Finales no fueron configurados de manera correcta.");
			throw new BusinessException(
					ConstantesRorac.ERROR_CONFIGURACION_MONITOR_CARGAS);
		}
		beanConsulta.setIdProceso(idProceso);
		beanConsulta.setIndiceInicialInsumo(idInicial);
		beanConsulta.setIndiceFinalInsumo(idFinal);
		beanConsulta.setMes(UtileriasNegocio.obtenerMesProceso());
		beanConsulta.setAnio(UtileriasNegocio.obtenerAnio());
		final BeanListaMonitorCargasDAO estatusInterfacesFinales = daoMonitor
				.consultaEstatusInterfacesFinales(beanConsulta, sessionBean);
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatusInterfacesFinales
				.getCodError())) {
			this.info("Se obtuvo un codigo de error al ejecutar la consulta de estatus de Interfaces Finales: "
					+ estatusInterfacesFinales.getCodError());
			throw new BusinessException(estatusInterfacesFinales.getCodError());
		}
		for (BeanEstatusCarga estatus : estatusInterfacesFinales
				.getListaMonitor()) {
			if ("10".equals(estatus.getEstatus())) {
				estatus.setEstatus(CARGA_INSUMO_ESTATUS_ERROR);
			} else if ("11".equals(estatus.getEstatus())) {
				estatus.setEstatus(CARGA_INSUMO_ESTATUS_OK);
			} else {
				estatus.setEstatus(CARGA_INSUMO_NO_CARGADO);
			}
			if (idOperacionActualizar.equals(estatus.getOperacion())) {
				estatus.setNombreInterfaz("Actualiza "
						+ estatus.getNombreInterfaz());
			} else if (idOperacionEliminar.equals(estatus.getOperacion())) {
				estatus.setNombreInterfaz("Eliminacion "
						+ estatus.getNombreInterfaz());
			}
		}
		this.info("Termina la ejecucion del metodo: " + metodo);

		return estatusInterfacesFinales.getListaMonitor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.isban.rorac.servicio.consultas.BOMonitorCargas#
	 * obtenerEstatusCargasRestateo
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanMonitorCargasRestateo obtenerEstatusCargasRestateo(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "obtenerEstatusCargasRestateo()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		BeanMonitorCargasRestateo monitorMotor = new BeanMonitorCargasRestateo();
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setCodigoOperacion(this
				.getConfigDeCmpAplicacion(RESTATEO_ID_OPERACION));
		pistaAuditoria.setNombreArchivo("No Aplica");
		try {
			List<BeanEstatusCarga> estatusInsumosRestateo = consultaEstatusInsumosRestateo(sessionBean);
			monitorMotor = fillMonitorRestateo(estatusInsumosRestateo);
		} catch (BusinessException e) {
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw e;
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Finaliza la ejecucion del metodo: " + metodo);
		return monitorMotor;
	}

	/**
	 * Metodo para llenar el monitor de cargas para restateo
	 *
	 * @param estatusInsumosRestateo
	 *            el total de los insumos
	 * @return el objeto BeanMonitorCargas Restateo llenado
	 */
	private BeanMonitorCargasRestateo fillMonitorRestateo(
			final List<BeanEstatusCarga> estatusInsumosRestateo) {
		final BeanMonitorCargasRestateo monitorMotor = new BeanMonitorCargasRestateo();
		monitorMotor.setRestCliente(extraeConfigsFromList(
				estatusInsumosRestateo, CLIENTE));
		monitorMotor.setRestActivo(extraeConfigsFromList(
				estatusInsumosRestateo, ACTIVO));
		monitorMotor.setRestPasivo(extraeConfigsFromList(
				estatusInsumosRestateo, PASIVO));
		monitorMotor.setRestContingentes(extraeConfigsFromList(
				estatusInsumosRestateo, CONTINGENTES));
		monitorMotor.setRestFondos(extraeConfigsFromList(
				estatusInsumosRestateo, FONDOS));
		monitorMotor.setRestComisiones(extraeConfigsFromList(
				estatusInsumosRestateo, COMISIONES));
		return monitorMotor;
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
		for (BeanEstatusCarga insumos : totalInsumosMotor) {
			if (configsToExtract.equalsIgnoreCase(insumos.getNombreInterfaz())) {
				nuevaListaConfiguraciones.add(insumos);
			}
		}
		return nuevaListaConfiguraciones;
	}

	/**
	 * Metodo para consultar el total de los insumos para restateo
	 *
	 * @param sessionBean
	 *            el objeto de session
	 * @return la lista de todos los insumos para restateo
	 * @throws BusinessException
	 *             excepcion lanzada al ocurrir un error
	 */
	private List<BeanEstatusCarga> consultaEstatusInsumosRestateo(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "consultaEstatusInsumosMotor()";
		this.info("Inicia la ejecucion del metodo: " + metodo);
		final String idProceso = this
				.getConfigDeCmpAplicacion(RESTATEO_ID_OPERACION);
		final BeanResultBOListasMonitor estatusMonitor = daoMonitor
				.consultaEstatusInsumos(idProceso, sessionBean);
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatusMonitor
				.getCodError())) {
			this.info("Se obtuvo un codigo de error al ejecutar la consulta de Cargas Manuales: "
					+ estatusMonitor.getCodError());
			throw new BusinessException(estatusMonitor.getCodError());
		}
		for (BeanEstatusCarga estatus : estatusMonitor.getListaMonitorMotor()) {
			if ("10".equals(estatus.getEstatus())) {
				estatus.setEstatus(CARGA_INSUMO_ESTATUS_ERROR);
			} else if ("11".equals(estatus.getEstatus())) {
				estatus.setEstatus(CARGA_INSUMO_ESTATUS_OK);
			} else {
				estatus.setEstatus(CARGA_INSUMO_NO_CARGADO);
			}
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return estatusMonitor.getListaMonitorMotor();
	}
}
