<template>
    <div id="edit">
        <a-modal title="编辑" 
            :visible.sync="editDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel" :label-col="labelCol" :wrapper-col="wrapperCol">
            <a-form-model :model="editForm">
                <a-form-model-item label="ID">
                    <a-input v-model="editForm.id" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="用户名">
                    <a-input v-model="editForm.userName" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="用户操作">
                    <a-input v-model="editForm.operation" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="方法">
                    <a-input v-model="editForm.method" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="参数">
                    <a-input v-model="editForm.params" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="IP地址">
                    <a-input v-model="editForm.ip" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建人">
                    <a-input v-model="editForm.createBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建时间">
                    <a-date-picker 
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="editForm.createTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
                <a-form-model-item label="更新人">
                    <a-input v-model="editForm.lastUpdateBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="更新时间">
                    <a-date-picker 
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="editForm.lastUpdateTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
            </a-form-model>
            <div slot="footer" class="dialog-footer">
                <a-button @click="editDialogFormVisible = false">取 消</a-button>
                <a-button type="primary" @click="editSysLog">确 定</a-button>
            </div>
        </a-modal>
    </div>
</template>

<script>
import LOG_API from '@api/api_sys_log'

export default {
    name: 'editSysLog',
    components: {},
    data () {
        return {
            labelCol: { span: 4 },
            wrapperCol: { span: 14 },
            editDialogFormVisible: false,
            editForm: {},
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
            LOG_API.viewSysLog(params).then(function (result) {
                if (result.code === 200) {
                    console.log()
                    that.editForm = result.map.sysLog
                    that.editDialogFormVisible = true
                }
            });
        },
        editSysLog: function(){
            let that = this
            // 定义请求参数
            let params = that.editForm
            // 调用接口
            LOG_API.updateSysLog(params).then(function (result) {
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