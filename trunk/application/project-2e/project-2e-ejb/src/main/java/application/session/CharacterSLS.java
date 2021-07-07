package application.session;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.logging.log4j.Logger;

import application.data.StatusResp;
import application.entity.Background;
import application.entity.Inventory;
import application.entity.Character;
import application.entity.User;
import application.entity.ref.RefAncestry;
import application.utils.DunGenLogger;
import application.entity.Character;

@Stateless
@EJB(name = "java:global/CharacterSLS", beanInterface = CharacterSLS.class)
@LocalBean
public class CharacterSLS {
	@EJB
	PersistenceSLS persistenceSLS;
	@EJB
	UserSLS userSLS;

	static Logger logger = DunGenLogger.getLogger();

	String className = this.getClass().getSimpleName();

	/**
	 * Creates a character and then creates the link to the user via the Character
	 * roster.
	 * 
	 * @param character
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public StatusResp createCharacter(final Long userId, final String characterName)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String method = this.className + ".createCharacter: ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		Optional<User> optUser = this.userSLS.getUserById(userId);
		if (!optUser.isPresent()) {
			throw new Error("crap");
		}
		User user = optUser.get();
		Character newCharacter = new Character(characterName, user, new Inventory(), new Background(),
				new ArrayList());
		newCharacter = this.persistenceSLS.persistPlayerCharacter(newCharacter);
		// return the persisted object to the client
		return new StatusResp(newCharacter);
	}

	public Character findCharacter(final Long id) {
		Optional<Character> character = this.getCharacterById(id);
		return character.get();
	}

	public StatusResp getCharacters() {
		List<Character> characterList = this.persistenceSLS.getDataList(Character.queryByAll, Character.class, null,
				"getPlayerCharacters");
		return new StatusResp(characterList);
	}

	public Optional<Character> getCharacterById(final Long id) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return this.persistenceSLS.getData("PlayerCharacter.byId", Character.class, parameters,
				"getCharacterById");
	}

	public Optional<RefAncestry> getAncestryByName(final String ancestryName) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", ancestryName);
		return this.persistenceSLS.getData(RefAncestry.queryByName, RefAncestry.class, parameters, "getAncestryByName");
	}


	public StatusResp setAncestry(final Long characterId, final String ancestryName) throws Exception {
		String method = this.className + ".setAncestry: ";
		if (logger.isDebugEnabled()) {
			logger.debug(method + "Entering");
		}
		Optional<Character> pcOpt = this.getCharacterById(characterId);
		if (!pcOpt.isPresent()) {
			throw new Exception("crap");
		}
		Character pc = pcOpt.get();
		Optional<RefAncestry> ancestryOpt = this.getAncestryByName(ancestryName);
		if (!ancestryOpt.isPresent()) {
			throw new Exception("crap");
		}
		RefAncestry ancestry = ancestryOpt.get();
		pc.setAncestry(ancestry);
		return new StatusResp(pc);
	}
}
