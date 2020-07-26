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
                    <el-button type="success" v-if="hasPermission('sys:user:add')" @click="addDialogFormVisible = true;" plain>增加</el-button>
                    <el-button type="danger" v-if="hasPermission('sys:user:delete')" @click="deleteSysUser" plain>删除</el-button>
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

            <div id="add">
                <el-dialog title="新增" :visible.sync="addDialogFormVisible">
                    <el-form :model="addForm">
                        <el-form-item label="ID" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-input v-model="addForm.id" autocomplete="off" placeholder="自动生成ID，无需填写"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="姓名" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-input v-model="addForm.name" autocomplete="off"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="部门" :label-width="formLabelWidth">
                            <el-cascader
                                v-model="addForm.dept_ids"
                                :options="deptOptions"
                                :props="{ expandTrigger: 'hover', checkStrictly: true }"
                                @change="handleChange"
                                clearable></el-cascader>
                        </el-form-item>
                        <el-form-item label="邮箱" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-input v-model="addForm.email" autocomplete="off"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="手机号" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-input v-model="addForm.mobile" autocomplete="off"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="状态" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-select v-model="addForm.status" placeholder="请选择状态">
                                    <el-option
                                    v-for="item in statusOptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="创建人" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-input v-model="addForm.create_by" autocomplete="off"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="创建时间" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-date-picker
                                    v-model="addForm.create_time"
                                    type="datetime"
                                    placeholder="选择日期时间"
                                    align="right"
                                    format="yyyy-MM-dd HH:mm:ss"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    :picker-options="pickerOptions">
                                </el-date-picker>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="更新人" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-input v-model="addForm.last_update_by" autocomplete="off"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="更新时间" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-date-picker
                                    v-model="addForm.last_update_time"
                                    type="datetime"
                                    placeholder="选择日期时间"
                                    align="right"
                                    format="yyyy-MM-dd HH:mm:ss"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    :picker-options="pickerOptions">
                                </el-date-picker>
                            </el-col>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="addDialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="addSysUser">确 定</el-button>
                    </div>
                </el-dialog>
            </div>

            <div id="view">
                <el-dialog title="详情" :visible.sync="viewDialogFormVisible">
                    <el-form :model="viewForm">
                        <el-form-item label="ID" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-input v-model="viewForm.id" readonly="readonly"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="姓名" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-input v-model="viewForm.name" readonly="readonly"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="部门" :label-width="formLabelWidth">
                            <el-cascader
                                v-model="viewForm.dept_ids"
                                :options="deptOptions"
                                :props="{ expandTrigger: 'hover', checkStrictly: true }"
                                @change="handleChange"></el-cascader>
                        </el-form-item>
                        <el-form-item label="角色" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-select v-model="role_ids" multiple placeholder="请选择角色">
                                    <el-option
                                    v-for="item in sysRoleList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="邮箱" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-input v-model="viewForm.email" readonly="readonly"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="手机号" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-input v-model="viewForm.mobile" readonly="readonly"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="状态" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-select v-model="viewForm.status" placeholder="请选择状态">
                                    <el-option
                                    v-for="item in statusOptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="创建人" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-input v-model="viewForm.create_by" readonly="readonly"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="创建时间" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-date-picker
                                    v-model="viewForm.create_time"
                                    type="datetime"
                                    placeholder="选择日期时间"
                                    align="right"
                                    format="yyyy-MM-dd HH:mm:ss"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    :picker-options="pickerOptions">
                                </el-date-picker>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="更新人" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-input v-model="viewForm.last_update_by" readonly="readonly"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="更新时间" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-date-picker
                                    v-model="viewForm.last_update_time"
                                    type="datetime"
                                    placeholder="选择日期时间"
                                    align="right"
                                    format="yyyy-MM-dd HH:mm:ss"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    :picker-options="pickerOptions">
                                </el-date-picker>
                            </el-col>
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
                        <el-form-item label="部门" :label-width="formLabelWidth">
                            <el-cascader
                                v-model="editForm.dept_ids"
                                :options="deptOptions"
                                :props="{ expandTrigger: 'hover', checkStrictly: true }"
                                @change="handleChange"></el-cascader>
                        </el-form-item>
                        <el-form-item label="角色" :label-width="formLabelWidth">
                            <el-col :span="24">
                                <el-select v-model="role_ids" multiple placeholder="请选择角色">
                                    <el-option
                                    v-for="item in sysRoleList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="邮箱" :label-width="formLabelWidth">
                            <el-input v-model="editForm.email" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="手机号" :label-width="formLabelWidth">
                            <el-input v-model="editForm.mobile" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="状态" :label-width="formLabelWidth">
                            <el-select v-model="editForm.status" placeholder="请选择状态">
                                <el-option
                                v-for="item in statusOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                                </el-option>
                            </el-select>
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
                        <el-button type="primary" @click="editSysUser">确 定</el-button>
                    </div>
                </el-dialog>
            </div>
        </div>
    </div>
