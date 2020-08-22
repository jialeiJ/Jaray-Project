<template>
    <div>
        <div>
            <div id="table">
                <div style="margin-bottom: 10px">
                    <el-input
                        placeholder="请输入姓名"
                        v-model="search"
                        clearable style="width: 200px">
                    </el-input>
                    <el-button type="success" @click="initTable" plain>查询</el-button>
                    <el-button type="success" @click="addComplaint" plain>增加</el-button>
                    <el-button type="danger" @click="deleteComplaints" plain>删除</el-button>
                </div>
                <i-table ref="iTable" 
                    @transmitParent="receiveChild"
                    @handleView="viewComplaint"
                    @handleEdit="editViewComplaint"
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
            <add-complaint ref="addComplaint"></add-complaint>

            <!-- 详情 -->
            <view-complaint ref="viewComplaint"></view-complaint>

            <!-- 编辑 -->
            <edit-complaint ref="editComplaint"></edit-complaint>
        </div>
    </div>
</template>

<script>
import COMPLAINT_API from '@api/api_complaint'
import iTable from '@components/common/iTable'
import iPagination from '@components/common/iPagination'
import addComplaint from '@views/complaint/addComplaint'
import viewComplaint from '@views/complaint/viewComplaint'
import editComplaint from '@views/complaint/editComplaint'

export default {
    name: 'complaint',
    components: { iTable, iPagination, addComplaint, viewComplaint, editComplaint },
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            // 表格高度
            tableHeight: 'calc(95vh - 200px)',
            search: '',
            tableTitle: [
                {prop: 'cid', label: 'ID', fixed: true, sort: true},
                {prop: 'title', label: '标题', sort: true, filters: [{text: '投诉',value: '投诉'}], formatter: this.titleFormatter},
                {prop: 'desc', label: '描述'},
                // 此处为操作栏，不需要可以删除，clickFun绑定此操作按钮的事件
                {prop: 'operation', label: '操作', fixed: 'right', width: 143,
                    operation: [
                        {name: '查看', style: 'primary', clickFun: this.viewComplaint, disabled: true},
                        {name: '修改', style: 'primary', clickFun: this.editViewComplaint, disabled: true},
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
        initTable: function(){
            let that = this
            // 定义请求参数
            let params = { 
                pageNum: that.pageNum,
                pageSize: that.pageSize
            }
            // 调用接口
            COMPLAINT_API.findComplaintList(params).then(function (result) {
                if (result.code === 200) {
                    that.total = result.map.complaints.total
                    that.currentPage = result.map.complaints.pageNum
                    that.pageSize = result.map.complaints.pageSize
                    that.tableData = result.map.complaints.list

                    that.filtersHandler(that.tableData)
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
            that.$refs['addComplaint'].openDialog()
        },
        viewComplaint: function(row) {
            let that = this
            that.$refs['viewComplaint'].openDialog(row)
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
            COMPLAINT_API.deleteComplaint(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$message({
                        message: '恭喜你，删除成功',
                        type: 'success'
                    });
                }
            });
        },
        editViewComplaint: function(row){
            let that = this
            that.$refs['editComplaint'].openDialog(row)
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