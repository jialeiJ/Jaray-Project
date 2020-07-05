<template>
    <el-tree
        :data="treeData"
        show-checkbox
        node-key="id"
        ref="tree"
        highlight-current
        @check="handleCheck"
        :props="defaultProps">
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
            tileTreeData: []
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

        // 重置
        resetChecked(keys) {
            let that = this
            // 移除父id
            that.tileTreeData.forEach(function(item, index){
                let i = keys.indexOf(item.parent_id);
                if(i > -1){
                    keys.splice(i,1)
                }
            })
            that.$refs.tree.setCheckedKeys(keys);
            that.handleCheck()
        },
        // 清空
        clearChecked() {
            let that = this
            that.$refs.tree.setCheckedKeys([]);
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
