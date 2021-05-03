<template>
    <div id="edit">
        <a-modal title="编辑" 
            :visible.sync="editDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="editForm" :label-col="labelCol" :wrapper-col="wrapperCol">
                <a-form-model-item label="ID">
                    <a-input v-model="editForm.id" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="上级机构">
                    <a-cascader 
                        v-model="deptIds"
                        change-on-select
                        :options="deptOptions"
                        @change="handleChange"
                        clearable></a-cascader>
                </a-form-model-item>
                <a-form-model-item label="名称">
                    <a-input v-model="editForm.name" autocomplete="off" placeholder="自动生成ID，无需填写"></a-input>
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
                <a-button type="primary" @click="editSysDept">确 定</a-button>
            </div>
        </a-modal>
    </div>
</template>

<script>
import API from '@api/api_sys_dept'

export default {
    name: 'editSysDept',
    props: { deptOptions: Array, tileDeptData: Array },
    components: {},
    data () {
        return {
            labelCol: { span: 4 },
            wrapperCol: { span: 14 },
            editDialogFormVisible: false,
            editForm: {},
            // 部门ids,用于回显
            deptIds: []
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
            API.viewSysDept(params).then(function (result) {
                if (result.code === 200) {
                    that.editForm = result.map.sysDept

                    // 获取部门id的父id
                    let id = result.map.sysDept.id
                    that.deptIds = []
                    that.getAllPidById(id, that.tileDeptData)
                    // 删除本机构,用于回显上层机构
                    let deptIds = that.deptIds.reverse()
                    deptIds.splice(that.deptIds.length-1, 1)
                    that.deptIds = deptIds

                    that.editDialogFormVisible = true
                }
            });
        },
        handleChange(value) {
            let that = this
        },
        getAllPidById: function(id, tileDeptData){
            let that = this
            if(id){
                that.deptIds.push(id)
            }
            tileDeptData.forEach(function(item, index){
                if(item.id == id){
                    that.getAllPidById(item.parentId, tileDeptData)
                }
            })
        },
        editSysDept: function(){
            let that = this
            if(that.deptIds && that.deptIds.length > 0){
                that.editForm.parentId = that.deptIds[0]
            }
            // 删除children属性
            that.$delete(this.editForm, 'children');
            // 定义请求参数
            let params = that.editForm
            // 调用接口
            API.updateSysDept(params).then(function (result) {
                if (result.code === 200) {
                    that.$parent.initTable()
                    that.$message.success('恭喜你，编辑成功');
                    that.editDialogFormVisible = false
                }
            });
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