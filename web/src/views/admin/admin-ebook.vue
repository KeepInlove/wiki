<template>

    <a-layout>
        <a-layout-content :style="{ padding: '24px', margin: 0, minHeight: '280px' }">
            <a-table
                    :columns="columns"
                    :row-key="record => record.id"
                    :data-source="ebooks"
                    :pagination="pagination"
                    :loading="loading"
                    @change="handleTableChange"
            >
                <template #cover="{ text: cover }">
                    <img v-if="cover" :src="cover" alt="avatar" />
                </template>
                <template v-slot:category="{ text, record }">
<!--                    <span>{{ getCategoryName(record.category1Id) }} / {{ getCategoryName(record.category2Id) }}</span>-->
                </template>
                <template v-slot:action="{ text, record }">
                    <a-space size="small">
                        <router-link :to="'/admin/doc?ebookId=' + record.id">
                            <a-button type="primary">
                                文档管理
                            </a-button>
                        </router-link>
                        <a-button type="primary" @click="edit(record)">
                            编辑
                        </a-button>
                        <a-popconfirm
                                title="删除后不可恢复，确认删除?"
                                ok-text="是"
                                cancel-text="否"
                                @confirm="handleDelete(record.id)"
                        >
                            <a-button type="danger">
                                删除
                            </a-button>
                        </a-popconfirm>
                    </a-space>
                </template>
            </a-table>
        </a-layout-content>
    </a-layout>
</template>

<script lang="ts">
    import { defineComponent, onMounted, ref } from 'vue';
    import axios from 'axios';
    import { message } from 'ant-design-vue';
    // import {Tool} from "@/util/tool";
    export default {
        name: "AdminEbook",
        setup(){
            const param = ref();
            param.value = {};
            const ebooks = ref();
            const pagination = ref({
                current: 1,
                pageSize: 10,
                total: 0
            });
            const loading = ref(false);

            const columns = [
                {
                    title: '封面',
                    dataIndex: 'cover',
                    slots: { customRender: 'cover' }
                },
                {
                    title: '名称',
                    dataIndex: 'name'
                },
                {
                    title: '分类',
                    slots: { customRender: 'category' }
                },
                {
                    title: '文档数',
                    dataIndex: 'docCount'
                },
                {
                    title: '阅读数',
                    dataIndex: 'viewCount'
                },
                {
                    title: '点赞数',
                    dataIndex: 'voteCount'
                },
                {
                    title: 'Action',
                    key: 'action',
                    slots: { customRender: 'action' }
                }
            ];
            return{
                param,
                ebooks:[
                    {
                        "id": 1,
                        "name": "JavaScript 入门教程",
                        "category1Id": null,
                        "category2Id": null,
                        "description": "零基础入门 Java 开发，企业级应用开发最佳首选框架",
                        "cover": "/image/cover2.png",
                        "docCount": 0,
                        "viewCount": 0,
                        "voteCount": 0
                    },
                    {
                        "id": 2,
                        "name": "Vue 入门教程",
                        "category1Id": null,
                        "category2Id": null,
                        "description": "零基础入门 Vue 开发，企业级应用开发最佳首选框架",
                        "cover": "/image/cover1.png",
                        "docCount": 0,
                        "viewCount": 0,
                        "voteCount": 0
                    }],
                pagination,
                columns,
                loading,
                // handleTableChange,
                // handleQuery,
                // getCategoryName,
                //
                // edit,
                // add,
                //
                // ebook,
                // modalVisible,
                // modalLoading,
                // handleModalOk,
                // categoryIds,
                // level1,
                //
                // handleDelete
            }
        }
    }
</script>

<style scoped>
    img {
        width: 50px;
        height: 50px;
    }
</style>
