package de.fhswf.statistics.api.parser;

import de.fhswf.statistics.model.Spiel;
import de.fhswf.statistics.model.SpielSpieler;
import de.fhswf.statistics.model.Spieldetails;
import de.fhswf.statistics.util.DateConverter;
import jakarta.json.JsonArray;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotNull;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Parser für ein einzelnes Spiel.
 *
 * @see StatParser Stats werden mit einem anderen Parser verarbeitet.
 * @see DetailsParser Spieldetails werden mit einem anderen Parser verarbeitet
 */
public class SpielParser implements ResponseParser<Spiel> {

    @NotNull
    @Override
    public Spiel parse(@NotNull JsonObject data) throws ParsingException {

        LocalDate date = null;
        try {
            date = DateConverter.StringtoDate(data.getString("datum"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Spiel spiel = new Spiel(data.getInt("id"), date);
        spiel.setName(data.getString("name"));
        spiel.setGegnerPunkte(data.getInt("gegnerPunkte"));
        spiel.setUnserePunkte(data.getInt("unserePunkte"));
        try {
            JsonArray arrayViertel = data.getJsonArray("viertel");
            Set<Spieldetails> details = new LinkedHashSet<>();

            DetailsParser detailsParser = new DetailsParser();
            for (int i = 0; i < arrayViertel.size(); i++) {
                details.add(detailsParser.parse(arrayViertel.getJsonObject(i)));
                System.out.println(arrayViertel.getJsonObject(i));
            }
            spiel.setSpieldetails(details);
            } catch (JsonException e) {
            throw new ParsingException("Unable to read Spieldetails.", e);
        }


        try {
            JsonArray array = data.getJsonArray("stats");

            Set<SpielSpieler> stats = new LinkedHashSet<>();
            StatParser parser = new StatParser();

            for (int i = 0; i < array.size(); i++) {
                stats.add(parser.parse(array.getJsonObject(i)));
            }

            spiel.setStats(stats);
        } catch (JsonException e) {
            throw new ParsingException("Unable to read stats.", e);
        }


        return spiel;
    }
}
