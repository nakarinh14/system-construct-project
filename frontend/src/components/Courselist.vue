<template>
    <b-container fluid>
        <b-table striped bordered hover :head-variant="'dark'" :items="courses" :fields="fields"></b-table>
    </b-container>
</template>

<script>

    const apiURL = "http://localhost:8080/api/courses";
    export default {
        name: 'Courselist',
        data() {
            return {
                fields: ["courseId","courseName","division","section",
                    "instructorName","capacity","registered","seatAvailable","time","info"],
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



