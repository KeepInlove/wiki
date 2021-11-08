<template>
    <a-layout>
        <a-layout-content :style="{ padding: '24px', margin: 0, minHeight: '280px' }">
            <p>
                <a-form layout="inline" :model="param">
                    <a-form-item>
                        <a-button type="primary" @click="add()">
                            新增
                        </a-button>
                    </a-form-item>
                </a-form>
            </p>
            <a-table
                    :columns="columns"
                    :row-key="record => record.id"
                    :data-source="level1"
                    :pagination="false"
                    :loading="loading">
                <template #cover="{ text: cover }">
                    <img v-if="cover" :src="cover" alt="avatar"/>
                </template>
                <template v-slot:doc="{ text, record }">
                </template>
                <template v-slot:action="{ text, record }">
                    <a-space size="small">

                        <a-button type="primary" @click="edit(record)">
                            编辑
                        </a-button>
                        <a-popconfirm
                                title="删除后不可恢复，确认删除?"
                                ok-text="是"
                                cancel-text="否"
                                @confirm="showDeleteConfirm(record.id,record.name)"
                        >
                            <a-button type="primary" danger>
                                删除
                            </a-button>
                        </a-popconfirm>
                    </a-space>
                </template>
            </a-table>

        </a-layout-content>
    </a-layout>
    <a-modal
            title="文档表单"
            v-model:visible="modalVisible"
            :confirm-loading="modalLoading"
            @ok="handleModalOk">
        <a-form :model="doc" :label-col="{span:6}" :wrapper-col="{spin:18}">
            <a-form-item label="名称">
                <a-input v-model:value="doc.name"/>
            </a-form-item>
            <a-form-item label="父文档">
                <a-tree-select
                        v-model:value="doc.parent"
                        style="width: 100%"
                        :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                        :tree-data="treeSelectData"
                        placeholder="请选择父文档"
                        tree-default-expand-all
                        :replaceFields="{title:'name', key:'id', value: 'id'}"
                >
                </a-tree-select>
            </a-form-item>
            <a-form-item label="顺序">
                <a-input v-model:value="doc.sort"/>
            </a-form-item>
            <a-form-item label="内容">
                <div id="editContent" ></div>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script lang="ts">
    import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
    import { defineComponent, onMounted, ref ,createVNode} from 'vue';
    import axios from 'axios';
    import { message,Modal  } from 'ant-design-vue';
    import {Tool} from "@/util/tool";
    import {useRoute} from "vue-router";
    import E from 'wangeditor'
    export default {
        name: "AdminDoc",
        // eslint-disable-next-line @typescript-eslint/explicit-module-boundary-types
        setup(){
            const route=useRoute();
            console.log("路由:",route);
            console.log("route.path:",route.path);
            console.log("route.query:",route.query);
            console.log("route.params:",route.params);
            console.log("route.fullPath:",route.fullPath);
            console.log("route.name:",route.name);
            console.log("route.meta:",route.meta);
            const param = ref();
            param.value = {};
            const docs = ref();
            const loading = ref(false);
            const columns = [
                {
                    title: '名称',
                    dataIndex: 'name'
                },
                {
                    title: '父文档',
                    key:'parent',
                    dataIndex: 'parent'
                },
                {
                    title: '顺序',
                    dataIndex: 'sort'
                },

                {
                    title: 'Action',
                    key: 'action',
                    slots: { customRender: 'action' }
                }
            ];

            /**
             * 数据查询
             **/
            const handleQuery = () => {
                loading.value = true;
                // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
                level1.value = [];
                axios.get("/doc/all").then((response) => {
                    loading.value = false;
                    const data = response.data;
                    if (data.success) {
                        docs.value = data.content;
                        console.log("原始数组:",docs.value);

                        level1.value=[];
                        level1.value=Tool.array2Tree(docs.value,0);
                        console.log("树形结构:",level1)
                    } else {
                        message.error(data.message);
                    }
                });
            };

            // -------- 表单 ---------
            //因为树选择组件的属性状态,会随当前编辑的节点而变化,所以单独声明一个响应式变量
            const treeSelectData=ref();
            treeSelectData.value=[];
            const docIds = ref();
            const doc = ref();
            const modalVisible = ref(false);
            const modalLoading = ref(false);
            //创建富文本编辑器
            const editor = new E('#editContent');


            const handleModalOk = () => {
                modalLoading.value = true;
                axios.post("/doc/save", doc.value).then((response) => {
                    modalLoading.value = false;
                    const data = response.data; // data = commonResp
                    if (data.success) {
                        modalVisible.value = false;
                        // 重新加载列表
                        handleQuery();
                    } else {
                        message.error(data.message);
                    }
                });
            };

            //将某节点及其子孙节点全部为disable,  ----递归算法
            const  setDisable=(treeSelectData:any,id:any)=>{
              //遍历数组,即遍历某一层节点
                for (let i=0;i<treeSelectData.length;i++){
                    const node=treeSelectData[i];
                    if (node.id===id){
                        console.log("disabled",node);
                        node.disabled=true;

                        //遍历所有子节点,将所有子节点全部都加上disabled
                        const children=node.children;
                        if (Tool.isNotEmpty(children)){
                            for (let j=0;j<children.length;j++){
                                setDisable(children,children[j].id)
                            }
                        }
                    }else {
                        const children=node.children;
                        if ((Tool.isNotEmpty(children))){
                            setDisable(children,id)
                        }
                    }
                }
            };

            //查找整根树枝
            const ids:Array<string>=[];
            const  getDeleteIds=(treeSelectData:any,id:any)=>{
                //遍历数组,即遍历某一层节点
                for (let i=0;i<treeSelectData.length;i++){
                    const node=treeSelectData[i];
                    if (node.id===id){
                        console.log("delete",node);
                        // node.disabled=true;
                        //将目标ID放入结果集ids
                        ids.push(id);
                        //遍历所有子节点,将所有子节点全部都加上disabled
                        const children=node.children;
                        if (Tool.isNotEmpty(children)){
                            for (let j=0;j<children.length;j++){
                                getDeleteIds(children,children[j].id)
                            }
                        }
                    }else {
                        const children=node.children;
                        if ((Tool.isNotEmpty(children))){
                            getDeleteIds(children,id)
                        }
                    }
                }
            };
            //编辑
            const edit = (record: any) => {

                //弹出model
                modalVisible.value = true;
                doc.value = Tool.copy(record);
                //不能选择当前节点及其所有子孙节点,作为父节点,会使树断开
                treeSelectData.value=Tool.copy(level1.value);
                setDisable(treeSelectData.value,record.id);
                treeSelectData.value.unshift({id:0,name:'无'});
                // docIds.value = [doc.value.doc1Id, doc.value.doc2Id]
                setTimeout(function () {
                    editor.create();
                },100);
            };

            //新增
            const add = () => {

                //弹出model
                modalVisible.value = true;
                doc.value = {
                    ebookId:route.query.ebookId
                };
                treeSelectData.value=Tool.copy(level1.value);
                treeSelectData.value.unshift({id:0,name:'无'});
                setTimeout(function () {
                    editor.create();
                },100);
            };

            const showDeleteConfirm = (id:any,name:any) => {
                console.log(name)
                getDeleteIds(level1.value,id);
                Modal.confirm({
                    title:  '重要提示',
                    icon:  createVNode(ExclamationCircleOutlined),
                    content:  '确认将删除【'+name+'】下所有文件?删除后不可恢复!' ,
                    okText:  '确定',
                    okType: 'danger',
                    cancelText: '取消',
                    onOk() {
                        console.log("删除id数组:",ids);
                        handleDelete();
                    },
                    onCancel() {
                        console.log('Cancel');
                    },
                });
            };
            const handleDelete = () => {
                //删除之前调用
                // console.log(id);
                // console.log("调用前:",level1.value,ids);
                // getDeleteIds(level1.value,id);
                // console.log("调用后:",level1.value,ids);

                axios.delete("/doc/delete/"+ids.join(",") ).then((response) => {
                    const data = response.data; // data = commonResp
                    if (data.success) {
                        // 重新加载列表
                        handleQuery();
                    } else {
                        message.error(data.message);
                    }
                });
            };
            const level1 =  ref();
            onMounted(() => {
                handleQuery();

            });

            return {
                param,
                docs,
                columns,
                loading,
                handleQuery,
                edit,
                add,
                doc,
                modalVisible,
                modalLoading,
                handleModalOk,
                docIds,
                level1,
                handleDelete,
                treeSelectData,
                showDeleteConfirm
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
