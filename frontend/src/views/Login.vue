<template>
    <div id="form-container">
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

        <h1 id="login-title">Login</h1>
        <b-form @submit="login">
            <b-container>
                <b-row class="justify-content-md-center label-row">
                    <label><BIconPersonFill style="margin-right: 5px"></BIconPersonFill>Username:</label>
                </b-row>
                <b-row class="justify-content-md-center ">
                    <b-form-input
                            id="input-1"
                            v-model="loginForm.username"
                            :state="validate.validate"
                            type="text"
                            class="w-25"
                            required
                            placeholder="uxxxxxxx or vxxxxxxx"
                    ></b-form-input>
                </b-row>
                <b-row class="justify-content-md-center label-row">
                    <label><BIconLockFill /> Password:</label>
                </b-row>
                <b-row class="justify-content-md-center">
                    <b-form-input
                            id="input-2"
                            v-model="loginForm.password"
                            :state="validate.validate"
                            type="password"
                            class="w-25"
                            required>
                    </b-form-input>
                    <b-form-invalid-feedback :state="validate.validate">
                        {{validate.message}}
                    </b-form-invalid-feedback>
                </b-row>
                <b-row class="justify-content-md-center">
                    <a href="#" style="margin-top:5px" @click.prevent="showModal">Register new user</a>
                </b-row>
                <b-row class="justify-content-md-center label-row">
                    <b-button type="submit" variant="primary" >Submit</b-button>
                </b-row>

            </b-container>
        </b-form>
    </div>
</template>

<script>
    import axios from 'axios';
    import { ValidationObserver, ValidationProvider } from "vee-validate";
    export default {
        name: 'Login',
        components: {
            ValidationObserver,
            ValidationProvider
        },
        data() {
            return {
                form:{
                    username: '',
                    password: '',
                    title: '',
                    firstname: '',
                    lastname: '',
                    role: null
                },
                loginForm: {
                    validate: null,
                    username: '',
                    password: ''
                },
                validate:{ // For ui error
                    validate: null,
                    message: ''
                },
                roleOptions:[
                    { text: 'Please select a role', value: null, disabled: false },
                    { text: 'Student', value: 'Student', disabled: false },
                    { text: 'Instructor', value: 'Instructor', disabled: false },
                    { text: 'Admin', value: 'Admin', disabled: false },
                ]
            }
        },
        methods: {
            validateTrigger: function(condition, message){
                this.validate.validate = condition;
                this.validate.message = message;
            },
            handleAddUserOk(){
                this.$refs['add-user-modal'].hide()
                this.addUserRequest()
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
                const apiURL = "http://localhost:8081/api/admin/add/user/";
                axios.post(apiURL, this.form, {withCredentials: true})
                    .then(response => {
                        if(response.data.status === "success") {
                            console.log(response.data)
                            this.requestSent({
                                variant:'success',
                                title: 'Add user success',
                                msg:`New user, ${this.form.username} (${this.form.firstname} ${this.form.lastname}), is successfully added to the database.`})
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
            makeToast(res){
                this.$bvToast.toast(res.msg, {
                    title: res.title,
                    variant: res.variant,
                    autoHideDelay: 4400,
                    appendToast: true
                })
            },
            showModal() {
                this.$refs['add-user-modal'].show()
            },
            requestSent(res){
                this.makeToast(res);
                this.clearForm();
            },
            login: function(e) {
                e.preventDefault();
                this.validateTrigger(null, "");
                const postUrl = "http://13.250.4.112/api/auth/login";
                axios.post(postUrl, {
                    username: this.loginForm.username,
                    password: this.loginForm.password,
                }, {withCredentials: true})
                    .then(response =>{
                        if(response.data.status === "success") {
                            this.$cookies.config(60 * 35); // set 35 minute expired token
                            this.$cookies.set('username',response.data.username);
                            this.$cookies.set('firstname',response.data.firstname);
                            this.$cookies.set('lastname',response.data.lastname);
                            this.$cookies.set('role',response.data.role.toLowerCase());
                            this.$router.push("/");
                        } else{
                            this.validateTrigger(false, "Authentication failed. Please check username and password.");
                        }
                    })
                        .catch(() => {
                            this.validateTrigger(false, "Login authentication request to the server failed.");
                            console.log("Login REST called failed");
                        })
            }
        },
        created() {
            this.$cookies.remove('username')
            this.$cookies.remove('firstname')
            this.$cookies.remove('lastname')
            this.$cookies.remove('role')
        }
    }

</script>

<style scoped>
    #form-container{
        margin-top: 100px;
    }
    #login-title{
        margin-bottom: 60px;
    }
    .label-row{
        margin-top: 30px;
        margin-bottom: 10px;
    }
</style>