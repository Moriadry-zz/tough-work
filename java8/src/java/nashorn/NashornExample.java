package nashorn;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by moriatry on 11/10/2015.
 */
public class NashornExample {

    public static void main(String[] args) throws ScriptException, FileNotFoundException, NoSuchMethodException {
        /**
         * Java's built-in javascript engine.
         * Calling script manager object
         * Using engine object to interact with Javacript Interpreter
         */
        ScriptEngineManager engineManager = new ScriptEngineManager();

        ScriptEngine engine = engineManager.getEngineByName("nashorn");

        engine.eval("print('Hello World!');");


        /**
         * Invoking Javascript Functions from Java
         */
        engine.eval(new FileReader("/Users/kanke/java8Features/src/test.js"));


        /**
         * In order to call a function you first have to cast the script engine to Invocable. The Invocable interface is implemented by the NashornScriptEngine implementation and defines a method invokeFunction to call a javascript function for a given name.
         */
        Invocable invocable = (Invocable) engine;

        Object result = invocable.invokeFunction("message", "kanke");
        System.out.println(result);
        System.out.println(result.getClass());



    }


    /**
     *Invoking Java Methods from Javascript
     */
    static String message(String name) {
        System.out.format("Hi" + name);
        return "greetings from inside java method";
    }

}
