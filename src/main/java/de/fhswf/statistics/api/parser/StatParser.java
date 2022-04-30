package de.fhswf.statistics.api.parser;

import de.fhswf.statistics.model.SpielSpieler;
import de.fhswf.statistics.model.SpielSpielerPK;
import jakarta.json.JsonObject;

/**
 * Parser für einzelne Fragen.
 */
public class StatParser implements ResponseParser<SpielSpieler> {
    @Override
    public SpielSpieler parse(JsonObject data) throws ParsingException {
        try {
            //TODO Richtiger nutzen von getInt ? | Wie mit PK ID umgehen ?
            SpielSpielerPK spielSpielerPK = new SpielSpielerPK(data.getInt("spielerId"), data.getInt("spielId"));
            SpielSpieler stat = new SpielSpieler(spielSpielerPK);
            //SpielSpieler stat = new SpielSpieler();
            //stat.getSpielSpielerPK().setSpielerId(data.getInt("spielerId"));
            stat.setPunkte(data.getInt("punkte"));
            stat.setGeworfeneFreiwuerfe(data.getInt("geworfeneFreiwuerfe"));
            stat.setGetroffeneFreiwuerfe(data.getInt("getroffeneFreiwuerfe"));
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
