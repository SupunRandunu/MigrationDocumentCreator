
package org.wso2.migration.docs.json.models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "product",
    "source",
    "target",
    "migration-resources",
    "dependency-products"
})
@Generated("jsonschema2pojo")
public class Product {

    @JsonProperty("product")
    private String product;
    @JsonProperty("source")
    private String source;
    @JsonProperty("target")
    private String target;
    @JsonProperty("migration-resources")
    private List<MigrationResource> migrationResources;
    @JsonProperty("dependency-products")
    private List<DependencyProduct> dependencyProducts;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("product")
    public String getProduct() {
        return product;
    }

    @JsonProperty("product")
    public void setProduct(String product) {
        this.product = product;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("target")
    public String getTarget() {
        return target;
    }

    @JsonProperty("target")
    public void setTarget(String target) {
        this.target = target;
    }

    @JsonProperty("migration-resources")
    public List<MigrationResource> getMigrationResources() {
        return migrationResources;
    }

    @JsonProperty("migration-resources")
    public void setMigrationResources(List<MigrationResource> migrationResources) {
        this.migrationResources = migrationResources;
    }

    @JsonProperty("dependency-products")
    public List<DependencyProduct> getDependencyProducts() {
        return dependencyProducts;
    }

    @JsonProperty("dependency-products")
    public void setDependencyProducts(List<DependencyProduct> dependencyProducts) {
        this.dependencyProducts = dependencyProducts;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
