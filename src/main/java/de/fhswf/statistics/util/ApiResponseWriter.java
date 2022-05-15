package de.fhswf.statistics.util;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;

/**
 * Utility-Klasse, mit der einheitliche API-Antworten geschrieben werden können.
 */
public final class ApiResponseWriter {

    private ApiResponseWriter() {
    }

    public static void writePositiveResponse(@NotNull HttpServletResponse response, JsonObject data)
            throws IOException {
        JsonObjectBuilder result = Json.createObjectBuilder();
        result.add("ok", true);
        if (data != null) result.add("data", data);
        response.getWriter().write(PrettyJson.printObject(result.build()));
    }

    public static void writePositiveResponse(@NotNull HttpServletResponse response)
            throws IOException {
        writePositiveResponse(response, null);
    }

    public static void writeNegativeResponse(@NotNull HttpServletResponse response, String message)
            throws IOException {
        response.getWriter().write(PrettyJson.printObject(
                Json.createObjectBuilder()
                        .add("ok", false)
                        .add("error_message", message)
                        .build()
        ));
    }

    public static void writeNegativeResponse(@NotNull HttpServletResponse response, @NotNull Throwable e)
            throws IOException {
        writeNegativeResponse(response, e.getMessage());
    }

    public static void writeNegativeResponse(@NotNull HttpServletResponse response) throws IOException {
        writeNegativeResponse(response, "Unknown API error.");
    }

}
