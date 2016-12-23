package io.sato;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import io.sato.app.SatoApp;
import io.sato.plugin.Plugin;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.jruby.Ruby;
import org.jruby.RubyObject;
import org.jruby.embed.ScriptingContainer;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.builtin.IRubyObject;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyProxy;
import org.python.util.PythonInterpreter;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main2(String[] args) {
        SatoApp satoApp = SatoApp.getInstance();
    }

    public static void main(String[] args) throws Exception {
        SatoApp satoApp = SatoApp.getInstance();
//        main.testJavaPluginMethod();
//        main.testScalaPluginMethod();
//        main.testPythonPluginMethod();
//        main.testRubyPluginMethod();
//        main.testGroovyPluginMethod();
//        main.testJavaScriptPluginMethod();
    }

    private void testJavaScriptPluginMethod() {
        String response = "{\"error\":\"could not execute plugin\"}";

        String requestPluginName = "UserDBPlugin";
        String requestMethodName = "findLecturesOfUser";
        String requestParameterString = "[{\"name\":\"john\",\"number\":220033}]";
        List<TreeMap> requestParameters = new Genson().deserialize(requestParameterString, new GenericType<List<TreeMap>>() {
        });

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(requestPluginName + ".js");
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String content = scanner.next();

            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            engine.eval(content);
            Invocable invocable = (Invocable) engine;
            Object instance = invocable.invokeFunction("instance");

            List<Object> paramList = new ArrayList<>();
            if (requestParameters.size() > 0) {
                TreeMap parameters = requestParameters.get(0);
                for (Object value : parameters.values()) {
                    paramList.add(value);
                }
            }
            Object[] params = new Object[paramList.size()];
            params = paramList.toArray(params);
            Object result = ((ScriptObjectMirror) instance).callMember(requestMethodName, params);
            response = new Genson().serialize(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response = " + response);
    }

    private void testGroovyPluginMethod() {
        String response = "{\"error\":\"could not execute plugin\"}";

        String requestPluginName = "UserDBPlugin";
        String requestMethodName = "findLecturesOfUser";
        String requestParameterString = "[{\"name\":\"john\",\"number\":220033}]";
        List<TreeMap> requestParameters = new Genson().deserialize(requestParameterString, new GenericType<List<TreeMap>>() {
        });

        try {

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(requestPluginName + ".groovy");
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String content = scanner.next();
            GroovyClassLoader classLoader = new GroovyClassLoader();
            Class pluginClass = classLoader.parseClass(content);
            GroovyObject groovyObj = null;
            try {
                groovyObj = (GroovyObject) pluginClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            Plugin plugin = (Plugin) groovyObj;

            Object[] parametersArray = null;
            Method callMethod = null;
            for (Method method : pluginClass.getMethods()) {
                if (method.getName().equals(requestMethodName)
                        && method.getParameterCount() == requestParameters.size()) {
                    callMethod = method;
                    break;
                }
            }
            if (callMethod != null) {
                List<Object> parameterList = new ArrayList<>();
                Class<?>[] parameterTypes = callMethod.getParameterTypes();
                for (int i = 0; i < requestParameters.size(); i++) {
                    String parameterString = new Genson().serialize(requestParameters.get(i));
                    Object parameterObject = new Genson().deserialize(parameterString, parameterTypes[i]);
                    parameterList.add(parameterObject);
                }
                parametersArray = parameterList.toArray();
                Object output = callMethod.invoke(plugin, parametersArray);
                response = new Genson().serialize(output);
            } else if (requestParameters.size() == 1) {
                TreeMap map = requestParameters.get(0);
                for (Method method : pluginClass.getMethods()) {
                    if (method.getName().equals(requestMethodName)
                            && method.getParameterCount() == map.size()) {
                        callMethod = method;
                        break;
                    }
                }
                if (callMethod != null) {
                    List<Object> parameterList = new ArrayList<>();
                    Class<?>[] parameterTypes = callMethod.getParameterTypes();
                    int i = 0;
                    for (Object key : map.keySet()) {
                        String parameterString = new Genson().serialize(map.get(key));
                        Object parameterObject = new Genson().deserialize(parameterString, parameterTypes[i]);
                        parameterList.add(parameterObject);
                        i++;
                    }
                    parametersArray = parameterList.toArray();
                }
            }

            if (callMethod != null) {
                Object output = callMethod.invoke(plugin, parametersArray);
                response = new Genson().serialize(output);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response = " + response);
    }

    private void testRubyPluginMethod() throws InvocationTargetException, IllegalAccessException {
        String response = "{\"error\":\"could not execute plugin\"}";

        String requestPluginName = "UserDBPlugin";
        String requestMethodName = "findLecturesOfUser";
        String requestParameterString = "[{\"name\":\"john\",\"number\":220033}]";
        List<TreeMap> requestParameters = new Genson().deserialize(requestParameterString, new GenericType<List<TreeMap>>() {
        });

        try {
            ScriptingContainer container = new ScriptingContainer();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(requestPluginName + ".rb");
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String content = scanner.next();
            Object greeter = container.runScriptlet(content);
            Plugin plugin = (Plugin) greeter;

            List<IRubyObject> paramList = new ArrayList<>();
            if (requestParameters.size() > 0) {
                TreeMap parameters = requestParameters.get(0);
                for (Object value : parameters.values()) {
                    IRubyObject rubyObject = JavaUtil.convertJavaToRuby(Ruby.getGlobalRuntime(), value);
                    paramList.add(rubyObject);
                }
            }
            IRubyObject[] params = new IRubyObject[paramList.size()];
            params = paramList.toArray(params);

            IRubyObject result = ((RubyObject) plugin).callMethod(requestMethodName, params);
            response = new Genson().serialize(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response = " + response);
    }

    private void testPythonPluginMethod() {
        String response = "{\"error\":\"could not execute plugin\"}";

        String requestPluginName = "UserDBPlugin";
        String requestMethodName = "findLecturesOfUser";
        String requestParameterString = "[{\"name\":\"john\",\"number\":220033}]";
        List<TreeMap> requestParameters = new Genson().deserialize(requestParameterString, new GenericType<List<TreeMap>>() {
        });

        try {
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.exec("from " + requestPluginName + " import " + requestPluginName);
            PyObject pyPluginObject = interpreter.get(requestPluginName);
            PyObject pluginObject = pyPluginObject.__call__();
            Plugin plugin = (Plugin) pluginObject.__tojava__(Plugin.class);

            List<PyObject> paramList = new ArrayList<>();
            if (requestParameters.size() > 0) {
                TreeMap parameters = requestParameters.get(0);
                for (Object value : parameters.values()) {
                    PyObject param = Py.java2py(value);
                    paramList.add(param);
                }
            }
            PyObject[] params = new PyObject[paramList.size()];
            params = paramList.toArray(params);

            PyObject instance = ((PyProxy) plugin)._getPyInstance();
            PyObject result = instance.invoke(requestMethodName, params);
            response = new Genson().serialize(result);
        } catch (PyException e) {
            e.printStackTrace();
        }
        System.out.println("response = " + response);
    }

    private void testJavaPluginMethod() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String response = "{\"error\":\"could not execute plugin\"}";

        String requestPluginName = "io.sato.UserDBPlugin";
        String requestMethodName = "findLecturesOfUser";
        String requestParameterString = "[{\"name\":\"john\",\"number\":220033}]";

        List<TreeMap> requestParameters = new Genson().deserialize(requestParameterString, new GenericType<List<TreeMap>>() {
        });

        Object[] parametersArray = null;
        Method callMethod = null;
        Class<? extends Plugin> pluginClass = Class.forName(requestPluginName).asSubclass(Plugin.class);
        Plugin plugin = pluginClass.newInstance();
        for (Method method : pluginClass.getMethods()) {
            if (method.getName().equals(requestMethodName)
                    && method.getParameterCount() == requestParameters.size()) {
                callMethod = method;
                break;
            }
        }

        if (callMethod != null) {
            List<Object> parameterList = new ArrayList<>();
            Class<?>[] parameterTypes = callMethod.getParameterTypes();
            for (int i = 0; i < requestParameters.size(); i++) {
                String parameterString = new Genson().serialize(requestParameters.get(i));
                Object parameterObject = new Genson().deserialize(parameterString, parameterTypes[i]);
                parameterList.add(parameterObject);
            }
            parametersArray = parameterList.toArray();
            Object output = callMethod.invoke(plugin, parametersArray);
            response = new Genson().serialize(output);

        } else if (requestParameters.size() == 1) {
            TreeMap map = requestParameters.get(0);
            for (Method method : pluginClass.getMethods()) {
                if (method.getName().equals(requestMethodName)
                        && method.getParameterCount() == map.size()) {
                    callMethod = method;
                    break;
                }
            }
            if (callMethod != null) {
                List<Object> parameterList = new ArrayList<>();
                Class<?>[] parameterTypes = callMethod.getParameterTypes();
                int i = 0;
                for (Object key : map.keySet()) {
                    String parameterString = new Genson().serialize(map.get(key));
                    Object parameterObject = new Genson().deserialize(parameterString, parameterTypes[i]);
                    parameterList.add(parameterObject);
                    i++;
                }
                parametersArray = parameterList.toArray();
            }
        }

        if (callMethod != null) {
            Object output = callMethod.invoke(plugin, parametersArray);
            response = new Genson().serialize(output);
        }

        System.out.println("response = " + response);
    }

    private void testScalaPluginMethod() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String response = "{\"error\":\"could not execute plugin\"}";

        String requestPluginName = "io.sato.UserDBPluginScala";
        String requestMethodName = "findLecturesOfUser";
        String requestParameterString = "[{\"name\":\"john\",\"number\":220033}]";

        List<TreeMap> requestParameters = new Genson().deserialize(requestParameterString, new GenericType<List<TreeMap>>() {
        });

        Object[] parametersArray = null;
        Method callMethod = null;
        Class<? extends Plugin> pluginClass = Class.forName(requestPluginName).asSubclass(Plugin.class);
        Plugin plugin = pluginClass.newInstance();
        for (Method method : pluginClass.getMethods()) {
            if (method.getName().equals(requestMethodName)
                    && method.getParameterCount() == requestParameters.size()) {
                callMethod = method;
                break;
            }
        }

        if (callMethod != null) {
            List<Object> parameterList = new ArrayList<>();
            Class<?>[] parameterTypes = callMethod.getParameterTypes();
            for (int i = 0; i < requestParameters.size(); i++) {
                String parameterString = new Genson().serialize(requestParameters.get(i));
                Object parameterObject = new Genson().deserialize(parameterString, parameterTypes[i]);
                parameterList.add(parameterObject);
            }
            parametersArray = parameterList.toArray();
            Object output = callMethod.invoke(plugin, parametersArray);
            response = new Genson().serialize(output);

        } else if (requestParameters.size() == 1) {
            TreeMap map = requestParameters.get(0);
            for (Method method : pluginClass.getMethods()) {
                if (method.getName().equals(requestMethodName)
                        && method.getParameterCount() == map.size()) {
                    callMethod = method;
                    break;
                }
            }
            if (callMethod != null) {
                List<Object> parameterList = new ArrayList<>();
                Class<?>[] parameterTypes = callMethod.getParameterTypes();
                int i = 0;
                for (Object key : map.keySet()) {
                    String parameterString = new Genson().serialize(map.get(key));
                    Object parameterObject = new Genson().deserialize(parameterString, parameterTypes[i]);
                    parameterList.add(parameterObject);
                    i++;
                }
                parametersArray = parameterList.toArray();
            }
        }

        if (callMethod != null) {
            Object output = callMethod.invoke(plugin, parametersArray);
            response = new Genson().serialize(output);
        }

        System.out.println("response = " + response);
    }

}
