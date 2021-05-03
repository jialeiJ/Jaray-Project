<template>
    <a-table 
        bordered
        size="small"
        :pagination="false"
        :data-source="tableData"
        :rowKey="(row,index)=> row.id?row.id:index"
        :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        :fixed="true"
        :customRow="onClickRow"
        :scroll="{ x: 1500, y: 'calc(95vh - 300px)' }"
        expandRowByClick
        style="margin-bottom: 12px">
        <template v-for="(item, itemIndex) in tableTitle">
            <a-table-column v-if="!item.operation" 
                :key="item.key" 
                :title="item.title"
                :sorter="item.sorter"
                filterMultiple
                :filters="item.filters"
                :data-index="item.dataIndex"
                :fixed="item.fixed"
                :width="item.width"
                :ellipsis="true">
                <!-- formatter：自定义过滤器 -->
                <template slot-scope="text, record, index">
                    <template v-if="item.formatter">
                        <template v-if="item.tagRender != undefined">
                            <!-- ** start ** 这一块做el-tag标签样式判断。不需要可以去除判断 -->
                            <a-tag size="mini" :color="item.tagRender(null, null, record[item.key], null)">{{ item.formatter(null, null, record[item.key], null) }}</a-tag>
                            <!-- ** end ** -->
                        </template>
                        <template v-else>
                            <a-popover :title="item.title">
                                <template slot="content">
                                    {{ item.formatter(null, null, record[item.key], null) }}
                                </template>
                                <template>
                                    {{ item.formatter(null, null, record[item.key], null) }}
                                </template>
                            </a-popover>
                        </template>
                    </template>
                    <template v-else>
                        <a-popover :title="item.title">
                            <template slot="content">
                                {{ record[item.key] }}
                            </template>
                            <template>
                                {{ record[item.key] }}
                            </template>
                        </a-popover>
                    </template>
                </template>
            </a-table-column>
            <a-table-column v-if="item.operation" 
                :key="item.key" 
                :title="item.title" 
                :data-index="item.dataIndex"
                :fixed="item.fixed"
                :width="item.width"
                :ellipsis="true">
                <template slot-scope="text, record">
                    <template v-for="(it, index) in item.operation">
                        <a @click.stop="it.clickFun(record)">{{it.name}}</a>
                        <a-divider v-if="index != (item.operation.length-1)" type="vertical" />
                    </template>
                </template>
            </a-table-column>
        </template>
    </a-table>
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
        },
        pagination: {

        },
        tableHeight: 0
    },
    data () {
        return {
            selectedRowKeys: [],
            currentPage: 1,
            // 表格高度自适应
            iKey: 0
        }
    },
    mounted: function(){
        
    },
    methods: {
        onSelectChange: function(selectedRowKeys) {
            let that = this
            that.selectedRowKeys = selectedRowKeys;
            that.transmitParent()
        },
        onClickRow(record, index) {
            let that = this
            return {
                on: {
                    click: () => {
                        that.$emit('row_click', record)
                    },
                },
            }
        },
        transmitParent(){
            let that = this
            that.$emit('transmit_parent', that.selectedRowKeys)
        },
        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] === value;
        }
    },
    watch: {
        
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
    /deep/ .ant-table-fixed-left table, .ant-table-fixed-right table{
        width: min-content;
    }
</style>