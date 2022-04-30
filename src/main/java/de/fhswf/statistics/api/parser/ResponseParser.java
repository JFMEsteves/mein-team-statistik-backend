package de.fhswf.statistics.api.parser;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotNull;

/**
 * Ein Interface, das einen einfachen Response-Parser beschreibt, welcher gegebene Daten im
 * JSON-Format interpretiert und in den geforderten Typen überführt.
 *
 * @param <T> Ergebnis Objekt-Typ.
 */
public interface ResponseParser<T> {

    /**
     * Verarbeite die JSON-Daten zu einer Instanz der Ziel-Objekt-Klasse.
     *
     * @param data Quelldaten.
     * @return Instanz der Ergebnis-Klasse.
     * @throws ParsingException geworfen, wenn beim Verarbeiten ein Fehler auftritt.
     */
    @NotNull
    T parse(@NotNull JsonObject data) throws ParsingException;

}
