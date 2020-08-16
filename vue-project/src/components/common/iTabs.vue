<template>
    <el-row>
        <el-col :span="24">
            <el-tabs 
                v-model="tabsValue" 
                type="border-card" 
                @tab-remove="removeTab"
                style="height: 100%;">
                <el-tab-pane :closable="!(index == 0)" v-for="(item, index) in tabs"
                    :key="item.name" 
                    :label="item.title" 
                    :name="item.name">
                    <span slot="label"><i class="el-icon-date"></i> {{item.title}}</span>
                    <!-- 若是Iframe，则不重复渲染，否则渲染 -->
                    <template v-if="item.path.indexOf('http') > -1">
                        <tab-component :is="item.content" :index="index" :name="index" class="tabComponent"></tab-component>
                    </template>
                    <template v-else>
                        <tab-component :index="index" :name="index" class="tabComponent"></tab-component>
                    </template>
                </el-tab-pane>
            </el-tabs>
        </el-col>
    </el-row>
</template>

<script>
import Vue from 'vue'
import $ from 'jquery'

export default {
    name: 'iTabs',
    data () {
        return {
            tabsValue: '',
            tabs: [],
            index: 0,
            tabComponent: {},
            iframeComponent: {}
        }
    },
    methods: {
        addOneTab(menu) {
            let that = this
            var exist = false;
            for (var i = 0; i < that.tabs.length; i++) {
                if (menu.name === that.tabs[i].name) {
                    exist = true;
                    that.index = i;
                    break
                }
            }
            if (exist === true) {
                that.tabsValue = menu.name;
                return
            } else {
                that.index = that.tabs.length
            }
            that.tabs.push({
                title: menu.name,
                name: menu.name,
                content: menu.component,
                path: menu.path
            });
            that.tabsValue = menu.name

            that.render()
        },
        addTab(params) {
            let that = this
            let tempRoutes = that.$router.options.routes;
            for (let i = 0; i < tempRoutes.length; i++) {
                let temp = tempRoutes[i];
                if(temp.path == params.path){
                    let menu = {};
                    menu.name = temp.title;
                    menu.component = temp.component;
                    menu.path = params.path;
                    that.addOneTab(menu);
                }
            }

            if(params.path && (params.path.indexOf('http') == 0 || params.path.indexOf('https') == 0)){
                let uid = that.$moment(new Date()).format('YYYYMMDDHHmmss')
                that.iframeComponent = Vue.component('iframe-component', {
                    template: '<iframe id="'+uid+'" name="'+uid+'" src="'+params.path+'" scrolling="yes" width="100%" height="100%" frameborder="0" style="height: calc(95vh - 100px);overflow: auto;"></iframe>',
                });
                let menu = {};
                menu.name = params.title;
                menu.component = that.iframeComponent.options;
                menu.path = params.path;
                that.index++
                that.addOneTab(menu);
            }
        },
        removeTab(targetName) {
            let that = this
            let tabs = that.tabs;
            let activeName = that.tabsValue;
            if (activeName === targetName) {
                tabs.forEach((tab, index) => {
                    if (tab.name === targetName) {
                        let nextTab = tabs[index + 1] || tabs[index - 1];
                        if (nextTab) {
                            activeName = nextTab.name;
                        }
                    }
                });
            }
            that.index--
            that.tabsValue = activeName;
            that.tabs = tabs.filter(tab => tab.name !== targetName);
        },
        render: function() {
            let that = this
            that.tabComponent = Vue.component('tab-component', {
                render: function (h) {
                    // 每次只渲染当前tab
                    if(this.index === that.index) {
                        var comp = that.tabs[this.index].content;
                        return h(comp)
                    }
                },
                props: {
                    index: {
                        type: Number,
                        required: true
                    }
                }
            }); 
        }
    },
    created: function(){
        let tempRoutes = this.$router.options.routes;
        for (let i = 0; i < tempRoutes.length; i++) {
            let temp = tempRoutes[i];
            if(temp.path == '/introduction'){
                let menu = {};
                menu.name = temp.title;
                menu.component = temp.component;
                menu.path = temp.path;
                this.addOneTab(menu);
            }
        }
    },
    mounted: function(){
        
    },
    watch: {
        'tabsValue': function(tabName) {
            let that = this
            for (var i = 0; i < that.tabs.length; i++) {
                if (tabName === that.tabs[i].name) {
                    that.index = i;
                    break
                }
            }
        }
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
    height:90vh;
    background-color: #FFFFFF;
    color: #333;
    line-height: 200px;
}
  
.el-main {
    padding: 6px 6px;
    background-color: #FFFFFF;
    color: #333;
    /* padding: 0 0px; */
}
  
body > .el-container {
    margin-bottom: 40px;
}

.el-container {
    width: 100%;
    height: 100%;
}

.el-row {
    display: flex;
    flex-wrap: wrap;
}

.tabComponent {
    padding-top: 0px;
    width: 100%;
}
</style>