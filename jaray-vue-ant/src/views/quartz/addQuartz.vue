<template>
    <div id="add">
        <a-modal title="新增" 
            :visible.sync="addDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="addForm">
                <a-form-model-item label="任务实现类">
                    <a-input v-model="addForm.className"></a-input>
                </a-form-model-item>
                <a-form-model-item label="任务名称">
                    <a-input v-model="addForm.jobName"></a-input>
                </a-form-model-item>
                <a-form-model-item label="任务组名">
                    <a-input v-model="addForm.jobGroupName"></a-input>
                </a-form-model-item>
                <a-form-model-item label="时间表达式">
                    <a-input v-model="addForm.jobTime"></a-input>
                </a-form-model-item>
                <a-form-model-item label="任务状态">
                    <a-input v-model="addForm.jobStatus"></a-input>
                </a-form-model-item>
                <a-form-model-item label="任务描述">
                    <a-input v-model="addForm.description"></a-input>
                </a-form-model-item>
            </a-form-model>
            <div slot="footer" class="dialog-footer">
                <a-button @click="addDialogFormVisible = false">取 消</a-button>
                <a-button type="primary" @click="addJob">确 定</a-button>
            </div>
        </a-modal>
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