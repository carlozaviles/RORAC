package mx.isban.rorac.dao.consultas;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.ExceptionDataAccess;
import mx.isban.agave.commons.interfaces.BeanResultBO;
import mx.isban.agave.dataaccess.DataAccess;
import mx.isban.agave.dataaccess.channels.database.dto.RequestMessageDataBaseDTO;
import mx.isban.agave.dataaccess.channels.database.dto.ResponseMessageDataBaseDTO;
import mx.isban.agave.dataaccess.factories.jdbc.ConfigFactoryJDBC;
import mx.isban.agave.logging.Level;
import mx.isban.rorac.bean.consultas.BeanADNLocal;
import mx.isban.rorac.bean.consultas.BeanADNRetail;
import mx.isban.rorac.bean.consultas.BeanFlagNeteo;
import mx.isban.rorac.bean.consultas.BeanListaADNRetailDAO;
import mx.isban.rorac.bean.consultas.BeanListaAdnLocalDAO;
import mx.isban.rorac.bean.consultas.BeanListaFlagNeteoDAO;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Session Bean implementation class DAOModificacionParametrosImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOModificacionParametrosImpl extends Architech implements
		DAOModificacionParametros {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -7396954377685586750L;
	/**
	 * ID_CANAL
	 */
	private static final String ID_CANAL = "ID_CANAL_DATABASE_JDBC";
	/**
	 * Update para ADN Local
	 */
	private static final String UPDATE_ADN_LOCAL = "UPDATE RRC_AUX_FIL_ADNLOCAL "
			+ "SET DESCRIPCION = ?, "
			+ "    BANCA = ?, "
			+ "    FLAGACTIVO = ?, "
			+ "    FLAGPASIVO = ?, "
			+ "	FLAGFONDOS = ?, "
			+ "	FLAGCOMISIONES = ?, "
			+ "	FLAGCONTINGENTES = ?, "
			+ "    FLAGAJUSTES = ?,"
			+ "    FLAGINTERNEGOCIOS = ?,"
			+ "    FECULTMO = SYSDATE "
			+ "WHERE IDAREADENEGOCIOLOCAL = ?";
	/**
	 * Update para Producto Gestion
	 */
	private static final String UPDATE_PRODUCTO_GESTION = "UPDATE RRC_AUX_FIL_PROGEST "
			+ "SET DESCRIPCION = ?, "
			+ "    FLAGACTIVO = ?, "
			+ "    FLAGPASIVO = ?, "
			+ "    FLAGFONDOS = ?, "
			+ "    FLAGCOMISIONES = ?, "
			+ "    FLAGCONTINGENTES = ?, "
			+ "    FLAGAJUSTES = ?,"
			+ "    FLAGINTERNEGOCIOS = ?,"
			+ "    FECULTMO = SYSDATE " + "WHERE IDPRODUCTOGEST = ?";
	/**
	 * Update para ADN Retail
	 */
	private static final String UPDATE_ADN_RETAIL = "UPDATE RRC_AUX_FIL_ADNSRET "
			+ "SET DESCRIPCION = ?, "
			+ "    BANCA = ?, "
			+ "    FLAGRETAIL = ?, "
			+ "    FECULTMO = SYSDATE "
			+ "WHERE IDSEGMENTOLOCAL = ?";
	/**
	 * Update para FlagNeteo
	 */
	private static final String UPADATE_FLAG_NETEO = "UPDATE RRC_AUX_FIL_FLAGNETEO "
			+ "SET FLAGNETEO = ?, "
			+ "    FECULTMO = SYSDATE "
			+ "WHERE ID_FLAGNETEO = ?";

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.isban.rorac.dao.consultas.DAOModificacionParametros#
	 * ejecutaModificacionADNLocal(mx.isban.rorac.bean.consultas.BeanADNLocal,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBO ejecutaModificacionADNLocal(
			final BeanADNLocal adnLocal, final ArchitechSessionBean sessionBean) {
		final String metodo = "ejecutaModificacionADNLocal()";
		this.info("Inicial la ejecucion del metodo " + metodo);
		final BeanResultBO estatus = new BeanListaADNRetailDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_UPDATE_PARAMS);
		requestDTO.setQuery(UPDATE_ADN_LOCAL);
		requestDTO.addParamToSql(adnLocal.getDescripcion());
		requestDTO.addParamToSql(adnLocal.getBanca());
		requestDTO.addParamToSql(adnLocal.getFlagActivo());
		requestDTO.addParamToSql(adnLocal.getFlagPasivo());
		requestDTO.addParamToSql(adnLocal.getFlagFondos());
		requestDTO.addParamToSql(adnLocal.getFlagComiciones());
		requestDTO.addParamToSql(adnLocal.getFlagContingentes());
		requestDTO.addParamToSql(adnLocal.getFlagAjustes());
		requestDTO.addParamToSql(adnLocal.getFlagInternegocios());
		requestDTO.addParamToSql(adnLocal.getIdAdnLocal());
		try {
			this.info("Se realizara la actualizacion sobre el registro ADN Local con idAdnLocal: "
					+ adnLocal.getIdAdnLocal());
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se recibio un codigo de error al intentar realizar la actualizacion del registro ADN Local: "
						+ responseDTO.getCodeError());
				estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			} else {
				this.info("Al modificar el registro ADN Local se acualizaron el siguiente numero de registros: "
						+ responseDTO.getRecordsAffected());
				estatus.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			estatus.setMsgError(e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return estatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.isban.rorac.dao.consultas.DAOModificacionParametros#
	 * ejecutaModificacionProductoGestion
	 * (mx.isban.rorac.bean.consultas.BeanProductoGestion,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBO ejecutaModificacionProductoGestion(
			final BeanProductoGestion productoGestion,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "ejecutaModificacionProductoGestion()";
		this.info("Inicia la ejecucion del metodo: " + metodo);
		final BeanResultBO estatus = new BeanListaADNRetailDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_UPDATE_PARAMS);
		requestDTO.setQuery(UPDATE_PRODUCTO_GESTION);
		requestDTO.addParamToSql(productoGestion.getDescripcion());
		requestDTO.addParamToSql(productoGestion.getFlagActivo());
		requestDTO.addParamToSql(productoGestion.getFlagPasivo());
		requestDTO.addParamToSql(productoGestion.getFlagFondos());
		requestDTO.addParamToSql(productoGestion.getFlagComiciones());
		requestDTO.addParamToSql(productoGestion.getFlagContingentes());
		requestDTO.addParamToSql(productoGestion.getFlagAjustes());
		requestDTO.addParamToSql(productoGestion.getFlagInternegocios());
		requestDTO.addParamToSql(productoGestion.getIdProductoGestion());
		try {
			this.info("Se realizara la actualizacion del registro de tipo Producto Gestion con idProductoGestion: "
					+ productoGestion.getIdProductoGestion());
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se recibio un codigo de error al intentar actualizar el registro de Producto Gestion: "
						+ responseDTO.getCodeError());
				estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			} else {
				this.info("Al modificar el registro Producto Gestion se acualizaron el siguiente numero de registros: "
						+ responseDTO.getRecordsAffected());
				estatus.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			estatus.setMsgError(e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo: " + metodo);
		return estatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.isban.rorac.dao.consultas.DAOModificacionParametros#
	 * ejecutaModificacionADNRetail(mx.isban.rorac.bean.consultas.BeanADNRetail,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBO ejecutaModificacionADNRetail(
			final BeanADNRetail adnRetail,
			final ArchitechSessionBean sessionBean) {
		// TODO Auto-generated method stub
		final String metodo = "ejecutaModificacionADNRetail()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final BeanResultBO estatus = new BeanListaADNRetailDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_UPDATE_PARAMS);
		requestDTO.setQuery(UPDATE_ADN_RETAIL);
		requestDTO.addParamToSql(adnRetail.getDescripcion());
		requestDTO.addParamToSql(adnRetail.getBanca());
		requestDTO.addParamToSql(adnRetail.getFlagRetail());
		requestDTO.addParamToSql(adnRetail.getIdSegmentoLocal());
		try {
			this.info("Se realizara la actualizacion del registro de tipo ADN Retail con idSegmentoLocal: "
					+ adnRetail.getIdSegmentoLocal());
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se recibio un codigo de error al intentar actualizar el registro de ADN Retail: "
						+ responseDTO.getCodeError());
				estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			} else {
				this.info("Al modificar el registro ADN Retail se acualizaron el siguiente numero de registros: "
						+ responseDTO.getRecordsAffected());
				estatus.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			estatus.setMsgError(e.getMessage());
		}
		this.info("Termina la ejecucion del metodo " + metodo);
		return estatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.isban.rorac.dao.consultas.DAOModificacionParametros#
	 * ejecutaModificacionFlagNeteo(mx.isban.rorac.bean.consultas.BeanFlagNeteo,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBO ejecutaModificacionFlagNeteo(
			final BeanFlagNeteo flagNeteo,
			final ArchitechSessionBean sessionBean) {
		// TODO Auto-generated method stub
		final String metodo = "ejecutaModificacionFlagNeteo()";
		this.debug("Comienza la ejecucion del metodo: " + metodo);
		final BeanResultBO estatus = new BeanListaFlagNeteoDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_UPDATE_PARAMS);
		requestDTO.setQuery(UPADATE_FLAG_NETEO);
		requestDTO.addParamToSql(flagNeteo.getValor());
		requestDTO.addParamToSql(flagNeteo.getIdRegistro());
		try {
			this.info("Se realiza la actualizacion del registro FlagNeteo con idRegistro "
					+ flagNeteo.getIdRegistro());
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se recibio un codigo de error al intentar actualizar el registro de FlagNeteo: "
						+ responseDTO.getCodeError());
				estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			} else {
				this.info("Al modificar el registro FlagNeteo se acualizaron el siguiente numero de registros: "
						+ responseDTO.getRecordsAffected());
				estatus.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			estatus.setMsgError(e.getMessage());
		}
		this.info("Termina la ejecucion del metodo: " + metodo);
		return estatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.isban.rorac.dao.consultas.DAOModificacionParametros#
	 * ejecutaEliminacionRegistros(java.lang.String,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBO ejecutaEliminacionRegistros(final String query,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "ejecutaEliminacionRegistros()";
		this.info("Se ejecuta el metodo " + metodo);
		final BeanResultBO estatus = new BeanListaAdnLocalDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_UPDATE);
		requestDTO.setQuery(query);
		try {
			this.info("Se ejecuta la baja logica de los registros elegidos por el usuario.");
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se recibio un codigo de error al intentar realizar la baja logica de los registros: "
						+ responseDTO.getCodeError());
				estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			} else {
				this.info("Al ejecutar la baja logica se acualizaron el siguiente numero de registros: "
						+ responseDTO.getRecordsAffected());
				estatus.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			estatus.setMsgError(e.getMessage());
		}
		this.info("Concluye la ejecucion del metodo " + metodo);
		return estatus;
	}

}
