/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : video

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-06-18 22:14:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bgm
-- ----------------------------
DROP TABLE IF EXISTS `bgm`;
CREATE TABLE `bgm` (
  `id` varchar(64) NOT NULL,
  `author` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL COMMENT '播放地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of bgm
-- ----------------------------
INSERT INTO `bgm` VALUES ('18052674D26HH32P', 'Dragon', 'Dragon 全部都是你', '\\bgm\\Dragon 全部都是你.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH33P', 'Nigel', 'Nigel Silin Sakura Tears', '\\bgm\\Nigel Silin Sakura Tears.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH34P', '陈小云', '陈小云 爱情骗子我问你', '\\bgm\\陈小云 爱情骗子我问你.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH35P', '张晓胖', '张晓胖 哒哒哒哒', '\\bgm\\张晓胖 哒哒哒哒.mp3');
INSERT INTO `bgm` VALUES ('18052674D26HH36P', '鞠文娴', '鞠文娴 BINGBIAN病变(Remix)', '\\bgm\\鞠文娴 BINGBIAN病变(Remix).mp3');

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id` varchar(20) NOT NULL,
  `father_comment_id` varchar(20) DEFAULT NULL,
  `to_user_id` varchar(20) DEFAULT NULL,
  `video_id` varchar(20) NOT NULL COMMENT '视频id',
  `from_user_id` varchar(20) NOT NULL COMMENT '留言者，评论的用户id',
  `comment` text NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程评论表';

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES ('180614F3MSNCTWPH', 'undefined', 'undefined', '180613FCKZZNYHSW', '180612G6XY3XCXD4', '测试测试', '2018-06-14 19:48:00');
INSERT INTO `comments` VALUES ('180614F5GRFB84X4', 'undefined', 'undefined', '180613FCKZZNYHSW', '180612G6XY3XCXD4', '我就是测试', '2018-06-14 19:53:41');
INSERT INTO `comments` VALUES ('180614FC6T2RPGMW', '180614F5GRFB84X4', '180612G6XY3XCXD4', '180613FCKZZNYHSW', '180612G6XY3XCXD4', '知道啦', '2018-06-14 20:13:45');
INSERT INTO `comments` VALUES ('180614FF2545TCM8', 'undefined', 'undefined', '180613F51DZ6F1S8', '180612G6XY3XCXD4', '好喜欢的音乐', '2018-06-14 20:19:15');
INSERT INTO `comments` VALUES ('180614G4B143391P', 'undefined', 'undefined', '180613FCABYKAFW0', '180612G6XY3XCXD4', '你好', '2018-06-14 21:14:15');

-- ----------------------------
-- Table structure for search_records
-- ----------------------------
DROP TABLE IF EXISTS `search_records`;
CREATE TABLE `search_records` (
  `id` varchar(64) NOT NULL,
  `content` varchar(255) NOT NULL COMMENT '搜索的内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频搜索的记录表';

-- ----------------------------
-- Records of search_records
-- ----------------------------
INSERT INTO `search_records` VALUES ('1', '妹子');
INSERT INTO `search_records` VALUES ('18051309YBCMHYRP', '风景');
INSERT INTO `search_records` VALUES ('1805130DAXX58ARP', '风景');
INSERT INTO `search_records` VALUES ('1805130DMG6P0ZC0', '风景');
INSERT INTO `search_records` VALUES ('1805130FNGHD3B0H', '妹子');
INSERT INTO `search_records` VALUES ('180513C94W152Z7C', '可爱');
INSERT INTO `search_records` VALUES ('180513DXNT7SY04H', '风景');
INSERT INTO `search_records` VALUES ('180613GY3XNT2MRP', '这样');
INSERT INTO `search_records` VALUES ('180613GYDD8F3R40', '测试');
INSERT INTO `search_records` VALUES ('180613GZ0ZDWGSRP', '美女');
INSERT INTO `search_records` VALUES ('180613H7BW3ZRD40', '美女');
INSERT INTO `search_records` VALUES ('180613H7D6WAA98H', '美女');
INSERT INTO `search_records` VALUES ('2', '妹子');
INSERT INTO `search_records` VALUES ('3', '妹子');
INSERT INTO `search_records` VALUES ('4', '妹子');
INSERT INTO `search_records` VALUES ('5', '美女');
INSERT INTO `search_records` VALUES ('6', '美女');
INSERT INTO `search_records` VALUES ('7', '萝莉');
INSERT INTO `search_records` VALUES ('8', '萝莉');
INSERT INTO `search_records` VALUES ('9', '萝莉');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` varchar(64) NOT NULL,
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `face_image` varchar(255) DEFAULT NULL COMMENT '我的头像，如果没有默认给一张',
  `nickname` varchar(20) NOT NULL COMMENT '昵称',
  `fans_counts` int(11) DEFAULT '0' COMMENT '我的粉丝数量',
  `follow_counts` int(11) DEFAULT '0' COMMENT '我关注的人总数',
  `receive_like_counts` int(11) DEFAULT '0' COMMENT '我接受到的赞美/收藏 的数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('180612G6XY3XCXD4', '一叶方舟', 'e10adc3949ba59abbe56e057f20f883e', '/180612G6XY3XCXD4/face/tmp_4e312187fdb2d2908f1a9f2738f70ff493e2532b3da93171.jpg', '一叶方舟', '6', '9', '8');
INSERT INTO `users` VALUES ('180613F5X598S4BC', '美女', 'e10adc3949ba59abbe56e057f20f883e', '/180613F5X598S4BC/face/tmp_e5282b4586db54e1246bb5258c598bf20a3747bfae6d620a.jpg', '美女', '0', '0', '0');
INSERT INTO `users` VALUES ('180613F5Z5SK4PX4', '校花', 'e10adc3949ba59abbe56e057f20f883e', '/180613F5Z5SK4PX4/face/tmp_255f5856ab22d8f945ee67d795eec16e94c1107b743ce2fc.jpg', '校花', '0', '0', '1');

-- ----------------------------
-- Table structure for users_fans
-- ----------------------------
DROP TABLE IF EXISTS `users_fans`;
CREATE TABLE `users_fans` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL COMMENT '用户',
  `fan_id` varchar(64) NOT NULL COMMENT '粉丝',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`fan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户粉丝关联关系表';

-- ----------------------------
-- Records of users_fans
-- ----------------------------
INSERT INTO `users_fans` VALUES ('180614G486S71N9P', '180612G6XY3XCXD4', '180612G6XY3XCXD4');

-- ----------------------------
-- Table structure for users_like_videos
-- ----------------------------
DROP TABLE IF EXISTS `users_like_videos`;
CREATE TABLE `users_like_videos` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL COMMENT '用户',
  `video_id` varchar(64) NOT NULL COMMENT '视频',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_video_rel` (`user_id`,`video_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户喜欢的/赞过的视频';

-- ----------------------------
-- Records of users_like_videos
-- ----------------------------
INSERT INTO `users_like_videos` VALUES ('180520HBA054FPPH', '180518CKMAAM5TXP', '180510CCX05TABHH');
INSERT INTO `users_like_videos` VALUES ('180520HSBDW6M0SW', '180518CKMAAM5TXP', '180510CD0A6K3SRP');
INSERT INTO `users_like_videos` VALUES ('180614DZDN3KP7TC', '180612G6XY3XCXD4', '180613F51DZ6F1S8');
INSERT INTO `users_like_videos` VALUES ('180614G8M4FD59WH', '180612G6XY3XCXD4', '180613FCABYKAFW0');
INSERT INTO `users_like_videos` VALUES ('180614DZ78BSZDYW', '180612G6XY3XCXD4', '180613FCKZZNYHSW');

-- ----------------------------
-- Table structure for users_report
-- ----------------------------
DROP TABLE IF EXISTS `users_report`;
CREATE TABLE `users_report` (
  `id` varchar(64) NOT NULL,
  `deal_user_id` varchar(64) NOT NULL COMMENT '被举报用户id',
  `deal_video_id` varchar(64) NOT NULL,
  `title` varchar(128) NOT NULL COMMENT '类型标题，让用户选择，详情见 枚举',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `userid` varchar(64) NOT NULL COMMENT '举报人的id',
  `create_date` datetime NOT NULL COMMENT '举报时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='举报用户表';

-- ----------------------------
-- Records of users_report
-- ----------------------------
INSERT INTO `users_report` VALUES ('180521FZ501ABDYW', '180425CFA4RB6T0H', '180510CD0A6K3SRP', '引人不适', '不合时宜的视频', '180518CKMAAM5TXP', '2018-05-21 20:58:35');
INSERT INTO `users_report` VALUES ('180521FZK44ZRWX4', '180425CFA4RB6T0H', '180510CD0A6K3SRP', '引人不适', '不合时宜的视频', '180518CKMAAM5TXP', '2018-05-21 20:59:53');
INSERT INTO `users_report` VALUES ('180521FZR1TYRTXP', '180425CFA4RB6T0H', '180510CD0A6K3SRP', '辱骂谩骂', 't4t43', '180518CKMAAM5TXP', '2018-05-21 21:00:18');

-- ----------------------------
-- Table structure for videos
-- ----------------------------
DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL COMMENT '发布者id',
  `audio_id` varchar(64) DEFAULT NULL COMMENT '用户使用音频的信息',
  `video_desc` varchar(128) DEFAULT NULL COMMENT '视频描述',
  `video_path` varchar(255) NOT NULL COMMENT '视频存放的路径',
  `video_seconds` float(6,2) DEFAULT NULL COMMENT '视频秒数',
  `video_width` int(6) DEFAULT NULL COMMENT '视频宽度',
  `video_height` int(6) DEFAULT NULL COMMENT '视频高度',
  `cover_path` varchar(255) DEFAULT NULL COMMENT '视频封面图',
  `like_counts` bigint(20) NOT NULL DEFAULT '0' COMMENT '喜欢/赞美的数量',
  `status` int(1) NOT NULL COMMENT '视频状态：\r\n1、发布成功\r\n2、禁止播放，管理员操作',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频信息表';

-- ----------------------------
-- Records of videos
-- ----------------------------
INSERT INTO `videos` VALUES ('180613F4TP1RPNHH', '180612G6XY3XCXD4', '18052674D26HH32P', '美女', '/180612G6XY3XCXD4/video/fe13fa1ffa99421a805c123ad74cb863.mp4', '9.00', '272', '480', '/180612G6XY3XCXD4/video/fe13fa1ffa99421a805c123ad74cb863.jpg', '0', '1', '2018-06-13 19:51:32');
INSERT INTO `videos` VALUES ('180613F51DZ6F1S8', '180612G6XY3XCXD4', '18052674D26HH33P', '', '/180612G6XY3XCXD4/video/6e7f3b4a16e24d02940394d5b10cce27.mp4', '4.00', '272', '544', '/180612G6XY3XCXD4/video/6e7f3b4a16e24d02940394d5b10cce27.jpg', '1', '1', '2018-06-13 19:52:09');
INSERT INTO `videos` VALUES ('180613F6GWXGWWDP', '180613F5Z5SK4PX4', '18052674D26HH35P', '视频测试', '/180613F5Z5SK4PX4/video/12fad3d48ec044ab9b8c0147fc3cfcbc.mp4', '15.00', '368', '640', '/180613F5Z5SK4PX4/video/12fad3d48ec044ab9b8c0147fc3cfcbc.jpg', '0', '1', '2018-06-13 19:56:42');
INSERT INTO `videos` VALUES ('180613FBWH1M7MW0', '180613F5X598S4BC', '18052674D26HH34P', '', '/180613F5X598S4BC/video/9b84f90fcc114bdf8287610f7facad5f.mp4', '9.00', '272', '480', '/180613F5X598S4BC/video/9b84f90fcc114bdf8287610f7facad5f.jpg', '0', '1', '2018-06-13 20:12:39');
INSERT INTO `videos` VALUES ('180613FC3W83WHM8', '180613F5X598S4BC', '', '抖音', '/180613F5X598S4BC/video/6e7f3b4a16e24d02940394d5b10cce27.mp4', '15.00', '272', '480', '/180612G6XY3XCXD4/video/6e7f3b4a16e24d02940394d5b10cce27.jpg', '0', '1', '2018-06-13 20:13:25');
INSERT INTO `videos` VALUES ('180613FCABYKAFW0', '180612G6XY3XCXD4', '', '就是测试呀', '/180612G6XY3XCXD4/video/6e7f3b4a16e24d02940394d5b10cce27.mp4', '10.00', '368', '640', '/180612G6XY3XCXD4/video/6e7f3b4a16e24d02940394d5b10cce27.jpg', '1', '1', '2018-06-13 20:14:08');
INSERT INTO `videos` VALUES ('180613FCKZZNYHSW', '180613F5Z5SK4PX4', '18052674D26HH35P', '是不是这样', '/180613F5Z5SK4PX4/video/bcdb1819d14e468d832bb1166e9de1e3.mp4', '15.00', '368', '640', '/180613F5Z5SK4PX4/video/bcdb1819d14e468d832bb1166e9de1e3.jpg', '1', '1', '2018-06-13 20:14:56');
INSERT INTO `videos` VALUES ('180614GFXT75XT7C', '180612G6XY3XCXD4', '18052674D26HH32P', '测试视频', '/180612G6XY3XCXD4/video/eb8a485f5bf3473492557426123afad6.mp4', '15.00', '640', '368', '/180612G6XY3XCXD4/video/eb8a485f5bf3473492557426123afad6.jpg', '0', '1', '2018-06-14 21:45:51');
