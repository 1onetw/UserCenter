<template>
  <div id="app">
    <BasicLayout />
  </div>
</template>

<style></style>

<script setup lang="ts">
import BasicLayout from "@/layouts/BasicLayout.vue";
import { useLoginUserStore } from "./store/user/useLoginUserStore";

const loginUserStore = useLoginUserStore();
// 远程获取登录用户信息
// loginUserStore.fetchLoginUser();

// 设置登录用户信息
// loginUserStore.setLoginUser("论昌周");
</script>
