<template>
    <div>
        <h2>Users Settings</h2>

        <b-btn v-b-modal="'add-user-modal'"><BIconPersonPlusFill></BIconPersonPlusFill> Add new user</b-btn>
        <b-modal
                id="add-user-modal"
                @ok="addUser"
        >
            <template v-slot:modal-title>
               Add new user
            </template>
            <AddUserForm />
        </b-modal>
        <b-container fluid>
            <b-table striped bordered :head-variant="'dark'" :items="users" :fields="fields">
                <template v-slot:cell(firstname)="row">
                    {{ row.item.profile.firstname}}
                </template>
                <template v-slot:cell(lastname)="row">
                    {{ row.item.profile.lastname}}
                </template>
                <template v-slot:cell(title)="row">
                    {{row.item.profile.title}}
                </template>
                <template v-slot:cell(role)="row">
                    {{ row.item.role.role}}
                </template>
                <template v-slot:cell(remove)>
                    <b-btn size="sm"><BIconTrashFill></BIconTrashFill></b-btn>
                </template >
                <template  v-slot:cell(courses)="row">
                    <b-btn v-if="row.item.role.role.toLowerCase() !== 'admin'"
                            size="sm"
                            class="mr-2"
                            v-b-modal="'show-modal-'+row.item.id"
                            :id="'btn-'+row.item.id"
                            @click="view_id = row.item.id">
                        <BIconSearch></BIconSearch>
                    </b-btn>
                    <b-btn v-else
                           disabled
                           size="sm"
                           class="mr-2"
                    >
                        <BIconSearch></BIconSearch>
                    </b-btn>
                    <b-modal
                            :id="'show-modal-'+row.item.id"
                            size="lg"
                    >
                        <template v-slot:modal-title>
                            <p v-if="row.item.role.role.toLowerCase() === 'student'" >
                                Enrolled Courses for {{`${row.item.profile.title} ${row.item.profile.firstname} ${row.item.profile.lastname}`}}
                            </p>
                            <p v-else>
                                Instructed Courses for {{`${row.item.profile.title} ${row.item.profile.firstname} ${row.item.profile.lastname}`}}
                            </p>
                        </template>
                        <!-- Table in show students modal  -->
                        <b-table bordered :head-variant="'dark'" :items="view_courses" :fields="view_fields">
                            <template v-slot:cell(termID)="inner">
                                {{inner.item.term.id}}
                            </template>
                            <template v-slot:cell(termName)="inner">
                                {{inner.item.term.semester}}, {{inner.item.term.year}}
                            </template>
                        </b-table>
                    </b-modal>
                </template>
            </b-table>
        </b-container>
    </div>
</template>

<script>

    import axios from "axios";
    import AddUserForm from '@/components/AddUserForm.vue'
    export default {
        name: "AddUserVue",
        data() {
            return {
                fields: [
                    {key:"id", sortable:true, label:"ID"},
                    {key:"username", sortable:true},
                    {key: "title", sortable: true},
                    {key:"firstname", sortable: true},
                    {key: "lastname", sortable: true},
                    {key: "courses"},
                    {key:"role", sortable:true},
                    {key:"remove", label:""},

                ],
                users: [],
                view_id: null,
                view_courses: [],
                view_fields: [
                    {key: "courseId", sortable:true},
                    {key:"courseName", sortable: true},
                    {key:"section", sortable:true},
                    {key:"termID", sortable: true},
                    {key:"termName", sortable: true}
                ]
            }
        },
        watch: {
            view_id: function(){
                const apiURL = "http://localhost:8081/api/admin/users/course/" + this.view_id;
                axios.get(apiURL,{withCredentials: true})
                    .then(response => {
                        if(response.data) {
                            this.view_courses = response.data
                        }
                    })
                    .catch(()=> {
                        console.log("Get All user REST call failed.")
                    })
            }
        },
        components: {
          AddUserForm
        },
        created() {
            const apiURL = "http://localhost:8081/api/admin/users";
            axios.get(apiURL, {withCredentials: true})
                .then(response => {
                    if(response.data){
                        this.users = response.data
                    }
                })
                .catch(()=> {
                    console.log("Dashboard user get call failed.")
                })
        },
        methods:{
            addUser: function(){
                console.log("adding user")
            },
            viewCourse: function(id){
                console.log(id)
            }
        }
    }
</script>

<style scoped>

</style>