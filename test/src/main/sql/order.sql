/*
 Navicat Premium Data Transfer

 Source Server         : @docker
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : 127.0.0.1:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 14/10/2020 11:29:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
                          `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
                          `order_no` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                          `buyer_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                          `seller_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                          `amount` double(255, 0) NULL DEFAULT NULL,
                          `create_time` datetime(0) NULL DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 'O-00001', 'user_1', 'kiven', 1000, '2020-10-14 10:01:57');
INSERT INTO `order` VALUES (2, 'O-00002', 'user_1', 'lisa', 999, '2020-10-14 11:14:24');

SET FOREIGN_KEY_CHECKS = 1;
