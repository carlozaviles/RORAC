/**
 * 
 */
package mx.isban.rorac.bean;

import java.util.Date;

import mx.isban.agave.commons.interfaces.EjecutorObjeto;

/**
 * @author everis
 *
 */
public class BeanPistasAuditoria implements EjecutorObjeto {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 6246182939427800432L;
	/**
	 * Nombre Clase.
	 */
	private String stringClass = this.getClass().getCanonicalName();
	/**
	 * Fecha
	 */
	private Date fecha;
	/**
	 * Hora
	 */
	private String hora;
	/**
	 * Direccion Ip
	 */
	private String direccionIp;
	/**
	 * Usuario.
	 */
	private String usuario;
	/**
	 * Aplicacion
	 */
	private String aplicacion;
	/**
	 * Codigo de Operacion.
	 */
	private String codigoOperacion;
	/**
	 * Id de Operacion.
	 */
	private String idOperacion;
	/**
	 * Estatus de la Operacion;
	 */
	private String estatusOperacion;
	/**
	 * ID de Sesion
	 */
	private String idSesion;
	/**
	 * Nombre del Archivo
	 */
	private String nombreArchivo;
	/**
	 * Id Instancia web
	 */
	private String idInstanciaWeb;
	/**
	 * Nombre del Servidor.
	 */
	private String nombreServidor;
	/**
	 * Id Insumo
	 */
	private String idInsumo;
	/**
	 * idEstatus
	 */
	private String idEstatus;
	
	/* (non-Javadoc)
	 * @see mx.isban.agave.commons.interfaces.EjecutorObjeto#getStringClass()
	 */
	@Override
	public String getStringClass() {
		return this.stringClass;
	}

	/* (non-Javadoc)
	 * @see mx.isban.agave.commons.interfaces.EjecutorObjeto#setStringClass(java.lang.String)
	 */
	@Override
	public void setStringClass(String stringClass) {
		// TODO Auto-generated method stub
		this.stringClass = stringClass;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the hora
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * @param hora the hora to set
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * @return the direccionIp
	 */
	public String getDireccionIp() {
		return direccionIp;
	}

	/**
	 * @param direccionIp the direccionIp to set
	 */
	public void setDireccionIp(String direccionIp) {
		this.direccionIp = direccionIp;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the aplicacion
	 */
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * @param aplicacion the aplicacion to set
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * @return the codigoOperacion
	 */
	public String getCodigoOperacion() {
		return codigoOperacion;
	}

	/**
	 * @param codigoOperacion the codigoOperacion to set
	 */
	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}

	/**
	 * @return the idOperacion
	 */
	public String getIdOperacion() {
		return idOperacion;
	}

	/**
	 * @param idOperacion the idOperacion to set
	 */
	public void setIdOperacion(String idOperacion) {
		this.idOperacion = idOperacion;
	}

	/**
	 * @return the estatusOperacion
	 */
	public String getEstatusOperacion() {
		return estatusOperacion;
	}

	/**
	 * @param estatusOperacion the estatusOperacion to set
	 */
	public void setEstatusOperacion(String estatusOperacion) {
		this.estatusOperacion = estatusOperacion;
	}

	/**
	 * @return the idSesion
	 */
	public String getIdSesion() {
		return idSesion;
	}

	/**
	 * @param idSesion the idSesion to set
	 */
	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * @param nombreArchivo the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	/**
	 * @return the idInstanciaWeb
	 */
	public String getIdInstanciaWeb() {
		return idInstanciaWeb;
	}

	/**
	 * @param idInstanciaWeb the idInstanciaWeb to set
	 */
	public void setIdInstanciaWeb(String idInstanciaWeb) {
		this.idInstanciaWeb = idInstanciaWeb;
	}

	/**
	 * @return the nombreServidor
	 */
	public String getNombreServidor() {
		return nombreServidor;
	}

	/**
	 * @param nombreServidor the nombreServidor to set
	 */
	public void setNombreServidor(String nombreServidor) {
		this.nombreServidor = nombreServidor;
	}

	/**
	 * @return the idInsumo
	 */
	public String getIdInsumo() {
		return idInsumo;
	}

	/**
	 * @param idInsumo the idInsumo to set
	 */
	public void setIdInsumo(String idInsumo) {
		this.idInsumo = idInsumo;
	}

	/**
	 * @return the idEstatus
	 */
	public String getIdEstatus() {
		return idEstatus;
	}

	/**
	 * @param idEstatus the idEstatus to set
	 */
	public void setIdEstatus(String idEstatus) {
		this.idEstatus = idEstatus;
	}


}