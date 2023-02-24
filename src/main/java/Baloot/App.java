package Baloot;

import Baloot.Commands.UsernameValidation;
import Baloot.Exception.InvalidCommand;
import Baloot.Validation.Username;
import com.google.gson.Gson;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class App {
    static Set<Class<?>> handlers;

    public static void main(String[] args) {
        boolean exit = false;
        Reflections reflections = new Reflections("Baloot");
        handlers = reflections.getTypesAnnotatedWith(Route.class);
        while (!exit) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//                String cmd = System.console().readLine();
                String cmd = reader.readLine();
                handle(cmd);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
    private static void validate(Object model) throws Exception {
        Field[] fields = model.getClass().getFields();
        for (Field field : fields) {

            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == Username.class && !UsernameValidation.userValidation((String) field.get(model))) {
                    throw new InvalidCommand();
                }
            }
        }
    }
    private static void handle(String cmd) {
        String[] segments = cmd.split(" ", 2);
        ResponseModel resposne = new ResponseModel();
        Gson gson = new Gson();

        resposne.success = false;
        resposne.data = new InvalidCommand().getMessage();

        for (Class<?> handler : handlers) {
            Route annotation = handler.getAnnotation(Route.class);
            if (!annotation.value().equals(segments[0])) {
                continue;
            }
            try {
                String data = segments.length > 1 ? segments[1] : "";
                Object result = CallMethod(handler, RequestMethod.GET, data);
                // { success : true , result }
                resposne.success = true;
                resposne.data = result;
                break;
            } catch (InvocationTargetException e) {
                // { success : false, error }
                resposne.success = false;
                resposne.data = e.getTargetException().getMessage();
                break;
            } catch (Exception e) {
            }
        }

        System.out.println(gson.toJson(resposne));
    }

    private static Object CallMethod(Class<?> handler, RequestMethod requestMethod, String body) throws Exception {
        Method[] methods = handler.getMethods();
        Object result = null;

        for (Method method : methods) {

            if (!method.isAnnotationPresent(AcceptMethod.class))
                continue;

            AcceptMethod annotation = method.getAnnotation(AcceptMethod.class);

            if (annotation.value().equals(requestMethod)) {
                Object instance = handler.getDeclaredConstructor().newInstance();
                Gson gson = new Gson();

                if (method.getParameterCount() == 0) {
                    result = method.invoke(instance);
                } else {
                    Class<?> parameterType = method.getParameterTypes()[0];
                    Object data = gson.fromJson(body, parameterType);
                    validate(data);
                    result = method.invoke(instance, data);
                }
            }
        }

        return result;
    }
}
