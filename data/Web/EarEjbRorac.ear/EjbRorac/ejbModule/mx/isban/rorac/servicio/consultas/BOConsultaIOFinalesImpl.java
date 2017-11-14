package mx.isban.rorac.servicio.consultas;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.BeanPistasAuditoria;
import mx.isban.rorac.bean.consultas.BeanConsultaIOFinalesDAO;
import mx.isban.rorac.dao.consultas.DAOConsultaIOFinales;
import mx.isban.rorac.servicio.util.BOPistasAuditoria;
import mx.isban.rorac.utilerias.general.ConstantesRorac;
import mx.isban.rorac.utilerias.general.UtileriasNegocio;

/**
 * Session Bean implementation class BOConsultaIOFinalesImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOConsultaIOFinalesImpl extends Architech implements
		BOConsultaIOFinales {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -6471376757212925753L;
	/**
	 * Consulta de Input Activo.
	 */
	private static final String CONSULTA_INPUT_ACTIVO = "SELECT FECEXTRDATOSMIS, FECDATA, IDCLIENTEMIS, IDCONTRATOMIS, CODDIVMIS, IDCENTRO, "
			+ "	IDENTIDADMIS, IDPRODUCTOOPERACIONAL, SUBPRODUCTOOPERAC, IDAREADENEGOCIOLOCAL, COD_CTA_CONT, GESTOR, SEGMENTOCLIENTEMIS, FEC_RENOV, "
			+ "	FECAPERTURA, FECVCTO, IDPRODUCTOGEST, IMP_SDO_CAP_ML, IMP_SDO_INT_ML, IMP_SDO_MED_CAP_ML, IMP_SDO_MED_INT_ML, INTERESES, TTI, "
			+ "	IMP_COM_ML, ROF, FECULTMO "
			+ "FROM RRC_DET_ACTIVO_MES "
			+ "WHERE IDCONTRATOMIS = ? AND FECDATA "
			+ "BETWEEN to_date(?,'YYYYMMDD') AND to_date(?,'YYYYMMDD')";
	/**
	 * Consulta de Input Pasivo.
	 */
	private static final String CONSULTA_INPUT_PASIVO = "SELECT FECEXTRDATOSMIS, FECDATA, IDCLIENTEMIS, IDCONTRATOMIS, CODDIVMIS, IDCENTRO, "
			+ "	IDENTIDADMIS, IDPRODUCTOOPERACIONAL, SUBPRODUCTOOPERAC, IDAREADENEGOCIOLOCAL, COD_CTA_CONT, GESTOR, SEGMENTOCLIENTEMIS, FECRENOV, "
			+ "	FECAPERTURA, FECVCTO, IDPRODUCTOGEST, IMP_SDO_CAP_ML, IMP_SDO_INT_ML, IMP_SDO_MED_CAP_ML, IMP_SDO_MED_INT_ML, INTERESES, TTI, "
			+ "	IMP_COM_ML, ROF, FECULTMO "
			+ "FROM RRC_DET_PASIVO_MES "
			+ "WHERE IDCONTRATOMIS = ? "
			+ "AND FECDATA BETWEEN to_date(?,'YYYYMMDD') AND to_date(?,'YYYYMMDD')";
	/**
	 * Consulta de Output Activo.
	 */
	private static final String CONSULTA_OUTPUT_ACTIVO = "SELECT IDCONTRATOMIS, IDAREADENEGOCIOLOCAL, IDDIVISION, IDPRODUCTOMIS, IDRU, IDCLIENTEMIS, "
			+ "	IDEMPRESABDR, IDCLIENTEBDR, IDSEGMENTOBDR1, MARGEN, MARGENYTD, MARGENHIST, SDBSMV, SFBSMV, EAD, K1EC, K2EC, PDEC, LGDEC, FLAGMORABDR, "
			+ "	TRATAMIENTO, FLAGMMFF, FLAGBDR, FLAGCURA, FECAPERTURA, FECVCTO, IDENTIDADMIS, IDCENTRO, IDSUBPRODUCTOOPERACIONAL, ZONA, TERRITORIAL, "
			+ "	GESTOR, IDCONTRATOBDR, IDSECTORCONT, IDSECTOR, IDSEGMENTOBDR2, IDPRODUCTOBDR1, IDPRODUCTOBDR2, MODELO_CLI, SITGESTBDR1, SITGESTBDR2, "
			+ "	CATEGORIABIS, SUBCATBIS, MODELO_CONT, CLI_TIPMODEL, CON_TIPMODEL, MOTEXCEC, IDGRUPO, GRUP_ECO, SDBSFM, SFBSFM, INTERESES, COMFIN, "
			+ "	COMNOFIN, CODVINCULA, ROF, TTI, MTM, NOCIONAL, FACTCLI, PUNTUACION, PROVESPECIFICA, PROVGENERICA, FACTGRUPO, CODDIVMIS, CODDIVBDR, "
			+ "	FLAGMORALOCAL, NUEVAPROD, FLAGCARTERIZADAS, NOMBRECLIEN, TIPPERS, NIFCIF, INTRAGRUPO, INTERNEGOCIO, IDAREADENEGOCIOCORP, IDPRODUCTOGEST, "
			+ "	SEGMENTOCLIENTEMIS, IDPRODUCTOOPERACIONAL, FICHEROMIS, FECRENOV, FECEXTRDATOSMIS, FECEXTRDATOSBDR, FECDATOSMESBDR, FECULTIMOEC, FECULTMO "
			+ "FROM RRC_MAE_ACTIVO_MES "
			+ "WHERE IDCONTRATOMIS = ? "
			+ "AND FECDATOSMESBDR BETWEEN to_date(?,'YYYYMMDD') AND to_date(?,'YYYYMMDD')";
	/**
	 * Consulta de Output Pasivo.
	 */
	private static final String CONSULTA_OUTPUT_PASIVO = "SELECT IDCONTRATO, IDAREADENEGOCIOLOCAL, IDSEGMENTOCOMLOCAL, IDPRODUCTO, MARGEN, "
			+ "	MARGENYTD, MARGENHIST, FECAPERTURA, FECVCTO, IDCLIENTE, IDENTIDAD, IDCENTRO, SUBPRODUCTO, ZONA, TERRITORIAL, GESTOR, SDBSMV, "
			+ "	SFBSMV, SDBSFM, SFBSFM, INTERESES, COMFIN, COMNOFIN, CODVINCULA, ROF, TTI, MTM, NOCIONAL, CODDIV, FLAGMORALOCAL, NUEVAPROD, "
			+ "	FLAGCARTERIZADAS, NOMBRECLIEN, TIPPERS, NIFCIF, INTRAGRUPO, INTERNEGOCIO, IDAREADENEGOCIOCORP, IDPRODUCTOGEST, SEGMENTOCLIENTE, "
			+ "	IDPRODUCTOOPERACIONAL, FICHERO, FECRENOV, FECEXTRDATOS, FECDATA, FECULTMO "
			+ "FROM RRC_MAE_PASIVO_MES "
			+ "WHERE IDCONTRATO = ? "
			+ "AND FECDATA BETWEEN to_date(?,'YYYYMMDD') AND to_date(?,'YYYYMMDD')";
	/**
	 * Parametro que contiene el id de Input final Activo.
	 */
	private static final String PARAM_INDICE_INPUT_ACTIVO = "PARAM_CONFIG_INDICE_INPUT_ACTIVO";
	/**
	 * Parametro que contiene el id de Input final Pasivo.
	 */
	private static final String PARAM_INDICE_INPUT_PASIVO = "PARAM_CONFIG_INDICE_INPUT_PASIVO";
	/**
	 * Parametro que contiene el id del Output final Activo
	 */
	private static final String PARAM_INDICE_OUTPUT_ACTIVO = "PARAM_CONFIG_INDICE_CONSULTA_OUTPUT_ACTIVO";
	/**
	 * Parametro que contiene el id del Ouput final Pasivo
	 */
	private static final String PARAM_INDICE_OUTPUT_PASIVO = "PARAM_CONFIG_INDICE_CONSULTA_OUTPUT_PASIVO";
	/**
	 * Parametro que contiene el id de operacion de Consulta de Inputs/Outputs
	 * finales.
	 */
	private static final String PARAM_CONSULTA_IO_FINALES_ID_OPERACION = "PARAM_CONFIG_CONSULTA_IO_FINALES_ID_OPERACION";
	/**
	 * Objeto de la capa de negocio encargado de realizar la consulta por
	 * contrato.
	 */
	@EJB
	private transient DAOConsultaIOFinales consultaIOFinales;
	/**
	 * Manejador de Pista de Auditoria
	 */
	@EJB
	private transient BOPistasAuditoria manejadorPistasAuditoria;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * mx.isban.rorac.servicio.consultas.BOConsultaIOFinales#llamaConsultaIOFinales
	 * (java.lang.String, java.lang.String,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public List<HashMap<String, Object>> llamaConsultaIOFinales(
			final String contrato, final String tipoInterfaz,
			final String anio, final String mes,
			final ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "llamaConsultaIOFinales()";
		this.info("Inicia la ejecucion del metodo: " + metodo);
		this.info("El tipo de interfaz recibido de la capa cliente es: "
				+ tipoInterfaz);
		this.info("El numero de contrato por el cual se realizara la consulta es: "
				+ contrato);
		if (tipoInterfaz == null || contrato == null) {
			this.info("Los parametros recibidos como parametros no son correctos.");
			throw new BusinessException(
					ConstantesRorac.ERROR_PARAMETROS_ENTRADA_CONSULTA_POR_CONTRATO);
		}
		String fechaInicial = anio + mes + "01";
		String fechaFinal = anio + mes
				+ UtileriasNegocio.obtenerUltimoDiaDelMes(anio, mes);
		String consulta = null;
		String idInsumo = null;
		if ("1".equals(tipoInterfaz)) {
			this.info("Se ejecuta la consulta de Input Final Activo por contrato.");
			consulta = CONSULTA_INPUT_ACTIVO;
			idInsumo = this.getConfigDeCmpAplicacion(PARAM_INDICE_INPUT_ACTIVO);
		} else if ("2".equals(tipoInterfaz)) {
			this.info("Se ejecuta la consulta de Input Final Pasivo por contrato.");
			consulta = CONSULTA_INPUT_PASIVO;
			idInsumo = this.getConfigDeCmpAplicacion(PARAM_INDICE_INPUT_PASIVO);
		} else if ("3".equals(tipoInterfaz)) {
			this.info("Se ejecuta la consulta de Output Final Activo por contrato.");
			consulta = CONSULTA_OUTPUT_ACTIVO;
			idInsumo = this
					.getConfigDeCmpAplicacion(PARAM_INDICE_OUTPUT_ACTIVO);
		} else if ("4".equals(tipoInterfaz)) {
			this.info("Se ejecuta la consulta de Output Final Pasivo por contrato.");
			consulta = CONSULTA_OUTPUT_PASIVO;
			idInsumo = this
					.getConfigDeCmpAplicacion(PARAM_INDICE_OUTPUT_PASIVO);
		} else {
			this.info("El parametro que indica el tipo de interfaz no es valido: "
					+ tipoInterfaz);
			throw new BusinessException(
					ConstantesRorac.ERROR_PARAMETROS_ENTRADA_CONSULTA_POR_CONTRATO);
		}
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria
				.setCodigoOperacion(this
						.getConfigDeCmpAplicacion(PARAM_CONSULTA_IO_FINALES_ID_OPERACION));
		pistaAuditoria.setIdInsumo(idInsumo);
		final BeanConsultaIOFinalesDAO resultadoConsulta = consultaIOFinales
				.ejecutaConsultaPorContrato(contrato, consulta, fechaInicial,
						fechaFinal, sessionBean);
		if (!ConstantesRorac.OPERACION_EXITOSA.equals(resultadoConsulta
				.getCodError())) {
			this.info("Se obtuvo un codigo de error al ejecutar la consulta de IO Finales: "
					+ resultadoConsulta.getCodError());
			pistaAuditoria
					.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
					sessionBean);
			throw new BusinessException(resultadoConsulta.getCodError());
		}
		pistaAuditoria
				.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
		manejadorPistasAuditoria.generaPistaAuditoria(pistaAuditoria,
				sessionBean);
		this.info("Termina la ejecucion del metodo " + metodo);
		return resultadoConsulta.getResultadosConsulta();
	}
}
