<template>
    <div id="edit">
        <a-modal title="编辑" 
            :visible.sync="editDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="editForm" :label-col="labelCol" :wrapper-col="wrapperCol">
                <a-form-model-item label="ID" >
                    <a-input v-model="editForm.id" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="姓名" >
                    <a-input v-model="editForm.name" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="部门">
                    <a-cascader
                        v-model="deptIds"
                        change-on-select
                        :options="deptOptions"
                        @change="handleChange"
                        clearable></a-cascader>
                </a-form-model-item>
                <a-form-model-item label="角色" >
                    <a-select v-model="roleIds" mode="tags">
                        <a-select-option
                            v-for="item in sysRoleList"
                            :key="item.value"
                            :value="item.value">
                            {{item.label}}
                        </a-select-option>
                    </a-select>
                </a-form-model-item>
                <a-form-model-item label="邮箱" >
                    <a-input v-model="editForm.email" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="手机号" >
                    <a-input v-model="editForm.mobile" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="状态" >
                    <a-select v-model="editForm.status">
                        <a-select-option
                            v-for="item in statusOptions"
                            :key="item.value"
                            :title="item.label"
                            :value="item.value">
                            {{item.label}}
                        </a-select-option>
                    </a-select>
                </a-form-model-item>
                <a-form-model-item label="创建人" >
                    <a-input v-model="editForm.createBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建时间" >
                    <a-date-picker 
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="editForm.createTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
                <a-form-model-item label="更新人" >
                    <a-input v-model="editForm.lastUpdateBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="更新时间" >
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
                <a-button type="primary" @click="editSysUser">确 定</a-button>
            </div>
        </a-modal>
    </div>
</template>

<script>
import SYS_USER_API from '@api/api_sys_user'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'editSysUser',
    props: { deptOptions: Array, statusOptions: Array, sysRoleList: Array, tileDeptData: Array, },
    components: {},
    data () {
        return {
            labelCol: { span: 4 },
            wrapperCol: { span: 14 },
            editDialogFormVisible: false,
            editForm: {},
            deptIds: [],
            roleIds: [],
        }
    },
    created: function(){
        
    },
    methods: {
        handleChange: function(value) {
            let that = this
            if (value) {
                that.deptIds = value
            }
        },
        openDialog: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            SYS_USER_API.viewSysUserById(params).then(function (result) {
                if (result.code === 200) {
                    that.editForm = result.map.sysUser

                    // 回显角色
                    that.roleIds = []
                    let roleIds = result.map.sysUser.roleId.split(',')
                    for(var i=0;i<roleIds.length;i++){
                        that.roleIds.push(roleIds[i])
                    }

                    // 获取部门id的父id
                    let id = result.map.sysUser.deptId
                    that.deptIds = []
                    that.getAllPidById(id, that.tileDeptData)
                    // 回显上层机构
                    let deptIds = that.deptIds.reverse()
                    that.deptIds = deptIds
                    console.log(that.deptIds)

                    that.editDialogFormVisible = true
                }
            });
        },
        getAllPidById: function(id, tileDeptData){
            let that = this
            if(id && that.deptIds.indexOf(id) == -1){
                that.deptIds.push(id)
            }
            tileDeptData.forEach(function(item, index){
                if(item.id == id){
                    that.getAllPidById(item.parentId, tileDeptData)
                }
            })
        },
        editSysUser: function(){
            let that = this
            that.editForm.deptId = that.deptIds[(that.deptIds.length - 1)]
            that.editForm.roleId = that.roleIds.join(',')
            // 定义请求参数
            let params = that.editForm
            // 调用接口
            SYS_USER_API.updateSysUserById(params).then(function (result) {
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