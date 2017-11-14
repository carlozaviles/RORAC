/**************************************************************
* Queretaro, Qro Mayo 2015
*
* La redistribucion y el uso en formas fuente y binario, 
* son responsabilidad del propietario.
* 
* Este software fue elaborado en @Everis
* 
* Para mas informacion, consulte <www.everis.com/mexico>
***************************************************************/
package mx.isban.rorac.dao.administracion.perfiles;

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
import mx.isban.rorac.bean.administracion.grupo.BeanGrupoRespuesta;
import mx.isban.rorac.bean.administracion.grupo.BeanGrupo;
import mx.isban.rorac.bean.administracion.pantalla.BeanPantalla;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Clase DAOGrupoImpl
 * 
 * Clase encargada de implementar la interface DAOGrupo.
 * Esta clase se encarga de todas las operaciones relacionadas a la tabla Grupo
 * 
 * @author Everis
 * @version 1.0
 * @see www.everis.com/mexico
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOGrupoImpl extends Architech implements DAOGrupo {
       
	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = -5740046547641502070L;
	/**
	 * Constante con el valor ID_CANAL_DATABASE_JDBC
	 */
	private static final String ID_CANAL = "ID_CANAL_DATABASE_JDBC";
	/**
	 * Consulta SQL para actualizar un grupo
	 */
	private static final String QUERY_ACTUALIZA_GRUPO = 
			"UPDATE RRC_ADMIN_GRUPO"
			+ " SET NOMBRE = ?,DESCRIPCION = ?"
			+ " WHERE ID_GRUPO = ?";
	/**
	 * Consulta SQL que elimina las relaciones GRUPO - PANTALLA
	 */
	private static final String QUERY_ELIMINA_RELACIONES_GRUPO_PANTALLA = 
			"DELETE FROM RRC_ADMIN_REL_GRUPO_PANTALLA WHERE FK_ID_GRUPO = ?";
	
	/**
	 * Consulta SQL que crea una relacion GRUPO - PANTALLA
	 */
	private static final String QUERY_CREA_RELACION_GRUPO_PANTALLA =
			"INSERT INTO RRC_ADMIN_REL_GRUPO_PANTALLA(ID_RELACION,FK_ID_GRUPO,FK_ID_PANTALLA) VALUES (fn_sig_nummero_seq('RRC_ADMIN_REL_GRUPO_PANTALLA'),?,?)";
	/**
	 * Consulta SQL que permite dar de alta un nuevo Grupo
	 */
	private static final String QUERY_ALTA_GRUPO = 
			"INSERT INTO RRC_ADMIN_GRUPO(ID_GRUPO,NOMBRE,DESCRIPCION) VALUES (fn_sig_nummero_seq('RRC_ADMIN_GRUPO'),?,?)";
	/**
	 * Consulta SQL que elimina un Grupo
	 */
	private static final String QUERY_ELIMINA_GRUPO = 
			"DELETE FROM RRC_ADMIN_GRUPO WHERE ID_GRUPO = ?";
	/**
	 * Consulta SQL que permite obtener los grupos relacionados a un usuario
	 */
	private static final String QUERY_OBTENER_GRUPO_POR_USUARIO = 
			"SELECT G.NOMBRE,G.ID_GRUPO,G.DESCRIPCION "
			+ " FROM RRC_ADMIN_GRUPO G"
			+ " JOIN RRC_ADMIN_REL_USUARIO_GRUPO REL"
			+ " ON G.ID_GRUPO = REL.FK_ID_GRUPO"
			+ " JOIN RRC_ADMIN_USUARIO U"
			+ " ON REL.FK_ID_USUARIO = U.ID_USUARIO"
			+ " WHERE U.ID_USUARIO = ?";
	/**
     *Constante con un mensaje indicando que se obtuvo un codigo de error al ejecutar
     *una consulta 
     */
	private static final String MENSAJE_ERROR = 
			"Se obtuvo un codigo de error al ejecutar una consulta :";

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.dao.administracion.perfiles.DAOGrupo#buscarTodosGrupos(mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanGrupoRespuesta buscarTodosGrupos(
			ArchitechSessionBean sessionBean) {
		this.info("Se inicia la consulta de todos los perfiles...");
		final String consulta = "SELECT ID_GRUPO,NOMBRE,DESCRIPCION FROM RRC_ADMIN_GRUPO";
		final BeanGrupoRespuesta grupos = new BeanGrupoRespuesta();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setQuery(consulta);
		requestDTO.setCodeOperation("COD01 Consultar-Todos-Grupos");
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError());
				grupos.setCodError(responseDTO.getCodeError());
				grupos.setMsgError(responseDTO.getMessageError());
			}else{
				final List<BeanGrupo> listaGrupos = obtenerListadoGrupo(responseDTO);
				grupos.setGrupos(listaGrupos);
				grupos.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			grupos.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			grupos.setMsgError(e.getMessage());
		}
		return grupos;
	}

	/**
	 * Metodo que itera en el resultado de la consulta y obtiene un listado 
	 * de objetos tipo BeanGrupo
	 * @param responseDTO La respuesta de la consulta en la base de datos
	 * @return Listado de objetos de tipo BeanGrupo
	 */
	private List<BeanGrupo> obtenerListadoGrupo(
			ResponseMessageDataBaseDTO responseDTO) {
		final List<BeanGrupo> listaGrupos = new ArrayList<BeanGrupo>();
		for(Map<String, Object> registro : responseDTO.getResultQuery()){
			final BeanGrupo grupo = new BeanGrupo();
			grupo.setIdGrupo(String.valueOf(registro.get("ID_GRUPO")));
			grupo.setNombreGrupo(String.valueOf(registro.get("NOMBRE")));
			grupo.setDescripcionGrupo(String.valueOf(registro.get("DESCRIPCION")));
			listaGrupos.add(grupo);
		}
		return listaGrupos;
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.dao.administracion.perfiles.DAOGrupo#consultarGrupoPorId(mx.isban.agave.commons.beans.ArchitechSessionBean, java.lang.String)
	 */
	@Override
	public BeanGrupoRespuesta consultarGrupoPorId(ArchitechSessionBean sessionBean,
			String idGrupo) {
		this.info("Se inicia la consulta del perfil con id:"+idGrupo);
		final String consulta = "SELECT ID_GRUPO,NOMBRE,DESCRIPCION FROM RRC_ADMIN_GRUPO WHERE ID_GRUPO = ?";
		final BeanGrupoRespuesta grupos = new BeanGrupoRespuesta();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(consulta);
		requestDTO.setCodeOperation("COD02 Consulta-Grupo Por id");
		requestDTO.addParamToSql(idGrupo);
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError());
				grupos.setCodError(responseDTO.getCodeError());
				grupos.setMsgError(responseDTO.getMessageError());
			}else{
				List<BeanGrupo> listaGrupos = obtenerListadoGrupo(responseDTO);
				grupos.setGrupos(listaGrupos);
				grupos.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			grupos.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			grupos.setMsgError(e.getMessage());
		}
		return grupos;
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.dao.administracion.perfiles.DAOGrupo#consultarGrupoPorNombre(mx.isban.agave.commons.beans.ArchitechSessionBean, java.lang.String)
	 */
	@Override
	public BeanGrupoRespuesta consultarGrupoPorNombre(
			ArchitechSessionBean sessionBean, String nombreGrupo) {
		this.info("Se inicia la consulta del perfil con el nombre:"+nombreGrupo);
		final String consulta = "SELECT ID_GRUPO,NOMBRE,DESCRIPCION FROM RRC_ADMIN_GRUPO WHERE NOMBRE = ?";
		final BeanGrupoRespuesta grupos = new BeanGrupoRespuesta();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(consulta);
		requestDTO.setCodeOperation("COD03 Consulta-Grupo por nombre");
		requestDTO.addParamToSql(nombreGrupo);
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError());
				grupos.setCodError(responseDTO.getCodeError());
				grupos.setMsgError(responseDTO.getMessageError());
			}else{
				List<BeanGrupo> listaGrupos = obtenerListadoGrupo(responseDTO);
				grupos.setGrupos(listaGrupos);
				grupos.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			grupos.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			grupos.setMsgError(e.getMessage());
		}
		return grupos;
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.dao.administracion.perfiles.DAOGrupo#modificarGrupo(mx.isban.agave.commons.beans.ArchitechSessionBean, mx.isban.cifrascontrol.beans.administracion.grupo.BeanGrupo)
	 */
	@Override
	public BeanGrupoRespuesta modificarGrupo(ArchitechSessionBean sessionBean,
			BeanGrupo grupo) {
		this.info("Se inicia la actualizacion del grupo");
		final BeanGrupoRespuesta grupos = new BeanGrupoRespuesta();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_UPDATE_PARAMS);
		requestDTO.setQuery(QUERY_ACTUALIZA_GRUPO);
		requestDTO.setCodeOperation("COD04 Actualiza-Grupo");
		requestDTO.addParamToSql(grupo.getNombreGrupo());
		requestDTO.addParamToSql(grupo.getDescripcionGrupo());
		requestDTO.addParamToSql(grupo.getIdGrupo());
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			//El componente IsbanDataAccess no contiene el metodo beginTransaction
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError());
				grupos.setCodError(responseDTO.getCodeError());
				grupos.setMsgError(responseDTO.getMessageError());
			}else{
				final BeanGrupoRespuesta eliminacionRelaciones = eliminarRelacionesGrupoPantalla(grupo.getIdGrupo());
				if(!ConstantesRorac.OPERACION_EXITOSA.equals(eliminacionRelaciones.getCodError())){
					this.error(MENSAJE_ERROR+eliminacionRelaciones.getCodError());
					grupos.setCodError(eliminacionRelaciones.getCodError());
					grupos.setMsgError(eliminacionRelaciones.getMsgError());
				}else{
					final List<BeanPantalla> pantallas = grupo.getPantallas();
					this.info("Tamaño de lista de pantallas seleccionadas:"+pantallas.size());
					for (int i = 0;i < pantallas.size();i++) {	
						final BeanGrupoRespuesta actualizaRelaciones = actualizaRelacionesGrupoPantalla(grupo.getIdGrupo(),pantallas.get(i).getIdPantalla());
						if(!ConstantesRorac.OPERACION_EXITOSA.equals(actualizaRelaciones.getCodError())){
							this.error(MENSAJE_ERROR+responseDTO.getCodeError());
							grupos.setCodError(actualizaRelaciones.getCodError());
							grupos.setMsgError(actualizaRelaciones.getMsgError());
							i = pantallas.size()+1;
						}else{
							grupos.setCodError(ConstantesRorac.OPERACION_EXITOSA);
						}
					}
				}
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			grupos.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			grupos.setMsgError(e.getMessage());
		}
		return grupos;
	}
	
	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.dao.administracion.perfiles.DAOGrupo#altaGrupo(mx.isban.agave.commons.beans.ArchitechSessionBean, mx.isban.cifrascontrol.beans.administracion.grupo.BeanGrupo)
	 */
	@Override
	public BeanGrupoRespuesta altaGrupo(ArchitechSessionBean sessionBean,
			BeanGrupo grupo) {
		this.info("Se inicia el alta de un nuevo grupo");
		final BeanGrupoRespuesta grupos = new BeanGrupoRespuesta();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT_PARAMS);
		requestDTO.setQuery(QUERY_ALTA_GRUPO);
		requestDTO.setCodeOperation("COD04 Alta-Grupo");
		requestDTO.addParamToSql(grupo.getNombreGrupo());
		requestDTO.addParamToSql(grupo.getDescripcionGrupo());
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError());
				grupos.setCodError(responseDTO.getCodeError());
				grupos.setMsgError(responseDTO.getMessageError());
			}else{
				final BeanGrupoRespuesta consultaGrupo = consultarGrupoPorNombre(sessionBean, grupo.getNombreGrupo());
				if(!ConstantesRorac.OPERACION_EXITOSA.equals(consultaGrupo.getCodError())){
					this.error(MENSAJE_ERROR+consultaGrupo.getCodError());
					grupos.setCodError(consultaGrupo.getCodError());
					grupos.setMsgError(consultaGrupo.getMsgError());
				}else{
					if(consultaGrupo.getGrupos().size()>=1){
						final String idPerfilNuevo =consultaGrupo.getGrupos().get(0).getIdGrupo(); 
						grupo.setIdGrupo(idPerfilNuevo);
						final List<BeanPantalla> pantallas = grupo.getPantallas();
						this.info("Tamanio de lista de pantallas seleccionadas:"+pantallas.size());
						for (int i = 0;i < pantallas.size();i++) {	
							final BeanGrupoRespuesta actualizaRelaciones = actualizaRelacionesGrupoPantalla(grupo.getIdGrupo(),pantallas.get(i).getIdPantalla());
							if(!ConstantesRorac.OPERACION_EXITOSA.equals(actualizaRelaciones.getCodError())){
								this.error(MENSAJE_ERROR+actualizaRelaciones.getCodError());
								grupos.setCodError(actualizaRelaciones.getCodError());
								grupos.setMsgError(actualizaRelaciones.getMsgError());
								i = pantallas.size()+1;
							}else{
								grupos.setCodError(ConstantesRorac.OPERACION_EXITOSA);
							}
						}
					}
				}
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			grupos.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			grupos.setMsgError(e.getMessage());
		}
		return grupos;
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.dao.administracion.perfiles.DAOGrupo#borrarGrupo(mx.isban.agave.commons.beans.ArchitechSessionBean, java.lang.String)
	 */
	@Override
	public BeanGrupoRespuesta borrarGrupo(ArchitechSessionBean sessionBean,
			String idGrupo) {
			this.info("Se inicia la eliminacion del grupo");
			final BeanGrupoRespuesta eliminacionRelaciones = eliminarRelacionesGrupoPantalla(idGrupo);
			if(!ConstantesRorac.OPERACION_EXITOSA.equals(eliminacionRelaciones.getCodError())){
				this.error(MENSAJE_ERROR+eliminacionRelaciones.getCodError());
			}else{
				final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
				requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_DELETE_PARAMS);
				requestDTO.setQuery(QUERY_ELIMINA_GRUPO);
				requestDTO.setCodeOperation("COD05 Eliminar-Grupo");
				requestDTO.addParamToSql(idGrupo);
				try{
					final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
					final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
					if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
						this.error(MENSAJE_ERROR+responseDTO.getCodeError());
						eliminacionRelaciones.setCodError(responseDTO.getCodeError());
						eliminacionRelaciones.setMsgError(responseDTO.getMessageError());
					}else{
						eliminacionRelaciones.setCodError(ConstantesRorac.OPERACION_EXITOSA);
					}
				}catch(ExceptionDataAccess e){
					this.error(ConstantesRorac.MENSAJE_ERROR_IDA + e);
					eliminacionRelaciones.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
					eliminacionRelaciones.setMsgError(e.getMessage());
				}
			}
		return eliminacionRelaciones;
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.dao.administracion.perfiles.DAOGrupo#obtenerGrupoPorUsuario(mx.isban.agave.commons.beans.ArchitechSessionBean, java.lang.String)
	 */
	@Override
	public BeanGrupoRespuesta obtenerGrupoPorUsuario(
			ArchitechSessionBean sessionBean, String idUsuario) {
		final BeanGrupoRespuesta grupos = new BeanGrupoRespuesta();
		this.info("Se inicia la consulta del perfil con el id de usaurio:"+idUsuario);
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(QUERY_OBTENER_GRUPO_POR_USUARIO);
		requestDTO.setCodeOperation("COD06 Consulta-Grupo por id usuario");
		requestDTO.addParamToSql(idUsuario);
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError());
				grupos.setCodError(responseDTO.getCodeError());
				grupos.setMsgError(responseDTO.getMessageError());
			}else{
				final List<BeanGrupo> listaGrupos = obtenerListadoGrupo(responseDTO);
				grupos.setGrupos(listaGrupos);
				grupos.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			grupos.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			grupos.setMsgError(e.getMessage());
		}
		return grupos;
	}

	/**
	 * Metodo encargado de actualizar las relaciones de un Grupo - Pantalla
	 * @param idGrupo Id del grupo a actualizar las relaciones
	 * @param idPantalla Id de la pantalla a actualizar las relaciones
	 * @return Un objeto de tipo BeanGrupoRespuesta
	 */
	private BeanGrupoRespuesta actualizaRelacionesGrupoPantalla(String idGrupo, String idPantalla) {
		final BeanGrupoRespuesta grupos = new BeanGrupoRespuesta();
		this.info("Se inicia la creacion de las relaciones Pantalla - Grupo");
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT_PARAMS);
		requestDTO.setQuery(QUERY_CREA_RELACION_GRUPO_PANTALLA);
		requestDTO.setCodeOperation("COD06 Crea Relaciones Grupo- Pantalla");
		requestDTO.addParamToSql(idGrupo);
		requestDTO.addParamToSql(idPantalla);
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError());
				grupos.setCodError(responseDTO.getCodeError());
				grupos.setMsgError(responseDTO.getMessageError());
			}else{
				grupos.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			grupos.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			grupos.setMsgError(e.getMessage());
		}
		return grupos;
	}

	/** Metodo que elimina las relaciones de un Grupo - Pantalla
	 * @param idGrupo El id del grupo al cual se le eliminaran las relaciones
	 * @return Un objeto de tipo BeanGrupoRespuesta
	 */
	private BeanGrupoRespuesta eliminarRelacionesGrupoPantalla(String idGrupo){
		final BeanGrupoRespuesta grupos = new BeanGrupoRespuesta();
		this.info("Se inicia la eliminacion de las relaciones Pantalla - Grupo");
		this.info(QUERY_ELIMINA_RELACIONES_GRUPO_PANTALLA);
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_DELETE_PARAMS);
		requestDTO.setQuery(QUERY_ELIMINA_RELACIONES_GRUPO_PANTALLA);
		requestDTO.setCodeOperation("COD04 Elimina Relaciones Grupo- Pantalla");
		requestDTO.addParamToSql(idGrupo);
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError());
				grupos.setCodError(responseDTO.getCodeError());
				grupos.setMsgError(responseDTO.getMessageError());
			}else{
				grupos.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			grupos.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			grupos.setMsgError(e.getMessage());
		}
		return grupos;
	}

}
