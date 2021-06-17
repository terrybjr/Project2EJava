package application.session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

public class SLSBase {

	@PersistenceContext
	protected EntityManager em;

	@PersistenceUnit
	protected EntityManagerFactory emf;

}
