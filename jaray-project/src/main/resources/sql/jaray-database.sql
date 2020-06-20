/*
 Navicat Premium Data Transfer

 Source Server         : Master-Centos
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : 192.168.1.104:3306
 Source Schema         : jaray-database

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 17/06/2020 23:31:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for annex
-- ----------------------------
DROP TABLE IF EXISTS `annex`;
CREATE TABLE `annex`  (
  `fid` int(0) NOT NULL AUTO_INCREMENT COMMENT '附件主键id',
  `fName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件名称',
  `fUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件url',
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of annex
-- ----------------------------
INSERT INTO `annex` VALUES (1, '附件1', '附件url1');
INSERT INTO `annex` VALUES (2, '附件2', '附件url2');

-- ----------------------------
-- Table structure for complaint
-- ----------------------------
DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint`  (
  `cid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '投诉主键id',
  `title` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `cFileIds` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '投诉附件id组,分割',
  `createTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of complaint
-- ----------------------------
INSERT INTO `complaint` VALUES ('1', '投诉', '产品使用体验不好1', '1,2', '2019-10-04 21:23:37', '2020-06-06 23:47:36');
INSERT INTO `complaint` VALUES ('2', '投诉', '体验不好bu', '1,3', '2019-12-08 21:42:30', '2019-12-09 00:19:50');
INSERT INTO `complaint` VALUES ('3', '投诉', '体验不好bu', '1,3', '2019-12-08 21:42:30', '2019-12-23 21:59:43');
INSERT INTO `complaint` VALUES ('4', '投诉', '体验不好bu', '1,3', NULL, '2019-12-23 21:59:45');
INSERT INTO `complaint` VALUES ('9', '12', '11111', '11,22', NULL, NULL);
INSERT INTO `complaint` VALUES ('10', '12', '11111', '11,22', NULL, NULL);
INSERT INTO `complaint` VALUES ('12', '12', '11111', '11,22', NULL, NULL);
INSERT INTO `complaint` VALUES ('C2020060600008', '12', '11111', '11,22', NULL, NULL);
INSERT INTO `complaint` VALUES ('C2020060600009', '12', '11111', '11,22', NULL, NULL);
INSERT INTO `complaint` VALUES ('C2020060600010', 'ceshi', '测试', '11,22', NULL, NULL);
INSERT INTO `complaint` VALUES ('C2020060600011', 'ceshi', '测试', '11,22', NULL, NULL);
INSERT INTO `complaint` VALUES ('C2020060600012', 'ceshi', '测试', '11,22', NULL, NULL);
INSERT INTO `complaint` VALUES ('C2020060600013', 'ceshi2', '测试', '11,22', NULL, '2020-06-07 01:49:44');
INSERT INTO `complaint` VALUES ('C2020060600014', 'ceshi', '测试', '11,22', NULL, NULL);
INSERT INTO `complaint` VALUES ('C2020060600015', 'ceshi', '测试', '11,22', NULL, NULL);
INSERT INTO `complaint` VALUES ('C2020060600016', '12', '测试', '11,22', NULL, '2020-06-07 01:42:32');
INSERT INTO `complaint` VALUES ('C2020060600017', '1341234', '123412342134', '11,22', NULL, NULL);

-- ----------------------------
-- Table structure for decision
-- ----------------------------
DROP TABLE IF EXISTS `decision`;
CREATE TABLE `decision`  (
  `did` int(0) NOT NULL COMMENT '决策主键id',
  `dOpinion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '决策意见',
  `closedLoop` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '闭环',
  `dFileIds` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '决策附件id组,分割',
  PRIMARY KEY (`did`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(0) NULL DEFAULT NULL,
  `refresh_token_validity` int(0) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('client_1', '', '$2a$10$HrSIBBuk6RB64JqgRIH8feqYT3R4UbKFP4N4YYw64z50Xt9KbmB3G', 'all', 'password', NULL, NULL, 1800, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('client_2', '', '$2a$10$HrSIBBuk6RB64JqgRIH8feqYT3R4UbKFP4N4YYw64z50Xt9KbmB3G', 'all', 'client_credentials', NULL, NULL, 1800, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('client_3', NULL, '$2a$10$HrSIBBuk6RB64JqgRIH8feqYT3R4UbKFP4N4YYw64z50Xt9KbmB3G', 'all', 'authorization_code', 'https://www.baidu.com', NULL, 1800, 1800, NULL, 'true');

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence`  (
  `seq_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `current_val` int(0) NOT NULL,
  `increment_val` int(0) NOT NULL DEFAULT 1
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 CHECKSUM = 1 COLLATE = utf8_general_ci COMMENT = '序列表，命名s_[table_name]' DELAY_KEY_WRITE = 1 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('seq_complaint_id', 17, 1);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构名称',
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '上级机构ID，一级机构为0',
  `order_num` int(0) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '机构管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (17, '轻尘集团', NULL, 0, 'admin', '2018-09-23 19:35:22', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (18, '牧尘集团', NULL, 1, 'admin', '2018-09-23 19:35:55', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (19, '三国集团', NULL, 2, 'admin', '2018-09-23 19:36:24', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (21, '上海分公司', 18, 0, 'admin', '2018-09-23 19:37:03', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (22, '北京分公司', 17, 1, 'admin', '2018-09-23 19:37:17', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (23, '北京分公司', 18, 1, 'admin', '2018-09-23 19:37:28', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (25, '技术部', 22, 0, 'admin', '2018-09-23 19:38:00', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (26, '技术部', 21, 0, 'admin', '2018-09-23 19:38:10', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (27, '技术部', 23, 0, 'admin', '2018-09-23 19:38:17', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (29, '市场部', 22, 0, 'admin', '2018-09-23 19:38:45', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (30, '市场部', 23, 0, 'admin', '2018-09-23 19:39:01', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (33, '魏国', 19, 0, 'admin', '2018-09-23 19:40:42', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (34, '蜀国', 19, 1, 'admin', '2018-09-23 19:40:54', NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (35, '吴国', 19, 2, 'admin', '2018-09-23 19:41:04', NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据值',
  `label` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名',
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
  `sort` decimal(10, 0) NOT NULL COMMENT '排序（升序）',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `del_flag` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (3, 'male', '男', 'sex', '性别', 0, 'admin', '2018-09-23 19:52:54', NULL, NULL, '性别', 0);
INSERT INTO `sys_dict` VALUES (4, 'female', '女', 'sex', '性别', 1, 'admin', '2018-09-23 19:53:17', NULL, NULL, '性别', 0);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `time` bigint(0) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1754 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1071, 'admin', NULL, 'com.louis.kitty.admin.sevice.impl.SysDictServiceImpl.findPage()', '{\"columnFilters\":{\"label\":{\"name\":\"label\",\"value\":\"\"}},\"pageNum\":1,\"pageSize\":8}', 4, '0:0:0:0:0:0:0:1', 'admin', '2018-09-23 19:54:16', NULL, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `type` int(0) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(0) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, NULL, NULL, 0, 'el-icon-setting', 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, '/sys/user', NULL, 1, 'el-icon-service', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (3, '机构管理', 1, '/sys/dept', NULL, 1, 'el-icon-news', 2, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (4, '角色管理', 1, '/sys/role', NULL, 1, 'el-icon-view', 4, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (5, '菜单管理', 1, '/sys/menu', NULL, 1, 'el-icon-menu', 5, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (6, '数据监控', 43, 'http://139.196.87.48:8001/druid/login.html', NULL, 1, 'el-icon-warning', 0, NULL, NULL, 'admin', '2018-12-27 11:03:45', 0);
INSERT INTO `sys_menu` VALUES (7, '字典管理', 1, '/sys/dict', NULL, 1, 'el-icon-edit-outline', 7, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (8, '系统日志', 1, '/sys/log', 'sys:log:view', 1, 'el-icon-info', 8, NULL, NULL, 'admin', '2018-09-23 19:32:28', 0);
INSERT INTO `sys_menu` VALUES (28, '使用案例', 0, NULL, NULL, 0, 'el-icon-picture-outline', 6, NULL, NULL, 'admin', '2018-11-15 14:39:43', 0);
INSERT INTO `sys_menu` VALUES (29, '国际化', 28, '/demo/i18n', NULL, 1, 'el-icon-edit', 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (30, '换皮肤', 28, '/demo/theme', NULL, 1, 'el-icon-picture', 2, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (35, '接口文档', 0, 'http://localhost:8080/swagger-ui.html', NULL, 1, 'el-icon-document', 3, NULL, NULL, 'admin', '2018-12-27 11:04:18', 0);
INSERT INTO `sys_menu` VALUES (38, '服务监控', 43, 'http://139.196.87.48:8000/', '', 1, 'el-icon-view', 1, 'admin', '2018-11-02 20:02:15', 'admin', '2018-12-27 11:03:53', 0);
INSERT INTO `sys_menu` VALUES (41, '注册中心', 44, 'http://139.196.87.48:8500', '', 1, ' el-icon-view', 0, 'admin', '2018-11-03 11:06:48', 'admin', '2018-12-27 11:08:11', 0);
INSERT INTO `sys_menu` VALUES (42, '代码生成', 0, '/generator/generator', '', 1, 'el-icon-star-on', 5, 'admin', '2018-11-15 14:39:30', 'admin', '2018-11-15 14:56:18', 0);
INSERT INTO `sys_menu` VALUES (43, '系统监控', 0, '', '', 0, 'el-icon-info', 1, 'admin', '2018-12-27 10:57:29', 'admin', '2018-12-27 11:02:26', 0);
INSERT INTO `sys_menu` VALUES (44, '服务治理', 0, '', '', 0, 'el-icon-service', 2, 'admin', '2018-12-27 11:05:48', 'admin', '2018-12-27 11:06:39', 0);
INSERT INTO `sys_menu` VALUES (45, '测试', 2, '/complaint', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'admin', '超级管理员', 'admin', '2018-08-14 11:11:11', 'admin', '2018-09-23 19:07:18', 0);
INSERT INTO `sys_role` VALUES (2, 'dev', '开发人员', 'admin', '2018-08-14 11:11:11', 'admin', '2018-08-14 11:11:11', 0);
INSERT INTO `sys_role` VALUES (3, 'test', '测试人员', 'admin', '2018-08-14 11:11:11', 'admin', '2018-08-14 11:11:11', 0);
INSERT INTO `sys_role` VALUES (8, 'mng', '部门经理', 'admin', '2018-09-23 19:09:52', NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(0) NULL DEFAULT NULL COMMENT '机构ID',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色机构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(0) NULL DEFAULT NULL COMMENT '菜单ID',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 445 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (224, 4, 1, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (225, 4, 2, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (226, 4, 9, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (227, 4, 3, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (228, 4, 13, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (229, 4, 4, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (230, 4, 17, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (231, 4, 5, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (232, 4, 21, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (233, 4, 6, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (234, 4, 7, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (235, 4, 31, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (236, 4, 8, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (237, 4, 25, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (238, 4, 26, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (239, 4, 27, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (240, 4, 28, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (241, 4, 29, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (242, 4, 30, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (243, 4, 35, 'admin', '2018-09-23 16:22:41', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (388, 2, 1, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (389, 2, 2, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (390, 2, 9, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (391, 2, 3, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (392, 2, 13, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (393, 2, 17, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (394, 2, 5, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (395, 2, 21, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (396, 2, 7, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (397, 2, 31, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (398, 2, 8, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (399, 2, 6, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (400, 2, 35, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (401, 2, 28, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (402, 2, 29, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (403, 2, 30, 'admin', '2018-09-23 19:51:53', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (404, 3, 1, 'admin', '2018-09-23 19:51:55', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (405, 3, 2, 'admin', '2018-09-23 19:51:55', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (406, 3, 9, 'admin', '2018-09-23 19:51:55', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (407, 3, 3, 'admin', '2018-09-23 19:51:55', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (408, 3, 13, 'admin', '2018-09-23 19:51:55', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (409, 3, 8, 'admin', '2018-09-23 19:51:55', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (410, 3, 6, 'admin', '2018-09-23 19:51:55', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (411, 3, 28, 'admin', '2018-09-23 19:51:55', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (412, 3, 29, 'admin', '2018-09-23 19:51:55', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (413, 3, 30, 'admin', '2018-09-23 19:51:55', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (431, 8, 1, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (432, 8, 2, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (433, 8, 9, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (434, 8, 3, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (435, 8, 13, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (436, 8, 4, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (437, 8, 17, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (438, 8, 5, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (439, 8, 21, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (440, 8, 7, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (441, 8, 31, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (442, 8, 8, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (443, 8, 6, 'admin', '2018-09-23 19:55:08', NULL, NULL);
INSERT INTO `sys_role_menu` VALUES (444, 8, 35, 'admin', '2018-09-23 19:55:08', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_perms
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_perms`;
CREATE TABLE `sys_role_perms`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `type` int(0) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(0) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_perms
-- ----------------------------
INSERT INTO `sys_role_perms` VALUES (9, '查看', 2, NULL, 'sys:user:view', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (10, '新增', 2, NULL, 'sys:user:add', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (11, '修改', 2, NULL, 'sys:user:edit', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (12, '删除', 2, NULL, 'sys:user:delete', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (13, '查看', 3, NULL, 'sys:dept:view', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (14, '新增', 3, NULL, 'sys:dept:add', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (15, '修改', 3, NULL, 'sys:dept:edit', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (16, '删除', 3, NULL, 'sys:dept:delete', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (17, '查看', 4, NULL, 'sys:role:view', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (18, '新增', 4, NULL, 'sys:role:add', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (19, '修改', 4, NULL, 'sys:role:edit', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (20, '删除', 4, NULL, 'sys:role:delete', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (21, '查看', 5, NULL, 'sys:menu:view', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (22, '新增', 5, NULL, 'sys:menu:add', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (23, '修改', 5, NULL, 'sys:menu:edit', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (24, '删除', 5, NULL, 'sys:menu:delete', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (31, '查看', 7, NULL, 'sys:dict:view', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (32, '新增', 7, NULL, 'sys:dict:add', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (33, '修改', 7, NULL, 'sys:dict:edit', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (34, '删除', 7, NULL, 'sys:dict:delete', 2, NULL, 0, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role_perms` VALUES (45, '测试', 2, '/complaint', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(0) NULL DEFAULT NULL COMMENT '机构ID',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(0) NULL DEFAULT 0 COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (2, 'admin', '$2a$10$d.xdzuGpgXQKPgnZOMdMv.h97epYAF2HurMKNdmu/16MJOT6to6/a', 'YzcmCZNvbXocrsz9dm8e', 'admin@qq.com', '13612345678', 1, 4, 'admin', '2018-08-14 11:11:11', 'admin', '2018-08-14 11:11:11', 0);
INSERT INTO `sys_user` VALUES (22, '刘备', 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 34, 'admin', '2018-09-23 19:43:00', 'admin', '2018-11-04 10:12:45', 0);
INSERT INTO `sys_user` VALUES (23, '赵云', 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 34, 'admin', '2018-09-23 19:43:44', 'admin', '2018-09-23 19:43:52', 0);
INSERT INTO `sys_user` VALUES (24, '诸葛亮', 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 34, 'admin', '2018-09-23 19:44:23', 'admin', '2018-09-23 19:44:29', 0);
INSERT INTO `sys_user` VALUES (25, '曹操', 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 33, 'admin', '2018-09-23 19:45:32', 'admin', '2018-09-23 19:45:37', 0);
INSERT INTO `sys_user` VALUES (26, '典韦', 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 33, 'admin', '2018-09-23 19:45:48', 'admin', '2018-09-23 19:45:57', 0);
INSERT INTO `sys_user` VALUES (27, '夏侯惇', 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 33, 'admin', '2018-09-23 19:46:09', 'admin', '2018-09-23 19:46:17', 0);
INSERT INTO `sys_user` VALUES (28, '荀彧', 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 33, 'admin', '2018-09-23 19:46:38', 'admin', '2018-11-04 15:33:17', 0);
INSERT INTO `sys_user` VALUES (29, '孙权', 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 35, 'admin', '2018-09-23 19:46:54', 'admin', '2018-09-23 19:47:03', 0);
INSERT INTO `sys_user` VALUES (30, '周瑜', 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 35, 'admin', '2018-09-23 19:47:28', 'admin', '2018-09-23 19:48:04', 0);
INSERT INTO `sys_user` VALUES (31, '陆逊', 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 35, 'admin', '2018-09-23 19:47:44', 'admin', '2018-09-23 19:47:58', 0);
INSERT INTO `sys_user` VALUES (32, '黄盖', 'fd80ebd493a655608dc893a9f897d845', 'YzcmCZNvbXocrsz9dm8e', 'test@qq.com', '13889700023', 1, 35, 'admin', '2018-09-23 19:48:38', 'admin', '2018-09-23 19:49:02', 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色ID',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (2, 2, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (26, 5, 3, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (33, 6, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (34, 4, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (35, 9, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (36, 10, 3, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (37, 11, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (38, 12, 3, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (39, 15, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (41, 16, 3, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (42, 8, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (43, 7, 4, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (45, 18, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (46, 17, 3, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (47, 3, 4, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (48, 21, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (50, 23, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (51, 24, 3, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (52, 25, 8, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (53, 26, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (54, 27, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (56, 29, 8, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (57, 31, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (58, 30, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (59, 32, 3, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (68, 33, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (69, 22, 8, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (70, 22, 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (71, 28, 2, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(0) NOT NULL,
  `token` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'token',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `token`(`token`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户Token   没有用数据库保存token  暂未用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES (53, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjExMjg3MCwiY3JlYXRlZCI6MTU5MjExMjg2MDU3MiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.gXL68q4F52uKp9LLDHPc8X9wKkNWCMBOFgyHjNDdRvN4Ku16rzIXFJjRGqrVcVX4QnP05OFF2NBA3vgvT0z0WQ', NULL, 'admin', '2020-06-14 13:34:21', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (54, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjExOTAwNiwiY3JlYXRlZCI6MTU5MjExODk5NjM1NSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.o4Ay4CmVzy4SITSnPzvm_MoDd2o9Mlwp_DZN9HnbYni9XnY7C4z1ElLjhCrx_e_MQjkLsxiiV1IBvgSNNvdMWg', NULL, 'admin', '2020-06-14 15:16:37', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (55, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjExOTIxMiwiY3JlYXRlZCI6MTU5MjExOTIwMjM0NywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.tFSj8swMQbaB-X23ruucPMxB4Sa_sWFFFVnM5vzBKwj_DxIvc0-sWpjUWFK5VDQQ2nymYG86OpuNYgXdHWSicw', NULL, 'admin', '2020-06-14 15:20:02', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (56, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjExOTM2MiwiY3JlYXRlZCI6MTU5MjExOTM1MjkxOSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.smPeYrotI6J7tU4vZQ8VmRYf5mdLZyOnB8CZFkDTkv878BxCMnF2R1FuEh4xSY5gpY0l43U-_b5HXqCjGxSz8Q', NULL, 'admin', '2020-06-14 15:22:33', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (57, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEyMDk3OCwiY3JlYXRlZCI6MTU5MjEyMDk2ODkwOSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.z0q_s5cxPfviNeXPlh5drpYd35g4nP8VvPIkq7gHjZpX5DbwefeNYAgZk30IO8uTi00heb3J58TNL10dGN9kZA', NULL, 'admin', '2020-06-14 15:49:29', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (58, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEyMjM2NiwiY3JlYXRlZCI6MTU5MjEyMjM1NjQ5OCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.2AuqF_346YYsw4Zfwbzk5lGQLScfo7qgmXqJnOuOXPhnHJnssbfNQINDK8IxREBEjb8ZWgs2GV4FPfuiyJSeaQ', NULL, 'admin', '2020-06-14 16:12:37', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (59, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEyNTk4NCwiY3JlYXRlZCI6MTU5MjEyNTk3NDM0NywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.ibj8M7nBs8B2GR7EhRmUQphegz76n39FCQuGfc8xOBhw1eo_wYjbr7hf35qKVFELjI16GTTsxz3G31WkOTvmkQ', NULL, 'admin', '2020-06-14 17:12:55', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (60, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEyNjE4MywiY3JlYXRlZCI6MTU5MjEyNjE3Mzc4MSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.4c-UeMxcq8yCuESR74Sjhfx6DUCVii8RsC3IfoQX1c6NJFkfjx6P4LU5AL1X_LCgIiiZFbSbWA3F4qX2TIYhmQ', NULL, 'admin', '2020-06-14 17:16:14', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (61, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEyNjM3NSwiY3JlYXRlZCI6MTU5MjEyNjM2NTU3NSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.TFLsFSIw-U59Ypw8cyPrLC_gMr7MTmAVzmlIhJXCppH1EZAgQ7StguuGapCrBgMbDoF99jmC5_FeEENxL4bnag', NULL, 'admin', '2020-06-14 17:19:26', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (62, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEyNjkxOSwiY3JlYXRlZCI6MTU5MjEyNjkwOTk5MywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.vmmqCGW6JPSHTbrQniZOYLuxyfGGPmrMcxtOVGNiZVJqxlsG7BHRFyl5LeAU35Rcp8g_BNhA4obh2w_Ayu82TQ', NULL, 'admin', '2020-06-14 17:28:30', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (63, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEyODI4MywiY3JlYXRlZCI6MTU5MjEyODI3MzA1OCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.6AD9Aw6RQqQD8ODm1R9GgLOnwxLMHdr1lpUGoJXsvFzxg2tqwEa5ciqmb8BmZvGHRPE7dMBv0lzaVjPukr2XbA', NULL, 'admin', '2020-06-14 17:51:13', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (64, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEyOTIzMCwiY3JlYXRlZCI6MTU5MjEyOTIyMDQ5MCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.9CRIO9T1PVWQ8pgat0Nmv-GHb8S1vU4LpNuDPuwIKfrjLP8ugHML9bLUJn5yV_3w9MgyU0qOgrIBBwPsux-3lg', NULL, 'admin', '2020-06-14 18:07:01', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (65, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEyOTcwMiwiY3JlYXRlZCI6MTU5MjEyOTY5MjQ1MiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.sGgWAwvoWhw6EKJmuo-_cZ6ua-mj0PQ8QL1t3keun_O4RUHdoC6--1tcgFC8epLkELpjBTbxSQLQRQSqJahDww', NULL, 'admin', '2020-06-14 18:14:52', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (66, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEyOTc4MSwiY3JlYXRlZCI6MTU5MjEyOTc3MTk1NywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.Bla7IGNTMtpMuuEst7xNGSpWO5c7-7Sp-cK7Xw5nUc2OIMi1EQzwgHcwFJIpaRUghJEI-RB32bk3Mvwuu--7Uw', NULL, 'admin', '2020-06-14 18:16:12', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (67, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzMDYwNywiY3JlYXRlZCI6MTU5MjEzMDU5Nzk0NiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.j3UBjvAJwIIGYANvAtsoe-2YOW735UrKTHzsnlb94uBm1hRkupEtipBkABey0Yhr7VQ1H0FVe8fsrsEns780jQ', NULL, 'admin', '2020-06-14 18:29:59', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (68, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzMDg3NywiY3JlYXRlZCI6MTU5MjEzMDg2NzYyMSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.nm1-QadOPIOmur5c1ZpIQn9BY5y5HYNzUIOFoIZQnvvGA8VZ4pULvbsq6vAchQzF5orCkz9qocSdwA6Xbauz2A', NULL, 'admin', '2020-06-14 18:34:28', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (69, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzMDk0OSwiY3JlYXRlZCI6MTU5MjEzMDkzOTMwNCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.F4xDxZbnJNPXr5AehNkhcpXkx3V5Zrs_9Q-BmZqqh0be118hDqjCWqObuZLsAl5_0UWsBcVYBHkezOOAgfjRIw', NULL, 'admin', '2020-06-14 18:35:39', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (70, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzMTA2NSwiY3JlYXRlZCI6MTU5MjEzMTA1NTk0NywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.TXH7CAptbQkSgmWa0n92Z9tnBCqbk3BQ1DXihChuDx5zJWgtdhm7vo-lNlbDPPtLm2uXNhbglHt1KVa0cHKTNg', NULL, 'admin', '2020-06-14 18:37:36', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (71, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzMTEzNCwiY3JlYXRlZCI6MTU5MjEzMTEyNDA1NiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.jb-ZkTdewkUQEUTyEhkb45qE4JAnNPbxDap390C0dIlShSfiLJeYxfYJaIPeR9k8mVw7QJHHZIhYg8ap9Nm-iw', NULL, 'admin', '2020-06-14 18:38:44', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (72, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzMTMxMSwiY3JlYXRlZCI6MTU5MjEzMTMwMTAxMCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.rnX162CXn6d86SDJddIp4Na8NL_Le8Gd4HYW-H1t0O7htk7UOuKGAAVjrkoya_iF-PMkBllxXC1JVQ1KX14NkA', NULL, 'admin', '2020-06-14 18:41:41', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (73, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzMTM3OSwiY3JlYXRlZCI6MTU5MjEzMTM2OTI1NywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.4b7M1o0yT-OCunto8eTGQ8PvF4f385eBba-d1qveVyRig15AmdJxpAbWVlR7bt0bGjq4F4b39-QCHcTimlReTw', NULL, 'admin', '2020-06-14 18:42:49', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (74, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzMzg4MywiY3JlYXRlZCI6MTU5MjEzMzgzMzk4OSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.RnJ4AQQ1x7knom67jVyG5qBb9o9O87U0L_sPu8Ur2b6iR5DFKl9HKASnMCGh8GdR1z-f7WyECjk1m3qFARplKA', NULL, 'admin', '2020-06-14 19:23:54', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (75, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzNDU2NiwiY3JlYXRlZCI6MTU5MjEzNDUxNjI3MywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.7unWKD7K72a7ffs4zNU33wc30zEGyLUfBwzXWnMUo8EUhLC44e11hLrgvtkQ6GRBtI5jnvwm9MCSL13YcgKZCg', NULL, 'admin', '2020-06-14 19:35:16', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (76, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzNTE4MCwiY3JlYXRlZCI6MTU5MjEzNTEzMDM4OCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.OjlgpnB7oRfXAvjSgekCZX0aKTDijj0KLOGVSjQankH5SOrmmaaONCKmxFxv5UiQWYJwSYbPfPWrKDLjofXkig', NULL, 'admin', '2020-06-14 19:45:31', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (77, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzNTI5NywiY3JlYXRlZCI6MTU5MjEzNTI0Nzc2OCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.liZTkoP6Bb6YeP-NubMaNix3NqR3KAdH7t00pKNo5d5m3NGj94x7JYz71njeMqxWOErZKNGflhZG3dHaaGGf3A', NULL, 'admin', '2020-06-14 19:47:28', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (78, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzNTQ4MSwiY3JlYXRlZCI6MTU5MjEzNTQzMTg4OSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.kGUu6ZOvd7osn3fF6QsHNURL-pJeIaOuHY4l7Lsdpc6YsfFbMJaqbCbTjgplfHhHWlLdy7fRWJVi4Gq9cKahVw', NULL, 'admin', '2020-06-14 19:50:32', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (79, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzNTc3MiwiY3JlYXRlZCI6MTU5MjEzNTcyMjI1MiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.A-0V2zY9aIIXOo4cmCUGfYU2XOWsOqkKGEHjmKo3spUJ0_JJREgqh17ci9YFX3WqOZou5YpJkkUtjujURoUizA', NULL, 'admin', '2020-06-14 19:55:22', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (80, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzNTg3NSwiY3JlYXRlZCI6MTU5MjEzNTgyNTQwNSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.xontTZjm__gAngRIf0W2Ue6ziylHFzo6nGP4X_DWLjaHCSJ5wjiQhGx4kkwcrG0jMpp8U7S9HEFcI8dhuHW8_A', NULL, 'admin', '2020-06-14 19:57:05', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (81, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzNjAyOCwiY3JlYXRlZCI6MTU5MjEzNTk3ODcxNCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0._a8il2JjsonGOTC9HMumA6Yc39d8gOzx34ymTWYKX71PWBEvXGbA84O_9rFE1cZZjWokOPy5-dAExEedklO-0Q', NULL, 'admin', '2020-06-14 19:59:39', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (82, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzNjEwNywiY3JlYXRlZCI6MTU5MjEzNjA1NzU0NiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.6NRLBXf0PO8yIHtZPeNoTyIkMk5Qo8kt-W1teWee4r7rzklJlw5CHnJYyNebAh_-NyXf-hSVoIzIRc2w6wcMDw', NULL, 'admin', '2020-06-14 20:00:58', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (83, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzNjI0NSwiY3JlYXRlZCI6MTU5MjEzNjE5NTgzNiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.Mao7Z-Dx9ez7ZpHDEVDzS8WeqLenHao8v0xB4BygnBofFs96npNjS0QJNbxXibkAI4vJkdHZEQw1sHX1AKHUAQ', NULL, 'admin', '2020-06-14 20:03:16', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (84, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjEzNjM5MywiY3JlYXRlZCI6MTU5MjEzNjM0MzIyMiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.PIKv8dcaS2DRevyTjYN-HVSDTtNSV_muoIJcJg7ZesDyW1ltrSC2U7Em87qep7rBjITloPu39il1jQu6DaZwJA', NULL, 'admin', '2020-06-14 20:05:43', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (85, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MDI4NywiY3JlYXRlZCI6MTU5MjE0MDIzNzAyOCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.frnX8VWeHtN2JLEAK9w1GFmMPeHv4yzVBj2eYYHalW8UeKCRO8snOwrdOhul22FE8pVYHyf1DBE-fi1FPf14Tw', NULL, 'admin', '2020-06-14 21:10:37', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (86, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MDQxOSwiY3JlYXRlZCI6MTU5MjE0MDM2OTI2OSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.d0rWSzTHORawYWqESjrsKgTUCCuy1rUbiFJwY7xVcoGH5Egr6OLe44vyGoT_-uwetmSjO8-G3tjSHezonxNqeA', NULL, 'admin', '2020-06-14 21:12:49', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (87, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MDQ0NCwiY3JlYXRlZCI6MTU5MjE0MDM5NDAwNywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.sbIOgw12Om6l591A7qttvib0SXRz1KH5tn2s08mHZojfqIRvraF9MzAbq8D3GP0qte0X0MKfZOywbKSL7-mYwA', NULL, 'admin', '2020-06-14 21:13:14', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (88, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MDUxMiwiY3JlYXRlZCI6MTU5MjE0MDQ2MjY1OSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.FtdDW-Amr4F2of7YCWRN1yKRqNzmb4Zkfp7uOZOp0s4mkvJ8CyMgtwBRjDKAyuXNKk0Jsuv_HmqQmwg8i8XL6g', NULL, 'admin', '2020-06-14 21:14:23', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (89, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MDc1NCwiY3JlYXRlZCI6MTU5MjE0MDcwNDM4NiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.b6Yw_82AOL_Wx2lRQbz7XtjAxPt_kEOi-g6sNaaqh1ZlhbeB2VyU2QudUjYEOSDymHnGGxHBUn-X_eEb3AODGA', NULL, 'admin', '2020-06-14 21:18:24', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (90, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MDk2NSwiY3JlYXRlZCI6MTU5MjE0MDkxNTEyMiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.ikQMKmvKhtrnlPDck1YSKmQbuXsw-qkIlA3LxUftJi5NeIkNhIzOj9zpnBscTuG0JZcMXgR8Zbv2VRSJ9PkkNg', NULL, 'admin', '2020-06-14 21:21:55', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (91, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MTI3MiwiY3JlYXRlZCI6MTU5MjE0MTIyMjI5NiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.E-t6YX7OUq31QyBfzmIsUdL7tgGnNBBIHslrRfwNx_7q-WtaKzEmPIwm9n_vo62Kl-_oaTJtBff4Kb6d7LN6TA', NULL, 'admin', '2020-06-14 21:27:02', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (92, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MTY0MiwiY3JlYXRlZCI6MTU5MjE0MTU5Mjk4NSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.SgBHbIylALpkAzvVcNJpYnENnW2VKQ-IrdXTFkO19khy9Cn68QvLzH2mFUMlCKEsF46poli95oIMuB5UCjPBXg', NULL, 'admin', '2020-06-14 21:33:13', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (93, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MjAzNSwiY3JlYXRlZCI6MTU5MjE0MTk4NTM1OSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.u5KUzxEA0gBVctYAxtFhbZSQTSEk3hk33LbXJ4o3-S1kIPKTdvUrwHgTWhgRcDLAoqTr5_Hk0lOGSX9TJMbepw', NULL, 'admin', '2020-06-14 21:39:45', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (94, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MjIzMSwiY3JlYXRlZCI6MTU5MjE0MjE4MTUwMCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.54Ax7LT0N1OcAnu_DCsJDC97nWapqDoqXbdBudxzsSFCAtqAedE4_f6VriVmZb6_pxbmrTmdzNIqffUqTyMhlg', NULL, 'admin', '2020-06-14 21:43:02', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (95, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MjQ5MywiY3JlYXRlZCI6MTU5MjE0MjQ0MzIxMywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.2DNXWtH6uPhbWa4Is0l8kTw3DXs5EJGf7o8XS_4Hqef-abvBYTHKVuKjBkAOhrK7mYUynsuMdENSs2nk4U5IBQ', NULL, 'admin', '2020-06-14 21:47:23', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (96, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MjU4MSwiY3JlYXRlZCI6MTU5MjE0MjUzMTc4NSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.-7MfiMoK1-ex7jAyqxQ69PUrACODVtsWGEd2TJgxQEbuL52XM2ZAo5CtqVT05KwsQC4EMgNgV0ngVhpZnzmkuw', NULL, 'admin', '2020-06-14 21:48:52', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (97, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0Mjg2OSwiY3JlYXRlZCI6MTU5MjE0MjgxOTQwMywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.Z80RUQt0At_qDHJARbpxtgG6_6WaiW_dW6A1wxhRQtq25VFeMdIXizCS9NXPKFs-ja_8NvlJAEDBOY2-Aj4nCw', NULL, 'admin', '2020-06-14 21:53:39', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (98, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0Mjk3NCwiY3JlYXRlZCI6MTU5MjE0MjkyNDI2MSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.SnQYV9Bl2n-bKnQ79pdq6HsjIosnO1PgVhbPXLrrRtXaSQbwdOIKguo9TwgmjO-vnWwzxhfhz4JvSYlrMp5-eg', NULL, 'admin', '2020-06-14 21:55:24', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (99, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0MzAyNCwiY3JlYXRlZCI6MTU5MjE0Mjk3NDI0MCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.aV_cGyHR2fYegIEu4JftCHEBTtmp1-1-0nJT6yPYRgaWOw8NL6ynzfOQtfP7eWUr7ZQ52ooVvQESN7IvHqHtKA', NULL, 'admin', '2020-06-14 21:56:14', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (100, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NDM0OCwiY3JlYXRlZCI6MTU5MjE0NDI5ODk2OCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.PYIySp7GVR3ZqgDLT6OSmmNFxDCjNbfImZMqAJzrpzt-q73azVSMy3KG6gP96kQWjlDOit68YKbs7JhLDKTnLA', NULL, 'admin', '2020-06-14 22:18:19', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (101, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NDQyMywiY3JlYXRlZCI6MTU5MjE0NDM3MzEyMSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.DONXH9TqhNw0FSsols4Qmh1XZ4WvD1WD9XOHIFPGGndDRXjb8UVJymEWrHoLkK5ARPSpXHnv9sIz0GMmdcCtYw', NULL, 'admin', '2020-06-14 22:19:33', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (102, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NDQ4NywiY3JlYXRlZCI6MTU5MjE0NDQzNzY2NCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.5AmPj3ti-rJoSNg3RAaCQsQ77sc8F5-84FEMFeH7o_wgMUnbJBR3awB3tKxacWBPWq5GKdYNWhm55bGqOy9s_A', NULL, 'admin', '2020-06-14 22:20:38', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (103, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NDQ5OSwiY3JlYXRlZCI6MTU5MjE0NDQ0OTMyNSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.PTwxmX8k0UIS7_WpNKJc1BNTMgqWy08ukbr17SAJGZcz6jtrZ7QL8GLyFZaLUxJwJloxOqqPLZ5Czdhwdw6XgA', NULL, 'admin', '2020-06-14 22:20:49', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (104, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NDYzNCwiY3JlYXRlZCI6MTU5MjE0NDU4NDk1MCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.narTiktR_Z1C1JTKIv_0FXOKOj8fndZlMCUX16XcDVWloZVFO6r8FaJwVgJONY-tWW_j7vn6jLHPiq9FW2QPlA', NULL, 'admin', '2020-06-14 22:23:05', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (105, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NDY4OCwiY3JlYXRlZCI6MTU5MjE0NDYzODY1NCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.FJcJeivDrDFS7rqvq2Pt_AX15VCS_YIJUbdvUB8Cra2VfdXC2R2k9lre9ZbnykQ3a5pkSB8V-o4POWofIkJEMQ', NULL, 'admin', '2020-06-14 22:23:59', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (106, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NTUwNSwiY3JlYXRlZCI6MTU5MjE0NTQ1NTI1NSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.UJvw4e-AOG3SINVPuaAuEgJrHZUgOvSUrc_UoWqGkTbl7c_-KppnTJTQ42NMziWH0bisGwxYjeFkCvABt8rDnA', NULL, 'admin', '2020-06-14 22:37:35', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (107, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NTc4NSwiY3JlYXRlZCI6MTU5MjE0NTU0NTI1MywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.BILM_ng6x0grCrwl6DP41NWCvNzd05JDz2c1qpAKBvxr9rG7pCZ8qdVg6UKshw0SBBxwk8oomSenq9kbn05U6A', NULL, 'admin', '2020-06-14 22:39:05', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (108, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NjEwNiwiY3JlYXRlZCI6MTU5MjE0NTg2NjMzMywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.LubGIkc3hpQ396xl2IkU9ITkw1OF8zENHt3U__GAtZKZxbPvl3vTZJ7JHdnz34lOKyMOokKA5F2NHGZbinhkZw', NULL, 'admin', '2020-06-14 22:44:26', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (109, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NzA0MSwiY3JlYXRlZCI6MTU5MjE0NjgwMTc0NCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.jE2rgwdgX_mm1ECb0fah_Z0ppwklbBE9EEuH46zYlzdvFnX097f9ErnoUv4a4dntdt9-PchkotRiK-lJQl3Ikg', NULL, 'admin', '2020-06-14 23:00:02', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (110, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NzMxOSwiY3JlYXRlZCI6MTU5MjE0NzA3OTcxMywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.WqczD1nb7ADjWDrmxAwt28mQppQcFyWLXMwLjqm9lfxT1jRJqx9sHgjmVENG1g2IsYWcsPa1tnrwg-0QW22kaw', NULL, 'admin', '2020-06-14 23:04:40', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (111, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NzkyMCwiY3JlYXRlZCI6MTU5MjE0NzY4MDIyNywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.CTYIpEIlbjEMgNFu9nxwCu-bafNpWvgtcztUSFMa6W6qjkGKqgvbh68q_tfNJy9d1C02uIQvlA35rnBrzwtPOg', NULL, 'admin', '2020-06-14 23:14:40', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (112, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0NzkzNCwiY3JlYXRlZCI6MTU5MjE0NzY5NDMzOSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.yVy13jL_wQZwX-A90KbwVITRYw3JBEW-i2fXFGEGs-jfkJjqdt7KAdzL3pmuHUstijhaIDfgm9BApVPbfVse7w', NULL, 'admin', '2020-06-14 23:14:54', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (113, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0ODA0MywiY3JlYXRlZCI6MTU5MjE0NzgwMzUxNiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.pHjR_i2XQroCwFmh4WwEmq2sdWd72AtbJ_-j7GLH3jKDmtwVzElYDnx_CNuv7xjMeRHpXSNd6MxGpdszggP4aQ', NULL, 'admin', '2020-06-14 23:16:44', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (114, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0ODE5NCwiY3JlYXRlZCI6MTU5MjE0Nzk1NDcwNywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.xrKPadcWvR-Nf5O0fCoxhOX5KnqDCcM8-wQciM4c6DVN34ib7SC2wciOICdbBz8k860JM9CjWKbgkMyFbbmAIQ', NULL, 'admin', '2020-06-14 23:19:15', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (115, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0ODU2MywiY3JlYXRlZCI6MTU5MjE0ODMyMzQyOCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.VW2fdkeorYgjNT6EkjOscE6NkUr-dI-4eXn1hLo_vD1wa3b9Bi9FfhO3bjdZ1w0oVHt0HhIYiVYIEJSiSgrJ7g', NULL, 'admin', '2020-06-14 23:25:23', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (116, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE0OTE5MywiY3JlYXRlZCI6MTU5MjE0ODg5MzQ3OSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.X4JxSL_ZoqsSvvS35DEUtZIBdvg7nN_XyZBzRWWJmnroZ2ENfgh5NHXoyiLy-mCtf0QW0r_w40sRf76g9MCdWg', NULL, 'admin', '2020-06-14 23:34:54', NULL, NULL);
INSERT INTO `sys_user_token` VALUES (117, 2, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU5MjE1MDEwOSwiY3JlYXRlZCI6MTU5MjE0OTgwOTc5MCwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifSx7ImF1dGhvcml0eSI6ImRlbGV0ZSJ9XX0.Y5sGEoU_v1WQG8wzBpOZrf1rRqB0HSMMwD9XP9phCMzJ2JRUYVE5NCDofP3BUT0ZtnUrtVDKPrpwShLrW_n_vw', NULL, 'admin', '2020-06-14 23:50:10', NULL, NULL);

-- ----------------------------
-- Function structure for currval
-- ----------------------------
DROP FUNCTION IF EXISTS `currval`;
delimiter ;;
CREATE FUNCTION `currval`(seq_name VARCHAR(50))
 RETURNS int
  DETERMINISTIC
BEGIN  
    DECLARE VALUE INTEGER;  
    SET VALUE = 0;  
    SELECT current_val INTO VALUE FROM sequence WHERE seq_name = seq_name;  
    RETURN VALUE;  
END
;;
delimiter ;

-- ----------------------------
-- Function structure for findNextFormatSeqVal
-- ----------------------------
DROP FUNCTION IF EXISTS `findNextFormatSeqVal`;
delimiter ;;
CREATE FUNCTION `findNextFormatSeqVal`(prefix VARCHAR(30), seq_name VARCHAR(50))
 RETURNS varchar(45) CHARSET utf8mb4
  DETERMINISTIC
RETURN (CONCAT(prefix,DATE_FORMAT(now(),'%Y%m%d'), LPAD(nextval(seq_name), 5, 0)))
;;
delimiter ;

-- ----------------------------
-- Function structure for nextval
-- ----------------------------
DROP FUNCTION IF EXISTS `nextval`;
delimiter ;;
CREATE FUNCTION `nextval`(seq_name VARCHAR(50))
 RETURNS int
  DETERMINISTIC
BEGIN  
    UPDATE sequence SET current_val = current_val + increment_val WHERE `seq_name` = seq_name;  
    RETURN currval(seq_name);  
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
