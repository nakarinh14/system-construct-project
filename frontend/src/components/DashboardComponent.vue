<template>
    <div class="home">
        <b>{{this.$cookies.get("firstname")}} {{this.$cookies.get("lastname")}}'s Dashboard</b>
        <!-- Select term -->
        <h3 v-if="requested_term.semester !== ''">{{requested_term.semester}}, {{requested_term.year}}</h3>
        <b-container fluid>
            <b-row align-h="start">
                <b-col sm="2">
                    <b-form-select v-model="selected_term" :options="options" size="sm" class="mt-3"></b-form-select>
                </b-col>
            </b-row>
        </b-container>

        <!-- Table component-->
        <b-container style="margin-bottom: 20px" fluid>
            <b-row cols="3">
                <b-col  class="d-flex justify-content-start">
                    <div>
                        <b>Show</b>
                        <b-form-select
                                v-model="perPage"
                                id="perPageSelect"
                                size="sm"
                                :options="pageOptions"
                                style="width: 65px"
                        ></b-form-select>
                        <b>entries</b>

                    </div>
                </b-col>
                <b-col>
                    <b-input-group size="sm">
                        <b-input-group-prepend>
                                <span class="input-group-text">
                                    <BIconSearch class="icon-bar"></BIconSearch>
                                </span>
                        </b-input-group-prepend>
                        <b-form-input
                                v-model="searchFilter"
                                placeholder="Search Courses"
                                type="search"
                                class="search-bar"
                        >
                        </b-form-input>
                    </b-input-group>
                </b-col>
            </b-row>
        </b-container>

        <component
                :is="component_name"
                :course="course"
                :busy="busy"
                :searchFilter="searchFilter"
                :currentPage="currentPage"
                :perPage="perPage"
                v-on:filterUpdated="updatePagination"
                v-on:fetchData="fetchData"
        ></component>

        <b-container style="margin-top: 15px" fluid>
            <b-row>
                <b-col></b-col>
                <b-col>
                    <span>{{currentPageCount}}</span>
                </b-col>
                <b-col>
                    <b-pagination
                            v-model="currentPage"
                            :per-page="perPage"
                            :total-rows="rows"
                            aria-controls="my-table"
                            size="sm"
                            class="justify-content-end"
                            prev-text="Prev"
                            next-text="Next"
                    ></b-pagination>
                </b-col>
            </b-row>
        </b-container>

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
                searchFilter: null,
                currentPage: 1,
                perPage: 10,
                rows: 0,
                pageOptions: [10, 25, 40, 100]
            }
        },
        computed: {
            currentPageCount: function(){
                let minBound = 1 + ((this.currentPage-1)*this.perPage)
                let maxBound = Math.min(this.currentPage * this.perPage, this.rows);
                if(this.rows === 0){
                    minBound = 0;
                    maxBound = 0;
                }
                console.log(minBound)
                return `Showing ${minBound} to ${maxBound} entries out of ${this.rows}`
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
            },
            course: function(){
                this.updatePagination(this.course.length)
            }
        },
        methods:{
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
            },
            updatePagination: function(filteredLength){
                this.rows = filteredLength
                this.currentPage = 1
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