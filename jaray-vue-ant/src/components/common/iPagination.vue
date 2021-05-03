<template>
    <a-pagination
        size="small"
        :current="currentPage"
        :pageSize="pageSize"
        @change="pageNumberChange"
        @showSizeChange="onShowSizeChange"
        :pageSizeOptions="['1', '5', '10', '20', '50', '100', '200', '300', '400']"
        :total="total"
        show-size-changer
        show-quick-jumper />
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
        onShowSizeChange(current, pageSize) {
            console.log(`每页 ${pageSize} 条`);
            let that = this
            that.$emit('handleSizeChange',pageSize)
        },
        pageNumberChange(pageNumber) {
            console.log(`当前页: ${pageNumber}`);
            let that = this
            that.$emit('handleCurrentChange',pageNumber)
        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
