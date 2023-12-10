package com.myserv.api.rh.configfile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Getter
@Setter
@Component("imageStorageProperties")
@ConfigurationProperties(prefix = "image")
public class ImageStorageProperties {
    private String ImageDir;


}
