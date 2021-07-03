package ua.itea.pom;

import java.util.List;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class Project {

    private String modelVersion;

    private String groupId;

    private String artifactId;

    private String version;

    private Properties properties;

    private List<Dependency> dependencies;

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String toString(){
        return "Project { modelVersion: " + modelVersion + ", groupId: " + groupId + ", artifactId: " + artifactId +
                ", version: " + version + ", properties: " + properties + ", dependencies: " + dependencies + " }";
    }
}
