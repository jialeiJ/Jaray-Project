<template>
    <a-tree
        ref="tree"
        checkable
        :replace-fields="replaceFields"
        :tree-data="treeData"
        :checked-keys="checkedKeys"
        :selected-keys="selectedKeys"
        @expand="onExpand"
        @check="onCheck"
        @select="onSelect"
        :height="treeHeight">
        <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>{{ node }} - {{ data }}</span>
            <span>
                <a-button
                    type="text"
                    size="small"
                    @click="() => append(data)">
                    Append
                </a-button>
                <a-button
                    type="text"
                    size="small"
                    @click="() => remove(node, data)">
                    Delete
                </a-button>
            </span>
      </span>
    </a-tree>
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
            replaceFields: {
                children: 'children',
                title: 'title',
                key: 'value'
            },
            checkedKeys:[],
            selectedKeys: [],
            tileTreeData: [],
            treeHeight: 0
        }
    },
    methods: {
        onExpand(expandedKeys) {
            console.log('onExpand', expandedKeys);
            this.expandedKeys = expandedKeys;
            this.autoExpandParent = false;
        },
        onCheck(checkedKeys) {
            console.log('onCheck', checkedKeys);
            this.checkedKeys = checkedKeys;

            let that = this
            let keys = []
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
        onSelect(selectedKeys, info) {
            console.log('onSelect', info);
            this.selectedKeys = selectedKeys;
        },
        allChecked(){
            let that = this
            let keys = []
            that.tileTreeData.forEach(function(item, index){
                let j = keys.indexOf(item.id);
                if(j == -1){
                    keys.push(item.id)
                }
            })
            that.checkedKeys = keys
            //that.$refs.tree.setCheckedKeys(keys);
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
            that.checkedKeys = keys
            //that.$refs.tree.setCheckedKeys(keys);
            //that.handleCheck()
        },
        // 清空
        clearChecked() {
            let that = this
            that.checkedKeys = []
            //that.$refs['tree'].setCheckedKeys([]);
            //that.handleCheck()
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
