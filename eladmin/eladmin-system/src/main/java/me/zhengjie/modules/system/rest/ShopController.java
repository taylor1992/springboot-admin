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
package me.zhengjie.modules.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.Shop;
import me.zhengjie.modules.system.service.ShopService;
import me.zhengjie.modules.system.domain.dto.ShopQueryCriteria;
import lombok.RequiredArgsConstructor;
import java.util.List;

import me.zhengjie.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.utils.PageResult;

/**
* @author Taylor
* @date 2025-06-01
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "Shop Manage")
@RequestMapping("/api/shop")
public class ShopController {

    private final ShopService shopService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('shop:list')")
    public void exportShop(HttpServletResponse response, ShopQueryCriteria criteria) throws IOException {
        shopService.download(shopService.queryAll(criteria), response);
    }

    @GetMapping
    @ApiOperation("查询Shop Manage")
    @PreAuthorize("@el.check('shop:list')")
    public ResponseEntity<PageResult<Shop>> queryShop(ShopQueryCriteria criteria){
        criteria.setCreateBy(SecurityUtils.getCurrentUsername());
        Page<Object> page = new Page<>(criteria.getPage(), criteria.getSize());
        return new ResponseEntity<>(shopService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增Shop Manage")
    @ApiOperation("新增Shop Manage")
    @PreAuthorize("@el.check('shop:add')")
    public ResponseEntity<Object> createShop(@Validated @RequestBody Shop resources){
        shopService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改Shop Manage")
    @ApiOperation("修改Shop Manage")
    @PreAuthorize("@el.check('shop:edit')")
    public ResponseEntity<Object> updateShop(@Validated @RequestBody Shop resources){
        shopService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除Shop Manage")
    @ApiOperation("删除Shop Manage")
    @PreAuthorize("@el.check('shop:del')")
    public ResponseEntity<Object> deleteShop(@ApiParam(value = "传ID数组[]") @RequestBody List<String> ids) {
        shopService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}