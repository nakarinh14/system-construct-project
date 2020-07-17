<template>
    <div>
        <b-container style="margin-bottom: 20px">
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
                                v-model="search_filter"
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
            <b-table striped bordered hover
                     :head-variant="'dark'"
                     :items="course"
                     :fields="course_fields"
                     :filter="filter"
                     :current-page="currentPage"
                     :per-page="perPage"
                     @filtered="onFiltered"
            >
                <template v-slot:cell(infos)="row">
                    <b-modal
                            :id="row.item.id+'-modal'"
                            @ok="handleOk(row.item.id)"
                            size="lg"
                    >
                        <template v-slot:modal-title>
                            {{row.item.courseName}} (Section {{row.item.section}})
                        </template>
                        <b-form-textarea
                                :id="row.item.id+'-textarea'"
                                :ref="row.item.id+'-textarea'"
                                :value="row.item.info"
                                placeholder="Enter something..."
                                rows="4"
                                max-rows="6"
                        ></b-form-textarea>
                    </b-modal>
                    <b-button v-b-modal="row.item.id+'-modal'" size="sm" class="mr-2">
                        <BIconPencilSquare />
                    </b-button>
                </template>
                <template v-slot:cell(students)="row">
                    <b-btn
                            size="sm"
                            class="mr-2"
                            v-b-modal="'show-modal-'+row.item.id"
                            :id="'btn-student-'+row.item.id"
                            @click="showStudents(row.item.id)"><BIconPersonFill />
                    </b-btn>
                    <b-modal
                            :id="'show-modal-'+row.item.id"
                            size="lg"
                    >
                        <template v-slot:modal-title>
                            Enrolled Students for {{row.item.courseName}} (Section {{row.item.section}})
                        </template>
                        <!-- Table in show students modal  -->
                        <b-table bordered :head-variant="'dark'" :items="students_get" :fields="students_fields">
                            <template v-slot:cell(name)="inner">
                                {{inner.item.profile.title}} {{inner.item.profile.firstname}} {{inner.item.profile.lastname}}
                            </template>
                        </b-table>
                    </b-modal>
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

    export default {
        name: 'InstructorDashboard',
        props: ['course', 'filter'],
        data() {
            return {
                course_fields: [
                    {key:"courseId", sortable:true, label:"Course ID"},
                    {key:"courseName", sortable:true},
                    {key:"division", sortable:true},
                    {key:"section", sortable:true},
                    {key:"instructorName", sortable:true},
                    {key:"capacity", sortable:true},
                    {key:"registered", sortable:true},
                    {key:"seatAvailable", sortable: true},
                    {key:"date"},
                    {key:"students",label:"Students"},
                    {key:"infos", label:"Info"}
                ],
                info: "",
                students_get: [],
                students_fields: [
                    {key:"id", sortable:true, label:"User ID"},
                    {key: 'username', sortable: true},
                    {key:'name', sortable: true},
                ],
                search_filter: null,
                currentPage: 1,
                perPage: 10,
                rows: 0,
                pageOptions: [10, 25, 40, 100]
            }
        },
        created(){
          this.rows = this.course.length;
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
        methods:{
            handleOk: function(id){
                const updatedText = this.$refs[id+'-textarea'].$refs.input._value // Access current value from form.
                console.log(updatedText)
                this.postInfo(id, updatedText)
            },
            postInfo: function(id, text){
                // Edit info of course teach by instructor.
                const apiURL = "http://localhost:8081/api/dashboard/update"
                axios.post(apiURL, {
                    "info": text,
                    "courseId": parseInt(id)
                },{withCredentials: true})
                    .then(response => {
                        if(response.data.status === "success"){
                            this.$router.go(0)
                            console.log("post update success")
                        } else{
                            console.log("post update not success")
                        }
                    })
                    .catch(()=> {
                        console.log("Dashboard post REST call failed.")
                    })
            },
            showStudents: function(id){
                console.log(id)
                const getUrl = "http://localhost:8081/api/dashboard/enrolled/"+id
                axios.get(getUrl,{withCredentials: true})
                    .then(response => {
                        this.students_get = response.data
                    })
                    .catch(()=> {
                        console.log("Dashboard post REST call failed.")
                    })
            },

            onFiltered: function(filteredItems) {
                // Trigger pagination to update the number of buttons/pages due to filtering
                this.rows = filteredItems.length
                this.currentPage = 1
            }
        }
    };
</script>



