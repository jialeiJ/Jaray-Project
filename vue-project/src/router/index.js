import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/common/Login'
import Introduction from '@/components/common/Introduction'
import MainPage from '@/components/common/MainPage'
import complaintList from '@/components/complaintList'

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
            name: 'complaintList',
            title: '投诉',
            component: complaintList
        }
    ]
})