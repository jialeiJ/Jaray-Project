<template>
    <div id="add">
        <el-dialog title="新增" :visible.sync="addDialogFormVisible">
            <el-form :model="addForm">
                <el-form-item label="ID" :label-width="formLabelWidth">
                    <el-input v-model="addForm.id" autocomplete="off" placeholder="自动生成ID，无需填写"></el-input>
                </el-form-item>
                <el-form-item label="标签值" :label-width="formLabelWidth">
                    <el-input v-model="addForm.label" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="数据值" :label-width="formLabelWidth">
                    <el-input v-model="addForm.value" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="类型" :label-width="formLabelWidth">
                    <el-input v-model="addForm.type" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="备注" :label-width="formLabelWidth">
                    <el-input v-model="addForm.remarks" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="创建人" :label-width="formLabelWidth">
                    <el-input v-model="addForm.createBy" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="创建时间" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="addForm.createTime"
                        type="datetime"
                        placeholder="选择日期时间"
                        align="right"
                        format="yyyy-MM-dd HH:mm:ss"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="更新人" :label-width="formLabelWidth">
                    <el-input v-model="addForm.lastUpdateBy" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="更新时间" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="addForm.lastUpdateTime"
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
                <el-button @click="addDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addSysDict">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import SYS_DICT_API from '@api/api_sys_dict'

export default {
    name: 'addSysDict',
    components: {},
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            addDialogFormVisible: false,
            addForm: {},
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
        openDialog: function() {
            let that = this
            that.addDialogFormVisible = true
        },
        addSysDict: function(){
            let that = this
            // 定义请求参数
            let params = that.addForm
            // 调用接口
            SYS_DICT_API.addSysDict(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$parent.$message({
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