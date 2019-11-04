package com.backbase.dbs.capabilities.extended.saltedge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:richardj@backbase.com">Richard Jones</a>
 * @since 2019-09-19
 */
@NoArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Error {

    @JsonProperty("class")
    private String errorClass;
    private String message;
    private String documentationUrl;
}
