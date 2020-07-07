<template>
    <div id="form-container">
        <h1 id="login-title">Login</h1>
        <b-form @submit="login">
            <b-container>
                <b-row class="justify-content-md-center label-row">
                    <label><BIconPersonFill style="margin-right: 5px"></BIconPersonFill>Username:</label>
                </b-row>
                <b-row class="justify-content-md-center ">
                    <b-form-input
                            id="input-1"
                            v-model="form.username"
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
                            v-model="form.password"
                            :state="validate.validate"
                            type="password"
                            class="w-25"
                            required>
                    </b-form-input>
                    <b-form-invalid-feedback :state="validate.validate">
                        {{validate.message}}
                    </b-form-invalid-feedback>
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
    import Vue from 'vue'

    export default {
        name: 'Login',
        data() {
            return {
                form: {
                    validate: null,
                    username: '',
                    password: ''
                },
                validate:{ // For ui error
                    validate: null,
                    message: ''
                }
            }
        },
        methods: {
            validateTrigger: function(condition, message){
                this.validate.validate = condition;
                this.validate.message = message;
            },
            login: function(e) {
                e.preventDefault();
                this.validateTrigger(null, "");
                const postUrl = "http://localhost:8081/api/auth/login";
                axios.post(postUrl, {
                    username: this.form.username,
                    password: this.form.password,
                }, {withCredentials: true})
                    .then(response =>{
                        if(response.data.status === "success") {
                            this.$cookies.set('username',response.data.username);
                            this.$cookies.set('firstname',response.data.firstname);
                            this.$cookies.set('lastname',response.data.lastname);
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
        }
    }


</script>

<style scoped>
    #form-container{
        margin-top: 180px;
    }
    #login-title{
        margin-bottom: 60px;
    }
    .label-row{
        margin-top: 30px;
        margin-bottom: 10px;
    }
</style>