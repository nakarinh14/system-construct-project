<template>
    <div>
        <b-modal
                id="add-course-modal"
        >
            <template v-slot:modal-title>
                Add new course
            </template>
            <AddCourseForm />
        </b-modal>

        <b-container>
            <b-row>
                <b-btn v-b-modal="'add-course-modal'" class="justify-content-start">
                    <BIconPlusSquareFill></BIconPlusSquareFill> Add new course
                </b-btn>
            </b-row>
        </b-container>

        <b-container fluid>
            <b-row align-h="start">
                <b-col sm="2">
                    <b-form-select v-model="selectedTerm" :options="options" size="sm" class="mt-3"></b-form-select>
                </b-col>
            </b-row>

        </b-container>
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
        <b-container fluid>
            <b-table striped bordered hover :head-variant="'dark'"
                     :items="courses"
                     :fields="fields"
                     :filter="searchFilter"
                     @filtered="onFiltered"
                     :current-page="currentPage"
                     :per-page="perPage"
                     class="align-middle"
            >
                <template v-slot:cell(infos)="row">
                    <b-modal
                            :id="row.item.id+'-modal'"
                            size="lg"
                    >
                        <template v-slot:modal-title>
                            {{row.item.courseName}} (Section {{row.item.section}})
                        </template>
                        {{row.item.info}}
                    </b-modal>
                    <b-button v-b-modal="row.item.id+'-modal'" size="sm" class="mr-2">
                        Show Info
                    </b-button>
                </template>

                <template v-slot:cell(seatAvailable)="row">
                    {{row.item.capacity - row.item.registered}}
                </template>
            </b-table>
        </b-container>
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
    import axios from "axios";
    import AddCourseForm from '@/components/AddCourseForm.vue'

    export default {
        name: 'EditCourseVue',
        data() {
            return {
                fields: ["courseId","courseName","division","section",
                    "instructorName","capacity","registered","seatAvailable","date","infos"],
                info: "",
                courses: [],
                options: [],
                requestedTerm: 0,
                selectedTerm: 0,
                searchFilter: null,
                currentPage: 1,
                perPage: 10,
                rows: 0,
                pageOptions: [10, 25, 40, 100]

            }
        },
        components:{
            AddCourseForm
        },
        watch:{
            selectedTerm: function(){
                this.getCourse()
            },
            courses: function(){
                this.onFiltered(this.courses)
            }
        },
        methods:{
            getCourse:function(){
                const apiURL = "http://localhost:8081/api/dashboard"
                axios.get(apiURL,
                    {
                        params: {
                            id: this.selectedTerm,
                        },
                        withCredentials: true
                    })
                    .then(response => {
                        this.requestedTerm = response.data.requestedTerm;
                        this.courses = response.data.courses;
                        this.options = response.data.termOptions.map(x => ({"value":x.id, "text":x.semester+", "+x.year}));
                        this.selectedTerm = response.data.requestedTerm.id;
                    })
                    .catch(()=> {
                        console.log("Dashboard REST call failed.")
                    })
            },
            onFiltered: function(filteredItems) {
                // Trigger pagination to update the number of buttons/pages due to filtering
                this.rows = filteredItems.length
                this.currentPage = 1
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
        created() {
            this.getCourse()
        }
    };
</script>
