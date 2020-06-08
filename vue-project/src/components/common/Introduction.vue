<template>
    <el-carousel :interval="4000" type="card" height="200px">
        <el-carousel-item height="900px">
            <h3 class="medium"></h3>
        </el-carousel-item>
        <el-carousel-item>
            <h3 class="medium"></h3>
        </el-carousel-item>
        <el-carousel-item>
            <h3 class="medium"></h3>
        </el-carousel-item>
    </el-carousel>
</template>

<script>

export default {
    name: 'Introduction',
    data () {
        return {
            
        }
    },
    methods: {
        
    },
    created: function(){
        
    },
    mounted: function(){
        
    }
}
</script>

<style scoped>
/** /deep/ 覆盖原有样式 */
/deep/ .el-carousel__container {
    height: 700px!important;
}

/deep/ .el-carousel__indicators--outside {
    display: none!important;
}
/deep/ .el-carousel__arrow {
    display: none!important;
}

.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
}
  
.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}
  
.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}
</style>