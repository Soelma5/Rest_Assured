package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class Formula {


    private int total;
    private String givenName;
    private String familyName;
    private String nationality;
}
