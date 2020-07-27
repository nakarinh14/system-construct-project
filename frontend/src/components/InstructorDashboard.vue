<template>
    <div>
        <b-container fluid>
            <b-table striped bordered hover
                     :head-variant="'dark'"
                     :items="data"
                     :fields="course_fields"
                     :filter="searchFilter"
                     :current-page="currentPage"
                     :per-page="perPage"
                     @filtered="onFiltered"
            >
                <template v-slot:cell(infos)="row">
                    <b-modal
                            :id="row.item.id+'-modal'"
                            @ok="handleOk(row.item.id, row.item.courseId)"
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
                                rows="12"
                                max-rows="12"
                        ></b-form-textarea>
                        <template v-slot:modal-ok>
                            Update
                        </template>
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
                            variant="primary"
                            @click="showStudents(row.item.id)"><BIconPersonFill />
                    </b-btn>
                    <b-modal
                            :id="'show-modal-'+row.item.id"
                            size="lg"
                            ok-only
                            scrollable
                    >
                        <template v-slot:modal-title>
                            Enrolled Students for {{row.item.courseName}} (Section {{row.item.section}})
                        </template>
                        <!-- Table in show students modal  -->
                        <b-table bordered :head-variant="'dark'" :items="studentsGet" :fields="students_fields"  sticky-header="400px">
                            <template v-slot:cell(name)="inner">
                                {{inner.item.profile.title}} {{inner.item.profile.firstname}} {{inner.item.profile.lastname}}
                            </template>
                        </b-table>
                        <template v-slot:modal-ok>
                            Done
                        </template>
                    </b-modal>
                </template>
                <template v-slot:cell(seatAvailable)="row">
                    {{row.item.capacity - row.item.registered}}
                </template>
            </b-table>
        </b-container>
    </div>
</template>
<script>
    import axios from "axios";

    export default {
        name: 'InstructorDashboard',
        props: ['data', 'searchFilter', 'currentPage', 'perPage'],
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
                info: "",
                studentsGet: [],
            }
        },
        methods:{
            handleOk: function(id, courseId){
                // Access current value from textarea form.
                const updatedText = this.$refs[id+'-textarea'].$refs.input._value
                // Update info from edited textarea
                this.postInfo(id, updatedText, courseId)
            },
            postInfo: function(id, text, courseId){
                // Edit info of course teach by instructor.
                const apiURL = "http://52.221.228.219/api/dashboard/update"
                axios.post(apiURL, {
                    "info": text,
                    "courseId": parseInt(id)
                },{withCredentials: true})
                    .then(response => {
                        if(response.data.status === "success"){
                            this.$emit('fetchData')
                            console.log("post update success")
                            this.makeToast(courseId, true);
                        } else{
                            console.log("post update not success")
                        }
                    })
                    .catch(()=> {
                        console.log("Dashboard post REST call failed.")
                        this.makeToast(courseId, false);
                    })
            },
            showStudents: function(id){ // For viewing students of a particular course when being viewed.
                const getUrl = "http://52.221.228.219/api/dashboard/enrolled/"+id
                axios.get(getUrl,{withCredentials: true})
                    .then(response => {
                        this.studentsGet = response.data
                    })
                    .catch(()=> {
                        console.log("Dashboard post REST call failed.")
                    })

            },
            makeToast: function(courseId, success){ // Creating small popup window on top right
                let title = 'Course Info Updated'
                let msg = `Course info for ${courseId} is successfully updated.`;
                let variant = 'success'

                if(!success){
                    title = 'Server Request Failed'
                    msg = `Course info for ${courseId} failed to update.`;
                    variant = 'danger'
                }
                this.$bvToast.toast(msg, {
                    title: title,
                    variant: variant,
                    autoHideDelay: 3800,
                    appendToast: true
                })
            },
            onFiltered: function(filteredItems) { // Trigger pagination to update the number of buttons/pages due to filtering
                // Emit event back to DashboardComponent to update pagination with filtered length
                this.$emit('filterUpdated', filteredItems.length)
            }
        },
    };
</script>



