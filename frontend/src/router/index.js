import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from "../views/Login";
import Setting from "../components/Setting";

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta:{
      requiresAuth:true
    }
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../components/About.vue')
  },
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
    {
      path:'/setting',
      name:'Setting',
      component: Setting,
      meta:{
        requiresAuth: true
      }
    }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if(to.matched.some(record => record.meta.requiresAuth)){
    console.log("yup");
    if(!Vue.prototype.$session.get("auth")){
      console.log("not auth yet");
      next({
        name: "Login"
      })
    } else{
      console.log("auth");
      next();
    }
  } else{
    console.log("na")
    next();
  }
})

export default router
