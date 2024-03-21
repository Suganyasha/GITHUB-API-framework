package responsePOJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetResponse {
	@JsonProperty(value = "id")
    public String Id;
    @JsonProperty(value = "node_id")
    public String node_Id;
    @JsonProperty(value = "name")
    public String name;
    @JsonProperty(value = "full_name")
    public String fullname;
    @JsonProperty(value = "private")
    public String Private;
    
}
