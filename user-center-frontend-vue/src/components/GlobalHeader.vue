<template>
  <div id="globalHeader">
    <a-row :wrap="false">
      <a-col flex="200px">
        <div class="title-bar">
          <img src="@/assets/logo.png" alt="logo" class="logo" />
          <div class="title">啾啾用户中心</div>
        </div>
      </a-col>
      <a-col flex="auto">
        <a-menu
          v-model:selectedKeys="current"
          mode="horizontal"
          :items="items"
          @click="doMenuClick"
        />
      </a-col>
      <a-col flex="80px">
        <div v-if="loginUserStore.loginUser.id">
          {{ loginUserStore.loginUser.username ?? "无名" }}
        </div>
        <div v-else>
          <a-button type="primary" href="/user/login">登录</a-button>
        </div>
      </a-col>
    </a-row>
  </div>
</template>
<script lang="ts" setup>
import { h, ref } from "vue";
import { HomeOutlined, CrownOutlined } from "@ant-design/icons-vue";
import { MenuProps } from "ant-design-vue";
import { useRouter } from "vue-router";
import { useLoginUserStore } from "@/store/user/useLoginUserStore";

const loginUserStore = useLoginUserStore();
console.log(loginUserStore.loginUser.username);
const router = useRouter();
const current = ref<string[]>();
router.afterEach((to, from, next) => {
  current.value = [to.path];
});
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
    key: "/admin/userManage",
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
// 路由跳转事件
const doMenuClick = ({ key }: { key: string }) => {
  router.push({
    path: key,
  });
};
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
