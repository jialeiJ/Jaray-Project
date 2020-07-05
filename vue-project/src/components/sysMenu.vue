<template>
    <div>
        <div>
            <div id="table">
                <div style="margin-bottom: 10px">
                    <el-button size="mini" @click="addDialogFormVisible = true;">增加</el-button>
                    <el-button size="mini" :loading="loading" @click="deleteSysMenu">删除</el-button>
                </div>
                <i-table ref="iTable" 
                    @transmitParent="receiveChild"
                    @handleView="viewSysMenu"
                    @handleEdit="editViewSysMenu"
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
                        <el-form-item label="名称" :label-width="formLabelWidth">
                            <el-input v-model="addForm.name" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="上级菜单" :label-width="formLabelWidth">
                            <!-- <el-input v-model="addForm.parent_id" autocomplete="off"></el-input> -->
                            <el-select v-model="addForm.parent_id" placeholder="请选择">
                                <el-option
                                v-for="item in menuOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="菜单路径" :label-width="formLabelWidth">
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
                        <el-form-item label="ID" :label-width="formLabelWidth">
                            <el-input v-model="editForm.id" autocomplete="off"></el-input>
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
import iTable from '../components/common/iTable'
import iPagination from '../components/common/iPagination'

export default {
    name: 'sysMenu',
    components: { iTable, iPagination },
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
                {prop: 'type', label: '类型', formatter: this.typeFormatter},
                {prop: 'url', label: '链接'},
                {prop: 'icon', label: '图标'},
                {prop: 'order_num', label: '排序'},
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
            menuOptions: [],
            typeOptions: [],
        }
    },
    created: function(){
        let that = this
        that.initTable()
        that.findAllDir()
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

                    that.filtersHandler(that.tableData)
                } else {
                    that.loading = false;
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
                    that.loading = false;
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
                    that.viewDialogFormVisible = true
                } else {
                    that.loading = false;
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
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
                    that.loading = false;
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
        },
        editSysMenu: function(){
            let that = this
            // 删除该属性
            delete that.editForm.childMenus
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
                    that.loading = false;
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
                    that.loading = false;
                    that.$message.error('失败：'+result.msg);// elementUI消息提示
                }
            });
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
        findAllDir: function(){
            let that = this
            // 定义请求参数
            let params = {}
            // 调用接口
            MENU_API.findAllDir(params).then(function (result) {
                if (result.code === 200) {
                    that.menuOptions = result.map.selectOptions
                } else {
                    that.$message.error(result.msg);// elementUI消息提示
                }
            });
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
h1, h2 {
  font-weight: normal;
}
/* ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
} */
a {
  color: #42b983;
}
</style>