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
import mx.isban.rorac.bean.consultas.BeanListaADNRetailDAO;
import mx.isban.rorac.bean.consultas.BeanListaAdnLocalDAO;
import mx.isban.rorac.bean.consultas.BeanListaProductoGestionDAO;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Session Bean implementation class DAOAltaParametrosImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOAltaParametrosImpl extends Architech implements
		DAOAltaParametros {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -7124449272427550203L;
	/**
	 * ID_CANAL
	 */
	private static final String ID_CANAL = "ID_CANAL_DATABASE_JDBC";
	/**
	 * Insert para los registros ADN Local.
	 */
	private static final String INSERT_ADN_LOCAL = "INSERT INTO RRC_AUX_FIL_ADNLOCAL (IDAREADENEGOCIOLOCAL, DESCRIPCION, BANCA, FLAGACTIVO, "
			+ "     FLAGPASIVO, FLAGFONDOS, FLAGCOMISIONES, FLAGAJUSTES, FLAGINTERNEGOCIOS, FLAGCONTINGENTES, FECULTMO) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
	/**
	 * Insert para los registros Producto Gestion.
	 */
	private static final String INSERT_PRODUCTO_GESTION = "INSERT INTO RRC_AUX_FIL_PROGEST (IDPRODUCTOGEST, DESCRIPCION, FLAGACTIVO, "
			+ "     FLAGPASIVO, FLAGFONDOS, FLAGCOMISIONES, FLAGCONTINGENTES, FLAGAJUSTES, FLAGINTERNEGOCIOS, FECULTMO)"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
	/**
	 * Insert para los registros ADN Retail.
	 */
	private static final String INSERT_ADN_RETAIL = "INSERT INTO RRC_AUX_FIL_ADNSRET (IDSEGMENTOLOCAL, DESCRIPCION, BANCA, FLAGRETAIL, FECULTMO) "
			+ "VALUES (?, ?, ?, ?, SYSDATE)";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.dao.consultas.DAOAltaParametros#ejecutaAltaADNLocal(mx
	 * .isban.rorac.bean.consultas.BeanADNLocal,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBO ejecutaAltaADNLocal(final BeanADNLocal adnLocal,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "ejecutaAltaADNLocal()";
		this.info("Inicia la ejecucion del metodo: " + metodo);
		final BeanResultBO estatus = new BeanListaAdnLocalDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT_PARAMS);
		requestDTO.setQuery(INSERT_ADN_LOCAL);
		requestDTO.addParamToSql(adnLocal.getIdAdnLocal());
		requestDTO.addParamToSql(adnLocal.getDescripcion());
		requestDTO.addParamToSql(adnLocal.getBanca());
		requestDTO.addParamToSql(adnLocal.getFlagActivo());
		requestDTO.addParamToSql(adnLocal.getFlagPasivo());
		requestDTO.addParamToSql(adnLocal.getFlagFondos());
		requestDTO.addParamToSql(adnLocal.getFlagComiciones());
		requestDTO.addParamToSql(adnLocal.getFlagContingentes());
		requestDTO.addParamToSql(adnLocal.getFlagAjustes());
		requestDTO.addParamToSql(adnLocal.getFlagInternegocios());
		try {
			this.info("Se realiza el insert del registro Adn Retail con idAdnLocal: "
					+ adnLocal.getIdAdnLocal());
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se obtuvo un codigo de error al intentar realzar el insert del registro ADNLocal: "
						+ responseDTO.getCodeError());
				estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			} else {
				this.info("Se acutualizaron el siguiente numero de registros en base de datos: "
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
	 * @see
	 * mx.isban.rorac.dao.consultas.DAOAltaParametros#ejecutaAltaProductoGestion
	 * (mx.isban.rorac.bean.consultas.BeanProductoGestion,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBO ejecutaAltaProductoGestion(final BeanProductoGestion productoGestion, final ArchitechSessionBean sessionBean) {
		final String metodo = "ejecutaAltaProductoGestion()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final BeanResultBO estatus = new BeanListaProductoGestionDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT_PARAMS);
		requestDTO.setQuery(INSERT_PRODUCTO_GESTION);
		requestDTO.addParamToSql(productoGestion.getIdProductoGestion());
		requestDTO.addParamToSql(productoGestion.getDescripcion());
		requestDTO.addParamToSql(productoGestion.getFlagActivo());
		requestDTO.addParamToSql(productoGestion.getFlagPasivo());
		requestDTO.addParamToSql(productoGestion.getFlagFondos());
		requestDTO.addParamToSql(productoGestion.getFlagComiciones());
		requestDTO.addParamToSql(productoGestion.getFlagContingentes());
		requestDTO.addParamToSql(productoGestion.getFlagAjustes());
		requestDTO.addParamToSql(productoGestion.getFlagInternegocios());
		try {
			this.info("Se realiza el insert del registro de tipo Producto Gestion con idProductoGestion: "
					+ productoGestion.getIdProductoGestion());
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se obtuvo un codigo de error al intentar realizar el insert del registro Producto Gestion: "
						+ responseDTO.getCodeError());
				estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			} else {
				this.info("Se actualizaron el siguiente numero de registros al realizar el insert del registro Producto Gestion: "
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
	 * @see
	 * mx.isban.rorac.dao.consultas.DAOAltaParametros#ejecutaAltaADNRetail(mx
	 * .isban.rorac.bean.consultas.BeanADNRetail,
	 * mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanResultBO ejecutaAltaADNRetail(final BeanADNRetail adnRetail,
			final ArchitechSessionBean sessionBean) {
		final String metodo = "ejecutaAltaADNRetail()";
		this.info("Inicia la ejecucion del metodo  " + metodo);
		final BeanResultBO estatus = new BeanListaADNRetailDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO
				.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT_PARAMS);
		requestDTO.setQuery(INSERT_ADN_RETAIL);
		requestDTO.addParamToSql(adnRetail.getIdSegmentoLocal());
		requestDTO.addParamToSql(adnRetail.getDescripcion());
		requestDTO.addParamToSql(adnRetail.getBanca());
		requestDTO.addParamToSql(adnRetail.getFlagRetail());
		try {
			this.info("Se realiza la insercion del registro ADN Retail con idSegmentoLocal: "
					+ adnRetail.getIdSegmentoLocal());
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se obtuvo un codigo de error al intentar realizar el insert del registro ADN Retail: "
						+ responseDTO.getCodeError());
				estatus.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			} else {
				this.info("Al realizar el insert del registro ADN Retail se actualizaron el siguiente numero de registros: "
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
}
