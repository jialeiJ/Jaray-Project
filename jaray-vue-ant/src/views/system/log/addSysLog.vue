<template>
    <div id="add">
        <a-modal title="新增" 
            :visible.sync="addDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="addForm" layout="horizontal" :label-col="labelCol" :wrapper-col="wrapperCol">
                <a-form-model-item label="ID">
                    <a-input v-model="addForm.id" autocomplete="off" placeholder="自动生成ID，无需填写"></a-input>
                </a-form-model-item>
                <a-form-model-item label="用户名">
                    <a-input v-model="addForm.userName" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="用户操作">
                    <a-input v-model="addForm.operation" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="方法">
                    <a-input v-model="addForm.method" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="参数">
                    <a-input v-model="addForm.params" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="IP地址">
                    <a-input v-model="addForm.ip" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建人">
                    <a-input v-model="addForm.createBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="创建时间">
                    <a-date-picker 
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="addForm.createTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
                <a-form-model-item label="更新人">
                    <a-input v-model="addForm.lastUpdateBy" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="更新时间">
                    <a-date-picker 
                        show-time 
                        show-today
                        placeholder="选择日期时间" 
                        v-model="addForm.lastUpdateTime"
                        @change="onChange" 
                        @ok="onOk" />
                    </a-date-picker>
                </a-form-model-item>
            </a-form-model>
            <div slot="footer" class="dialog-footer">
                <a-button @click="addDialogFormVisible = false">取 消</a-button>
                <a-button type="primary" @click="addSysLog">确 定</a-button>
            </div>
        </a-modal>
    </div>
</template>

<script>
import Log_API from '@api/api_sys_log'

export default {
    name: 'addSysLog',
    components: {},
    data () {
        return {
            labelCol: { span: 4 },
            wrapperCol: { span: 14 },
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
        addSysLog: function(){
            let that = this
            // 定义请求参数
            let params = that.addForm
            // 调用接口
            Log_API.addSysLog(params).then(function (result) {
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