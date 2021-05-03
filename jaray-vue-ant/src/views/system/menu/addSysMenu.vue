<template>
    <div id="add">
        <a-modal title="新增" 
            :visible.sync="addDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="addForm" layout="horizontal" :label-col="labelCol" :wrapper-col="wrapperCol">
                <a-form-model-item label="类型">
                    <a-radio-group v-model="addForm.type">
                        <a-radio :value="0">目录</a-radio>
                        <a-radio :value="1">菜单</a-radio>
                    </a-radio-group>
                </a-form-model-item>
                <a-form-model-item label="ID">
                    <a-input v-model="addForm.id" autocomplete="off" placeholder="自动生成ID，无需填写"></a-input>
                </a-form-model-item>
                <a-form-model-item label="上级菜单">
                    <a-cascader 
                        v-model="menuPids"
                        change-on-select
                        :options="dirTreeData"
                        @change="handleChange"
                        clearable></a-cascader>
                    </el-cascader>
                </a-form-model-item>
                <a-form-model-item label="名称">
                    <a-input v-model="addForm.name" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item v-if="addForm.type == 1" label="菜单路径">
                    <a-input v-model="addForm.url" :disabled="addForm.type == 0?true:false" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="图标">
                    <a-input v-model="addForm.icon" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建人">
                    <a-input v-model="addForm.createBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建时间">
                    <a-date-picker 
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="addForm.createTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
                <a-form-model-item label="更新人">
                    <a-input v-model="addForm.lastUpdateBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="更新时间">
                    <a-date-picker 
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="addForm.lastUpdateTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
            </a-form-model>
            <div slot="footer" class="dialog-footer">
                <a-button @click="addDialogFormVisible = false">取 消</a-button>
                <a-button type="primary" @click="addSysMenu">确 定</a-button>
            </div>
        </a-modal>
    </div>
</template>

<script>
import MENU_API from '@api/api_sys_menu'

export default {
    name: 'addSysMenu',
    props: { dirTreeData: Array },
    components: {},
    data () {
        return {
            labelCol: { span: 4 },
            wrapperCol: { span: 14 },
            // 表格高度
            tableHeight: 'calc(95vh - 200px)',
            addDialogFormVisible: false,
            addForm: {},
            tileMenuData: [],
            // 菜单ids,用于回显
            menuIds: [],
            menuPids: [],
            typeOptions: [],
        }
    },
    created: function(){
        
    },
    methods: {
        openDialog: function() {
            let that = this
            that.addDialogFormVisible = true
        },
        addSysMenu: function(){
            let that = this
            // 目录/菜单父id赋值
            if(that.menuPids){
                that.parentId = that.menuPids[that.menuPids.length-1]
            }
            
            // 定义请求参数
            let params = that.addForm
            // 调用接口
            MENU_API.addSysMenu(params).then(function (result) {
                if (result.code === 200) {
                    that.$parent.initTable()
                    that.$message.success('恭喜你，添加成功');
                    that.addDialogFormVisible = false
                    that.addForm = {}
                }
            });
        },
        handleChange(value) {
            let that = this
            that.parentId = value[value.length-1]
        },
        getDirTreeData: function(dirTreeData){
            let that = this
            for(var i=0;i<dirTreeData.length;i++){
                if(dirTreeData[i].children && dirTreeData[i].children.length){
                    let data = dirTreeData[i].children.filter(function(item){
                        return item.type == 0
                    })

                    if(data && data.length){
                        dirTreeData[i].children = data
                    }else{
                        // 删除children属性
                        that.$delete(dirTreeData[i], 'children');
                    }
                    
                    if(data.length){
                        that.getDirTreeData(dirTreeData[i].children)    
                    }else{
                        continue
                    }
                    
                }
            }
        },
        handleOk(e) {
            let that = this
            that.addDialogFormVisible = false;
        },
        handleCancel(e) {
            let that = this
            that.addDialogFormVisible = false;
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