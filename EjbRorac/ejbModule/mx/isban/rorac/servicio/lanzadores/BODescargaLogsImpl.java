package mx.isban.rorac.servicio.lanzadores;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.agave.commons.interfaces.BeanResultBO;
import mx.isban.rorac.bean.BeanPistasAuditoria;
import mx.isban.rorac.bean.consultas.BeanMonitorCargas;
import mx.isban.rorac.bean.lanzadores.BeanEstatusLog;
import mx.isban.rorac.bean.lanzadores.BeanListaLogsDAO;
import mx.isban.rorac.bean.lanzadores.BeanLogValidaciones;
import mx.isban.rorac.bean.lanzadores.BeanLogValidacionesRestateo;
import mx.isban.rorac.bean.lanzadores.BeanParametrosLogs;
import mx.isban.rorac.dao.lanzadores.DAODescargaLogs;
import mx.isban.rorac.servicio.util.BOPistasAuditoria;
import mx.isban.rorac.utilerias.general.ConstantesRorac;
import mx.isban.rorac.utilerias.general.UtileriasNegocio;

/**
 * Session Bean implementation class BODescargaLogsImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BODescargaLogsImpl extends Architech implements BODescargaLogs {

	/**
	 * Nombre del log de validacion para adn pasivo
	 */
	private static final String ADN_PASIVO = "Valid_AdNRestPasivo";
	/**
	 * Nombre del log de validacion para adn activo
	 */
	private static final String ADN_ACTIVO = "Valid_AdNRestActivo";
	/**
	 * Nombre del log de validacion para contrato pasivo
	 */
	private static final String CONT_PASIVO = "Valid_ContRestPasivo";
	/**
	 * Nombre del log de validacion para contrato activo
	 */
	private static final String CONT_ACTIVO = "Valid_ContRestActivo";
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 2356522350405521503L;
	/**
	 * Configuracion para el id proceso restateo
	 */
	private static final String RESTATEO_ID_OPERACION = "PARAM_CONFIG_RESTATEO_ID_OPERACION";
	/**
	 * Nombre del parametro que contiene el codigo de Log Generado.
	 */
	private static final String PARAM_ESTATUS_LOG_GENERADO = "PARAM_CONFIG_ESTATUS_LOG_GENERADO";
	/**
	 * Nombre del parametro que contiene el codigo de Log registrado para
	 * descarga.
	 */
	private static final String PARAM_ESTATUS_LOG_DESCARGA = "PARAM_CONFIG_ESTATUS_LOG_DESCARGA";
	/**
	 * Parametro que contiene el ID de proceso de logs de erroes.
	 */
	private static final String PARAM_CODIGO_LOGS_ERRORES = "PARAM_CONFIG_CODIGO_LOGS_ERRORES";
	/**
	 * Parametro que contiene el ID de proceso de logs de validaciones
	 */
	private static final String PARAM_CODIGO_LOGS_VALIDACIONES = "PARAM_CONFIG_CODIGO_LOGS_VALIDACIONES";
	/**
	 * Nombre del parametro que contiene el indice inicial de los logs de
	 * errores.
	 */
	private static final String PARAM_INDICE_INICIAL_ERRORES = "PARAM_CONFIG_INDICE_INICIAL_ERRORES";
	/**
	 * Nombre del parametro que contiene el indice final de los logs de errores.
	 */
	private static final String PARAM_INDICE_FINAL_ERRORES = "PARAM_CONFIG_INDICE_FINAL_ERRORES";
	/**
	 * Nombre del parametro que contiene el indice inicial de los logs de
	 * validaciones.
	 */
	private static final String PARAM_INDICE_INICIAL_VALIDACIONES = "PARAM_CONFIG_INDICE_INICIAL_VALIDACIONES";
	/**
	 * Nombre del parametro que contiene el indice de inicio de las validaciones
	 * a nivel total sobre input final
	 */
	private static final String PARAM_INDICE_TOTAL_INPUT_FINAL = "PARAM_CONFIG_INDICE_TOTAL_INPUT_FINAL";
	/**
	 * Nombre del parametro que contiene el indice de inicio de las validaciones
	 * a nivel total sobre el input de activo.
	 */
	private static final String PARAM_INDICE_TOTAL_INPUT_ACTIVO = "PARAM_CONFIG_INDICE_TOTAL_INPUT_ACTIVO";
	/**
	 * Nombre del parametro que contiene el indice final de los logs de
	 * validaciones.
	 */
	private static final String PARAM_INDICE_FINAL_VALIDACIONES = "PARAM_CONFIG_INDICE_FINAL_VALIDACIONES";
	/**
	 * Objeto de la capa de acceso a datos.
	 */
	@EJB
	private transient DAODescargaLogs daoDescarga;
	/**
	 * Manejador de Pistas de Auditoria
	 */
	@EJB
	private transient BOPistasAuditoria manejadorPistasAuditoria;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.servicio.lanzadores.BODescargaLogs#obtenerEstatusLogsCruces
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public List<BeanEstatusLog> obtenerEstatusLogsCruces(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "obtenerEstatusLogsCruces()";
		this.info("Inicio de ejecucion del metodo " + metodo);

		final BeanParametrosLogs beanParametros = generaListaParametros(
				PARAM_CODIGO_LOGS_ERRORES, PARAM_INDICE_INICIAL_ERRORES,
				PARAM_INDICE_FINAL_ERRORES);
		this.info("Se realiza la peticion a la capa de acceso a datos para obtener la lista de logs de cruces.");
		final BeanListaLogsDAO respuesta = daoDescarga.consultaEstatusLogs(
				beanParametros, sessionBean);
		if (respuesta == null) {
			this.info("Se obtuvo una respuesta nula de la capa de acceso a datos.");
			throw new BusinessException(ConstantesRorac.ERROR_NO_RESPUESTA_LOGS);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(respuesta.getCodError())) {
			this.info("Se obtuvo el siguiente codigo de error: "
					+ respuesta.getCodError());
			throw new BusinessException(respuesta.getCodError());
		}
		this.info("Se envian los resultados obtenidos a la capa Front");
		this.info("Fin de ejecucion de metodo " + metodo);
		return respuesta.getListaLogs();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.isban.rorac.servicio.lanzadores.BODescargaLogs#
	 * obtenerEstatusLogsValidaciones
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanLogValidaciones obtenerEstatusLogsValidaciones(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "obtenerEstatusLogsValidaciones";
		this.info("Inicio de ejecucion de metodo: " + metodo);

		final BeanParametrosLogs beanParametros = generaListaParametros(
				PARAM_CODIGO_LOGS_VALIDACIONES,
				PARAM_INDICE_INICIAL_VALIDACIONES,
				PARAM_INDICE_FINAL_VALIDACIONES);
		this.info("Se realiza la peticion a la capa de acceso a datos pata obtener la lista de los de validaciones");
		final BeanListaLogsDAO logsValidaciones = daoDescarga
				.consultaEstatusLogs(beanParametros, sessionBean);
		if (logsValidaciones == null) {
			this.info("Se obtuvo una respuesta nula de la capa de acceso a datos.");
			throw new BusinessException(ConstantesRorac.ERROR_NO_RESPUESTA_LOGS);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(logsValidaciones
				.getCodError())) {
			this.info("Se obtuvo el codigo de error "
					+ logsValidaciones.getCodError());
			throw new BusinessException(logsValidaciones.getCodError());
		}

		final BeanLogValidaciones respuesta = new BeanLogValidaciones();
		final List<BeanEstatusLog> contratoActivo = new ArrayList<BeanEstatusLog>();
		final List<BeanEstatusLog> contratoPasivo = new ArrayList<BeanEstatusLog>();
		final List<BeanEstatusLog> totalActivo = new ArrayList<BeanEstatusLog>();
		final List<BeanEstatusLog> totalFinal = new ArrayList<BeanEstatusLog>();

		final int inicioNivelContrato = Integer.parseInt(this
				.getConfigDeCmpAplicacion(PARAM_INDICE_INICIAL_VALIDACIONES));
		final int inicioTotalInputFinal = Integer.parseInt(this
				.getConfigDeCmpAplicacion(PARAM_INDICE_TOTAL_INPUT_FINAL));
		final int inicioTotalInputActivo = Integer.parseInt(this
				.getConfigDeCmpAplicacion(PARAM_INDICE_TOTAL_INPUT_ACTIVO));
		final int inicioContratoPasivo = Integer.parseInt(this
				.getConfigDeCmpAplicacion(PARAM_INDICE_FINAL_VALIDACIONES));

		for (BeanEstatusLog log : logsValidaciones.getListaLogs()) {
			this.debug(log.getIdRegistroEstatus() + " " + log.getNombreLog()
					+ " " + log.isGenerado());
			final int codigoLog = Integer.parseInt(log.getIdLogInsumos());
			if (codigoLog >= inicioNivelContrato
					&& codigoLog < inicioTotalInputFinal) {
				contratoActivo.add(log);
			} else if (codigoLog >= inicioTotalInputFinal
					&& codigoLog < inicioTotalInputActivo) {
				totalFinal.add(log);
			} else if (codigoLog >= inicioTotalInputActivo
					&& codigoLog < inicioContratoPasivo) {
				totalActivo.add(log);
			} else if (codigoLog >= inicioContratoPasivo) {
				contratoPasivo.add(log);
			}
		}
		respuesta.setValidacionesContratoActivo(contratoActivo);
		respuesta.setValidacionesContratoPasivo(contratoPasivo);
		respuesta.setValidacionesTotalActivo(totalActivo);
		respuesta.setValidacionesTotalFinal(totalFinal);
		this.info("El estatus de los logs de validaciones se envia a la capa front");
		this.info("Termina la ejecucion de " + metodo);
		return respuesta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.servicio.lanzadores.BODescargaLogs#lanzaDescargaLog(java
	 * .lang.String, mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void lanzaDescargaLog(final String idLog,
			final ArchitechSessionBean bean) throws BusinessException {
		final String metodo = "lanzaDescargaLog()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		this.info("El idEstatus del log que sera registrado para descarga es "
				+ idLog);
		final String codigoDescarga = this
				.getConfigDeCmpAplicacion(PARAM_ESTATUS_LOG_DESCARGA);
		if (codigoDescarga == null) {
			this.info("El parametro que contiene el codigo de estatus de descarga no fue configurado. "
					+ "Por lo que no es posible continuar con la operacion");
			throw new BusinessException(
					ConstantesRorac.ERROR_CONFIGURACION_LOGS);
		}

		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setIdEstatus(idLog);

		final BeanResultBO estatus = daoDescarga.registraLogParaDescarga(idLog,
				codigoDescarga, bean);
		if (estatus == null) {
			this.info("Se obtubo una respuesta nula desde la capa de negocio.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria, bean);
			throw new BusinessException(ConstantesRorac.ERROR_NO_RESPUESTA_LOGS);
		} else if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus
				.getCodError())) {
			this.info("Se obtuvo un codigo de error desde la capa de acceso a datos "
					+ estatus.getCodError());
			pistaAuditoria.setEstatusOperacion("Error");
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria, bean);
			throw new BusinessException(estatus.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria, bean);
		this.info("La operacion de descarga ha sido registrada para el log "
				+ idLog);
		this.debug("Finaliza la ejecucion del metodo " + metodo);
	}

	/**
	 * Creal un bean con los parametros necesarios para ejecutar la consulta de
	 * estatus de los logs.
	 *
	 * @param idOperacion
	 *            Define si se consultan los logs de errores o los logs de
	 *            validaciones.
	 * @param idInicial
	 *            Define el indice inicial de busqueda de los logs.
	 * @param idFinal
	 *            Define el indice final de busqueda de los logs.
	 * @return BeanParametros
	 * @throws BusinessException
	 *             Excepcion.
	 */
	private BeanParametrosLogs generaListaParametros(final String idOperacion,
			final String idInicial, final String idFinal)
			throws BusinessException {
		final BeanParametrosLogs beanParametros = new BeanParametrosLogs();

		final String idProceso = this.getConfigDeCmpAplicacion(idOperacion);
		final String indiceInicial = this.getConfigDeCmpAplicacion(idInicial);
		final String indiceFinal = this.getConfigDeCmpAplicacion(idFinal);
		final String codigoLogGenerado = this
				.getConfigDeCmpAplicacion(PARAM_ESTATUS_LOG_GENERADO);
		if (idProceso == null || indiceInicial == null || indiceFinal == null
				|| codigoLogGenerado == null) {
			this.info("Los parametros necesarios para la ejecucion de la consulta no han sido configurados.");
			throw new BusinessException(
					ConstantesRorac.ERROR_CONFIGURACION_LOGS);
		}
		beanParametros.setIdProceso(idProceso);
		beanParametros.setIdLogInicial(indiceInicial);
		beanParametros.setIdLogFinal(indiceFinal);
		beanParametros.setCodigoLogGenerado(codigoLogGenerado);
		beanParametros.setAnio(UtileriasNegocio.obtenerAnio());
		beanParametros.setMes(UtileriasNegocio.obtenerMesProceso());

		return beanParametros;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.isban.rorac.servicio.lanzadores.BODescargaLogs#
	 * obtenerLogsValidacionesRestateo
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanLogValidacionesRestateo obtenerLogsValidacionesRestateo(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "obtenerLogsValidacionesRestateo";
		this.info("Inicio de ejecucion de metodo: " + metodo);
		this.info("Se consultan los logs de validacion para Restateo");
		List<BeanEstatusLog> listaLogs = consultaLogValidacion(sessionBean);
		final BeanLogValidacionesRestateo respuesta = fillValidacionesRestateo(listaLogs);
		this.info("Termina la ejecucion de " + metodo);
		
		
		
		
		return respuesta;
	}

	/**
	 * Metodo que ccompleta el listado de las configuraciones para los logs de
	 * validacion
	 *
	 * @param listaLogs
	 *            el total de los logs
	 * @return listas separadas dependiendo el tipo de log
	 */
	private BeanLogValidacionesRestateo fillValidacionesRestateo(
			final List<BeanEstatusLog> listaLogs) {
		final BeanLogValidacionesRestateo respuesta = new BeanLogValidacionesRestateo();
		respuesta.setValidacionesContratoActivo(extraeConfigsFromList(
				listaLogs, CONT_ACTIVO));
		respuesta.setValidacionesContratoPasivo(extraeConfigsFromList(
				listaLogs, CONT_PASIVO));
		respuesta.setValidacionesAdNActivo(extraeConfigsFromList(listaLogs,
				ADN_ACTIVO));
		respuesta.setValidacionesAdNPasivo(extraeConfigsFromList(listaLogs,
				ADN_PASIVO));
		return respuesta;
	}

	/**
	 * Extrae algun tipo de configuracion de una lista
	 *
	 * @param listaLogs
	 *            listado con el total de las configuraciones para los logs de
	 *            validacion restateo
	 * @param config
	 *            la configuracion a extraer
	 * @return una lista de las configuraciones que encontro
	 */
	private List<BeanEstatusLog> extraeConfigsFromList(
			final List<BeanEstatusLog> listaLogs, final String config) {
		List<BeanEstatusLog> nuevaListaConfiguraciones = new ArrayList<BeanEstatusLog>();
		for (BeanEstatusLog insumos : listaLogs) {
			if (insumos.getNombreLog().startsWith(config)) {
				nuevaListaConfiguraciones.add(insumos);
			}
		}
		return nuevaListaConfiguraciones;
	}

	/**
	 * Metodo que maneja la consulta de los logs de validacion para restateo
	 *
	 * @param sessionBean
	 *            el bean de session
	 * @return lista de status de logs
	 * @throws BusinessException
	 *             Excepcion lanzada al ocurrir un error
	 */
	private List<BeanEstatusLog> consultaLogValidacion(
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String idProceso = this
				.getConfigDeCmpAplicacion(RESTATEO_ID_OPERACION);
		final BeanListaLogsDAO logsValidaciones = daoDescarga
				.consultaEstatusLogsRestateo(idProceso, sessionBean);
		if (logsValidaciones == null) {
			this.info("Se obtuvo una respuesta nula de la capa de acceso a datos.");
			throw new BusinessException(ConstantesRorac.ERROR_NO_RESPUESTA_LOGS);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(logsValidaciones
				.getCodError())) {
			this.info("Se obtuvo el codigo de error "
					+ logsValidaciones.getCodError());
			throw new BusinessException(logsValidaciones.getCodError());
		}
		
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		final BeanMonitorCargas monitorCargas = new BeanMonitorCargas();

		pistaAuditoria.setCodigoOperacion(this
				.getConfigDeCmpAplicacion(RESTATEO_ID_OPERACION));
		pistaAuditoria.setNombreArchivo("No Aplica");
		try {
			
		} catch (Exception e) {
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			
		}
		
		
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria, sessionBean);
		
		
		
		return logsValidaciones.getListaLogs();
	}
}
