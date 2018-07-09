package br.com.idealitajuba.crm.business;

import java.io.Serializable;
import java.sql.SQLException;

import javax.inject.Inject;

import com.mysql.jdbc.exceptions.jdbc4.MySQLDataException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.com.idealitajuba.crm.model.Cliente;
import br.com.idealitajuba.crm.repository.ClienteRepos;
import br.com.idealitajuba.crm.util.Transactional;

/**
 * Classe que implementa as regras de negócio.
 * 
 * @author Leandro Duarte
 *
 */
public class CadastroCliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteRepos cr;

	@Transactional
	public void salvar(Cliente c) throws Exception {
		this.cr.guardar(c);

	}

	/**
	 * RN01 - Não é possível excluir Cliente que possui Contato associado.
	 * 
	 * @param c
	 * @throws BusinessException
	 */
	@Transactional
	public void excluir(Cliente c) throws BusinessException {
		c = this.cr.porId(c.getId());
		if (!this.cr.verificaCliente(c.getId()))
			throw new BusinessException("Não é possível excluir Cliente que " + "possui Contato associado.");
		this.cr.remover(c);
	}
}
