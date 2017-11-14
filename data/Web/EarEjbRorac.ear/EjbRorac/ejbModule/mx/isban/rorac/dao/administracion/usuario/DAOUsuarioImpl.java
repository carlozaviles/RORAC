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
package mx.isban.rorac.dao.administracion.usuario;

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
import mx.isban.rorac.bean.administracion.grupo.BeanGrupo;
import mx.isban.rorac.bean.administracion.usuario.BeanUsuario;
import mx.isban.rorac.bean.administracion.usuario.BeanUsuarioRespuesta;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
* Clase DAOUsuarioImpl
* 
* Clase encargada de implementar la interface DAOUsuario.
* Esta clase se encarga de todas las operaciones relacionadas a la tabla de usuarios
* 
* @author Everis
* @version 1.0
* @see www.everis.com/mexico
* 
*/
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DAOUsuarioImpl extends Architech implements DAOUsuario {
    
	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = -6678344268852753892L;
	/**
     *Constante con un mensaje indicando que se obtuvo un codigo de error al ejecutar
     *una consulta 
     */
	private static final String MENSAJE_ERROR = 
			"Se obtuvo un codigo de error al ejecutar una consulta :";
	/**
	 * Constante con el valor: ID_CANAL_DATABASE_JDBC
	 */
	private static final String ID_CANAL = "ID_CANAL_DATABASE_JDBC";
	/**
	 * Constante que contiene una consulta SQL que permite la actualizacion del usuario
	 */
	private static final String QUERY_ACTUALIZA_USUARIO = 
			"UPDATE RRC_ADMIN_USUARIO"
		+ " SET ESTATUS = ?"
		+ " WHERE ID_USUARIO = ?";
	
	/**
	 * Constante que contiene una consulta SQL que permite la eliminacion de las relaciones 
	 * Usuario - Grupo
	 */
	private static final String QUERY_ELIMINA_RELACIONES_USUARIO_GRUPO = 
			"DELETE FROM RRC_ADMIN_REL_USUARIO_GRUPO WHERE FK_ID_USUARIO = ?";
	/**
	 * Constante que contiene una consulta SQL que permite la creacion de las relaciones Usuario - Grupo
	 */
	private static final String QUERY_CREA_RELACIONES_USUARIO_GRUPO =
			"INSERT INTO RRC_ADMIN_REL_USUARIO_GRUPO(ID_RELACION,FK_ID_USUARIO,FK_ID_GRUPO) VALUES (fn_sig_nummero_seq('RRC_ADMIN_REL_USUARIO_GRUPO'),?,?)";
	/**
	 * Constante que contiene una consulta SQL que permite realizar el alta de un usuario
	 */
	private static final String QUERY_ALTA_USUARIO = 
			"INSERT INTO RRC_ADMIN_USUARIO(ID_USUARIO,ESTATUS) VALUES (?,?)";

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.dao.administracion.usuario.DAOUsuario#altaUsuario(mx.isban.agave.commons.beans.ArchitechSessionBean, mx.isban.cifrascontrol.beans.administracion.usuario.BeanUsuario)
	 */
	@Override
	public BeanUsuarioRespuesta altaUsuario(ArchitechSessionBean sessionBean,
			BeanUsuario usuario) {
		this.info("Se inicia el alta de un nuevo usuario");
		final BeanUsuarioRespuesta respuesta = new BeanUsuarioRespuesta();
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT_PARAMS);
		requestDTO.setQuery(QUERY_ALTA_USUARIO);
		requestDTO.setCodeOperation("COD21 Alta-Usuario");
		requestDTO.addParamToSql(usuario.getIdUsuario());
		Integer estatus = 0;
		if(usuario.isEstatus()){
			estatus = 1;
		}
		requestDTO.addParamToSql(estatus);
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError()+responseDTO.getMessageError());
				respuesta.setCodError(responseDTO.getCodeError());
				respuesta.setMsgError(responseDTO.getMessageError());
			}else{
				final List<BeanGrupo> grupos = usuario.getGrupos();
				if(!grupos.isEmpty()){
					for (int i = 0;i < grupos.size();i++) {	
						final BeanUsuarioRespuesta actualizaRelaciones = actualizaRelacionesUsuarioGrupo(usuario.getIdUsuario(), grupos.get(i).getIdGrupo());
						if(!ConstantesRorac.OPERACION_EXITOSA.equals(actualizaRelaciones.getCodError())){
							respuesta.setCodError(actualizaRelaciones.getCodError());
							respuesta.setMsgError(actualizaRelaciones.getMsgError());
							i = grupos.size()+1;
						}else{
							respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
						}
					}
				}
			
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			respuesta.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			respuesta.setMsgError(ConstantesRorac.MENSAJE_ERROR_IDA + e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo de alta de usuarios");
		return respuesta;
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.dao.administracion.usuario.DAOUsuario#obtenerTodosUsuarios(mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public BeanUsuarioRespuesta obtenerTodosUsuarios(
			ArchitechSessionBean sessionBean) {
		final String consulta = "SELECT ID_USUARIO,ESTATUS FROM RRC_ADMIN_USUARIO";
		final BeanUsuarioRespuesta usuariosRespuesta = new BeanUsuarioRespuesta();
		this.info("Se inicia la consulta de todos los usuarios...");
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY);
		requestDTO.setQuery(consulta);
		requestDTO.setCodeOperation("COD15 Consulta-Usuarios");
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError()+responseDTO.getMessageError());
				usuariosRespuesta.setCodError(responseDTO.getCodeError());
				usuariosRespuesta.setMsgError(responseDTO.getMessageError());
			}else{
				final List<BeanUsuario> listaUsuarios = new ArrayList<BeanUsuario>();
				for(Map<String, Object> registro : responseDTO.getResultQuery()){
					final BeanUsuario usuario = new BeanUsuario();
					usuario.setIdUsuario(String.valueOf(registro.get("ID_USUARIO")));
					final String status = String.valueOf(registro.get("ESTATUS"));
					if("1".equals(status)){
						usuario.setEstatus(true);
					}
					listaUsuarios.add(usuario);
				}
				usuariosRespuesta.setUsuarios(listaUsuarios);
				usuariosRespuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			usuariosRespuesta.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			usuariosRespuesta.setMsgError(ConstantesRorac.MENSAJE_ERROR_IDA + e.getMessage());
		}
		this.info("Finaliza la ejecucion del metodo de obtencion de todos los usuarios");
		return usuariosRespuesta;
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.dao.administracion.usuario.DAOUsuario#obtenerUsuarioPorID(mx.isban.agave.commons.beans.ArchitechSessionBean, java.lang.String)
	 */
	@Override
	public BeanUsuarioRespuesta obtenerUsuarioPorID(
			ArchitechSessionBean sessionBean, String idUsuario) {
		final String consulta = "SELECT ID_USUARIO,ESTATUS FROM RRC_ADMIN_USUARIO WHERE ID_USUARIO = ?";
		final BeanUsuarioRespuesta usuarios = new BeanUsuarioRespuesta();
		this.info("Se inicia la consulta del usuario con id:"+idUsuario);
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_QUERY_PARAMS);
		requestDTO.setQuery(consulta);
		requestDTO.setCodeOperation("COD17 Consulta-Usuario por id");
		requestDTO.addParamToSql(idUsuario);
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError()+responseDTO.getMessageError());
				usuarios.setCodError(responseDTO.getCodeError());
				usuarios.setMsgError(responseDTO.getMessageError());
			}else{
				final List<BeanUsuario> listaUsuarios = new ArrayList<BeanUsuario>();
				for(Map<String, Object> registro : responseDTO.getResultQuery()){
					final BeanUsuario usuario = new BeanUsuario();
					usuario.setIdUsuario(String.valueOf(registro.get("ID_USUARIO")));
					final String status = String.valueOf(registro.get("ESTATUS"));
					if("1".equals(status)){
						usuario.setEstatus(true);
					}
					listaUsuarios.add(usuario);
				}
				usuarios.setUsuarios(listaUsuarios);
				usuarios.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			usuarios.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			usuarios.setMsgError(ConstantesRorac.MENSAJE_ERROR_IDA + e.getMessage());
		}
		return usuarios;
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.dao.administracion.usuario.DAOUsuario#modificarUsuario(mx.isban.agave.commons.beans.ArchitechSessionBean, mx.isban.cifrascontrol.beans.administracion.usuario.BeanUsuario)
	 */
	@Override
	public BeanUsuarioRespuesta modificarUsuario(
			ArchitechSessionBean sessionBean, BeanUsuario usuario) {
		final BeanUsuarioRespuesta respuesta = new BeanUsuarioRespuesta();
		this.info("Se inicia la actualizacion del usuario");
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_UPDATE_PARAMS);
		requestDTO.setQuery(QUERY_ACTUALIZA_USUARIO);
		requestDTO.setCodeOperation("COD19 Actualiza-Usuario");
		Integer estatus = 0;
		if(usuario.isEstatus()){
			estatus = 1;
		}
		requestDTO.addParamToSql(estatus);
		requestDTO.addParamToSql(usuario.getIdUsuario());
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError()+responseDTO.getMessageError());
				respuesta.setCodError(responseDTO.getCodeError());
				respuesta.setMsgError(responseDTO.getCodeError());
			}else{
				BeanUsuarioRespuesta eliminacionRelaciones = eliminarRelacionesUsuarioGrupo(usuario.getIdUsuario());
				if(!ConstantesRorac.OPERACION_EXITOSA.equals(eliminacionRelaciones.getCodError())){
					this.error(MENSAJE_ERROR+eliminacionRelaciones.getCodError()+eliminacionRelaciones.getMsgError());
					respuesta.setCodError(eliminacionRelaciones.getCodError());
					respuesta.setMsgError(eliminacionRelaciones.getMsgError());
				}else{
					List<BeanGrupo> grupos = usuario.getGrupos();
					this.info("Tamaño de lista seleccionada:"+grupos.size());
					for (int i = 0;i < grupos.size();i++) {	
						BeanUsuarioRespuesta actualizaRelaciones = actualizaRelacionesUsuarioGrupo(usuario.getIdUsuario(),grupos.get(i).getIdGrupo());
						if(!ConstantesRorac.OPERACION_EXITOSA.equals(actualizaRelaciones.getCodError())){
							this.error(MENSAJE_ERROR+actualizaRelaciones.getCodError()+actualizaRelaciones.getMsgError());
							respuesta.setCodError(actualizaRelaciones.getCodError());
							respuesta.setMsgError(actualizaRelaciones.getMsgError());
							i = grupos.size()+1;
						}else{
							respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
						}
					}
				}
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			respuesta.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			respuesta.setMsgError(ConstantesRorac.MENSAJE_ERROR_IDA + e.getMessage());
		}
		return respuesta;
	}

	/**
	 * Metodo encargado de actualizar las relaciones usuario - grupo
	 * @param idUsuario El id del usuario a crear las relaciones
	 * @param idGrupo El id del grupo a crear las relaciones
	 * @return Un objeto de tipo BeanUsuarioRespuesta
	 */
	private BeanUsuarioRespuesta actualizaRelacionesUsuarioGrupo(String idUsuario, String idGrupo) {
		final BeanUsuarioRespuesta respuesta = new BeanUsuarioRespuesta();
		this.info("Se inicia la creacion de las relaciones Usuario - Grupo");
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_INSERT_PARAMS);
		requestDTO.setQuery(QUERY_CREA_RELACIONES_USUARIO_GRUPO);
		requestDTO.setCodeOperation("COD21 Crea Relaciones Usuario - Grupo");
		requestDTO.addParamToSql(idUsuario);
		requestDTO.addParamToSql(idGrupo);
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError()+responseDTO.getMessageError());
				respuesta.setCodError(responseDTO.getCodeError());
				respuesta.setMsgError(responseDTO.getMessageError());
			}else{
				respuesta.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			respuesta.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			respuesta.setMsgError(ConstantesRorac.MENSAJE_ERROR_IDA + e.getMessage());
		}
		this.info("Finaliza el metodo de actualizacion de relaciones");
		return respuesta;
	}

	/**
	 * Metodo encargado de eliminar las relaciones UsuarioGrupo
	 * @param idUsuario El id del usuario a eliminar las relaciones Usuario - Grupo
	 * @return Un objeto de tipo BeanUsuarioRespuesta con el resultado de la consulta en la bd
	 */
	private BeanUsuarioRespuesta eliminarRelacionesUsuarioGrupo(String idUsuario) {
		final BeanUsuarioRespuesta usuarios = new BeanUsuarioRespuesta();
		this.info("Se inicia la eliminacion de las relaciones Usuario - Grupo");
		final RequestMessageDataBaseDTO requestDTO = new RequestMessageDataBaseDTO();
		requestDTO.setTypeOperation(ConfigFactoryJDBC.OPERATION_TYPE_DELETE_PARAMS);
		requestDTO.setQuery(QUERY_ELIMINA_RELACIONES_USUARIO_GRUPO);
		requestDTO.setCodeOperation("COD20 Elimina Relaciones Usuario -Grupo");
		requestDTO.addParamToSql(idUsuario);
		try{
			final DataAccess ida = DataAccess.getInstance(requestDTO, this.getLoggingBean());
			final ResponseMessageDataBaseDTO responseDTO = (ResponseMessageDataBaseDTO)ida.execute(ID_CANAL);
			if(!ConfigFactoryJDBC.CODE_SUCCESFULLY.equals(responseDTO.getCodeError())){
				this.error(MENSAJE_ERROR+responseDTO.getCodeError()+responseDTO.getMessageError());
				usuarios.setCodError(responseDTO.getCodeError());
				usuarios.setMsgError(responseDTO.getMessageError());
			}else{
				usuarios.setCodError(ConstantesRorac.OPERACION_EXITOSA);
			}
		}catch(ExceptionDataAccess e){
			showException(e, Level.ERROR);
			usuarios.setCodError(ConstantesRorac.ERROR_EJECUCION_IDA_PERFILAMIENTO);
			usuarios.setMsgError(ConstantesRorac.MENSAJE_ERROR_IDA + e.getMessage());
		}
		return usuarios;
	}


}
