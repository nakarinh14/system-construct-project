<template>
    <div>
        <h2>Courses Settings</h2>
        <div style="margin-bottom: 30px">
            <a href="#" @click.prevent="showModalTerm" style="padding-right: 15px">
                <BIconPlusSquareFill></BIconPlusSquareFill> Add new term
            </a>
            <a href="#" @click.prevent="showModalCourse" style="padding-left: 15px">
                <BIconPlusSquareFill></BIconPlusSquareFill> Add new course
            </a>
        </div>
        <b-modal
                ref="add-course-modal"
                @ok="sendCourseRequest"
        >
            <template v-slot:modal-title>
                Add new course
            </template>
            <b-container>
                <b-form>
                    <b-form-group
                            id="input-group-id"
                            label="Course Id:"
                            label-for="input-id"
                    >
                        <b-form-input
                                id="input-id"
                                v-model="courseForm.id"
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
                                v-model="courseForm.name"
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
                                v-model="courseForm.division"
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
                                v-model="courseForm.section"
                                type="text"
                                required
                                placeholder="Enter Course Section"
                        ></b-form-input>
                    </b-form-group>
                    <b-form-group id="input-group-instructor" label="Instructor:" label-for="input-instructor">
                        <b-form-select
                                v-model="courseForm.instructor"
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
                                v-model="courseForm.capacity"
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
                                v-model="courseForm.registered"
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
                                v-model="courseForm.date"
                                type="text"
                                required
                                placeholder="Enter Date"
                        ></b-form-input>
                    </b-form-group>
                </b-form>
            </b-container>
        </b-modal>
        <b-modal
                ref="add-term-modal"
        >
            <template v-slot:modal-title>
                Add new term
            </template>
            <b-form>
                <b-form-group
                        id="input-group-1"
                        label="Term period:"
                        label-for="input-term"
                >
                    <b-form-input
                            id="input-term"
                            v-model="termForm.term"
                            type="text"
                            required
                            placeholder="Enter term period  (e.g. 'Summer', 'Term I', 'Term II', etc.)"
                    ></b-form-input>
                </b-form-group>
                <b-form-group
                        id="input-group-2"
                        label="Year period:"
                        label-for="input-year"
                >
                    <b-form-input
                            id="input-year"
                            v-model="termForm.year"
                            type="text"
                            required
                            placeholder="Enter year period  (e.g. '2020-2021', '2019-2020', etc.)"
                    ></b-form-input>
                </b-form-group>
            </b-form>
        </b-modal>
        <DashboardComponent>

        </DashboardComponent>
    </div>
</template>


<script>
    import DashboardComponent from "../components/DashboardComponent";
    import axios from "axios";

    export default {
        name: 'EditCourseVue',
        data(){
            return {
                termForm:{
                    term: "",
                    year: ""
                },
                courseForm:{
                    id: '',
                    name: '',
                    division: '',
                    section: '',
                    capacity: '',
                    registered: '',
                    date: '',
                    instructorId: '',
                    termId: ''
                },
                allInstructor: []
            }
        },

        components:{
            DashboardComponent,
        },
        methods:{
            showModal(){
                this.$refs['add-course-modal'].show()
            },
            showModalCourse(){
                this.getInstructor()
                this.$refs['add-term-modal'].show()
            },
            getInstructor(){
                const apiURL = "http://localhost:8081/api/admin/users/instructor";
                axios.get(apiURL, {withCredentials: true})
                    .then(response => {
                        if(response.data) {
                            this.allInstructor = response.data
                        }
                    })
                    .catch(()=>
                       console.log("error getting instructor")
                    )
            },
            sendCourseRequest(){
                const apiURL = "http://localhost:8081/api/admin/courses/add/";
                this.trimForm();
                axios.post(apiURL, this.courseForm, {withCredentials: true})
                    .then(response => {
                        if(response.data.status) {
                            this.makeToast(
                                'success',
                                'Add user success',
                                `New user, ${this.form.username} is successfully added to the database.`
                            )
                        } else{
                            this.makeToast(
                                'danger',
                                'Add user failed',
                                'The server isn\'t able to add user. Please use a different username'
                            )
                        }
                    })
                    .catch(()=>
                        this.makeToast(
                            'warning',
                            'Server request failed',
                            'Server may be temporally unavailable. Please try again later.'
                        )
                    )
            },
            trimForm(){
                for(const key of Object.keys(this.form)){
                    this.form[key] = this.form[key].trim()
                }
            },
            makeToast: function(title, msg, variant){ // Creating small popup window on top right
                this.$bvToast.toast(msg, {
                    title: title,
                    variant: variant,
                    autoHideDelay: 1000,
                    appendToast: false
                })
            },
        }
    };
</script>
<style scoped>
    h2{
        margin-bottom:30px
    }
</style>