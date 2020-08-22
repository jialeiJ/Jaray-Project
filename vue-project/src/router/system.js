export default [
        {
            path: '/',
            name: 'login',
            title: '登录',
            component: () => import('@components/common/Login')
        },
        {
            path: '/introduction',
            name: 'introduction',
            title: '介绍',
            component: () => import('@components/common/Introduction')
        },
        {
            path: '/main',
            name: 'mainPage',
            title: '主页',
            component: () => import('@components/common/MainPage')
        },
        {
            path: '/sys/user',
            name: 'sysUser',
            title: '用户',
            component: () => import('@views/system/user/sysUser')
        },
        {
            path: '/sys/dept',
            name: 'sysDept',
            title: '机构',
            component: () => import('@views/system/dept/sysDept')
        },
        {
            path: '/sys/role',
            name: 'sysRole',
            title: '角色',
            component: () => import('@views/system/role/sysRole')
        },
        {
            path: '/sys/menu',
            name: 'sysMenu',
            title: '菜单',
            component: () => import('@views/system/menu/sysMenu')
        },
        {
            path: '/sys/dict',
            name: 'sysDict',
            title: '字典',
            component: () => import('@views/system/dict/sysDict')
        },
        {
            path: '/sys/log',
            name: 'sysLog',
            title: '日志',
            component: () => import('@views/system/log/sysLog')
        },
    ]
