<template>
    <a-layout-footer :style="{textAlign:'center',backgroundColor:'#fff',marginBottom:'0'}" >
        <CopyrightCircleTwoTone /> 2021 Created by Guo
        <br/>
        <SafetyCertificateTwoTone />
        备案号: <a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=你的备案号" target="_blank" :style="{color: '#000001'}">123456789xxxx</a>
    </a-layout-footer>
</template>

<script>
    import {SafetyCertificateTwoTone,CopyrightCircleTwoTone ,} from '@ant-design/icons-vue';
    export default {
        name: "the-footer",
        components: {
            SafetyCertificateTwoTone,
            CopyrightCircleTwoTone
        },
    }
</script>

<style scoped>

</style>
