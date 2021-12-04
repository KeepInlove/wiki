import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import axios from 'axios';
//引入所有图标
import * as Icons from '@ant-design/icons-vue';
import {Tool} from "@/util/tool";
axios.defaults.baseURL=process.env.VUE_APP_SERVER;
/**
 * axios拦截器
 */
axios.interceptors.request.use(function (config) {
    console.log('请求参数:',config);
    const token=store.state.user.token;
    if (Tool.isNotEmpty(token)){
        config.headers.token=token;
        console.log("请求headers增加token:",token);
    }
    return config;
},error => {
    return Promise.reject(error)
});
axios.interceptors.response.use(function (res) {
    console.log('响应结果:',res);
    return res;
},error => {
    console.log('返回错误:',error);
    return Promise.reject(error)
    });
const app = createApp(App);
app.use(store).use(router).use(Antd).mount('#app');

//全局使用图标
const icons: any=Icons;
for (const i in icons){
    app.component(i,icons[i])
}
console.log('启动环境:',process.env.NODE_ENV);
console.log('服务端ip:',process.env.VUE_APP_SERVER);
