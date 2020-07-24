<template>
    <div>
        <h2>Courses Settings</h2>
        <div style="margin-bottom: 30px">
            <a href="#" @click.prevent="showModalTerm" style="padding-right: 15px">
                <BIconPlusSquareFill></BIconPlusSquareFill> Add new term
            </a>
            <a href="#" @click.prevent="showModal" style="padding-left: 15px">
                <BIconPlusSquareFill></BIconPlusSquareFill> Add new course
            </a>
        </div>
        <b-modal
                ref="add-course-modal"
        >
            <template v-slot:modal-title>
                Add new course
            </template>
            <AddCourseForm />
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
    import AddCourseForm from '@/components/AddCourseForm.vue'
    import DashboardComponent from "../components/DashboardComponent";

    export default {
        name: 'EditCourseVue',
        data(){
            return {
                termForm:{
                    term: "",
                    year: ""
                }
            }
        },

        components:{
            DashboardComponent,
            AddCourseForm
        },
        methods:{
            showModal(){
                this.$refs['add-course-modal'].show()
            },
            showModalTerm(){
                this.$refs['add-term-modal'].show()
            }
        }
    };
</script>
<style scoped>
    h2{
        margin-bottom:30px
    }
</style>