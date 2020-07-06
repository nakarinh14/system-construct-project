<template>
    <b-container fluid>
        <b-table striped bordered hover :head-variant="'dark'" :items="courses" :fields="fields">
            <template v-slot:cell(infos)="row">
                <b-button size="sm" @click="row.toggleDetails" class="mr-2">
                    {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
                </b-button>

            </template>
            <template v-slot:row-details="row">
                <b-card>
                    <b-form-textarea
                        id="textarea"
                        v-model="text"
                        placeholder="Enter something..."
                        rows="3"
                        max-rows="6"
                    ></b-form-textarea>
                    {{row.item.info}}
                    <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>
                </b-card>
            </template>
        </b-table>
    </b-container>
</template>


<script>

    const apiURL = "http://localhost:8080/api/student/course";
    export default {
        name: 'InstructorDashboard',
        props: ['course'],
        data() {
            return {
                fields: ["courseId","courseName","division","section",
                    "capacity","registered","seatAvailable","time","infos"],
                courses: this.course,
                info: ""
            }
        },
    };
</script>



