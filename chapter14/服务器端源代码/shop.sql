/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;


CREATE DATABASE `shop` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `shop`;
CREATE TABLE `bill` (
  `Id` int(11) NOT NULL auto_increment,
  `uid` varchar(20) default '',
  `gids` varchar(255) default '',
  `gnums` varchar(255) default '',
  `state` varchar(20) default '',
  `btime` varchar(10) default '',
  `btype` varchar(10) default '',
  `ctime` varchar(20) default '',
  `address` varchar(255) default '',
  PRIMARY KEY  (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `bill` VALUES (1,'user','1,5','1,1','处理中','周一至周五','现金','2010-04-11','北京');
CREATE TABLE `goods` (
  `Id` int(11) NOT NULL auto_increment,
  `brand` varchar(20) default '',
  `price` float default '0',
  `discount` float default '0',
  `bcount` int(11) default '0',
  `des` text,
  `pic` varchar(20) default '',
  `dir` varchar(20) default '',
  `gid` varchar(20) default '',
  `type` int(11) default '0',
  `pop` int(11) default '0',
  PRIMARY KEY  (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

INSERT INTO `goods` VALUES (1,'液晶电视',2800,0.8,321,'降低杂质信号干扰,使图像画质提升,可手动调节画面的锐度,平滑度,自动识别并且增强需要强化的颜色区域来调整图像的彩色表现,','jd1.jpg','img/','jd337621',1,1);
INSERT INTO `goods` VALUES (2,'冰箱',1799,0.89,56,'智节系列白色两门冰箱,冷藏可调，锁住食物营养成份,强力速冻留住食物新鲜口味,透明抽屉一目了然,制冷循环优化制冷系统','jd2.jpg','img/','jd538772',1,0);
INSERT INTO `goods` VALUES (3,'变频空调',3200,0.9,35,'热干燥清洁功能,四档送风功能;可控式电辅助加热热功能,强劲运行功能;双重健康滤网,时尚外观设计;','jd3.jpg','img/','jd366857',1,0);
INSERT INTO `goods` VALUES (4,'电热水器',1600,0.4,532,'独有下潜式加热体,大大增加了与水的接触面积,在热量得以充分释放的同时,实现全方位加热无死角,能源充分利用,节能更高效','jd4.jpg','img/','jd184086',1,0);
INSERT INTO `goods` VALUES (5,'3G手机',3000,0.6,432,'全新Android 2.2智能系统海量应用下载,极速多任务处理,多项超强功能提升,最好的互联网体验.','sj1.jpg','img/','sj337624',2,1);
INSERT INTO `goods` VALUES (6,'数码相机',700,0.6,476,'1400万有效像素的分辨率极富感染力,绝不会错失拍摄对象的详尽细节, 拍摄的图像即使以放大尺寸打印时依然会保持画面清晰,色泽明亮,鲜艳动人','sj2.jpg','img/','sj356433',2,0);
INSERT INTO `goods` VALUES (7,'MP4播放器',379,0.5,32,'全新的时尚外观设计,时尚而充满质感,600万色5.0英寸顶级显示屏的运用,分辨率高达800×480,影像逼真细腻,可视角度高达170度的全方位呈现','sj3.jpg','img/','sj313156',2,0);
INSERT INTO `goods` VALUES (8,'镁铝合金三脚架',600,0.5,87,'中轴锁紧钮的飞翼形设计,可更省力更方便的调节中轴高度,而中轴的防旋转凹槽设计,更方便摄影师的拍摄','sj4.jpg','img/','sj271764',2,0);
INSERT INTO `goods` VALUES (9,'笔记本电脑',2900,0.8,654,'使用可选的蓝牙连接功能,连接无线键盘,鼠标和耳机,不支持蓝牙耳机和麦克的数据传输','dn1.jpg','img/','dn346848',3,1);
INSERT INTO `goods` VALUES (10,'台式电脑',3000,0.8,434,'选择最高配置的512 MB NVIDIA独立图形卡,您可以使用可选的DVI ADD2卡添加第二台数字显示器,以扩展可视工作区','dn2.jpg','img/','dn334336',3,0);
INSERT INTO `goods` VALUES (11,'激光打印机',1199,0.5,34,'融合尖端技术的P105b轻松实现1200X1200dpi的物理打印分辨率,标配64M超大内存,月负荷高达20000页','dn3.jpg','img/','dn327335',3,0);
INSERT INTO `goods` VALUES (12,'宽屏投影机',7999,0.6,432,'搭载TI新一代 1280 X 800高清晰宽屏芯片,与商务办公的宽屏显示设备点对点对应,符合宽屏办公的新趋势,宽屏画面比传统画面更接近黄金分割比例','dn4','img/','dn339138',3,0);
CREATE TABLE `users` (
  `Id` int(11) NOT NULL auto_increment,
  `uid` varchar(20) default '',
  `pwd` varchar(20) default '',
  PRIMARY KEY  (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `users` VALUES (1,'user','123');

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
