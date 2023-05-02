package nl.han.ica.veiligveilen.server.model.responseobjects;

import java.util.UUID;

public record IPDiscoveryResponse(int discoveryID, String ipAddress, UUID auctionId) { }
