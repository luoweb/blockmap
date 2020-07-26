<template>
  <div id="app">
    <keep-alive include="home">
      <router-view/>
    </keep-alive>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 100%; /*宽度为屏幕宽度的100%*/
  height: 100%; /*高度为屏幕高度的100%*/
}
*{ /*设置全局无内外边距*/
  padding: 0;
  margin: 0;
}
</style>
