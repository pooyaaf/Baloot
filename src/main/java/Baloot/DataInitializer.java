package Baloot;

import Baloot.Context.ContextManager;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class DataInitializer {
    @PostConstruct
    public void populateCourses(){

        ContextManager.getInstance().initialize();
    }
}
