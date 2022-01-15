<template>
    <a-layout>
        <a-layout-content :style="{background:'#fff',padding:'24px',margin:0,minHeight:'280px'}">
           <H2 v-if="level1.length==0" :style="{textAlign: 'center',color:'crimson'}">对比起，找不到相关文档</H2>
            <a-row>
                <a-col :span="5" :style="{textAlign: 'center'}">
                    <a-tree
                    v-if="level1.length > 0"
                    :tree-data="level1"
                    @select="onSelect"
                    :replace-fields="{title:'name',key:'id',value:'id'}"
                    :default-expand-all="true"
                    :defaultSelectedKeys="defaultSelectedKeys">
                    </a-tree>
                </a-col>
                <a-col :span="16">
                    <div>
<!--                        <h2>{{doc.name}}</h2>-->
                        <div>
                            <h2>{{doc.name}}</h2>
                            <span> <EyeTwoTone />阅读数: {{doc.viewCount}}</span>&nbsp;&nbsp;&nbsp;
                            <span><LikeTwoTone />点赞数: {{doc.voteCount}}</span>
                            <a-divider style="height: 2px;background-color: #9999cc"/>
                        </div>
                    </div>
                    <div class="wangeditor" :innerHTML="html"></div>
                    <div class="vote-div">
                        <a-button type="primary" shape="round" :size="large" @click="vote">
                            <template #icon><LikeOutlined/>&nbsp;&nbsp;点赞数 : {{doc.voteCount}}</template>
                        </a-button>
                    </div>
                </a-col>
        </a-row>
    </a-layout-content>
    </a-layout>
</template>
<script lang="ts">
    import {defineComponent, onMounted, ref} from "vue";
    import {useRoute} from "vue-router";
    import axios from "axios";
    import {Tool} from "@/util/tool";
    import {message} from "ant-design-vue";

    export default defineComponent({
        name: 'Doc',
        setup(){
            const route=useRoute();
            const docs=ref();
            const defaultSelectedKeys=ref();
            defaultSelectedKeys.value=[];
            const level1 = ref();
            const html=ref();
            level1.value=[];
            //选中的文档
            const doc=ref();
            doc.value={};

            //内容查询
            const handleQueryContent = (id:number) => {
                axios.get("/doc/findContent/"+id).then((response) => {
                    // console.log(response.data);
                    const data = response.data;
                    // console.log(data);
                    if (data.success) {
                        html.value=data.content;
                    } else {
                        message.error(data.message);
                    }
                });
            };
            /**
             * 数据查询
             **/
            const handleQuery = () => {
                axios.get("/doc/all/"+route.query.ebookId).then((response) => {
                    const data = response.data;
                    if (data.success) {
                        docs.value = data.content;
                        level1.value=[];
                        level1.value=Tool.array2Tree(docs.value,0);

                        if (Tool.isNotEmpty(docs.value)){
                            defaultSelectedKeys.value=[level1.value[0].id];
                            handleQueryContent(level1.value[0].id);
                            //初始显示文档
                            doc.value=level1.value[0]
                        }
                    } else {
                        message.error(data.message);
                    }
                });
            };
            //点赞
            const vote=()=>{
                axios.get('/doc/vote/'+doc.value.id).then((resp)=>{
                    const data=resp.data;
                    if (data.success){
                        doc.value.voteCount++;
                    }else {
                        message.error(data.message)
                    }
                })
            };
            //选中节点
           const onSelect=(selectedKeys:any,info:any)=>{
               // console.log('selected',selectedKeys,info);
               if (Tool.isNotEmpty(selectedKeys)){
                   //选中某个节点时,加载该节点的文档信息
                   doc.value=info.selectedNodes[0].props;
                   handleQueryContent(selectedKeys[0]);
               }
            };
            onMounted(()=>{
                handleQuery();
            });
            return{
                level1,
                html,
                doc,
                onSelect,
                vote
            }
        }
    });
</script>
<style>
    /* wangeditor默认样式, 参照: http://www.wangeditor.com/doc/pages/02-%E5%86%85%E5%AE%B9%E5%A4%84%E7%90%86/03-%E8%8E%B7%E5%8F%96html.html */
    /* table 样式 */
    .wangeditor table {
        border-top: 1px solid #ccc;
        border-left: 1px solid #ccc;
    }
    .wangeditor table td,
    .wangeditor table th {
        border-bottom: 1px solid #ccc;
        border-right: 1px solid #ccc;
        padding: 3px 5px;
    }
    .wangeditor table th {
        border-bottom: 2px solid #ccc;
        text-align: center;
    }

    /* blockquote 样式 */
    .wangeditor blockquote {
        display: block;
        border-left: 8px solid #d0e5f2;
        padding: 5px 10px;
        margin: 10px 0;
        line-height: 1.4;
        font-size: 100%;
        background-color: #f1f1f1;
    }

    /* code 样式 */
    .wangeditor code {
        display: inline-block;
        *display: inline;
        *zoom: 1;
        background-color: #f1f1f1;
        border-radius: 3px;
        padding: 3px 5px;
        margin: 0 3px;
    }
    .wangeditor pre code {
        display: block;
    }

    /* ul ol 样式 */
    .wangeditor ul, ol {
        margin: 10px 0 10px 20px;
    }

    /* 和antdv p冲突，覆盖掉 */
    .wangeditor blockquote p {
        font-family:"SimSun";
        margin: 20px 10px !important;
        font-size: 18px !important;
        font-weight:600;
    }

    /* 点赞 */
    .vote-div {
        padding: 15px;
        text-align: center;
    }

    /* 图片自适应 */
    .wangeditor img {
        max-width: 100%;
        height: auto;
    }

    /* 视频自适应 */
    .wangeditor iframe {
        width: 100%;
        height: 400px;
    }
    .ant-tree-title{
        font-size:17px

    }
</style>
