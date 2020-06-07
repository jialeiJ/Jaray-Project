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
            let params = {}
            // 调用接口
            API.findLeftNav(params).then(function (result) {
                if (result.code === 200) {
                that.leftMenus =result.map.menuEntities
                } else {
                that.loading = false;
                that.$message.error(result.msg);// elementUI消息提示
                }
            })
        },
        handleOpen: function(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose: function(key, keyPath) {
            console.log(key, keyPath);
        },
        addTab: function(key, keyPath){
            let that = this
            that.findLeftMenu(that.leftMenus, keyPath)
            //通过 emit 触发
            this.$emit('addTab',that.url)
        },
        findLeftMenu: function(leftMenus=[], keyPath=[]){
            let that = this
            for(var i=0;i<keyPath.length;i++){
                let tempLeftMenus = leftMenus.filter(function(data){
                    return data.id==keyPath[i]?data.childMenus:null;
                });
                for(var j=0;j<tempLeftMenus.length;j++){
                    if(tempLeftMenus[j] != null && tempLeftMenus[j].childMenus.length > 0){
                        keyPath.splice(0,1)
                        that.findLeftMenu(tempLeftMenus[j].childMenus, keyPath);
                    }else if(tempLeftMenus[j].childMenus.length == 0){
                        that.url = tempLeftMenus[j].url
                    }
                }
            }
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