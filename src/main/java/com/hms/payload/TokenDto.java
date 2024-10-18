package com.hms.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.SqlReturnType;
@Setter
@Getter
public class TokenDto {
    private String token;
    private String type;
}
