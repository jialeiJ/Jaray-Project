import Vue from 'vue'
import Router from 'vue-router'
import Login from '@components/common/Login'
import Introduction from '@components/common/Introduction'
import MainPage from '@components/common/MainPage'
import Complaint from '@views/complaint/complaint'
import SysUser from '@views/system/user/sysUser'
import SysMenu from '@views/system/menu/sysMenu'
import SysRole from '@views/system/role/sysRole'
import SysDept from '@views/system/dept/sysDept'
import SysDict from '@views/system/dict/sysDict'
import SysLog from '@views/system/log/sysLog'
import system from './system'

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        ...system,
        {
            path: '/complaint',
            name: 'complaint',
            title: '投诉',
            component: Complaint
        },
    ]
})
