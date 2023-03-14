package Baloot;

import Baloot.Commands.UsernameValidation;
import Baloot.Context.ContextManager;
import Baloot.Exception.HttpException;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    static Set<Class<?>> handlers;

    public static void main(String[] args) {
        boolean exit = false;
        Reflections reflections = new Reflections("Baloot");
        ContextManager.initialize();

        Javalin app = Javalin.create();
        Set<Class<?>> handlers = reflections.getTypesAnnotatedWith(RouteContainer.class);
        handlers.addAll(reflections.getTypesAnnotatedWith(Route.class));
        for (Class<?> handler : handlers) {
            Route[] routes = handler.getAnnotationsByType(Route.class);
            for (Route route : routes) {
                app.get(route.value(), new RequestHandler(handler));
                app.post(route.value(), new RequestHandler(handler));
            }
            app.error(404, ctx -> {
                ctx.html(getCodeResponse(404));
            });
            app.error(403, ctx -> {
                ctx.html(getCodeResponse(403));
            });
        }
        app.start(7070);
    }
    public static String getCodeResponse(Integer code) throws Exception {
        Path path = Paths.get("src/main/resources/templates", code + ".html");
        return Files.readString(path);
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

            String body = gson.toJson(bind(context));
            try {
                Object response = CallMethod(command, RequestMethod.valueOf(context.req.getMethod()), body);
                context.res.setStatus(200);

                if (response instanceof Component) {
                    Component component = (Component) response;
                    String output = component.render();
                    context.html(output);
                }
                else{
                    context.html(App.getCodeResponse(200));
                }
            } catch (HttpException e) {
                context.res.setStatus(e.getStatus());
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
        private static void AddAll(Map<String, Object> map, Map<String, List<String>> data) {
            if (data == null)
                return;
            for (Map.Entry<String, List<String>> entry : data.entrySet()) {
                if (entry.getValue().size() == 1) {
                    map.put(entry.getKey(), entry.getValue().get(0));
                } else {
                    map.put(entry.getKey(), entry.getValue());
                }
            }
        }
        private static Map<String, Object> bind(io.javalin.http.Context context) {
            Map<String, Object> params = new HashMap<>();
            AddAll(params, context.formParamMap());
            AddAll(params, context.queryParamMap());
            params.putAll(context.pathParamMap());
            return params;
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
