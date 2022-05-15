package de.fhswf.statistics.api;

import de.fhswf.statistics.db.SpielerService;
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
        name = "List Spieler",
        urlPatterns = "/api/spieler/list",
        description = "Gibt eine Liste (Übersicht) aller Spieler aus."
)
public class ListSpielerServlet extends HttpServlet {

    @Inject
    SpielerService spielerService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // List spieler
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        spielerService.findAll().forEach(s -> arrayBuilder.add(s.toJson(true)));

        JsonObject result = Json.createObjectBuilder()
                .add("kader", arrayBuilder)
                .build();

        ApiResponseWriter.writePositiveResponse(resp, result);
    }
}
