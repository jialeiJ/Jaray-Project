<template>
    <el-table
        border
        size="mini"
        :data="tableData"
        :key="iKey"
        max-height="750px"
        tooltip-effect="dark"
        :highlight-current-row="true"
        @selection-change="handleSelectionChange"
        @filter-change="handlerFilterChange"
        style="width: 100%;box-shadow: 3px 6px 5px #888888;">
        <el-table-column
            type="selection"
            width="40">
        </el-table-column>
        <template v-for="(item, key) in tableTitle" >
            <el-table-column v-if="item.filters"
                :key="key"
                :fixed="item.fixed"
                :prop="item.prop"
                :label="item.label"
                :width="item.width"
                :sortable="item.sort"
                :column-key="item.prop"
                :filters="item.filters"
                :filter-method="filterHandler"
                :formatter="item.formatter"
                :show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column v-else
                :key="key"
                :fixed="item.fixed"
                :prop="item.prop"
                :label="item.label"
                :width="item.width"
                :sortable="item.sort"
                :column-key="item.prop"
                :formatter="item.formatter"
                :show-overflow-tooltip="true">
            </el-table-column>
        </template>
        <el-table-column
            fixed="right"
            label="操作"
            width="90">
            <template slot-scope="scope">
                <el-button @click="handleView(scope.$index, scope.row)" type="text" size="mini">查看</el-button>
                <el-button @click="handleEdit(scope.$index, scope.row)" type="text" size="mini">编辑</el-button>
            </template>
        </el-table-column>
    </el-table>
</template>

<script>

export default {
    name: 'iTable',
    props: {
        tableTitle: {
            type: Array,
            default: function(){
                return []
            }
        },
        tableData: {
            type: Array,
            default: function(){
                return []
            }
        }
    },
    data () {
        return {
            search: '',
            multipleSelection: [],
            currentPage: 1,
            // 表格高度自适应
            iKey: 1
        }
    },
    methods: {
        handleClick: function(row) {
            // console.log(row);
        },
        handleView: function(index, row){
            // console.log(index, row)
            let that = this
            that.$emit('handleView', row)
        },
        handleEdit: function(index, row){
            // console.log(index, row)
            let that = this
            that.$emit('handleEdit', row)
        },
        handleSelectionChange: function(val) {
            let that = this
            that.multipleSelection = val;
            that.transmitParent()
        },
        handlerFilterChange: function(val){
            // console.log(val)
        },
        transmitParent(){
            let that = this
            that.$emit('transmitParent', that.multipleSelection)
        },
        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] === value;
        }
    },
    watch: {
        tableLoading: function() {
            ++this.iKey
        }
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
