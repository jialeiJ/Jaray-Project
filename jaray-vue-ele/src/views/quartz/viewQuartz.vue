<template>
    <div id="view">
        <el-dialog title="详情" :visible.sync="viewDialogFormVisible">
            <el-form :model="viewForm">
                <el-form-item label="任务名称" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="viewForm.jobName" readonly="readonly"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="任务组名" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="viewForm.jobGroupName" readonly="readonly"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="时间表达式" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="viewForm.jobTime" readonly="readonly"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="任务状态" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="viewForm.jobStatus" readonly="readonly"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="任务描述" :label-width="formLabelWidth">
                    <el-col :span="24">
                        <el-input v-model="viewForm.description" readonly="readonly"></el-input>
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
import QUARTZ_API from '@api/api_quartz'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'viewQuartz',
    props: {},
    components: {},
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            viewDialogFormVisible: false,
            viewForm: {},
        }
    },
    created: function(){
        
    },
    methods: {
        openDialog: function(row){
            let that = this
            that.viewForm = row
                
            that.viewDialogFormVisible = true
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