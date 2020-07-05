<template>
    <el-menu @select="addTab" 
        unique-opened 
        :default-active="$route.path" 
        class="el-menu-vertical-demo" 
        @open="handleOpen" 
        @close="handleClose" 
        :collapse="collapsed">
        <left-nav id="leftNav" :leftMenus="leftMenus" @addTab="addTab"></left-nav>
    </el-menu>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
import API from '../../api/api_system'
import LeftNav from '@/components/nav/LeftNav'

export default {
    name: 'MenuTree',
    components: { LeftNav },
    data () {
        return {
            leftMenus: [],
            url: ''
        }
    },
    methods: {
        findLeftNav: function(){
            let that = this;
            // 定义请求参数
            let accessUser = JSON.parse(sessionStorage.getItem('access-user'))

            let params = {user_id: accessUser.user_id}
            // 调用接口
            API.findLeftNav(params).then(function (result) {
                if (result.code === 200) {
                    that.leftMenus =result.map.leftMenu
                } else {
                    that.loading = false;
                    that.$message.error(result.msg);// elementUI消息提示
                }
            })
        },
        handleOpen: function(key, keyPath) {
            // console.log(key, keyPath);
        },
        handleClose: function(key, keyPath) {
            // console.log(key, keyPath);
        },
        addTab: function(key, keyPath){
            let that = this
            //通过 emit 触发
            this.$emit('addTab', key)
        },
        ...mapActions( // 语法糖
            ['modifyCollapsed'] // 相当于this.$store.dispatch('modifyCollapsed'),提交这个方法
        ),
    },
    computed: {
        ...mapGetters(['collapsed']) //动态计算属性，相当于this.$store.getters.name
    },
    created: function(){
        let that = this
        that.findLeftNav()
    },
    mounted: function(){
        
    }
}
</script>

<style scoped lang="scss">
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
}
</style>