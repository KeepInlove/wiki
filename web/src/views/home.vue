<template>
    <a-layout>
     <a-layout-sider width="200px"  :style="{background: '#fff'}">
         <a-menu mode="inline" :style="{  height: '100%',borderRight: 0 }" @click="handleClick">
         <a-menu-item key="welcome">
             <SmileTwoTone  style="fontSize:16px" />
             <span>欢迎</span>
         </a-menu-item>
        <a-sub-menu v-for="item in level1" :key="item.id">
          <template v-slot:title>
              <span>{{item.name}}</span>
          </template>
          <a-menu-item v-for="child in item.children" :key="child.id">
              <span>{{child.name}}</span>
          </a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
     <a-layout-content :style="{ padding: '24px', margin: 0, minHeight: '450px'}">
            <a-card class="welcome" v-show="isShowWelcome">
                <h1>欢迎来到首页</h1>
            </a-card>
            <a-card v-show="!isShowWelcome">
                <a-list  item-layout="vertical" size="large"  :data-source="ebooks" :grid="{ gutter: 20, column: 3 }">
                <template #renderItem="{ item }">
                    <a-list-item key="item.name">
                        <template #actions>
                            <span>
                                <component v-bind:is="'EyeOutlined'" style="margin-right: 8px" />
                                {{ item.viewCount }}
                            </span>
                            <span>
                                <component v-bind:is="'LikeOutlined'" style="margin-right: 8px" />
                                {{ item.voteCount }}
                            </span>
                            <span>
                                <component v-bind:is="'FolderOpenOutlined'" style="margin-right: 8px" />
                                {{ item.docCount }}
                            </span>
                        </template>
                        <a-list-item-meta :description="item.description">
                            <template #title>
                                <router-link :to="'/doc?ebookId='+item.id">{{ item.name }}</router-link>
                            </template>
                            <template #avatar><a-avatar :src="item.cover" /></template>
                        </a-list-item-meta>
                        {{ item.content }}
                    </a-list-item>
                </template>
                </a-list>
             </a-card>
     </a-layout-content>
    </a-layout>
</template>

<script lang="ts">
import { defineComponent ,onMounted,ref} from 'vue';
import {message} from "ant-design-vue";
import {Tool} from "@/util/tool";
import axios from 'axios'
export default defineComponent({
  name: 'Home',
  setup(){
    console.log('setup');
    const ebooks=ref();
    // const ebooks1=reactive({books:[]});
      const level1 =  ref();
      let categorys: any;
      //查询所有分类
      const handleQueryCategory = () => {
          axios.get("/category/all").then((response) => {
              const data = response.data;
              if (data.success) {
                  categorys = data.content;
                  console.log("原始数组：", categorys);

                  level1.value = [];
                  level1.value = Tool.array2Tree(categorys, 0);
                  console.log("树形结构：", level1.value);
                  // 加载完分类后，再加载电子书，否则如果分类树加载很慢，则电子书渲染会报错
              } else {
                  message.error(data.message);
              }
          });
      };
      const handQueryEbook=()=>{
         axios.get("/ebook/list",{
             params:{
                 page:1,
                 size:200,
                 categoryId2: categoryId2
             }
         }).then((res)=>{
             const data=res.data;
             ebooks.value=data.content.list;
             // ebooks1.books=data.content.list;
             // console.log(res)
         });
     };
      const isShowWelcome=ref(true);
      let categoryId2=0;
      const handleClick=(value:any)=>{
          console.log("menu click",value);
          if (value.key==='welcome'){
              isShowWelcome.value=true;
          }else {
              categoryId2=value.key;
              isShowWelcome.value=false;
              handQueryEbook();
          }
          // isShowWelcome.value = value.key === 'welcome';
      };
    onMounted(()=> {
        handleQueryCategory();
    });
      // const actions: Record<string, string>[] = [
      //     { type: 'StarOutlined', text: '123' },
      //     { type: 'LikeOutlined', text: '456' },
      //     { type: 'MessageOutlined', text: '66' },
      //
      // ];
      return{
          handleQueryCategory,
          handleClick,
          level1,
          ebooks,
          // actions,
          isShowWelcome
      }
  },
});
</script>
<style scoped>
    .ant-avatar{
        width: 45px;
        height: 45px;
        line-height: 50px;
        border-radius: 8%;
        margin: 5px 0;
    }
</style>
