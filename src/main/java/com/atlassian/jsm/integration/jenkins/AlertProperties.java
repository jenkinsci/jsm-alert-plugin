package com.atlassian.jsm.integration.jenkins;

public class AlertProperties {
    private String tags;
    private String teams;
    private AlertPriority priority;
    private AlertPriority buildStartPriority;

    public AlertProperties setTags(String tags) {
        this.tags = tags;
        return this;
    }


    public AlertProperties setTeams(String teams) {
        this.teams = teams;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public String getTeams() {
        return teams;
    }

    public AlertPriority getPriority() {
        return priority;
    }

    public AlertProperties setPriority(AlertPriority priority) {
        this.priority = priority;
        return this;
    }

    public AlertPriority getBuildStartPriority() {
        return buildStartPriority;
    }

    public AlertProperties setBuildStartPriority(AlertPriority buildStartPriority) {
        this.buildStartPriority = buildStartPriority;
        return this;
    }
}
