package mx.isban.rorac.servicio.consultas;

import javax.ejb.Remote;

import mx.isban.agave.commons.beans.ArchitechSessionBean;
import mx.isban.agave.commons.exception.BusinessException;
import mx.isban.rorac.bean.consultas.BeanADNLocal;
import mx.isban.rorac.bean.consultas.BeanADNRetail;
import mx.isban.rorac.bean.consultas.BeanProductoGestion;

@Remote
public interface BOAltaParametros {

	/**
	 * Da de alta un nuevo registro de tipo ADN Local.
	 * 
	 * @param nuevoRegistro
	 *            Contiene la informacion del registro ADN Local que se dara de
	 *            alta.
	 * @param sessionBean
	 *            Objeto de la arquitectura Agave.
	 * @throws BusinessException
	 *             Exception.
	 */
	public void llamaAltaADNLocal(BeanADNLocal nuevoRegistro,
			ArchitechSessionBean sessionBean) throws BusinessException;

	/**
	 * Da de alta un nuevo registro de tipo Producto Gestion.
	 * 
	 * @param nuevoRegistro
	 *            Contiene la informacion del registro Producto Gestio que se
	 *            dara de alta.
	 * @param sessionBean
	 *            Objeto de la arquitectura Agave.
	 * @throws BusinessException
	 *             Exception
	 */
	public void llamaAltaProductoGestion(BeanProductoGestion nuevoRegistro,
			ArchitechSessionBean sessionBean) throws BusinessException;

	/**
	 * Da de alta un nuevo registro de tipo ADNRetail.
	 * 
	 * @param nuevoRegistro
	 *            Contiene la informacion del registro Adn Retail que se dara de
	 *            alta.
	 * @param sessionBean
	 *            Objeto de la arquitectura Agave.
	 * @throws BusinessException
	 *             Exception.
	 */
	public void llamaAltaADNRetail(BeanADNRetail nuevoRegistro,
			ArchitechSessionBean sessionBean) throws BusinessException;
}
