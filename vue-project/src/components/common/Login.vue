<template>
    <div>
        <el-form ref="loginForm" :model="loginForm" :rules="rules" label-width="80px" class="login-box">
            <h2 class="login-title">欢迎登录</h2>
            <el-form-item label="账号" prop="username">
                <el-input type="text" placeholder="请输入账号" v-model="loginForm.username"/>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input type="password" placeholder="请输入密码" v-model="loginForm.password"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" v-on:click="onSubmit('loginForm')">登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import API from '../../api/api_system'
import MenuTree from '@/components/nav/MenuTree'
import HeadNav from '@/components/nav/HeadNav'
import iTabs from '@/components/common/iTabs'

export default {
    name: 'Login',
    components: { MenuTree,HeadNav,iTabs },
    data () {
        return {
            loginForm: {
                username: '',
                password: ''
            },

            // 表单验证，需要在 el-form-item 元素中增加 prop 属性
            rules: {
                username: [
                    {required: true, message: '账号不可为空', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '密码不可为空', trigger: 'blur'}
                ]
            }
        }
    },
    methods: {
        onSubmit(formName) {
            let that = this
            // 为表单绑定验证功能
            that.$refs[formName].validate((valid) => {
                if (valid) {
                    // 使用 vue-router 路由到指定页面，该方式称之为编程式导航
                    that.$router.push({path: "main"});
                } else {
                    return false;
                }
            });
        }
    },
  created: function(){
    
  },
  mounted: function(){
    
  }
}
</script>

<style scoped>
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
    text-align: center;
    margin: 0 auto 40px auto;
    color: #303133;
}
</style>