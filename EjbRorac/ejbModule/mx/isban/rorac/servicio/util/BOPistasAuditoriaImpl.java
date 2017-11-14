package mx.isban.rorac.servicio.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.beans.MessageBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.agave.commons.exception.MensajeClientException;
import mx.isban.agave.mensajeria.MensajeClient;
import mx.isban.rorac.bean.BeanPistasAuditoria;
import mx.isban.rorac.dao.DAOPistasAuditoria;
import mx.isban.rorac.dao.util.DAOUtilPistasAuditoria;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Session Bean implementation class BOPistasAuditoriaImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOPistasAuditoriaImpl extends Architech implements BOPistasAuditoria {
       
    /**
	 * Serial.
	 */
	private static final long serialVersionUID = 4375290519247645091L;
	/**
	 * Objeto utlizado para obtener datos de los procesos y asi generar las pistas de auditoria
	 */
	@EJB
	private transient DAOUtilPistasAuditoria daoPistas;

	/* (non-Javadoc)
	 * @see mx.isban.rorac.servicio.util.BOPistasAuditoria#generaPistaAuditoria(mx.isban.rorac.bean.BeanPistasAuditoria, mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void generaPistaAuditoria(BeanPistasAuditoria pistaAuditoria, ArchitechSessionBean sessionBean)  throws BusinessException {
		this.setArchitechBean(sessionBean);
		final String metodo = "generaPistaAuditoria()";
		this.info("Comienza ejecucion del metodo " + metodo);
		validaPistaAuditoria(pistaAuditoria, sessionBean);
		final MensajeClient clienteMensajeria = new MensajeClient(this.getLoggingBean());
		pistaAuditoria.setFecha(new Date());
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		pistaAuditoria.setHora(sdf.format(new Date()));
		pistaAuditoria.setUsuario(sessionBean.getUsuario());
		pistaAuditoria.setDireccionIp(sessionBean.getIPCliente());
		pistaAuditoria.setAplicacion(sessionBean.getNombreAplicacion());
		pistaAuditoria.setIdSesion(sessionBean.getIdSesion());
		pistaAuditoria.setNombreServidor(sessionBean.getIPServidor());
		pistaAuditoria.setIdInstanciaWeb(sessionBean.getNombreServidor());
		boolean excepcionGenerada = false;
		try{
			final MessageBean mensaje = new MessageBean();
			mensaje.setLogginBean(this.getLoggingBean());
			mensaje.setDaoEjecutor(DAOPistasAuditoria.class.getCanonicalName());
			mensaje.setObjetoEjecutor(pistaAuditoria);
			clienteMensajeria.enviaMensaje(mensaje);
		}catch(MensajeClientException e){
			this.warn("Se genero una excepcion al intentar enviar la pista de auditoria: " + e);
			excepcionGenerada = true;
		}
		if(excepcionGenerada){
			throw new BusinessException(ConstantesRorac.ERROR_GENERAR_PISTA_AUDITORIA);
		}
		this.info("Finaliza la ejecucion del metodo " + metodo);
	}
	
	/**
	 * Verifica que los campos del bean de pista de auditoria, y de ser necesario complementa
	 * la informacion.
	 * @param pistaAuditoria Objeto que contiene la informacion de pistas de auditoria.
	 * @param sessionBean Objeto de la arquitectura agave.
	 * @throws BusinessException Excepcion
	 */
	private void validaPistaAuditoria(BeanPistasAuditoria pistaAuditoria, ArchitechSessionBean sessionBean) 
			throws BusinessException {
		if(pistaAuditoria.getNombreArchivo() == null && pistaAuditoria.getIdInsumo() != null){
			final String nombreArchivo = daoPistas.obtenerNombreInsumo(pistaAuditoria.getIdInsumo(), sessionBean);
			pistaAuditoria.setNombreArchivo(nombreArchivo);
		}else if(pistaAuditoria.getIdEstatus() != null){
			final Map<String, String> resultado = daoPistas.obtenerInfoOperacion(pistaAuditoria.getIdEstatus(), sessionBean);
			pistaAuditoria.setNombreArchivo(resultado.get(DAOUtilPistasAuditoria.NOMBRE_ARCHIVO));
			pistaAuditoria.setCodigoOperacion(resultado.get(DAOUtilPistasAuditoria.ID_OPERACION));
		}
	}

}
