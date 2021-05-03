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
                    <el-button v-if="false" type="success" @click="addSysLog" plain>增加</el-button>
                    <el-button v-if="false" type="danger" @click="deleteSysLog" plain>删除</el-button>
                </div>
                <i-table ref="iTable" 
                    @transmitParent="receiveChild"
                    @handleView="viewSysLog"
                    @handleEdit="editViewSysLog"
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
            <add-sys-log ref="addSysLog"></add-sys-log>

            <!-- 详情 -->
            <view-sys-log ref="viewSysLog"></view-sys-log>

            <!-- 编辑 -->
            <edit-sys-log ref="editSysLog"></edit-sys-log>
        </div>
    </div>
</template>

<script>
import API from '@api/api_sys_log'
import iTable from '@components/common/iTable'
import iPagination from '@components/common/iPagination'
import addSysLog from '@views/system/log/addSysLog'
import viewSysLog from '@views/system/log/viewSysLog'
import editSysLog from '@views/system/log/editSysLog'

export default {
    name: 'sysLog',
    components: { iTable, iPagination, addSysLog, viewSysLog, editSysLog },
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            // 表格高度
            tableHeight: 'calc(95vh - 200px)',
            search: '',
            tableTitle: [
                {prop: 'id', label: 'ID', fixed: true, sort: true},
                {prop: 'userName', label: '用户姓名', sort: true, filters: []},
                {prop: 'operation', label: '用户操作'},
                {prop: 'method', label: '方法'},
                {prop: 'params', label: '参数'},
                {prop: 'ip', label: 'IP地址'},
                {prop: 'createBy', label: '创建人'},
                {prop: 'createTime', label: '创建时间', formatter: this.dateTimeFormatter},
                {prop: 'lastUpdateBy', label: '更新人'},
                {prop: 'lastUpdateTime', label: '更新时间', formatter: this.dateTimeFormatter},
                // 此处为操作栏，不需要可以删除，clickFun绑定此操作按钮的事件
                {prop: 'operation', label: '操作', fixed: 'right', width: 143,
                    operation: [
                        {name: '查看', style: 'primary', clickFun: this.viewSysLog, disabled: true},
                        {name: '修改', style: 'primary', clickFun: this.editViewSysLog},
                    ]
                }
            ],
            tableData: [],
            multipleSelection: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
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
        let that = this
        that.initTable()
    },
    methods: {
        initTable: function(){
            let that = this
            // 定义请求参数
            let params = { 
                search: that.search,
                pageNum: that.pageNum,
                pageSize: that.pageSize
            }
            // 调用接口
            API.findSysLogList(params).then(function (result) {
                if (result.code === 200) {
                    that.total = result.map.sysLogs.total
                    that.currentPage = result.map.sysLogs.pageNum
                    that.pageSize = result.map.sysLogs.pageSize
                    that.tableData = result.map.sysLogs.list

                    that.filtersHandler(that.tableData)
                }
            });
        },
        receiveChild: function(data){
            let that = this
            that.multipleSelection = data
            console.log("父组件接收的数据", that.multipleSelection)
        },
        addSysLog: function(){
            let that = this
            that.$refs['addSysLog'].openDialog()
        },
        viewSysLog: function(row){
            let that = this
            that.$refs['viewSysLog'].openDialog(row)
        },
        editViewSysLog: function(row){
            let that = this
            that.$refs['editSysLog'].openDialog(row)
        },
        deleteSysLog: function(){
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
            API.deleteSysLog(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$message({
                        message: '恭喜你，删除成功',
                        type: 'success'
                    });
                }
            });
        },
        dateTimeFormatter: function(row, column, cellValue, index){
            if(cellValue == undefined || cellValue == null || cellValue == ''){
                return ''
            }

            return this.$moment(cellValue).format('YYYY-MM-DD HH:mm:ss')
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