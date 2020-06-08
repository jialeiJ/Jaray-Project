import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/common/Login'
import iTable from '@/components/common/iTable'
import MainPage from '@/components/common/MainPage'
import complaintList from '@/components/complaintList'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/login',
            name: 'Login',
            title: '登录界面',
            component: Login
        },
        {
            path: '/main',
            name: 'MainPage',
            title: '主界面',
            component: MainPage
        },
        {
            path: '/complaint',
            name: 'complaintList',
            title: '投诉界面',
            component: complaintList
        }
    ]
})
