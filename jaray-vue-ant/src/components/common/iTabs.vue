<template>
    <a-row>
        <a-col :span="24">
            <a-tabs 
                default-active-key="introduction"
                :active-key="activeKey" 
                hide-add 
                type="editable-card" 
                @edit="tabEdit"
                @change="tabChange">
                <a-tab-pane :closable="!(index == 0)" v-for="(item, index) in tabs" :key="item.key" :tab="item.title">
                    <!-- 若是Iframe，则不重复渲染，否则渲染 -->
                    <template v-if="item.path.indexOf('http') > -1">
                        <tab-component ref="iframe" :is="item.content" :index="index" :name="index" class="tabComponent"></tab-component>
                    </template>
                    <template v-else>
                        <tab-component :index="index" :name="index" class="tabComponent"></tab-component>
                    </template>
                </a-tab-pane>
            </a-tabs>
        </a-col>
    </a-row>
</template>

<script>
import Vue from 'vue'
import $ from 'jquery'

export default {
    name: 'iTabs',
    data () {
        return {
            activeKey: '',
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
                that.activeKey = menu.name;
                return
            } else {
                that.index = that.tabs.length
            }
            that.tabs.push({
                title: menu.name,
                name: menu.name,
                key: menu.name,
                content: menu.component,
                path: menu.path
            });
            
            that.activeKey = menu.name

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
                setTimeout(function(){
                    // console.log(that.$refs.iframe[0].contentWindow.location)
                    // console.log(that.$refs.iframe[0].contentWindow.location.replace(params.path))
                    that.$refs.iframe[0].contentWindow.location.href = params.path
                }, 10000)
                
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
        tabEdit(targetKey, action) {
            let that = this
            let tabs = that.tabs;
            let activeName = that.activeKey;
            if (activeName === targetKey) {
                tabs.forEach((tab, index) => {
                    if (tab.name === targetKey) {
                        let nextTab = tabs[index + 1] || tabs[index - 1];
                        if (nextTab) {
                            activeName = nextTab.name;
                        }
                    }
                });
            }
            that.index--
            that.activeKey = activeName;
            that.tabs = tabs.filter(tab => tab.name !== targetKey);
        },
        tabChange(activeKey) {
            let that = this
            that.activeKey = activeKey
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
                menu.closable = false;
                this.addOneTab(menu);
            }
        }
    },
    mounted: function(){
        
    },
    watch: {
        'activeKey': function(tabName) {
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
.ant-header, .ant-footer {
    padding:  0px;
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
}

.ant-aside {
    height:90vh;
    background-color: #FFFFFF;
    color: #333;
    line-height: 200px;
}
  
.ant-main {
    padding: 6px 6px;
    background-color: #FFFFFF;
    color: #333;
    /* padding: 0 0px; */
}
  
body > .ant-container {
    margin-bottom: 40px;
}

.ant-container {
    width: 100%;
    height: 100%;
}

.ant-row {
    display: flex;
    flex-wrap: wrap;
}

.tabComponent {
    padding-top: 0px;
    width: 100%;
}
</style>