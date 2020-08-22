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
                <el-button type="success" v-if="hasPermission('sys:user:add')" @click="addSysUser" plain>增加</el-button>
                <el-button type="danger" v-if="hasPermission('sys:user:delete')" @click="deleteSysUser" plain>删除</el-button>
                <el-button type="danger" @click="addTask" plain>添加任务</el-button>
                <el-button type="danger" @click="pauseJob" plain>暂停任务</el-button>
                <el-button type="danger" @click="deleteJob" plain>删除任务</el-button>
            </div>

            <i-table ref="iTable"
                @transmitParent="receiveChild"
                @rowClick="rowClick"
                @handleView="viewSysUser"
                @handleEdit="editViewSysUser"
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
        <add-sys-user ref="addSysUser" :deptOptions="deptOptions" :statusOptions="statusOptions"></add-sys-user>

        <!-- 详情 -->
        <view-sys-user ref="viewSysUser" :deptOptions="deptOptions" :statusOptions="statusOptions" :tileDeptData="tileDeptData" :sysRoleList="sysRoleList"></view-sys-user>

        <!-- 编辑 -->
        <edit-sys-user ref="editSysUser" :deptOptions="deptOptions" :statusOptions="statusOptions" :tileDeptData="tileDeptData" :sysRoleList="sysRoleList"></edit-sys-user>
    </div>
</template>

<script>
import USER_API from '@api/api_sys_user'
import DICT_API from '@api/api_sys_dict'
import DEPT_API from '@api/api_sys_dept'
import iTable from '@components/common/iTable'
import iPagination from '@components/common/iPagination'
import addSysUser from '@views/system/user/addSysUser'
import editSysUser from '@views/system/user/editSysUser'
import viewSysUser from '@views/system/user/viewSysUser'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'sysUser',
    components: { iTable, iPagination, addSysUser, editSysUser, viewSysUser },
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            // 表格高度
            tableHeight: 'calc(95vh - 200px)',
            search: '',
            tableTitle: [
                {prop: 'id', label: 'ID', fixed: true, sort: true},
                {prop: 'name', label: '姓名', sort: true, filters: []},
                {prop: 'email', label: '邮箱'},
                {prop: 'mobile', label: '手机号'},
                {prop: 'status', label: '状态', formatter: this.statusFormatter},
                {prop: 'dept_id', label: '部门', formatter: this.deptFormatter},
                {prop: 'role_id', label: '角色', formatter: this.roleFormatter},
                {prop: 'create_by', label: '创建人'},
                {prop: 'create_time', label: '创建时间', formatter: this.dateTimeFormatter},
                {prop: 'last_update_by', label: '更新人'},
                {prop: 'last_update_time', label: '更新时间', formatter: this.dateTimeFormatter},
                // 此处为操作栏，不需要可以删除，clickFun绑定此操作按钮的事件
                {prop: 'operation', label: '操作', fixed: 'right', width: 143,
                    operation: [
                        {name: '查看', style: 'primary', clickFun: this.viewSysUser, disabled: this.hasPermission('sys:user:view')},
                        {name: '修改', style: 'primary', clickFun: this.editViewSysUser, disabled: this.hasPermission('sys:user:view')},
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
            statusOptions: [],
            sysDeptList: [],
            sysRoleList: [],
            role_ids: [],
            deptOptions: [],
            tileDeptData: [],
            // 部门ids,用于回显
            deptIds: []
        }
    },
    created: function(){
        let that = this
        that.initTable()
        that.findSysDictByDesc()
    },
    methods: {
        addSysUser: function(){
            let that = this
            that.$refs['addSysUser'].openDialog()
        },
        viewSysUser: function(row){
            let that = this
            that.$refs['viewSysUser'].openDialog(row)
        },
        editViewSysUser: function(row){
            let that = this
            that.$refs['editSysUser'].openDialog(row)
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
            USER_API.findAllSysUser(params).then(function (result) {
                if (result.code === 200) {
                    that.sysDeptList = result.map.sysDeptList
                    that.sysRoleList = result.map.sysRoleList

                    that.total = result.map.sysUsers.total
                    that.currentPage = result.map.sysUsers.pageNum
                    that.pageSize = result.map.sysUsers.pageSize
                    that.tableData = result.map.sysUsers.list

                    that.filtersHandler(that.tableData)
                }
            });
            // 重置请求参数
            params = {}
            // 调用接口
            DEPT_API.findSysDeptList(params).then(function (result) {
                if (result.code === 200) {
                    that.deptOptions = result.map.sysDepts.list
                    that.tileDeptList(result.map.sysDepts.list)
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
            USER_API.deleteSysUserByIds(params).then(function (result) {
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
            let that = this
            if(cellValue == undefined){
                return ''
            }
            return that.$moment(cellValue).format('YYYY-MM-DD HH:mm:ss')
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
        findSysDictByDesc: function(){
            let that = this
            // 定义请求参数
            let params = {description: '用户状态'}
            // 调用接口
            DICT_API.findSysDictByDesc(params).then(function (result) {
                if (result.code === 200) {
                    that.statusOptions = result.map.selectOptions
                }
            });
        },
        statusFormatter: function(row, column, cellValue, index){
            if(cellValue == undefined){
                return ''
            }
            let result = ''
            for(var i=0;i<this.statusOptions.length;i++){
                if(cellValue == this.statusOptions[i].value){
                    result = this.statusOptions[i].label
                }
            }

            return result;
        },
        deptFormatter: function(row, column, cellValue, index){
            if(cellValue == undefined){
                return ''
            }
            let result = ''
            for(var i=0;i<this.sysDeptList.length;i++){
                if(cellValue == this.sysDeptList[i].value){
                    result = this.sysDeptList[i].label
                }
            }

            return result;
        },
        roleFormatter: function(row, column, cellValue, index){
            if(cellValue == undefined){
                return ''
            }
            let result = ''
            let valueArr = cellValue.split(',')
            if(valueArr.length > 1){
                let resultArr = []
                for(var i=0;i<valueArr.length;i++){
                    for(var j=0;j<this.sysRoleList.length;j++){
                        if(valueArr[i] == this.sysRoleList[j].value){
                            resultArr.push(this.sysRoleList[j].remark)
                        }
                    }
                }
                result = resultArr.join(',')
            }else{
                for(var i=0;i<this.sysRoleList.length;i++){
                    if(cellValue == this.sysRoleList[i].value){
                        result = this.sysRoleList[i].remark
                    }
                }
            }
            
            return result;
        },
        // 平铺数据
        tileDeptList(deptDataList){
            let that = this
            deptDataList.forEach(function(item, index){
                that.tileDeptData.push(item)
                if(item.children && item.children.length){
                    that.tileDeptList(item.children)
                }
            })
        },
        addTask: function() {
            let that = this
            // 定义请求参数
            let params = {}
            // 调用接口
            USER_API.addTask(params).then(function (result) {
                if (result.code === 200) {
                    
                }
            });
        },
        pauseJob: function(){
            let that = this
            // 定义请求参数
            let params = {}
            // 调用接口
            USER_API.pauseJob(params).then(function (result) {
                if (result.code === 200) {
                    
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