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
        <ValidationObserver ref="observer" v-slot="{ passes }">
            <b-modal
                    ref="add-course-modal"
                    @ok.prevent="passes(handleCourseFormOk)"
                    @cancel="clearForm(courseForm)"
            >
                <template v-slot:modal-title>
                    Add new course
                </template>
                <b-container>
                    <b-form>
                        <ValidationProvider rules="alpha_num|required" name="ID" v-slot="{ valid, errors }">
                            <b-form-group
                                    id="input-group-id"
                                    label="Course Id:"
                                    label-for="input-id"
                            >
                                <b-form-input
                                        id="input-id"
                                        v-model="courseForm.courseId"
                                        type="text"
                                        :state="errors[0] ? false : null"
                                        placeholder="Enter Course ID"
                                ></b-form-input>
                                <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                            </b-form-group>
                        </ValidationProvider>
                        <ValidationProvider rules="required" name="Name" v-slot="{ valid, errors }">
                                <b-form-group
                                        id="input-group-name"
                                        label="Course Name:"
                                        label-for="input-name"
                                >
                                    <b-form-input
                                            id="input-name"
                                            v-model="courseForm.courseName"
                                            type="text"
                                            :state="errors[0] ? false : null"
                                            placeholder="Enter Course Name"
                                    ></b-form-input>
                                    <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                                </b-form-group>
                        </ValidationProvider>
                        <ValidationProvider rules="alpha|required" name="Division" v-slot="{ valid, errors }">
                            <b-form-group
                                    id="input-group-division"
                                    label="Course Division:"
                                    label-for="input-division"
                            >
                                <b-form-input
                                        id="input-division"
                                        v-model="courseForm.division"
                                        type="text"
                                        :state="errors[0] ? false : null"
                                        placeholder="Enter Course Division"
                                ></b-form-input>
                                <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                            </b-form-group>
                        </ValidationProvider>
                        <ValidationProvider rules="numeric|required" name="Section" v-slot="{ valid, errors }">
                            <b-form-group
                                    id="input-group-section"
                                    label="Course Section:"
                                    label-for="input-section"
                            >
                                <b-form-input
                                        id="input-section"
                                        v-model="courseForm.section"
                                        type="text"
                                        :state="errors[0] ? false : null"
                                        placeholder="Enter Course Section"
                                ></b-form-input>
                                <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                            </b-form-group>
                        </ValidationProvider>
                            <b-form-group id="input-group-instructor" label="Instructor:" label-for="input-instructor">
                                <b-form-select
                                        v-model="courseForm.instructorId"
                                        :options="allInstructor"
                                        id="input-instructor"
                                        value-field="id"
                                        text-field="username"
                                ></b-form-select>
                            </b-form-group>
                        <ValidationProvider rules="required" name="Term" v-slot="{ valid, errors }">
                            <b-form-group id="input-group-termcourse" label="Term:" label-for="input-term-c">
                                <b-form-select
                                        v-model="courseForm.termId"
                                        id="input-term-c"
                                        value-field="id"
                                        text-field="name"
                                        :options="termOptions"
                                        :state="errors[0] ? false : null"
                                ></b-form-select>
                                <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                            </b-form-group>
                        </ValidationProvider>
                        <ValidationProvider rules="numeric|required" name="Capacity" v-slot="{ valid, errors }">
                            <b-form-group
                                    id="input-group-capacity"
                                    label="Course Capacity:"
                                    label-for="input-capacity"
                            >
                                <b-form-input
                                        id="input-capacity"
                                        v-model="courseForm.capacity"
                                        type="text"
                                        :state="errors[0] ? false : null"
                                        placeholder="Enter Capacity"
                                ></b-form-input>
                                <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                            </b-form-group>
                        </ValidationProvider>
                        <ValidationProvider rules="numeric|required" name="Registered" v-slot="{ valid, errors }">
                            <b-form-group
                                    id="input-group-registered"
                                    label="Course Registered:"
                                    label-for="input-registered"
                            >
                                <b-form-input
                                        id="input-registered"
                                        v-model="courseForm.registered"
                                        type="text"
                                        :state="errors[0] ? false : null"
                                        placeholder="Enter Registered Students"
                                ></b-form-input>
                                <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                            </b-form-group>
                        </ValidationProvider>
                        <ValidationProvider rules="required" name="Date" v-slot="{ valid, errors }">
                            <b-form-group
                                    id="input-group-date"
                                    label="Course Date:"
                                    label-for="input-date"
                            >
                                <b-form-input
                                        id="input-date"
                                        v-model="courseForm.date"
                                        type="text"
                                        :state="errors[0] ? false : null"
                                        placeholder="Enter Date"
                                ></b-form-input>
                                <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                            </b-form-group>
                        </ValidationProvider>
                    </b-form>
                </b-container>
            </b-modal>
        </ValidationObserver>
        <!--   Adding Term Form     -->
        <ValidationObserver ref="observer" v-slot="{ passes }">
            <b-modal
                    ref="add-term-modal"
                    @ok.prevent="passes(handleTermFormOk)"
                    @cancel="clearForm(termForm)"
            >
                <template v-slot:modal-title>
                    Add new term
                </template>
                <b-form>
                    <ValidationProvider rules="required" name="Term" v-slot="{ valid, errors }">
                            <b-form-group
                                    id="input-group-term"
                                    label="Term period:"
                                    label-for="input-term"
                            >
                                <b-form-input
                                        id="input-term"
                                        v-model="termForm.semester"
                                        type="text"
                                        :state="errors[0] ? false : null"
                                        placeholder="Enter term period  (e.g. 'Summer', 'Term I', 'Term II', etc.)"
                                ></b-form-input>
                                <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                            </b-form-group>
                    </ValidationProvider>
                    <ValidationProvider rules="required" name="Year" v-slot="{ valid, errors }">
                        <b-form-group
                                id="input-group-year"
                                label="Year period:"
                                label-for="input-year"
                        >
                            <b-form-input
                                    id="input-year"
                                    v-model="termForm.year"
                                    type="text"
                                    :state="errors[0] ? false : null"
                                    placeholder="Enter year period  (e.g. '2020-2021', '2019-2020', etc.)"
                            ></b-form-input>
                            <b-form-invalid-feedback>{{ errors[0] }}</b-form-invalid-feedback>
                        </b-form-group>
                    </ValidationProvider>
                </b-form>
                <template v-slot:modal-ok>
                    Submit
                </template>
            </b-modal>
        </ValidationObserver>
        <DashboardComponent>

        </DashboardComponent>
    </div>
