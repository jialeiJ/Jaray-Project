<template>
    <el-menu
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
        <el-menu-item index="title">
            <a style="text-decoration:none;color:white;font-size:20px;">
                后台管理系统
            </a>
        </el-menu-item>
        <el-menu-item index="collapsed">
            <i style="display : block;" :class="[collapsed?'iconfont icon-collapse-white go':'iconfont icon-collapse-white to']"></i>
        </el-menu-item>
        <el-submenu index="1">
            <template slot="title">我的工作台</template>
            <el-menu-item index="1-1">选项1</el-menu-item>
            <el-menu-item index="1-2">选项2</el-menu-item>
            <el-menu-item index="1-3">选项3</el-menu-item>
            <el-submenu index="1-4">
                <template slot="title">选项4</template>
                <el-menu-item index="1-4-1">选项1</el-menu-item>
                <el-menu-item index="1-4-2">选项2</el-menu-item>
                <el-menu-item index="1-4-3">选项3</el-menu-item>
            </el-submenu>
        </el-submenu>
        <el-menu-item index="2">消息中心</el-menu-item>
        <el-menu-item index="3"><a href="#" target="_blank">订单管理</a></el-menu-item>
    </el-menu>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'HeadNav',
    data () {
        return {
      
        }
    },
    methods: {
        ...mapActions( // 语法糖
            ['modifyCollapsed'] // 相当于this.$store.dispatch('modifyCollapsed') 提交这个方法
        ),
        toMain: function(){
            this.$router.push({name: 'MainPage'})
        },
        handleSelect: function(key, keyPath) {
            // 跳转默认页
            if(key === "title"){
                this.toMain()
            }
            if(key === "collapsed"){
                this.changeCollapse()
            }
            console.log(key, keyPath);
        },
        //折叠导航栏
        changeCollapse: function() {
            this.collapsed = !this.collapsed
        }
    },
    computed: {
        ...mapGetters(['collapsed']),// 动态计算属性，相当于this.$store.getters.collapsed
        collapsed: {
            get(){
                return this.$store.state.collapsed
            },
            set(val){
                this.$store.state.collapsed = val
            }
        }
    }
}
</script>

<style scoped lang="scss">
/* 去除el-menu-item下划线样式 */
.el-menu-demo .el-menu-item {
    border-bottom-color:black;
    //border-bottom-color:#ffffff!important;
    font-size: 16px;
}

.el-menu-demo .el-menu-item:nth-child(1) {
    border: 0px;
    text-align: center;
    width: 200px;
}
.el-menu-demo .el-menu-item:nth-child(2) {
    border: 0px;
}

.to {
    transition: all 0.5s;
    // 解决内联元素使用transform的rotate属性不起作用
    display: inline-block;
}

.go {
    transform: rotate(-90deg)!important;
    -ms-transform: rotate(-90deg) !important; /* IE 9 */
    -moz-transform: rotate(-90deg) !important; /* Firefox */
    -webkit-transform: rotate(-90deg) !important; /* Safari 和 Chrome */
    -o-transform: rotate(-90deg) !important; /* Opera */
    transition: all 0.5s;
    // 解决内联元素使用transform的rotate属性不起作用
    display: inline-block;
}
</style>
