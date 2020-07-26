<template>
<div>
    <el-menu
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
        <el-menu-item index="title">
            <a style="text-decoration:none;color:white;font-size:20px;">
                后台管理系统
            </a>
        </el-menu-item>
        <el-menu-item index="collapsed">
            <i style="display : block;" :class="[collapsed?'iconfont icon-collapse-white go':'iconfont icon-collapse-white to']"></i>
        </el-menu-item>
        <el-submenu index="1">
            <template slot="title">我的工作台</template>
            <el-menu-item index="1-1">选项1</el-menu-item>
            <el-menu-item index="1-2">选项2</el-menu-item>
            <el-menu-item index="1-3">选项3</el-menu-item>
            <el-submenu index="1-4">
                <template slot="title">选项4</template>
                <el-menu-item index="1-4-1">选项1</el-menu-item>
                <el-menu-item index="1-4-2">选项2</el-menu-item>
                <el-menu-item index="1-4-3">选项3</el-menu-item>
            </el-submenu>
        </el-submenu>
        <el-menu-item index="2">消息中心</el-menu-item>
        <el-menu-item index="3"><a href="#" target="_blank">订单管理</a></el-menu-item>

        <el-submenu index="user" style="float: right;">
            <template slot="title">用户：{{userInfo.name}}</template>
            <el-menu-item index="user-1">选项1</el-menu-item>
            <el-menu-item index="user-2" @click="editDialogFormVisible = true">修改密码</el-menu-item>
            <el-menu-item index="user-3" @click="signOut">退出系统</el-menu-item>
        </el-submenu>
    </el-menu>
    <div id="edit">
        <el-dialog title="修改密码" :visible.sync="editDialogFormVisible">
            <el-form :model="editForm" :rules="rules" ref="editForm">
                <el-form-item label="原始密码" prop="srcPassword" :label-width="formLabelWidth">
                    <el-input type="password" v-model="editForm.srcPassword" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password" :label-width="formLabelWidth">
                    <el-input type="password" v-model="editForm.password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="password2" :label-width="formLabelWidth">
                    <el-input type="password" v-model="editForm.password2" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="editPassword('editForm')">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</div>
</template>

<script>
import API from '../../api/api_system'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'HeadNav',
    data () {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.editForm.password !== '') {
                    this.$refs.editForm.validateField('password');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.editForm.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            formLabelWidth: '120px',
            editDialogFormVisible: false,
            userInfo: {
                name: ''
            },
            editForm: {
                id: '',
                srcPassword: '',
                password: '',
                password2: ''
            },
            rules: {
                password: [
                    { validator: validatePass, trigger: 'blur' }
                ],
                password2: [
                    { validator: validatePass2, trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        toMain: function(){
            this.$router.push({name: 'MainPage'})
        },
        handleSelect: function(key, keyPath) {
            // 跳转默认页
            if(key === "title"){
                this.toMain()
            }
            if(key === "collapsed"){
                this.changeCollapse()
            }
            console.log(key, keyPath);
        },
        //折叠导航栏
        changeCollapse: function() {
            this.collapsed = !this.collapsed
        },
        setUserInfo: function() {
            let that = this
            let accessUser = sessionStorage.getItem('access-user')
            that.userInfo = JSON.parse(accessUser)
            that.editForm.id = that.userInfo.user_id
        },
        editPassword: function(formName){
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    return false;
                    let that = this
                    let accessUser = sessionStorage.getItem('access-user')
                    // 定义请求参数
                    let params = that.editForm;
                    // 调用接口
                    API.updatePassword(params).then(function (result) {
                        if (result.code === 200) {
                            that.$message({
                                message: '恭喜你，密码修改成功,请重新登陆',
                                type: 'success'
                            });
                            that.editDialogFormVisible = false
                            sessionStorage.clear()
                            that.$router.push({path: "/"});
                        } else {
                            that.$message({
                                message: '抱歉，密码修改失败',
                                type: 'error'
                            });
                        }
                    });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        signOut: function() {
            let that = this
            sessionStorage.clear()
            that.$router.push({path: "/"});
        },
        ...mapActions( // 语法糖
            ['modifyCollapsed'] // 相当于this.$store.dispatch('modifyCollapsed') 提交这个方法
        ),
    },
    mounted: function() {
        this.setUserInfo();
    },
    computed: {
        ...mapGetters(['collapsed']),// 动态计算属性，相当于this.$store.getters.collapsed
        collapsed: {
            get(){
                return this.$store.state.collapsed
            },
            set(val){
                this.$store.state.collapsed = val
            }
        }
    }
}
</script>

<style scoped lang="scss">
/* 去除el-menu-item下划线样式 */
.el-menu-demo .el-menu-item {
    border-bottom-color:black;
    //border-bottom-color:#ffffff!important;
    font-size: 16px;
}

.el-menu-demo .el-menu-item:nth-child(1) {
    border: 0px;
    text-align: center;
    width: 200px;
}
.el-menu-demo .el-menu-item:nth-child(2) {
    border: 0px;
}

.to {
    transition: all 0.5s;
    // 解决内联元素使用transform的rotate属性不起作用
    display: inline-block;
}

.go {
    transform: rotate(-90deg)!important;
    -ms-transform: rotate(-90deg) !important; /* IE 9 */
    -moz-transform: rotate(-90deg) !important; /* Firefox */
    -webkit-transform: rotate(-90deg) !important; /* Safari 和 Chrome */
    -o-transform: rotate(-90deg) !important; /* Opera */
    transition: all 0.5s;
    // 解决内联元素使用transform的rotate属性不起作用
    display: inline-block;
}
</style>
