import * as API from '.'

export default {
    //查询菜单信息列表
    findSysMenuList: params => {
        return API.POST('/sys/menu/find/all', params);
    },
    //删除菜单信息
    deleteSysMenu: params => {
        return API.POST('/sys/menu/delete', params);
    },
    //添加菜单信息
    addSysMenu: params => {
        return API.POST('/sys/menu/add', params);
    },
    //更新菜单信息
    updateSysMenu: params => {
        return API.POST('/sys/menu/update', params);
    },
    //查询菜单信息
    viewSysMenu: params => {
        return API.POST('/sys/menu/find', params);
    },
}