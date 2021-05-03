<template>
    <div>
        <div id="table">
            <div style="margin-bottom: 10px">
                <a-input
                    placeholder="请输入姓名"
                    v-model="search"
                    clearable :style="{width: '200px'}">
                </a-input>
                <a-button type="success" @click="initTable" plain>查询</a-button>
                <a-button type="success" v-if="hasPermission('sys:user:add')" @click="addSysUser" plain>增加</a-button>
                <a-button type="danger" v-if="hasPermission('sys:user:delete')" @click="deleteSysUser" plain>删除</a-button>
            </div>

            <i-table ref="iTable"
                @transmit_parent="receiveChild"
                :tableTitle="tableTitle" 
                :tableData="tableData"
                :tableHeight="tableHeight">
            </i-table>
            <i-pagination ref="iPagination" 
                :total="total"
                :currentPage="currentPage"
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
            // 表格高度
            tableHeight: 'calc(95vh - 200px)',
            search: '',
            tableTitle: [
                {title: 'ID', dataIndex: 'id', key: 'id', width: '100px', fixed: 'left'},
                {title: '姓名', dataIndex: 'name', key: 'name', width: '100px', filters: []},
                {title: '邮箱', dataIndex: 'email', key: 'email', width: '120px'},
                {title: '手机号', dataIndex: 'mobile', key: 'mobile', width: '120px'},
                {title: '状态', dataIndex: 'status', key: 'status', width: '100px', formatter: this.statusFormatter},
                {title: '部门', dataIndex: 'deptId', key: 'deptId', width: '160px', formatter: this.deptFormatter},
                {title: '角色', dataIndex: 'roleId', key: 'roleId', width: '160px', formatter: this.roleFormatter},
                {title: '创建人', dataIndex: 'createBy', key: 'createBy', width: '160px'},
                {title: '创建时间', dataIndex: 'createTime', key: 'createTime', width: '160px', formatter: this.dateTimeFormatter},
                {title: '更新人', dataIndex: 'lastUpdateBy', key: 'lastUpdateBy', width: '160px'},
                {title: '更新时间', dataIndex: 'lastUpdateTime', key: 'lastUpdateTime', width: '160px', formatter: this.dateTimeFormatter},
                // 此处为操作栏，不需要可以删除，clickFun绑定此操作按钮的事件
                {prop: 'operation', title: '操作', dataIndex: 'operation', key: 'operation', fixed: 'right', width: '120px',
                    operation: [
                        {name: '查看', style: 'primary', clickFun: this.viewSysUser, disabled: this.hasPermission('sys:user:view')},
                        {name: '修改', style: 'primary', clickFun: this.editViewSysUser, disabled: this.hasPermission('sys:user:view')},
                    ]
                }
            ],
            tableData: [],
            selectedRowKeys: [],
            total: 0,
            currentPage: 1,
            pageSize: 10,
            currentPage: 1,
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
                pageNum: that.currentPage,
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
            that.selectedRowKeys = data
            console.log("表格组件接收子组件的数据", that.selectedRowKeys)
        },
        deleteSysUser: function(){
            let that = this
            let ids = new Array()
            for(var i=0;i<that.selectedRowKeys.length;i++){
                ids.push(that.selectedRowKeys[i])
            }
            // 定义请求参数
            let params = { 
                ids: ids.join(',')
            }
            // 调用接口
            USER_API.deleteSysUserByIds(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$message.success('恭喜你，删除成功');
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
            // 当选择页面显示条数时初始化当前页
            that.currentPage = 1
            that.pageSize = pageSize
            that.initTable()
        },
        handleCurrentChange: function(currentPage){
            let that = this
            that.currentPage = currentPage
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
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
</style>