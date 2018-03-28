package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {

    @Value("${application.backend.url.base}")
    private String backendBaseUrl;

    @Value("${application.frontend.url.base}")
    private String frontendBaseUrl;

    @Value("${admin.mail}")
    private String adminMail;

    @Value("${admin.name}")
    private String adminName;

    @Value("${company.name}")
    private String companyName;

    @Value("${company.address}")
    private String companyAddress;

}
