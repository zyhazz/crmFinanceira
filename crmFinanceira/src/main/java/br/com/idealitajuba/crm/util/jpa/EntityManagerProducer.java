package br.com.idealitajuba.crm.util.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

/**
 * Classe que contêm um método produtor (anotado), que 
 * gera um objeto que pode ser injetado.
 * @author Leandro Duarte
 *
 */
@ApplicationScoped
public class EntityManagerProducer {

	private EntityManagerFactory factory;

	public EntityManagerProducer() {
		this.factory = Persistence.createEntityManagerFactory("CrmFinanceiraPU");
	}

	@Produces
	@RequestScoped
	public Session createEntityManager() {
		return (Session) factory.createEntityManager();

	}

	public void closeEntityManager(@Disposes EntityManager manager) {
		manager.close();
	}

}
