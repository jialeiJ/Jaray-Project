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
            url: '',
            //leftMenus: [],
            tileLeftNavData: []
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
                } else if(result.code !== 403){
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

            let params = {
                path: key
            }
            if(key.indexOf('http') == 0 || key.indexOf('https') == 0){
                let obj = that.tileLeftNavData.filter(function(item){
                    return item.url == key;
                })
                params.title = obj[0].label
                params.path = obj[0].url
            }

            //通过 emit 触发
            this.$emit('addTab', params)
        },
        // 平铺数据
        tileLeftNav(leftNavData){
            let that = this
            leftNavData.forEach(function(item, index){
                that.tileLeftNavData.push(item)
                if(item.children.length){
                    that.tileLeftNav(item.children)
                }
            })
        },
        ...mapActions( // 语法糖
            ['modifyCollapsed'] // 相当于this.$store.dispatch('modifyCollapsed'),提交这个方法
        ),
        ...mapActions( // 语法糖
            ['modifyLeftMenus'] // 相当于this.$store.dispatch('modifyLeftMenus'),提交这个方法
        ),
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
        },
        ...mapGetters(['leftMenus']),// 动态计算属性，相当于this.$store.getters.collapsed
        leftMenus: {
            get(){
                return this.$store.state.leftMenus
            },
            set(val){
                this.$store.state.leftMenus = val
            }
        },
    },
    created: function(){
        let that = this
        that.findLeftNav()
    },
    mounted: function(){
        
    },
    watch: {
        // 使用监听
        leftMenus: {
            handler(newVal, oldVal){
                if(newVal){
                    this.tileLeftNav(newVal)
                }
            },
            immediate: true, // 立即执行
            deep: true //深度监听
        }
    }
}
</script>

<style scoped lang="scss">
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
}
</style>