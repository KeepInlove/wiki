<template>
    <a-layout-footer :style="{textAlign:'center',backgroundColor:'#fff',marginBottom:'0'}" >
        <CopyrightCircleTwoTone /> 2021-2022 Created by Guo
        <br/>
        <SafetyCertificateTwoTone />
        备案号: <a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=豫ICP备2021029145号-1" target="_blank" :style="{color: '#000001'}">豫ICP备2021029145号-1</a>
    </a-layout-footer>
</template>

<script lang="ts">
    import {Tool} from "@/util/tool";
    import store from "@/store";
    import {defineComponent,computed,onMounted} from 'vue'
    import { notification } from 'ant-design-vue';
    import {SafetyCertificateTwoTone,CopyrightCircleTwoTone ,} from '@ant-design/icons-vue';
    export default {
        name: "the-footer",
        components: {
            SafetyCertificateTwoTone,
            CopyrightCircleTwoTone
        },
        setup(){
            const user=computed(()=>store.state.user);
            let websocket:any;
            let token:any;
            const onOpen=()=>{
                // console.log('webSocket连接成功,状态码:',websocket.readyState)
            };
            const onMessage = (event: any) => {
                console.log('WebSocket收到消息：', event.data);
                notification['info']({
                    message: '收到消息',
                    description: event.data,
                });
            };
            const onError = () => {
                // console.log('WebSocket连接错误，状态码：', websocket.readyState)
            };
            const onClose = () => {
                // console.log('WebSocket连接关闭，状态码：', websocket.readyState)
            };
            const initWebSocket = () => {
                // 连接成功
                websocket.onopen = onOpen;
                // 收到消息的回调
                websocket.onmessage = onMessage;
                // 连接错误
                websocket.onerror = onError;
                // 连接关闭的回调
                websocket.onclose = onClose;
            };

            onMounted(() => {
                // WebSocket
                if ('WebSocket' in window) {
                    token = Tool.uuid(10);
                    // 连接地址：ws://127.0.0.1:8880/ws/xxx
                    websocket = new WebSocket(process.env.VUE_APP_WS_SERVER + '/ws/' + token);
                    initWebSocket()

                    // 关闭
                    // websocket.close();
                } else {
                    alert('当前浏览器 不支持')
                }
            });

            return {
                user
            }
        }
    }
</script>

<style scoped>

</style>
