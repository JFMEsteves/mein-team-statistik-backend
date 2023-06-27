package de.fhswf.statistics.api.parser;

import de.fhswf.statistics.model.SpielSpieler;
import de.fhswf.statistics.model.SpielSpielerPK;
import jakarta.json.JsonObject;

/**
 * Parser für einzelne SpielSpieler Objekte.
 */
public class StatParser implements ResponseParser<SpielSpieler> {
    @Override
    public SpielSpieler parse(JsonObject data) throws ParsingException {
        try {
            SpielSpielerPK spielSpielerPK = new SpielSpielerPK(data.getInt("spielerId"), data.getInt("spielId"));
            SpielSpieler stat = new SpielSpieler(spielSpielerPK);
            stat.setPunkte(data.getInt("punkte"));
            stat.setGetroffeneFreiwuerfe(data.getInt("getroffeneFreiwuerfe"));
            stat.setGeworfeneFreiwuerfe(data.getInt("geworfeneFreiwuerfe"));
            stat.setDreiPunkteTreffer(data.getInt("dreiPunkteTreffer"));
            stat.setFouls(data.getInt("fouls"));

            // Parse generic question details
            // JSON.get(...) >> Mandatory field; throws exception when missing
            // JSON.opt(...) >> Optional field with default/fallback value


            return stat;
        } catch (Exception e) {
            throw new ParsingException("Parsing Statistik failed.", e);
        }
    }
}
