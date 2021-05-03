<template>
    <div id="add">
        <a-modal title="新增" 
            :visible.sync="addDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="addForm" layout="horizontal" :label-col="labelCol" :wrapper-col="wrapperCol">
                <a-form-model-item label="ID">
                    <a-input v-model="addForm.id" autocomplete="off" placeholder="自动生成ID，无需填写"></a-input>
                </a-form-model-item>
                <a-form-model-item label="姓名" >
                    <a-input v-model="addForm.name" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="部门">
                    <a-cascader 
                        v-model="deptIds"
                        change-on-select
                        :options="deptOptions"
                        @change="handleChange"
                        clearable></a-cascader>
                </a-form-model-item>
                <a-form-model-item label="邮箱">
                    <a-input v-model="addForm.email" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="手机号">
                    <a-input v-model="addForm.mobile" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="状态">
                    <a-select v-model="addForm.status" placeholder="请选择状态">
                        <a-select-option
                            v-for="item in statusOptions"
                            :key="item.value"
                            :title="item.label"
                            :value="item.value">
                            {{item.label}}
                        </a-select-option>
                    </a-select>
                </a-form-model-item>
                <a-form-model-item label="创建人">
                    <a-input v-model="addForm.createBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建时间" >
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
                <a-button type="primary" @click="addSysUser">确 定</a-button>
            </div>
        </a-modal>
    </div>
</template>

<script>
import SYS_USER_API from '@api/api_sys_user'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'addSysUser',
    props: { deptOptions: Array, statusOptions: Array },
    components: {},
    data () {
        return {
            labelCol: { span: 4 },
            wrapperCol: { span: 14 },
            addDialogFormVisible: false,
            addForm: {},
            deptIds: [],
        }
    },
    created: function(){
        
    },
    methods: {
        openDialog: function() {
            let that = this
            that.addDialogFormVisible = true
        },
        closeDialog: function() {
            let that = this
            that.addDialogFormVisible = false
        },
        handleChange: function(value) {
            let that = this
            if (value) {
                that.addForm.deptId = value[value.length - 1]
            }
        },
        addSysUser: function(){
            let that = this
            // 定义请求参数
            let params = that.addForm
            // 调用接口
            SYS_USER_API.addSysUser(params).then(function (result) {
                if (result.code === 200) {
                    that.$parent.initTable()
                    that.$message.success('恭喜你，添加成功');
                    that.addDialogFormVisible = false
                    that.addForm = {}
                }
            });
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