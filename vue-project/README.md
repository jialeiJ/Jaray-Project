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
    4、npm install element-ui –g   （全局安装elementui）
        在package.json可以看到elementui已经安装好了
        在main.js中引入elementui  （main.js是入口文件，所以在这里引入就行，页面就不用引入了）
        import ElementUI from 'element-ui'
        import 'element-ui/lib/theme-chalk/index.css'

        Vue.use(ElementUI)
    5、npm install axios -g                 （前后端交互，要全局安装axios）
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
        npm install node-sass --sass_binary_site=https://npm.taobao.org/mirrors/node-sass/
        npm install sass-loader --g
        npm install sass-loader@7.3.1 --g -dev
        npm install node-sass --g
        
    

二、监听某个div宽度的变化
    1、安装 element-resize-detector 
        npm install element-resize-detector -g

三、解决菜单折叠文字不隐藏问题
    1、安装vue-fragment
        npm install vue-fragment --g
    2、使用
        <fragment></fragment>标签包裹即可


四、格式化时间
    1、安装moment
        npm install moment --g
    2、配置
        第一种

            // 引入JavaScript 日期处理类库（格式化）
            import moment from "moment";
            // moment.locale('zh-cn') // 汉化
            
            /**
            * 全局挂载（适用于script标签中的js代码格式化时间）
            * 使用方式：this.$moment(时间).format('时间格式')
            */
            Vue.prototype.$moment = moment;
        第二种

            /**
            * 注册为全局过滤器（适用于template标签中的html代码 => 插值表达式和v-bind属性绑定）
            * 使用方式：<span>{{ 时间 | formatDate('时间格式') }}</span>
            * @parms { String } formatStr  时间格式："Y-M-D h:m:s"
            * @parms { any } data 时间：可以是时间戳，也可以是其他形式的时间，比如2019/8/14
            * 时间戳要求是毫秒值，如果是秒值，需要在过滤前 * 1000变为毫秒值
            *  <span> {{ 1111111111 * 1000 | formatDate('Y-M-D h:m:s') }} </span> 
            * 'hh:mm:ss'是十二小时制时间，'HH:mm:ss'是二十四小时制时间
            */
            Vue.filter('formatDate', function (date: any, formatStr: string) {
                return moment(date).format(formatStr)
            })

五、浏览器控制台警告
    1、安装default-passive-events
        npm install default-passive-events --g
