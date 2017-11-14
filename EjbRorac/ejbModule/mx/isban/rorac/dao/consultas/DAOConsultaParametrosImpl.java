package mx.isban.rorac.dao.consultas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.ExceptionDataAccess;
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
import mx.isban.rorac.bean.consultas.BeanListaProductoGestionDAO;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Session Bean implementation class DAOConsultaParametrosImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOConsultaParametrosImpl extends Architech implements
		DAOConsultaParametros {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -5275588114112042900L;
	/**
	 * ID_Canal
	 */
	private static final String ID_CANAL = "ID_CANAL_DATABASE_JDBC";
	/**
	 * Consulta ADN Local.
	 */
	private static final String CONSULTA_ADN_LOCAL = "SELECT IDAREADENEGOCIOLOCAL, DESCRIPCION, BANCA, FLAGACTIVO, FLAGPASIVO, FLAGFONDOS, "
			+ "   FLAGCOMISIONES, FLAGCONTINGENTES, FLAGAJUSTES, FLAGINTERNEGOCIOS "
			+ "FROM RRC_AUX_FIL_ADNLOCAL";
	/**
	 * Consulta Producto Gestion.
	 */
	private static final String CONSULTA_PRODUCTO_GESTION = "SELECT IDPRODUCTOGEST, DESCRIPCION, FLAGACTIVO, FLAGPASIVO, FLAGFONDOS, "
			+ "     FLAGCOMISIONES, FLAGCONTINGENTES, FLAGAJUSTES, FLAGINTERNEGOCIOS "
			+ "FROM RRC_AUX_FIL_PROGEST";
	/**
	 * Consulta ADN Retail y No Retail
	 */
	private static final String CONSULTA_ADN_RETAIL = "SELECT IDSEGMENTOLOCAL, DESCRIPCION, BANCA, FLAGRETAIL "
			+ "FROM RRC_AUX_FIL_ADNSRET";
	/**
	 * Consulta FlagNeteo
	 */
	private static final String CONSULTA_FLAGNETEO = "SELECT ID_FLAGNETEO, FLAGNETEO "
			+ "FROM RRC_AUX_FIL_FLAGNETEO";
	/**
	 * Identificador de campo retornado por consulta de parametros.
	 */
	private static final String DESCRIPCION = "DESCRIPCION";
	/**
	 * Identificador de campo retornado por consulta de parametros.
	 */
	private static final String BANCA = "BANCA";
	/**
	 * Identificador de campo retornado por consulta de parametros.
	 */
	private static final String FLAG_ACTIVO = "FLAGACTIVO";
	/**
	 * Identificador de campo retornado por consulta de parametros.
	 */
	private static final String FLAG_PASIVO = "FLAGPASIVO";
	/**
	 * Identificador de campo retornado por consulta de parametros.
	 */
	private static final String FLAG_FONDOS = "FLAGFONDOS";
	/**
	 * Identificador de campo retornado por consulta de parametros.
	 */
	private static final String FLAG_COMICIONES = "FLAGCOMISIONES";
	/**
	 * Identificador de campo retornado por consulta de parametros.
	 */
	private static final String FLAG_CONTINGENTES = "FLAGCONTINGENTES";
	/**
	 * Identificador de campo retornado por consulta de parametros.
	 */
	private static final String FLAG_AJUSTES = "FLAGAJUSTES";
	/**
	 * Identificador de campo retornado por consulta de parametros.
	 */
	private static final String FLAG_INTERNEGOCIOS = "FLAGINTERNEGOCIOS";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.dao.consultas.DAOConsultaParametros#ejecutaConsultaADNLocal
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanListaAdnLocalDAO ejecutaConsultaADNLocal(
			final ArchitechSessionBean sessionBean) {
		final String metodo = "ejecutaConsultaADNLocal()";
		this.info("Comienza la ejecucion del metodo " + metodo);
		final BeanListaAdnLocalDAO respuesta = new BeanListaAdnLocalDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setQuery(CONSULTA_ADN_LOCAL);
		try {
			this.info("Se ejecuta la consulta ADN Local.");
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se genero un error al ejecutar la consulta ADN Local: "
						+ responseDTO.getCodeError());
				respuesta
						.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			} else {
				final List<BeanADNLocal> listaAdnLocal = new ArrayList<BeanADNLocal>();
				this.info(responseDTO.getResultQuery().toString());
				for (Map<String, Object> registro : responseDTO
						.getResultQuery()) {
					final BeanADNLocal adnLocal = new BeanADNLocal();
					adnLocal.setIdAdnLocal((String) registro
							.get("IDAREADENEGOCIOLOCAL"));
					adnLocal.setDescripcion((String) registro.get(DESCRIPCION));
					adnLocal.setBanca((String) registro.get(BANCA));
					adnLocal.setFlagActivo((String) registro.get(FLAG_ACTIVO));
					adnLocal.setFlagPasivo((String) registro.get(FLAG_PASIVO));
					adnLocal.setFlagFondos((String) registro.get(FLAG_FONDOS));
					adnLocal.setFlagComiciones((String) registro
							.get(FLAG_COMICIONES));
					adnLocal.setFlagContingentes((String) registro
							.get(FLAG_CONTINGENTES));
					adnLocal.setFlagAjustes((String) registro.get(FLAG_AJUSTES));
					adnLocal.setFlagInternegocios((String) registro
							.get(FLAG_INTERNEGOCIOS));
					listaAdnLocal.add(adnLocal);
				}
				respuesta.setListaADNLocal(listaAdnLocal);
				respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
				this.info("Se envian los resultados de la consulta ADN Local hacia la capa de Negocio.");
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			respuesta.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			respuesta.setMsgError(e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);
		return respuesta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.isban.rorac.dao.consultas.DAOConsultaParametros#
	 * ejecutaConsultaProductoGestion
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanListaProductoGestionDAO ejecutaConsultaProductoGestion(
			final ArchitechSessionBean sessionBean) {
		final String metodo = "ejecutaConsultaProductoGestion()";
		this.info("Inicia la ejecucion del metodo " + metodo);
		final BeanListaProductoGestionDAO respuesta = new BeanListaProductoGestionDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setQuery(CONSULTA_PRODUCTO_GESTION);
		try {
			this.info("Se ejecuta la consulta de Producto Gestion.");
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se obtuvo un codigo de error al ejecutar la consulta Producto Gestion: "
						+ responseDTO.getCodeError());
				respuesta
						.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			} else {
				final List<BeanProductoGestion> listaProductoGestion = new ArrayList<BeanProductoGestion>();
				this.info(responseDTO.getResultQuery().toString());
				for (Map<String, Object> registro : responseDTO
						.getResultQuery()) {
					final BeanProductoGestion productoGestion = new BeanProductoGestion();
					productoGestion.setIdProductoGestion((String) registro
							.get("IDPRODUCTOGEST"));
					productoGestion.setDescripcion((String) registro
							.get(DESCRIPCION));
					productoGestion.setFlagActivo((String) registro
							.get(FLAG_ACTIVO));
					productoGestion.setFlagPasivo((String) registro
							.get(FLAG_PASIVO));
					productoGestion.setFlagFondos((String) registro
							.get(FLAG_FONDOS));
					productoGestion.setFlagComiciones((String) registro
							.get(FLAG_COMICIONES));
					productoGestion.setFlagContingentes((String) registro
							.get(FLAG_CONTINGENTES));
					productoGestion.setFlagAjustes((String) registro
							.get(FLAG_AJUSTES));
					productoGestion.setFlagInternegocios((String) registro
							.get(FLAG_INTERNEGOCIOS));
					listaProductoGestion.add(productoGestion);
				}
				respuesta.setListaProductoGestion(listaProductoGestion);
				respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
				this.info("Se envian los resultados de la consulta Producto Gestion a la capa de Negocio.");
			}
		} catch (ExceptionDataAccess e) {
			showException(e, Level.ERROR);
			respuesta.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			respuesta.setMsgError(e.getMessage());
		}
		this.info("Termina la ejecucion del metodo: " + metodo);
		return respuesta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.dao.consultas.DAOConsultaParametros#ejecutaConsultaADNRetail
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanListaADNRetailDAO ejecutaConsultaADNRetail(
			final ArchitechSessionBean sessionBean) {
		final String metodo = "ejecutaConsultaADNRetail()";
		this.info("Inicia la ejecucion del metodo: " + metodo);
		final BeanListaADNRetailDAO respuesta = new BeanListaADNRetailDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setQuery(CONSULTA_ADN_RETAIL);
		try {
			this.info("Se ejecuta la consulta ADN Retail.");
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se obtuvo un codigo de error al ejecutar la consulta ADN Retail y No Retail: "
						+ responseDTO.getCodeError());
				respuesta
						.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			} else {
				this.info(responseDTO.getResultQuery().toString());
				final List<BeanADNRetail> listaAdnRetail = new ArrayList<BeanADNRetail>();
				for (Map<String, Object> registro : responseDTO
						.getResultQuery()) {
					final BeanADNRetail adnRetail = new BeanADNRetail();
					adnRetail.setIdSegmentoLocal((String) registro
							.get("IDSEGMENTOLOCAL"));
					adnRetail
							.setDescripcion((String) registro.get(DESCRIPCION));
					adnRetail.setBanca((String) registro.get(BANCA));
					adnRetail
							.setFlagRetail((String) registro.get("FLAGRETAIL"));
					listaAdnRetail.add(adnRetail);
				}
				this.info("Se envian los resultados de la consulta ADN Retail y No Retail a la capa de negocio.");
				respuesta.setListaADNRetail(listaAdnRetail);
				respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			this.info("Se genero una excepcion al ejecutar la consulta ADN Retail y No Retail: "
					+ e);
			respuesta.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
		}
		this.info("Termina la ejecucion del metodo: " + metodo);
		return respuesta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.isban.rorac.dao.consultas.DAOConsultaParametros#ejecutaConsultaFlagNeteo
	 * (mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanListaFlagNeteoDAO ejecutaConsultaFlagNeteo(
			final ArchitechSessionBean sessionBean) {
		final String metodo = "ejecutaConsultaFlagNeteo()";
		this.info("Comienza la ejecucion del metodo: " + metodo);
		final BeanListaFlagNeteoDAO respuesta = new BeanListaFlagNeteoDAO();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setQuery(CONSULTA_FLAGNETEO);
		try {
			this.info("Se ejecuta la consulta de FlagNeteo");
			final DataAccess ida = DataAccess.getInstance(requestDTO,
					this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO) ida
					.execute(ID_CANAL);
			if (!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO
					.getCodeError())) {
				this.info("Se obtuvo un codigo de error al ejecutar la consulta FlagNeteo: "
						+ responseDTO.getCodeError());
				respuesta
						.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
			} else {
				this.info(responseDTO.getResultQuery().toString());
				List<BeanFlagNeteo> listaFlagNeteo = new ArrayList<BeanFlagNeteo>();
				for (Map<String, Object> registro : responseDTO
						.getResultQuery()) {
					final BeanFlagNeteo flagNeteo = new BeanFlagNeteo();
					flagNeteo.setIdRegistro(registro.get("ID_FLAGNETEO")
							.toString());
					flagNeteo.setValor((String) registro.get("FLAGNETEO"));
					listaFlagNeteo.add(flagNeteo);
				}
				this.info("Se envian los resultados de la consulta FlagNeteo hacia la capa de negocio.");
				respuesta.setListaFlagNeteo(listaFlagNeteo);
				respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		} catch (ExceptionDataAccess e) {
			this.info("Se genero una excepcion al ejecutar la consulta FlagNeteo: "
					+ e);
			respuesta.setCodError(ConstantesRorac.ERROR_IDA_TABLAS_PARAMETROS);
		}
		this.info("Termino la ejecucion del metodo " + metodo);
		return respuesta;
	}

}
