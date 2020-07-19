<template>
    <div class="home" v-if="course !== null">
        <b v-if="component_name !== 'AdminCourseTable'">{{this.$cookies.get("firstname")}} {{this.$cookies.get("lastname")}}'s Dashboard</b>
        <!-- Select term -->
        <h3 v-if="requested_term.semester !== ''">{{requested_term.semester}}, {{requested_term.year}}</h3>
        <!--  Selected Term Options -->
        <b-container fluid>
            <b-row align-h="start">
                <b-col sm="2">
                    <b-form-select v-model="selected_term"
                                   :options="options"
                                   size="sm"
                                   class="mt-3">

                    </b-form-select>
                </b-col>
            </b-row>
        </b-container>
        <BaseTable
                :data="course"
                :tableComponent="component_name"
                v-on:fetchData="fetchData"
        ></BaseTable>

    </div>
</template>

<script>
    // @ is an alias to /src

    import BaseTable from '@/components/BaseTable.vue'
    import axios from "axios";

    export default {
        name: 'DashboardComponent',
        components:{
            BaseTable
        },
        data(){
            return{
                component_name: '',
                selected_term: 0,
                course: null,
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
            }
        },
        watch:{
            selected_term: function(){
                this.fetchData()
            },

        },
        methods:{
            getTableComponent: function(role){
                switch(role){
                    case "student":
                        this.component_name = "StudentDashboard"
                        break;
                    case "instructor":
                        this.component_name = "InstructorDashboard"
                        break;
                    case "admin":
                        this.component_name = "AdminCourseTable"
                        break;
                }
            },
            fetchData: function(){
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
                            if(this.component_name === ''){
                                this.getTableComponent(response.data.role)
                                this.selected_term = response.data.requestedTerm.id;
                                this.options = response.data.termOptions.map(x => ({"value":x.id, "text":x.semester+", "+x.year}));
                            }
                        }
                    })
                    .catch(()=> {
                        console.log("Dashboard REST call failed.")
                    })
            }
        },
        created() {
            this.fetchData();
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