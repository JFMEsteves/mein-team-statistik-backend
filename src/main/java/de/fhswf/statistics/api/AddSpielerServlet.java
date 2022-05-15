package de.fhswf.statistics.api;

import de.fhswf.statistics.api.parser.SpielerParser;
import de.fhswf.statistics.db.SpielerService;
import de.fhswf.statistics.model.Spieler;
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
        name = "Add Spieler",
        description = "Nimmt einen neuen Spieler entgegen.",
        urlPatterns = "/api/spieler/add"
)
public class AddSpielerServlet extends HttpServlet {

    @Inject
    SpielerService spielerService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // JSON Request-Body
            JsonObject params = Json.createReader(req.getReader()).readObject();

            //Parse Spiel
            Spieler spieler = new SpielerParser().parse(params.getJsonObject("spieler"));

            if (params.containsKey("update")) {
                Spieler current = spielerService.findById(params.getInt("update"));
                spielerService.remove(current);
                spieler.setId(current.getId());
            }
            spieler = spielerService.save(spieler);
            // Update spieler
            spielerService.update(spieler);

            // Gebe das neue Spiel als Antwort zurück
            ApiResponseWriter.writePositiveResponse(resp, spieler.toJson());
        } catch (JsonException e) {
            ApiResponseWriter.writeNegativeResponse(resp, "Malformed request body!");
        } catch (Exception e) {
            ApiResponseWriter.writeNegativeResponse(resp, e);
        }
    }
}
