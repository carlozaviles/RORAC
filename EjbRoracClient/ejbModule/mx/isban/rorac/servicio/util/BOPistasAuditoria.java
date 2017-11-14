package mx.isban.rorac.servicio.util;

import javax.ejb.Local;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.BeanPistasAuditoria;

@Local
public interface BOPistasAuditoria {

	/**
	 * Ejecuta el proceso para guardar las pistas de auditoria generadas en la aplicacion.
	 * @param pistaAuditoria Contiene la informacion de las pistas de auditoria a guardar.
	 * @param sessionBean Objeto de la arquitectura agave.
	 * @throws BusinessException Exception.
	 */
	public void generaPistaAuditoria(BeanPistasAuditoria pistaAuditoria, ArchitechSessionBean sessionBean) 
			throws BusinessException;
}
