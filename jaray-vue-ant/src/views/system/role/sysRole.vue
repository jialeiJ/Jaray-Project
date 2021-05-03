<template>
    <div>
        <div>
            <div id="menu">
                <i-tree
                    ref="iTree"
                    @rowClick="rowClick"
                    @transmitParentKeys="receiveKeys"
                    :treeData="treeData"
                    style="max-height: 400px;overflow-y: auto;">
                </i-tree>
                <div slot="footer" class="dialog-footer">
                    <a-button size="small" @click="allChecked">全选</a-button>
                    <a-button size="small" @click="clearTreeChecked">清空</a-button>
                    <a-button size="small" @click="resetTreeChecked">重置</a-button>
                    <a-button size="small" type="primary" v-if="hasPermission('sys:role:edit')" @click="editSysRoleMenuPerm">保存</a-button>
                </div>
            </div>

            <div id="table">
                <div style="margin-bottom: 10px">
                    <a-input
                        placeholder="请输入姓名"
                        v-model="search"
                        clearable style="width: 200px">
                    </a-input>
                    <a-button type="success" @click="initTable" plain>查询</a-button>
                    <a-button type="success" v-if="hasPermission('sys:role:add')" @click="addSysRole" plain>增加</a-button>
                    <a-button type="danger" v-if="hasPermission('sys:role:delete')" @click="deleteSysRole" plain>删除</a-button>
                </div>
                <i-table ref="iTable" 
                    @transmitParent="receiveChild"
                    @row_click="rowClick"
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

            <!-- <div id="menu">
                <i-tree
                    ref="iTree"
                    @rowClick="rowClick"
                    @transmitParentKeys="receiveKeys"
                    :treeData="treeData"
                    style="max-height: 300px;overflow-y: auto;">
                </i-tree>
                <div slot="footer" class="dialog-footer">
                    <a-button size="small" @click="allChecked">全选</a-button>
                    <a-button size="small" @click="clearTreeChecked">清空</a-button>
                    <a-button size="small" @click="resetTreeChecked">重置</a-button>
                    <a-button size="small" type="primary" v-if="hasPermission('sys:role:edit')" @click="editSysRoleMenuPerm">保存</a-button>
                </div>
            </div> -->

            <!-- 新增 -->
            <add-sys-role ref="addSysRole"></add-sys-role>

            <!-- 详情 -->
            <view-sys-role ref="viewSysRole"></view-sys-role>

            <!-- 编辑 -->
            <edit-sys-role ref="editSysRole"></edit-sys-role>
        </div>
    </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
import API from '@api/api_sys_role'
import SYS_API from '@api/api_system'
import ROLE_MENU_PERM_API from '@api/api_sys_role_menu_perm'
import ROLE_PERM_API from '@api/api_sys_role_perm'
import iTable from '@components/common/iTable'
import iPagination from '@components/common/iPagination'
import iTree from '@components/common/iTree'
import addSysRole from '@views/system/role/addSysRole'
import viewSysRole from '@views/system/role/viewSysRole'
import editSysRole from '@views/system/role/editSysRole'

