package de.fhswf.statistics.api;

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
        name = "Get Spiel",
        description = "Gibt ein Spiel mit allen Statistiken aus.",
        urlPatterns = "/api/spiel/details"
)
public class GetSpielServlet extends HttpServlet {

    @Inject
    SpielService spielService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // Parse request body
            JsonObject params = Json.createReader(req.getReader()).readObject();
            int id = params.getInt("id");
            if(id == 0) {
                throw new ApiException("Parameter 'id' missing or empty!");
            }

            // Get spieler
            Spiel spiel = spielService.findById(id);
            ApiResponseWriter.writePositiveResponse(resp, spiel.toJson());
        }catch (JsonException e) {
            ApiResponseWriter.writeNegativeResponse(resp, "Malformed request body!");
        } catch (ApiException e) {
            ApiResponseWriter.writeNegativeResponse(resp, e);
        }
    }
}
