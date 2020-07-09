<template>
    <div class="home">
        <b>{{this.$cookies.get("firstname")}} {{this.$cookies.get("lastname")}}'s Dashboard</b>
        <h3 v-if="requested_term.semester !== ''">{{requested_term.semester}}, {{requested_term.year}}</h3>
        <b-container fluid>
            <b-row align-h="start">
                <b-col sm="2">
                    <b-form-select v-model="selected_term" :options="options" size="sm" class="mt-3"></b-form-select>
                </b-col>
            </b-row>
        </b-container>

        <component :is="component_name" :course="course" :busy="busy"></component>
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
                selected_term: null,
                component_name: '',
                course: '',
                requested_term: {
                    id: '',
                    semester: '',
                    year:'',
                },
                // Dropdown options
                showed_term: '',
                options: [],
                busy: false
            }
        },
        watch:{
            selected_term: function(){
                this.busy = true;
                const apiURL = "http://localhost:8081/api/dashboard";
                axios.post(apiURL, {termId: this.selected_term},
                    {withCredentials: true})
                    .then(response => {
                        if(response.data.role){
                            this.course = response.data.courses;
                            this.requested_term = response.data.requestedTerm;
                        }
                    })
                this.busy = false;
            }
        },
        created() {
            const apiURL = "http://localhost:8081/api/dashboard";
            this.busy = true;
            axios.post(apiURL, {termId:null},
            {withCredentials: true})
                .then(response => {
                    if(response.data.role){
                        this.requested_term = response.data.requestedTerm;
                        this.course = response.data.courses;
                        this.options = response.data.termOptions.map(x => ({"value":x.id, "text":x.semester+", "+x.year}));
                        this.selected_term = response.data.requestedTerm.id;
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
            // this.busy = false;
            this.busy = false;
        }
    }
</script>
