<template>
    <div id="view">
        <el-dialog ref="player" title="详情" :visible.sync="viewDialogFormVisible" 
            :close="handleClose">
            <!-- <div id="J_prismPlayer" class="prism-player"></div> -->
            <ali-player :source="视频url" :vid="视频vid" :playauth="播放鉴权" ref="player"></ali-player>
            <button @click="play">播放</button>
            <button @click="pause">暂停</button>
            <button @click="replay">重播</button>
        </el-dialog>
    </div>
</template>

<script>
//import VueAliplayer from 'vue-aliplayer'
import {mapActions, mapGetters} from 'vuex'

export default {
    name: 'viewVideo',
    props: {  },
    //components: { 'ali-player': VueAliplayer },
    data () {
        return {
            // label宽度
            formLabelWidth: 'calc(14vh - 0px)',
            viewDialogFormVisible: false,
            viewForm: {},
            source: 'https://outin-04074955395d11eb99ff00163e1c35d5.oss-cn-shanghai.aliyuncs.com/sv/2743e610-1764d0f8085/2743e610-1764d0f8085.mp3?Expires=1607788965&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=twyOufTBrgv3FhfXqtkIUHd2EVI%3D',
            playerId: '672980c7d8134824b379876ce858b881',
            playAuth: 'eyJTZWN1cml0eVRva2VuIjoiQ0FJU2h3TjFxNkZ0NUIyeWZTaklyNWZHYzlDQmxPWjAyWlNUVTF6NnAwTm1icmRpbnFTVGtUejJJSDlJZEhWb0FPOGZ2dlUwbTJ0WTdQc1psck1xRk1jWUhoV1lONVlydjhRSHFWdnhKcExGc3QySjZyOEpqc1ZsaWJWRWhsaXBzdlhKYXNEVkVmbDJFNVhFTWlJUi8wMGU2TC8rY2lyWXBUWEhWYlNDbFo5Z2FQa09Rd0M4ZGtBb0xkeEtKd3hrMnQxNFVtWFdPYVNDUHdMU2htUEJMVXhtdldnR2wyUnp1NHV5M3ZPZDVoZlpwMXI4eE80YXhlTDBQb1AyVjgxbExacGxlc3FwM0k0U2M3YmFnaFpVNGdscjhxbHg3c3BCNVN5Vmt0eVdHVWhKL3phTElvaXQ3TnBqZmlCMGVvUUFQb3BGcC9YNmp2QWF3UExVbTliWXhncGhCOFIrWGo3RFpZYXV4N0d6ZW9XVE84MCthS3p3TmxuVXo5bUxMZU9WaVE0L1ptOEJQdzQ0RUxoSWFGMElVRTF5R21DQ2QvWDRvZ3VSUDF6N0VwTG9pdjltamNCSHFIeno1c2VQS2xTMVJMR1U3RDBWSUpkVWJUbHphRUpHZ1RTNExmWldJbGNUS0FNOVd1MlBNYXgzYlFGRHI1M3ZzVGJiWHpaYjBtcHR1UG56ZDE0Sk9CS2cxMUtVR29BQkFYaFBVeW15Y05nUVY2MjFXUUJubXk1d1VEblNqSmhYM2kzN1I1SElwanJDaXlLUFpyU3pCQWZVQ0ZTNFFQL0R4UXQwVTJmU3J6ay9sRzkwUjN5WlZYL05jbHZXSEJjQWhRdGZiajN0RUsrTTJsbUVQYUl0YnhuWFVjWWJYNnIxOFVmd240amFJYlYxNnoyK0VpTFJaY2YzVE1PTkJaYzBFYWZIZ0JJNVFRcz0iLCJBdXRoSW5mbyI6IntcIkNJXCI6XCJIWW9SSTM3RHVCZk8xbGV1R0d2LzNWdURoL1krMzZHNUN0YkVVWnJRdzNqWEphcHFhcGpObHRCQlo5Q2ViN09CXCIsXCJDYWxsZXJcIjpcImpmWlFhV1kvVHFVaVBWbUp3OTlpM1FVMEFPWEJBRk01MXBhK1VDdWdLVzA9XCIsXCJFeHBpcmVUaW1lXCI6XCIyMDIwLTEyLTEyVDE2OjExOjIyWlwiLFwiTWVkaWFJZFwiOlwiNjcyOTgwYzdkODEzNDgyNGIzNzk4NzZjZTg1OGI4ODFcIixcIlNpZ25hdHVyZVwiOlwiU0U1bzBtRmFuMWpqRzJHYkdxOHRFNEFuV3k4PVwifSIsIlZpZGVvTWV0YSI6eyJTdGF0dXMiOiJOb3JtYWwiLCJWaWRlb0lkIjoiNjcyOTgwYzdkODEzNDgyNGIzNzk4NzZjZTg1OGI4ODEiLCJUaXRsZSI6IuS9leaZn+mTrSAtIOS9m+ivtC5tcDMiLCJEdXJhdGlvbiI6Mjc0LjYyNTN9LCJBY2Nlc3NLZXlJZCI6IlNUUy5OVHM4ajV5OVVuVnhRektDQzNiOE5xYThzIiwiQWNjZXNzS2V5U2VjcmV0IjoiN3B5M045RnZMQzN0aExLaGlIaVhHc0xBY1BZa2RwRXVvMXBzYjlkTWtaVjgiLCJSZWdpb24iOiJjbi1zaGFuZ2hhaSIsIkN1c3RvbWVySWQiOjE3MDUzMTQyNTM5ODgwMDh9',
            player: null,
        }
    },
    created: function(){
        
    },
    mounted: function() {
        
    },
    methods: {
        openDialog: function(row){
            let that = this
            that.viewDialogFormVisible = true
            that.playerId = row.videoId;
            that.source = row.videoAddr;
            that.playAuth = '';

            if (!that.player) {
                setTimeout(() => {
                    var player = new Aliplayer({
                        id: "J_prismPlayer",
                        source: "https://outin-04074955395d11eb99ff00163e1c35d5.oss-cn-shanghai.aliyuncs.com/sv/39bf18fe-17647a8380f/39bf18fe-17647a8380f.mp3?Expires=1607830321&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=tngu9JhYRfaCZDUxqZjxvcOwO78%3D",
                        width: "100%",
                        height: "300px",
                        /* To set an album art, you must set 'autoplay' and 'preload' to 'false' */
                        autoplay: true,
                        preload: false,
                        isLive: false
                    }, function (player) {
                        console.log(player);
                        that.player = player;
                        console.log(that.player);
                        console.log("The player is created");
                    });
                });
                
            } else if (that.player.getStatus() == 'pause') {
                 that.player.play();
            }

            console.log(row);
        },
        handleClose(done) {
            let that = this;
            if (that.player) {
                console.log(that.player.getStatus());
            }
            
            if (that.player && that.player.getStatus() == 'playing') {
                
                that.player.pause();
            }
            done();
        },
        play: function(){
            const player = this.$refs.player.instance
            player && player.play()
        },
        pause: function() {
            const player = this.$refs.player.instance
            player && player.pause()
        },
        replay: function() {
            const player = this.$refs.player.instance
            player && player.replay()
        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
// <style lang="scss" scoped>
// /deep/ .el-select,.el-cascader {
//     display: block;
//     position: relative;
// }

// /deep/ .el-dialog .el-dialog__body {
//   border-top: 1px solid #dcdfe6;
//   border-bottom: 1px solid #dcdfe6;
//   max-height: calc(85vh - 260px); 
//   overflow-y: auto;
// }
// </style>
<style lang="postcss" scoped>
    @import 'https://g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css';
</style>
