<template>
    <div id="edit">
        <el-dialog title="编辑" :visible.sync="editDialogFormVisible">
            <el-form :model="editForm">
                <el-form-item label="ID" :label-width="formLabelWidth">
                    <el-input v-model="editForm.id" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="上级机构" :label-width="formLabelWidth">
                    <el-cascader
                        v-model="editForm.dept_ids"
                        :options="deptOptions"
                        :props="{ expandTrigger: 'hover', checkStrictly: true }"
                        @change="handleChange"></el-cascader>
                </el-form-item>
                <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="editForm.name" autocomplete="off" placeholder="自动生成ID，无需填写"></el-input>
                </el-form-item>
                <el-form-item label="创建人" :label-width="formLabelWidth">
                    <el-input v-model="editForm.create_by" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="创建时间" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="editForm.create_time"
                        type="datetime"
                        placeholder="选择日期时间"
                        align="right"
                        format="yyyy-MM-dd HH:mm:ss"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="更新人" :label-width="formLabelWidth">
                    <el-input v-model="editForm.last_update_by" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="更新时间" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="editForm.last_update_time"
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
                <el-button type="primary" @click="editSysDept">确 定</el-button>
            </div>
        </el-dialog>
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
                    let dept_ids = that.deptIds.reverse()
                    dept_ids.splice(that.deptIds.length-1, 1)
                    that.editForm.dept_ids = dept_ids

                    that.editDialogFormVisible = true
                }
            });
        },
        handleChange(value) {
            let that = this
            console.log(value);
            console.log(that.tileDeptData)
        },
        getAllPidById: function(id, tileDeptData){
            let that = this
            if(id){
                that.deptIds.push(id)
            }
            tileDeptData.forEach(function(item, index){
                if(item.id == id){
                    that.getAllPidById(item.parent_id, tileDeptData)
                }
            })
        },
        editSysDept: function(){
            let that = this
            if(that.editForm.dept_ids && that.editForm.dept_ids.length > 0){
                that.editForm.parent_id = that.editForm.dept_ids[0]
            }
            // 删除children属性
            that.$delete(this.editForm, 'children');
            // 定义请求参数
            let params = that.editForm
            // 调用接口
            API.updateSysDept(params).then(function (result) {
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
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
/deep/ .el-cascader {
    display: block;
    position: relative;
}
</style>