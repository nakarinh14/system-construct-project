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
            <template v-slot:cell(seatAvailable)="row">
                {{row.item.capacity - row.item.registered}}
            </template>
        </b-table>
    </b-container>
</template>


<script>
    export default {
        name: 'AdminDashboard',
        props: ['course'],
        data() {
            return {
                fields: ["courseId","courseName","division","section",
                    "instructorName","capacity","registered","seatAvailable","date","infos"],
                courses: this.course,
                info: ""
            }
        }
    };
</script>
