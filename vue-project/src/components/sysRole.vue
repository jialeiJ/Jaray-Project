<template>
    <div>
        <div>
            <div id="table">
                <div style="margin-bottom: 10px">
                    <el-button size="mini" @click="addDialogFormVisible = true;">增加</el-button>
                    <el-button size="mini" :loading="loading" @click="deleteSysRole">删除</el-button>
                </div>
                <i-table ref="iTable" 
                    @transmitParent="receiveChild"
                    @rowClick="rowClick"
                    @handleView="viewSysRole"
                    @handleEdit="editViewSysRole"
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

            <div id="menu">
                <i-tree
                    ref="iTree"
                    @transmitParentKeys="receiveKeys"
                    :treeData="treeData"
                    style="max-height: 300px;overflow-y: auto;"
                ></i-tree>
                <div slot="footer" class="dialog-footer">
                    <el-button size="mini" @click="allChecked">全选</el-button>
                    <el-button size="mini" @click="clearTreeChecked">清空</el-button>
                    <el-button size="mini" @click="resetTreeChecked">重置</el-button>
                    <el-button size="mini" type="primary" @click="editSysRoleMenuPerm">保存</el-button>
                </div>
            </div>

            <div id="add">
                <el-dialog title="新增" :visible.sync="addDialogFormVisible">
                    <el-form :model="addForm">
                        <el-form-item label="ID" :label-width="formLabelWidth">
                            <el-input v-model="addForm.id" autocomplete="off" placeholder="自动生成ID，无需填写"></el-input>
                        </el-form-item>
                        <el-form-item label="姓名" :label-width="formLabelWidth">
                            <el-input v-model="addForm.name" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="备注" :label-width="formLabelWidth">
                            <el-input v-model="addForm.remark" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="创建人" :label-width="formLabelWidth">
                            <el-input v-model="addForm.create_by" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="创建时间" :label-width="formLabelWidth">
                            <el-date-picker
                                v-model="addForm.create_time"
                                type="datetime"
                                placeholder="选择日期时间"
                                align="right"
                                format="yyyy-MM-dd HH:mm:ss"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                :picker-options="pickerOptions">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="更新人" :label-width="formLabelWidth">
                            <el-input v-model="addForm.last_update_by" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="更新时间" :label-width="formLabelWidth">
                            <el-date-picker
                                v-model="addForm.last_update_time"
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
                        <el-button type="primary" @click="addSysRole">确 定</el-button>
                    </div>
                </el-dialog>
            </div>

            <div id="view">
                <el-dialog title="详情" :visible.sync="viewDialogFormVisible">
                    <el-form :model="viewForm">
                        <el-form-item label="ID" :label-width="formLabelWidth">
                            <el-input v-model="viewForm.id" readonly="readonly"></el-input>
                        </el-form-item>
                        <el-form-item label="姓名" :label-width="formLabelWidth">
                            <el-input v-model="viewForm.name" readonly="readonly"></el-input>
                        </el-form-item>
                        <el-form-item label="备注" :label-width="formLabelWidth">
                            <el-input v-model="viewForm.remark" readonly="readonly"></el-input>
                        </el-form-item>
                        <el-form-item label="创建人" :label-width="formLabelWidth">
                            <el-input v-model="viewForm.create_by" readonly="readonly"></el-input>
                        </el-form-item>
                        <el-form-item label="创建时间" :label-width="formLabelWidth">
                            <el-date-picker
                                v-model="viewForm.create_time"
                                type="datetime"
                                placeholder="选择日期时间"
                                align="right"
                                format="yyyy-MM-dd HH:mm:ss"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                :picker-options="pickerOptions">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="更新人" :label-width="formLabelWidth">
                            <el-input v-model="viewForm.last_update_by" readonly="readonly"></el-input>
                        </el-form-item>
                        <el-form-item label="更新时间" :label-width="formLabelWidth">
                            <el-date-picker
                                v-model="viewForm.last_update_time"
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
                        <el-button type="primary" @click="viewDialogFormVisible = false">关闭</el-button>
                    </div>
                </el-dialog>
            </div>

            <div id="edit">
                <el-dialog title="编辑" :visible.sync="editDialogFormVisible">
                    <el-form :model="editForm">
                        <el-form-item label="ID" :label-width="formLabelWidth">
                            <el-input v-model="editForm.id" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="姓名" :label-width="formLabelWidth">
                            <el-input v-model="editForm.name" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="备注" :label-width="formLabelWidth">
                            <el-input v-model="editForm.remark" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="创建人" :label-width="formLabelWidth">
                            <el-input v-model="editForm.create_by" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="创建时间" :label-width="formLabelWidth">
                            <el-date-picker
                                v-model="editForm.create_time"
                                type="datetime"
                                placeholder="选择日期时间"
                                align="right"
                                format="yyyy-MM-dd HH:mm:ss"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                :picker-options="pickerOptions">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="更新人" :label-width="formLabelWidth">
                            <el-input v-model="editForm.last_update_by" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="更新时间" :label-width="formLabelWidth">
                            <el-date-picker
                                v-model="editForm.last_update_time"
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
                        <el-button @click="editDialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="editSysRole">确 定</el-button>
                    </div>
                </el-dialog>
            </div>
        </div>
    </div>
