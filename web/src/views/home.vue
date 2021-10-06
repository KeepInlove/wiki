<template>
 <a-layout>
    <a-layout-sider width="200"  :style="{background: '#fff'}">
      <a-menu mode="inline" :style="{  height: '100%',borderRight: 0 ,}">
         <a-menu-item>
             <router-link :to="'/'">
<!--                 <SmileTwoTone />-->
<!--                 <HeartTwoTone twoToneColor="#eb2f96" />-->
<!--                 <CheckCircleTwoTone twoToneColor="#52c41a" />-->
                 <FolderOpenTwoTone style="fontSize:16px" />

                 <span>文档目录</span>
             </router-link>
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
    <a-layout-content :style="{ padding: '24px', margin: 0, minHeight: '280px' ,height: '100%' }">
        <a-card >
        <a-list item-layout="vertical" size="large"  :data-source="ebooks" :grid="{ gutter: 20, column: 3 }">
            <template #renderItem="{ item }">
                <a-list-item key="item.name">
                    <template #actions>
                        <span v-for="{ type, text } in actions" :key="type">
                            <component v-bind:is="type" style="margin-right: 8px" />
                            {{ text }}
                        </span>
                    </template>
                    <a-list-item-meta :description="item.description">
                        <template #title>
                            <a :href="item.href">{{ item.name }}</a>
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
    onMounted(()=> {
        handleQueryCategory();
        // // console.log('onMounted');
        axios.get("/ebook/list",{
            params:{
                page:1,
                size:200
            }
        }).then((res)=>{
            const data=res.data;
            ebooks.value=data.content.list;
            // ebooks1.books=data.content.list;
            // console.log(res)
        });
    });
    //   const pagination = {
    //       onChange: (page: number) => {
    //           console.log(page);
    //       },
    //       pageSize: 3,
    //   };
      const actions: Record<string, string>[] = [
          { type: 'StarOutlined', text: '123' },
          { type: 'LikeOutlined', text: '456' },
          { type: 'MessageOutlined', text: '66' },
      ];
      return{
          level1,
          ebooks,
          // listData,
          // pagination,
          handleQueryCategory,
          actions,
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
    /*.site-layout-background {*/
    /*    background: #fff;*/
    /*}*/
</style>
