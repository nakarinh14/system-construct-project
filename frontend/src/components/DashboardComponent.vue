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


        <component :is="component_name" :course="course" :busy="busy" :filter="search_filter"></component>
    </div>
</template>

<script>
    // @ is an alias to /src
    import StudentDashboard from '@/components/StudentDashboard.vue'
    import InstructorDashboard from '@/components/InstructorDashboard.vue';
    import axios from "axios";

    export default {
        name: 'DashboardComponent',
        components: {
            StudentDashboard,
            InstructorDashboard,
        },
        data(){
            return{
                component_name: '',
                selected_term: 0,
                course: '',
                // Response term.
                requested_term: {
                    id: '',
                    semester: '',
                    year:'',
                },
                // Dropdown options
                showed_term: '',
                options: [],
                busy: false,
                search_filter: '',
                currentPage: 0
            }
        },
        watch:{
            selected_term: function(){
                this.busy = true;
                const apiURL = "http://localhost:8081/api/dashboard";
                axios.get(apiURL,
                    {
                        params: {
                            id: this.selected_term,
                        },
                        withCredentials: true
                    })
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
            axios.get(apiURL,{
                params: {id: this.selected_term},
                withCredentials: true
            })
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
<style>
    td {
        font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
        font-size: 15px;
    }
    .search-bar{
        border-left: none;
    }
    .input-group-text {
        border-right: none;
        background-color: #ffffff;
    }

    .icon-bar{
        display: inline-block;
        width: 100%;
    }

    .search-bar:focus{
        outline: 30px;
    }

</style>