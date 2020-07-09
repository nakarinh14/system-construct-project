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
                    <b-container>
                        <b-form>
                            <b-form-group
                                    id="input-group-1"
                                    label="Username:"
                                    label-for="input-1"
                            >
                                <b-form-input
                                        id="input-1"
                                        type="text"
                                        required
                                        placeholder="Enter username"
                                ></b-form-input>
                            </b-form-group>
                            <b-form-group
                                    id="input-group-pw"
                                    label="Password:"
                                    label-for="input-pw"
                            >
                                <b-form-input
                                        id="input-pw"
                                        type="password"
                                        required
                                        placeholder="Enter password"
                                ></b-form-input>
                            </b-form-group>
                            <b-form-group
                                    id="input-group-title"
                                    label="Title:"
                                    label-for="input-title"
                            >
                                <b-form-input
                                        id="input-title"
                                        type="text"
                                        required
                                        placeholder="Enter title"
                                ></b-form-input>
                            </b-form-group>
                            <b-form-group id="input-group-fn" label="Firstname:" label-for="input-fn">
                                <b-form-input
                                        id="input-fn"
                                        required
                                        placeholder="Enter firstname"
                                ></b-form-input>
                            </b-form-group>

                            <b-form-group id="input-group-ln" label="Lastname:" label-for="input-ln">
                                <b-form-input
                                        id="input-ln"
                                        required
                                        placeholder="Enter lastname"
                                ></b-form-input>
                            </b-form-group>

                            <b-form-group id="input-group-3" label="Role:" label-for="input-r">
                                <b-form-select
                                        id="input-r"
                                        required
                                ></b-form-select>
                            </b-form-group>

                        </b-form>
                    </b-container>
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
                    <b-btn><BIconTrashFill></BIconTrashFill></b-btn>
                </template>
            </b-table>
        </b-container>


    </div>
</template>

<script>

    import axios from "axios";

    export default {
        name: "AddUserVue",
        data() {
            return {
                fields: [
                    {key:"id", sortable:true},
                    {key:"username", sortable:true},
                    {key: "title", sortable: true},
                    {key:"firstname", sortable: true},
                    {key: "lastname", sortable: true},
                    {key:"role", sortable:true},
                    {key:"remove", label:""},

                ],
                users: []
            }
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
            }
        }
    }
</script>

<style scoped>

</style>