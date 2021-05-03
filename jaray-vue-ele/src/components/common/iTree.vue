<template>
    <el-tree
        ref="tree"
        size="mini"
        :data="treeData"
        show-checkbox
        node-key="id"
        highlight-current
        @check="handleCheck"
        :props="defaultProps"
        :height="treeHeight">
        <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>{{ node.label }}</span>
            <span>
                <el-button
                    type="text"
                    size="mini"
                    @click="() => append(data)">
                    Append
                </el-button>
                <el-button
                    type="text"
                    size="mini"
                    @click="() => remove(node, data)">
                    Delete
                </el-button>
            </span>
      </span>
    </el-tree>
</template>

<script>

export default {
    name: 'iTree',
    props: {
        treeData: {
            type: Array,
            default: function(){
                return []
            }
        }
    },
    data () {
        return {
            defaultProps: {
                children: 'children',
                label: 'label'
            },
            tileTreeData: [],
            treeHeight: 0
        }
    },
    methods: {
        handleCheck(data, checked, indeterminate) {
            let that = this
            let keys = []
            let checkedKeys = that.$refs.tree.getCheckedKeys()
            that.tileTreeData.forEach(function(item, index){
                if(checkedKeys.indexOf(item.id) > -1){
                    if(keys.indexOf(item.parent_id) == -1){
                        keys.push(item.parent_id);
                    }
                    keys.push(item.id);
                }
            })

            that.$emit('transmitParentKeys', keys)
        },
        allChecked(){
            let that = this
            let keys = []
            console.log(that.tileTreeData)
            that.tileTreeData.forEach(function(item, index){
                let i = keys.indexOf(item.parent_id);
                if(i == -1){
                    keys.push(item.parent_id)
                }
                let j = keys.indexOf(item.id);
                if(j == -1){
                    keys.push(item.id)
                }
            })
            that.$refs.tree.setCheckedKeys(keys);
            that.$emit('transmitParentKeys', keys)
        },
        // 重置
        resetChecked(keys) {
            let that = this
            // 移除父id
            that.tileTreeData.forEach(function(item, index){
                let i = keys.indexOf(item.parent_id);
                if(i > -1){
                    //keys.splice(i,1)
                }
            })
            that.$refs.tree.setCheckedKeys(keys);
            that.handleCheck()
        },
        // 清空
        clearChecked() {
            let that = this
            that.$refs['tree'].setCheckedKeys([]);
            that.handleCheck()
        },
        // 平铺数据
        tileTree(treeData){
            let that = this
            treeData.forEach(function(item, index){
                that.tileTreeData.push(item)
                if(item.children.length){
                    that.tileTree(item.children)
                }
            })
        }
    },
    created: function(){
        // 渲染前执行,无法获取到父组件的传值
    },
    mounted: function(){
        // 渲染后执行,无法获取到父组件的传值 
    },
    watch: {
        // 使用监听
        treeData: {
            handler(newVal, oldVal){
                if(newVal){
                    this.tileTree(newVal) 
                }
            },
            immediate: true, // 立即执行
            deep: true //深度监听
        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
}
</style>
