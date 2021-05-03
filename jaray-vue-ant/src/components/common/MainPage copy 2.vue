<template>
    <a-layout id="components-layout-demo-custom-trigger">
        <!-- collapsible  -->
        <a-layout-sider :style="{ overflow: 'auto', height: '100vh', left: 0 }" 
            v-model="collapsed">
            <left-nav @add_tab="addTab"></left-nav>
        </a-layout-sider>
        <a-layout>
            <a-layout-header :style="{ background: '#fff', padding: 0 }">
                <div class="logo" />
                <a-icon
                    class="trigger"
                    :type="collapsed ? 'menu-unfold' : 'menu-fold'"
                    @click="() => (collapsed = !collapsed)"
                />
                <head-nav @add_tab="addTab"></head-nav>
            </a-layout-header>
            <a-layout-content :style="{ margin: '12px 8px 0', overflow: 'initial' }">
                <div :style="{ padding: '12px', background: '#fff' }">
                    <i-tabs ref="iTabs"></i-tabs>
                </div>
            </a-layout-content>
        </a-layout>
    </a-layout>

    <!-- <a-layout id="components-layout-demo-responsive" style="min-height: 100vh">
        <a-layout-sider 
            collapsible 
            v-model="collapsed">
            <div class="logo" />
            <left-nav @add_tab="addTab"></left-nav>
        </a-layout-sider>
        <a-layout>
            <a-layout-header :style="{ background: '#fff', padding: 0 }">
                <head-nav @add_tab="addTab"></head-nav>
            </a-layout-header>
            <a-layout-content :style="{ margin: '12px 8px 0', overflow: 'initial' }">
                <div :style="{ padding: '12px', background: '#fff' }">
                    <i-tabs ref="iTabs"></i-tabs>
                </div>
            </a-layout-content>
            <a-layout-footer :style="{ textAlign: 'center', position: 'fixed', bottom: '0px', width: '100%' }">
                Ant Design ©2018 Created by Ant UED
            </a-layout-footer>
        </a-layout>
    </a-layout> -->
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
#components-layout-demo-responsive .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}

/deep/ .ant-layout-footer {
    padding: 14px 25px;
    color: rgba(0, 0, 0, 0.65);
    font-size: 14px;
    background: #f0f2f5;
}


#components-layout-demo-custom-trigger .trigger {
  float: left;
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
</style>