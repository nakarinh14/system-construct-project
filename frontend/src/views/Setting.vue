<template>
    <div>
        <h1>Admin Setting</h1>
        <hr style="max-width: 60%; margin-top: 40px; margin-bottom: 60px">
        <b-btn v-if="setting_component !== null" @click="backMain">Go back</b-btn>
        <b-container v-if="setting_component === null">
            <b-row>
                <b-col>
                    <b-card
                            class="shadow rounded"
                            style="cursor: pointer;"
                            header="Add Course"
                            header-text-variant="white"
                            header-tag="header"
                            header-bg-variant="dark"
                            title="Add new courses to the database."


                    >
                    </b-card>
                </b-col>
                <b-col>
                    <b-card
                            class="shadow rounded"
                            style="cursor: pointer;"
                            header="Add User"
                            header-text-variant="white"
                            header-tag="header"
                            header-bg-variant="dark"
                            title="Add new users to the database."
                            @click="test"

                    >
                    </b-card>
                </b-col>
                <b-col>
                    <b-card
                            class="shadow rounded"
                            style="cursor: pointer;"
                            header="Assign Course"
                            header-text-variant="white"
                            header-tag="header"
                            header-bg-variant="dark"
                            title="Assign course to a user."

                    >
                    </b-card>
                </b-col>
            </b-row>
        </b-container>
        <component :is="setting_component"></component>
    </div>
</template>

<script>

    import AddCourseVue from '@/components/AddCourseVue.vue'
    import AddUserVue from '@/components/AddUserVue.vue';
    import AssignCourseVue from '@/components/AssignCourseVue.vue';

    export default {
        name: "Setting",
        components:{
            AddCourseVue,
            AddUserVue,
            AssignCourseVue
        },
        data(){
            return{
                setting_component: null
            }
        },
        methods:{
            test: function(){
                this.setting_component="AddUserVue"
            },
            backMain: function(){
                this.setting_component = null;
            }
        }
    }


</script>

<style scoped>

</style>