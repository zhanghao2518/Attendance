<template>
<div>
  账号:<input type="text">
  年龄:<input type="number">
  部门Id:<input type="number">
  部门名称:<input type="text">
  邮箱:<input type="text">
  性别:<input type="number">
  级别:<input type="number">
  Id:<input type="number">
  姓名:<input type="text">
  密码:<input type="text">
  联系方式:<input type="text">
  剩余假期:<input type="number">
  <input type="button" @click="add_user">
</div>
</template>

<script>
    import axios from 'axios'
    export default {
      methods: {
        add_user() {
          let x = document.getElementsByTagName('input')
          axios.request({
            headers: {  'Content-Type': 'application/json'  },
            method: 'POST',
            url: 'http://localhost:9999/user/add',
            data: {
              'account': x[0].value, 'age': x[1].value, 'did': x[2].value, 'dname': x[3].value, 'email': x[4].value,
              'gender': x[5].value, 'grade': x[6].value, 'id': x[7].value, 'name': x[8].value, 'password': x[9].value,
              'telephone': x[9].value, 'timeReamining': x[10].value
            }
          }).then(res => {
            console.log(res);
          })
        }
      }
    }
</script>

<style scoped>

</style>
