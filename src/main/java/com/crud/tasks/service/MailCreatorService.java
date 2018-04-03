package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Arrays;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @AllArgsConstructor
    @Getter
    private class LinkInfo {
        private String link;
        private String caption;
    }

    private final String BUTTON_CAPTION = "Visit website";

    private final List<String> FUNCTIONALITIES = Arrays.asList(
            "You can manage your tasks",
            "Provides connection with Trello account",
            "Application allows sending tasks to Trello");

    private static class EmailBuilder {
        private Context context = new Context();
        private final String GOODBYE_MESSAGE = "Yours truly,";
        private final String SIGNATURE = "CRUD App team";

        AdminConfig adminConfig;

        private TemplateEngine templateEngine;

        public EmailBuilder(AdminConfig adminConfig, TemplateEngine templateEngine) {
            this.adminConfig = adminConfig;
            this.templateEngine = templateEngine;
            context.setVariable("is_friend", false);
            context.setVariable("show_tasks_count", false);
            context.setVariable("show_message", false);
            context.setVariable("show_links", false);
            context.setVariable("show_button", false);
            context.setVariable("show_functionality", false);
            context.setVariable("admin_config", adminConfig);
            context.setVariable("goodbye_message_text", GOODBYE_MESSAGE);
            context.setVariable("goodbye_message_signature", SIGNATURE);
        }

        public EmailBuilder setRecipientAsFriend() {
            context.setVariable("is_friend", true);
            return this;
        }

        public EmailBuilder addTaskCountMessage(long taskCount) {
            context.setVariable("show_tasks_count", true);
            context.setVariable("task_count", taskCount);
            return this;
        }

        public EmailBuilder addMessage(String message) {
            context.setVariable("show_message", true);
            context.setVariable("message", message);
            return this;
        }

        public EmailBuilder addLinks(List<LinkInfo> links) {
            context.setVariable("show_links", true);
            context.setVariable("link_list", links);
            return this;
        }

        public EmailBuilder addLinkButton(String caption) {
            context.setVariable("show_button", true);
            context.setVariable("button", "Visit website");
            return this;
        }

        public EmailBuilder addFunctionalityInfo(List<String> functionalities) {
            context.setVariable("show_functionality", true);
            context.setVariable("application_functionality", functionalities);
            return this;
        }

        public String build() {
            return templateEngine.process("mail/tasks-mail", context);
        }
    }

    public String buildTrelloCardEmail(String message) {
        return new MailCreatorService.EmailBuilder(adminConfig, templateEngine)
                .setRecipientAsFriend()
                .addMessage(message)
                .addLinkButton(BUTTON_CAPTION)
                .addFunctionalityInfo(FUNCTIONALITIES)
                .build();
    }

    public String buildTaskCountEmail(long taskCount) {
        final List<LinkInfo> LINKS = Arrays.asList(
                new LinkInfo(adminConfig.getFrontendBaseUrl(), "Application"),
                new LinkInfo(adminConfig.getBackendBaseUrl() +  "/swagger-ui.html", "API documentation"),
                new LinkInfo(adminConfig.getBackendBaseUrl() +  "/application/health", "App status info"));

        return new MailCreatorService.EmailBuilder(adminConfig, templateEngine)
                .addTaskCountMessage(taskCount)
                .addLinks(LINKS)
                .build();
    }
}
