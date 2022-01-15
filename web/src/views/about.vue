<template>
  <div class="about">
            <div class="tip">
              <a-card>
                <div><b>网站技术：</b></div>
                <div>1. 统计数据是真实的，一分钟左右延时，<b>用到了定时器、复杂SQL统计、echarts</b></div>
                <div>2. 有文档被别人点赞，你也会收到实时通知哦！<b>用到了websocket、异步化</b></div>
                <div>3. 文档树可无限级扩展，支持文字、图片、视频。<b>用到了无限级树设计知识</b></div>
                <div>4. 登录后可看到更多菜单。<b>用到了单点登录、前后端登录拦截、安全性设计</b></div>
                <div>5. 技术栈思维导图:</div>
                <br> <br> <br>
                  <a-row type="flex" justify="space-around" align="middle">
                    <a-col :span="9">
                      <a-image :src="`${hdImageSrc}`"/>
                    </a-col>
                    <a-col :span="9">
                      <a-image :src="`${qdImageSrc}`"/>
                    </a-col>

                  </a-row>


              </a-card>
            </div>
  </div>
</template>
<script lang="ts">
  import {defineComponent,ref} from "vue";
  export default defineComponent({
    name: 'About',
    setup(){

      return{
        hdImageSrc: require("../assets/hd.jpg"),
        qdImageSrc:require('../assets/qd.jpg')
      }
    }
  });
</script>
<style scoped>
  .tip {
    padding: 10px 5px;
    margin-bottom: 20px;
    /*border: 1px solid transparent;*/
    background: linear-gradient(white,white) padding-box,repeating-linear-gradient(-45deg, black 0, black 25%, white 0, white 50%) 0/.6em .6em;
    animation:ants 12s linear infinite;
  }
  .tip b{
    color: red;
  }
  img{
    max-width: 30%;
    height: auto;
  }
</style>
