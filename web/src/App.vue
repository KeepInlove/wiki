<template>
<!--  <a-layout id="components-layout-demo-top-side-2">-->
<!--    <the-header></the-header>-->
<!--    <router-view/>-->
<!--    <the-footer></the-footer>-->
<!--  </a-layout>-->

  <a-layout>
    <the-header></the-header>
    <router-view/>
    <the-footer></the-footer>
  </a-layout>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  // @ is an alias to /src
  import TheHeader from '@/components/the-header.vue';
  import TheFooter from '@/components/the-footer.vue'
  export default defineComponent({
    name: 'app',
    components:{
      TheHeader,
      TheFooter
    },
  });
</script>
