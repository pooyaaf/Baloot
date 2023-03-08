package Baloot;

import Baloot.Commands.UsernameValidation;
import Baloot.Context.ContextManager;
import Baloot.Exception.InvalidCommand;
import Baloot.Model.view.Component;
import Baloot.Validation.Username;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
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
        Javalin app = Javalin.create();

        Set<Class<?>> handlers = reflections.getTypesAnnotatedWith(Route.class);
        for (Class<?> handler : handlers) {
            Route route = handler.getAnnotation(Route.class);
            app.addHandler(HandlerType.GET, route.value(), (Handler) new RequestHandler(handler));
        }

        app.start(7070);
    }
}

    class RequestHandler implements Handler {
        private Class<?> command;

        public RequestHandler(Class<?> _command) {
            command = _command;
        }

        @Override
        public void handle(io.javalin.http.Context context) throws java.lang.Exception {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .serializeNulls()
                    .create();

            String body = gson.toJson(context.pathParamMap());
            Object response = CallMethod(command, RequestMethod.valueOf(context.req.getMethod()), body);

            if(response instanceof Component){
                Component component = (Component)response;
                String output = component.render();
                context.res.setStatus(200);
                context.html(output);
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
