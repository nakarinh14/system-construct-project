<template>
    <div id="app">
        <div id="nav" v-if="this.$route.path !== '/login'">
            <router-link to="/"><BIconHouseFill></BIconHouseFill>Home</router-link>  |
            <router-link to="/about">About</router-link>  |
            <router-link to="/setting"><BIconGearFill></BIconGearFill>Setting</router-link>
        </div>
        <router-view/>
    </div>
</template>

<script>

    export default {
        name: 'app',
    }

</script>

<style>
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }

    #nav {
        padding: 30px;
    }

    #nav a {
        font-weight: bold;
        color: #2c3e50;
        margin-right: 5px;
        margin-left: 5px;
    }

    #nav a.router-link-exact-active {
        color: #42b983;
    }

</style>
