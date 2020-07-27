<template>
    <b-container fluid>
        <b-table striped bordered
                 :head-variant="'dark'"
                 :items="data"
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
                <b :class="computeRoleColor(row.item.role.role)">{{ row.item.role.role}}</b>
            </template>
            <template v-slot:cell(remove)="row">
                <b-btn
                        :disabled="$cookies.get('username') === row.item.username"
                        size="sm"
                        variant="warning"
                        @click="removeUserRequest(row.item.username)"

                >
                    <BIconTrashFill></BIconTrashFill>
                </b-btn>
            </template >
            <template  v-slot:cell(courses)="row">
                <b-btn :disabled="row.item.role.role.toLowerCase() === 'admin'"
                       size="sm"
                       class="mr-2"
                       v-b-modal="'show-modal-'+row.item.id"
                       :id="'btn-'+row.item.id"
                       variant="primary"
                       @click="view_id = row.item.id">
                    <BIconSearch></BIconSearch>
                </b-btn>
                <b-modal
                        :id="'show-modal-'+row.item.id"
                        size="lg"
                        ok-only
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
        name: "AdminUserTable",
        props: ['data', 'busy', 'searchFilter', 'currentPage', 'perPage'],
        data(){
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
                view_fields: [
                    {key: "courseId", sortable:true},
                    {key:"courseName", sortable: true},
                    {key:"section", sortable:true},
                    {key:"termID", sortable: true},
                    {key:"termName", sortable: true}
                ],
                view_id: null,
                view_courses: [],
            }
        },
        methods:{
            onFiltered: function(filteredItems) {
                // Trigger pagination to update the number of buttons/pages due to filtering
                // Emit event back to DashboardComponent to update pagination with filtered length
                this.$emit('filterUpdated', filteredItems.length)
            },
            removeUserRequest(username){
                this.sendRemoveUserRequest(username)
            },
            sendRemoveUserRequest(username){
                const apiURL = "http://13.250.4.112/api/admin/remove/user"
                axios.post(apiURL,{username:username}, {withCredentials: true})
                    .then(response => {
                        if(response.data.status === "success") {
                            this.makeToast(
                                'Remove user success',
                                `${username} is removed from the database.`,
                                'info',
                            )
                        }
                    })
                    .catch(()=> {
                        this.makeToast(
                            'Server request failed',
                            `Something went wrong. Please try again later.`,
                            'warning',
                        )
                    })
                   .finally(() => {this.$emit('fetchData')})
            },
            makeToast: function(title, msg, variant){ // Creating small popup window on top right
                this.$bvToast.toast(msg, {
                    title: title,
                    variant: variant,
                    autoHideDelay: 2000,
                    appendToast: false
                })
            },
            computeRoleColor: function(role){
                switch(role){
                    case("ADMIN"):
                        return "text-warning"
                    case("INSTRUCTOR"):
                        return "text-primary"
                    case("STUDENT"):
                        return "text-info"
                }
            }
        },
        watch: {
            view_id: function(){
                const apiURL = "http://13.250.4.112/api/admin/users/course/" + this.view_id;
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
        },
    }
</script>

