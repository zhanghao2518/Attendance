<template>
  <div id="login_box">
    <h2>登 录</h2>
    <div id="form">
      <div class="input_box">
        <i class="fa fa-user" aria-hidden="true"></i>
        <input class="in" type="text" placeholder="Username" />
      </div>
      <div class="input_box">
        <i class="fa fa-key" aria-hidden="true"></i>
        <input class="in" type="password" placeholder="Password" />
      </div>
    </div>

    <button @click="login">Sign in</button><br />
  </div>

  <!-- <div class="login">
    <p>账号:</p>
    <el-input
      v-model="input"
      class="login-id"
      placeholder="请输入账号"
    ></el-input>

    <p>密码</p>
    <el-input
      class="login-password"
      placeholder="请输入密码"
      v-model="input"
      show-password
    ></el-input>
    <p>
      <el-button type="info" plain value="登录" @click="login">登录</el-button>
    </p>
  </div> -->
</template>



<script>
 import axios from "axios";
// import Aside from "@/components/Aside/Aside";
// import asideEmp from "@/components/Aside/Aside_emp";
// import asideManager from "@/components/Aside/Aside_manager";
// import asideAdmin from "@/components/Aside/admin_aside";
// import asideNone from "@/components/Aside/Aside_none";
export default {
  methods: {
    login() {
      let param = document.getElementsByTagName("input");
      console.log(param[0].value)
      console.log(param[1].value)
      axios
        .request({
          method: "post",
          url: "http://localhost:9999/login",
          params: { account: param[0].value, password: param[1].value },
        })
        .then((res) => {
          this.$root.user = res.data;
          console.log(this.$root.user.grade)
          this.$router.push('/')
        });
      // if (this.$root.user.grade === 0) {
      //   console.log("nb");
      //   this.iAside = asideEmp;
      // } else if (this.$root.user.grade === 1) {
      //   this.components.iAside = Aside;
      // } else if (this.$root.user.grade === 2) {
      //   this.components.iAside = asideManager;
      // } else if (this.$root.user.grade === 3) {
      //   this.components.iAside = asideAdmin;
      // } else {
      //   this.components.iAside = asideNone;
      // }
    },
  },
};
</script>

<style scoped>
body {
  background-image: url("http://www.talkimages.cn/images/medium/20133067/tkf003_446370.jpg");
  background-size: cover;
  background-repeat: no-repeat;
}
#login_box {
  width: 80%;
  height: 400px;
  background: #c4e0f040;
  margin: auto;
  margin-top: 2%;
  text-align: center;
  border-radius: 10px;
  padding: 50px 50px;
}
#login_box h2 {
  color: #6870df30;
  margin-top: 5%;
}
#login_box #form .input_box {
  margin-top: 5%;
}
#login_box #form .input_box i {
  color: #e8e7b730;
}
#login_box #form .input_box input {
  border: 0;
  width: 60%;
  font-size: 15px;
  color: rgb(14, 110, 253);
  background: #ffffff00;
  border-bottom: 2px solid#eacda950;
  padding: 5px 10px;
  margin-top: 10px;
}
#login_box button {
  margin-top: 50px;
  width: 40%;
  height: 30px;
  border-radius: 10px;
  border: 0;
  color: #fff;
  font-size: 15px;
  background-image: linear-gradient(to top, #30324d 0%, #5f6298 100%);
}
.sel {
  margin-top: 20px;
}
/* .login {
  padding: 20px 30px;
  width: 500px;
  margin: 100px auto;
  border-style: dotted;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2), 0 0 6px rgba(0, 0, 0, 0.3);
}
.login-id {
  width: 250px;
}
.login-password {
  width: 250px;
} */
</style>
