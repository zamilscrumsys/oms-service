package scripts;
public class ScriptConstants {

    public static final String JS_RULE = """
function execute(data) {
    let totalScore = 0;

    if (Array.isArray(data.scores) && data.scores.length > 0) {
        totalScore = data.scores.reduce((a, b) => a + b, 0);
    }

    return {
        valid:
            data.age >= 18 &&
            typeof data.salary === "number" &&
            data.salary > 30000 &&
            /@gmail\\.com$/.test(data.email) &&
            (data.country === "IN" || data.country === "US"),

        grade: data.age >= 25 ? "A" : "B",
        totalScore: totalScore
    };
}
""";

    public static final String PY_RULE = """
def execute(data):
    try:
        age = data.get("age")
        if age is None:
            age = 0
    except:
        age = 0

    try:
        salary = data.get("salary")
        if salary is None:
            salary = 0
    except:
        salary = 0

    try:
        email = str(data.get("email"))
    except:
        email = ""

    try:
        country = data.get("country")
    except:
        country = ""

    try:
        scores = data.get("scores")
    except:
        scores = []

    total_score = 0
    try:
        total_score = sum(list(scores))
    except:
        total_score = 0

    return {
        "valid": (
            age >= 18 and
            salary > 30000 and
            email.endswith("@gmail.com") and
            (country == "IN" or country == "US")
        ),
        "grade": "A" if age >= 25 else "B",
        "totalScore": total_score
    }
""";


}

