<template>
    <div id="edit">
        <el-dialog title="编辑" :visible.sync="editDialogFormVisible">
            <el-form :model="editForm">
                <el-form-item label="类型" :label-width="formLabelWidth">
                    <el-radio-group v-model="editForm.type">
                        <el-radio :label="0">目录</el-radio>
                        <el-radio :label="1">菜单</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="ID" :label-width="formLabelWidth">
                    <el-input v-model="editForm.id" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="上级菜单" :label-width="formLabelWidth">
                    <el-cascader
                        v-model="editForm.menuPids"
                        :options="dirTreeData"
                        :props="{ expandTrigger: 'hover', checkStrictly: true }"
                        @change="handleChange"
                        clearable>
                    </el-cascader>
                </el-form-item>
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="editForm.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="类型" :label-width="formLabelWidth">
                    <el-input v-model="editForm.type" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="链接" :label-width="formLabelWidth">
                    <el-input v-model="editForm.url" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="图标" :label-width="formLabelWidth">
                    <el-input v-model="editForm.icon" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="排序" :label-width="formLabelWidth">
                    <el-input v-model="editForm.orderNum" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="创建人" :label-width="formLabelWidth">
                    <el-input v-model="editForm.createBy" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="创建时间" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="editForm.createTime"
                        type="datetime"
                        placeholder="选择日期时间"
                        align="right"
                        format="yyyy-MM-dd HH:mm:ss"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="更新人" :label-width="formLabelWidth">
                    <el-input v-model="editForm.lastUpdateBy" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="更新时间" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="editForm.lastUpdateTime"
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
                <el-button @click="editDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="editSysMenu">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import MENU_API from '@api/api_sys_menu'

export default {
    name: 'editSysMenu',
    props: { dirTreeData: Array },
    components: {},
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            editDialogFormVisible: false,
            editForm: {},
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
                    that.editForm = result.map.sysMenu

                     // 获取部门id的父id
                    let id = result.map.sysMenu.parentId
                    that.menuIds = []
                    that.getAllPidById(id, that.tileMenuData)
                    
                    // 回显上层机构
                    let menuIds = that.menuIds.reverse()
                    that.editForm.menuPids = menuIds

                    that.editDialogFormVisible = true
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
                    that.getAllPidById(item.parentId, tileMenuData)
                }
            })
        },
        editSysMenu: function(){
            let that = this
            // 删除该属性
            delete that.editForm.childMenus
            delete that.editForm.perms

            // 目录/菜单父id赋值
            that.editForm.parentId = that.editForm.menuPids[that.editForm.menuPids.length-1]
            // 定义请求参数
            let params = that.editForm
            // 调用接口
            MENU_API.updateSysMenu(params).then(function (result) {
                if (result.code === 200) {
                    that.$parent.initTable()
                    that.$message({
                        message: '恭喜你，编辑成功',
                        type: 'success'
                    });
                    that.editDialogFormVisible = false
                }
            });
        },
        handleChange(value) {
            let that = this
            that.parentId = value[value.length-1]
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