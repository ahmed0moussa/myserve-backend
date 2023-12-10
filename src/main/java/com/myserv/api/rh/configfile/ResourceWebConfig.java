package com.myserv.api.rh.configfile;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceWebConfig implements WebMvcConfigurer {
    final Environment environment;

    public ResourceWebConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        String cvLocation = environment.getProperty("app.file.storage.mapping.cv");
        String lettreLocation = environment.getProperty("app.file.storage.mapping.lettre");
        String imageLocation = environment.getProperty("app.file.storage.mapping.Image");
        String compteRenduLocation = environment.getProperty("app.file.storage.mapping.compteRendu");


        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(cvLocation)
                .addResourceLocations(lettreLocation)
                .addResourceLocations(imageLocation)
                .addResourceLocations(compteRenduLocation);


    }
}
