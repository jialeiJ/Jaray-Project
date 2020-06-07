<template>
    <div>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[1, 5, 10, 100, 200, 300, 400]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
    </div>
</template>

<script>

export default {
    name: 'iPagination',
    props: {
        total: {
            type: Number,
            default: function(){
            return 0
        }
    },
    currentPage: {
        type: Number,
        default: function(){
            return 1
        }
    },
    pageSize: {
        type: Number,
        default: function(){
            return 10
        }
    }
  },
  data () {
    return {
      
    }
  },
  methods: {
    handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.$emit('handleSizeChange',val)
    },
    handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.$emit('handleCurrentChange',val)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
    font-weight: normal;
}
ul {
    list-style-type: none;
    padding: 0;
}
li {
    display: inline-block;
    margin: 0 10px;
}
a {
    color: #42b983;
}
</style>
