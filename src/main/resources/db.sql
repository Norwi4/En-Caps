DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user` (
  `user_name` varchar(30) NOT NULL,
  `user_pass` varchar(255) NOT NULL,
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_name`)
);

CREATE TABLE `user_role` (
  `user_name` varchar(30) NOT NULL,
  `user_role` varchar(15) NOT NULL,
  FOREIGN KEY (`user_name`) REFERENCES `user` (`user_name`)
);


CREATE TABLE `DashboardViewParams` (
  `id` bigint(20) NOT NULL,
  `Object_Id` bigint(20) DEFAULT NULL,
  `User_Id` bigint(20) DEFAULT NULL,
  `Param_Id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Параметры показываемые на дашбоарде для конкретного пользователя';

-- --------------------------------------------------------

--
-- Структура таблицы `Objects`
--

CREATE TABLE `Objects` (
  `id` bigint(20) NOT NULL,
  `Project_id` bigint(20) NOT NULL COMMENT 'ID Проекта',
  `Name` varchar(100) DEFAULT NULL COMMENT 'Название объекта',
  `DateCreate` datetime DEFAULT current_timestamp() COMMENT 'Дата создания',
  `Visible` tinyint(1) DEFAULT 0 COMMENT 'Видимость на дашборде',
  `Active` tinyint(1) DEFAULT 0 COMMENT 'Активность опроса данных'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Объекты' PACK_KEYS=0;

-- --------------------------------------------------------

--
-- Структура таблицы `Object_Alarm`
--

CREATE TABLE `Object_Alarm` (
  `id` bigint(20) NOT NULL,
  `Object_Id` bigint(20) DEFAULT NULL,
  `Alarm_Id` bigint(20) DEFAULT NULL,
  `DateTime` datetime(6) DEFAULT current_timestamp(6) COMMENT 'Дата и время возникновения события',
  `Comment` varchar(100) DEFAULT NULL COMMENT 'Комментарий'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='События объекта';

-- --------------------------------------------------------

--
-- Структура таблицы `Object_Params`
--

CREATE TABLE `Object_Params` (
  `id` bigint(20) NOT NULL,
  `Object_Id` bigint(20) DEFAULT NULL,
  `Param_Id` bigint(20) DEFAULT NULL,
  `ValueInt` bigint(20) DEFAULT NULL COMMENT 'Значение типа INT',
  `ValueFloat` float(9,3) DEFAULT NULL COMMENT 'Значение типа FLOAT',
  `ValueStr` varchar(255) DEFAULT NULL COMMENT 'Значение типа STRING'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Параметры объекта';

-- --------------------------------------------------------

--
-- Структура таблицы `ParamsDictionary`
--

CREATE TABLE `ParamsDictionary` (
  `id` bigint(20) NOT NULL,
  `Code` varchar(20) NOT NULL COMMENT 'Уникальный код параметра',
  `Name` varchar(255) DEFAULT NULL COMMENT 'Рабочее название',
  `Format` varchar(20) DEFAULT NULL COMMENT 'Формат отображаемых данных'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Словарь параметров';

-- --------------------------------------------------------

--
-- Структура таблицы `Project`
--

CREATE TABLE `Project` (
  `id` bigint(20) NOT NULL,
  `Name` varchar(100) NOT NULL COMMENT 'Имя проекта',
  `Date_create` datetime DEFAULT NULL COMMENT 'Дата создания'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Проекты' PACK_KEYS=0;

-- --------------------------------------------------------

--
-- Структура таблицы `UserObjects`
--

CREATE TABLE `UserObjects` (
  `id` bigint(20) NOT NULL,
  `Object_Id` bigint(20) DEFAULT NULL,
  `User_Id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Привязка пользователей к объектам';

-- --------------------------------------------------------

--
-- Структура таблицы `UsersGlobal`
--

CREATE TABLE `UsersGlobal` (
  `id` bigint(20) NOT NULL,
  `FirstName` varchar(100) DEFAULT NULL COMMENT 'Имя',
  `MiddleName` varchar(100) DEFAULT NULL COMMENT 'Отчество',
  `LastName` varchar(100) DEFAULT NULL COMMENT 'Фамилия',
  `Login` varchar(20) DEFAULT NULL COMMENT 'Логин',
  `Password` varchar(128) DEFAULT NULL COMMENT 'Пароль',
  `Email` varchar(100) DEFAULT NULL COMMENT 'Email',
  `Organization` varchar(255) DEFAULT NULL COMMENT 'Название организации',
  `Phone` varchar(11) DEFAULT NULL COMMENT 'Телефон с кодом страны',
  `RuleFull` tinyint(1) DEFAULT 0 COMMENT 'Полный доступ',
  `RuleView` tinyint(1) DEFAULT 0 COMMENT 'Просмотр данных',
  `RuleControl` tinyint(1) DEFAULT 0 COMMENT 'Управление объектом',
  `LastActive` datetime DEFAULT current_timestamp() COMMENT 'Последняя авторизация',
  `TempBlock` datetime DEFAULT NULL COMMENT 'Временно заблокирован (дата/время)',
  `Enabled` tinyint(1) DEFAULT 1 COMMENT 'Включен / выключен пользователь'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Пользователи';

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `AlarmDictionary`
--
ALTER TABLE `AlarmDictionary`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `DashboardViewParams`
--
ALTER TABLE `DashboardViewParams`
  ADD PRIMARY KEY (`id`),
  ADD KEY `DashboardViewParams_fk1` (`Object_Id`),
  ADD KEY `DashboardViewParams_fk2` (`User_Id`),
  ADD KEY `DashboardViewParams_fk3` (`Param_Id`);

--
-- Индексы таблицы `Objects`
--
ALTER TABLE `Objects`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Objects_idx1` (`Project_id`);

--
-- Индексы таблицы `Object_Alarm`
--
ALTER TABLE `Object_Alarm`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Object_Alarm_fk1` (`Object_Id`),
  ADD KEY `Object_Alarm_fk2` (`Alarm_Id`);

--
-- Индексы таблицы `Object_Params`
--
ALTER TABLE `Object_Params`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Object_Params_fk1` (`Object_Id`),
  ADD KEY `Object_Params_fk2` (`Param_Id`);

--
-- Индексы таблицы `ParamsDictionary`
--
ALTER TABLE `ParamsDictionary`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Code` (`Code`);

--
-- Индексы таблицы `Project`
--
ALTER TABLE `Project`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `Session`
--
ALTER TABLE `Session`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Session_fk1` (`User_Id`);

--
-- Индексы таблицы `ShemaBlock`
--
ALTER TABLE `ShemaBlock`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `Shemas`
--
ALTER TABLE `Shemas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Shemas_fk1` (`Object_Id`);

--
-- Индексы таблицы `UserObjects`
--
ALTER TABLE `UserObjects`
  ADD PRIMARY KEY (`id`),
  ADD KEY `UserObjects_fk1` (`Object_Id`),
  ADD KEY `UserObjects_fk2` (`User_Id`);

--
-- Индексы таблицы `UsersGlobal`
--
ALTER TABLE `UsersGlobal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `AlarmDictionary`
--
ALTER TABLE `AlarmDictionary`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `DashboardViewParams`
--
ALTER TABLE `DashboardViewParams`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `Objects`
--
ALTER TABLE `Objects`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `Object_Params`
--
ALTER TABLE `Object_Params`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `ParamsDictionary`
--
ALTER TABLE `ParamsDictionary`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `Project`
--
ALTER TABLE `Project`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `Session`
--
ALTER TABLE `Session`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `ShemaBlock`
--
ALTER TABLE `ShemaBlock`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `Shemas`
--
ALTER TABLE `Shemas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `UserObjects`
--
ALTER TABLE `UserObjects`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `UsersGlobal`
--
ALTER TABLE `UsersGlobal`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `DashboardViewParams`
--
ALTER TABLE `DashboardViewParams`
  ADD CONSTRAINT `DashboardViewParams_fk1` FOREIGN KEY (`Object_Id`) REFERENCES `Objects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `DashboardViewParams_fk2` FOREIGN KEY (`User_Id`) REFERENCES `UsersGlobal` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `DashboardViewParams_fk3` FOREIGN KEY (`Param_Id`) REFERENCES `ParamsDictionary` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `Objects`
--
ALTER TABLE `Objects`
  ADD CONSTRAINT `Objects_fk1` FOREIGN KEY (`Project_id`) REFERENCES `Project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `Object_Alarm`
--
ALTER TABLE `Object_Alarm`
  ADD CONSTRAINT `Object_Alarm_fk1` FOREIGN KEY (`Object_Id`) REFERENCES `Objects` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `Object_Alarm_fk2` FOREIGN KEY (`Alarm_Id`) REFERENCES `AlarmDictionary` (`id`) ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `Object_Params`
--
ALTER TABLE `Object_Params`
  ADD CONSTRAINT `Object_Params_fk1` FOREIGN KEY (`Object_Id`) REFERENCES `Objects` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `Object_Params_fk2` FOREIGN KEY (`Param_Id`) REFERENCES `ParamsDictionary` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `Session`
--
ALTER TABLE `Session`
  ADD CONSTRAINT `Session_fk1` FOREIGN KEY (`User_Id`) REFERENCES `UsersGlobal` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `Shemas`
--
ALTER TABLE `Shemas`
  ADD CONSTRAINT `Shemas_fk1` FOREIGN KEY (`Object_Id`) REFERENCES `Objects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `UserObjects`
--
ALTER TABLE `UserObjects`
  ADD CONSTRAINT `UserObjects_fk1` FOREIGN KEY (`Object_Id`) REFERENCES `Objects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;