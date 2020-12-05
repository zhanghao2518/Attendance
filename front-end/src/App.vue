<template>
  <div id="app">
    <el-container>
      <el-aside width="250px">
        <div>
          <img src="@/assets/tju.jpg" alt="公司的图标" class="business_logo" />
        </div>
        <div class="personal_position" @click="login">
          <img src="@/assets/tou.jpg" alt="个人的头像" class="personal_logo" />
        </div>
        <div class="personal_name">
          <p>{{ this.$root.user.name }}</p>
        </div>
        <component :is="iAside"></component>
      </el-aside>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
import Aside from "@/components/Aside/Aside";
import asideEmp from "@/components/Aside/Aside_emp";
import asideManager from "@/components/Aside/Aside_manager";
import asideAdmin from "@/components/Aside/admin_aside";
import asideNone from "@/components/Aside/Aside_none";
// console.log("1111" + this.$root.user.grade);
export default {
  name: "App",

  data() {
    return {
      iAside: Object,
      personalName: this.$root.user.name,
    };
  },
  components: {
    asideEmp,
    asideManager,
    asideAdmin,
    asideNone,
    Aside
  },
  updated() {
    console.log(this.$root.user.grade)
    if (this.$root.user.grade === 0) {
        console.log("nb");
        this.iAside = asideEmp;
      } else if (this.$root.user.grade === 1) {
        this.iAside = Aside;
      } else if (this.$root.user.grade === 2) {
        this.iAside = asideManager;
      } else if (this.$root.user.grade === 3) {
        this.iAside = asideAdmin;
      } else {
        this.iAside = asideNone;
      }
  },
  methods: {
    login() {
      this.$router.push("/login");
      console.log(this.$root.user.grade);
      //   if (this.$root.user.grade === 0) {
      //     console.log("nb");
      //     this.iAside = asideEmp;
      //   } else if (this.$root.user.grade === 1) {
      //     this.components.iAside = Aside;
      //   } else if (this.$root.user.grade === 2) {
      //     this.components.iAside = iAside_dep;
      //   } else if (this.$root.user.grade === 3) {
      //     this.components.iAside = iAside_manager;
      //   } else {
      //     this.components.iAside = iAside_none;
      //   }
    },
  },
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
.business_logo {
  width: 20%;
}
.personal_position {
  text-align: center;
}
.personal_logo {
  width: 20%;
  height: 20%;
}
.personal_name {
  text-align: center;
  font-size: 16px;
}
</style>
