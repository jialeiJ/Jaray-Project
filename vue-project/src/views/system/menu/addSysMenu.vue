<template>
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
                        v-model="addForm.menuPids"
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
                    <el-input v-model="addForm.createBy" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="创建时间" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="addForm.createTime"
                        type="datetime"
                        placeholder="选择日期时间"
                        align="right"
                        format="yyyy-MM-dd HH:mm:ss"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="更新人" :label-width="formLabelWidth">
                    <el-input v-model="addForm.lastUpdateBy" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="更新时间" :label-width="formLabelWidth">
                    <el-date-picker
                        v-model="addForm.lastUpdateTime"
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
</template>

<script>
import MENU_API from '@api/api_sys_menu'

export default {
    name: 'addSysMenu',
    props: { dirTreeData: Array },
    components: {},
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            // 表格高度
            tableHeight: 'calc(95vh - 200px)',
            addDialogFormVisible: false,
            addForm: {},
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
            tileMenuData: [],
            // 菜单ids,用于回显
            menuIds: [],
            typeOptions: [],
        }
    },
    created: function(){
        
    },
    methods: {
        openDialog: function() {
            let that = this
            that.addDialogFormVisible = true
        },
        addSysMenu: function(){
            let that = this
            // 目录/菜单父id赋值
            if(that.addForm.menuPids){
                that.addForm.parentId = that.addForm.menuPids[that.addForm.menuPids.length-1]
            }
            
            // 定义请求参数
            let params = that.addForm
            // 调用接口
            MENU_API.addSysMenu(params).then(function (result) {
                if (result.code === 200) {
                    that.$parent.initTable()
                    that.$message({
                        message: '恭喜你，添加成功',
                        type: 'success'
                    });
                    that.addDialogFormVisible = false
                    that.addForm = {}
                }
            });
        },
        handleChange(value) {
            let that = this
            that.parentId = value[value.length-1]
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
                    
                    if(data.length){
                        that.getDirTreeData(dirTreeData[i].children)    
                    }else{
                        continue
                    }
                    
                }
            }
        },
    },
    mounted: function(){
        
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
/deep/ .el-dialog .el-dialog__body {
  border-top: 1px solid #dcdfe6;
  border-bottom: 1px solid #dcdfe6;
  max-height: calc(85vh - 260px); 
  overflow-y: auto;
}
</style>