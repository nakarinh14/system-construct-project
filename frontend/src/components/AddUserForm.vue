<template>
    <b-container>
        <b-form>
            <b-form-group
                    id="input-group-1"
                    label="Username:"
                    label-for="input-1"
            >
                <b-form-input
                        id="input-1"
                        type="text"
                        required
                        placeholder="Enter username"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-pw"
                    label="Password:"
                    label-for="input-pw"
            >
                <b-form-input
                        id="input-pw"
                        type="password"
                        required
                        placeholder="Enter password"
                ></b-form-input>
            </b-form-group>
            <b-form-group
                    id="input-group-title"
                    label="Title:"
                    label-for="input-title"
            >
                <b-form-input
                        id="input-title"
                        type="text"
                        required
                        placeholder="Enter title"
                ></b-form-input>
            </b-form-group>
            <b-form-group id="input-group-fn" label="Firstname:" label-for="input-fn">
                <b-form-input
                        id="input-fn"
                        required
                        placeholder="Enter firstname"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-ln" label="Lastname:" label-for="input-ln">
                <b-form-input
                        id="input-ln"
                        required
                        placeholder="Enter lastname"
                ></b-form-input>
            </b-form-group>

            <b-form-group id="input-group-3" label="Role:" label-for="input-r">
                <b-form-select
                        id="input-r"
                        :options="['Student', 'Instructor', 'Admin']"
                        required
                ></b-form-select>
            </b-form-group>

        </b-form>
    </b-container>
</template>

<script>
    export default {
        name: "AddUserForm"
    }
</script>

<style scoped>

</style>