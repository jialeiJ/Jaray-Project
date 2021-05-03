<template>
    <div id="edit">
        <a-modal title="编辑" 
            :visible.sync="editDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="editForm">
                <a-form-model-item label="ID" :label-width="formLabelWidth">
                    <a-input v-model="editForm.id" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="姓名" :label-width="formLabelWidth">
                    <a-input v-model="editForm.name" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="部门" :label-width="formLabelWidth">
                    <a-input v-model="editForm.id" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="角色" :label-width="formLabelWidth">
                    <a-input v-model="editForm.id" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="邮箱" :label-width="formLabelWidth">
                    <a-input v-model="editForm.email" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="手机号" :label-width="formLabelWidth">
                    <a-input v-model="editForm.mobile" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="状态" :label-width="formLabelWidth">
                    <a-input v-model="editForm.id" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建人" :label-width="formLabelWidth">
                    <a-input v-model="editForm.createBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建时间" :label-width="formLabelWidth">
                    <a-input v-model="editForm.id" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="更新人" :label-width="formLabelWidth">
                    <a-input v-model="editForm.lastUpdateBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="更新时间" :label-width="formLabelWidth">
                    <a-input v-model="editForm.id" autocomplete="off"></a-input>
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
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
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
                that.editForm.deptIId = value[value.length - 1]
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
                    that.editForm.deptIds = deptIds

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
            that.editForm.deptId = that.editForm.deptIds[(that.editForm.deptIds.length - 1)]
            that.editForm.roleId = that.roleIds.join(',')
            // 定义请求参数
            let params = that.editForm
            // 调用接口
            SYS_USER_API.updateSysUserById(params).then(function (result) {
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
        handleOk(e) {
            let that = this
            that.addDialogFormVisible = false;
        },
        handleCancel(e) {
            let that = this
            that.addDialogFormVisible = false;
        },
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
/deep/ .el-select,.el-cascader {
    display: block;
    position: relative;
}

/deep/ .el-dialog .el-dialog__body {
  border-top: 1px solid #dcdfe6;
  border-bottom: 1px solid #dcdfe6;
  max-height: calc(85vh - 260px); 
  overflow-y: auto;
}
</style>