package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Url {
    private Long id;
    private String name;
    private Timestamp createdAt;
    private List<UrlCheck> urlChecks = new ArrayList<>();
    private UrlCheck lastUrlCheck;

    public Url(String name) {
        this.name = name;
    }
}
