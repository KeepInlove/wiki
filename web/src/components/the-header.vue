<template>
    <a-layout-header class="header" :style="{backgroundColor:'#fff'}">
<!--        <div class="logo">-->
<!--            <div>-->
<!--            <img src="https://aliyuncdn.antdv.com/v2/assets/logo.1ef800a8.svg">-->
<!--            </div>-->
<!--            <h2>Mr Freshman</h2>-->
<!--        </div>-->
        <!--栅格布局-->
        <a-row>
            <a-col :span="4">
                <div class="logo">
                    <div>
                        <img src="https://aliyuncdn.antdv.com/v2/assets/logo.1ef800a8.svg">
                    </div>
                    <h2>知识库系统</h2>
                </div>
            </a-col>
            <a-col :span="16">
                <a-menu
                        theme="light"
                        mode="horizontal"
                        :style="{ lineHeight: '64px',fontSize:'16px'}">
                    <a-menu-item key="/">
                        <router-link to="/">首页</router-link>
                    </a-menu-item>
                    <a-menu-item key="/about">
                        <router-link to="/about">关于我</router-link>
                    </a-menu-item>
                    <a-menu-item key="/admin/user">
                        <router-link to="/admin/user" v-show="user.id">用户管理</router-link>
                    </a-menu-item>
                    <a-menu-item key="/admin/ebook">
                        <router-link to="/admin/ebook" v-show="user.id">文档管理</router-link>
                    </a-menu-item>
                    <a-menu-item key="/admin/category">
                        <router-link to="/admin/category" v-show="user.id">分类管理</router-link>
                    </a-menu-item>

                </a-menu>
            </a-col>
            <a-col :span="4">
                <a-menu
                        theme="light"
                        mode="horizontal"
                        :style="{ lineHeight: '64px'  ,float: 'right',fontSize:'16px'}">
                <a-menu-item key="login">
                    <a @click="showLoginModal" v-show="!user.id">
                        <span>登录</span>
                    </a>
                    <a-popconfirm
                            title="确认退出登录?"
                            ok-text="是"
                            cancel-text="否"
                            @confirm="logout()"
                    >
                        <a v-show="user.id">
                            <span>退出</span>
                        </a>
                    </a-popconfirm>
                </a-menu-item>
                <a-menu-item key="github">
                    <a href="https://github.com/KeepInlove" target="_blank">
                        <GithubOutlined style="fontSize:20px"/>
                    </a>
                </a-menu-item>
                </a-menu>
            </a-col>
        </a-row>
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
    import { defineComponent,ref ,computed} from 'vue';
    import { GithubOutlined } from '@ant-design/icons-vue';
    import {message} from "ant-design-vue";
    import axios from 'axios'
    import store from "@/store";
    declare let hexMd5:any;
    declare let KEY: any;
    export default defineComponent({
        name: 'the-header',
        components: {
            GithubOutlined
        },
        setup(){
            const user = computed(() => store.state.user);
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
                       store.commit("setUser", data.content);
                   }else {
                       message.error(data.message)
                   }
                })
            };
            // 退出登录
            const logout = () => {
                console.log("退出登录开始");
                axios.delete('/user/logout/' + user.value.token).then((response) => {
                    const data = response.data;
                    if (data.success) {
                        message.success("退出成功！");
                        store.commit("setUser", {});
                    } else {
                        message.error(data.message);
                    }
                });
            };
            return{
                user,
                loginUser,
                loginModalVisible,
                loginModalLoading,
                showLoginModal,
                login,
                logout,
            }

        }
    });
</script>
<style>

    .logo {
        width: 200px;
        height: 64px;
        display: flex;
        flex-flow: row;
        text-align: center;
        justify-content: space-around;
    }
    img{
        width: 32px;
        height: 32px;
    }
</style>
