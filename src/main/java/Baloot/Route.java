package Baloot;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RouteContainer.class)
@Target(ElementType.TYPE)
public @interface Route {
    String value();
}