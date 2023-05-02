package nl.han.ica.veiligveilen.server.model.responseobjects;

import java.util.UUID;

/**
 * This class contains the data given after a succesful login.
 */
public record LoginResponse(UUID loginId) {
}
