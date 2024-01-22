package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.Data;
import io.javalin.Javalin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import io.javalin.testtools.JavalinTest;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    Javalin app;

    @BeforeEach
    public final void setUp() throws SQLException, IOException {
        var hikariConfig = new HikariConfig();
        var jdbcUrl = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;";
        hikariConfig.setJdbcUrl(jdbcUrl);

        String sql = Data.readResourceFile("schema.sql");

        var dataSource = new HikariDataSource(hikariConfig);

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        BaseRepository.dataSource = dataSource;
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
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
        var date = new Date();
        Url url = new Url("www.example.com", new Timestamp(date.getTime()));
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
}
