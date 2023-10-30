package com.atlassian.jsm.integration.jenkins;

import hudson.model.Run;
import hudson.model.TaskListener;

public class JSMNotificationRequest {
    private String apiKey;
    private String apiUrl;
    private AlertProperties alertProperties;
    private Run<?, ?> build;
    private TaskListener listener;

    public String getApiKey() {
        return apiKey;
    }

    public JSMNotificationRequest setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public JSMNotificationRequest setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }

    public AlertProperties getAlertProperties() {
        return alertProperties;
    }

    public JSMNotificationRequest setAlertProperties(AlertProperties alertProperties) {
        this.alertProperties = alertProperties;
        return this;
    }

    public Run<?, ?> getBuild() {
        return build;
    }

    public JSMNotificationRequest setBuild(Run<?, ?> build) {
        this.build = build;
        return this;
    }

    public TaskListener getListener() {
        return listener;
    }

    public JSMNotificationRequest setListener(TaskListener listener) {
        this.listener = listener;
        return this;
    }
}
