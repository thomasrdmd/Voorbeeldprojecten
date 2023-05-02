package nl.han.ica.veiligveilen.server.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.dao.IClientRepository;
import nl.han.ica.veiligveilen.server.dao.ILoginsRepository;
import nl.han.ica.veiligveilen.server.dao.IUserClientsRepository;
import nl.han.ica.veiligveilen.server.dao.IUserRepository;
import nl.han.ica.veiligveilen.server.model.dataclasses.Client;
import nl.han.ica.veiligveilen.server.model.dataclasses.User;
import nl.han.ica.veiligveilen.server.model.dataclasses.UserClient;
import nl.han.ica.veiligveilen.server.model.exceptions.InternalServerErrorException;
import nl.han.ica.veiligveilen.server.model.keys.UserClientKey;
import nl.han.ica.veiligveilen.server.model.requestobjects.LoginDTO;
import nl.han.ica.veiligveilen.server.model.responseobjects.LoginResponse;
import nl.han.ica.veiligveilen.server.service.interfaces.ILoginService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for logging into the network.
 */

@Service
public class LoginService implements ILoginService {

	private final Logger logger = LoggerFactory.getLogger(LoginService.class);
	private final IUserRepository userRepository;
	private final IClientRepository clientRepository;
	private final IUserClientsRepository userClientsRepository;
	private final ILoginsRepository loginsRepository;

	/**
	 * Constructor for this class. All repositories are automatically injected by spring.
	 * @param userRepository Needed to check user info.
	 * @param clientRepository Needed to check and maybe add client info.
	 * @param userClientsRepository Needed to bind a user to a client.
	 * @param loginsRepository Needed to log logins.
	 */
	@Autowired
	public LoginService(IUserRepository userRepository, IClientRepository clientRepository,
						IUserClientsRepository userClientsRepository,
						ILoginsRepository loginsRepository) {
		this.userRepository = userRepository;
		this.clientRepository = clientRepository;
		this.userClientsRepository = userClientsRepository;
		this.loginsRepository = loginsRepository;
	}

	@Override
	public LoginResponse loginUser(LoginDTO authenticationRequest, String ip) throws AuthenticationException {
		final User user = this.getUserByUsername(authenticationRequest.getUsername());
		final UUID userId = user.getUserId();

		UserClient userClient = null;

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
			throw new AuthenticationException("Invalid username or password");
		}

		try {
			userClient = updateClientId(authenticationRequest.getClientId(), userId);
		} catch (AuthenticationException e) {
			this.logger.warn("Client id is not valid.");
		}

		if (userClient != null) {
			final UUID loginID = recordLogin(userClient, ip);
			return new LoginResponse(loginID);
		} else {
			throw new InternalServerErrorException("Could not create login.");
		}
	}


	/**
	 * This function checks if the username is known in the database. If so, it returns a LoginDTO with all
	 * available data. In this clientId is null, since the controller already knows this.
	 * If needed in another function, it can be added through the UserClient repository.
	 * Throws an exception when the username isn't found in the database.
	 *
	 * @param username The username of the user.
	 * @return Returns a LoginDTO with all available info.
	 */
	private User getUserByUsername(String username) {
		final Optional<User> login = this.userRepository.findByUsername(username);

		if (login.isEmpty()) {
			throw new InternalServerErrorException("User not found with username: " + username);
		}
		return login.get();

	}

	/**
	 * records a login in the database.
	 *
	 * @param userClient An instance of a UserClient containing the user and client ids.
	 * @param ipAddress The IP address as a String from where the request originated.
	 *
	 * @return Returns the id of the login as a UUID.
	 */
	private UUID recordLogin(UserClient userClient, String ipAddress) {
		UUID loginId = UUID.randomUUID();
		LocalDateTime now = LocalDateTime.now();

		this.loginsRepository.insertLogin(
			loginId.toString(),
			userClient.getKey().getUser().getUserId().toString(),
			userClient.getKey().getClient().getClientId().toString(),
			ipAddress,
			now);
		return loginId;
	}

	/**
	 * Updates the clientId for a user if the user logs in with a new Client.
	 * If the clientId or userId are empty strings, an AuthenticationException is thrown.
	 *
	 * @param clientId The id of the client
	 * @param userId The id of the user.
	 * @throws AuthenticationException Thrown when either of the params are empty strings.
	 */
	private UserClient updateClientId(UUID clientId, UUID userId) throws AuthenticationException {
		if (clientId == null || userId == null) {
			throw new AuthenticationException("No client or userId found ");
		}
		checkClientId(clientId);

		return updateUserClient(clientId, userId);
	}

	/**
	 * Checks if the clientId is already known. If not, it is added to the database.
	 * @param clientId the to be checked (and added) clientId.
	 */
	private void checkClientId(UUID clientId) {
		Optional<Client> client = this.clientRepository.findById(clientId);

		if (client.isPresent()) {
			return;
		}
		this.clientRepository.save(new Client(clientId));
	}

	/**
	 * Add new login attempt to the database.
	 * @param clientId The to be checked clientId.
	 * @param userId The to be checked userId.
	 */
	private UserClient updateUserClient(UUID clientId, UUID userId) {

		UserClient userClient = new UserClient(new UserClientKey(
			userRepository.findById(userId).orElse(null),
			clientRepository.findById(clientId).orElse(null)
		));

		this.userClientsRepository.save(userClient);
		this.userClientsRepository.flush();

		return userClient;
	}
}
