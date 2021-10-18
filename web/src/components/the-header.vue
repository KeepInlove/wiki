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
            <a-menu-item key="/admin/ebook">
                <router-link to="/admin/ebook">电子书管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/category">
                <router-link to="/admin/category">分类管理</router-link>
            </a-menu-item>
            <a-menu-item key="/about">
                <router-link to="/about">关于我</router-link>
            </a-menu-item>
        </a-menu>
    </a-layout-header>
</template>
<script lang="ts">
    import { defineComponent } from 'vue';

    export default defineComponent({
        name: 'the-header',
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
