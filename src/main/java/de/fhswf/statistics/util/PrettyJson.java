package de.fhswf.statistics.util;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;
import jakarta.json.JsonWriterFactory;
import jakarta.json.stream.JsonGenerator;
import jakarta.validation.constraints.NotNull;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility-Klasse, die Methoden zur Verfügung stellt, um Jakarta JSON-Objekte als leserlichen String
 * mit Einrückungen auszugeben.
 */
public final class PrettyJson {

    private PrettyJson() {
    }

    /**
     * Gebe das JSON-Objekt als formatierten String aus.
     *
     * @param object Ziel-Objekt.
     * @return JSON-Inhalte als String.
     */
    @NotNull
    public static String printObject(@NotNull JsonObject object) {
        // Use JSON-Writer to generate a formatted response
        // I. Configuration for the writer
        Map<String, Boolean> writerConfig = new HashMap<>();
        writerConfig.put(JsonGenerator.PRETTY_PRINTING, true);

        // II. Get a writer
        StringWriter stringWriter = new StringWriter();
        JsonWriterFactory writerFactory = Json.createWriterFactory(writerConfig);
        JsonWriter writer = writerFactory.createWriter(stringWriter);

        // III. Write to StringBuffer
        writer.write(object);
        writer.close();

        return stringWriter.toString();
    }

}
