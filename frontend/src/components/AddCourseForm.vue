<template>
    <b-container>
        <b-form>
            <b-form-group
                    id="input-group-1"
                    label="Course Id:"
                    label-for="input-id"
            >
                <b-form-input
                        id="input-id"
                        type="text"
                        required
                        placeholder="Enter Course ID"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-name"
                    label="Course Name:"
                    label-for="input-name"
            >
                <b-form-input
                        id="input-name"
                        type="text"
                        required
                        placeholder="Enter Course Name"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-division"
                    label="Course Division:"
                    label-for="input-division"
            >
                <b-form-input
                        id="input-division"
                        type="text"
                        required
                        placeholder="Enter Course Division"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-section"
                    label="Course Division:"
                    label-for="input-section"
            >
                <b-form-input
                        id="input-section"
                        type="text"
                        required
                        placeholder="Enter Course Section"
                ></b-form-input>
            </b-form-group>
            <b-form-group id="input-group-instructor" label="Instructor:" label-for="input-instructor">
                <b-form-select
                        id="input-instructor"
                        required
                ></b-form-select>
            </b-form-group>
            <b-form-group
                    id="input-group-capacity"
                    label="Course Capacity:"
                    label-for="input-capacity"
            >
                <b-form-input
                        id="input-capacity"
                        type="text"
                        required
                        placeholder="Enter Capacity"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-registered"
                    label="Course Registered:"
                    label-for="input-registered"
            >
                <b-form-input
                        id="input-registered"
                        type="text"
                        required
                        placeholder="Enter Registered Students"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-date"
                    label="Course Date:"
                    label-for="input-date"
            >
                <b-form-input
                        id="input-date"
                        type="text"
                        required
                        placeholder="Enter Date"
                ></b-form-input>
            </b-form-group>
        </b-form>
    </b-container>
</template>

<script>

    export default {
        name: "AddCourseForm"
    }
</script>

<style scoped>

</style>