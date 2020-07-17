<template>
    <div>
        <b-modal
                id="add-course-modal"
        >
            <template v-slot:modal-title>
                Add new course
            </template>
            <AddCourseForm />
        </b-modal>

        <b-container>
            <b-row>
                <b-btn v-b-modal="'add-course-modal'" class="justify-content-start">
                    <BIconPlusSquareFill></BIconPlusSquareFill> Add new course
                </b-btn>
            </b-row>
        </b-container>


        <b-container fluid>
            <b-table striped bordered hover :head-variant="'dark'" :items="courses" :fields="fields" class="align-middle">
                <template v-slot:cell(infos)="row">
                    <b-modal
                            :id="row.item.id+'-modal'"
                            size="lg"
                    >
                        <template v-slot:modal-title>
                            {{row.item.courseName}} (Section {{row.item.section}})
                        </template>
                        {{row.item.info}}
                    </b-modal>
                    <b-button v-b-modal="row.item.id+'-modal'" size="sm" class="mr-2">
                        Show Info
                    </b-button>
                </template>

                <template v-slot:cell(seatAvailable)="row">
                    {{row.item.capacity - row.item.registered}}
                </template>
            </b-table>
        </b-container>
    </div>
</template>


<script>
    import axios from "axios";
    import AddCourseForm from '@/components/AddCourseForm.vue'

    export default {
        name: 'EditCourseVue',
        data() {
            return {
                fields: ["courseId","courseName","division","section",
                    "instructorName","capacity","registered","seatAvailable","date","infos"],
                info: "",
                courses: [],
                options: [],
                requestedTerm: 0,
            }
        },
        components:{
            AddCourseForm
        },

        methods:{
            get_course:function(){
                const apiURL = "http://localhost:8081/api/dashboard"
                axios.get(apiURL,
                    {
                        params: {
                            id: this.requestedTerm,
                        },
                        withCredentials: true
                    })
                    .then(response => {
                        this.requested_term = response.data.requestedTerm;
                        this.courses = response.data.courses;
                        this.options = response.data.termOptions.map(x => ({"value":x.id, "text":x.semester+", "+x.year}));
                        this.selected_term = response.data.requestedTerm.id;
                    })
                    .catch(()=> {
                        console.log("Dashboard REST call failed.")
                    })
                // this.busy = false;

            }
        },
        created() {
            this.get_course();
        }
    };
</script>
