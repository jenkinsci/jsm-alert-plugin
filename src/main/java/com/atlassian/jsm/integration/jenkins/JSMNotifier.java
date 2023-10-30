package com.atlassian.jsm.integration.jenkins;

import hudson.Extension;
import hudson.Launcher;
import hudson.Util;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;

import hudson.util.Secret;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.export.Exported;

import java.io.IOException;

public class JSMNotifier extends Notifier {
    private static final String DEFAULT_API_URL = "https://api.atlassian.com/";
    private boolean enable;
    private String tags;
    private boolean notifyBuildStart;
    private Secret apiKey;
    private String apiUrl;
    private String teams;
    private AlertPriority alertPriority;
    private AlertPriority notifyBuildStartPriority;

    @DataBoundConstructor
    public JSMNotifier(boolean enable,
                       boolean notifyBuildStart,
                       String tags,
                       Secret apiKey,
                       String apiUrl,
                       String teams,
                       String alertPriority,
                       String buildStartAlertPriority) {
        this.enable = enable;
        this.notifyBuildStart = notifyBuildStart;
        this.tags = tags;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.teams = teams;
        this.alertPriority = AlertPriority.fromDisplayName(alertPriority);
        this.notifyBuildStartPriority = AlertPriority.fromDisplayName(buildStartAlertPriority);
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        if (!isEnable()) {
            return true;
        }
        JSMNotificationService service = createJSMNotificationService(build, listener);

        return service.sendAfterBuildData();
    }

    private JSMNotificationService createJSMNotificationService(AbstractBuild<?, ?> build, BuildListener listener) {

        // This variables for override the fields if they are not empty
        String tagsGiven = Util.fixNull(tags).isEmpty() ? getDescriptor().getTags() : tags;
        String teamsGiven = Util.fixNull(teams).isEmpty() ? getDescriptor().getTeams() : teams;

        AlertProperties alertProperties = new AlertProperties()
                .setTags(tagsGiven)
                .setTeams(teamsGiven)
                .setPriority(alertPriority)
                .setBuildStartPriority(notifyBuildStartPriority);

        String apiKeyGiven;
        if(apiKey != null && !Util.fixNull(apiKey.getPlainText()).isEmpty()) {
            apiKeyGiven = apiKey.getPlainText();
        } else {
            apiKeyGiven = getDescriptor().getApiKey().getPlainText();
        }

        String apiUrlGiven = Util.fixNull(apiUrl).isEmpty() ? getDescriptor().getApiUrl() : apiUrl;

        JSMNotificationRequest request =
                new JSMNotificationRequest()
                        .setAlertProperties(alertProperties)
                        .setBuild(build)
                        .setListener(listener)
                        .setApiKey(apiKeyGiven)
                        .setApiUrl(apiUrlGiven);

        return new JSMNotificationService(request);
    }

    @Override
    public boolean prebuild(AbstractBuild<?, ?> build, BuildListener listener) {
        if (!isEnable() || !isNotifyBuildStart()) {
            return true;
        }

        JSMNotificationService JSMNotificationService = createJSMNotificationService(build, listener);
        return JSMNotificationService.sendPreBuildPayload();
    }

    @Override
    public String toString() {
        return "JSMNotifier{" +
                "disable=" + enable +
                ", notifyBuildStart=" + notifyBuildStart +
                ", tags='" + tags + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", teams='" + teams + '\'' +
                '}';
    }

    @Exported
    public boolean isEnable() {
        return enable;
    }

    @Exported
    public boolean isNotifyBuildStart() {
        return notifyBuildStart;
    }

    @Exported
    public Secret getApiKey() {
        return apiKey;
    }

    @Exported
    public String getApiUrl() {
        return apiUrl;
    }

    @Exported
    public String getTags() {
        return tags;
    }

    @Exported
    public String getTeams() {
        return teams;
    }

    @Exported
    public AlertPriority getNotifyBuildStartPriority() {
        return notifyBuildStartPriority;
    }

    @Exported
    public AlertPriority getAlertPriority() {
        return alertPriority;
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {
        /**
         * To persist global configuration information,
         * simply store it in a field and call save().
         * <p/>
         * <p/>
         * If you don't want fields to be persisted, use <tt>transient</tt>.
         */
        private Secret apiKey;
        private String teams;
        private String tags;
        private String apiUrl;

        /**
         * In order to load the persisted global configuration, you have to
         * call load() in the constructor.
         */
        public DescriptorImpl() {
            load();
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // Indicates that this builder can be used with all kinds of project types
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Send Alert to Jira Service Management";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            apiKey = Secret.fromString((String) formData.get("apiKey"));
            apiUrl = formData.getString("apiUrl");
            tags = formData.getString("tags");
            teams = formData.getString("teams");
            save();
            return super.configure(req, formData);
        }

        public Secret getApiKey() {
            return apiKey;
        }

        public String getApiUrl() {
            if (StringUtils.isBlank(apiUrl)) {
                apiUrl = JSMNotifier.DEFAULT_API_URL;
            }
            return apiUrl;
        }

        public String getTeams() {
            return teams;
        }


        public String getTags() {
            return tags;
        }

        @Override
        public String toString() {
            return "DescriptorImpl{" +
                    "apiUrl='" + apiUrl + '\'' +
                    ", teams='" + teams + '\'' +
                    ", tags='" + tags + '\'' +
                    '}';
        }
    }
}
