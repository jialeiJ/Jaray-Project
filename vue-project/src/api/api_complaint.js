import * as API from './'

export default {
    //查询投诉信息列表
    findComplaintList: params => {
        return API.GET('/complaint/findComplaintList', params);
    },
    //删除投诉信息列表
    delComplaint: params => {
        return API.POST('/complaint/delComplaint', params);
    },
    //添加投诉信息列表
    addOrUpdateComplaint: params => {
        return API.POST('/complaint/addOrUpdateComplaint', params);
    },
    //添加投诉信息列表
    viewComplaint: params => {
        return API.POST('/complaint/findComplaint', params);
    },
}