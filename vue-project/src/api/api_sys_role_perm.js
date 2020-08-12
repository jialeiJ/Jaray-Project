import * as API from '.'

export default {
    //查询角色权限列表
    findSysRolePermList: params => {
        return API.POST('/sys/role/perm/find/all', params);
    },
    //删除角色权限信息
    deleteSysRolePerm: params => {
        return API.POST('/sys/role/perm/delete', params);
    },
    //添加角色权限信息
    addSysRolePerm: params => {
        return API.POST('/sys/role/perm/add', params);
    },
    //更新角色权限信息
    updateSysRolePerm: params => {
        return API.POST('/sys/role/perm/update', params);
    },
    //查询角色权限信息
    viewSysRolePermByRid: params => {
        return API.POST('/sys/role/perm/findByRid', params);
    },
}