<template>
    <div>
        <h2>Users Settings</h2>
        <b-container v-if="users!=null" style="margin-bottom: 20px">
            <b-row>
                <b-col>
                    <a href="#" @click.prevent="showModal"><BIconPersonPlusFill></BIconPersonPlusFill> Add User  </a>
                </b-col>
            </b-row>
        </b-container>

        <b-modal
                ref="add-user-modal"
                @ok="addUser"

        >
            <template v-slot:modal-title>
               Add new user
            </template>
            <AddUserForm />
        </b-modal>
       <BaseTable
               :data="users"
               tableComponent="AdminUserTable"
       ></BaseTable>
    </div>
</template>

<script>

    import axios from "axios";
    import AddUserForm from '@/components/AddUserForm.vue'
    import BaseTable from "./BaseTable";
    export default {
        name: "AddUserVue",
        data() {
            return {
                users: null,
            }
        },
        components: {
            BaseTable,
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
            showModal() {
                this.$refs['add-user-modal'].show()
            },
        },
    }
</script>

<style scoped>
    h2{
        margin-bottom:20px
    }
</style>