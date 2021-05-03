import Vue from 'vue'
import Router from 'vue-router'
import system from './system'
import onlineEducation from './onlineEducation'

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        ...system,
        ...onlineEducation,
        {
            path: '/complaint',
            name: 'complaint',
            title: '投诉',
            component: () => import('@views/complaint/complaint')
        },{
            path: '/quartz',
            name: 'quartz',
            title: '定时任务',
            component: () => import('@views/quartz/quartz')
        },
    ]
})
