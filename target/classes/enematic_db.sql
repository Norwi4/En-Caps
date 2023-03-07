UNLOCK
    TABLES;

DROP TABLE IF EXISTS `plc`;
DROP TABLE IF EXISTS `parameters`;
DROP TABLE IF EXISTS `device`;
DROP TABLE IF EXISTS `companies`;
DROP TABLE IF EXISTS `failure`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `authResponse`;

CREATE TABLE `plc`
(
    `id`         bigint(19) unsigned NOT NULL AUTO_INCREMENT,
    `company_id` bigint(19)          NULL,
    `device_id`  bigint(19)          NOT NULL,
    `name`       varchar(255)        NOT NULL,
    `type`       varchar(255)        NULL,
    `status`     varchar(255)        NOT NULL,
    `created_at` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `device`
(
    `id`          bigint(19) unsigned NOT NULL AUTO_INCREMENT,
    `company_id`      bigint(19)          NOT NULL,
    `device_id`   bigint(19)          NOT NULL,
    `code`        varchar(255)        NOT NULL,
    `category_id` bigint(19)          NOT NULL,
    `name`        varchar(255)        NOT NULL,
    `type`        varchar(255)        NOT NULL,
    `user_id`        varchar(255)        NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `parameters`
(
    `id`         bigint(19) unsigned NOT NULL AUTO_INCREMENT,
    `device_id`  bigint(19)          NOT NULL,
    `timeInOven` varchar(255)        NOT NULL,
    `value`      varchar(255)        NULL,
    `codeError`  varchar(255)        NOT NULL,
    `format`     varchar(255)        NOT NULL,
    `created_at` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `user_id`    varchar(255)        NOT NULL,
    `company_id` varchar(255)        NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `failure`
(
    `id`         bigint(19) unsigned NOT NULL AUTO_INCREMENT,
    `device_id`  bigint(19)          NOT NULL,
    `timeInOven` varchar(255)        NOT NULL,
    `value`      varchar(255)        NULL,
    `codeError`  varchar(255)        NOT NULL,
    `format`     varchar(255)        NOT NULL,
    `created_at` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `companies`
(
    `id`         bigint(19) unsigned NOT NULL AUTO_INCREMENT,
    `company_id` bigint(19) unsigned NOT NULL,
    `name`       varchar(255)        NOT NULL,
    `user_id`    varchar(255)        NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `users`
(
    `id`       bigint(19) unsigned NOT NULL AUTO_INCREMENT,
    `name`     varchar(255)        NOT NULL,
    `surname`  varchar(255)        NULL,
    `password` varchar(255)        NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `authResponse`
(
    `id`      bigint(19) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255)        NOT NULL,
    `token`   varchar(255)        NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

UNLOCK
    TABLES;
