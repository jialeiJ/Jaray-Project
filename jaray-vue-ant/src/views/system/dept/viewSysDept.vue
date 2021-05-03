<template>
    <div id="view">
        <a-modal title="详情" 
            :visible.sync="viewDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="viewForm">
                <a-form-model-item label="ID">
                    <a-input v-model="viewForm.id"  placeholder="自动生成ID，无需填写" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="上级机构">
                    <a-cascader :disabled="true"
                        v-model="deptIds"
                        change-on-select
                        :options="deptOptions"
                        @change="handleChange"
                        clearable></a-cascader>
                </a-form-model-item>
                <a-form-model-item label="名称">
                    <a-input v-model="viewForm.name" autocomplete="off" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建人">
                    <a-input v-model="viewForm.createBy" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建时间">
                    <a-date-picker :disabled="true"
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="viewForm.createTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
                <a-form-model-item label="更新人">
                    <a-input v-model="viewForm.lastUpdateBy" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="更新时间">
                    <a-date-picker :disabled="true"
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="viewForm.lastUpdateTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
            </a-form-model>
            <div slot="footer" class="dialog-footer">
                <a-button type="primary" @click="viewDialogFormVisible = false">关闭</a-button>
            </div>
        </a-modal>
    </div>
</template>

<script>
import API from '@api/api_sys_dept'

export default {
    name: 'viewSysDept',
    props: { deptOptions: Array, tileDeptData: Array },
    components: {},
    data () {
        return {
            labelCol: { span: 4 },
            wrapperCol: { span: 14 },
            viewDialogFormVisible: false,
            viewForm: {},
            // 部门ids,用于回显
            deptIds: []
        }
    },
    created: function(){
        
    },
    methods: {
        handleChange(value) {
        },
        openDialog: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            API.viewSysDept(params).then(function (result) {
                if (result.code === 200) {
                    that.viewForm = result.map.sysDept

                    // 获取部门id的父id
                    let id = result.map.sysDept.id
                    that.deptIds = []
                    that.getAllPidById(id, that.tileDeptData)
                    // 删除本机构,用于回显上层机构
                    let deptIds = that.deptIds.reverse()
                    deptIds.splice(that.deptIds.length-1, 1)
                    that.deptIds = deptIds
                    
                    that.viewDialogFormVisible = true
                }
            });
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
        handleOk(e) {
            let that = this
            that.viewDialogFormVisible = false;
        },
        handleCancel(e) {
            let that = this
            that.viewDialogFormVisible = false;
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