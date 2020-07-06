<template>
    <div class="home">
        <component :is="component_name" :course="course"></component>
    </div>
</template>

<script>
    // @ is an alias to /src
    import StudentDashboard from '@/components/StudentDashboard.vue'
    import InstructorDashboard from "@/components/InstructorDashboard";
    import Vue from "vue";
    import axios from "axios";

    export default {
        name: 'Home',
        components: {
            StudentDashboard,
            InstructorDashboard
        },
        data(){
            return{
                component_name: '',
                course: ''
            }
        },
        created() {
            const apiURL = "http://localhost:8081/api/dashboard";
            axios.post(apiURL, {})
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
                        }
                    } else{
                        this.$router.push("/login");
                    }
                })
                .catch(()=> {
                    console.log("Dashboard REST call failed.")
                })
        }
    }
</script>
