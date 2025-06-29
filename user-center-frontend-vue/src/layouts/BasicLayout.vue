<template>
  <div id="basicLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header">
        <GlobalHeader />
      </a-layout-header>
      <a-layout-content class="content">
        <RouterView />
      </a-layout-content>
      <a-layout-footer class="footer">
        <a href="https://www.journy.online"> lcz博客 by 程序员啾啾 </a>
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<script setup lang="ts">
import GlobalHeader from "@/components/GlobalHeader.vue";
</script>

<style scoped>
#basicLayout .footer {
  background: #efefef;
  text-align: center;
  padding: 16px;
}

#basicLayout .header {
  background: white;
  margin-bottom: 16px;
  padding-inline: 20px;
}
</style>
