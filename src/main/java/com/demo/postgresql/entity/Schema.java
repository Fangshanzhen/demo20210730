package com.demo.postgresql.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Schema {
    @ApiModelProperty(value = "schemaName", example = "public")
    String schemaName;
}
