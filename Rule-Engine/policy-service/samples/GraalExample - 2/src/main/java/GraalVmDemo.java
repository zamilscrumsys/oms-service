import RuleEngine.RuleEngineService;
import util.RuleInput;



import java.util.List;
import java.util.Map;




public class GraalVmDemo {

    public static void main(String[] args) throws Exception {

        RuleEngineService engine = new RuleEngineService();
        var input = RuleInput.build();

        System.out.println(engine.graalJsString(input));
        System.out.println(engine.graalPythonString(input));
        System.out.println(engine.interpreterPythonString(input));
        System.out.println(engine.graalJsFile(input));
        System.out.println(engine.graalPythonFile(input));

        System.out.println(engine.interpreterPythonFile(input));


        //for interpretor JS in string and file getting error so commented
        //System.out.println(engine.interpreterJsString(input));
        System.out.println(engine.interpreterJsFile(input));
    }


    }


