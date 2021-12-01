<template>
    <a-layout-header class="header" :style="{backgroundColor:'#fff'}">
        <div class="logo">
            <div>
            <img src="https://aliyuncdn.antdv.com/v2/assets/logo.1ef800a8.svg">
            </div>
            <h2>Mr Freshman</h2>
        </div>
        <a-menu
                theme="light"
                mode="horizontal"
                :style="{ lineHeight: '64px' ,float: 'right',fontSize:'16px'}">
            <a-menu-item key="/">
               <router-link to="/">首页</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/user">
                <router-link to="/admin/user">用户管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/ebook">
                <router-link to="/admin/ebook">文档管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/category">
                <router-link to="/admin/category">分类管理</router-link>
            </a-menu-item>
            <a-menu-item key="/about">
                <router-link to="/about">关于我</router-link>
            </a-menu-item>
            <a-menu-item>
                <a href="https://github.com/KeepInlove" target="_blank" >
                    <GithubOutlined style="fontSize:20px"/>
                </a>
            </a-menu-item>

            <a-menu-item>
                <a @click="showLoginModal">
                 <span>登录</span>
                </a>
            </a-menu-item>
        </a-menu>
    </a-layout-header>
    <a-modal
    title="登录"
    v-model:visible="loginModalVisible"
    :confirm-loading="loginModalLoading"
    @ok="login"
    >
        <a-form :model="loginUser" :label-col="{span:6}" :wrapper-col="{ span: 18 }">
            <a-form-item label="用户名">
                <a-input v-model:value="loginUser.loginName"/>
            </a-form-item>
            <a-form-item label="密码">
                <a-input type="password" v-model:value="loginUser.password"/>
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script lang="ts">
    import { defineComponent,ref ,} from 'vue';
    import { GithubOutlined } from '@ant-design/icons-vue';
    import {message} from "ant-design-vue";
    import axios from 'axios'
    declare let hexMd5:any;
    declare let KEY: any;
    export default defineComponent({
        name: 'the-header',
        components: {
            GithubOutlined
        },
        setup(){
            const loginUser=ref({
                loginName:"test",
                password:"test"
            });
            const loginModalVisible=ref(false);
            const loginModalLoading=ref(false);

            const showLoginModal=()=>{
                loginModalVisible.value=true;
            };
            //登录
            const login=()=>{
                console.log('开始登录');
                loginModalLoading.value=true;
                loginUser.value.password=hexMd5(loginUser.value.password + KEY);
                axios.post('/user/login',loginUser.value).then((response)=>{
                    loginModalLoading.value=false;
                   const data=response.data;
                   if (data.success){
                       loginModalVisible.value=false;
                       message.success("登录成功!!");
                   }else {
                       message.error(data.message)
                   }
                })
            };

            return{
                loginUser,
                loginModalVisible,
                loginModalLoading,
                showLoginModal,
                login
            }

        }
    });
</script>
<style>

    .logo {
        float: left;
        width: 200px;
        height: 64px;
        display: flex;
        flex-flow: row;
        text-align: center;
        justify-content: space-between;
    }
    img{
        width: 32px;
        height: 32px;
    }
</style>
