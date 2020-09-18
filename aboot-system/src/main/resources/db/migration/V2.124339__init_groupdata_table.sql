/* 2020/03/11 121830 mission */
DROP TABLE IF EXISTS `tool_group_data`;
CREATE TABLE `tool_group_data` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`group_name` VARCHAR(100) NOT NULL COMMENT '对应的数据名称',
	`value` TEXT NOT NULL COMMENT '数据组对应的数据值（json数据）',
	`sort` INT(11) NULL DEFAULT NULL COMMENT '排序字段',
	`enabled` BIT(1) NOT NULL COMMENT '状态（1：开启；2：关闭；）',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
    `deleted_at` timestamp NULL DEFAULT NULL COMMENT '逻辑删除时间',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `updated_by` bigint(20) DEFAULT NULL COMMENT '修改人',
	PRIMARY KEY (`id`)
)
COMMENT='数据组'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
