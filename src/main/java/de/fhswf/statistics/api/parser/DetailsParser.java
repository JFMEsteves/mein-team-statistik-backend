package de.fhswf.statistics.api.parser;

import de.fhswf.statistics.model.Spieldetails;
import de.fhswf.statistics.model.SpieldetailsId;
import jakarta.json.JsonObject;

/**
 * Parser für einzelne Spieldetails Objekte.
 *
 * @see SpielParser ruft diesen Parser auf, um die Spieldetails zu verarbeiten.
 */
public class DetailsParser implements ResponseParser<Spieldetails> {
    @Override
    public Spieldetails parse(JsonObject data) throws ParsingException {
        try
        {
            SpieldetailsId spieldetailsId = new SpieldetailsId(data.getInt("detailsId"), data.getInt("enemy"));
            Spieldetails details = new Spieldetails(spieldetailsId);
            details.setViertel1(data.getInt("viertel1"));
            details.setViertel2(data.getInt("viertel2"));
            details.setViertel3(data.getInt("viertel3"));
            details.setViertel4(data.getInt("viertel4"));

            // Parse generic question details
            // JSON.get(...) >> Mandatory field; throws exception when missing
            // JSON.opt(...) >> Optional field with default/fallback value


            return details;
        } catch (Exception e) {
            throw new ParsingException("ParsingStatistik failed.", e);
        }
    }
}
