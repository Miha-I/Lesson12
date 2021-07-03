package ua.itea.pom;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class Properties {

    private String projectBuildSourceEncoding;

    private String mavenCompilerCourse;

    private String mavenCompilerTarget;

    public String getProjectBuildSourceEncoding() {
        return projectBuildSourceEncoding;
    }

    public void setProjectBuildSourceEncoding(String projectBuildSourceEncoding) {
        this.projectBuildSourceEncoding = projectBuildSourceEncoding;
    }

    public String getMavenCompilerCourse() {
        return mavenCompilerCourse;
    }

    public void setMavenCompilerCourse(String mavenCompilerCourse) {
        this.mavenCompilerCourse = mavenCompilerCourse;
    }

    public String getMavenCompilerTarget() {
        return mavenCompilerTarget;
    }

    public void setMavenCompilerTarget(String mavenCompilerTarget) {
        this.mavenCompilerTarget = mavenCompilerTarget;
    }

    @Override
    public String toString() {
        return "Properties { projectBuildSourceEncoding: " + projectBuildSourceEncoding + ", mavenCompilerCourse: " +
                mavenCompilerCourse + ", mavenCompilerTarget: " + mavenCompilerTarget + " }";
    }
}