</template>

<script>
import API from '../api/api_sys_role'
import SYS_API from '../api/api_system'
import ROLE_MENU_PERM_API from '../api/api_sys_role_menu_perm'
import ROLE_PERM_API from '../api/api_sys_role_perm'
import iTable from '../components/common/iTable'
import iPagination from '../components/common/iPagination'
import iTree from '../components/common/iTree'

export default {
    name: 'sysRole',
    components: { iTable, iPagination, iTree },
    data () {
        return {
            formLabelWidth: '120px',
            loading: false,
            addDialogFormVisible: false,
            viewDialogFormVisible: false,
            editDialogFormVisible: false,
            search: '',
            tableTitle: [
                {prop: 'id', label: 'ID', fixed: true, sort: true},
                {prop: 'name', label: '名称', sort: true, filters: []},
                {prop: 'remark', label: '备注'},
                {prop: 'create_by', label: '创建人'},
                {prop: 'create_time', label: '创建时间', formatter: this.dateTimeFormatter},
                {prop: 'last_update_by', label: '更新人'},
                {prop: 'last_update_time', label: '更新时间', formatter: this.dateTimeFormatter},
            ],
            tableData: [],
            multipleSelection: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            addForm: {},
            viewForm: {},
            editForm: {},
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
            treeData: [],
            menu_perm : {
                role_id: '',
                menu_perm_id: '',
                menu_id: '',
                perm_id: '',
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
                } else {
                    that.loading = false;
                    that.$message.error(result.msg);// elementUI消息提示
                }
            });
        },
        rowClick: function(row) {
            console.log(row)
            let that = this
            that.menu_perm.role_id = row.id

            // 定义请求参数
            let params = {
                role_id: that.menu_perm.role_id
            }
            // 调用接口
            ROLE_MENU_PERM_API.viewSysRoleMenuPermByRid(params).then(function (result) {
                if (result.code === 200) {
                    console.log(result)
                    that.menu_perm.menu_id = result.map.sysRoleMenu.menu_id
                    that.menu_perm.perm_id = result.map.sysRolePerm.perm_id
                    let menuPermId = []
                    menuPermId.push(result.map.sysRoleMenu.menu_id)
                    menuPermId.push(result.map.sysRolePerm.perm_id)
                    that.menu_perm.menu_perm_id = menuPermId.join(',')
                } else {
                    that.menu_perm.menu_perm_id = ''
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
            that.menu_perm.keys = keys
            console.log("树形组件接收子组件的数据", that.menu_perm.keys)
        },
        allChecked: function(){
            let that = this
            that.$refs.iTree.allChecked() 
        },
        resetTreeChecked: function(row){
            let that = this
            let permKeys = that.menu_perm.perm_id.split(",")
            let menuKeys = that.menu_perm.menu_id.split(",")
            
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
            that.$refs.iTree.clearChecked()
        },
        editSysRoleMenuPerm :function(){
            let that = this
            if(that.menu_perm.role_id == '1'){
                that.$message({
                        message: '管理员不允许编辑',
                        type: 'warning'
                    });
                return false
            }

            // 定义请求参数
            let params = {
                role_id: that.menu_perm.role_id,
                menu_perm_id: that.menu_perm.keys.join(',')
            }
            // 调用接口
            ROLE_MENU_PERM_API.updateSysRoleMenuPerm(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$message({
                        message: '恭喜你，编辑成功',
                        type: 'success'
                    });
                } else {
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        addSysRole: function(){
            let that = this
            // 定义请求参数
            let params = that.addForm
            // 调用接口
            API.addSysRole(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$message({
                        message: '恭喜你，添加成功',
                        type: 'success'
                    });
                    that.addDialogFormVisible = false
                    that.addForm = {}
                } else {
                    that.loading = false;
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        viewSysRole: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            API.viewSysRole(params).then(function (result) {
                if (result.code === 200) {
                    that.viewForm = result.map.sysRole
                    that.viewDialogFormVisible = true
                } else {
                    that.loading = false;
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        editViewSysRole: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            API.viewSysRole(params).then(function (result) {
                if (result.code === 200) {
                    that.editForm = result.map.sysRole
                    that.editDialogFormVisible = true
                } else {
                    that.loading = false;
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        editSysRole: function(){
            let that = this
            // 定义请求参数
            let params = that.editForm
            // 调用接口
            API.updateSysRole(params).then(function (result) {
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
                } else {
                    that.loading = false;
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
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
                } else {
                    that.$message.error(result.msg);// elementUI消息提示
                }
            })
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