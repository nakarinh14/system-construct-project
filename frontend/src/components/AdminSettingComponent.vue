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
                            header="Edit Courses"
                            header-text-variant="white"
                            header-tag="header"
                            header-bg-variant="dark"
                            title="View and modifies courses."
                            @click="switch_component('EditCourseVue')"


                    >
                    </b-card>
                </b-col>
                <b-col>
                    <b-card
                            class="shadow rounded"
                            style="cursor: pointer;"
                            header="Edit Users"
                            header-text-variant="white"
                            header-tag="header"
                            header-bg-variant="dark"
                            title="View and modifies user settings."
                            @click="switch_component('EditUserVue')"

                    >
                    </b-card>
                </b-col>
            </b-row>
        </b-container>
        <component :is="setting_component"></component>
    </div>
</template>

<script>

    import EditUserVue from '@/components/EditUserVue.vue'
    import EditCourseVue from '@/components/EditCourseVue.vue';

    export default {
        name: "Setting",
        components:{
            EditUserVue,
            EditCourseVue
        },
        data(){
            return{
                setting_component: null
            }
        },
        methods:{
            switch_component: function(name){
                this.setting_component=name
            },
            backMain: function(){
                this.setting_component = null;
            }
        }
    }


</script>

<style scoped>

</style>