<template>
    <div id="view">
        <el-dialog title="详情" :visible.sync="viewDialogFormVisible">
            <el-form :model="form">
                <el-form-item label="标题" :label-width="formLabelWidth">
                    <el-input v-model="form.title" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="描述" :label-width="formLabelWidth">
                    <el-input v-model="form.desc" readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="描述" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model="form.desc" readonly="readonly"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="viewDialogFormVisible = false">关闭</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import COMPLAINT_API from '@api/api_complaint'

export default {
    name: 'viewComplaint',
    components: {},
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            viewDialogFormVisible: false,
            form: {
                
            }
        }
    },
    created: function(){

    },
    methods: {
        openDialog: function(row) {
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            COMPLAINT_API.viewComplaint(params).then(function (result) {
                if (result.code === 200) {
                    that.form = result.map.complaint
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