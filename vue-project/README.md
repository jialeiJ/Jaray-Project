# jaray

> A Vue.js project

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).


# 一、搭建项目
    1、vue init webpack 项目名称
    2、cd 项目目录下
    3、npm run dev   (运行项目)
    4、npm i element-ui –S   （安装elementui）
        在package.json可以看到elementui已经安装好了
        在main.js中引入elementui  （main.js是入口文件，所以在这里引入就行，页面就不用引入了）
        import ElementUI from 'element-ui'
        import 'element-ui/lib/theme-chalk/index.css'

        Vue.use(ElementUI)
    5、npm install axios      （前后端交互，要安装axios）
        在main.js引入axios（安装跟引入  跟elementui一样）
        import axios from 'axios'

        axios.get(url).then(res=>{
            if(res.data.retCode == 200){
	            //成功
            }else{
                //失败
            }
        })

    6、使用lang="scss"
        npm install sass-loader --save
        npm install sass-loader@7.3.1 --save-dev
        npm install node-sass --save
        npm i node-sass --sass_binary_site=https://npm.taobao.org/mirrors/node-sass/
    

二、监听某个div宽度的变化
    1、安装 element-resize-detector 
        npm install element-resize-detector

三、安装vue-fragment解决菜单折叠文字不隐藏问题
