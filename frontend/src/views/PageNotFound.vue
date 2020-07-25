<template>
    <b-spinner variant="primary" label="Spinning"></b-spinner>
</template>
<script>
    export default {
        name: "PageNotFound",
        created() {
            this.$router.push("/")
        }
    }
</script>

