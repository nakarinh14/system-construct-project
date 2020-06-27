<template>
    <div>
        <b-container>
            <b-form @submit="login">

            <b-form-group
                    id="input-group-1"
                    label="ID:"
                    label-for="input-1"
                    class="form-align-self-center"
            >
                <b-form-input
                        id="input-1"
                        v-model="form.id"
                        type="text"
                        required
                        placeholder="uxxxxxxx or vxxxxxxx"
                ></b-form-input>
            </b-form-group>


                <b-form-group id="input-group-2" label="Password:" label-for="input-2" class="form-align-self-center">
                    <b-form-input
                            id="input-2"
                            v-model="form.password"
                            type="password"
                            required
                    ></b-form-input>
                </b-form-group>



                <b-button type="submit" variant="primary">Submit</b-button>

            </b-form>
        </b-container>
    </div>

</template>

<script>
    export default {
        name: 'Login',
        data() {
            return {
                form: {
                    id: '',
                    password: ''
                },
            }
        },
        methods: {
            login: function() {
                // post check
                this.$session.set("auth", true);
                this.$router.push("/");
                // const postUrl = "http://localhost:8080/api/auth/login"
                // this.axios.post(postUrl, {
                //     username: '',
                //     password: '',
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

<!--<style scoped>-->
<!--.form-align-self-center {-->
<!--    div {-->
<!--        div {-->
<!--            align-self: center !important;-->
<!--        }-->
<!--    }-->
<!--}-->

<!--</style>-->