<template>
    <b-container fluid>
        <b-modal
                ref="assign-modal"
                size="xl"
                okOnly
        >
            <template v-slot:modal-header>
                <b>Assigning Students For {{currentViewedCourse.courseCode}}</b>
            </template>
            <b-container>
                <b-row>
                    <b-col>
                        <b-input-group size="sm">
                            <b-input-group-prepend>
                                <span class="input-group-text">
                                    <BIconSearch class="icon-bar"></BIconSearch>
                                </span>
                            </b-input-group-prepend>
                            <b-form-input
                                    v-model="searchFilterE"
                                    placeholder="Search enrolled"
                                    type="search"
                                    class="search-bar"
                            >
                            </b-form-input>
                        </b-input-group>
                        <!--  The assigned students table -->
                        <b-table
                                 :items="studentsGet"
                                 :fields="students_fields_assign"
                                 :filter="searchFilterE"
                                 :busy="assignBusy"
                                 class="assign-table text-center"
                                 sticky-header="400px"
                                 style="min-height: 400px"
                                 small
                        >
                            <template v-slot:thead-top>
                                <b-tr>
                                    <b-th colspan="3" variant="info">
                                        Enrolled Students
                                    </b-th>
                                </b-tr>
                            </template>
                            <template v-slot:cell(fullname)="row">
                                {{`${row.item.profile.firstname} ${row.item.profile.lastname}  (${row.item.username})`}}
                            </template>
                            <template v-slot:cell(remove)="row">
                                <a href="#" @click.prevent="requestWithdraw(currentViewedCourse.courseId, currentViewedCourse.courseCode, row.item.username)">
                                    <BIconPersonDashFill class="icon-tmp"></BIconPersonDashFill>
                                </a>
                            </template>
                            <template v-slot:table-busy>
                                <div class="text-center">
                                    <b-spinner variant="primary" class="align-middle" style="margin-right: 7px"></b-spinner>
                                </div>
                            </template>
                        </b-table>

                    </b-col>
                    <b-col>
                        <b-input-group size="sm">
                            <b-input-group-prepend>
                                <span class="input-group-text">
                                    <BIconSearch class="icon-bar"></BIconSearch>
                                </span>
                            </b-input-group-prepend>
                            <b-form-input
                                    v-model="searchFilterA"
                                    placeholder="Search unassigned"
                                    type="search"
                                    class="search-bar"
                            >
                            </b-form-input>
                        </b-input-group>
                        <!--  The unassigned students table-->
                        <b-table
                                :items="unassigned_students"
                                :fields="students_fields_assign"
                                :filter="searchFilterA"
                                :busy="assignBusy"
                                class="assign-table text-center"
                                sticky-header="400px"
                                style="min-height: 400px"
                                small
                        >
                            <template v-slot:thead-top>
                                <b-tr>
                                    <b-th colspan="3" variant="warning">
                                        Unassigned Students
                                    </b-th>
                                </b-tr>
                            </template>
                            <template v-slot:cell(fullname)="row">
                                {{`${row.item.profile.firstname} ${row.item.profile.lastname}  (${row.item.username})`}}
                            </template>
                            <template v-slot:cell(remove)="row">
                                <a href="#" @click.prevent="requestAssign(currentViewedCourse.courseId, currentViewedCourse.courseCode, row.item.username)">
                                    <BIconPersonPlusFill class="icon-tmp"></BIconPersonPlusFill>
                                </a>
                            </template>
                            <template v-slot:table-busy>
                                <div class="text-center">
                                    <b-spinner variant="warning" class="align-middle" style="margin-right: 7px"></b-spinner>
                                </div>
                            </template>
                        </b-table>
                    </b-col>
                </b-row>
            </b-container>
            <template v-slot:modal-ok>
                Done
            </template>
        </b-modal>
        <b-table striped bordered hover :head-variant="'dark'"
                 :items="data"
                 :fields="course_fields"
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
            <template v-slot:cell(students)="row">
                <b-btn
                        size="sm"
                        class="mr-2"
                        v-b-modal="'show-modal-'+row.item.id"
                        :id="'btn-student-'+row.item.id"
                        variant="primary"
                        @click="getStudentFromCourseId(row.item.id)"><BIconPersonFill />
                </b-btn>
                <b-modal
                        :id="'show-modal-'+row.item.id"
                        size="lg"
                        no-stacking
                        ok-only
                        scrollable
                >
                    <template v-slot:modal-header>
                        <b>Enrolled Students in {{row.item.courseId}} (Section {{row.item.section}})</b>
                        <a href="#" @click.prevent="showAssign(row.item.id, row.item.courseId)" style="padding-left: 15px">
                            <BIconPersonCheckFill></BIconPersonCheckFill> Add students to course
                        </a>
                    </template>
                    <!-- Table in show students modal  -->
                    <b-table bordered :head-variant="'dark'" :items="studentsGet" :fields="students_fields" sticky-header="400px">
                        <template v-slot:cell(name)="inner">
                            {{inner.item.profile.title}} {{inner.item.profile.firstname}} {{inner.item.profile.lastname}}
                        </template>

                    </b-table>
                    <template v-slot:modal-ok>
                        Done
                    </template>
                </b-modal>
            </template>
        </b-table>
    </b-container>
