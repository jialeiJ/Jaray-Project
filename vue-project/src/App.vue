<template>
    <div id="app">
        <main-page></main-page>
    </div>
</template>

<script>
import MainPage from '@/components/common/MainPage'

export default {
    name: 'App',
    components: { MainPage },
}
</script>

<style>
#app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #46515c;
}
</style>
