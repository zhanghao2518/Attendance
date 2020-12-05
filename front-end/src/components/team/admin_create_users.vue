<template>
<div>
  <div class="box">
  <p class='t'>账  号:
  <input type="text">
  </p>
 <p class='t'>年  龄:<input type="number"></p>
 <p class='t'>部门Id:<input type="number"></p>
 <p class='t'>部门:<input type="text"></p>
<p class='t'>邮  箱:<input type="text"></p>
<p class='t'>性  别:<input type="number"></p>
<p class='t'>级  别:<input type="number"></p>
 <p class='t'> Id:<input type="number"></p>
 <p class='t'>姓  名:<input type="text"></p>
 <p class='t'> 密  码:<input type="text"></p>
 <p class='t'>联系方式:<input type="text"></p>
<p class='t'>剩余假期:<input type="number"></p>
 <input type="button" @click="add_user" value="Submit">

  </div>
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
.box {
position: relative;
text-align: center;
background: #c4e0f040;

};

.t {
  display: inline-block;
  text-align :justify;
  text-align-last:justify;
  word-break: break-word;
  padding-top: 100rpx;
}
  
input[type="text"]{
background-color: fff(209, 202, 202);
    border: 1px solid rgba(0,0,0,.15);
    padding-left: 1em;
    width: 23em;
    height: 2.8em;
    margin-bottom: 1.2em;
    border-radius:9px
}

input[type="number"]{
background-color: fff(209, 202, 202);
    border: 1px solid rgba(0,0,0,.15);
    padding-left: 1em;
    width: 23em;
    height: 2.8em;
    margin-bottom: 1.2em;
    border-radius:9px
    
}
input[type="button"]{
margin-top: 50px;
  width: 200px;
  height: 30px;
  border-radius: 10px;
  border: 0;
  color: #fff;
  font-size: 15px;
  background-image: linear-gradient(to top, #30324d 0%, #5f6298 100%);
}


</style>
