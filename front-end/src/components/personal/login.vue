<template>
  <div>
    <p>账号</p>
    <input type="text">
    <p>密码</p>
    <input type="password">
    <input type="button" value="登录" @click="login">
  </div>
</template>

<script>
  import user from '@/components/global_data'
  import axios from 'axios'
  export default {
    methods:{
      login(){
        let param=document.getElementsByTagName('input')
        axios.request({
          method:'post',
          url:'http://localhost:9999/login',
          params:{'account':param[0].value,'password':param[1].value}
        }).then(res=>{
          this.$root.user=res.data
        })
      }
    }
  }
</script>

<style scoped>

</style>