</template>


<script>
    import DashboardComponent from "../components/DashboardComponent";
    import { ValidationObserver, ValidationProvider } from "vee-validate";
    import axios from "axios";

    export default {
        name: 'EditCourseVue',
        data(){
            return {
                termForm:{
                    term: "",
                    semester: ""
                },
                courseForm:{
                    courseId: '',
                    courseName: '',
                    division: '',
                    section: '',
                    capacity: '',
                    registered: '',
                    date: '',
                    instructorId: '',
                    termId: ''
                },
                allInstructor: [],
                termOptions: []
            }
        },

        components:{
            DashboardComponent,
            ValidationObserver,
            ValidationProvider
        },
        methods:{
            showModalCourse(){
                this.getTerm()
                this.getInstructor()
                this.$refs['add-course-modal'].show()
            },
            showModalTerm(){
                this.$refs['add-term-modal'].show()
            },
            getInstructor(){
                const apiURL = "http://localhost:80/api/admin/users/instructor";
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
            getTerm(){
                const apiURL = "http://localhost:80/api/admin/get/term";
                axios.get(apiURL, {withCredentials: true})
                    .then(response => {
                        if(response.data) {
                            this.termOptions = response.data.map(x => ({id: x.id, name:`${x.semester}, ${x.year}`}))
                        }
                    })
                    .catch(()=>
                        console.log("error getting instructor")
                    )
            },
            sendCourseRequest(){
                const apiURL = "http://localhost:80/api/admin/add/course";
                axios.post(apiURL, this.courseForm, {withCredentials: true})
                    .then(response => {
                        if(response.data.status) {
                            this.makeToast(
                                'Add user success',
                                `New stuff added`,
                            'success',
                            )
                        } else{
                            this.makeToast(
                                'Add user failed',
                                'The server isn\'t able to add course. Please try again later',
                            'danger',
                            )
                        }
                    })
                    .catch(()=>
                        this.makeToast(
                            'Server request failed',
                            'Server may be temporally unavailable. Please try again later.',
                            'warning',
                        )
                    )
                    .finally(
                        () => {
                            this.clearForm(this.courseForm)
                            this.$emit('fetchData')
                        }
                    )
            },
            sendTermRequest(){
                const apiURL = "http://13.250.4.112/api/admin/add/term";
                axios.post(apiURL, this.termForm, {withCredentials: true})
                    .then(response => {
                        if(response.data.status) {
                            this.makeToast(

                                'Add term success',
                                `Term, ${this.termForm.term} ${this.termForm.year} is successfully added to the database.`,
                                'success',
                            )
                        } else{
                            this.makeToast(

                                'Add term failed',
                                'The server isn\'t able to add term. Please try again later',
                                'danger',
                            )
                        }
                    })
                    .catch(()=>
                        this.makeToast(

                            'Server request failed',
                            'Server may be temporally unavailable. Please try again later.',
                            'warning',
                        )
                    )
                    .finally(() => this.clearForm(this.termForm))

            },
            handleCourseFormOk(){
              this.$refs['add-course-modal'].hide()
              this.sendCourseRequest()
            },
            handleTermFormOk(){
                this.$refs["add-term-modal"].hide()
                this.sendTermRequest()
            },
            clearForm(form) {
                for(let key in form){
                    if(Object.prototype.hasOwnProperty.call(form,key)){
                        form[key] = ""
                    }
                }
                requestAnimationFrame(() => {
                    this.$refs.observer.reset();
                });
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