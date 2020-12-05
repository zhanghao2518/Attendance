import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
import personalAttendance from '@/components/personal/personal_attendance'
import personalVocation from '@/components/personal/personal_vocation'
import getUsers from '@/components/team/admin_get_users'
import teamAttendance from "../components/team/team_attendance";
import createUser from '@/components/team/admin_create_users'
import login from "@/components/personal/login"
import teamVocation from "@/components/team/team_vocation"


export default new Router({
  routes: [
      {
          path: '/login',
          component: login
      },
      {
          path: '/',
          redirect: '/team_vocation'
      },{
          path: '/team_attendance',
          component: teamAttendance
      }, {
          path: '/team_vocation',
          component: teamVocation
      }, {
          path: '/personal_attendance',
          component: personalAttendance
      }, {
        path: '/admin_create_users',
        component: createUser
      }, {
        path: '/personal_vocation',
        component: personalVocation
      }, {
        path: '/get_users',
        component: getUsers
    }
  ]
})
