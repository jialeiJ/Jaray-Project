<template>
    <div id="edit">
        <a-modal title="编辑" 
            :visible.sync="editDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="editForm">
                <a-form-model-item label="任务名称" :label-width="formLabelWidth">
                    <a-input v-model="editForm.jobName"></a-input>
                </a-form-model-item>
                <a-form-model-item label="任务组名" :label-width="formLabelWidth">
                    <a-input v-model="editForm.jobGroupName"></a-input>
                </a-form-model-item>
                <a-form-model-item label="时间表达式" :label-width="formLabelWidth">
                    <a-input v-model="editForm.jobTime"></a-input>
                </a-form-model-item>
                <a-form-model-item label="任务状态" :label-width="formLabelWidth">
                    <a-input v-model="editForm.jobStatus"></a-input>
                </a-form-model-item>
                <a-form-model-item label="任务描述" :label-width="formLabelWidth">
                    <a-input v-model="editForm.description"></a-input>
                </a-form-model-item>
            </a-form-model>
            <div slot="footer" class="dialog-footer">
                <a-button @click="editDialogFormVisible = false">取 消</a-button>
                <a-button type="primary" @click="editQuartz">确 定</a-button>
            </div>
        </a-modal>
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