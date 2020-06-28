<template>
    <div id="form-container">
        <h1 id="login-title">Login</h1>

        <b-form @submit="login">
            <b-container>

                <b-row class="justify-content-md-center label-row">
                    <label><BIconTextLeft style="margin-right: 5px"></BIconTextLeft>Username:</label>
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
            login: function() {
                // post check
                Vue.prototype.$session.set("auth", true);
                // Vue.prototype.$session.set("user_type", "instructor")
                this.$router.push("/");
                // const postUrl = "http://localhost:8080/api/auth/login"
                // this.axios.post(postUrl, {
                //     username: this.username,
                //     password: this.password,
                // })
                //     .then(response =>{
                //         if(response.status === 200){
                //             this.$session.start();
                //             this.$session.set('jwt', response.body.token);
                //         }
                //     })
            },
            onSubmit: function(evt) {
                evt.preventDefault()
                alert(JSON.stringify(this.form))
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