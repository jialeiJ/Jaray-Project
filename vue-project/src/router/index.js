import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/common/Login'
import Introduction from '@/components/common/Introduction'
import MainPage from '@/components/common/MainPage'
import Complaint from '@/components/complaint'
import SysUser from '@/components/sysUser'
import SysMenu from '@/components/sysMenu'
import SysRole from '@/components/sysRole'
import SysDept from '@/components/sysDept'
import SysDict from '@/components/sysDict'
import SysLog from '@/components/sysLog'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'login',
            title: '登录',
            component: Login
        },
        {
            path: '/introduction',
            name: 'introduction',
            title: '介绍',
            component: Introduction
        },
        {
            path: '/main',
            name: 'mainPage',
            title: '主页',
            component: MainPage
        },
        {
            path: '/complaint',
            name: 'complaint',
            title: '投诉',
            component: Complaint
        },
        {
            path: '/sys/user',
            name: 'sysUser',
            title: '用户',
            component: SysUser
        },
        {
            path: '/sys/dept',
            name: 'sysDept',
            title: '机构',
            component: SysDept
        },
        {
            path: '/sys/role',
            name: 'sysRole',
            title: '角色',
            component: SysRole
        },
        {
            path: '/sys/menu',
            name: 'sysMenu',
            title: '菜单',
            component: SysMenu
        },
        {
            path: '/sys/dict',
            name: 'sysDict',
            title: '字典',
            component: SysDict
        },
        {
            path: '/sys/log',
            name: 'sysLog',
            title: '日志',
            component: SysLog
        },
    ]
})
