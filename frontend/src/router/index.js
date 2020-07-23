import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from "../views/Login";
import Setting from "../components/AdminSettingComponent";
import EditUserVue from "../components/AdminUserView";
import EditCourseVue from "../components/AdminCourseView";
import axios from 'axios';

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
    },
    {
        path:'/admin/courses',
        name: 'Courses',
        component: EditCourseVue,
        meta:{
            requiresAuth: true
        }
    },
    {
        path:'/admin/users',
        name:'Users',
        component: EditUserVue,
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

        const validateUrl = "http://localhost:8081/api/auth/validate";
        axios.get(validateUrl, {withCredentials: true})
            .then(response => {
                if(response.data.status === "success"){
                    console.log("validated")
                    next();
                } else {
                    this.$cookies.remove("username");
                    this.$cookies.remove("firstname");
                    this.$cookies.remove("lastname");
                    console.log("not validate");
                    next({name: "Login"})
                }
            })
            .catch(() =>{
                console.log("Server request failed")
                next({name:"Login"})
            })
    } else{
        next();
    }
})

export default router
