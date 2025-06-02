/*
*  Copyright 2019-2025 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.system.domain.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;
import io.swagger.annotations.ApiModelProperty;

/**
* @author Taylor
* @date 2025-06-01
**/
@Data
public class ShopQueryCriteria{

    @ApiModelProperty(value = "页码", example = "1")
    private Integer page = 1;

    @ApiModelProperty(value = "每页数据量", example = "10")
    private Integer size = 10;

    @ApiModelProperty(value = "Shop Id")
    private String id;

    @ApiModelProperty(value = "Shop Name")
    private String name;

    @ApiModelProperty(value = "Shop Type")
    private Integer type;

    @ApiModelProperty(value = "Create By")
    private String createBy;
    private List<Timestamp> createTime;
}