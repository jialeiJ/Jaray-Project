<template>
    <div id="edit">
        <a-modal title="编辑" 
            :visible.sync="editDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="editForm">
                <a-form-model-item label="类型">
                    <a-radio-group v-model="editForm.type">
                        <a-radio :value="0">目录</a-radio>
                        <a-radio :value="1">菜单</a-radio>
                    </a-radio-group>
                </a-form-model-item>
                <a-form-model-item label="ID">
                    <a-input v-model="editForm.id" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="上级菜单">
                    <a-cascader 
                        v-model="menuPids"
                        change-on-select
                        :options="dirTreeData"
                        @change="handleChange"
                        clearable></a-cascader>
                    </a-cascader>
                </a-form-model-item>
                <a-form-model-item label="名称">
                    <a-input v-model="editForm.name" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="类型">
                    <a-input v-model="editForm.type" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="链接">
                    <a-input v-model="editForm.url" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="图标">
                    <a-input v-model="editForm.icon" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="排序">
                    <a-input v-model="editForm.orderNum" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建人">
                    <a-input v-model="editForm.createBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建时间">
                    <a-date-picker 
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="editForm.createTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
                <a-form-model-item label="更新人">
                    <a-input v-model="editForm.lastUpdateBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="更新时间">
                    <a-date-picker 
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="editForm.lastUpdateTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
            </a-form-model>
            <div slot="footer" class="dialog-footer">
                <a-button @click="editDialogFormVisible = false">取 消</a-button>
                <a-button type="primary" @click="editSysMenu">确 定</a-button>
            </div>
        </a-modal>
    </div>
</template>

<script>
import MENU_API from '@api/api_sys_menu'

export default {
    name: 'editSysMenu',
    props: { dirTreeData: Array, tileMenuData: Array },
    components: {},
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            editDialogFormVisible: false,
            editForm: {},
            // 菜单ids,用于回显
            menuIds: [],
            menuPids: [],
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
                    that.menuPids = menuIds

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
            that.editForm.parentId = that.menuPids[that.menuPids.length-1]
            // 定义请求参数
            let params = that.editForm
            // 调用接口
            MENU_API.updateSysMenu(params).then(function (result) {
                if (result.code === 200) {
                    that.$parent.initTable()
                    that.$message.success('恭喜你，编辑成功');
                    that.editDialogFormVisible = false
                }
            });
        },
        handleChange(value) {
            let that = this
            that.parentId = value[value.length-1]
        },
        handleOk(e) {
            let that = this
            that.editDialogFormVisible = false;
        },
        handleCancel(e) {
            let that = this
            that.editDialogFormVisible = false;
        },
        onChange(value, dateString) {
        },
        onOk(value) {
        },
    },
    mounted: function(){
        
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
    /deep/ .ant-modal .ant-modal-body {
        border-top: 1px solid #dcdfe6;
        border-bottom: 1px solid #dcdfe6;
        max-height: calc(85vh - 260px); 
        overflow-y: auto;
    }

    .code-box-demo .ant-select {
        margin: 0 8px 10px 0;
    }

    .ant-row-rtl .code-box-demo .ant-select {
        margin: 0 0 10px 8px;
    }

    #components-select-demo-search-box .code-box-demo .ant-select {
        margin: 0;
    }
</style>