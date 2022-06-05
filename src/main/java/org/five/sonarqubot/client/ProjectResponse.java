package org.five.sonarqubot.client;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIncludeProperties()
public class ProjectResponse {
    private String name;
    private String project;
    private String visibility;
    private String qualifier;

}
