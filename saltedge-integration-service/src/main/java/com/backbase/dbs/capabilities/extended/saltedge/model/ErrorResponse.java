package com.backbase.dbs.capabilities.extended.saltedge.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:richardj@backbase.com">Richard Jones</a>
 * @since 2019-09-19
 */
@NoArgsConstructor
@Data
public class ErrorResponse<T> {

    private Error error;
    private T request;

}
