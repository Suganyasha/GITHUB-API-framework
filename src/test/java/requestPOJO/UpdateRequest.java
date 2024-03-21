package requestPOJO;

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
public class UpdateRequest {
	@JsonProperty(value = "name")
    public String name;
    @JsonProperty(value = "description")
    public String description;
    @JsonProperty(value = "Private")
    public boolean Private;

}
