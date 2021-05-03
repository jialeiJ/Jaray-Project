<template>
    <a-menu
        mode="inline"
        theme="light"
        @select="addTab"
        @click="addTab"
        :style="{ 'overflow-x': 'hidden', 'overflow-y': 'auto', height: '90vh', left: 0 }" 
        :inline-collapsed="collapsed">
        <!-- 菜单遍历的开始 -->
        <template v-for="(item, index) in leftMenus">
            <!-- 如果当前遍历项没有children，视为子菜单项，注意所有的key都是path用于路由跳转，以及当前选中记录 -->
            <a-menu-item v-if="item.type == 1" :key="item.path == ''?item.name+index:item.path">
                <!-- <i v-if="item.icon" :class="item.icon" /> -->
                <a-icon type="setting" />
                <span>{{ item.name }}</span>
            </a-menu-item>
            <!-- 否则视为子菜单，传入菜单信息并且运用下面定义的函数式组件 -->
            <!-- <a-menu-item v-else-if="item.type == 0 && item.children.length > 0">{{item.path}}--{{item.name}}--{{item}}</a-menu-item> -->
            <sub-menu v-else-if="item.type == 0" :key="item.path == ''?item.name+index:item.path" :menu-info="item" />
        </template>
    </a-menu>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
import API from '../../api/api_system'

import { Menu } from 'ant-design-vue';
const SubMenu = {
    template: `
        <a-sub-menu :key="menuInfo.path == ''?menuInfo.name:menuInfo.path" v-bind="$props" v-on="$listeners">
            <span slot="title">
                <a-icon type="setting" /><span>{{ menuInfo.name }}</span>
            </span>
            <template v-for="(item, index) in menuInfo.children">
                <a-menu-item v-if="item.type == 1" :key="item.path == ''?item.name+index:item.path">
                    <i :class="item.icon" />
                    <span>{{ item.name }}</span>
                </a-menu-item>
                <sub-menu v-else-if="item.type == 0" :key="item.path == ''?item.name+index:item.path" :menu-info="item" />
            </template>
        </a-sub-menu>
    `,
    name: 'SubMenu',
    // must add isSubMenu: true
    isSubMenu: true,
    props: {
        ...Menu.SubMenu.props,
        // Cannot overlap with properties within Menu.SubMenu.props
        menuInfo: {
            type: Object,
            default: () => ({}),
        },
    },
};
export default {
    name: 'LeftNav',
    // 注册局部组件
    components: { SubMenu },
    data () {
        return {
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
                }
            })
        },
        handleOpen: function(key, keyPath) {
            // console.log(key, keyPath);
        },
        handleClose: function(key, keyPath) {
            // console.log(key, keyPath);
        },
        addTab: function({ item, key, selectedKeys }){
            let that = this

            let params = {}
            let obj = that.tileLeftNavData.filter(function(item){
                return item.url == key;
            })
            params.title = obj[0].name
            params.path = obj[0].url

            //通过 emit 触发
            this.$emit('add_tab', params)
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
        ...mapGetters(['leftMenus']),// 动态计算属性，相当于this.$store.getters.leftMenus
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
        },
    }
}
</script>

<style lang="scss" scoped>
</style>