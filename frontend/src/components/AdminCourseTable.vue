<template>
    <b-container fluid>
        <b-modal
                ref="assign-modal"
                size="xl"
        >
            <template v-slot:modal-header>
                <b>Assigning Students For ICCS101</b>
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
                        <b-table
                                 :items="studentsGet"
                                 :fields="students_fields_assign"
                                 :filter="searchFilterE"
                                 class="assign-table text-center"
                                 sticky-header="400px"
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
                            <template v-slot:cell(remove)>
                                <a href="#"><BIconPersonDashFill class="icon-tmp"></BIconPersonDashFill></a>
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
                        <b-table
                                :items="unassigned_students"
                                :fields="students_fields_assign"
                                :filter="searchFilterA"
                                class="assign-table text-center"
                                sticky-header="400px"
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
                            <template v-slot:cell(remove)>
                                <BIconPersonPlusFill class="icon-tmp"></BIconPersonPlusFill>
                            </template>
                        </b-table>
                    </b-col>
                </b-row>
            </b-container>
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
                        @click="showStudents(row.item.id)"><BIconPersonFill />
                </b-btn>
                <b-modal
                        :id="'show-modal-'+row.item.id"
                        size="lg"
                        no-stacking
                >
                    <template v-slot:modal-header>
                        Enrolled Students for {{row.item.courseName}} (Section {{row.item.section}})
                        <a href="#" @click.prevent="showAssign" style="padding-left: 15px">
                            <BIconPersonCheckFill></BIconPersonCheckFill> Add students to course
                        </a>
                    </template>
                    <!-- Table in show students modal  -->
                    <b-table bordered :head-variant="'dark'" :items="studentsGet" :fields="students_fields">
                        <template v-slot:cell(name)="inner">
                            {{inner.item.profile.title}} {{inner.item.profile.firstname}} {{inner.item.profile.lastname}}
                        </template>
                    </b-table>
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
                unassigned_students: null,
                searchFilterE: null,
                searchFilterA: null,
            }
        },
        methods:{
            onFiltered: function(filteredItems) {
                // Trigger pagination to update the number of buttons/pages due to filtering
                // Emit event back to DashboardComponent to update pagination with filtered length
                this.$emit('filterUpdated', filteredItems.length)
            },
            showStudents: function(id){ // For viewing students of a particular course when being viewed.
                console.log(id)
                const getUrl = "http://localhost:8081/api/dashboard/enrolled/"+id
                axios.get(getUrl,{withCredentials: true})
                    .then(response => {
                        this.studentsGet = response.data
                    })
                    .catch(()=> {
                        console.log("Dashboard post REST call failed.")
                    })

            },
            getAllStudents(){
                console.log("tessss")
                const apiURL = "http://localhost:8081/api/admin/users";
                axios.get(apiURL, {withCredentials: true})
                    .then(response => {
                        if(response.data){
                            this.unassigned_students = this.filterUser(response.data)
                        }
                    })
                    .catch(()=> {
                        console.log("Dashboard user get call failed.")
                    })
            },
            showAssign: function(){
                this.getAllStudents()
                this.$refs['assign-modal'].show()
            },
            filterUser: function(users){
                let enrolledId = this.studentsGet.map(x => x.id)
                console.log(enrolledId)
                let o = users.filter(x =>
                    (x.role.role.toLowerCase() === "student") && (!(x.id in enrolledId))
                )
                console.log(o)
                return o
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