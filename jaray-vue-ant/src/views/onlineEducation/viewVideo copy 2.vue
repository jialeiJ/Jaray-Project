<template>
    <div id="view">
        <el-dialog :title="title" :visible.sync="viewDialogFormVisible" 
            :before-close="handleClose">
            <ali-player v-if="showFlag" ref="aliPlayer" :vid="playerId" :playauth="playAuth" :source="source"></ali-player>
        </el-dialog>
    </div>
</template>

<script>
import aliPlayer from '@components/common/aliPlayer'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'viewVideo',
    props: {  },
    components: { 'ali-player': aliPlayer },
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            viewDialogFormVisible: false,
            showFlag: false,
            title: '',
            source: '',
            playerId: '',
            playAuth: '',
        }
    },
    created: function(){
        
    },
    mounted: function() {
        
    },
    methods: {
        openDialog: function(row){
            let that = this
            that.title = row.name
            that.showFlag = true
            that.viewDialogFormVisible = true
            
            that.playerId = row.videoId;
            that.source = row.videoAddr;
            that.playAuth = row.playAuth;
            console.log(row);
        },
        handleClose(done) {
            let that = this
            that.$refs.aliPlayer.pause()
            that.showFlag = false
            done();
        },
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
/deep/ .el-dialog .el-dialog__body {
  border-top: 1px solid #dcdfe6;
  border-bottom: 1px solid #dcdfe6;
  max-height: calc(85vh - 260px); 
  overflow-y: auto;
}
</style>
