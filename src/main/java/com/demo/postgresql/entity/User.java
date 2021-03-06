package com.demo.postgresql.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class User {
    @ApiModelProperty(value = "name1", example = "郑晟旻")
    String firstName;
    @ApiModelProperty(value = "name2", example = "pg_toast_2604_index")
    String secondName;
    @ApiModelProperty(value = "name3", example = "Extended dynamic SQL")
    String thirdName;
}
