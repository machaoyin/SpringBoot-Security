/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : springbootsecurity

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2020-10-11 19:22:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `idx` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsmeouyvuytpgxnligmevgetge` (`parent_id`),
  CONSTRAINT `FKsmeouyvuytpgxnligmevgetge` FOREIGN KEY (`parent_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'ROLE_SYSTEM', '1', '管理员', null);
INSERT INTO `sys_role` VALUES ('2', 'ROLE_USER', '2', '用户', null);
INSERT INTO `sys_role` VALUES ('3', 'ROLE_CSYH', '3', '测试用户', null);
INSERT INTO `sys_role` VALUES ('39', null, '1', '系统设置', '1');
INSERT INTO `sys_role` VALUES ('40', null, '1', '用户管理', '1');
INSERT INTO `sys_role` VALUES ('41', null, '2', '菜单管理', '1');
INSERT INTO `sys_role` VALUES ('42', null, '3', '角色管理', '1');
INSERT INTO `sys_role` VALUES ('43', null, '2', '测试菜单', '1');
INSERT INTO `sys_role` VALUES ('44', null, '1', '测试子菜单', '1');
INSERT INTO `sys_role` VALUES ('45', null, '2', '测试子菜单2', '1');
INSERT INTO `sys_role` VALUES ('46', null, '3', '测试菜单1', '1');
INSERT INTO `sys_role` VALUES ('47', null, '4', '测试菜单2', '1');
INSERT INTO `sys_role` VALUES ('48', null, '5', '测试菜单3', '1');
INSERT INTO `sys_role` VALUES ('49', null, '2', '测试菜单', '2');
INSERT INTO `sys_role` VALUES ('50', null, '1', '测试子菜单', '2');
INSERT INTO `sys_role` VALUES ('51', null, '2', '测试子菜单2', '2');
INSERT INTO `sys_role` VALUES ('52', null, '3', '测试菜单1', '2');
INSERT INTO `sys_role` VALUES ('94', null, '2', '测试菜单', '3');
INSERT INTO `sys_role` VALUES ('95', null, '1', '测试子菜单', '3');
INSERT INTO `sys_role` VALUES ('96', null, '2', '测试子菜单2', '3');
INSERT INTO `sys_role` VALUES ('97', null, '3', '测试菜单1', '3');
INSERT INTO `sys_role` VALUES ('98', null, '4', '测试菜单2', '3');
INSERT INTO `sys_role` VALUES ('99', null, '5', '测试菜单3', '3');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_51bvuyvihefoh4kp5syh2jpi4` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('2', '湖北宜昌', '张三', '$2a$10$MR7oaMH0rExBdyi3yjfvzurUCmVQ8e8Usj5RzrwORCTGyAxB.Mye2', 'admin');
INSERT INTO `sys_user` VALUES ('4', '', '管理员', '$2a$10$s6ILBXUjJcJlAuhn/.1amuGGP0D2JzlQ5yDemV3YLcyPxVZf3jZk.', 'admin1');
INSERT INTO `sys_user` VALUES ('5', '', '用户', '$2a$10$WRfaymcYU6Qn3IfUrpZ8teeswobhanCtImg5YaGyQw97AQDAAf1gm', 'admin2');
INSERT INTO `sys_user` VALUES ('6', '', '测试用户', '$2a$10$5B5AvZP1eFgX7WaUOhWIxe2cZzrcKgW5F8T/TfTPDI8crO98FVO2C', 'admin3');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  KEY `FKb40xxfch70f5qnyfw8yme1n1s` (`user_id`),
  CONSTRAINT `FKb40xxfch70f5qnyfw8yme1n1s` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('2', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2');
INSERT INTO `sys_user_role` VALUES ('4', '1');
INSERT INTO `sys_user_role` VALUES ('5', '2');
INSERT INTO `sys_user_role` VALUES ('6', '3');

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) DEFAULT NULL,
  `idx` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf6cync8rfoqw0tl3s3q8s1les` (`parent_id`),
  CONSTRAINT `FKf6cync8rfoqw0tl3s3q8s1les` FOREIGN KEY (`parent_id`) REFERENCES `tb_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES ('1', null, '1', '系统设置', '', null);
INSERT INTO `tb_menu` VALUES ('2', null, '1', '用户管理', 'user/manage', '1');
INSERT INTO `tb_menu` VALUES ('3', null, '2', '菜单管理', 'menu/manage', '1');
INSERT INTO `tb_menu` VALUES ('4', null, '3', '角色管理', 'role/manage', '1');
INSERT INTO `tb_menu` VALUES ('7', null, '2', '测试菜单', '', null);
INSERT INTO `tb_menu` VALUES ('8', null, '1', '测试子菜单', '', '7');
INSERT INTO `tb_menu` VALUES ('9', null, '2', '测试子菜单2', '', '7');
INSERT INTO `tb_menu` VALUES ('10', null, '3', '测试菜单1', '', null);
INSERT INTO `tb_menu` VALUES ('11', null, '4', '测试菜单2', '', null);
INSERT INTO `tb_menu` VALUES ('12', null, '5', '测试菜单3', '', null);
