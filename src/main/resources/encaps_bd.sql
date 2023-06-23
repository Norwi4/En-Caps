UNLOCK
    TABLES;

DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `company`;
DROP TABLE IF EXISTS `user_company`;
DROP TABLE IF EXISTS `object`;
DROP TABLE IF EXISTS `dashboardview`;
DROP TABLE IF EXISTS `response`;
DROP TABLE IF EXISTS `objectparams`;
DROP TABLE IF EXISTS `objectdata`;
DROP TABLE IF EXISTS `paramsdictionary`;

CREATE TABLE `user` (
  `user_name` varchar(30) NOT NULL,
  `user_pass` varchar(255) NOT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_name`)
)ENGINE = InnoDB
   DEFAULT CHARSET = utf8;

CREATE TABLE `user_role` (
  `user_name` varchar(30) NOT NULL,
  `user_role` varchar(15) NOT NULL,
  FOREIGN KEY (`user_name`) REFERENCES `user` (`user_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `company` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL COMMENT 'Название компании',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `object` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `object_name` varchar(255) NOT NULL COMMENT 'Название объекта (котельной например)',
  `company_id` bigint(19) NOT NULL COMMENT 'ID компании, к которой привязан объект',
  `device_id` bigint(19) NOT NULL COMMENT 'ID ПЛК',
  `visible` tinyint(1) DEFAULT 0 COMMENT 'Видимость на дашборде',
  `active` tinyint(1) DEFAULT 0 COMMENT 'Активность опроса данных',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `dashboardview` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(19) NOT NULL COMMENT 'ID пользователя',
  `object_id` bigint(19) NOT NULL COMMENT 'ID обьекта, к которой привязан пользователь',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `response` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` bigint(19) NOT NULL COMMENT 'ID обьекта (котельной)',
  `token` varchar(255) NOT NULL COMMENT 'токен от ОВ',
  `visible` tinyint(1) DEFAULT 0 COMMENT 'Видимость',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `objectparams` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `object_id` bigint(19) NOT NULL COMMENT 'ID обьекта (котельной)',
  `param_id` varchar(255) NOT NULL COMMENT 'ID параметра (расшифровка его)',
  `value` varchar(255) NOT NULL COMMENT 'показатель датчика',
  `timeInOven` varchar(255)        NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `objectdata` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` bigint(19) NOT NULL COMMENT 'ID обьекта (котельной)',
  `status` varchar(255) NOT NULL COMMENT 'Статус объекта (ПЛК)',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `paramsdictionary` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` bigint(19) NOT NULL COMMENT 'ID обьекта (котельной)',
  `param_id` bigint(19) NOT NULL COMMENT 'ID датчика',
  `code` varchar(255) NOT NULL COMMENT 'Какой-то код типа UID1073741881',
  `type` varchar(255) NOT NULL COMMENT 'Тип параметра (авария или параметр)',
  `name` varchar(255) NOT NULL COMMENT 'Наименование обьекта в системе Овен',
  `name_in_system` varchar(15)  NULL COMMENT 'Полное наименование обьекта',
  `short_name_in_system` varchar(15)  NULL COMMENT 'Короткое наименование обьекта',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


UNLOCK
    TABLES;