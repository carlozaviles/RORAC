package mx.isban.rorac.servicio.lanzadores;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.agave.commons.interfaces.BeanResultBO;
import mx.isban.rorac.bean.BeanPistasAuditoria;
import mx.isban.rorac.bean.lanzadores.BeanLanzamientoMotorRorac;
import mx.isban.rorac.bean.lanzadores.BeanLanzamientoOperacion;
import mx.isban.rorac.dao.lanzadores.DAOLanzadorOperaciones;
import mx.isban.rorac.servicio.util.BOPistasAuditoria;
import mx.isban.rorac.utilerias.general.ConstantesRorac;
import mx.isban.rorac.utilerias.general.UtileriasNegocio;

/**
 * Session Bean implementation class BOCargaIOFinalesImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOLanzadoresImpl extends Architech implements BOLanzadores {

	/**
	 * Estatus inicio de la ejecucion para el Motor Rorac
	 */
	private static final String ESTATUS_INICIO_LMR = "21";
	/**
	 * Estatus de inicio de validacion
	 */
	private static final String ESTATUS_INICIO_VALIDACION = "01";
	/**
	 * Nombre del archivo
	 */
	private static final String NAME_CARGA_TXT = "carga.txt";
	/**
	 * Configuracion que contiene la ruta donde sera generado el archivo
	 * carga.txt
	 */
	private static final String PATH_CARGA_TXT = "PARAM_CONFIG_PATH_CARGA_TXT";
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -6629866769694755234L;
	/**
	 * Codigo de operacion de descarga.
	 */
	private static final String PARAM_CODIGO_INICIAR_OPERACION = "PARAM_CONFIG_CODIGO_INICIAR_OPERACION";
	/**
	 * Codigo que contiene el ID de operacion.
	 */
	private static final String PARAM_CARGA_IO_ID_OPERACION = "PARAM_CONFG_CARGA_IO_ID_OPERACION";
	/**
	 * Codigo de operacion Actualizar
	 */
	private static final String PARAM_CODIGO_ACTUALIZAR = "PARAM_CONFIG_CODIGO_ACTUALIZAR";
	/**
	 * Codigo de la operacion Eliminar
	 */
	private static final String PARAM_CODIGO_ELIMINAR = "PARAM_CONFIG_CODIGO_ELIMINAR";
	/**
	 * Contiene el indice en la tabla de insumos para el archivo Input Activo.
	 */
	private static final String PARAM_INDICE_INPUT_ACTIVO = "PARAM_CONFIG_INDICE_INPUT_ACTIVO";
	/**
	 * Contiene el indice en la tabla de insumos para el archivo Input Pasivo.
	 */
	private static final String PARAM_INDICE_INPUT_PASIVO = "PARAM_CONFIG_INDICE_INPUT_PASIVO";
	/**
	 * Contiene el indice en la tabla de insumos para el archivo Input Activo.
	 */
	private static final String PARAM_INDICE_INPUT_ACTIVO_REPROCESOS = "PARAM_CONFIG_INDICE_INPUT_ACTIVO_REPROCESOS";
	/**
	 * Contiene el indice en la tabla de insumos para el archivo Input Pasivo.
	 */
	private static final String PARAM_INDICE_INPUT_PASIVO_REPROCESOS = "PARAM_CONFIG_INDICE_INPUT_PASIVO_REPROCESOS";
	/**
	 * Contiene el indice en la tabla de insumos para el archivo Output Activo.
	 */
	private static final String PARAM_INDICE_OUTPUT_ACTIVO_APROV = "PARAM_CONFIG_INDICE_OUTPUT_ACTIVO_APROV";
	/**
	 * Contiene el indice en la tabla de insumos para el archivo Output Pasivo.
	 */
	private static final String PARAM_INDICE_OUTPUT_PASIVO_APROV = "PARAM_CONFIG_INDICE_OUTPUT_PASIVO_APROV";
	/**
	 * Id de operacion para el lanzamiento de motor RORAC.
	 */
	private static final String PARAM_MOTOR_RORAC_OPERACION = "PARAM_CONFIG_MOTOR_RORAC_OPERACION";
	/**
	 * Id de Proceso del lanzador de reprocesos.
	 */
	private static final String PARAM_LANZADOR_REPROCESO_ID_OPERACION = "PARAM_CONFIG_LANZADOR_REPROCESO_ID_OPERACION";
	/**
	 * Id de proceso de lanzador de Aprovisionamiento Historico.
	 */
	private static final String PARAM_LANZADOR_APROVISIONAMIENTO_ID_OPERACION = "PARAM_CONFIG_LANZADOR_APROVISIONAMIENTO_ID_OPERACION";
	/**
	 * Instancia de DAOCargaIOFinales
	 */
	@EJB
	private transient DAOLanzadorOperaciones daoLanzador;
	/**
	 * Manejador de Pista de Auditoria
	 */
	@EJB
	private transient BOPistasAuditoria manejadorPistasAuditoria;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.servicio.lanzadores.BOCargaIOFinales#registraCargaArchivo
	 * (java.lang.String, java.lang.String,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void registraCargaArchivo(final String idArchivo,
			final String idOperacion, final ArchitechSessionBean sessionBean)
			throws BusinessException {
		final boolean estatusCodigos = this
				.getConfigDeCmpAplicacion(PARAM_CODIGO_ACTUALIZAR) == null
				|| this.getConfigDeCmpAplicacion(PARAM_CODIGO_ELIMINAR) == null
				|| this.getConfigDeCmpAplicacion(PARAM_CODIGO_INICIAR_OPERACION) == null
				|| this.getConfigDeCmpAplicacion(PARAM_CARGA_IO_ID_OPERACION) == null;
		final boolean estatusIndiceArchivos = this
				.getConfigDeCmpAplicacion(PARAM_INDICE_INPUT_ACTIVO) == null
				|| this.getConfigDeCmpAplicacion(PARAM_INDICE_INPUT_PASIVO) == null;
		if (estatusCodigos || estatusIndiceArchivos) {
			this.info("Los parametros de configuracion necesarios para llevar a cabo la operacion no fueron dados de alta.");
			throw new BusinessException(
					ConstantesRorac.ERROR_CONFIGURACION_IO_FINALES);
		}
		if (!(this.getConfigDeCmpAplicacion(PARAM_CODIGO_ACTUALIZAR).equals(
				idOperacion) || this.getConfigDeCmpAplicacion(
				PARAM_CODIGO_ELIMINAR).equals(idOperacion))) {
			this.info("El codigo de operacion recibido no es valido: "
					+ idOperacion);
			throw new BusinessException(
					ConstantesRorac.ERROR_PARAMETROS_ENTRADA_IO_FINALES);
		}
		String indiceArchivo = null;
		if ("1".equals(idArchivo)) {
			indiceArchivo = this
					.getConfigDeCmpAplicacion(PARAM_INDICE_INPUT_ACTIVO);
		} else if ("2".equals(idArchivo)) {
			indiceArchivo = this
					.getConfigDeCmpAplicacion(PARAM_INDICE_INPUT_PASIVO);
		} else {
			this.info("El identificador de archivo recibido no es valido: "
					+ idArchivo);
			throw new BusinessException(
					ConstantesRorac.ERROR_PARAMETROS_ENTRADA_IO_FINALES);
		}
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setCodigoOperacion(this
				.getConfigDeCmpAplicacion(PARAM_CARGA_IO_ID_OPERACION));
		pistaAuditoria.setIdInsumo(indiceArchivo);
		final BeanLanzamientoOperacion bean = new BeanLanzamientoOperacion();
		bean.setIndiceArchivo(indiceArchivo);
		bean.setIdOperacion(idOperacion);
		bean.setIdProceso(this
				.getConfigDeCmpAplicacion(PARAM_CARGA_IO_ID_OPERACION));
		bean.setAnio(UtileriasNegocio.obtenerAnio());
		bean.setMes(UtileriasNegocio.obtenerMesProceso());
		bean.setCodigoEstatus(this
				.getConfigDeCmpAplicacion(PARAM_CODIGO_INICIAR_OPERACION));
		final BeanResultBO beanResult = daoLanzador.registraOperacion(bean,
				sessionBean);
		if (beanResult == null) {
			this.info("Se obtuvo una respuesta nula de la capa de acceso a datos.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_IO_FINALES);
		} else if (!ConstantesRorac.OPERACION_EXITOSA.equals(beanResult
				.getCodError())) {
			this.info("Se obtuvo un codigo de error de la capa de acceso a datos: "
					+ beanResult.getCodError());
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(beanResult.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.servicio.lanzadores.BOLanzadores#lanzaMotorRorac(mx.isban
	 * .agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void lanzaMotorRorac(final ArchitechSessionBean sessionBean)
			throws BusinessException {
		final String metodo = "lanzaMotorRorac()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		this.info("El usuario ha solicitado la ejecucion del motor Rorac.");
		final String idProceso = this
				.getConfigDeCmpAplicacion(PARAM_MOTOR_RORAC_OPERACION);
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setCodigoOperacion(idProceso);
		pistaAuditoria
				.setNombreArchivo(ConstantesRorac.PISTAS_AUDITORIA_NO_APLICA);
		this.info("Se realiza la peticion a la capa de acceso a datos para registrar el lanzamiento del motor rorac.");
		final BeanResultBO estatus = daoLanzador.updateRegistroOperacionMotor(
				idProceso, ESTATUS_INICIO_LMR, null, sessionBean);
		if (estatus == null) {
			this.info("No se obtuvo un codigo de estatus de la capa de acceso a datos.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_IO_FINALES);
		} else if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus
				.getCodError())) {
			this.info("Se obtuvo un codigo de error al intentar registrar el lanzamiento del motor RORAC: "
					+ estatus.getCodError());
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(estatus.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Se obtuvo un codigo de operacion exitosa al registrar el lanzamiento de la ejecucion del motor RORAC.");
		this.info("Finaliza la ejecucion del metodo " + metodo);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.servicio.lanzadores.BOLanzadores#lanzaReproceso(mx.isban
	 * .rorac.bean.lanzadores.BeanLanzamientoOperacion,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void lanzaReproceso(final BeanLanzamientoOperacion datosReproceso,
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "lanzaReproceso()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final String idProceso = this
				.getConfigDeCmpAplicacion(PARAM_LANZADOR_REPROCESO_ID_OPERACION);
		final String estatusOperacion = this
				.getConfigDeCmpAplicacion(PARAM_CODIGO_INICIAR_OPERACION);
		final String idInputActivo = this
				.getConfigDeCmpAplicacion(PARAM_INDICE_INPUT_ACTIVO_REPROCESOS);
		final String idInputPasivo = this
				.getConfigDeCmpAplicacion(PARAM_INDICE_INPUT_PASIVO_REPROCESOS);
		if (idProceso == null || estatusOperacion == null
				|| idInputActivo == null || idInputPasivo == null) {
			this.info("Los parametros de lanzador de reprocesos no fueron configurados de manera correcta.");
			throw new BusinessException(
					ConstantesRorac.ERROR_CONFIGURACION_REPROCESOS);
		}

		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setCodigoOperacion(idProceso);

		if ("1".equals(datosReproceso.getIndiceArchivo())) {
			datosReproceso.setIndiceArchivo(idInputActivo);
			pistaAuditoria.setIdInsumo(idInputActivo);
		} else if ("2".equals(datosReproceso.getIndiceArchivo())) {
			datosReproceso.setIndiceArchivo(idInputPasivo);
			pistaAuditoria.setIdInsumo(idInputPasivo);
		} else {
			this.info("El parametro de archivo recbido como parametro no es valido: "
					+ datosReproceso.getIndiceArchivo());
			throw new BusinessException(
					ConstantesRorac.ERROR_PARAMETROS_ENTRADA_REPROCESO);
		}
		datosReproceso.setCodigoEstatus(estatusOperacion);
		datosReproceso.setIdProceso(idProceso);
		this.info("Se realiza la peticion a la capa de acceso a datos para registrar la operacion de lanzamiento de reproceso.");
		final BeanResultBO estatus = daoLanzador.registraOperacionAprov(
				datosReproceso, sessionBean);

		if (estatus == null) {
			this.info("Se recibio una respuesta nula al intentar registrar la operacion de reproceso.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_IO_FINALES);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus.getCodError())) {
			this.info("Se recibio un codigo de error al intentar registrar la operacion de lanzador de reproceso.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(estatus.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Fue registrada la operacion de lanzador de reprocesos en base de datos.");
		this.info("Termina la ejecucion del metodo: " + metodo);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.servicio.lanzadores.BOLanzadores#lanzaAprovisionamiento
	 * (mx.isban.rorac.bean.lanzadores.BeanLanzamientoOperacion,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void lanzaAprovisionamiento(
			final BeanLanzamientoOperacion datosAprov,
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "lanzaAprovisionamiento()";
		this.info("Se ejecuta el metodo " + metodo);
		final String idProceso = this
				.getConfigDeCmpAplicacion(PARAM_LANZADOR_APROVISIONAMIENTO_ID_OPERACION);
		final String estatusOperacion = this
				.getConfigDeCmpAplicacion(PARAM_CODIGO_INICIAR_OPERACION);
		final String idOutputActivo = this
				.getConfigDeCmpAplicacion(PARAM_INDICE_OUTPUT_ACTIVO_APROV);
		final String idOutputPasivo = this
				.getConfigDeCmpAplicacion(PARAM_INDICE_OUTPUT_PASIVO_APROV);
		if (idProceso == null || estatusOperacion == null
				|| idOutputActivo == null || idOutputPasivo == null) {
			this.info("Los parametros de configuracion para el lanzamiento de aprovisionamiento historico no fueron dados de alta.");
			throw new BusinessException(ConstantesRorac.ERROR_CONFIGURACION_AH);
		}

		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setCodigoOperacion(idProceso);

		if ("1".equals(datosAprov.getIndiceArchivo())) {
			datosAprov.setIndiceArchivo(idOutputActivo);
			pistaAuditoria.setIdInsumo(idOutputActivo);
		} else if ("2".equals(datosAprov.getIndiceArchivo())) {
			datosAprov.setIndiceArchivo(idOutputPasivo);
			pistaAuditoria.setIdInsumo(idOutputPasivo);
		} else {
			this.info("El parametro de entrada que indica el tipo de archivo no es correcto: "
					+ datosAprov.getIndiceArchivo());
			throw new BusinessException(
					ConstantesRorac.ERROR_PARAMETROS_ENTRADA_AH);
		}
		datosAprov.setCodigoEstatus(estatusOperacion);
		datosAprov.setIdProceso(idProceso);
		this.info("Se realiza la peticion a la capa de acceso a datos para registrar la operacion Aprovisionamiento Historico.");
		final BeanResultBO estatus = daoLanzador.registraOperacionAprov(
				datosAprov, sessionBean);
		if (estatus == null) {
			this.info("Se recibio una respuesta nula al intentar registrar la operacion de aprovisionamiento historico.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_IO_FINALES);
		}
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus.getCodError())) {
			this.info("Se recibio un codigo de error al intentar registrar la operacion de aprovisionamiento historico");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(estatus.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("La operacion de aprovisionamiento historico fua registrada en base de datos.");
		this.info("Termina la ejecucion del metodo " + metodo);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see mx.isban.rorac.servicio.lanzadores.BOLanzadores#validaPeticion()
	 */
	@Override
	public boolean validaPeticion(final ArchitechSessionBean sessionBean)
			throws BusinessException {
		final String metodo = "validaPeticion()";
		this.info("Se ejecuta el metodo " + metodo);
		boolean validaPeticion;
		final BeanResultBO beanResult = daoLanzador
				.obtenerNumeroPeticionesAbiertas(this
						.getConfigDeCmpAplicacion(PARAM_CARGA_IO_ID_OPERACION),
						UtileriasNegocio.obtenerAnio(), UtileriasNegocio
								.obtenerMesProceso());
		if (beanResult == null) {
			this.info("Se obtuvo una respuesta nula de la capa de acceso a datos.");
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_IO_FINALES);
		} else if (ConstantesRorac.OPERACION_EXITOSA.equals(beanResult
				.getCodError())) {
			validaPeticion = true;
		} else if (ConstantesRorac.EJECUCION_EN_PROCESO.equals(beanResult
				.getCodError())) {
			validaPeticion = false;
		} else {
			this.info("Se obtuvo un codigo de error de la capa de acceso a datos: "
					+ beanResult.getCodError());
			throw new BusinessException(beanResult.getCodError());
		}
		this.info("Termina la ejecucion del metodo " + metodo);
		return validaPeticion;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.servicio.lanzadores.BOLanzadores#creaArchivoCarga(mx.isban
	 * .rorac.bean.lanzadores.BeanLanzamientoMotorRorac)this.getArchitechBean()
	 */
	@Override
	public void executeValidacionMotor(final ArchitechSessionBean sessionBean,
			final BeanLanzamientoMotorRorac beanForm) throws BusinessException,
			IOException {
		final String metodo = "executeValidacionMotor()";
		this.info("Se ejecuta el metodo " + metodo);
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		final BeanResultBO estatus = daoLanzador.updateRegistroOperacionMotor(
				this.getConfigDeCmpAplicacion(PARAM_MOTOR_RORAC_OPERACION),
				ESTATUS_INICIO_VALIDACION, beanForm, sessionBean);
		if (estatus == null) {
			this.info("No se obtuvo un codigo de estatus de la capa de acceso a datos.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_IO_FINALES);
		} else if (!ConstantesRorac.OPERACION_EXITOSA.equals(estatus
				.getCodError())) {
			this.info("Se obtuvo un codigo de error al intentar registrar el lanzamiento del motor RORAC: "
					+ estatus.getCodError());
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(estatus.getCodError());
		}
		this.info("Se obtuvo un codigo de operacion exitosa al registrar el lanzamiento de la ejecucion del motor RORAC.");
		this.info("Se procedera a crear el archivo carga.txt para la ejecucion del motor");
		createFileMotor(beanForm);
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Finaliza la ejecucion del metodo " + metodo);
	}

	/**
	 * @throws IOException
	 *
	 *             Metodo encargado de generar el archivo para ejecutar el motor
	 *             RORAC
	 *
	 * @param beanForm
	 *            contenido del archivo
	 */
	private boolean createFileMotor(final BeanLanzamientoMotorRorac beanForm)
			throws IOException {
		String pathCargaTxt = this.getConfigDeCmpAplicacion(PATH_CARGA_TXT);
		if (!pathCargaTxt.endsWith("/")) {
			pathCargaTxt += "/";
		}
		String anio = beanForm.getAnio();
		String mes = beanForm.getMes();
		PrintWriter pw = null;
		FileWriter fwCargaTxt = null;
		try {
			fwCargaTxt = new FileWriter(pathCargaTxt + NAME_CARGA_TXT);
			pw = new PrintWriter(fwCargaTxt);
			pw.println("%%FINALIDAD=" + beanForm.getFinalidad());
			pw.println("%%FECHA=" + anio + mes
					+ UtileriasNegocio.obtenerUltimoDiaDelMes(anio, mes));
			pw.println("%%DIVISA=" + beanForm.getDivisa());
			pw.println("%%N=" + beanForm.getValorN());
		} catch (IOException e) {
			this.error("Ocurrio un error al crear el fichero carga.txt");
			throw e;
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
		return new File(pathCargaTxt + NAME_CARGA_TXT).exists();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.servicio.lanzadores.BOLanzadores#hayEjecucionEnProgreso
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public boolean hayEjecucionEnProgreso(final ArchitechSessionBean sessionBean)
			throws BusinessException {
		final String metodo = "hayEjecucionEnProgreso()";
		this.info("Se ejecuta el metodo " + metodo);
		boolean validaPeticion;
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		final BeanResultBO beanResult = daoLanzador.validaEjecucion(this
				.getConfigDeCmpAplicacion(PARAM_MOTOR_RORAC_OPERACION));
		if (beanResult == null) {
			this.info("Se obtuvo una respuesta nula de la capa de acceso a datos.");
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(
					ConstantesRorac.ERROR_NO_RESPUESTA_IO_FINALES);
		} else if (ConstantesRorac.OPERACION_EXITOSA.equals(beanResult
				.getCodError())) {
			validaPeticion = false;
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
		} else if (ConstantesRorac.EJECUCION_EN_PROCESO.equals(beanResult
				.getCodError())) {
			validaPeticion = true;
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
		} else {
			this.info("Se obtuvo un codigo de error de la capa de acceso a datos: "
					+ beanResult.getCodError());
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(beanResult.getCodError());
		}
		this.info("Termina la ejecucion del metodo " + metodo);
		return validaPeticion;
	}
}
