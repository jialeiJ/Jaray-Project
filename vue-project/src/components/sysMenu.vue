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
                    <el-button type="success" v-if="hasPermission('sys:menu:add')" @click="addDialogFormVisible = true;" plain>增加</el-button>
                    <el-button type="danger" v-if="hasPermission('sys:menu:delete')" @click="deleteSysMenu" plain>删除</el-button>
                </div>
                <i-tree-table ref="iTable" 
                    @transmitParent="receiveChild"
                    @handleView="viewSysMenu"
                    @handleEdit="editViewSysMenu"
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

            <div id="add">
                <el-dialog title="新增" :visible.sync="addDialogFormVisible">
                    <el-form :model="addForm">
                        <el-form-item label="类型" :label-width="formLabelWidth">
                            <el-radio-group v-model="addForm.type">
                                <el-radio :label="0">目录</el-radio>
                                <el-radio :label="1">菜单</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="ID" :label-width="formLabelWidth">
                            <el-input v-model="addForm.id" autocomplete="off" placeholder="自动生成ID，无需填写"></el-input>
                        </el-form-item>
                        <el-form-item label="上级菜单" :label-width="formLabelWidth">
                            <el-cascader
                                v-model="addForm.menu_pids"
                                :options="dirTreeData"
                                :props="{ expandTrigger: 'hover', checkStrictly: true }"
                                @change="handleChange"
                                clearable>
                            </el-cascader>
                        </el-form-item>
                        <el-form-item label="名称" :label-width="formLabelWidth">
                            <el-input v-model="addForm.name" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item v-if="addForm.type == 1" label="菜单路径" :label-width="formLabelWidth">
                            <el-input v-model="addForm.url" :disabled="addForm.type == 0?true:false" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="图标" :label-width="formLabelWidth">
                            <el-input v-model="addForm.icon" autocomplete="off"></el-input>
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
                        <el-button type="primary" @click="addSysMenu">确 定</el-button>
                    </div>
                </el-dialog>
            </div>

            <div id="view">
                <el-dialog title="详情" :visible.sync="viewDialogFormVisible">
                    <el-form :model="viewForm">
                        <el-form-item label="类型" :label-width="formLabelWidth">
                            <el-radio-group v-model="viewForm.type" disabled>
                                <el-radio :label="0">目录</el-radio>
                                <el-radio :label="1">菜单</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="ID" :label-width="formLabelWidth">
                            <el-input v-model="viewForm.id" readonly="readonly"></el-input>
                        </el-form-item>
                        <el-form-item label="上级菜单" :label-width="formLabelWidth">
                            <el-cascader
                                v-model="viewForm.menu_pids"
                                :options="dirTreeData"
                                :props="{ expandTrigger: 'hover', checkStrictly: true }"
                                @change="handleChange"
                                clearable>
                            </el-cascader>
                        </el-form-item>
                        <el-form-item label="名称" :label-width="formLabelWidth">
                            <el-input v-model="viewForm.name" readonly="readonly"></el-input>
                        </el-form-item>
                        <el-form-item label="链接" :label-width="formLabelWidth">
                            <el-input v-model="viewForm.url" readonly="readonly"></el-input>
                        </el-form-item>
                        <el-form-item label="图标" :label-width="formLabelWidth">
                            <el-input v-model="viewForm.icon" readonly="readonly"></el-input>
                        </el-form-item>
                        <el-form-item label="排序" :label-width="formLabelWidth">
                            <el-input v-model="viewForm.order_num" readonly="readonly"></el-input>
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
                        <el-form-item label="类型" :label-width="formLabelWidth">
                            <el-radio-group v-model="editForm.type">
                                <el-radio :label="0">目录</el-radio>
                                <el-radio :label="1">菜单</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="ID" :label-width="formLabelWidth">
                            <el-input v-model="editForm.id" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="上级菜单" :label-width="formLabelWidth">
                            <el-cascader
                                v-model="editForm.menu_pids"
                                :options="dirTreeData"
                                :props="{ expandTrigger: 'hover', checkStrictly: true }"
                                @change="handleChange"
                                clearable>
                            </el-cascader>
                        </el-form-item>
                        <el-form-item label="名称" :label-width="formLabelWidth">
                            <el-input v-model="editForm.name" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="类型" :label-width="formLabelWidth">
                            <el-input v-model="editForm.type" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="链接" :label-width="formLabelWidth">
                            <el-input v-model="editForm.url" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="图标" :label-width="formLabelWidth">
                            <el-input v-model="editForm.icon" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="排序" :label-width="formLabelWidth">
                            <el-input v-model="editForm.order_num" autocomplete="off"></el-input>
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
                        <el-button type="primary" @click="editSysMenu">确 定</el-button>
                    </div>
                </el-dialog>
            </div>
        </div>
    </div>
