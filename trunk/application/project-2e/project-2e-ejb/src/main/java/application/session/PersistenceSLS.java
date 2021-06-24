package application.session;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import application.entity.Background;
import application.entity.Inventory;
import application.entity.Level;
import application.entity.PlayerCharacter;
import application.entity.ToDo;
import application.entity.User;
import application.utils.MiscUtils;

@Stateless
@EJB(name = "java:global/PersistenceSLS", beanInterface = PersistenceSLS.class)
@LocalBean

public class PersistenceSLS extends SLSBase {

	public <T> T persistItem(final T item, final String getKeyMethod, final String callingMethod)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		final Class<? extends Object> cls = item.getClass();
		final Method keyMethod = MiscUtils.matchMethod(cls.getMethods(), getKeyMethod);

		this.em.persist(item);
		this.em.flush();

		final Object keyId = keyMethod.invoke(item, (Object[]) null);
		if (keyId instanceof Integer) {
			if ((Integer) keyId == 0) {
				// TODO:... needs to auto-increment... throw error
			}
		}
		@SuppressWarnings("unchecked")
		final T newClass = (T) this.em.find(item.getClass(), keyMethod.invoke(item, (Object[]) null));
		if (newClass == null) {
			// TODO: throw error.... we have a failure to persist
		}
		return newClass;
	}

	public <T, K> List<T> getDataList(final String queryName, final Class<T> queryClass,
			final Map<String, ?> queryParameters, final String callingmethod) {
		List<T> results = null;

		final TypedQuery<T> query = this.em.createNamedQuery(queryName, queryClass);
		if (queryParameters != null) {
			for (final Entry<String, ?> e : queryParameters.entrySet()) {
				query.setParameter(e.getKey(), e.getValue());
			}
		}
		results = query.getResultList();
		return results;
	}

	public <T, K> Optional<T> getData(final String queryName, final Class<T> queryClass,
			final Map<String, ?> queryParameters, final String callingmethod) {
		final List<T> results = this.getDataList(queryName, queryClass, queryParameters, callingmethod);
		if (results.size() == 0) {
			// TODO: debug... nada
			return Optional.ofNullable(null);
		}
		if (results.size() > 1) {
			// TODO: debug Too many
		}
		return Optional.ofNullable(results.get(0));
	}

	public Level persistInventory(final Level item)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return this.persistItem(item, item.methodGetKey(), "persistLevel");
	}

	public Inventory persistInventory(final Inventory item)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return this.persistItem(item, item.methodGetKey(), "persistInventory");
	}

	public Background persistBackground(final Background item)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return this.persistItem(item, item.methodGetKey(), "persistBackground");
	}

	public ToDo persistToDo(final ToDo toDo)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return this.persistItem(toDo, toDo.methodGetKey(), "persistToDo");
	}

	public PlayerCharacter persistPlayerCharacter(final PlayerCharacter character)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return this.persistItem(character, character.methodGetKey(), "persistPlayerCharacter");
	}

	public User persistUser(final User user)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return this.persistItem(user, user.methodGetKey(), "persistUser");
	}
}
