<template>
    <div>
        <div id="table">
            <div style="margin-bottom: 10px">
                <el-input
                    placeholder="请输入名称"
                    v-model="search"
                    clearable style="width: 200px">
                </el-input>
                <el-button type="success" @click="initTable" plain>查询</el-button>
                <el-button type="danger" @click="addJob" plain>添加任务</el-button>
                <el-button type="danger" @click="deleteJob" plain>删除任务</el-button>
            </div>

            <i-table ref="iTable"
                @transmitParent="receiveChild"
                @rowClick="rowClick"
                @handleView="viewJob"
                @handleEdit="editViewJob"
                :tableTitle="tableTitle" 
                :tableData="tableData"
                :tableHeight="tableHeight">
            </i-table>
            <i-pagination ref="iPagination" 
                :total="total"
                :currentPage="pageNum"
                :pageSize="pageSize"
                @handleSizeChange="handleSizeChange"
                @handleCurrentChange="handleCurrentChange">
            </i-pagination>
        </div>

        <!-- 新增 -->
        <add-quartz ref="addQuartz"></add-quartz>

        <!-- 详情 -->
        <view-quartz ref="viewQuartz"></view-quartz>

        <!-- 编辑 -->
        <edit-quartz ref="editQuartz"></edit-quartz>
    </div>
</template>

<script>
import QUARTZ_API from '@api/api_quartz'
import iTable from '@components/common/iTable'
import iPagination from '@components/common/iPagination'
import addQuartz from '@views/quartz/addQuartz'
import editQuartz from '@views/quartz/editQuartz'
import viewQuartz from '@views/quartz/viewQuartz'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'sysUser',
    components: { iTable, iPagination, addQuartz, editQuartz, viewQuartz },
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            // 表格高度
            tableHeight: 'calc(95vh - 200px)',
            search: '',
            tableTitle: [
                {prop: 'jobClass', label: '任务实现类', fixed: true, sort: true},
                {prop: 'jobName', label: '任务名称', fixed: true, sort: true},
                {prop: 'jobGroupName', label: '任务组名', sort: true, filters: []},
                {prop: 'jobTime', label: '时间表达式'},
                {prop: 'jobStatus', label: '任务状态'},
                {prop: 'description', label: '任务描述'},
                // 此处为操作栏，不需要可以删除，clickFun绑定此操作按钮的事件
                {prop: 'operation', label: '操作', fixed: 'right', width: 285,
                    operation: [
                        {name: '查看', style: 'primary', clickFun: this.viewJob, disabled: true},
                        {name: '暂停', style: 'primary', clickFun: this.pauseJob, disabled: true},
                        {name: '恢复', style: 'primary', clickFun: this.resumeJob, disabled: true},
                        {name: '修改', style: 'primary', clickFun: this.editViewJob, disabled: true},
                    ]
                }
            ],
            tableData: [],
            multipleSelection: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
        }
    },
    created: function(){
        let that = this
        that.initTable()
    },
    methods: {
        addJob: function(){
            let that = this
            that.$refs['addQuartz'].openDialog()
        },
        viewJob: function(row){
            let that = this
            that.$refs['viewQuartz'].openDialog(row)
        },
        editViewJob: function(row){
            let that = this
            that.$refs['editQuartz'].openDialog(row)
        },
        initTable: function(){
            let that = this
            // 定义请求参数
            let params = { 
                search: that.search,
                pageNum: that.pageNum,
                pageSize: that.pageSize
            }
            // 调用接口
            QUARTZ_API.findAllJob(params).then(function (result) {
                if (result.code === 200) {
                    that.total = result.map.jobList.total
                    that.currentPage = result.map.jobList.pageNum
                    that.pageSize = result.map.jobList.pageSize
                    that.tableData = result.map.jobList.list
                }
            });
        },
        receiveChild: function(data){
            let that = this
            that.multipleSelection = data
            console.log("表格组件接收子组件的数据", that.multipleSelection)
        },
        deleteSysUser: function(){
            let that = this
            let ids = new Array()
            for(var i=0;i<that.multipleSelection.length;i++){
                ids.push(that.multipleSelection[i].id)
            }
            // 定义请求参数
            let params = { 
                ids: ids.join(',')
            }
            // 调用接口
            QUARTZ_API.deleteSysUserByIds(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$message({
                        message: '恭喜你，删除成功',
                        type: 'success'
                    });
                }
            });
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
        },
        rowClick: function(row) {
            console.log(row)
        },
        pauseJob: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            QUARTZ_API.pauseJob(params).then(function (result) {
                if (result.code === 200) {
                    that.$message({
                        message: '恭喜你，暂停成功',
                        type: 'success'
                    });
                    that.initTable()
                }
            });
        },
        resumeJob: function(row) {
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            QUARTZ_API.resumeJob(params).then(function (result) {
                if (result.code === 200) {
                    that.$message({
                        message: '恭喜你，恢复成功',
                        type: 'success'
                    });
                    that.initTable()
                }
            });
        },
        deleteJob: function(){
            let that = this
            // 定义请求参数
            let params = {}
            // 调用接口
            USER_API.deleteJob(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                }
            });
        }
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