export default {
    name: 'sysRole',
    components: { iTable, iPagination, iTree, addSysRole, viewSysRole, editSysRole },
    data () {
        return {
            // 表格高度
            tableHeight: 'calc(65vh - 260px)',
            loading: false,
            search: '',
            tableTitle: [
                {title: 'ID', dataIndex: 'id', key: 'id', },
                {title: '名称', dataIndex: 'name', key: 'name', filters: []},
                {title: '备注', dataIndex: 'remark', key: 'remark'},
                {title: '创建人', dataIndex: 'createBy', key: 'createBy'},
                {title: '创建时间', dataIndex: 'createTime', key: 'createTime', formatter: this.dateTimeFormatter},
                {title: '更新人', dataIndex: 'lastUpdateBy', key: 'lastUpdateBy'},
                {title: '更新时间', dataIndex: 'lastUpdateTime', key: 'lastUpdateTime', formatter: this.dateTimeFormatter},
                // 此处为操作栏，不需要可以删除，clickFun绑定此操作按钮的事件
                {prop: 'operation', label: '操作', fixed: 'right', width: 143,
                    operation: [
                        {name: '查看', style: 'primary', clickFun: this.viewSysRole, disabled: this.hasPermission('sys:role:view')},
                        {name: '修改', style: 'primary', clickFun: this.editViewSysRole, disabled: this.hasPermission('sys:role:edit')},
                    ]
                }
            ],
            tableData: [],
            multipleSelection: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            treeData: [],
            menuPerm : {
                roleId: '',
                menuPermId: '',
                menuId: '',
                permId: '',
                keys: []
            },
        }
    },
    created: function(){
        let that = this
        that.initTable()
        that.findLeftNav()
    },
    methods: {
        addSysRole: function() {
            let that = this
            that.$refs['addSysRole'].openDialog()
        },
        initTable: function(){
            let that = this
            // 定义请求参数
            let params = { 
                pageNum: that.pageNum,
                pageSize: that.pageSize
            }
            // 调用接口
            API.findSysRoleList(params).then(function (result) {
                if (result.code === 200) {
                    that.total = result.map.sysRoles.total
                    that.currentPage = result.map.sysRoles.pageNum
                    that.pageSize = result.map.sysRoles.pageSize
                    that.tableData = result.map.sysRoles.list

                    that.filtersHandler(that.tableData)
                }
            });
        },
        rowClick: function(row) {
            let that = this
            that.menuPerm.roleId = row.id

            // 定义请求参数
            let params = {
                roleId: that.menuPerm.roleId
            }
            // 调用接口
            ROLE_MENU_PERM_API.viewSysRoleMenuPermByRid(params).then(function (result) {
                if (result.code === 200) {
                    let menuPermId = []
                    if (result.map.sysRoleMenu) {
                        that.menuPerm.menuId = result.map.sysRoleMenu.menuId
                        menuPermId.push(result.map.sysRoleMenu.menuId)
                    }
                    if (result.map.sysRolePerm) {
                        that.menuPerm.permId = result.map.sysRolePerm.permId
                        menuPermId.push(result.map.sysRolePerm.permId)
                    }
                    
                    if (menuPermId && menuPermId.length) {
                        that.menuPerm.menuPermId = menuPermId.join(',')
                    } else {
                        that.menuPerm.menuPermId = ''
                        that.menuPerm.permId = ''
                        that.menuPerm.menuId = ''
                    }
                } else {
                    that.menuPerm.menuPermId = ''
                        that.menuPerm.permId = ''
                        that.menuPerm.menuId = ''
                }
                that.resetTreeChecked()
            });
        },
        receiveChild: function(data){
            let that = this
            that.multipleSelection = data
            console.log("父组件接收的数据", that.multipleSelection)
        },
        receiveKeys: function(keys){
            let that = this
            that.menuPerm.keys = keys
            console.log("树形组件接收子组件的数据", that.menuPerm.keys)
        },
        allChecked: function(){
            let that = this
            that.$refs.iTree.allChecked() 
        },
        resetTreeChecked: function(){
            let that = this
            let permKeys = [];
            if (that.menuPerm.permId != '') {
                permKeys = that.menuPerm.permId.split(",")
            } 
            let menuKeys = [];
            if (that.menuPerm.menuId != '') {
                menuKeys = that.menuPerm.menuId.split(",")
            } 
            
            
            
            for(var i=0;i<menuKeys.length;i++){
                for(var j=0;j<permKeys.length;j++){
                    if(menuKeys[i]==permKeys[j]){
                        menuKeys.splice(i,1)
                    }
                }
            }

            let keys = menuKeys.concat(permKeys)
            that.$refs.iTree.resetChecked(keys)
        },
        clearTreeChecked: function(){
            let that = this
            that.$refs['iTree'].clearChecked()
        },
        editSysRoleMenuPerm :function(){
            let that = this
            if(that.menuPerm.roleId == '1'){
                that.$message({
                        message: '管理员不允许编辑',
                        type: 'warning'
                    });
                return false
            }

            // 定义请求参数
            let params = {
                roleId: that.menuPerm.roleId,
                menuPermId: that.menuPerm.keys.join(',')
            }
            // 调用接口
            ROLE_MENU_PERM_API.updateSysRoleMenuPerm(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$message.success('恭喜你，编辑成功');
                    // 刷新左侧菜单（自己不能修改自己，所以不用刷新）
                    //that.refreshFindLeftNav()
                }
            });
        },
        viewSysRole: function(row){
            let that = this
            that.$refs['viewSysRole'].openDialog(row)
        },
        editViewSysRole: function(row){
            let that = this
            that.$refs['editSysRole'].openDialog(row)
        },
        deleteSysRole: function(){
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
            API.deleteSysRole(params).then(function (result) {
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
        findLeftNav: function(){
            let that = this;
            // 定义请求参数
            let params = {}
            // 调用接口
            SYS_API.findLeftNav(params).then(function (result) {
                if (result.code === 200) {
                    that.treeData =result.map.leftMenu
                }
            })
        },
        refreshFindLeftNav: function(){
            let that = this;
            // 定义请求参数
            let accessUser = JSON.parse(sessionStorage.getItem('access-user'))

            let params = {userId: accessUser.userId}
            // 调用接口
            SYS_API.findLeftNav(params).then(function (result) {
                if (result.code === 200) {
                    that.leftMenus =result.map.leftMenu
                }
            })
        },
        ...mapActions( // 语法糖
            ['modifyLeftMenus'] // 相当于this.$store.dispatch('modifyLeftMenus'),提交这个方法
        ),
    },
    computed: {
        ...mapGetters(['leftMenus']),// 动态计算属性，相当于this.$store.getters.collapsed
        leftMenus: {
            get(){
                return this.$store.state.leftMenus
            },
            set(val){
                this.$store.state.leftMenus = val
            }
        },
    },
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

#table {
  float:left;
  width: 80%;
}
#menu {
  float:left;
  width: 20%;
}
</style>