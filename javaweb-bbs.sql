/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : javaweb-bbs

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 24/03/2022 00:13:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_article
-- ----------------------------
DROP TABLE IF EXISTS `sys_article`;
CREATE TABLE `sys_article` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `user_id` int NOT NULL COMMENT '用户ID',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `author` varchar(16) NOT NULL COMMENT '作者',
  `content` longtext NOT NULL COMMENT '文章内容',
  `publish_date` datetime NOT NULL COMMENT '发布时间',
  `click_count` int unsigned NOT NULL DEFAULT '0' COMMENT '文章点击数量',
  PRIMARY KEY (`id`),
  KEY `index_title` (`title`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100007 DEFAULT CHARSET=utf8mb3 COMMENT='系统文章表';

-- ----------------------------
-- Records of sys_article
-- ----------------------------
BEGIN;
INSERT INTO `sys_article` (`id`, `user_id`, `title`, `author`, `content`, `publish_date`, `click_count`) VALUES (100000, 1, '东部战区陆军：丢掉幻想，准备打仗！', 'admin', '<p style=\"font-family:PingFangSC-Regular, 微软雅黑, STXihei, Verdana, Calibri, Helvetica, Arial, sans-serif;font-size:16px;text-indent:32px;background-color:#FFFFFF;\">\n	中国人民解放军东部战区陆军微信公众号“人民前线”4月15日发布《丢掉幻想，准备打仗！ 》，以下为文章全文：\n</p>\n<p style=\"font-family:PingFangSC-Regular, 微软雅黑, STXihei, Verdana, Calibri, Helvetica, Arial, sans-serif;font-size:16px;text-indent:32px;background-color:#FFFFFF;\">\n	文丨陈前线\n</p>\n<p style=\"font-family:PingFangSC-Regular, 微软雅黑, STXihei, Verdana, Calibri, Helvetica, Arial, sans-serif;font-size:16px;text-indent:32px;background-color:#FFFFFF;\">\n	“丢掉幻想，准备斗争！”\n</p>\n<p style=\"font-family:PingFangSC-Regular, 微软雅黑, STXihei, Verdana, Calibri, Helvetica, Arial, sans-serif;font-size:16px;text-indent:32px;background-color:#FFFFFF;\">\n	这是新中国成立前夕，毛主席发表的一篇文章标题。\n</p>\n<p style=\"font-family:PingFangSC-Regular, 微软雅黑, STXihei, Verdana, Calibri, Helvetica, Arial, sans-serif;font-size:16px;text-indent:32px;background-color:#FFFFFF;\">\n	毛主席曾说过： “我们爱好和平，但以斗争求和平则和平存，以妥协求和平则和平亡。 ”\n</p>\n<p style=\"font-family:PingFangSC-Regular, 微软雅黑, STXihei, Verdana, Calibri, Helvetica, Arial, sans-serif;font-size:16px;text-indent:32px;background-color:#FFFFFF;text-align:center;\">\n	<img src=\"/res/images/20200415203823695.jpg\" />\n</p>\n<p style=\"font-family:PingFangSC-Regular, 微软雅黑, STXihei, Verdana, Calibri, Helvetica, Arial, sans-serif;font-size:16px;text-indent:32px;background-color:#FFFFFF;\">\n	放眼今日之中国，九州大地上热潮迭涌。 在中国梦的指引下，华夏儿女投身祖国各项建设事业，追赶新时代发展的脚步。 中国在国际上的影响力显著增强，“向东看”开始成为一股潮流。\n</p>', '2020-04-19 17:35:06', 56434);
INSERT INTO `sys_article` (`id`, `user_id`, `title`, `author`, `content`, `publish_date`, `click_count`) VALUES (100001, 1, '面对战争，时刻准备着！', 'admin', '<p style=\"font-family:&quot;font-size:16px;background-color:#FFFFFF;text-align:justify;\">\n	这话是20年前，我的新兵连长说的。\n</p>\n<p style=\"font-family:&quot;font-size:16px;background-color:#FFFFFF;text-align:justify;\">\n	&emsp;&emsp;那是我们授衔后的第一个晚上，班长一脸神秘地说：“按照惯例，今天晚上肯定要紧急集合的，这是你们的‘成人礼’。”于是，熄灯哨音响过之后，我们都衣不解带地躺在床上。班长为了所谓的班级荣誉，也默认了我们的做法。\n</p>\n<p style=\"font-family:&quot;font-size:16px;background-color:#FFFFFF;text-align:justify;\">\n	&emsp;&emsp;果然，深夜一阵急促的哨音响起，我们迅速打起被包，冲到指定地点集合。大个子连长看着整齐的队伍，说了句：“不错，解散!”一个皆大欢喜的局面。我们都高高兴兴地回到宿舍，紧绷的神经一下子放松下来，排房里很快就响起了呼噜声。\n</p>\n<p align=\"center\" style=\"font-family:&quot;font-size:16px;background-color:#FFFFFF;\">\n	<img src=\"/res/images/20200419133156232.jpg\" alt=\"500\" />\n</p>\n<p style=\"font-family:&quot;font-size:16px;background-color:#FFFFFF;text-align:justify;\">\n	&emsp;&emsp;可是，令人没有想到的是，睡梦中又一阵急促的哨音划破夜空的宁静——连长再次拉起了紧急集合。这一次，情况就完全不一样了，毫无准备的我们，狼狈不堪，有的被包来不及打好，不得不用手抱住;有的找不到自己的鞋子，光脚站在地上，有的甚至连裤子都穿反了……\n</p>', '2020-04-19 17:37:40', 14343);
INSERT INTO `sys_article` (`id`, `user_id`, `title`, `author`, `content`, `publish_date`, `click_count`) VALUES (100002, 5, '东部战区，开训！', 'rasp', '<p>1月4日，习主席签署中央军委2022年1号命令：“向全军发布开训动员令，号令如山，训练场上风雷动，士气如虹，强军路上勇争先”。东部战区座座军营，迅速掀起练兵备战热潮。</p>\n<p>1月4日，第71集团军王杰部队组织2022年开训动员，数千名官兵全副武装，整齐列队、士气高涨，拉开了新年度练兵备战的序幕，为新年度军事训练工作开好头、起好步奠定坚实基础。</p>\n<p align=\"center\" style=\"font-family:&quot;font-size:16px;background-color:#FFFFFF;\">\n	<img src=\"/res/images/1648050701642.jpg\" alt=\"500\" />\n</p>', '2022-01-05 10:02:32', 151442);
INSERT INTO `sys_article` (`id`, `user_id`, `title`, `author`, `content`, `publish_date`, `click_count`) VALUES (100003, 2, '大学毕业直接当军官！东部战区陆军欢迎你！', 'test', '<p>临近毕业，你是否彷徨在迷茫的十字路口不知该何去何从？如果你有一颗报国之心，如果你有一腔从军之志，别犹豫，来军营吧！穿上军装，手握钢枪，你会拥有，一种至高的荣耀，一种独特的气质，一个无悔的选择！  </p>\n<p align=\"center\" style=\"font-family:&quot;font-size:16px;background-color:#FFFFFF;\">\n	<img src=\"/res/images/1648005267458.jpg\" alt=\"500\" />\n</p>\n<p>搏击骇浪长空，征战荒漠戈壁，感受从未有过的体验，体会从未有过的荣耀、使命和担当！</p>', '2022-03-18 21:49:40', 45454);
INSERT INTO `sys_article` (`id`, `user_id`, `title`, `author`, `content`, `publish_date`, `click_count`) VALUES (100004, 4, '南部战区组织兵力赶赴现场救援', 'user', '<h5>南部战区组织兵力赶赴东航坠机现场救援</h5>\n<p>南部战区迅速启动应急机制，梧州军分区、武警梧州支队第一时间组织救援力量赶赴事故现场展开救援。陆军某陆航旅、某合成旅、某空中突击旅，空军某运搜旅，武警驻事发地周边力量等迅速完成备勤，随时准备支援。事故地点三面环山 进入山坳只有一条小路据广西梧州市消防救援支队政治委员欧灵介绍，客机失事地点是三面环山的山坳，现场没有通电，进入山坳只有一条小路。</p>\n<p>记者在现场看到，为了方便大型救援机械设备进出，有关部门临时在该高速路上开了个缺口，正在往现场运送救援及补给物资。</p>', '2022-03-21 22:57:33', 64952);
INSERT INTO `sys_article` (`id`, `user_id`, `title`, `author`, `content`, `publish_date`, `click_count`) VALUES (100005, 5, '美国给乌克兰“秘密杀器”', 'rasp', '<p>乌克兰上空的争夺日益激烈！为压制俄罗斯的制空权，美国秘密出手了，要把这些年通过强取豪夺获得的“苏联的防空导弹”运往乌克兰。</p>\n<p align=\"center\" style=\"font-family:&quot;font-size:16px;background-color:#FFFFFF;\">\n	<img src=\"/res/images/1648051247006.jpg\" alt=\"500\" />\n</p>\n<p>“多年来，美国用俘获、抢夺以及其他方式获得了各种前苏联时期制造的防空系统，现在他们可能在乌克兰作战”，据美国“战区”网透露出一惊人消息。</p>\n<p>此前，美国往乌克兰运送的多是肩扛式的单兵防空导弹，威力有限。现在，美国干涉俄乌战争的行动升级了。</p>\n<p>据报道，美国政府正在向乌克兰运送其拥有的苏联时代的防空系统。多年来，美国军方和情报部门以各种方式获得了这些防空系统，用于情报分析和军事训练。</p>\n<p>现在，这些存在美国库房里的防空系统，将成为“乌克兰急需的额外防空能力”的有用来源。</p>', '2022-03-23 13:30:14', 343585);
INSERT INTO `sys_article` (`id`, `user_id`, `title`, `author`, `content`, `publish_date`, `click_count`) VALUES (100006, 3, '穿上星空迷彩，中部战区部队春季第一批入伍新兵来了！', 'root', '<p>参军报国立壮志，风华正茂正当时，在万事可期充满希望的季节里，一批批有志青年英姿勃发、士气昂扬、携笔从戎、奔赴军营。他们像颗颗火种奔向曙色荣光，迈开他们军旅生涯第一步，开启人生新征程！<p>\n<p align=\"center\" style=\"font-family:&quot;font-size:16px;background-color:#FFFFFF;\">\n	<img src=\"/res/images/1648049798502.jpg\" alt=\"500\" />\n</p>\n<p>近日，中部战区部队迎来了2022年度春季第一批入伍新兵，值得注意的是，这批新兵都穿上了刚配发的21式星空迷彩，新战友们更显朝气蓬勃、英姿飒爽！</p>', '2022-03-23 23:34:36', 245968);
COMMIT;

-- ----------------------------
-- Table structure for sys_comment
-- ----------------------------
DROP TABLE IF EXISTS `sys_comment`;
CREATE TABLE `sys_comment` (
  `comment_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '评论Id',
  `comment_article_id` int unsigned NOT NULL DEFAULT '0' COMMENT '评论的文章Id',
  `comment_user_id` int unsigned NOT NULL DEFAULT '0' COMMENT '评论的用户Id(未登录的用户Id为0)',
  `comment_author` varchar(16) NOT NULL COMMENT '评论者名称',
  `comment_content` text NOT NULL COMMENT '评论内容',
  `comment_date` datetime NOT NULL COMMENT '评论发布时间',
  PRIMARY KEY (`comment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of sys_comment
-- ----------------------------
BEGIN;
INSERT INTO `sys_comment` (`comment_id`, `comment_article_id`, `comment_user_id`, `comment_author`, `comment_content`, `comment_date`) VALUES (1, 100000, 1, 'admin', '<a href=\"download.php?fileName=../../../../../../../../../../../../../../etc/passwd\">任意文件下载测试</a>', '2021-06-16 10:44:55');
INSERT INTO `sys_comment` (`comment_id`, `comment_article_id`, `comment_user_id`, `comment_author`, `comment_content`, `comment_date`) VALUES (2, 100000, 1, 'admin', '测试', '2022-03-23 11:11:11');
INSERT INTO `sys_comment` (`comment_id`, `comment_article_id`, `comment_user_id`, `comment_author`, `comment_content`, `comment_date`) VALUES (3, 100005, 2, 'test', '反对战争，霸权主义！', '2022-03-24 01:11:11');
COMMIT;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `config_key` varchar(255) NOT NULL COMMENT '键',
  `config_value` text COMMENT '值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `config_key` (`config_key`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COMMENT='系统配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` (`id`, `config_key`, `config_value`) VALUES (1, 'sys_website_name', 'RASP 漏洞测试平台');
INSERT INTO `sys_config` (`id`, `config_key`, `config_value`) VALUES (2, 'sys_base_url', 'http://localhost:8000/');
INSERT INTO `sys_config` (`id`, `config_key`, `config_value`) VALUES (3, 'sys_index_page', 'index.php');
INSERT INTO `sys_config` (`id`, `config_key`, `config_value`) VALUES (4, 'sys_aliases_name', 'XXX');
INSERT INTO `sys_config` (`id`, `config_key`, `config_value`) VALUES (6, 'sys_website_description', 'RASP 漏洞测试平台');
INSERT INTO `sys_config` (`id`, `config_key`, `config_value`) VALUES (7, 'sys_version', 'javaweb-vuln 1.0.0');
INSERT INTO `sys_config` (`id`, `config_key`, `config_value`) VALUES (8, 'sys_admin_email', 'admin@javaweb.org');
INSERT INTO `sys_config` (`id`, `config_key`, `config_value`) VALUES (9, 'sys_icp_num', '京B2-XXXXXX-1');
INSERT INTO `sys_config` (`id`, `config_key`, `config_value`) VALUES (10, 'sys_website_keywords', 'Java漏洞测试靶场');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(16) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '用户密码',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `user_avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_user_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `username`, `password`, `email`, `user_avatar`, `register_time`) VALUES (1, 'admin', '123456', 'admin@baidu.com', 'res/images/avatar/1583996615108087.jpeg', '2020-05-05 17:21:27');
INSERT INTO `sys_user` (`id`, `username`, `password`, `email`, `user_avatar`, `register_time`) VALUES (2, 'test', '123456', 'test@baidu.com', 'res/images/avatar/1583996615117674.jpeg', '2020-05-06 18:27:10');
INSERT INTO `sys_user` (`id`, `username`, `password`, `email`, `user_avatar`, `register_time`) VALUES (3, 'root', '123456', 'root@baidu.com', 'res/images/avatar/1583996615679902.jpeg\n', '2020-05-06 18:28:27');
INSERT INTO `sys_user` (`id`, `username`, `password`, `email`, `user_avatar`, `register_time`) VALUES (4, 'user', '123456', 'user@baidu.com', 'res/images/avatar/1583996615271100.jpeg', '2020-05-06 18:31:34');
INSERT INTO `sys_user` (`id`, `username`, `password`, `email`, `user_avatar`, `register_time`) VALUES (5, 'rasp', '123456', 'rasp@baidu.com', 'res/images/avatar/1583996615380400.jpeg', '2020-05-06 18:32:08');
INSERT INTO `sys_user` (`id`, `username`, `password`, `email`, `user_avatar`, `register_time`) VALUES (7, 'guest', '123456', 'guest@baidu.con', 'res/images/avatar/1583996615179270.jepg', '2020-05-06 11:12:02');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