</template>

<script>
import MENU_API from '../api/api_sys_menu'
import DICT_API from '../api/api_sys_dict'
import iTreeTable from '../components/common/iTreeTable'
import iPagination from '../components/common/iPagination'

export default {
    name: 'sysMenu',
    components: { iTreeTable, iPagination },
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
                {prop: 'name', label: '名称', sort: true, filters: []},
                {prop: 'type', label: '类型', formatter: this.typeFormatter,renderComponent: 'tag'},
                {prop: 'url', label: '链接'},
                {prop: 'icon', label: '图标'},
                {prop: 'order_num', label: '排序'},
                {prop: 'create_by', label: '创建人'},
                {prop: 'create_time', label: '创建时间', formatter: this.dateTimeFormatter},
                {prop: 'last_update_by', label: '更新人'},
                {prop: 'last_update_time', label: '更新时间', formatter: this.dateTimeFormatter},
                // 此处为操作栏，不需要可以删除，clickFun绑定此操作按钮的事件
                {prop: 'operation', label: '操作', fixed: 'right', width: 175,
                    operation: [
                        {name: '查看', style: 'primary', clickFun: this.viewSysMenu, disabled: this.hasPermission('sys:menu:view')},
                        {name: '修改', style: 'primary', clickFun: this.editViewSysMenu, disabled: this.hasPermission('sys:menu:edit')},
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
            dirTreeData: [],
            tileMenuData: [],
            // 菜单ids,用于回显
            menuIds: [],
            typeOptions: [],
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
                pageNum: that.pageNum,
                pageSize: that.pageSize
            }
            // 调用接口
            MENU_API.findSysMenuList(params).then(function (result) {
                if (result.code === 200) {
                    that.total = result.map.sysMenus.total
                    that.currentPage = result.map.sysMenus.pageNum
                    that.pageSize = result.map.sysMenus.pageSize
                    that.tableData = result.map.sysMenus.list

                    that.tileMenuList(result.map.sysMenus.list)

                    // 解决双向修改问题
                    //JSON.parse(JSON.stringify(result.map.sysMenus.list))
                    let dirTreeData = JSON.parse(JSON.stringify(result.map.sysMenus.list))
                    let data = dirTreeData.filter(function(item){
                        return item.type == 0
                    })
                    that.dirTreeData = data
                    that.getDirTreeData(that.dirTreeData)
                    console.log(that.dirTreeData)

                    that.filtersHandler(that.tableData)
                } else {
                    that.$message.error(result.msg);// elementUI消息提示
                }
            });
        },
        receiveChild: function(data){
            let that = this
            that.multipleSelection = data
            console.log("父组件接收的数据", that.multipleSelection)
        },
        addSysMenu: function(){
            let that = this
            // 目录/菜单父id赋值
            that.addForm.parent_id = that.addForm.menu_pids[that.addForm.menu_pids.length-1]
            // 定义请求参数
            let params = that.addForm
            // 调用接口
            MENU_API.addSysMenu(params).then(function (result) {
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
        viewSysMenu: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            MENU_API.viewSysMenu(params).then(function (result) {
                if (result.code === 200) {
                    that.viewForm = result.map.sysMenu

                    // 获取部门id的父id
                    let id = result.map.sysMenu.parent_id
                    that.menuIds = []
                    that.getAllPidById(id, that.tileMenuData)
                    
                    // 回显上层机构
                    let menu_ids = that.menuIds.reverse()
                    that.viewForm.menu_pids = menu_ids
                    console.log(that.viewForm.menu_pids)

                    that.viewDialogFormVisible = true
                } else {
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        getAllPidById: function(id, tileMenuData){
            let that = this
            if(id && that.menuIds.indexOf(id) == -1){
                that.menuIds.push(id)
            }
            tileMenuData.forEach(function(item, index){
                if(item.id == id){
                    that.getAllPidById(item.parent_id, tileMenuData)
                }
            })
        },
        editViewSysMenu: function(row){
            let that = this
            // 定义请求参数
            let params = row
            // 调用接口
            MENU_API.viewSysMenu(params).then(function (result) {
                if (result.code === 200) {
                    that.editForm = result.map.sysMenu
                    that.editDialogFormVisible = true
                } else {
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        editSysMenu: function(){
            let that = this
            // 删除该属性
            delete that.editForm.childMenus
            delete that.editForm.perms

            // 目录/菜单父id赋值
            that.editForm.parent_id = that.editForm.menu_pids[that.editForm.menu_pids.length-1]
            // 定义请求参数
            let params = that.editForm
            // 调用接口
            MENU_API.updateSysMenu(params).then(function (result) {
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
        deleteSysMenu: function(){
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
            MENU_API.deleteSysMenu(params).then(function (result) {
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
        handleChange(value) {
            let that = this
            that.parent_id = value[value.length-1]
            console.log(that.parent_id);
        },
        typeFormatter: function(row, column, cellValue, index){
            if(cellValue == undefined){
                return ''
            }
            let result = ''
            for(var i=0;i<this.typeOptions.length;i++){
                if(cellValue == this.typeOptions[i].value){
                    result = this.typeOptions[i].label
                }
            }

            return result;
        },
        dateTimeFormatter: function(row, column, cellValue, index){
            if(cellValue == undefined){
                return ''
            }

            return this.$moment(cellValue).format('YYYY-MM-DD HH:mm:ss');
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
        getDirTreeData: function(dirTreeData){
            let that = this
            for(var i=0;i<dirTreeData.length;i++){
                if(dirTreeData[i].children && dirTreeData[i].children.length){
                    let data = dirTreeData[i].children.filter(function(item){
                        return item.type == 0
                    })

                    if(data && data.length){
                        dirTreeData[i].children = data
                    }else{
                        // 删除children属性
                        that.$delete(dirTreeData[i], 'children');
                    }
                    
                    //dirTreeData[i].children = data
                    if(data.length){
                        that.getDirTreeData(dirTreeData[i].children)
                        
                    }else{
                        continue
                    }
                    
                }
            }
            console.log(that.dirTreeData)
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
        tileMenuList(menuDataList){
            let that = this
            menuDataList.forEach(function(item, index){
                that.tileMenuData.push(item)
                if(item.children && item.children.length){
                    that.tileMenuList(item.children)
                }
            })
        },
        findSysDictByDesc: function(){
            let that = this
            // 定义请求参数
            let params = {description: '菜单类型'}
            // 调用接口
            DICT_API.findSysDictByDesc(params).then(function (result) {
                if (result.code === 200) {
                    that.typeOptions = result.map.selectOptions
                } else {
                    that.$message.error(result.msg);// elementUI消息提示
                }
            });
        }
    },
    mounted: function(){
        
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/deep/ .el-dialog .el-dialog__body {
  border-top: 1px solid #dcdfe6;
  border-bottom: 1px solid #dcdfe6;
  max-height: calc(85vh - 260px); 
  overflow-y: auto;
}
</style>