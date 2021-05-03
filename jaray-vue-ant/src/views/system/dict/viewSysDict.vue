<template>
    <div id="view">
        <a-modal title="详情" 
            :visible.sync="viewDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="viewForm" layout="horizontal" :label-col="labelCol" :wrapper-col="wrapperCol">
                <a-form-model-item label="ID">
                    <a-input v-model="viewForm.id" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="标签值">
                    <a-input v-model="viewForm.label" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="数据值">
                    <a-input v-model="viewForm.value" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="类型">
                    <a-input v-model="viewForm.type" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="备注">
                    <a-input v-model="viewForm.remarks" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建人">
                    <a-input v-model="viewForm.createBy" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建时间">
                    <a-date-picker :disabled="true"
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="viewForm.createTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
                <a-form-model-item label="更新人">
                    <a-input v-model="viewForm.lastUpdateBy" :disabled="true"></a-input>
                </a-form-model-item>
                <a-form-model-item label="更新时间">
                    <a-date-picker :disabled="true"
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="viewForm.lastUpdateTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
            </a-form-model>
            <div slot="footer" class="dialog-footer">
                <a-button type="primary" @click="viewDialogFormVisible = false">关闭</a-button>
            </div>
        </a-modal>
    </div>
</template>

<script>
import SYS_DICT_API from '@api/api_sys_dict'

export default {
    name: 'viewSysDict',
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
            // 定义请求参数
            let params = row
            // 调用接口
            SYS_DICT_API.viewSysDict(params).then(function (result) {
                if (result.code === 200) {
                    that.viewForm = result.map.sysDict
                    that.viewDialogFormVisible = true
                }
            });
        },
        handleOk(e) {
            let that = this
            that.viewDialogFormVisible = false;
        },
        handleCancel(e) {
            let that = this
            that.viewDialogFormVisible = false;
        },
        onChange(value, dateString) {
        },
        onOk(value) {
        },
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>