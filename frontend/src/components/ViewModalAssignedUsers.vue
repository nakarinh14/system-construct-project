<template>
<!--    <b-btn-->
<!--            size="sm"-->
<!--            class="mr-2"-->
<!--            v-b-modal="'show-modal-'+row.item.id"-->
<!--            :id="'btn-student-'+row.item.id"-->
<!--            @click="showStudents(row.item.id)"><BIconSearch></BIconSearch>-->
<!--    </b-btn>-->
<!--    -->
<!--    <b-modal-->
<!--            :id="'show-modal-'+row.item.id"-->
<!--    >-->
<!--        <template v-slot:modal-title>-->
<!--            Enrolled Students for {{row.item.courseName}} (Section {{row.item.section}})-->
<!--        </template>-->
<!--        &lt;!&ndash; Table in show students modal  &ndash;&gt;-->
<!--        <b-table bordered :head-variant="'dark'" :items="students_get" :fields="students_fields">-->
<!--            <template v-slot:cell(name)="inner">-->
<!--                {{inner.item.profile.title}} {{inner.item.profile.firstname}} {{inner.item.profile.lastname}}-->
<!--            </template>-->
<!--        </b-table>-->
<!--    </b-modal>-->
<!--    -->
</template>

<script>
    export default {
        name: "ViewModalAssignedUsers",
        props: ['user_list', ]
    }
</script>

<style scoped>

</style>