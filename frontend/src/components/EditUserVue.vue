<template>
    <div>
        <h3>Users Settings</h3>

        <b-btn v-b-modal="'add-user-modal'"><BIconPersonPlusFill></BIconPersonPlusFill> Add User  </b-btn>
        <b-modal
                id="add-user-modal"
                @ok="addUser"
        >
            <template v-slot:modal-title>
               Add new user
            </template>
            <AddUserForm />
        </b-modal>
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
                                placeholder="Search Users"
                                type="search"
                                class="search-bar"
                        >
                        </b-form-input>
                    </b-input-group>
                </b-col>
            </b-row>
        </b-container>
        <b-container fluid>
            <b-table striped bordered
                     :head-variant="'dark'"
                     :items="users"
                     :fields="fields"
                     :filter="searchFilter"
                     @filtered="onFiltered"
                     :current-page="currentPage"
                     :per-page="perPage"
            >
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
                ],
                currentPage: 1,
                perPage: 10,
                rows: 0,
                pageOptions: [10, 25, 40, 100],
                searchFilter: null,
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
            },
            users: function(){
                this.onFiltered(this.users)
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
    }
</script>

<style scoped>

</style>