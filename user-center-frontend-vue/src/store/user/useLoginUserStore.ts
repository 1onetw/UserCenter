import { defineStore } from "pinia";
import { ref } from "vue";
import { getCurrentUser } from "@/api/user";

export const useLoginUserStore = defineStore("loginUser", () => {
  const loginUser = ref<any>({
    username: "未登录",
  });

  // 远程获取登录信息
  async function fetchLoginUser() {
    const res = await getCurrentUser();
    if (res.data.code === 0 && res.data.data) {
      loginUser.value = res.data.data;
    }
  }

  // 设置登录信息
  function setLoginUser(newLoginUser: any) {
    loginUser.value = {
      username: newLoginUser,
    };
  }

  return { loginUser, fetchLoginUser, setLoginUser };
});
