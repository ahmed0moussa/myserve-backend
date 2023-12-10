package com.myserv.api.rh.configfile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("lettreStorageProperties")
@ConfigurationProperties(prefix = "lettre")

public class LettreStorageProperties {
    private String uploadLettreDir;
    public String getLettreDir() {
        return uploadLettreDir;
    }

    public void setLettreDir(String uploadLettreDir) {
        this.uploadLettreDir = uploadLettreDir;
    }


}
