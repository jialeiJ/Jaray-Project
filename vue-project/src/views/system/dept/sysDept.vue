<template>
    <div>
        <div id="table">
            <div style="margin-bottom: 10px">
                <el-input
                    placeholder="请输入姓名"
                    v-model="search"
                    clearable style="width: 200px">
                </el-input>
                <el-button type="success" @click="initTable" plain>查询</el-button>
                <el-button type="success" v-if="hasPermission('sys:dept:add')" @click="addSysDept" plain>增加</el-button>
                <el-button type="danger" v-if="hasPermission('sys:dept:delete')" @click="deleteSysDept" plain>删除</el-button>
            </div>
            <i-tree-table ref="iTable" 
                @transmitParent="receiveChild"
                @handleView="viewSysDept"
                @handleEdit="editViewSysDept"
                :tableTitle="tableTitle" 
                :tableData="tableData"
                :tableHeight="tableHeight">
            </i-tree-table>
            <i-pagination ref="iPagination" 
                :total="total"
                :currentPage="pageNum"
                :pageSize="pageSize"
                @handleSizeChange="handleSizeChange"
                @handleCurrentChange="handleCurrentChange">
            </i-pagination>
        </div>

        <!-- 新增 -->
        <add-sys-dept ref="addSysDept" :deptOptions="deptOptions"></add-sys-dept>

        <!-- 编辑 -->
        <edit-sys-dept ref="editSysDept" :deptOptions="deptOptions" :tileDeptData="tileDeptData"></edit-sys-dept>

        <!-- 详情 -->
        <view-sys-dept ref="viewSysDept" :deptOptions="deptOptions" :tileDeptData="tileDeptData"></view-sys-dept>
    </div>
</template>

<script>
import API from '@api/api_sys_dept'
import iTreeTable from '@components/common/iTreeTable'
import iPagination from '@components/common/iPagination'
import addSysDept from '@views/system/dept/addSysDept'
import editSysDept from '@views/system/dept/editSysDept'
import viewSysDept from '@views/system/dept/viewSysDept'

export default {
    name: 'sysDept',
    components: { iTreeTable, iPagination, addSysDept, editSysDept, viewSysDept },
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            // 表格高度
            tableHeight: 'calc(95vh - 200px)',
            search: '',
            tableTitle: [
                {prop: 'id', label: 'ID', fixed: true, sort: true},
                {prop: 'name', label: '名称', sort: true, filters: []},
                {prop: 'createBy', label: '创建人'},
                {prop: 'createTime', label: '创建时间', formatter: this.dateTimeFormatter},
                {prop: 'lastUpdateBy', label: '更新人'},
                {prop: 'lastUpdateTime', label: '更新时间', formatter: this.dateTimeFormatter},
                // 此处为操作栏，不需要可以删除，clickFun绑定此操作按钮的事件
                {prop: 'operation', label: '操作', fixed: 'right', width: 143,
                    operation: [
                        {name: '查看', style: 'primary', clickFun: this.viewSysDept, disabled: this.hasPermission('sys:dept:view')},
                        {name: '修改', style: 'primary', clickFun: this.editViewSysDept, disabled: this.hasPermission('sys:dept:edit')},
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
            },
            deptOptions: [],
            tileDeptData: [],
        }
    },
    created: function(){
        let that = this
        that.initTable()
    },
    methods: {
        addSysDept: function() {
            let that = this
            that.$refs['addSysDept'].openDialog()
        },
        initTable: function(){
            let that = this
            // 定义请求参数
            let params = { 
                pageNum: that.pageNum,
                pageSize: that.pageSize
            }
            // 调用接口
            API.findSysDeptList(params).then(function (result) {
                if (result.code === 200) {
                    that.total = result.map.sysDepts.total
                    that.currentPage = result.map.sysDepts.pageNum
                    that.pageSize = result.map.sysDepts.pageSize
                    that.tableData = result.map.sysDepts.list

                    that.deptOptions = result.map.sysDepts.list
                    that.tileDeptData = []
                    that.tileDeptList(result.map.sysDepts.list)

                    that.filtersHandler(that.tableData)
                }
            });
        },
        receiveChild: function(data){
            let that = this
            that.multipleSelection = data
            console.log("父组件接收的数据", that.multipleSelection)
        },
        viewSysDept: function(row){
            let that = this
            that.$refs['viewSysDept'].openDialog(row)
        },
        editViewSysDept: function(row){
            let that = this
            that.$refs['editSysDept'].openDialog(row)
        },
        deleteSysDept: function(){
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
            API.deleteSysDept(params).then(function (result) {
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
            if(cellValue == undefined){
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
        },
        // 平铺数据
        tileDeptList: function(deptDataList){
            let that = this
            deptDataList.forEach(function(item, index){
                that.tileDeptData.push(item)
                if(item.children && item.children.length){
                    that.tileDeptList(item.children)
                }
            })
        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
/deep/ .el-cascader {
    display: block;
    position: relative;
}
</style>