</template>

<script>
import USER_API from '../api/api_sys_user'
import DICT_API from '../api/api_sys_dict'
import DEPT_API from '../api/api_sys_dept'
import iTable from '../components/common/iTable'
import iPagination from '../components/common/iPagination'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'sysUser',
    components: { iTable, iPagination },
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            // 表格高度
            tableHeight: 'calc(95vh - 200px)',
            addDialogFormVisible: false,
            viewDialogFormVisible: false,
            editDialogFormVisible: false,
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
                {prop: 'operation', label: '操作', fixed: 'right', width: 175,
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

                    console.log(that.$store.getters.leftMenus)

                    that.filtersHandler(that.tableData)
                } else {
                    that.$message.error(result.msg);// elementUI消息提示
                }
            });
            // 重置请求参数
            params = {}
            // 调用接口
            DEPT_API.findSysDeptList(params).then(function (result) {
                if (result.code === 200) {
                    that.deptOptions = result.map.sysDepts.list
                    that.tileDeptList(result.map.sysDepts.list)
                } else {
                    that.$message.error(result.msg);// elementUI消息提示
                }
            });
        },
        handleChange(value) {
            let that = this
            console.log(value);
        },
        receiveChild: function(data){
            let that = this
            that.multipleSelection = data
            console.log("表格组件接收子组件的数据", that.multipleSelection)
        },
        addSysUser: function(){
            let that = this
            // 定义请求参数
            let params = that.addForm
            // 调用接口
            USER_API.addSysUser(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$message({
                        message: '恭喜你，添加成功',
                        type: 'success'
                    });
                    that.addDialogFormVisible = false
                    that.addForm = {}
                } else {
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        viewSysUser: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            USER_API.viewSysUserById(params).then(function (result) {
                if (result.code === 200) {
                    that.viewForm = result.map.sysUser

                    // 回显角色
                    that.role_ids = []
                    let role_ids = result.map.sysUser.role_id.split(',')
                    for(var i=0;i<role_ids.length;i++){
                        that.role_ids.push(role_ids[i])
                    }

                    // 获取部门id的父id
                    let id = result.map.sysUser.dept_id
                    that.deptIds = []
                    that.getAllPidById(id, that.tileDeptData)
                    // 回显上层机构
                    let dept_ids = that.deptIds.reverse()
                    that.viewForm.dept_ids = dept_ids
                    
                    that.viewDialogFormVisible = true
                } else {
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        getAllPidById: function(id, tileDeptData){
            let that = this
            if(id && that.deptIds.indexOf(id) == -1){
                that.deptIds.push(id)
            }
            tileDeptData.forEach(function(item, index){
                if(item.id == id){
                    that.getAllPidById(item.parent_id, tileDeptData)
                }
            })
        },
        editViewSysUser: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            USER_API.viewSysUserById(params).then(function (result) {
                if (result.code === 200) {
                    that.editForm = result.map.sysUser

                    // 回显角色
                    that.role_ids = []
                    let role_ids = result.map.sysUser.role_id.split(',')
                    for(var i=0;i<role_ids.length;i++){
                        that.role_ids.push(role_ids[i])
                    }

                    // 获取部门id的父id
                    let id = result.map.sysUser.dept_id
                    that.deptIds = []
                    that.getAllPidById(id, that.tileDeptData)
                    // 回显上层机构
                    let dept_ids = that.deptIds.reverse()
                    that.editForm.dept_ids = dept_ids

                    that.editDialogFormVisible = true
                } else {
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        editSysUser: function(){
            let that = this
            that.editForm.dept_id = that.editForm.dept_ids[(that.editForm.dept_ids.length - 1)]
            that.editForm.role_id = that.role_ids.join(',')
            console.log(that.editForm)
            // 定义请求参数
            let params = that.editForm
            // 调用接口
            USER_API.updateSysUserById(params).then(function (result) {
                if (result.code === 200) {
                    that.initTable()
                    that.$message({
                        message: '恭喜你，编辑成功',
                        type: 'success'
                    });
                    that.editDialogFormVisible = false
                } else {
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
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
                } else {
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
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
                } else {
                    that.$message.error(result.msg);// elementUI消息提示
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
        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
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