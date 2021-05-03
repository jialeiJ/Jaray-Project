<template>
    <div id="edit">
        <el-dialog title="编辑" :visible.sync="editDialogFormVisible">
            <el-form :model="editForm">
                <el-form-item label="任务名称" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="editForm.jobName"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="任务组名" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="editForm.jobGroupName"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="时间表达式" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="editForm.jobTime"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="任务状态" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="editForm.jobStatus"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="任务描述" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="editForm.description"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="editQuartz">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import QUARTZ_API from '@api/api_quartz'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'editQuartz',
    props: {},
    components: {},
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            editDialogFormVisible: false,
            editForm: {},
        }
    },
    created: function(){
        
    },
    methods: {
        openDialog: function(row){
            let that = this
            that.editForm = row

            that.editDialogFormVisible = true
        },
        editQuartz: function(){
            let that = this
            // 定义请求参数
            let params = that.editForm
            // 调用接口
            QUARTZ_API.updateJob(params).then(function (result) {
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