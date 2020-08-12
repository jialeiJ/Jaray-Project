import * as API from '.'

export default {
    //查询角色信息列表
    findSysDeptList: params => {
        return API.POST('/sys/dept/find/all', params);
    },
    //删除角色信息
    deleteSysDept: params => {
        return API.POST('/sys/dept/delete', params);
    },
    //添加/更新角色信息
    addSysDept: params => {
        return API.POST('/sys/dept/add', params);
    },
    //添加/更新角色信息
    updateSysDept: params => {
        return API.POST('/sys/dept/update', params);
    },
    //查询角色信息
    viewSysDept: params => {
        return API.POST('/sys/dept/find', params);
    },
}