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
package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.Shop;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.system.service.ShopService;
import me.zhengjie.modules.system.domain.dto.ShopQueryCriteria;
import me.zhengjie.modules.system.mapper.ShopMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.zhengjie.utils.PageUtil;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import me.zhengjie.utils.PageResult;

/**
* @description 服务实现
* @author Taylor
* @date 2025-06-01
**/
@Service
@RequiredArgsConstructor
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    private final ShopMapper shopMapper;

    @Override
    public PageResult<Shop> queryAll(ShopQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(shopMapper.findAll(criteria, page));
    }

    @Override
    public List<Shop> queryAll(ShopQueryCriteria criteria){
        return shopMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Shop resources) {
        shopMapper.insert(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Shop resources) {
        Shop shop = getById(resources.getId());
        shop.copy(resources);
        shopMapper.updateById(shop);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<String> ids) {
        shopMapper.deleteBatchIds(ids);
    }

    @Override
    public void download(List<Shop> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Shop shop : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("Shop Name", shop.getName());
            map.put("Shop Type", shop.getType());
            map.put(" createBy",  shop.getCreateBy());
            map.put("Create Time", shop.getCreateTime());
            map.put("Update Time", shop.getUpdateTime());
            map.put("Update By", shop.getUpdateBy());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}