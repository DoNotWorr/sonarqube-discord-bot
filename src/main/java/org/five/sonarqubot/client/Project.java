package org.five.sonarqubot.client;

public class Project {
    private String name;
    private String project;
    private String visibility;
    private String qualifier;

    public Project() {
    }

    ;

    public Project(String name, String project, String visibility) {
        this.name = name;
        this.project = project;
        this.visibility = visibility;
    }

    public Project(String name, String project, String visibility, String qualifier) {

        this.name = name;
        this.project = project;
        this.visibility = visibility;
        this.qualifier = qualifier;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
