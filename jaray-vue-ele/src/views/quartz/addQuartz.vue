<template>
    <div id="add">
        <el-dialog title="新增" :visible.sync="addDialogFormVisible">
            <el-form :model="addForm">
                <el-form-item label="任务实现类" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="addForm.className"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="任务名称" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="addForm.jobName"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="任务组名" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="addForm.jobGroupName"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="时间表达式" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="addForm.jobTime"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="任务状态" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="addForm.jobStatus"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="任务描述" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="addForm.description"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addJob">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import QUARTZ_API from '@api/api_quartz'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'addQuartz',
    props: { deptOptions: Array, statusOptions: Array },
    components: {},
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            addDialogFormVisible: false,
            addForm: {},
        }
    },
    created: function(){
        
    },
    methods: {
        openDialog: function() {
            let that = this
            that.addDialogFormVisible = true
        },
        addJob: function(){
            let that = this
            // 定义请求参数
            let params = that.addForm
            // 调用接口
            QUARTZ_API.addJob(params).then(function (result) {
                if (result.code === 200) {
                    that.$parent.initTable()
                    that.$message({
                        message: '恭喜你，添加成功',
                        type: 'success'
                    });
                    that.addDialogFormVisible = false
                    that.addForm = {}
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