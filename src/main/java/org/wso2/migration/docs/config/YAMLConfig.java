package org.wso2.migration.docs.config;

public class YAMLConfig {
    private String token;
    private String repository;
    private String directory;
    private String organization;
    private String source;
    private String target;
    private String dependency;

    private String product;

    public String getToken() {
        return token;
    }

    public String getRepository() {
        return repository;
    }

    public String getDirectory() {
        return directory;
    }

    public String getOrganization() {
        return organization;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getDependency() {
        return dependency;
    }

    public String getProduct() {
        return product;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "YAMLConfig{" +
                "token='" + token + '\'' +
                ", repository='" + repository + '\'' +
                ", directory='" + directory + '\'' +
                ", organization='" + organization + '\'' +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", dependency='" + dependency + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
