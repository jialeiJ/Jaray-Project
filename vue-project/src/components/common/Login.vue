<template>
    <div class="login">
        <el-form ref="loginForm" :model="loginForm" :rules="rules" label-width="80px" class="login-box">
            <h2 class="login-title">系统登录</h2>
            <el-form-item label="" prop="username">
                <el-input type="text" size="arge" placeholder="账号" v-model="loginForm.username"/>
            </el-form-item>
            <el-form-item label="" prop="password">
                <el-input type="password" size="arge" placeholder="密码" v-model="loginForm.password"/>
            </el-form-item>
            <el-form-item label="" prop="captcha">
                <el-row :gutter="20">
                    <el-col :span="14">
                        <el-input id="captcha" type="text" size="arge" placeholder="验证码,单击图片刷新" v-model="loginForm.captcha"/>
                    </el-col>
                    <el-col :span="10">
                        <el-image id="imagecode" :src="checkCodeSrc" @click="reloadCode()"></el-image>
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" size="arge" @click="resetForm('loginForm')">重置</el-button>
                <el-button type="primary" size="arge" @click="submitForm('loginForm')">登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import Env from '../../api/env'
import API from '../../api/api_system'
import MenuTree from '@/components/nav/MenuTree'
import HeadNav from '@/components/nav/HeadNav'
import iTabs from '@/components/common/iTabs'

export default {
    name: 'Login',
    components: { MenuTree,HeadNav,iTabs },
    data () {
        return {
            checkCodeSrc: Env.baseURL + '/system/captcha.jpg',
            loginForm: {
                username: 'admin',
                password: 'Passw0rd',
                captcha: '12345'
            },
            // 表单验证，需要在 el-form-item 元素中增加 prop 属性
            rules: {
                username: [
                    {required: true, message: '账号不能为空', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '密码不能为空', trigger: 'blur'}
                ],
                captcha: [
                    {required: true, message: '验证码不能为空', trigger: 'blur'}
                ]
            }
        }
    },
    methods: {
        reloadCode: function(){
            let that = this
            var time = new Date().getTime();
            document.getElementById("imagecode").src=that.checkCodeSrc+"?d="+time;
        },
        submitForm: function(formName) {
            let that = this
            // 为表单绑定验证功能
            that.$refs[formName].validate((valid) => {
                if (valid) {
                    // 定义请求参数
                    let params = that.loginForm
                    // 调用接口
                    API.login(params).then(function (result) {
                        if (result.code === 200) {
                            // 使用 vue-router 路由到指定页面，该方式称之为编程式导航
                            sessionStorage.setItem('access-user', JSON.stringify(result.map.sysUserToken)); // 将用户信息存到sessionStorage中
                            sessionStorage.setItem('access-token', result.map.sysUserToken.token); // 将token信息存到sessionStorage中
                            //localStorage.setItem('access-user', JSON.stringify(result.map.sysUserToken)); // 将用户信息存到localStorage中
                            //localStorage.setItem('access-token', result.map.sysUserToken.token); // 将token信息存到localStorage中
                            that.$router.push({path: "main"});
                        }else{
                            that.$message({
                                type: 'error',
                                message: result.msg
                            })
                        }
                    });
                } else {
                    return false;
                }
            });
        },
        resetForm: function(formName){
            this.$refs[formName].resetFields();
        },
        keyupEnter: function() {
            var that = this;
            document.onkeydown = function(e) {
                var key = window.event.keyCode;
                if (key == 13) {
                    that.submitForm('loginForm');
                }
            }
        },
    },
    created: function() {
        this.keyupEnter()
    },
    mounted: function() {
        
    }
}
</script>

<style lang="scss" scoped>
/deep/ .el-form-item__content{
    margin-left:0!important;
} 

.login {
    .el-button--primary {
        width: 48%;
        color: #FFF;
        background-color: #14889A;
        border-color: #14889A;
    }
    .el-button–primary:hover{
        background-color: #14889A;
        border-color: #14889A;
    }
    .el-button–primary:focus{
        background-color: #14889A;
        border-color: #14889A;
    }
}

.login-box {
    border: 1px solid #DCDFE6;
    width: 350px;
    margin: 180px auto;
    padding: 35px 35px 15px 35px;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    box-shadow: 0 0 25px #909399;
}

.login-title {
    font-size: 30px;
    text-align: center;
    margin: 0 auto 40px auto;
    color: #303133;
}
</style>