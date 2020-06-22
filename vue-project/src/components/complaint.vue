<template>
    <div>
        <div>
            <div id="table">
                <div style="margin-bottom: 10px">
                    <el-button size="mini" @click="addDialogFormVisible = true;">增加</el-button>
                    <el-button size="mini" :loading="loading" @click="deleteComplaints">删除</el-button>
                </div>
                <i-table ref="iTable" 
                    @transmitParent="receiveChild"
                    @handleView="viewComplaint"
                    @handleEdit="editViewComplaint"
                    :tableTitle="tableTitle" 
                    :tableData="tableData">
                </i-table>
                <i-pagination ref="iPagination" 
                    :total="total"
                    :currentPage="pageNum"
                    :pageSize="pageSize"
                    @handleSizeChange="handleSizeChange"
                    @handleCurrentChange="handleCurrentChange">
                </i-pagination>
            </div>

            <div id="add">
                <el-dialog title="新增" :visible.sync="addDialogFormVisible">
                    <el-form :model="form">
                        <el-form-item label="标题" :label-width="formLabelWidth">
                            <el-input v-model="form.title" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="描述" :label-width="formLabelWidth">
                            <el-input v-model="form.desc" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="活动区域" :label-width="formLabelWidth">
                            <el-select v-model="form.region" placeholder="请选择活动区域">
                                <el-option label="区域一" value="shanghai"></el-option>
                                <el-option label="区域二" value="beijing"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="addDialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="addComplaint">确 定</el-button>
                    </div>
                </el-dialog>
            </div>

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

            <div id="edit">
                <el-dialog title="编辑" :visible.sync="editDialogFormVisible">
                    <el-form :model="form">
                        <el-form-item label="标题" :label-width="formLabelWidth">
                            <el-input v-model="form.title"></el-input>
                        </el-form-item>
                        <el-form-item label="描述" :label-width="formLabelWidth">
                            <el-input v-model="form.desc"></el-input>
                        </el-form-item>
                        <el-form-item label="描述" :label-width="formLabelWidth">
                            <el-input type="textarea" v-model="form.desc" readonly="readonly"></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="editDialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="editComplaint">确 定</el-button>
                    </div>
                </el-dialog>
            </div>
        </div>
    </div>
</template>

<script>
import API from '../api/api_complaint'
import iTable from '../components/common/iTable'
import iPagination from '../components/common/iPagination'

export default {
    name: 'complaint',
    components: { iTable, iPagination },
    data () {
        return {
            formLabelWidth: '120px',
            loading: false,
            addDialogFormVisible: false,
            viewDialogFormVisible: false,
            editDialogFormVisible: false,
            search: '',
            tableTitle: [
                {prop: 'cid', label: 'ID', fixed: true, sort: true},
                {prop: 'title', label: '标题', sort: true, filters: [{text: '投诉',value: '投诉'}], formatter: this.titleFormatter},
                {prop: 'desc', label: '描述'}
            ],
            tableData: [],
            multipleSelection: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            form: {
                
            }
        }
    },
    created: function(){
        let that = this
        that.initTable()
    },
    methods: {
        initTable: function(){
            let that = this
            // 定义请求参数
            let params = { 
                pageNum: that.pageNum,
                pageSize: that.pageSize
            }
            // 调用接口
            API.findComplaintList(params).then(function (result) {
                if (result.code === 200) {
                console.log(result.map.complaints)
                that.total = result.map.complaints.total
                that.currentPage = result.map.complaints.pageNum
                that.pageSize = result.map.complaints.pageSize
                that.tableData = result.map.complaints.list

                that.filtersHandler(that.tableData)
                } else {
                    that.loading = false;
                    that.$message.error(result.msg);// elementUI消息提示
                }
            });
        },
        receiveChild: function(data){
            let that = this
            that.multipleSelection = data
            console.log("父组件接收的数据", that.multipleSelection)
        },
        addComplaint: function(){
            let that = this
            // 定义请求参数
            let params = that.form
            // 调用接口
            API.addComplaint(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$message({
                        message: '恭喜你，添加成功',
                        type: 'success'
                    });
                    that.addDialogFormVisible = false
                } else {
                    that.loading = false;
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        deleteComplaints: function(){
            let that = this
            let cids = new Array()
            for(var i=0;i<that.multipleSelection.length;i++){
                cids.push(that.multipleSelection[i].cid)
            }
            // 定义请求参数
            let params = { 
                cids: cids.join(',')
            }
            // 调用接口
            API.deleteComplaint(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$message({
                        message: '恭喜你，删除成功',
                        type: 'success'
                    });
                } else {
                    that.loading = false;
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        editViewComplaint: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            API.viewComplaint(params).then(function (result) {
                if (result.code === 200) {
                    console.log(result)
                    that.form = result.map.complaint
                    that.editDialogFormVisible = true
                } else {
                    that.loading = false;
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        editComplaint: function(){
            let that = this
            // 定义请求参数
            let params = that.form
            // 调用接口
            API.updateComplaint(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$message({
                        message: '恭喜你，编辑成功',
                        type: 'success'
                    });
                    that.editDialogFormVisible = false
                } else {
                    that.loading = false;
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        viewComplaint: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            API.viewComplaint(params).then(function (result) {
                if (result.code === 200) {
                    console.log(result)
                    that.form = result.map.complaint
                    that.viewDialogFormVisible = true
                } else {
                    that.loading = false;
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        titleFormatter: function(row, column, cellValue, index){
            return cellValue
        },
        handleSizeChange: function(pageSize){
            let that = this
            that.pageSize = pageSize
            that.initTable()
        },
        handleCurrentChange: function(currentPage){
            let that = this
            that.pageNum = currentPage
            that.initTable()
        },
        filtersHandler: function(tableData){
            let that = this
            for(var i=0;i<that.tableTitle.length;i++){
                if(that.tableTitle[i].filters){
                    let filters = new Array()
                    for(var j=0;j<tableData.length;j++){
                        for(var item in tableData[j]){ 
                            if(item == that.tableTitle[i].prop+''){  //item 表示Json串中的属性，如'title' 
                                filters.push({text: tableData[j][that.tableTitle[i].prop],value: tableData[j][that.tableTitle[i].prop]})
                            } 
                        }
                    }
                    that.tableTitle[i].filters = that.filtersArrayUnique(filters)
                }
            }
            
        },
        filtersArrayUnique: function(filters){
            let that = this
            for(var i=0;i<filters.length;i++){
                for(var j=i+1;j<filters.length;j++){
                    if(filters[i].text == filters[j].text){ // 第一个等于第二个，splice方法删除第二个
                        filters.splice(j, 1)
                        j--
                    }
                }
            }
            return filters
        }
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