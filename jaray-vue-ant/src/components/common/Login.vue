<template>
    <div class="login">
        <a-form-model ref="loginForm" :model="loginForm" :rules="rules" laba-width="80px" class="login-box">
            <h2 class="login-title">系统登录</h2>
            <a-form-model-item laba="" prop="username">
                <a-input type="text" size="large" placeholder="账号" v-model="loginForm.username"/>
            </a-form-model-item>
            <a-form-model-item laba="" prop="password">
                <a-input type="password" size="large" placeholder="密码" v-model="loginForm.password"/>
            </a-form-model-item>
            <a-form-model-item laba="" prop="captcha">
                <a-row :gutter="20">
                    <a-col :span="10">
                        <a-input type="text" size="large" placeholder="验证码" v-model="loginForm.captcha"/>
                    </a-col>
                    <a-col :span="14">
                        <img id="imagecode" :src="checkCodeSrc" @click="reloadCode"></img>
                    </a-col>
                </a-row>
            </a-form-model-item>
            <a-form-item>
                <a-row :gutter="16">
                    <a-col class="gutter-row" :span="12">
                        <a-button type="primary" size="large" @click="resetForm('loginForm')">重置</a-button>
                    </a-col>
                    <a-col class="gutter-row" :span="12">
                        <a-button type="primary" size="large" @click="submitForm('loginForm')">登录</a-button>
                    </a-col>
                </a-row>
            </a-form-item>
        </a-form-model>
    </div>
</template>

<script>
import Env from '@api/env'
import API from '@api/api_system'
import HeadNav from '@/components/nav/HeadNav'
import iTabs from '@/components/common/iTabs'

export default {
    name: 'Login',
    components: { HeadNav,iTabs },
    data () {
        return {
            checkCodeSrc: Env.baseURL + '/system/captcha.jpg',
            loginForm: {
                username: 'admin',
                password: 'Passw0rd',
                captcha: '12345'
            },
            // 表单验证，需要在 a-form-item 元素中增加 prop 属性
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
            that.$refs[formName].validate(valid => {
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
/deep/ .ant-form-item__content{
    margin-left:0!important;
} 

.login {
    .ant-btn {
        float: right;
    }

    .ant-btn-primary {
        width: 100%;
        color: #FFF;
        background-color: #14889A;
        border-color: #14889A;
    }
    .ant-btn-primary:hover{
        background-color: #14889A;
        border-color: #14889A;
    }
    .ant-btn-primary:focus{
        background-color: #14889A;
        border-color: #14889A;
    }
}

.login-box {
    border: 1px solid #DCDFE6;
    width: 420px;
    margin: 120px auto;
    padding: 35px 35px 15px 35px;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    box-shadow: 0 0 25px #909399;
}

.login-title {
    font-size: 30px;
    text-align: center;
    margin: 0 auto 28px auto;
    color: #303133;
}
</style>