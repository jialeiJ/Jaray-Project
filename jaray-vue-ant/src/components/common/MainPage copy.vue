<template>
    <a-layout id="components-layout-demo-top-side-2">
        <a-layout-header class="header">
            <div class="logo" />
            <!-- <a-icon
                class="trigger"
                :type="collapsed ? 'menu-unfold' : 'menu-fold'"
                @click="() => (collapsed = !collapsed)"/> -->
            <head-nav @add_tab="addTab"></head-nav>
        </a-layout-header>
        <a-layout>
            <a-layout-sider v-model="collapsed" collapsible style="background: #fff">
                <left-nav @add_tab="addTab"></left-nav>
            </a-layout-sider>
            <a-layout>
                <a-layout-content 
                    :style="{ margin: '12px 8px', padding: '12px', background: '#fff', minHeight: '480px' }">
                    <i-tabs ref="iTabs"></i-tabs>
                </a-layout-content>
                <a-layout-footer style="textAlign: center; position: fixed; bottom: 0px; width: 100%;">
                    Ant Design ©2018 Created by Ant UED
                </a-layout-footer>
            </a-layout>
        </a-layout>
    </a-layout>
</template>

<script>
import API from '../../api/api_system'
import LeftNav from '@/components/nav/LeftNav'
import HeadNav from '@/components/nav/HeadNav'
import iTabs from '@/components/common/iTabs'

export default {
    name: 'MainPage',
    components: { LeftNav,HeadNav,iTabs },
    data () {
        return {
            collapsed: false,
            // 左边菜单默认宽度
            //asideWidth: 'c200'
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

<style lang="scss" scoped>
#components-layout-demo-top-side-2 .logo {
  width: 120px;
  height: 31px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px 28px 16px 0;
  float: left;
}

/deep/ .ant-layout-footer {
    padding: 14px 25px;
    color: rgba(0, 0, 0, 0.65);
    font-size: 14px;
    background: #f0f2f5;
}
</style>