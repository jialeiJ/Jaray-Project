<template>
    <div>
        <div id="table">
            <div style="margin-bottom: 10px">
                <a-input
                    placeholder="请输入姓名"
                    v-model="search"
                    clearable style="width: 200px">
                </a-input>
                <a-button type="success" @click="initTable" plain>查询</a-button>
                <a-button type="success" v-if="hasPermission('sys:user:add')" @click="addVideo" plain>增加</a-button>
                <a-button type="danger" v-if="hasPermission('sys:user:delete')" @click="deleteVideo" plain>删除</a-button>
            </div>

            <i-table ref="iTable"
                @transmitParent="receiveChild"
                @rowClick="rowClick"
                @handleView="viewVideo"
                @handleEdit="editViewVideo"
                :tableTitle="tableTitle" 
                :tableData="tableData"
                :tableHeight="tableHeight">
            </i-table>
            <!-- <i-pagination ref="iPagination" 
                :total="total"
                :currentPage="pageNum"
                :pageSize="pageSize"
                @handleSizeChange="handleSizeChange"
                @handleCurrentChange="handleCurrentChange">
            </i-pagination> -->
        </div>

        <!-- 新增 -->
        <add-video ref="addVideo" :deptOptions="deptOptions" :statusOptions="statusOptions"></add-video>

        <!-- 详情 -->
        <view-video ref="viewVideo" :deptOptions="deptOptions" :statusOptions="statusOptions" :tileDeptData="tileDeptData" :sysRoleList="sysRoleList"></view-video>

        <!-- 编辑 -->
        <edit-video ref="editVideo" :deptOptions="deptOptions" :statusOptions="statusOptions" :tileDeptData="tileDeptData" :sysRoleList="sysRoleList"></edit-video>
    </div>
</template>

<script>
import VIDEO_API from '@api/api_video'
import DICT_API from '@api/api_sys_dict'
import DEPT_API from '@api/api_sys_dept'
import iTable from '@components/common/iTable'
import iPagination from '@components/common/iPagination'
import addVideo from '@views/onlineEducation/addVideo'
import editVideo from '@views/onlineEducation/editVideo'
import viewVideo from '@views/onlineEducation/viewVideo'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'Video',
    components: { iTable, iPagination, addVideo, editVideo, viewVideo },
    data () {
        return {
            // 表格高度
            tableHeight: 'calc(95vh - 200px)',
            search: '',
            tableTitle: [
                {title: 'ID', dataIndex: 'id', key: 'id', sort: true,},
                {title: '名称', dataIndex: 'name', key:'name', sort: true, filters: []},
                {title: '类别', dataIndex: 'category', key:'category', ellipsis: true, filters: []},
                {title: '视频ID', dataIndex: 'videoId', key:'videoId', ellipsis: true,},
                {title: '视频地址', dataIndex: 'videoAddr', key:'videoAddr', ellipsis: true,},
                // 此处为操作栏，不需要可以删除，clickFun绑定此操作按钮的事件
                // {title: '操作',
                //     dataIndex: 'operate',
                //     key: 'operate',
                //     customRender: (text, row, index) => {
                //         return '123';
                //     }
                // },
                {title: '操作', dataIndex: 'operation', key:'operation', fixed: 'right', width: 180,
                    operation: [
                        {name: '查看', style: 'primary', clickFun: this.viewVideo, disabled: true},
                        {name: '修改', style: 'primary', clickFun: this.editViewVideo, disabled: true},
                    ]
                }
            ],
            tableData: [],
            multipleSelection: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
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
        //that.findSysDictByDesc()
    },
    methods: {
        addVideo: function(){
            let that = this
            that.$refs['addVideo'].openDialog()
        },
        viewVideo: function(row){
            let that = this
            that.$refs['viewVideo'].openDialog(row)
        },
        editViewVideo: function(row){
            let that = this
            that.$refs['editVideo'].openDialog(row)
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
            VIDEO_API.findAllVideo(params).then(function (result) {
                if (result.code === 200) {
                    that.sysDeptList = result.map.sysDeptList
                    that.sysRoleList = result.map.sysRoleList

                    that.total = result.map.videoList.total
                    that.currentPage = result.map.videoList.pageNum
                    that.pageSize = result.map.videoList.pageSize
                    that.tableData = result.map.videoList.list

                    that.filtersHandler(that.tableData)
                }
            });
        },
        receiveChild: function(data){
            let that = this
            that.multipleSelection = data
            console.log("表格组件接收子组件的数据", that.multipleSelection)
        },
        deleteVideo: function(id){
            console.log(id)
            return false;
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
            VIDEO_API.deleteVideoByIds(params).then(function (result) {
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