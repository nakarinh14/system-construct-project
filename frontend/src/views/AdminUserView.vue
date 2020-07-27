<template>
    <div>
        <h2>Users Settings</h2>
        <b-container style="margin-bottom: 20px">
            <b-row>
                <b-col>
                    <a href="#" @click.prevent="showModal"><BIconPersonPlusFill></BIconPersonPlusFill> Add User  </a>
                </b-col>
            </b-row>
        </b-container>
        <ValidationObserver ref="observer" v-slot="{ passes }">
            <b-modal
                    ref="add-user-modal"
                    @ok.prevent="passes(handleAddUserOk)"
                    @cancel="clearForm"
            >
                <template v-slot:modal-title>
                   Add new user
                </template>
                <b-form >
                    <ValidationProvider rules="alpha_num|required" name="Username" v-slot="{ valid, errors }">
                        <b-form-group
                                id="input-group-1"
                                label="Username:"
                                label-for="input-1"
                        >
                            <b-form-input
                                    id="input-1"
                                    v-model="form.username"
                                    type="text"
                                    :state="errors[0] ? false : null"
                                    placeholder="Enter username"
                            ></b-form-input>
                            <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                        </b-form-group>
                    </ValidationProvider>
                    <ValidationProvider rules="alpha_num|required" name="Password" v-slot="{ valid, errors }">
                        <b-form-group
                                id="input-group-pw"
                                label="Password:"
                                label-for="input-pw"
                        >
                            <b-form-input
                                    id="input-pw"
                                    v-model="form.password"
                                    type="password"
                                    :state="errors[0] ? false : null"
                                    placeholder="Enter password"
                            ></b-form-input>
                            <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                        </b-form-group>
                    </ValidationProvider>
                    <ValidationProvider rules="required" name="Title" v-slot="{ valid, errors }">
                        <b-form-group
                                id="input-group-title"
                                label="Title:"
                                label-for="input-title"
                        >
                            <b-form-input
                                    id="input-title"
                                    v-model="form.title"
                                    type="text"
                                    :state="errors[0] ? false : null"
                                    placeholder="Enter title (Mr., Ms., Dr., etc.)"
                            ></b-form-input>
                            <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                        </b-form-group>
                    </ValidationProvider>
                    <ValidationProvider rules="alpha|required" name="Firstname" v-slot="{ valid, errors }">
                        <b-form-group id="input-group-fn" label="Firstname:" label-for="input-fn">
                            <b-form-input
                                    id="input-fn"
                                    v-model="form.firstname"
                                    :state="errors[0] ? false : null"
                                    placeholder="Enter firstname"
                            ></b-form-input>
                            <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                        </b-form-group>
                    </ValidationProvider>
                    <ValidationProvider rules="alpha|required" name="Lastname" v-slot="{ valid, errors }">
                        <b-form-group id="input-group-ln" label="Lastname:" label-for="input-ln">
                            <b-form-input
                                    id="input-ln"
                                    v-model="form.lastname"
                                    :state="errors[0] ? false : null"
                                    placeholder="Enter lastname"
                            ></b-form-input>
                            <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                        </b-form-group>
                    </ValidationProvider>
                    <ValidationProvider rules="required" name="Role" v-slot="{ valid, errors }">
                        <b-form-group id="input-group-3" label="Role:" label-for="input-r">
                            <b-form-select
                                    id="input-r"
                                    v-model="form.role"
                                    :options="roleOptions"
                                    :state="errors[0] ? false : null"
                            ></b-form-select>
                            <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                        </b-form-group>
                    </ValidationProvider>
                </b-form>
                <template v-slot:modal-ok>
                    Submit
                </template>
            </b-modal>
        </ValidationObserver>

       <BaseTable
               :data="users"
               tableComponent="AdminUserTable"
               v-on:fetchData="fetchData"
       ></BaseTable>
    </div>
</template>

<script>

    import axios from "axios";
    import { ValidationObserver, ValidationProvider } from "vee-validate";
    import BaseTable from "../components/BaseTable";
    export default {
        name: "AddUserVue",
        data() {
            return {
                users: null,
                toggle: false,
                form:{
                    username: '',
                    password: '',
                    title: '',
                    firstname: '',
                    lastname: '',
                    role: null
                },
                roleOptions:[
                    { text: 'Please select a role', value: null, disabled: false },
                    { text: 'Student', value: 'Student', disabled: false },
                    { text: 'Instructor', value: 'Instructor', disabled: false },
                    { text: 'Admin', value: 'Admin', disabled: false },
                ]
            }
        },
        components: {
            BaseTable,
            ValidationObserver,
            ValidationProvider
        },
        created() {
            this.fetchData()
        },
        methods:{
            fetchData(){
                const apiURL = "http://52.221.228.219/api/admin/users";
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
            showModal() {
                this.$refs['add-user-modal'].show()
            },
            requestSent(res){
                if(res.variant === "success") {
                    this.fetchData()
                }
                this.makeToast(res);
                this.clearForm();
            },
            makeToast(res){
                this.$bvToast.toast(res.msg, {
                    title: res.title,
                    variant: res.variant,
                    autoHideDelay: 4400,
                    appendToast: true
                })
            },
            clearForm() {
                this.form = {
                    username: '',
                    password: '',
                    title: '',
                    firstname: '',
                    lastname: '',
                    role: null
                }
                requestAnimationFrame(() => {
                    this.$refs.observer.reset();
                });
            },
            addUserRequest(){
                console.log("enter sendRequest")
                const apiURL = "http://52.221.228.219/api/admin/add/user/";
                axios.post(apiURL, this.form, {withCredentials: true})
                    .then(response => {
                        if(response.data.status === "success") {
                            this.fetchData()
                            this.requestSent({
                                variant:'success',
                                title: 'Add user success',
                                msg:`New user, ${this.form.username} (${this.form.firstname} ${this.form.lastname}), is successfully added to the database.`}
                                )
                        } else{
                            this.requestSent({
                                variant:'danger',
                                title: 'Add user failed',
                                msg:'The server isn\'t able to add user. Please use a different username'})
                        }
                    })
                    .catch(() => {
                        this.requestSent({
                            variant:'warning',
                            title: 'Server request failed',
                            msg:'Server may be temporally unavailable. Please try again later.'}
                        )
                    })
                .finally(() => this.clearForm())
            },
            handleAddUserOk(){
                this.$refs['add-user-modal'].hide()
                this.addUserRequest()
            }
        },
    }
</script>

<style scoped>
    h2{
        margin-bottom:20px
    }
</style>