<template>
    <b-container>
        <b-form>
            <b-form-group
                    id="input-group-1"
                    label="Username:"
                    label-for="input-1"
            >
                <b-form-input
                        id="input-1"
                        v-model="form.username"
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
                        v-model="form.password"
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
                        v-model="form.title"
                        type="text"
                        required
                        placeholder="Enter title"
                ></b-form-input>
            </b-form-group>
            <b-form-group id="input-group-fn" label="Firstname:" label-for="input-fn">
                <b-form-input
                        id="input-fn"
                        v-model="form.firstname"
                        required
                        placeholder="Enter firstname"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-ln" label="Lastname:" label-for="input-ln">
                <b-form-input
                        id="input-ln"
                        v-model="form.lastname"
                        required
                        placeholder="Enter lastname"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-3" label="Role:" label-for="input-r">
                <b-form-select
                        id="input-r"
                        v-model="form.role"
                        :options="['Student', 'Instructor', 'Admin']"
                        required
                ></b-form-select>
            </b-form-group>

        </b-form>
    </b-container>
</template>

<script>
    import axios from "axios";

    export default {
        name: "AddUserForm",
        props: ['clickedOk'],
        watch:{
          clickedOk: function(){
              if(this.clickedOk === true){
                  this.sendRequest()
                  this.$emit('requestSent')
              }
          }
        },
        data(){
            return {
                form:{
                    username: '',
                    password: '',
                    title: '',
                    firstname: '',
                    lastname: '',
                    role: ''
                }
            }
        },
        methods:{
            sendRequest(){
                const apiURL = "http://localhost:8081/api/admin/add/user/";
                console.log("request SENT!")
                axios.post(apiURL, this.form, {withCredentials: true})
                    .then(response => {
                        if(response.data.status) {
                            console.log(response.data)
                            this.$emit('requestSent', {
                                variant:'success',
                                title: 'Add user success',
                                msg:`New user, ${this.form.username} (${this.form.firstname} ${this.form.lastname}), is successfully added to the database.`})
                        } else{
                            this.$emit('requestSent', {
                                variant:'danger',
                                title: 'Add user failed',
                                msg:'The server isn\'t able to add user. Please use a different username'})
                        }
                    })
                    .catch(()=> {
                        this.$emit('requestSent', {
                            variant:'warning',
                            title: 'Server request failed',
                            msg:'Server may be temporally unavailable. Please try again later.'}
                            )
                    })
            }
        }
    }
</script>

<style scoped>

</style>