import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from "../views/Login";
import EditUserVue from "../views/AdminUserView";
import EditCourseVue from "../views/AdminCourseView";
import axios from 'axios';


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        meta:{
            title: 'Home | Learning Management System',
            requiresAuth:true
        }
    },
    {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../components/About.vue'),
        meta:{
            title: 'About | Learning Management System'
        }
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
        meta:{
            title: 'Login | Learning Management System'
        }
    },
    {
        path:'/admin/courses',
        name: 'Courses',
        component: EditCourseVue,
        meta:{
            title: 'Course Settings | Learning Management System',
            requiresAuth: true,
            adminOnly: true
        }
    },
    {
        path:'/admin/users',
        name:'Users',
        component: EditUserVue,
        meta:{
            title: 'User Settings | Learning Management System',
            requiresAuth: true,
            adminOnly: true
        }

    },
]

const router = new VueRouter({
    routes
})

router.beforeEach((to, from, next) => {
    if(to.matched.some(record => record.meta.requiresAuth)){
        const validateUrl = "http://52.221.228.219/api/auth/validate";
        axios.get(validateUrl, {withCredentials: true})
            .then(response => {
                if(response.data.status === "success"){
                    Vue.$cookies.config(60 * 35); // set 35 minute expired token
                    if(to.matched.some(record => record.meta.adminOnly)){
                        if(Vue.$cookies.get("role") === "admin"){
                            next();
                        } else{
                            next({name: 'Home'})
                        }
                    } else{
                        next();
                    }
                } else {
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

router.afterEach((to) => {
    // Use next tick to handle router history correctly
    Vue.nextTick(() => {
        document.title = to.meta.title;
    });
});

export default router
