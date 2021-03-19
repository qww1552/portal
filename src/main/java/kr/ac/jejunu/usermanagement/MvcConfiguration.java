package kr.ac.jejunu.usermanagement;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/list");
        registry.addViewController("/create").setViewName("edit");
        registry.addViewController("/edit");
    }
}
