<template>
    <div id="view">
        <el-dialog title="详情" :visible.sync="viewDialogFormVisible">
            <el-form :model="viewForm">
                <el-form-item label="ID" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="viewForm.id" readonly="readonly"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="姓名" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="viewForm.name" readonly="readonly"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="部门" :label-width="formLabelWidth">
                    <el-cascader
                        v-model="viewForm.dept_ids"
                        :options="deptOptions"
                        :props="{ expandTrigger: 'hover', checkStrictly: true }"
                        @change="handleChange"></el-cascader>
                </el-form-item>
                <el-form-item label="角色" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-select v-model="role_ids" multiple placeholder="请选择角色">
                            <el-option
                            v-for="item in sysRoleList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="邮箱" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="viewForm.email" readonly="readonly"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="手机号" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="viewForm.mobile" readonly="readonly"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="状态" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-select v-model="viewForm.status" placeholder="请选择状态">
                            <el-option
                            v-for="item in statusOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="创建人" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="viewForm.create_by" readonly="readonly"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="创建时间" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-date-picker
                            v-model="viewForm.create_time"
                            type="datetime"
                            placeholder="选择日期时间"
                            align="right"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            :picker-options="pickerOptions">
                        </el-date-picker>
                    </el-col>
                </el-form-item>
                <el-form-item label="更新人" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="viewForm.last_update_by" readonly="readonly"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="更新时间" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-date-picker
                            v-model="viewForm.last_update_time"
                            type="datetime"
                            placeholder="选择日期时间"
                            align="right"
                            format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            :picker-options="pickerOptions">
                        </el-date-picker>
                    </el-col>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="viewDialogFormVisible = false">关闭</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import SYS_USER_API from '@api/api_sys_user'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'viewSysUser',
    props: { deptOptions: Array, statusOptions: Array, sysRoleList: Array, tileDeptData: Array, },
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
            role_ids: [],
            // 部门ids,用于回显
            deptIds: []
        }
    },
    created: function(){
        
    },
    methods: {
        handleChange(value) {
            let that = this
            if (value) {
                that.viewForm.dept_id = value[value.length - 1]
            }
        },
        openDialog: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            SYS_USER_API.viewSysUserById(params).then(function (result) {
                if (result.code === 200) {
                    that.viewForm = result.map.sysUser

                    // 回显角色
                    that.role_ids = []
                    let role_ids = result.map.sysUser.role_id.split(',')
                    for(var i=0;i<role_ids.length;i++){
                        that.role_ids.push(role_ids[i])
                    }

                    // 获取部门id的父id
                    let id = result.map.sysUser.dept_id
                    that.deptIds = []
                    that.getAllPidById(id, that.tileDeptData)
                    // 回显上层机构
                    let dept_ids = that.deptIds.reverse()
                    that.viewForm.dept_ids = dept_ids
                    
                    that.viewDialogFormVisible = true
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
                    that.getAllPidById(item.parent_id, tileDeptData)
                }
            })
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