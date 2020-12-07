<template>
  <el-menu :default-active="defaultActive" router>
    <el-menu-item
      v-for="(item, index) in fullMenu"
      :index="item.pathname"
      :key="index"
      class="menu"
    >
      <span slot="title">{{ item.name }}</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  data() {
    return {
      fullMenu: [
        {
          name: "查看所有员工",
          pathname: "/get_users",
        },
        {
          name: "创建账号",
          pathname: "/admin_create_users",
        },
      ],
    };
  },
  computed: {
    defaultActive() {
      return this.$route.path;
    },
  },
};
</script>

<style lang="css">
.menu {
  text-align: center;
  font-size: 16px;
}
</style>
