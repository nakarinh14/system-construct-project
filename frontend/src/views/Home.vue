<template>
    <div class="home">
        <h3>{{this.$cookies.get("firstname")}} {{this.$cookies.get("lastname")}}'s Dashboard</h3>
        <component :is="component_name" :course="course"></component>
    </div>
</template>

<script>
    // @ is an alias to /src
    import StudentDashboard from '@/components/StudentDashboard.vue'
    import InstructorDashboard from '@/components/InstructorDashboard.vue';
    import AdminDashboard from '@/components/AdminDashboard.vue';
    import axios from "axios";

    export default {
        name: 'Home',
        components: {
            StudentDashboard,
            InstructorDashboard,
            AdminDashboard
        },
        data(){
            return{
                component_name: '',
                course: ''
            }
        },
        created() {
            const apiURL = "http://localhost:8081/api/dashboard";
            axios.get(apiURL, {withCredentials: true})
                .then(response => {
                    if(response.data.role){
                        this.course = response.data.courses;
                        switch(response.data.role){
                            case "student":
                                this.component_name = "StudentDashboard"
                                break;
                            case "instructor":
                                this.component_name = "InstructorDashboard"
                                break;
                            case "admin":
                                this.component_name = "AdminDashboard"
                                break;
                        }
                    }
                })
                .catch(()=> {
                    console.log("Dashboard REST call failed.")
                })
        }
    }
</script>
