<template>
    <el-container>
        <el-header>
            <head-nav @addTab="addTab"></head-nav>
        </el-header>
        <el-container>
            <el-aside style="width:auto;" :width="asideWidth">
                <menu-tree @addTab="addTab"></menu-tree>
            </el-aside>
            <el-main>
                <i-tabs ref="iTabs"></i-tabs>
                <!-- <router-view /> -->
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
import API from '../../api/api_system'
import MenuTree from '@/components/nav/MenuTree'
import HeadNav from '@/components/nav/HeadNav'
import iTabs from '@/components/common/iTabs'

export default {
    name: 'MainPage',
    components: { MenuTree,HeadNav,iTabs },
    data () {
        return {
            // 左边菜单默认宽度
            asideWidth: 'c200'
        }
    },
    methods: {
        addTab: function(params){
            this.$refs['iTabs'].addTab(params)
        }
    },
    created: function(){
        
    },
    mounted: function(){
        
    }
}
</script>

<style scoped>
.el-header, .el-footer {
    padding:  0px;
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
}

.el-aside {
    width: auto;
    height:90vh;
    background-color: #FFFFFF;
    color: #333;
    line-height: 200px;
}
  
.el-main {
    padding: 6px 6px;
    background-color: #FFFFFF;
    color: #333;
    height: 100%;
    /* padding: 0 0px; */
}
  
body > .el-container {
    margin-bottom: 40px;
}

.el-container {
    width: 100%;
}
</style>