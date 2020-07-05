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
                            type="password"
                            class="w-25"
                            required>

                    </b-form-input>
                </b-row>
                <b-row class="justify-content-md-center label-row">
                    <b-button type="submit" variant="primary" >Submit</b-button>
                </b-row>

            </b-container>
        </b-form>
    </div>

</template>

<script>
    import Vue from "vue";
    import axios from 'axios';

    export default {
        name: 'Login',
        data() {
            return {
                form: {
                    username: '',
                    password: ''
                },
            }
        },
        methods: {
            login: function(e) {
                // post check
                e.preventDefault();
                const postUrl = "http://localhost:8081/api/test/auth"
                axios.post(postUrl, {
                    username: this.form.username,
                    password: this.form.password,
                })
                    .then(response =>{
                        Vue.prototype.$session.set("role", response.data.role);
                        Vue.prototype.$session.set("username", this.form.username);
                        Vue.prototype.$session.set("auth", true);
                        this.$router.push("/");
                    })
                        .catch(() => {
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