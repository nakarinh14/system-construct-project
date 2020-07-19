<template>
    <div>
        <h1>Admin Page</h1>
        <hr style="max-width: 60%; margin-top: 40px; margin-bottom: 60px">
        <b-container>
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
                            @click="switchPage('/admin/courses')"
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
                            @click="switchPage('/admin/users')"

                    >
                    </b-card>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    export default {
        name: "Setting",
        data(){
            return{
                setting_component: null
            }
        },
        methods:{
            switchPage: function(name){
                this.$router.push(name)
            }
        }
    }


</script>

<style scoped>

</style>