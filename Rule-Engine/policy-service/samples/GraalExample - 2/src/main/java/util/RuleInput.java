package util;

import java.util.List;
import java.util.Map;

public class RuleInput {

    public static Map<String, Object> build() {
        return Map.of(
                "age", 25,
                "name", "Mohamed",
                "email", "mohamed@gmail.com",
                "salary", 55000,
                "scores", List.of(80, 90, 70),
                "country", "IN"
        );
    }
}
