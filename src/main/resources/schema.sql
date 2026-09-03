-- 创建数据库
CREATE DATABASE IF NOT EXISTS book_trading
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE book_trading;

-- 1. 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `user_id` INT NOT NULL AUTO_INCREMENT COMMENT '用户ID（主键）',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名（唯一）',
    `password` VARCHAR(100) NOT NULL COMMENT '密码（存储BCrypt加密后的密文）',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `credit_score` DECIMAL(3,2) DEFAULT 5.00 COMMENT '信用分（1.00~5.00，保留两位小数）',
    `status` VARCHAR(20) DEFAULT 'normal' COMMENT '状态：normal正常 / banned封禁',
    `role` VARCHAR(20) DEFAULT 'USER' COMMENT '角色：USER普通用户 / ADMIN管理员',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uk_username` (`username`) COMMENT '用户名唯一索引，加速登录查询'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 2. 管理员扩展表
CREATE TABLE IF NOT EXISTS `admin` (
    `admin_id` INT NOT NULL AUTO_INCREMENT COMMENT '管理员记录ID',
    `user_id` INT NOT NULL COMMENT '关联用户ID',
    `permission_level` VARCHAR(50) DEFAULT 'MANAGE' COMMENT '权限等级：SUPER / MANAGE / AUDIT',
    `last_login_ip` VARCHAR(45) DEFAULT NULL COMMENT '最后登录IP（IPv6兼容）',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    PRIMARY KEY (`admin_id`),
    UNIQUE KEY `uk_user_id` (`user_id`),
    CONSTRAINT `fk_admin_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员扩展表';

