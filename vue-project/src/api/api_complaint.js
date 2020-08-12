import * as API from './'

export default {
    //查询投诉信息列表
    findComplaintList: params => {
        return API.POST('/complaint/find/all', params);
    },
    //添加投诉信息
    addComplaint: params => {
        return API.POST('/complaint/add', params);
    },
    //删除投诉信息
    deleteComplaint: params => {
        return API.POST('/complaint/delete', params);
    },
    //更新投诉信息
    updateComplaint: params => {
        return API.POST('/complaint/update', params);
    },
    //查询投诉信息
    viewComplaint: params => {
        return API.POST('/complaint/find', params);
    },
}