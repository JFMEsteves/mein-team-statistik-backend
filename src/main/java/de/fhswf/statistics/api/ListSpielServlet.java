package de.fhswf.statistics.api;

import de.fhswf.statistics.db.SpielService;
import de.fhswf.statistics.util.ApiResponseWriter;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name = "List Spiele",
        urlPatterns = "/api/spiel/list",
        description = "Gibt eine Liste (Übersicht) aller Spiele aus."
)
public class ListSpielServlet extends HttpServlet {

    @Inject
    SpielService spielService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // List surveys
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        spielService.findAll().forEach(s -> arrayBuilder.add(s.toJson(true)));

        JsonObject result = Json.createObjectBuilder()
                .add("spiel", arrayBuilder)
                .build();

        ApiResponseWriter.writePositiveResponse(resp, result);
    }
}
