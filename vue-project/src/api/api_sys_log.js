import * as API from '.'

export default {
    //查询日志信息列表
    findSysLogList: params => {
        return API.GET('/sys/log/find/all', params);
    },
    //删除日志信息
    deleteSysLog: params => {
        return API.POST('/sys/log/delete', params);
    },
    //添加日志信息
    addSysLog: params => {
        return API.POST('/sys/log/add', params);
    },
    //更新日志信息
    updateSysLog: params => {
        return API.POST('/sys/log/update', params);
    },
    //查询日志信息
    viewSysLog: params => {
        return API.POST('/sys/log/find', params);
    },
}