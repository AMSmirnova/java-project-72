package hexlet.code.controller;

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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.SQLException;


public class CheckController {
    public static void createCheck(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(urlId)
                .orElseThrow(() -> new NotFoundResponse("url with id = " + urlId + " not found"));
        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            var status = response.getStatus();
            Document document = Jsoup.parse(response.getBody());
            String title = document.title();

            Element h1Element = document.selectFirst("h1");
            var h1 = h1Element != null ? h1Element.text() : "";

            Elements metaTags = document.getElementsByAttributeValue("name", "description");
            String description = metaTags.isEmpty() ? "" : metaTags.get(0).attr("content");

            var urlCheck = new UrlCheck(status, title, h1, description, urlId);

            CheckRepository.save(urlCheck);

            ctx.sessionAttribute("flash", "Проверка успешно добавлена");
            ctx.sessionAttribute("flash-type", "success");

        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "danger");
        }
        ctx.redirect(NamedRoutes.urlPath(urlId));
    }
}
