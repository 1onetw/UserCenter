<template>
  <div id="globalHeader">
    <a-row>
      <a-col flex="200px">
        <div class="title-bar">
          <img src="" alt="logo" class="logo" />
          <div class="title">啾啾用户中心</div>
        </div>
      </a-col>
      <a-col flex="auto">
        <a-menu
          v-model:selectedKeys="current"
          mode="horizontal"
          :items="items"
        />
      </a-col>
      <a-col flex="80px">
        <a-button type="primary" href="/user/login">登录</a-button>
      </a-col>
    </a-row>
  </div>
</template>
<script lang="ts" setup>
import { h, ref } from "vue";
import { HomeOutlined, CrownOutlined } from "@ant-design/icons-vue";
import { MenuProps } from "ant-design-vue";

const current = ref<string[]>(["mail"]);
const items = ref<MenuProps["items"]>([
  {
    key: "/",
    icon: () => h(HomeOutlined),
    label: "主页",
    title: "主页",
  },
  {
    key: "/user/login",
    label: "用户登录",
    title: "用户登录",
  },
  {
    key: "/user/register",
    label: "用户注册",
    title: "用户注册",
  },
  {
    key: "/user/userManage",
    icon: () => h(CrownOutlined),
    label: "用户管理",
    title: "用户管理 ",
  },
  {
    key: "others",
    label: h(
      "a",
      { href: "https://journy.online", target: "_blank" },
      "lcz博客"
    ),
    title: "lcz博客",
  },
]);
</script>

<style scoped>
#globalHeader .title-bar {
  display: flex;
  align-items: center;
}

#globalHeader .title {
  color: black;
  font-size: 18px;
  margin-left: 16px;
}

#globalHeader .logo {
  height: 48px;
}
</style>
