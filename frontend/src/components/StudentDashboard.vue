<template>
        <b-container fluid>
            <b-table striped bordered hover :busy="busy" :head-variant="'dark'" :items="course" :fields="fields">
                <template v-slot:table-busy>
                    <div class="text-center text-danger my-2">
                        <b-spinner class="align-middle"></b-spinner>
                        <strong>Loading...</strong>
                    </div>
                </template>
                <template v-slot:cell(infos)="row">
                    <b-modal
                            :id="row.item.id+'-modal'"
                            size="lg"
                    >
                        <template v-slot:modal-title>
                            {{row.item.courseName}} (Section {{row.item.section}})
                        </template>
                        {{row.item.info}}
                    </b-modal>
                    <b-button v-b-modal="row.item.id+'-modal'" size="sm" class="mr-2">
                        Show Info
                    </b-button>
                </template>

                <template v-slot:cell(seatAvailable)="row">
                    {{row.item.capacity - row.item.registered}}
                </template>
            </b-table>
        </b-container>
</template>


<script>
    export default {
        name: 'StudentDashboard',
        props: ['course', 'busy'],
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
        }
    };
</script>



