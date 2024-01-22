package hexlet.code.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.CheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CheckController {
    public static void createCheck(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(urlId)
                .orElseThrow(() -> new NotFoundResponse("url with id = " + urlId + " not found"));
        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            var status = response.getStatus();
            Document doc = Jsoup.parse(response.getBody());
            var title = doc.getElementsByTag("title").isEmpty()
                    ? "" : doc.getElementsByTag("title").html();
            var h1 = doc.getElementsByTag("h1").isEmpty()
                    ? "" : doc.getElementsByTag("h1").html();
            Elements metaTags = doc.getElementsByAttributeValue("name", "description");
            String description = metaTags.isEmpty() ? "" : metaTags.get(0).attr("content");

            var createdAt = Timestamp.valueOf(LocalDateTime.now());
            var urlCheck = new UrlCheck(status, title, h1, description, urlId, createdAt);

            CheckRepository.save(urlCheck);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ctx.redirect(NamedRoutes.urlPath(urlId));
    }
}