-- 3. 课程表
CREATE TABLE IF NOT EXISTS `course` (
    `course_id` INT NOT NULL AUTO_INCREMENT COMMENT '课程ID',
    `course_name` VARCHAR(100) NOT NULL COMMENT '课程名称（如：数据结构与算法）',
    `course_code` VARCHAR(50) DEFAULT NULL COMMENT '课程编号（如：CS101）',
    `semester` VARCHAR(30) DEFAULT NULL COMMENT '学期（如：2025-2026-1）',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
    PRIMARY KEY (`course_id`),
    KEY `idx_course_name` (`course_name`) COMMENT '课程名称索引，加速搜索'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 4. 书籍表
CREATE TABLE IF NOT EXISTS `book` (
    `book_id` INT NOT NULL AUTO_INCREMENT COMMENT '书籍ID',
    `seller_id` INT NOT NULL COMMENT '卖家用户ID（外键→user）',
    `course_id` INT DEFAULT NULL COMMENT '关联课程ID（外键→course，可为空）',
    `title` VARCHAR(200) NOT NULL COMMENT '书名',
    `isbn` VARCHAR(20) DEFAULT NULL COMMENT 'ISBN号（13位或10位）',
    `original_price` DECIMAL(10,2) DEFAULT NULL COMMENT '原价（新书价）',
    `selling_price` DECIMAL(10,2) NOT NULL COMMENT '售价',
    `condition_desc` VARCHAR(255) DEFAULT NULL COMMENT '成色描述（如：八成新，有笔记）',
    `cover_img_path` VARCHAR(255) DEFAULT NULL COMMENT '封面图片相对路径（如 /upload/books/xxx.jpg）',
    `status` VARCHAR(20) DEFAULT '在售' COMMENT '状态：在售/已预定/已售出/已下架',
    `publish_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    PRIMARY KEY (`book_id`),
    KEY `idx_seller_id` (`seller_id`),
    KEY `idx_course_id` (`course_id`),
    KEY `idx_status` (`status`),
    KEY `idx_selling_price` (`selling_price`) COMMENT '价格区间查询索引',
    FULLTEXT KEY `ft_title` (`title`) COMMENT '书名全文索引（可选，依赖MySQL版本）',
    CONSTRAINT `fk_book_seller` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT,
    CONSTRAINT `fk_book_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='书籍表';

-- 5. 订单主表
CREATE TABLE IF NOT EXISTS `order` (
    `order_id` INT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `buyer_id` INT NOT NULL COMMENT '买家ID（外键→user）',
    `seller_id` INT NOT NULL COMMENT '卖家ID（外键→user）',
    `total_amount` DECIMAL(10,2) NOT NULL COMMENT '订单总金额（由order_item汇总得出，快照存储）',
    `status` VARCHAR(20) DEFAULT '待付款' COMMENT '状态：待付款/已付款/已完成/已取消',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
    `pay_time` DATETIME DEFAULT NULL COMMENT '付款时间（模拟付款时写入）',
    PRIMARY KEY (`order_id`),
    KEY `idx_buyer_id` (`buyer_id`),
    KEY `idx_seller_id` (`seller_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`) COMMENT '按时间排序查询',
    CONSTRAINT `fk_order_buyer` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT,
    CONSTRAINT `fk_order_seller` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主表';

-- 6. 订单详情表
CREATE TABLE IF NOT EXISTS `order_item` (
    `item_id` INT NOT NULL AUTO_INCREMENT COMMENT '详情项ID',
    `order_id` INT NOT NULL COMMENT '所属订单ID（外键→order）',
    `book_id` INT NOT NULL COMMENT '书籍ID（外键→book）',
    `price_snapshot` DECIMAL(10,2) NOT NULL COMMENT '下单时该书的售价快照',
    `quantity` INT DEFAULT 1 COMMENT '购买数量（扩展字段，当前固定为1）',
    PRIMARY KEY (`item_id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_book_id` (`book_id`),
    CONSTRAINT `fk_item_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE,
    CONSTRAINT `fk_item_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表';

-- 7. 站内消息表
CREATE TABLE IF NOT EXISTS `message` (
    `msg_id` INT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    `from_user_id` INT NOT NULL COMMENT '发送者ID（外键→user）',
    `to_user_id` INT NOT NULL COMMENT '接收者ID（外键→user）',
    `book_id` INT DEFAULT NULL COMMENT '关联书籍ID（外键→book，可空）',
    `parent_msg_id` INT DEFAULT NULL COMMENT '父消息ID（外键→message，用于回复线程）',
    `content` TEXT NOT NULL COMMENT '消息正文',
    `send_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    `is_read` TINYINT(1) DEFAULT 0 COMMENT '是否已读：0未读 / 1已读',
    PRIMARY KEY (`msg_id`),
    KEY `idx_from_user_id` (`from_user_id`),
    KEY `idx_to_user_id` (`to_user_id`),
    KEY `idx_parent_msg_id` (`parent_msg_id`),
    CONSTRAINT `fk_msg_from_user` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
    CONSTRAINT `fk_msg_to_user` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
    CONSTRAINT `fk_msg_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE SET NULL,
    CONSTRAINT `fk_msg_parent` FOREIGN KEY (`parent_msg_id`) REFERENCES `message` (`msg_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='站内消息表';

-- 8. 评价表
CREATE TABLE IF NOT EXISTS `evaluation` (
    `eval_id` INT NOT NULL AUTO_INCREMENT COMMENT '评价ID',
    `order_id` INT NOT NULL COMMENT '关联订单ID（外键→order，且唯一）',
    `evaluator_id` INT NOT NULL COMMENT '评价发起人（买家）ID（外键→user）',
    `target_user_id` INT NOT NULL COMMENT '被评价人（卖家）ID（外键→user）',
    `score` TINYINT NOT NULL COMMENT '评分（1~5分）',
    `comment` VARCHAR(255) DEFAULT NULL COMMENT '评语文字',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
    PRIMARY KEY (`eval_id`),
    UNIQUE KEY `uk_order_id` (`order_id`) COMMENT '一笔订单只能评价一次',
    KEY `idx_target_user_id` (`target_user_id`) COMMENT '加速查询某卖家的所有评价',
    CONSTRAINT `fk_eval_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE RESTRICT,
    CONSTRAINT `fk_eval_evaluator` FOREIGN KEY (`evaluator_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT,
    CONSTRAINT `fk_eval_target` FOREIGN KEY (`target_user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- 9. 收藏表
CREATE TABLE IF NOT EXISTS `favorite` (
    `fav_id` INT NOT NULL AUTO_INCREMENT COMMENT '收藏记录ID',
    `user_id` INT NOT NULL COMMENT '用户ID（外键→user）',
    `book_id` INT NOT NULL COMMENT '书籍ID（外键→book）',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (`fav_id`),
    UNIQUE KEY `uk_user_book` (`user_id`, `book_id`) COMMENT '唯一组合索引，防止重复收藏',
    KEY `idx_book_id` (`book_id`),
    CONSTRAINT `fk_fav_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
    CONSTRAINT `fk_fav_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';
