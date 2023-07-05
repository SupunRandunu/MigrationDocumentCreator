
package org.wso2.migration.docs.json.models;

import java.util.LinkedHashMap;
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
    "source-path",
    "target-folder"
})
@Generated("jsonschema2pojo")
public class MigrationResource {

    @JsonProperty("source-path")
    private String sourcePath;
    @JsonProperty("target-folder")
    private String targetFolder;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("source-path")
    public String getSourcePath() {
        return sourcePath;
    }

    @JsonProperty("source-path")
    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @JsonProperty("target-folder")
    public String getTargetFolder() {
        return targetFolder;
    }

    @JsonProperty("target-folder")
    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
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
