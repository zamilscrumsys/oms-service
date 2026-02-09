package RuleEngine;
import org.graalvm.polyglot.*;
import scripts.ScriptConstants;

import javax.script.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
public class RuleEngineService {

    /* 1️⃣ Graal JS – String */
    public Map<String, Object> graalJsString(Map<String, Object> input) {
        Context ctx = Context.newBuilder("js")
                .allowAllAccess(true)
                .build();
        System.out.println("Entering into GraaljsString");
        ctx.eval("js", ScriptConstants.JS_RULE);
        Value function = ctx.getBindings("js").getMember("execute");
        Value result = function.execute(input);
        return result.as(Map.class);
    }

    /* 2️⃣ Graal Python – String */
    public Map<String, Object> graalPythonString(Map<String, Object> input) {
        Context ctx = Context.newBuilder("python")
                .allowAllAccess(true)
                .build();
            ctx.eval("python", ScriptConstants.PY_RULE);
            System.out.println("Entered GraalPython String ");
            return ctx.getBindings("python").getMember("execute").execute(input).as(Map.class);

    }

    /* 3️⃣ Interpreter JS – String */
    public Object interpreterJsString(Map<String, Object> input) throws Exception {
        ScriptEngine js = new ScriptEngineManager().getEngineByName("nashorn");
        js.eval(ScriptConstants.JS_RULE);
        return ((Invocable) js).invokeFunction("execute", input);
    }

    /* 4️⃣ Interpreter Python – String */
    public Object interpreterPythonString(Map<String, Object> input) {
        org.python.util.PythonInterpreter py = new org.python.util.PythonInterpreter();
        System.out.println("Entering into interpreterPythonString..");
        py.exec(ScriptConstants.PY_RULE);
        py.set("java_input", input);
        return py.eval("execute(java_input)");
    }


    /* 5️⃣ Graal JS – File */
    public Map<String, Object> graalJsFile(Map<String, Object> input) throws Exception {
        Context ctx = Context.newBuilder("js")
                .allowAllAccess(true)
                .build();

        System.out.println("Entering into GraaljsFile");

        String jsCode = Files.readString(
                Path.of("src/main/resources/RuleScript.js")
        );
        ctx.eval("js", jsCode);
        return ctx.getBindings("js").getMember("execute").execute(input).as(Map.class);

    }

    /* 6️⃣ Graal Python – File */
    public Map<String, Object> graalPythonFile(Map<String, Object> input) throws Exception {


        Context ctx = Context.newBuilder("python")
                .allowAllAccess(true)
                .build();

        System.out.println("Entering into Graalpythonfile");

        String pyCode = Files.readString(
                Path.of("src/main/resources/RuleScript.py")
        );
        ctx.eval("python", pyCode);
        return ctx.getBindings("python").getMember("execute").execute(input).as(Map.class);

    }



    /* 7️⃣ Interpreter Python – File */
    public Object interpreterPythonFile(Map<String, Object> input) {
        org.python.util.PythonInterpreter py =
                new org.python.util.PythonInterpreter();

        System.out.println("Entering into interpreterPythonFile.. 7");

        // Load Python file directly
        py.execfile("src/main/resources/RuleScript.py");

        // Pass Java Map
        py.set("java_input", input);

        // Call execute(data)
        return py.eval("execute(java_input)");
    }


    /* 8️⃣ Interpreter JS – File */
    public Object interpreterJsFile(Map<String, Object> input) throws Exception {

        ScriptEngine js =
                new ScriptEngineManager().getEngineByName("nashorn");

        System.out.println("Entering into interpreterJsFile..");

        // Read JS file
        String jsCode = Files.readString(
                Path.of("src/main/resources/RuleScript.js")
        );

        js.eval(jsCode);

        return ((Invocable) js).invokeFunction("execute", input);
    }

}
