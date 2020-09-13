<template>
    <div id="view">
        <el-dialog title="详情" :visible.sync="viewDialogFormVisible">
            <el-form :model="viewForm">
                <el-form-item label="ID" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.id" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="用户名" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.userName" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="用户操作" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.operation" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="方法" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.method" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="参数" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.params" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="IP地址" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.ip" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="创建人" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.createBy" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="创建时间" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="viewForm.createTime"
                        type="datetime"
                        placeholder="选择日期时间"
                        align="right"
                        format="yyyy-MM-dd HH:mm:ss"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="更新人" :label-width="formLabelWidth">
                    <el-input v-model="viewForm.lastUpdateBy" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="更新时间" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="viewForm.lastUpdateTime"
                        type="datetime"
                        placeholder="选择日期时间"
                        align="right"
                        format="yyyy-MM-dd HH:mm:ss"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="viewDialogFormVisible = false">关闭</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import LOG_API from '@api/api_sys_log'

export default {
    name: 'viewSysLog',
    components: {},
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            viewDialogFormVisible: false,
            viewForm: {},
            pickerOptions: {
                shortcuts: [{
                    text: '今天',
                    onClick(picker) {
                        picker.$emit('pick', new Date());
                    }
                }, {
                    text: '昨天',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '一周前',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', date);
                    }
                }]
            }
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
                    that.viewForm = result.map.sysLog
                    that.viewDialogFormVisible = true
                }
            });
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