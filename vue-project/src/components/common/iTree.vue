<template>
    <el-tree
        :data="treeData"
        show-checkbox
        default-expand-all
        node-key="id"
        ref="tree"
        highlight-current
        :props="defaultProps">
    </el-tree>
</template>

<script>

export default {
    name: 'iTree',
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
            treeData: [{
                id: 1,
                label: '一级 1',
                children: [{
                    id: 4,
                    label: '二级 1-1',
                    children: [{
                        id: 9,
                        label: '三级 1-1-1'
                    }, {
                        id: 10,
                        label: '三级 1-1-2'
                    }]
                }]
            }, {
                id: 2,
                label: '一级 2',
                children: [{
                    id: 5,
                    label: '二级 2-1'
                }, {
                    id: 6,
                    label: '二级 2-2'
                }]
            }, {
                id: 3,
                label: '一级 3',
                children: [{
                    id: 7,
                    label: '二级 3-1'
                }, {
                    id: 8,
                    label: '二级 3-2'
                }]
            }],
            defaultProps: {
                children: 'children',
                label: 'label'
            }
        }
    },
    methods: {
        getCheckedNodes() {
            console.log(this.$refs.tree.getCheckedNodes());
        },
        getCheckedKeys() {
            console.log(this.$refs.tree.getCheckedKeys());
        },
        setCheckedNodes() {
            this.$refs.tree.setCheckedNodes([{
            id: 5,
            label: '二级 2-1'
            }, {
            id: 9,
            label: '三级 1-1-1'
            }]);
        },
        setCheckedKeys() {
            this.$refs.tree.setCheckedKeys([3]);
        },
        resetChecked() {
            this.$refs.tree.setCheckedKeys([]);
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
