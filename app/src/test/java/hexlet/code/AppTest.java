package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.CheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    Javalin app;
    static MockWebServer mockWebServererver;

    public static String readFixture(String pathString) throws Exception {
        Path path = Path.of(pathString);
        path.toAbsolutePath().normalize();

        return Files.readString(path).trim();
    }

    @BeforeAll
    public static void beforeAll() throws Exception {
        mockWebServererver = new MockWebServer();
        MockResponse mockResponse = new MockResponse().setBody(readFixture("src/test/resources/index.html"));
        mockWebServererver.enqueue(mockResponse);
        mockWebServererver.start();
    }


    @BeforeEach
    public final void setUp() throws Exception {
        app = App.getApp();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServererver.shutdown();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body()).isNotNull();
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=www.example.com";
            var response = client.post("/urls/", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("www.example.com");
        });
    }

    @Test
    public void addSameUrl() throws SQLException {
        Url url = new Url("www.example.com");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=www.example.com";
            var response = client.post("/urls/", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(UrlRepository.getEntities()).hasSize(1);
        });
    }

    @Test
    public void addIncorrectUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=example";
            var response = client.post("/urls/", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(UrlRepository.getEntities()).hasSize(0);
        });
    }

    @Test
    public void testNoExistPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls" + 333L);
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testAddChecks() throws SQLException {
        String textUrl = mockWebServererver.url("/").toString();
        var url = new Url(textUrl);
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls/" + url.getId() + "/checks");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("test page for example");

            UrlCheck check = CheckRepository.findLatestCheck(url.getId()).orElse(null);
            assertThat(check).isNotNull();
            assertThat(check.getStatusCode()).isEqualTo(200);
            assertThat(check.getH1()).isEqualTo("Example Page");
            assertThat(check.getDescription()).isEqualTo("test page for example");
            assertThat(check.getTitle()).isEqualTo("Example");
        });
    }
}
