<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">Shop Id</label>
        <el-input v-model="query.id" clearable placeholder="Shop Id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">Shop Name</label>
        <el-input v-model="query.name" clearable placeholder="Shop Name" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-select v-model="query.type" clearable size="small" placeholder="Shop Type" class="filter-item" style="width: 120px" @change="crud.toQuery">
          <el-option v-for="item in dict.dict.shop_type" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
        <date-range-picker
          v-model="query.createTime"
          start-placeholder="createTimeStart"
          end-placeholder="createTimeStart"
          class="date-item"
        />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="Shop Id" prop="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="Shop Name" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="Shop Type" prop="type">
            <el-select v-model="form.type" filterable placeholder="请选择">
              <el-option
                v-for="item in dict.shop_type"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="Shop Id" />
        <el-table-column prop="name" label="Shop Name" />
        <el-table-column prop="type" label="Shop Type">
          <template slot-scope="scope">
            {{ dict.label.shop_type[scope.row.type] }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="Create Time" />
        <el-table-column prop="updateTime" label="Update Time" />
        <el-table-column v-if="checkPer(['admin','shop:edit','shop:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudShop from '@/api/shop'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, name: null, type: null, createBy: null, createTime: null, updateTime: null, updateBy: null }
export default {
  name: 'Shop',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  dicts: ['shop_type'],
  cruds() {
    return CRUD({ title: 'Shop Manage', url: 'api/shop', idField: 'id', sort: 'id,desc', crudMethod: { ...crudShop }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'shop:add'],
        edit: ['admin', 'shop:edit'],
        del: ['admin', 'shop:del']
      },
      rules: {
        id: [
          { required: true, message: 'Shop Id不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: 'Shop Name不能为空', trigger: 'blur' }
        ],
        type: [
          { required: true, message: 'Shop Type不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'id', display_name: 'Shop Id' },
        { key: 'name', display_name: 'Shop Name' }
      ]
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
