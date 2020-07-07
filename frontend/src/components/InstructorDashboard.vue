<template>
    <b-container fluid>
        <b-table striped bordered hover :head-variant="'dark'" :items="course" :fields="fields">
            <template v-slot:cell(infos)="row">
                <b-modal
                        :id="row.item.id+'-modal'"
                        @ok="handleOk"
                >
                    <b-form-textarea
                            :id="row.item.id+'-textarea'"
                            :ref="row.item.id+'-textarea'"
                            :value="row.item.info"
                            placeholder="Enter something..."
                            rows="4"
                            max-rows="6"
                    ></b-form-textarea>
                </b-modal>
                <b-button v-b-modal="row.item.id+'-modal'" size="sm" @click="row.toggleDetails" class="mr-2">
                    Edit Info
                </b-button>

            </template>
            <template v-slot:cell(seatAvailable)="row">
                {{row.item.capacity - row.item.registered}}
            </template>
        </b-table>
    </b-container>
</template>
<script>
    import axios from "axios";

    export default {
        name: 'InstructorDashboard',
        props: ['course'],
        data() {
            return {
                fields: [
                    {key:"courseId", sortable:true},
                    {key:"courseName", sortable:true},
                    {key:"division", sortable:true},
                    {key:"section", sortable:true},
                    {key:"instructorName", sortable:true},
                    {key:"capacity", sortable:true},
                    {key:"registered", sortable:true},
                    {key:"seatAvailable", sortable: true},
                    {key:"date"},
                    {key:"infos", label:"Info"}
                ],
                info: ""
            }
        },
        methods:{
            handleOk: function(e){
                const id = e.target.id.split("-")[0]  // Get id num
                const updatedText = this.$refs[id+'-textarea'].$refs.input._value // Access current value from form.
                console.log(updatedText)
                this.postInfo(id, updatedText)
            },
            postInfo: function(id, text){
                // Edit info of course teach by instructor.
                const apiURL = "http://localhost:8081/api/dashboard/update"
                axios.post(apiURL, {
                    "info": text,
                    "courseId": parseInt(id)
                },{withCredentials: true})
                    .then(response => {
                        if(response.data.status === "success"){
                            this.$router.go(0)
                            console.log("post update success")
                        } else{
                            console.log("post update not success")
                        }
                    })
                    .catch(()=> {
                        console.log("Dashboard post REST call failed.")
                    })
            }
        }
    };
</script>



