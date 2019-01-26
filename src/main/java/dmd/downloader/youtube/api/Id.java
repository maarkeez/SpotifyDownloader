package dmd.downloader.youtube.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Id {
    private String kind;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String videoId;
}
