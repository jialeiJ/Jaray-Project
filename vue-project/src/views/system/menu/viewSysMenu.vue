<template>
    <div id="view">
        <el-dialog title="详情" :visible.sync="viewDialogFormVisible">
            <el-form :model="viewForm">
                <el-form-item label="类型" :label-width="formLabelWidth">
                    <el-radio-group v-model="viewForm.type" disabled>
                        <el-radio :label="0">目录</el-radio>
                        <el-radio :label="1">菜单</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="ID" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.id" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="上级菜单" :label-width="formLabelWidth">
                    <el-cascader
                        v-model="viewForm.menu_pids"
                        :options="dirTreeData"
                        :props="{ expandTrigger: 'hover', checkStrictly: true }"
                        @change="handleChange"
                        clearable>
                    </el-cascader>
                </el-form-item>
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.name" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="链接" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.url" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="图标" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.icon" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="排序" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.order_num" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="创建人" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.create_by" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="创建时间" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="viewForm.create_time"
                        type="datetime"
                        placeholder="选择日期时间"
                        align="right"
                        format="yyyy-MM-dd HH:mm:ss"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="更新人" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.last_update_by" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="更新时间" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="viewForm.last_update_time"
                        type="datetime"
                        placeholder="选择日期时间"
                        align="right"
                        format="yyyy-MM-dd HH:mm:ss"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="viewDialogFormVisible = false">关闭</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import MENU_API from '@api/api_sys_menu'

export default {
    name: 'viewSysMenu',
    props: { dirTreeData: Array },
    components: {},
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            viewDialogFormVisible: false,
            viewForm: {},
            pickerOptions: {
                shortcuts: [{
                    text: '今天',
                    onClick(picker) {
                        picker.$emit('pick', new Date());
                    }
                }, {
                    text: '昨天',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '一周前',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', date);
                    }
                }]
            },
            tileMenuData: [],
            // 菜单ids,用于回显
            menuIds: [],
            typeOptions: [],
        }
    },
    created: function(){
        
    },
    methods: {
        openDialog: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            MENU_API.viewSysMenu(params).then(function (result) {
                if (result.code === 200) {
                    that.viewForm = result.map.sysMenu

                    // 获取部门id的父id
                    let id = result.map.sysMenu.parent_id
                    that.menuIds = []
                    that.getAllPidById(id, that.tileMenuData)
                    
                    // 回显上层机构
                    let menu_ids = that.menuIds.reverse()
                    that.viewForm.menu_pids = menu_ids

                    that.viewDialogFormVisible = true
                }
            });
        },
        getAllPidById: function(id, tileMenuData){
            let that = this
            if(id && that.menuIds.indexOf(id) == -1){
                that.menuIds.push(id)
            }
            tileMenuData.forEach(function(item, index){
                if(item.id == id){
                    that.getAllPidById(item.parent_id, tileMenuData)
                }
            })
        },
        handleChange(value) {
            let that = this
            that.parent_id = value[value.length-1]
        },
    },
    mounted: function(){
        
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
/deep/ .el-dialog .el-dialog__body {
  border-top: 1px solid #dcdfe6;
  border-bottom: 1px solid #dcdfe6;
  max-height: calc(85vh - 260px); 
  overflow-y: auto;
}
</style>