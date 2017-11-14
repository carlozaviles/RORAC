package mx.isban.rorac.servicio.inputs;

import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import mx.isban.agave.commons.architech.Architech;
import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.BeanPistasAuditoria;
import mx.isban.rorac.servicio.util.BOPistasAuditoria;
import mx.isban.rorac.utilerias.general.ConstantesRorac;

/**
 * Session Bean implementation class BOCargaManualImpl
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BOCargaManualImpl extends Architech implements BOCargaManual {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Propiedad que contiene el id de operacion de Cargas Manuales.
	 */
	private static final String PARAM_CARGA_MANUAL_ID_OPERACION = "PARAM_CONFIG_CARGA_MANUAL_ID_OPERACION";
	/**
	 * Cantidad de interfaces registradas para realizar la validacion de la carga.
	 */
	private static final String PARAMETRO_NUMERO_INTERFACES = "PARAM_CONFIG_NUMERO_INTERFACES";
	/**
	 * Nombre base para los parametros que contienen las mascaras de las interfaces a validar.
	 */
	private static final String PARAMETRO_NOMBRE_MASCARA = "PARAM_CONFIG_MASCARA";
	/**
	 * Objeto que guarda pistas de auditoria.
	 */
	@EJB
	private transient BOPistasAuditoria manejadorPistaAuditoria;

	/* (non-Javadoc)
	 * @see mx.isban.rorac.servicio.inputs.BOCargaManual#validarNombreInput(java.lang.String, mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public Boolean validarNombreInput(String nombreInput,
			ArchitechSessionBean sessionBean) throws BusinessException {
		final String metodo = "validarNombreInput()";
		this.debug("Inicio de ejecucion del metodo: " + metodo);
		this.debug("El nombre de interfaz que sera validado es " + nombreInput);
		final int numeroInterfaces = Integer.parseInt(this.getConfigDeCmpAplicacion(PARAMETRO_NUMERO_INTERFACES));
		boolean validacionInterfaz = false;
		for(int i = 1; i <= numeroInterfaces; i++){
			final String mascara = this.getConfigDeCmpAplicacion(PARAMETRO_NOMBRE_MASCARA + i);
			if(mascara == null){
				this.debug("Ocurrio un error al cargar el parametro " + PARAMETRO_NOMBRE_MASCARA + i);
				throw new BusinessException(ConstantesRorac.ERROR_CONFIGURACION_CARGAS_MANUALES);
			}
			validacionInterfaz = Pattern.matches(mascara, nombreInput);
			if(validacionInterfaz){
				this.debug("El nombre del input " + nombreInput + " fue validado contra la mascara " + mascara);
				break;
			}
		}
		if(!validacionInterfaz){
			this.debug("El nombre del input " + nombreInput + " no corresponde con ninguna de las mascaras registradas");
		}
		this.debug("Fin de ejecucion del metodo: " + metodo);
		return validacionInterfaz;
	}

	/* (non-Javadoc)
	 * @see mx.isban.rorac.servicio.inputs.BOCargaManual#registraCargaInput(java.lang.String, java.lang.Boolean, mx.isban.agave.commons.beans.ArchitechSessionBean)
	 */
	@Override
	public void registraCargaInput(String nombreInput, Boolean cargado, ArchitechSessionBean sessionBean) 
			throws BusinessException {
		final String metodo = "registraCargaInput";
		this.debug("Inicio de ejecucion del metodo: " + metodo);
		final String codOperacionCargas = this.getConfigDeCmpAplicacion(PARAM_CARGA_MANUAL_ID_OPERACION);
		final BeanPistasAuditoria pistaAuditoria = new BeanPistasAuditoria();
		pistaAuditoria.setCodigoOperacion(codOperacionCargas);
		pistaAuditoria.setNombreArchivo(nombreInput);
		if(cargado){
			this.debug("Se registrara en Pistas de Auditoria la operacion de carga con estatus OK.");
			pistaAuditoria.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_OK);
			manejadorPistaAuditoria.generaPistaAuditoria(pistaAuditoria, sessionBean);
		}else{
			this.debug("Se registrara en Pistas de Auditoria la operacion de carga con estatus ERROR.");
			pistaAuditoria.setEstatusOperacion(ConstantesRorac.PISTAS_AUDITORIA_ESTATUS_ERROR);
			manejadorPistaAuditoria.generaPistaAuditoria(pistaAuditoria, sessionBean);
		}
		this.debug("Fin de ejecucion del metodo " + metodo);
	}

}
