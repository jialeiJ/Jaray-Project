import Vue from 'vue'
import store from '@/store'

let permissions = []

function getAllPermission(leftMenus){
    for(let i=0;i<leftMenus.length;i++){
        if(leftMenus[i].type == 1 && leftMenus[i].perms && leftMenus[i].perms.length){
            for(let j=0;j<leftMenus[i].perms.length;j++){
                permissions.push(leftMenus[i].perms[j].perm)
            } 
        }else if(leftMenus[i].children && leftMenus[i].children.length){
            getAllPermission(leftMenus[i].children)
        }
    }
    return permissions
}

/**
 * 判断用户是否拥有操作权限
 * 根据传入的权限标识，查看是否存在用户权限标识集合
 * @param perm
 */
function hasPermission(perm){
    let hasPermissionFlag = false
    let leftMenus = store.getters.leftMenus
    let permissions = getAllPermission(leftMenus)
    for(let i=0;i<permissions.length;i++){
        if(permissions[i] === perm){
            hasPermissionFlag = true
            break
        }
    }
    return hasPermissionFlag;
}

export default {
    // Vue.js的插件应当有一个公开方法 install.这个方法的第一个参数是Vue构造器，第二个参数是一个可选的选项对象
    install: function(Vue){
        Vue.prototype.hasPermission = (perm) => hasPermission(perm)
    }
}