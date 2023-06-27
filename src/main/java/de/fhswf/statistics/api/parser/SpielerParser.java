package de.fhswf.statistics.api.parser;

import de.fhswf.statistics.model.SpielSpieler;
import de.fhswf.statistics.model.Spieler;
import jakarta.json.JsonArray;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Parser für einen einzelnen Spieler.
 *
 * @see StatParser Stats werden mit einem anderen Parser verarbeitet.
 */
public class SpielerParser implements ResponseParser<Spieler> {

    @NotNull
    @Override
    public Spieler parse(@NotNull JsonObject data) throws ParsingException {
        //Allgemeine Daten
        Spieler spieler = new Spieler(data.getInt("id"),
                data.getString("name"));

        // Fragen sind optional
        try {
            JsonArray array = data.getJsonArray("stats");

            Set<SpielSpieler> stats = new LinkedHashSet<>();
            StatParser parser = new StatParser();

            for (int i = 0; i < array.size(); i++) {
                stats.add(parser.parse(array.getJsonObject(i)));
            }

            spieler.setStats(stats);

        } catch (JsonException e) {
            throw new ParsingException("Unable to read stats.", e);
        }

        return spieler;
    }
}
