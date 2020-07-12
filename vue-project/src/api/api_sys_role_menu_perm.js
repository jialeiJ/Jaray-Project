import * as API from '.'

export default {
    //查询角色菜单列表
    findSysRoleMenuPermList: params => {
        return API.GET('/sys/role/menu/perm/find/all', params);
    },
    //删除角色菜单信息
    deleteSysRoleMenuPerm: params => {
        return API.POST('/sys/role/menu/perm/delete', params);
    },
    //添加角色菜单信息
    addSysRoleMenuPerm: params => {
        return API.POST('/sys/role/menu/perm/add', params);
    },
    //更新角色菜单信息
    updateSysRoleMenuPerm: params => {
        return API.POST('/sys/role/menu/perm/update', params);
    },
    //查询角色菜单信息
    viewSysRoleMenuPermByRid: params => {
        return API.POST('/sys/role/menu/perm/findByRid', params);
    },
}