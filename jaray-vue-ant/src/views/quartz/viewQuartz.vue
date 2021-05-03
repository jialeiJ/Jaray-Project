<template>
    <div id="view">
        <a-modal title="详情" 
            :visible.sync="viewDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="viewForm"  layout="horizontal" :label-col="labelCol" :wrapper-col="wrapperCol">
                <a-form-model-item label="任务名称">
                    <a-input v-model="viewForm.jobName" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="任务组名">
                    <a-input v-model="viewForm.jobGroupName" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="时间表达式">
                    <a-input v-model="viewForm.jobTime" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="任务状态">
                    <a-input v-model="viewForm.jobStatus" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="任务描述">
                    <a-input v-model="viewForm.description" :disabled="true"></a-input>
                </a-form-model-item>
            </a-form-model>
            <div slot="footer" class="dialog-footer">
                <a-button type="primary" @click="viewDialogFormVisible = false">关闭</a-button>
            </div>
        </a-modal>
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
            labelCol: { span: 4 },
            wrapperCol: { span: 14 },
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
        handleOk(e) {
            let that = this
            that.viewDialogFormVisible = false;
        },
        handleCancel(e) {
            let that = this
            that.viewDialogFormVisible = false;
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