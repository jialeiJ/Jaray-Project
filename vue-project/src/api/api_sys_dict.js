import * as API from '.'

export default {
    //查询字典信息列表
    findSysDictList: params => {
        return API.GET('/sys/dict/find/all', params);
    },
    //删除字典信息
    deleteSysDict: params => {
        return API.POST('/sys/dict/delete', params);
    },
    //添加字典信息
    addSysDict: params => {
        return API.POST('/sys/dict/add', params);
    },
    //更新字典信息
    updateSysDict: params => {
        return API.POST('/sys/dict/update', params);
    },
    //查询字典信息
    viewSysDict: params => {
        return API.POST('/sys/dict/find', params);
    },
    //查询字典信息
    findSysDictByDesc: params => {
        return API.POST('/sys/dict/find/desc', params);
    },
}