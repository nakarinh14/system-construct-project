<template>
    <div>
        <h2>Courses Settings</h2>
        <div style="margin-bottom: 30px">
            <a href="#" @click.prevent="showModal" style="padding-right: 15px">
                <BIconPlusSquareFill></BIconPlusSquareFill> Add new course
            </a>
            <a href="#" @click.prevent="showModal" style="padding-left: 15px">
                <BIconPersonCheckFill></BIconPersonCheckFill> Assign course to student
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
        <DashboardComponent>

        </DashboardComponent>
    </div>
</template>


<script>
    import AddCourseForm from '@/components/AddCourseForm.vue'
    import DashboardComponent from "./DashboardComponent";

    export default {
        name: 'EditCourseVue',

        components:{
            DashboardComponent,
            AddCourseForm
        },
        methods:{
            showModal(){
                this.$refs['add-course-modal'].show()
            }
        }
    };
</script>
<style scoped>
    h2{
        margin-bottom:30px
    }
</style>