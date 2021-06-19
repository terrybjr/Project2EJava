package application.session;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import application.entity.PlayerCharacter;
import application.entity.User;
import application.entity.PlayerCharacter;

@Stateless
@EJB(name = "java:global/PlayerCharacterSLS", beanInterface = PlayerCharacterSLS.class)
@LocalBean
public class PlayerCharacterSLS {
	@EJB
	PersistenceSLS persistenceSLS;
	@EJB
	UserSLS userSLS;

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
	public PlayerCharacter createPlayerCharacter(final Long userId, final String characterName)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Optional<User> optUser = this.userSLS.getUserById(userId);
		if (!optUser.isPresent()) {
			throw new Error("crap");
		}
		User user = optUser.get();
		PlayerCharacter newCharacter = new PlayerCharacter();
		newCharacter.setName(characterName);

		// persist into db
		return this.persistenceSLS.persistPlayerCharacter(newCharacter);
		// return the persisted object to the client
	}

	public PlayerCharacter updatePlayerCharacter(final PlayerCharacter character) {
		Optional<PlayerCharacter> CharacterOpt = this.getPlayerCharacterById(character.getId());
		PlayerCharacter entCharacter = CharacterOpt.get();
		entCharacter.updateItem(character);
		return entCharacter;
	}

	public PlayerCharacter findPlayerCharacter(final Long id) {
		Optional<PlayerCharacter> character = this.getPlayerCharacterById(id);
		return character.get();
	}

	public List<PlayerCharacter> getPlayerCharacters() {
		List<PlayerCharacter> characterList = this.persistenceSLS.getDataList(PlayerCharacter.queryByAll, PlayerCharacter.class, null,
				"getPlayerCharacters");
		return characterList;
	}

	public Optional<PlayerCharacter> getPlayerCharacterById(final Long id) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", id);
		return this.persistenceSLS.getData("character.byId", PlayerCharacter.class, parameters, "getCharacterById");
	}
}
