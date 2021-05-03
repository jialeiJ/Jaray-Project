<template>
    <div id="edit">
        <a-modal title="编辑" 
            :visible.sync="editDialogFormVisible"
            @ok="handleOk"
            @cancel="handleCancel">
            <a-form-model :model="editForm"  :label-col="labelCol" :wrapper-col="wrapperCol">
                <a-form-model-item label="ID">
                    <a-input v-model="editForm.id" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="标签值">
                    <a-input v-model="editForm.label" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="数据值">
                    <a-input v-model="editForm.value" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="类型">
                    <a-input v-model="editForm.type" autocomplete="off"></a-input>
                </a-form-model-item>
                <a-form-model-item label="备注">
                    <a-input v-model="editForm.remarks" autocomplete="off"></a-input>
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
                <a-button type="primary" @click="editSysDict">确 定</a-button>
            </div>
        </a-modal>
    </div>
</template>

<script>
import SYS_DICT_API from '@api/api_sys_dict'

export default {
    name: 'editSysDict',
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
            SYS_DICT_API.viewSysDict(params).then(function (result) {
                if (result.code === 200) {
                    that.editForm = result.map.sysDict
                    that.editDialogFormVisible = true
                }
            });
        },
        editSysDict: function(){
            let that = this
            // 定义请求参数
            let params = that.editForm
            // 调用接口
            SYS_DICT_API.updateSysDict(params).then(function (result) {
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