</template>

<script>
    import axios from "axios";

    export default {
        name: "AdminCourseTable",
        props: ['data', 'busy', 'searchFilter', 'currentPage', 'perPage'],
        data() {
            return {
                course_fields: [
                    {key:"courseId", sortable:true, label:"ID"},
                    {key:"courseName", sortable:true},
                    {key:"division", sortable:true},
                    {key:"section", sortable:true},
                    {key:"instructorName", sortable:true, label:"Instructor"},
                    {key:"capacity", sortable:true},
                    {key:"registered", sortable:true},
                    {key:"seatAvailable", sortable: true, label:"Available"},
                    {key:"date"},
                    {key:"students",label:"Students"},
                    {key:"infos", label:"Info"}
                ],
                students_fields: [
                    {key:"id", sortable:true, label:"User ID"},
                    {key: 'username', sortable: true},
                    {key:'name', sortable: true},
                ],
                students_fields_assign:[
                    {key:"id", sortable:true, label:"ID"},
                    {key:"fullname", sortable: true},
                    {key:"remove", label:""}
                ],
                info: "",
                studentsGet: [],
                currentViewedCourse: {courseId: "", courseName:""},
                unassigned_students: null,
                searchFilterE: null,
                searchFilterA: null,
                enrolledId: null,
                spamProtected: false,
                assignBusy: false
            }
        },
        methods:{
            onFiltered: function(filteredItems) {
                // Trigger pagination to update the number of buttons/pages due to filtering
                // Emit event back to DashboardComponent to update pagination with filtered length
                this.$emit('filterUpdated', filteredItems.length)
            },
            getStudentFromCourseId: function(id){ // For viewing students of a particular course when being viewed.
                console.log(id)
                const getUrl = "http://localhost:8081/api/dashboard/enrolled/"+id
                axios.get(getUrl,{withCredentials: true})
                    .then(response => {
                        this.studentsGet = response.data
                        this.enrolledId = response.data.map(x => x.id)
                    })
                    .catch(()=> {
                        console.log("Dashboard post REST call failed.")
                    })

            },
            getAllStudents(){
                const apiURL = "http://localhost:8081/api/admin/users";
                axios.get(apiURL, {withCredentials: true})
                    .then(response => {
                        if(response.data){
                            this.unassigned_students = this.filterUser(response.data)
                        }
                    })
                    .catch(()=> {
                        console.log("Dashboard user get call failed.")
                    }).finally(() => this.assignBusy = false)
            },
            showAssign: function(courseId, courseCode){
                this.getAllStudents()
                this.currentViewedCourse = {courseId: courseId, courseCode: courseCode}
                this.$refs['assign-modal'].show()
            },
            filterUser: function(users){
                return users.filter(x =>
                    (x.role.role.toLowerCase() === "student") && !this.enrolledId.includes(x.id)
                )
            },
            requestAssign: function(courseId, courseCode, username){
                this.assignBusy = true
                const apiURL = "http://localhost:8081/api/admin/courses/assign";
                axios.post(apiURL,
                    {
                        addCourseID: courseId,
                        username: username
                    },
                    {withCredentials: true})
                    .then(response => {
                        if(response.data){
                            this.getStudentFromCourseId(courseId)
                            this.getAllStudents()
                            // this.makeToast(
                            //     "Student Assigned Success",
                            //     `${username} is assigned to ${courseCode} successfully`,
                            //     "primary"
                            // )
                        }
                    })
                    .catch(()=> {
                        this.createFailToast()
                        console.log("Dashboard user get call failed.")
                    })



            },
            requestWithdraw: function(courseId, courseCode, username){
                this.assignBusy = true
                const apiURL = "http://localhost:8081/api/admin/users/remove/course";
                axios.post(apiURL,
                    {
                        removeCourseID: courseId,
                        username: username
                    },
                    {withCredentials: true})
                    .then(response => {
                        if(response.data){
                            this.getStudentFromCourseId(courseId)
                            this.getAllStudents()
                            // this.makeToast(
                            //     "Student Removed Success",
                            //     `${username} is unassigned from ${courseCode} successfully`,
                            //     "warning"
                            // )
                        }
                    })
                    .catch(()=> {
                        this.createFailToast()
                        console.log("Dashboard user get call failed.")
                    })

            },
            makeToast: function(title, msg, variant){ // Creating small popup window on top right
                this.$bvToast.toast(msg, {
                    title: title,
                    variant: variant,
                    autoHideDelay: 1000,
                    appendToast: false
                })
            },
            createFailToast: function(){
                this.makeToast(
                    "Server Request Failed",
                    `Server isn't able to make request. Please try again later`,
                    "warning"
                )
            }
        }
    }
</script>

<style scoped>
    /deep/ .assign-table{
        font-size: 14px;
    }
    /deep/ .assign-table td{
        font-size: 15px;
    }
    /deep/ .icon-tmp{
        font-size:19px;
    }

</style>