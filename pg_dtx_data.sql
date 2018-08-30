/*
Navicat PGSQL Data Transfer

Source Server         : host-196
Source Server Version : 100400
Source Host           : host-196:5432
Source Database       : seastar
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 100400
File Encoding         : 65001

Date: 2018-08-30 16:19:08
*/


-- ----------------------------
-- Table structure for app1_tx1
-- ----------------------------
DROP TABLE IF EXISTS "public"."app1_tx1";
CREATE TABLE "public"."app1_tx1" (
"id" int4,
"name" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of app1_tx1
-- ----------------------------
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');
INSERT INTO "public"."app1_tx1" VALUES ('2', 'aaa');

-- ----------------------------
-- Table structure for app1_tx2
-- ----------------------------
DROP TABLE IF EXISTS "public"."app1_tx2";
CREATE TABLE "public"."app1_tx2" (
"id" int4,
"name" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of app1_tx2
-- ----------------------------
INSERT INTO "public"."app1_tx2" VALUES ('2', 'aaa');

-- ----------------------------
-- Table structure for app1_tx3
-- ----------------------------
DROP TABLE IF EXISTS "public"."app1_tx3";
CREATE TABLE "public"."app1_tx3" (
"id" int4,
"name" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of app1_tx3
-- ----------------------------
INSERT INTO "public"."app1_tx3" VALUES ('2', 'aaa');

-- ----------------------------
-- Table structure for tx1
-- ----------------------------
DROP TABLE IF EXISTS "public"."tx1";
CREATE TABLE "public"."tx1" (
"id" int4,
"name" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of tx1
-- ----------------------------
INSERT INTO "public"."tx1" VALUES ('1', 'aa');
INSERT INTO "public"."tx1" VALUES ('1', 'aa');
INSERT INTO "public"."tx1" VALUES ('1', 'aa');
INSERT INTO "public"."tx1" VALUES ('1', 'aa');
INSERT INTO "public"."tx1" VALUES ('1', 'aa');
INSERT INTO "public"."tx1" VALUES ('1', 'aa');
INSERT INTO "public"."tx1" VALUES ('1', 'aa');

-- ----------------------------
-- Table structure for tx2
-- ----------------------------
DROP TABLE IF EXISTS "public"."tx2";
CREATE TABLE "public"."tx2" (
"id" int4,
"name" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of tx2
-- ----------------------------
INSERT INTO "public"."tx2" VALUES ('2', 'aaa');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------
