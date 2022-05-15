package de.fhswf.statistics.api;

import de.fhswf.statistics.api.parser.SpielParser;
import de.fhswf.statistics.db.SpielService;
import de.fhswf.statistics.model.Spiel;
import de.fhswf.statistics.util.ApiResponseWriter;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(
        name = "Add Spiel",
        description = "Nimmt ein Neues Spiel entgegen.",
        urlPatterns = "/api/spiel/add"
)
public class AddGameServlet extends HttpServlet {

    @Inject
    SpielService spielService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // JSON Request-Body
            JsonObject params = Json.createReader(req.getReader()).readObject();

            //Parse Spiel
            System.out.println("Start des Parsings");
            Spiel spiel = new SpielParser().parse(params.getJsonObject("spiel"));


            if (params.containsKey("update")) {
                Spiel current = spielService.findById(params.getInt("update"));
                spielService.remove(current);
                spiel.setId(current.getId());
            }


            spiel = spielService.save(spiel);

            // Update survey
            spielService.update(spiel);

            // Gebe das neue Spiel als Antwort zurück
            ApiResponseWriter.writePositiveResponse(resp, spiel.toJson());
        } catch (JsonException e) {
            ApiResponseWriter.writeNegativeResponse(resp, "Malformed request body!");
        } catch (Exception e) {
            ApiResponseWriter.writeNegativeResponse(resp, e);
        }
    }
}
