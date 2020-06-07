import Vue from 'vue'
import Router from 'vue-router'
import iTable from '@/components/common/iTable'
import MainPage from '@/components/common/MainPage'
import complaintList from '@/components/complaintList'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/main',
            name: 'MainPage',
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
