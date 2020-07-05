<template>
        <b-container fluid>
            <b-table striped bordered hover :head-variant="'dark'" :items="courses" :fields="fields">
                <template v-slot:cell(infos)="row">
                    <b-button size="sm" @click="row.toggleDetails" class="mr-2">
                        {{ row.detailsShowing ? 'Hide' : 'Show'}} Details <BIconInfoCircle></BIconInfoCircle>
                    </b-button>

                </template>
                <template v-slot:row-details="row">
                    <b-card>
                        {{row.item.info}}
                        <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>
                    </b-card>
                </template>
            </b-table>
        </b-container>
</template>


<script>
    import axios from 'axios'
    import Vue from "vue";

    export default {
        name: 'Courselist',
        data() {
            return {
                fields: ["courseId","courseName","division","section",
                    "instructorName","capacity","registered","seatAvailable","time","infos"],
                courses: [],
                info: ""
            }
        },
        // methods:{
        //   onSubmit(){
        //       const postUrl = "http://localhost:8080/api/instructor/update"
        //
        //       this.axios.post(postUrl, {
        //           username: this.username,
        //           info: this.info,
        //       })
        //           .then(response =>{
        //               if(response.status === 200){
        //               }
        //           })
        //   }
        // },
        created() {
            const apiURL = "http://localhost:8081/api/test/student/course";
            axios.post(apiURL, {
                username: Vue.prototype.$session.get("username"),
                role: Vue.prototype.$session.get("role")
            })
                .then(response => {
                    this.courses = response.data;
                })
                    .catch(()=> {
                        console.log("Dashboard REST call failed.");
                    })
        }
    };
</script>



