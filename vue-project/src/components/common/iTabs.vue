<template>
    <div>
        <el-tabs 
            v-model="tabsValue" 
            type="border-card" 
            closable 
            @tab-remove="removeTab"
            style="height: 100%;">
            <el-tab-pane v-for="(item, index) in tabs" 
                :key="item.name" 
                :label="item.title" 
                :name="item.name">
                <span slot="label"><i class="el-icon-date"></i> {{item.title}}</span>
                <tab-component :index="index" :name="index" style="padding-top: 0px;"></tab-component>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import Vue from 'vue'

export default {
    name: 'iTabs',
    data () {
        return {
            tabsValue: '',
            tabs: [],
            index:0,
            tabComponent:{}
        }
    },
    methods: {
        addOneTab(menu) {
            var exist = false;
            for (var i = 0; i < this.tabs.length; i++) {
                if (menu.name === this.tabs[i].name) {
                    exist = true;
                    break
                }
            }
            if (exist === true) {
                this.tabsValue = menu.name;
                return
            }
            this.tabs.push({
                title: menu.name,
                name: menu.name,
                content: menu.component
            });
            var _this = this;
            this.tabComponent = Vue.component('tab-component', {
                render: function (h) {
                    var comp = _this.tabs[this.index].content;
                    return h(comp)
                },
                props: {
                    index: {
                        type: Number,
                        required: true
                    }
                }
            });
            this.tabsValue = menu.name
        },
        addTab(params) {
            let tempRoutes = this.$router.options.routes;
            for (let i = 0; i < tempRoutes.length; i++) {
                let temp = tempRoutes[i];
                if(temp.path == params){
                    let menu = {};
                    menu.name = temp.title;
                    menu.component = temp.component;
                    this.addOneTab(menu);
                }
            }
        },
        removeTab(targetName) {
            let tabs = this.tabs;
            let activeName = this.tabsValue;
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
            this.tabsValue = activeName;
            this.tabs = tabs.filter(tab => tab.name !== targetName);
            }
        },
    created: function(){
        let tempRoutes = this.$router.options.routes;
        for (let i = 0; i < tempRoutes.length; i++) {
            let temp = tempRoutes[i];
            if(temp.name == 'introduction'){
                let menu = {};
                menu.name = temp.title;
                menu.component = temp.component;
                this.addOneTab(menu);
            }
        }
    },
    mounted: function(){
        // let tempRoutes = this.$router.options.routes;
        // for (let i = 0; i < tempRoutes.length; i++) {
        //     let temp = tempRoutes[i];
        //     if(temp.name == 'introduction'){
        //         let menu = {};
        //         menu.name = temp.title;
        //         menu.component = temp.component;
        //         this.addOneTab(menu);
        //     }
        // }
    }
}
</script>

<style>
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
}
</style>