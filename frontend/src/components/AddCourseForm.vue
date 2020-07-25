<template>
    <b-container>
        <b-form>
            <b-form-group
                    id="input-group-1"
                    label="Course Id:"
                    label-for="input-id"
            >
                <b-form-input
                        id="input-id"
                        v-model="form.id"
                        type="text"
                        required
                        placeholder="Enter Course ID"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-name"
                    label="Course Name:"
                    label-for="input-name"
            >
                <b-form-input
                        id="input-name"
                        v-model="form.name"
                        type="text"
                        required
                        placeholder="Enter Course Name"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-division"
                    label="Course Division:"
                    label-for="input-division"
            >
                <b-form-input
                        id="input-division"
                        v-model="form.division"
                        type="text"
                        required
                        placeholder="Enter Course Division"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-section"
                    label="Course Division:"
                    label-for="input-section"
            >
                <b-form-input
                        id="input-section"
                        v-model="form.section"
                        type="text"
                        required
                        placeholder="Enter Course Section"
                ></b-form-input>
            </b-form-group>
            <b-form-group id="input-group-instructor" label="Instructor:" label-for="input-instructor">
                <b-form-select
                        v-model="form.instructor"
                        id="input-instructor"
                        required
                ></b-form-select>
            </b-form-group>
            <b-form-group
                    id="input-group-capacity"
                    label="Course Capacity:"
                    label-for="input-capacity"
            >
                <b-form-input
                        id="input-capacity"
                        v-model="form.capacity"
                        type="text"
                        required
                        placeholder="Enter Capacity"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-registered"
                    label="Course Registered:"
                    label-for="input-registered"
            >
                <b-form-input
                        id="input-registered"
                        v-model="form.registered"
                        type="text"
                        required
                        placeholder="Enter Registered Students"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-date"
                    label="Course Date:"
                    label-for="input-date"
            >
                <b-form-input
                        id="input-date"
                        v-model="form.date"
                        type="text"
                        required
                        placeholder="Enter Date"
                ></b-form-input>
            </b-form-group>
        </b-form>
    </b-container>
</template>

<script>

    import axios from "axios";

    export default {
        name: "AddCourseForm",
        data(){
            return{
                form:{
                    id: '',
                    name: '',
                    division: '',
                    section: '',
                    capacity: '',
                    registered: '',
                    date: '',
                    instructorId: '',
                    termId: ''
                }
            }
        },
        methods:{
            sendRequest(){
                const apiURL = "http://localhost:8081/api/admin/courses/add/";
                this.trimForm();
                axios.post(apiURL, this.form, {withCredentials: true})
                    .then(response => {
                        if(response.data.status) {
                            this.$emit('requestSent', {
                                variant:'success',
                                title: 'Add user success',
                                msg:`New user, ${this.form.username} is successfully added to the database.`})
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
            },
            trimForm(){
                for(const key of Object.keys(this.form)){
                    this.form[key] = this.form[key].trim()
                }

            }
        }
    }
</script>

<style scoped>

</style>