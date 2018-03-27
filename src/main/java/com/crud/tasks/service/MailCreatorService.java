package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    TaskRepository taskRepository;

    @AllArgsConstructor
    @Getter
    private class LinkInfo {
        private String link;
        private String caption;
    }

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/old");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("company_address", adminConfig.getCompanyAddress());
        context.setVariable("goodbye_message_text", "Yours truly,");
        context.setVariable("goodbye_message_signature", "CRUD App team");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);

    }

    public String buildTaskCountEmail(long taskCount) {
        List<LinkInfo> links = new ArrayList<>();
        links.add(new LinkInfo("http://localhost:8888/tasks_frontend/old", "Application"));
        links.add(new LinkInfo("http://localhost:8080/swagger-ui.html", "API documentation"));
        links.add(new LinkInfo("http://localhost:8080/application/health", "App status info"));

        Context context = new Context();
        context.setVariable("task_count", taskRepository.count());
        context.setVariable("link_list", links);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("goodbye_message_text", "Yours truly,");
        context.setVariable("goodbye_message_signature", "CRUD App team");
        return templateEngine.process("mail/number-of-tasks-mail", context);
    }
}
