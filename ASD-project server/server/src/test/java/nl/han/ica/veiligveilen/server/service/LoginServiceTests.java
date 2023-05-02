package nl.han.ica.veiligveilen.server.service;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import nl.han.ica.veiligveilen.server.model.requestobjects.LoginDTO;
import nl.han.ica.veiligveilen.server.model.responseobjects.LoginResponse;
import org.apache.tomcat.websocket.AuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class LoginServiceTests {

	private String ip;
	private LoginDTO loginDTO;
	private UUID clientId;
	private User user;

	private LoginService sut;
	private IUserRepository userRepositoryFixture;
	private IUserClientsRepository userClientsRepositoryFixture;
	private IClientRepository clientRepositoryFixture;
	private ILoginsRepository loginsRepositoryFixture;


	@BeforeEach
	void setup(){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		this.userRepositoryFixture = mock(IUserRepository.class);
		this.userClientsRepositoryFixture = mock(IUserClientsRepository.class);
		this.clientRepositoryFixture = mock(IClientRepository.class);
		this.loginsRepositoryFixture = mock(ILoginsRepository.class);
		this.sut = new LoginService(
			userRepositoryFixture,
			clientRepositoryFixture,
			userClientsRepositoryFixture,
			loginsRepositoryFixture
		);

		this.ip = "1.1.1.1";
		this.clientId = UUID.randomUUID();
		this.loginDTO = new LoginDTO("username", "password", clientId);
		this.user = new User(UUID.randomUUID(), "username", encoder.encode("password"));
	}

	@Test
	void testLoginWithValidCredentialsWithAlreadyKnownClient() throws AuthenticationException {
		when(this.userRepositoryFixture.findByUsername(anyString())).thenReturn(Optional.of(this.user));
		when(this.userRepositoryFixture.findById(any(UUID.class))).thenReturn(Optional.ofNullable(this.user));
		when(this.clientRepositoryFixture.findById(any(UUID.class))).thenReturn(Optional.of(new Client(clientId)));

		LoginResponse response = sut.loginUser(this.loginDTO, this.ip);

		verify(this.clientRepositoryFixture, times(0)).save(any(Client.class));
		verify(this.userClientsRepositoryFixture, times(1)).save(any(UserClient.class));
		verify(this.userClientsRepositoryFixture, times(1)).flush();
		verify(this.loginsRepositoryFixture, times(1))
			.insertLogin(anyString(), anyString(),anyString(), anyString(), any(LocalDateTime.class));

		assertNotNull(response.loginId());
	}

}
