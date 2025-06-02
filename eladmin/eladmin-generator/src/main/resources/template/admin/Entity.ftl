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
package ${package}.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
<#assign notBlankUsed = false>
<#assign notNullUsed = false>
<#if columns??>
    <#list columns as column>
        <#if column.istNotNull && column.columnKey != 'PRI'>
            <#if column.columnType = 'String'>
                <#assign notBlankUsed = true>
            <#else>
                <#assign notNullUsed = true>
            </#if>
        </#if>
    </#list>
</#if>
<#if notBlankUsed>
import javax.validation.constraints.NotBlank;
</#if>
<#if notNullUsed>
import javax.validation.constraints.NotNull;
</#if>
import java.io.Serializable;
<#if auto>
import com.baomidou.mybatisplus.annotation.IdType;
</#if>
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import me.zhengjie.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
* @description /
* @author ${author}
* @date ${date}
**/
@Getter
@Setter
@TableName("${tableName}")
public class ${className} extends BaseEntity implements Serializable {
<#if columns??>
    <#list columns as column>

    <#if !(column.changeColumnName?contains("createBy") || column.changeColumnName?contains("createTime") || column.changeColumnName?contains("updateBy") || column.changeColumnName?contains("updateTime"))>

    <#if column.columnKey = 'PRI'>
    @TableId(value = "${column.columnName}"<#if auto>, type = IdType.AUTO</#if>)
    </#if>
    <#if column.istNotNull && column.columnKey != 'PRI'>
        <#if column.columnType = 'String'>
    @NotBlank
        <#else>
    @NotNull
        </#if>
    </#if>
    <#if column.remark != ''>
    @ApiModelProperty(value = "${column.remark}")
    <#else>
    @ApiModelProperty(value = "${column.changeColumnName}")
    </#if>
    private ${column.columnType} ${column.changeColumnName};
    </#if>
    </#list>
</#if>

    public void copy(${className} source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
