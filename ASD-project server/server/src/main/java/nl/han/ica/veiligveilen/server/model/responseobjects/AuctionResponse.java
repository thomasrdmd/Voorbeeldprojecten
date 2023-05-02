package nl.han.ica.veiligveilen.server.model.responseobjects;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import nl.han.ica.veiligveilen.server.model.dataclasses.Category;
import nl.han.ica.veiligveilen.server.model.dataclasses.Lot;

public record AuctionResponse(UUID auctionId, String auctionName, Category category,
				String auctionType, Date startDate, Date endDate, List<Lot> lots) {

}
