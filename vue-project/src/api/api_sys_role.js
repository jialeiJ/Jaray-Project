import * as API from '.'

export default {
    //查询角色信息列表
    findSysRoleList: params => {
        return API.GET('/sys/role/find/all', params);
    },
    //删除角色信息
    deleteSysRole: params => {
        return API.POST('/sys/role/delete', params);
    },
    //添加角色信息
    addSysRole: params => {
        return API.POST('/sys/role/add', params);
    },
    //更新角色信息
    updateSysRole: params => {
        return API.POST('/sys/role/update', params);
    },
    //查询角色信息
    viewSysRole: params => {
        return API.POST('/sys/role/find', params);
    },
}