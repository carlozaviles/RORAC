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
package mx.isban.rorac.servicio.administracion.usuario;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.agave.commons.interfaces.BeanResultBO;
import mx.isban.rorac.bean.administracion.grupo.BeanGrupo;
import mx.isban.rorac.bean.administracion.grupo.BeanGrupoRespuesta;
import mx.isban.rorac.bean.administracion.pantalla.BeanPantalla;
import mx.isban.rorac.bean.administracion.pantalla.BeanPantallaRespuesta;
import mx.isban.rorac.bean.administracion.usuario.BeanUsuario;
import mx.isban.rorac.bean.administracion.usuario.BeanUsuarioRespuesta;
import mx.isban.rorac.dao.administracion.pantalla.DAOPantalla;
import mx.isban.rorac.dao.administracion.perfiles.DAOGrupo;
import mx.isban.rorac.dao.administracion.usuario.DAOUsuario;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
* Clase BOUsuarioImpl
* 
* <P>Clase de tipo SessionBean, el cual implementa la interface BOUsuario, 
* la cual se encarga de realizar la logica de negocio relacionada a la administracion de 
* usuarios.
* 
* @author Everis
* @version 1.0
* @see www.everis.com/mexico
* 
*/
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOUsuarioImpl extends Architech implements BOUsuario {

	/**
	 * Numero de version de la clase serializada
	 */
	private static final long serialVersionUID = -1025972788766748015L;
	
	/**
	 * Un objeto de tipo DAOUsuario
	 */
	@EJB
	private DAOUsuario daoUsuario;
	
	/**
	 * Un objeto de tipo DAOGrupo
	 */
	@EJB
	private DAOGrupo daoGrupo;
	
	/**
	 * Un objeto de tipo DAOPantalla
	 */
	@EJB
	private DAOPantalla daoPantalla;

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.servicio.administracion.usuario.BOUsuario#obtenerTodosUsuarios(mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public List<BeanUsuario> obtenerTodosUsuarios(
			ArchitechSessionBean sessionBean)throws BusinessException {
		this.info("Iniciando la busqueda de todos los usaurios....");
		final BeanUsuarioRespuesta resultadoConsulta = daoUsuario.obtenerTodosUsuarios(sessionBean);
		verificarRespuesta(resultadoConsulta);
		final List<BeanUsuario> usuarios = resultadoConsulta.getUsuarios();
		establecerGrupos(usuarios, sessionBean);
		this.info("El numero de usuarios encontrados:"+usuarios.size());
		this.info("Finaliza la ejecucion del metodo de busqueda de todos los grupos administrativos");
		return usuarios;
	}
	
	/**
	 * Metodo que establece los grupos de los usuarios
	 * @param usuarios Lista de usuarios a establecer los grupos
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @throws BusinessException En caso de presentarse un error al consultar la base de datos 
	 */
	private void establecerGrupos(List<BeanUsuario> usuarios, ArchitechSessionBean sessionBean)throws BusinessException{
		this.info("Iniciando la busqueda de todos los grupos por usuario....");
		for (BeanUsuario beanUsuario : usuarios) {
			final BeanGrupoRespuesta respuesta = daoGrupo.obtenerGrupoPorUsuario(sessionBean, beanUsuario.getIdUsuario());
			verificarRespuesta(respuesta);
			final List<BeanGrupo> grupo = respuesta.getGrupos();
			establecerPantallas(grupo,sessionBean);
			beanUsuario.setGrupos(grupo);
		}
		this.info("Finaliza el establecimiento de grupos");
	}

	/**
	 * Metodo que establece las pantallas en relacion a un listado de gruopos
	 * @param grupo La lista de grupos a establecer las pantallas
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @throws BusinessException En caso de presentarse un error al consultar la base de datos
	 */
	private void establecerPantallas(List<BeanGrupo> grupo,
			ArchitechSessionBean sessionBean)throws BusinessException {
		this.info("iniciando la busqueda de todas las pantallas relacionadas a un grupo");
		for (BeanGrupo beanGrupo : grupo) {
			final BeanPantallaRespuesta respuesta = daoPantalla.obtenerPantallasPorGrupo(sessionBean, beanGrupo.getIdGrupo());
			verificarRespuesta(respuesta);
			final List<BeanPantalla> pantallas = respuesta.getPantallas();
			beanGrupo.setPantallas(pantallas);
		}
		this.info("Finaliza el establecimiento de las pantallas");
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.servicio.administracion.usuario.BOUsuario#obtenerUsuarioPorID(mx.isban.agave.commons.beans.ArchitechSessionBean, java.lang.String)
	 */
	@Override
	public BeanUsuario obtenerUsuarioPorID(ArchitechSessionBean sessionBean,
			String idUsuario) throws BusinessException{
		this.info("Iniciando la busqueda de un usuario....");
		final BeanUsuarioRespuesta resultadoConsulta = daoUsuario.obtenerUsuarioPorID(sessionBean, idUsuario);
		verificarRespuesta(resultadoConsulta);
		final List<BeanUsuario> usuarios = resultadoConsulta.getUsuarios();
		this.info("El numero de usuarios encontrados:"+usuarios.size());
		BeanUsuario usuario = new BeanUsuario();
		if(!usuarios.isEmpty()){
			usuario = usuarios.get(0);
			final List<BeanGrupo> grupos = establecerGruposSeleccionados(sessionBean, idUsuario);
			usuario.setGrupos(grupos);
		}
		this.info("Finaliza la ejecucion del metodo de busqueda de un usuario");
		return usuario;
	}

	/**
	 * Metodo que establece los grupos seleccionados en relacion a un usuario
	 * @param sessionBean Un objeto de tipo ArchitechSessionBean
	 * @param idUsuario El id del usuario a buscar los grupos seleccionados
	 * @return El listado de grupos al que pertenece el usuario
	 * @throws BusinessException En caso de presentarse un error al consultar la base de datos
	 */
	private List<BeanGrupo> establecerGruposSeleccionados(
			ArchitechSessionBean sessionBean, String idUsuario)throws BusinessException {
		this.info("Inicia el establecimiento de grupos seleccionados");
		final BeanGrupoRespuesta gruposSeleccionadosRespuesta = daoGrupo.obtenerGrupoPorUsuario(sessionBean, idUsuario);
		verificarRespuesta(gruposSeleccionadosRespuesta);
		final List<BeanGrupo> gruposSeleccionados = gruposSeleccionadosRespuesta.getGrupos();
		final BeanGrupoRespuesta todosGrupos = daoGrupo.buscarTodosGrupos(sessionBean);
		verificarRespuesta(todosGrupos);
		final List<BeanGrupo> grupos = todosGrupos.getGrupos();
		for (BeanGrupo beanGrupo : grupos) {
			for (BeanGrupo beanGrupoSeleccionado : gruposSeleccionados) {
				if(beanGrupo.getIdGrupo().equals(beanGrupoSeleccionado.getIdGrupo())){
					beanGrupo.setGrupoSeleccionado(true);
				}
			}
		}
		this.info("Finaliza el establecmimiento de los grupos seleccionados");
		return grupos;
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.servicio.administracion.usuario.BOUsuario#modificarUsuario(mx.isban.agave.commons.beans.ArchitechSessionBean, mx.isban.cifrascontrol.beans.administracion.usuario.BeanUsuario)
	 */
	@Override
	public void modificarUsuario(ArchitechSessionBean sessionBean,
			BeanUsuario usuario) throws BusinessException{
		this.info("Iniciando la modificacion de un usuario....");
		final BeanUsuarioRespuesta resultadoConsulta = daoUsuario.modificarUsuario(sessionBean, usuario);
		verificarRespuesta(resultadoConsulta);
		this.info("Finaliza la ejecucion del metodo de actualizacion de usuario");		
	}

	/* (non-Javadoc)
	 * @see mx.isban.cifrascontrol.servicio.administracion.usuario.BOUsuario#altaUsuario(mx.isban.agave.commons.beans.ArchitechSessionBean, mx.isban.cifrascontrol.beans.administracion.usuario.BeanUsuario)
	 */
	@Override
	public void altaUsuario(ArchitechSessionBean sessionBean,
			BeanUsuario usuario) throws BusinessException{
		this.info("Iniciando el alta de un usuario....");
		final BeanUsuarioRespuesta resultadoConsulta = daoUsuario.altaUsuario(sessionBean, usuario);
		verificarRespuesta(resultadoConsulta);
		this.info("Finaliza la ejecucion del metodo de alta de usuario");	
	}

	/**
	 * Metodo encargado de verificar las respuestas obtenidas del DAO
	 * @param resultadoConsulta Un objeto con los resultados de la consulta
	 * @throws BusinessException En caso de presentarse un error
	 */
	private void verificarRespuesta(BeanResultBO resultadoConsulta)throws BusinessException{
		if(null == resultadoConsulta){
			this.error("Respuesta nula al consultar la base de datos");
			throw new BusinessException(ConstantesRorac.ERROR_RESPUESTA_NULA_IDA_PERFILAMIENTO,
					"Respuesta nula al consultar la base de datos");
		}
		if(!ConstantesRorac.OPERACION_EXITOSA.equals(resultadoConsulta.getCodError())){
			this.error("Se ha presentado un error al momento de realizar la consulta en la base de datos :"+resultadoConsulta.getCodError());
			throw new BusinessException(resultadoConsulta.getCodError(),resultadoConsulta.getMsgError());
		}
	}

	/**
	 * Metodo encargado de obtener un objeto de tipo DAOUsuario
	 * @return Un objeto de tipo DAOUsuario
	 */
	public DAOUsuario getDaoUsuario() {
		return daoUsuario;
	}

	/**
	 * Metodo encargado de establecer un objeto de tipo DAOUsuario
	 * @param daoUsuario El objeto de tipo DAOUsuario a establecer
	 */
	public void setDaoUsuario(DAOUsuario daoUsuario) {
		this.daoUsuario = daoUsuario;
	}

	/**
	 * Metodo encargado de obtener un objeto de tipo DAOGrupo
	 * @return Un objeto de tipo DAOGrupo
	 */
	public DAOGrupo getDaoGrupo() {
		return daoGrupo;
	}

	/**
	 * Metodo encargado de establecer un objeto de tipo DAOGrupo
	 * @param daoGrupo El objeto de tipo DAOGrupo a establecer
	 */
	public void setDaoGrupo(DAOGrupo daoGrupo) {
		this.daoGrupo = daoGrupo;
	}

	/**
	 * Metodo encargado de obtener un objeto de tipo DAOPantalla
	 * @return Un objeto de tipo DAOPantalla
	 */
	public DAOPantalla getDaoPantalla() {
		return daoPantalla;
	}

	/**
	 * Metodo encargado de establecer un objeto de tipo DAOPantalla
	 * @param daoPantalla El objeto de tipo DAOPantalla a establecer
	 */
	public void setDaoPantalla(DAOPantalla daoPantalla) {
		this.daoPantalla = daoPantalla;
	}
	
}
