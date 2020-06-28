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
                    {{row.item.info}}
                    <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>
                </b-card>
            </template>
        </b-table>
    </b-container>
</template>


<script>

    const apiURL = "http://localhost:8080/api/courses";
    export default {
        name: 'Courselist',
        data() {
            return {
                fields: ["courseId","courseName","division","section",
                    "instructorName","capacity","registered","seatAvailable","time","infos"],
                courses: [],
            }
        },
        created() {
            this.axios.get(apiURL)
                .then(response => {
                    this.courses = response.data;
                })
                    .catch(()=> {
                        console.log("REST call failed.");
                    })

        }
    };
</script>



