/**
 * INTEL CONFIDENTIAL
 * Copyright (c) 2014 Intel Corporation All Rights Reserved.
 * The source code contained or described herein and all documents related to the source code ("Material")
 * are owned by Intel Corporation or its suppliers or licensors. Title to the Material remains with Intel
 * Corporation or its suppliers and licensors. The Material contains trade secrets and proprietary and
 * confidential information of Intel or its suppliers and licensors. The Material is protected by
 * worldwide copyright and trade secret laws and treaty provisions. No part of the Material may be used,
 * copied, reproduced, modified, published, uploaded, posted, transmitted, distributed, or disclosed in
 * any way without Intel's prior express written permission. No license under any patent, copyright,
 * trade secret or other intellectual property right is granted to or conferred upon you by disclosure
 * or delivery of the Materials, either expressly, by implication, inducement, estoppel or otherwise.
 * Any license under such intellectual property rights must be express and approved by Intel in writing.
 */

package com.intel.stl.ui.common;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fernando Fernandez
 * 
 */
public enum STLConstants {

    K0001_FABRIC_VIEWER_TITLE(1),
    K0002_LOADING(2),
    K0003_DUPLICATE_INSTANCES(3),
    K0004_SWITCH(4),
    K0005_HOST_FABRIC_INTERFACE(5),
    K0006_ROUTER(6),
    K0007_SUBNET(7),
    K0008_UPTIME(8),
    K0009_DAYS(9),
    K0010_HOURS(10),
    K0011_MINUTES(11),
    K0012_SECONDS(12),
    K0013_LINKS(13),
    K0014_ACTIVE_NODES(14),
    K0015_TYPES(15),
    K0016_UNKNOWN(16),
    K0017_SWITCH(17),
    K0018_HFI(18),
    K0019_ROUTER(19),
    K0020_FAILED(20),
    K0021_SKIPPED(21),
    K0022_NORMAL(22),
    K0023_STATES(23),
    K0024_ACTIVE_PORTS(24),
    K0025_MASTER_SM(25),
    K0026_LID(26),
    K0027_PORT_GUID(27),
    K0028_ELAPSED_TIME(28),
    K0029_CRITICAL(29),
    K0030_ERROR(30),
    K0031_WARNING(31),
    K0032_INFORMATIONAL(32),
    K0033_SWITCH_STAETES(33),
    K0034_HFI_STATES(34),
    K0035_TIME(35),
    K0036_MORE(36),
    K0037_HELP(37),
    K0038_PIN_TOOLTIP(38),
    K0039_NOT_AVAILABLE(39),
    K0040_MBPS(40),
    K0041_BANDWIDTH(41),
    K0042_ERROE_RATE(42),
    K0043_CONGESTION_ERROR(43),
    K0044_NUM_PORTS(44),
    K0045_BANDWIDTH_HISTOGRAM(45),
    K0046_CONGESTION_HISTOGRAM(46),
    K0047_NUM_ERRORS(47),
    K0048_SWITCHES(48),
    K0049_HFIS(49),
    K0050_ROUTERS(50),
    K0051_HOST(51),
    K0052_HOSTS(52),
    K0053_CAPABILITY(53),
    K0054_CONFIGURE(54),
    K0055_NUM_NODES(55),
    K0056_NONE(56),
    K0057_RANDOM(57),
    K0058_PORT_MASK(58),
    K0059_STANDBY_SMS(59),
    K0060_ISL(60),
    K0061_HOST_LINKS(61),
    K0062_STATUS(62),
    K0063_SW_STATUS(63),
    K0064_FI_STATUS(64),
    K0065_PACKET_RATE(65),
    K0066_KPPS(66),
    K0067_SIGNAL_INTEGRITY_ERROR(67),
    K0068_SIGNAL_INTEGRITY_HISTOGRAM(68),
    K0069_CONNECT_TO(69),
    K0070_SMA_CONGESTION_ERROR(70),
    K0071_SMA_CONGESTION_HISTOGRAM(71),
    K0072_SECURITY(72),
    K0073_SECURITY_HISTOGRAM(73),
    K0074_ROUTING_ERROR(74),
    K0075_ROUTING_HISTOGRAM(75),
    K0076_INTERNAL(76),
    K0077_EXTERNAL(77),
    K0078_TREND(78),
    K0079_HISTOGRAM(79),
    K0080_ON(80),
    K0081_YES(81),
    K0082_NO(82),
    K0083_NODE_TYPE(83),
    K0084_LINK_WIDTH_DOWNGRADE(84),
    K0085_SM_CONFIG_STARTED(85),
    K0086_NEIGHBOR_NORMAL(86),
    K0087_SUBNET_TIMEOUT(87),
    K0088_SNOOP_SUPPORTED(88),
    K0089_ASYNC_SC2VL_SUPPORTED(89),
    K0090_ADDRRANGECONFIG_SUPPORTED(90),
    K0091_PASSTHRU_SUPPORTED(91),
    K0092_SHAREDSPACE_SUPPORTED(92),
    K0093_VL15MULTI_SUPPORTED(93),
    K0094_VLMARKER_SUPPORTED(94),
    K0095_VLR_SUPPORTED(95),
    K0096_OVERALL_BUFF_SPACE(96),
    K0097_GANGED_PORT_DETAILS(97),
    K0098_REPLAY_DEPTH_BUFFER(98),
    K0099_REPLAY_DEPTH_WIRE(99),

    K0100_HOME(100),
    K0101_HOME_DESCRIPTION(101),
    K0102_HOME_SUMMARY(102),
    K0103_HOME_PERFORMANCE(103),
    K0104_HOME_EVENTS(104),
    K0105_HEALTH_HISTORY(105),
    K0106_WORST_NODES(106),
    K0107_REFRESH(107),
    K0108_SCORE(108),
    K0109_OTHERS(109),
    K0110_HFI(110),
    K0111_HFIS(111),

    K0112_ONLINE_HELP(112),

    K0113_PORT_VALUE(113),
    K0114_NBR_VALUE(114),

    K0200_PERFORMANCE(200),
    K0201_PERFORMANCE_DESCRIPTION(201),
    K0202_PERFORMANCE_STATISTICS(202),
    K0203_PERFORMANCE_PROPERTIES(203),
    K0204_GENERAL_SUMMARY(204),
    K0205_NODE_SUMMARY(205),
    K0206_EVENTS_SUMMARY(206),
    K0207_NODES_AND_PORTS(207),
    K0208_PORTS_TABLE(208),
    K0209_TX_RX_PACKETS(209),
    K0210_SHOW_OPTIONS(210),
    K0211_APPLY_OPTIONS(211),

    // Property text
    K0300_PROPERTIES(300),
    K0301_PROPERTIES_DESCRIPTION(301),

    K0302_PORT_PART_ENFORCE_TITLE(302),
    K0303_PORT_PART_ENFORCE_IN(303),
    K0304_PORT_PART_ENFORCE_OUT(304),
    K0305_PORT_FIL_RAW_PKT_IN(305),
    K0306_PORT_FIL_RAW_PKT_OUT(306),

    K0307_DEV_INFO_TITLE(307),
    K0308_BASE_LID(308),
    K0309_PORT_STATE(309),
    K0310_PORT_PHY_STATE(310),
    K0311_PORT_NODE_GUID(311),
    K0312_PORT_GID_PREFIX(312),
    K0313_PORT_UNI_DIAG_CODE(313),
    K0314_PORT_VENDER_DIAG_CODE(314),
    K0315_PORT_MAX_NEIGBHOR_SIZE(315),
    K0316_PORT_MAX_MUT_SIZE(316),
    K0317_PORT_SUPP_GUID_ENTRIES(317),

    K0318_PORT_LINK_TITLE(318),
    K0319_PORT_LINK_WIDTH(319),
    K0320_PORT_LINK_ENABLED(320),
    K0321_PORT_LINK_SUPPORTED(321),
    K0322_PORT_LINK_ACTIVE(322),
    K0323_PORT_LINK_SPEED(323),
    K0324_PORT_LINK_CONN_TO(324),
    K0325_PORT_NODE_DESCRIPTION(325),
    K0326_GUID(326),
    K0327_PORT_INDEX(327),

    K0328_PORT_CAPABILITY_TITLE(328),
    K0329_PORT_SUBNET_MANAGER(329),
    K0330_PORT_NOTICE_SUPP(330),
    K0331_PORT_TRAP_SUPP(331),
    K0332_PORT_RESET_SUPP(332),
    K0333_PORT_AUTO_MIGRATION_SUPP(333),
    K0334_PORT_SL_MAP_SUPP(334),
    K0335_PORT_MKEY_SUPP(335),
    K0336_PORT_PKEY_SUPP(336),
    K0337_PORT_LED_LABEL_SUPP(337),
    K0338_PORT_SM_DISABLED_SUPP(338),
    K0339_PORT_CONN_LABEL_SUPP(339),
    K0340_PORT_DEVICE_MAN_SUPP(340),
    K0341_PORT_VENDOR_CLS_SUPP(341),

    K0342_PORT_VL_TITLE(342),
    K0343_PORT_VL_CAP(343),
    K0344_PORT_VL_HI_LIMIT(344),
    K0345_PORT_VL_ARBIT_HI_CAP(345),
    K0346_PORT_VL_ARBIT_LOW_CAP(346),
    K0347_PORT_VL_STALL_COUNT(347),
    K0348_PORT_HOQLIFE_LABEL(348),
    K0349_PORT_OPERATIONAL_VLS(349),

    K0350_PORT_DIAG_TITLE(350),
    K0351_PORT_MASTER_SMSL(351),
    K0352_PORT_M_KEY_VIOLATION(352),
    K0353_PORT_P_KEY_VIOLATION(353),
    K0354_PORT_Q_KEY_VIOLATION(354),
    K0355_PORT_RESPONSE_TIME(355),
    K0356_PORT_LOCAL_PHY_ERR(356),
    K0357_PORT_OVERRUN_ERR(357),

    K0358_PORT_MANAGEMENT_TITLE(358),
    K0359_PORT_M_KEY(359),
    K0360_PORT_MASTER_SM_LID(360),
    K0361_PORT_M_KEY_LEASE_PERIOD(361),
    K0362_NODE_STATE(362),
    K0363_NODE_GUID(363),
    K0364_NODE_PORT_COUNT(364),
    K0365_STATE(365),
    K0366_NODE_VENDOR_ID(366),
    K0367_DEVICE_GRP(367),

    K0368_NO_STATE_CHANGE(368),
    K0369_DOWN(369),
    K0370_INIT(370),
    K0371_ARMED(371),
    K0372_ACTIVE(372),
    K0373_GANGED(373),

    K0374_SLEEP(374),
    K0375_POLLING(375),
    K0376_DISABLED(376),
    K0377_CONFIG_TRAIN(377),
    K0378_LINKUP(378),
    K0379_LINK_ERR_RECOV(379),
    K0380_SHALLOW_SLEEP(380),
    K0381_OFFLINE(381),
    K0382_TEST(382),
    K0383_NA(383),
    K0384_ALL_SUPP(384),
    K0385_TRUE(385),
    K0386_FALSE(386),
    K0387_UNKNOWN(387),
    K0388_OR(388),
    K0389_NUM_PORTS(389),
    K0390_NUM_LIDS(390),
    K0391_NUM_ENTRIES(391),
    K0392_PORT_PROPERTIES(392),
    K0393_IN_PCK_ENFORCE(393),
    K0394_IN_RAW_ENFORCE(394),
    K0395_ENHANCED_SP0_SUPP(395),
    K0396_IPCHASSIS_NAME(396),
    K0397_GENERAL(397),
    K0398_LINEAR_FWD_TABLE(398),
    K0399_MULTI_FWD_TABLE(399),

    // Event Table Headings
    K0400_DATE(400),
    K0401_TIME(401),
    K0402_SEVERITY(402),
    K0403_SOURCE(403),
    K0404_PORT_NUMBER(404),
    K0405_DESCRIPTION(405),
    K0406_EVENTS(406),

    // Tree Headings
    K0407_DEVICE_TYPES(407),
    K0408_DEVICE_GROUPS(408),
    K0409_VIRTUAL_FABRICS(409),
    K0410_CONGESTED_NODES(410),

    K0411_SUMMARY(411),
    K0412_SUMMARY_DESCRIPTION(412),
    K0413_STATISTICS(413),
    K0414_STATISTICS_DESCRIPTION(414),
    K0415_CONNECTIVITY(415),
    K0416_CONNECTIVITY_DESCRIPTION(416),
    K0417_PATH_CONNECTIVITY(417),

    K0420_ADVANCED_PROPERTIES(420),
    K0421_ADVANCED_PROPERTIES_DESCRIPTION(421),
    K0422_TYPE(422),
    K0423_SYSTEM_IMAGE_GUID(423),
    K0424_PARTITION_CAP(424),
    K0425_BASE_VERSION(425),
    K0426_SMA_VERSION(426),
    K0427_PORT_NUMBER(427),
    K0428_LINEAR_FDB_CAP(428),
    K0429_LINEAR_FDB_TOP(429),
    K0430_MULTICAST_FDB_CAP(430),
    K0431_MULTICAST_FDB_TOP(431),
    K0432_PORT_GROUP_CAP(432),
    K0433_PORT_GROUP_TOP(433),
    K0434_PORT_STATE_CHANGE(434),
    K0435_FORWARDING_INFO(435),
    K0436_SWITCH_INFO(436),
    K0437_SWITCH_PROPERTIES(437),
    K0438_ROUTING_INFO(438),
    K0439_ROUTING_MODE(439),
    K0440_IP_ADDR(440),
    K0441_IPV4_ADDR(441),
    K0442_IPV6_ADDR(442),
    K0443_ADAPTIVE_ROUTING(443),
    K0444_NOT_SUPPORTED(444),
    K0445_ENABLE(445),
    K0446_PAUSE(446),
    K0447_LOST_ROUTES_ONLY(447),
    K0448_FREQUENCY(448),
    K0449_ALGORITHM(449),
    K0450_VL_PREEMPTING_LIMIT(450),
    K0451_FLOW_CONTROL_DISABLE_MASK(451),
    K0452_VL_PREEMPT_CAP(452),
    K0453_PORT_LID(453),
    K0454_LMC(454),
    K0455_PORT_LINK_MODE(455),
    K0456_PORT_LTP_CRC_MODE(456),
    K0457_LINK_CONNECTION(457),
    K0458_ACTIVE_DEFER(458),
    K0459_DEEP_SLEEP(459),

    // Link down reasons
    K0460_FM_BOUNCE(460),
    K0461_RECEIVE_ERROR(461),
    K0462_BAD_PACKET_LENGTH(462),
    K0463_PACKET_TOO_LONG(463),
    K0464_PACKET_TOO_SHORT(464),
    K0465_BAD_SOURCE_LID(465),
    K0466_BAD_DESTINATION_LID(466),
    K0467_BAD_L2(467),
    K0468_BAD_SC(468),
    K0469_BAD_MID_TAIL(469),
    K0470_BAD_VL_MARKER(470),
    K0471_BAD_HEAD_DIST(471),
    K0472_BAD_TAIL_DIST(472),
    K0473_BAD_CTRL_DIST(473),
    K0474_BAD_CREDIT_ACK(474),
    K0475_BAD_PREEMPT(475),
    K0476_BAD_CTRL_FLIT(476),
    K0477_PREMPT_ERROR(477),
    K0478_PREMPT_VL15(478),
    K0479_UNSUPPORTED_VL_MARKER(479),
    K0480_EXCEEDED_MULTICAST_LIMIT(480),
    K0481_EXCESSIVE_BUFFER_OVERRUN(481),
    K0482_REBOOT(482),
    K0483_NEIGHBOR_UNKNOWN(482),
    K0484_NEIGHBOR_DISABLED(484),
    K0485_NEIGHBOR_REBOOT(485),

    K0486_LIFETIME_VALUE(486),
    K0487_BUBBLE_ERROR(487),
    K0488_BUBBLE_HISTOGRAM(488),

    K0489_THRESHOLD(489),

    K0490_LINK_DOWN_REASON(490),
    K0491_DISCONNECTED(491),
    K0492_STANDARD(492),
    K0493_FIXED(493),
    K0494_VARIABLE(494),
    K0495_SILICON_PHOTONICS(495),
    K0496_PORT_TYPE(496),
    K0497_NEIGHBOR_MODE(497),
    K0498_MANAGEMENT_ALLOWED(498),
    K0499_FWD_AUTHENT_BYPASS(499),

    // Node Table Headings
    K0500_NODE_NAME(500),
    K0501_LID(501),
    K0502_REVISION(502),
    K0503_NUMBER_OF_PORTS(503),
    K0504_DEVICE_ID(504),

    // Connectivity Table Headings (partial)
    K0505_LINK_STATE(505),
    K0506_PHYSICAL_LINK_STATE(506),
    K0507_LINK_WIDTH_ENABLED(507),
    K0508_LINK_WIDTH_SUPPORTED(508),
    K0509_ACTIVE_LINK_SPEED(509),
    K0510_LINK_SPEED_ENABLED(510),
    K0511_LINK_SPEED_SUPPORTED(511),
    // K0512 is Available
    // K0513 is Available
    // K0514 is Available
    // K0515 is Available
    // K0516 is Available
    K0517_LINK_RECOVERIES(517),
    K0518_LINK_DOWN(518),
    K0519_RX_ERRORS(519),
    K0520_RX_REMOTE_PHY_ERRORS(520),
    K0521_TX_PORT_CONSTRAINT(521),
    K0522_RX_PORT_CONSTRAINT(522),
    // K0523 is Available
    K0524_INACTIVE(524),
    K0525_NEIGHBOR(525),
    // K0526 is Available
    // K0527 is Available
    // K0528 is Available
    // K0529 is Available
    K0530_SHOW_BORDER(530),
    K0531_HIDE_BORDER(531),
    K0532_ALT_ROWS(532),
    K0533_UNI_ROWS(533),

    // Setup Wizard Text.
    // Subnet Panel
    K0600_SUBNET_NAME(600),
    K0601_IP_HOST_ADDRESS(601),
    K0602_USER_NAME(602),
    K0603_USER_PASSWORD(603),
    K0604_AUTO_CONNECT(604),
    K0605_CONNECT_TO_SUBNET(605),
    K0606_CONNECTING(606),
    // Event Rules Panel
    K0607_SELECT_EVENT(607),
    K0608_NEW(608),
    K0609_RENAME(609),
    K0610_DELETE(610),
    K0611_EDIT(611),
    K0612_DELETE_EVENT(612),
    K0613_REMOVE_EVENT(613),
    K0614_RENAME_EVENT(614),
    // Event Wizard Panel
    K0615_CHASSIS_ADD(615),
    K0616_CHASSIS_REMOVE(616),
    K0617_MODULE_ADD(617),
    K0618_MODULE_REMOVE(618),
    K0619_WIZARD(619),
    K0620_SELECT_SEVERITY_LEVEL(620),
    K0621_CANCEL(621),
    K0622_NEXT(622),
    K0623_EVENT_SUMMARY(623),
    K0624_BACK(624),
    K0625_SELECT_ACTION(625),
    K0626_DIALOG(626),
    K0627_FINISH(627),
    K0628_ENTER_NAME(628),
    K0629_SELECT_SUBNET(629),
    // Logging Config Panel
    K0630_DEBUG(630),
    K0631_INFO(631),
    K0632_FATAL(632),
    K0633_OUTPUT_DESTINATION(633),
    K0634_DESTINATION_LIST(634),
    K0635_DESTINATION_CONFIGURATION(635),
    K0636_INFORMATION_LEVEL(636),
    K0637_OUTPUT_FORMAT(637),
    K0638_MAX_FILE_SIZE(638),
    K0639_MB(639),
    K0640_MAX_NUM_FILE(640),
    K0641_FILE_LOC(641),
    K0642_BROWSE(642),
    K0643_SELECT_FILE(643),
    // RenameEventDialog
    K0644_NEW_EVENT_NAME(644),
    K0645_OK(645),
    // ConversionPatternHelpDialog
    K0646_CONVERSION_CHARACTER(646),
    K0647_EFFECT(647),
    K0648_OUTPUT_FORMAT_HELP(648),
    K0649_SC(649),
    K0650_C(650),
    K0651_D(651),
    K0652_F(652),
    K0653_SL(653),
    K0654_L(654),
    K0655_SM(655),
    K0656_M(656),
    K0657_SN(657),
    K0658_SP(658),
    K0659_SR(659),
    K0660_ST(660),
    K0661_SX(661),
    K0662_DOUBLE_PERCENT(662),
    K0663_CONVERSION_PATTERN(663),
    K0664_PREVIEW(664),
    K0665_ENTER_FORMAT(665),
    K0666_SAMPLE_FORMATTED(666),
    // SetupWizard
    K0667_CONFIG_SETUP_WIZARD(667),
    K0668_EVENT_RULES(668),
    K0669_LOGGING(669),
    K0670_PREV(670),
    K0671_CONFIRM(671),
    K0672_APPLY(672),
    // EventWizardPanelController
    K0673_EVENT_CLASS(673),
    K0674_EVENT_TYPE(674),
    K0675_ACTION(675),
    // EventRule
    K0676_SUBNET_TOPO_CHANGE(676),
    K0677_PORT_ACTIVE(677),
    K0678_PORT_INACTIVE(678),
    K0679_FE_CONN_LOST(679),
    K0680_SM_CONN_LOST(680),
    K0681_SM_CONN_EST(681),
    K0682_DISPLAY_MESSAGE(682),
    K0683_EMAIL_MESSAGE(683),
    K0684_SUBNET_EVENTS(684),
    K0685_MISC_EVENTS(685),
    K0686_DEF_TOPO_CHANGE(686),
    K0687_DEF_FE_DOWN(687),
    // LoggingInputValidator
    K0688_FORMAT_STRING_EMPTY(688),
    K0689_WIZARD(689),
    // Logging Config Panel
    K0690_LAYOUT(690),
    K0691_PATTERN_LAYOUT(691),
    K0692_ENHANCED_PATTERN(692),
    K0693_PATTERN_LAYOUT_FULL(693),
    K0694_ENHANCED_PATTERN_FULL(694),
    K0695_KB(695),
    K0696_GB(696),
    K0697_BYTE(697),
    K0698_ALL(698),
    K0699_OFF(699),

    // Performance Performance sub page K0700 - K0799
    // K0700 is Available
    K0701_BYTE_PER_SEC(701),
    K0702_KB_PER_SEC(702),
    K0703_MB_PER_SEC(703),
    K0704_GB_PER_SEC(704),
    K0705_PORT_COUNTERS(705),
    K0706_REC_PORT(706),
    // K0707 is Available
    K0708_CONSTRAINT(708),
    K0709_REC_ERRS(709),
    K0710_REC_CUM(710),
    K0711_TRAN_COUNTERS(711),
    K0712_TRAN_ERRS(712),
    K0713_TRAN_CUM(713),
    K0714_TRAN_DISCARDS(714),
    K0715_OTHER_COUNTERS(715),
    K0716_UNCORR_ERR(716),
    K0717_REC_SW_REL_ERR(717),
    K0718_LOCAL_LINK_INTEG_ERR(718),
    K0719_EXCESS_BUFF_OVERRUNS(719),
    K0720_FM_CONFIG_ERR(720),
    K0721_ERRORS(721),
    K0722_MB(722),

    K0723_RX_PORT(723),
    // K0724 is Available
    // K0725 is Available
    K0726_RX_PKTS_RATE(726),
    K0727_RX_DATA_RATE(727),
    K0728_RX_CUMULATIVE_PACKETS(728),
    K0729_RX_CUMULATIVE_DATA(729),
    // K0730 is Available
    K0731_TX_DISCARDS(731),
    // K0732 is Available
    K0733_TX_PKTS_RATE(733),
    K0734_TX_CUMULATIVE_PACKETS(734),
    K0735_TX_CUMULATIVE_DATA(735),
    K0736_TX_DATA_RATE(736),
    K0737_FM_CONFIG_ERRRORS(737),
    // Logging Config Panel appender change warning dialog
    K0738_APPENDER_CONFIG_CHANGED(738),
    // Setup Wizard title for non-initial setup.
    K0739_CONFIG_SETUP(739),
    // Setup Wizard Close Button
    K0740_CLOSE(740),
    K0741_COUNTERS(741),
    K0742_THOUSAND(742),
    K0743_MILLION(743),
    K0744_BILLION(744),
    K0745_TRANSMIT(745),
    K0746_RECEIVE(746),
    K0747_DATA_TYPE(747),
    K0748_FLITS(748),

    K0750_PPS(750),
    K0751_KPPS(751),
    K0752_MPPS(752),
    K0753_GPPS(753),

    K0757_OFFLINE_DISABLED_REASON(757),
    K0758_READONLY(758),
    K0759_HIDE(759),
    K0760_SECURE(760),
    K0761_M_KEY_PROTECT(761),
    K0762_PORT_UNSLEEP_STATE(762),
    K0763_INTERLEAVE(763),
    K0764_PREEMPTION(764),
    K0765_DISTANCE_SUP(765),
    K0766_DISTANCE_ENA(766),
    K0767_MAX_NEST_LVL_TX_ENA(767),
    K0768_MAX_NEST_LVL_RX_SUP(768),
    K0769_MIN_INITIAL(769),
    K0770_MIN_TAIL(770),
    K0771_LARGE_PKT_LIM(771),
    K0772_SMALL_PKT_LIM(772),
    K0773_MAX_SMALL_PKT_LIM(773),
    K0774_PREEMPTION_LIM(774),
    K0775_FLIT_CTRL(775),
    K0776_EXCESSIVE_BUFF_OVERRUN(776),
    K0777_FM_CONFIG_ERROR(777),
    K0778_PORT_RCV_ERROR(778),
    K0779_EXCEEDMULTICASTLIMIT(779),
    K0780_BADCONTROLFLIT(780),
    K0781_BADPREEMPT(781),
    K0782_UNSUPPORTEDVLMARKER(782),
    K0783_BADCRDTACK(783),
    K0784_BADCTRLDIST(784),
    K0785_BADTAILDIST(785),
    K0786_BADHEADDIST(786),
    K0787_BADVLMARKER(787),
    K0788_PREEMPTVL15(788),
    K0789_PREEMPTERROR(789),
    K0790_BADMIDTAIL(790),
    K0791_RESERVED(791),
    K0792_BADSC(792),
    K0793_BADL2(793),
    K0794_BADDLID(794),
    K0795_BADSLID(795),
    K0796_PKTLENTOOSHORT(796),
    K0797_PKTLENTOOLONG(797),
    K0798_BADPKTLEN(798),
    K0799_NO_ACTIONS(799),

    K0800_SWITCH_PORTS(800),
    K0801_HCA_PORTS(801),
    K0802_NODES_SKIPPED(802),
    K0803_PORTS_SKIPPED(803),
    K0804_LINKS(804),
    K0805_TCA_PORTS(805),
    K0806_NODES_FAILED(806),
    K0807_EVENTS(807),

    K0810_ACTIVE_OPTIM_ENA(810),
    K0811_PASSTHRU_ENA(811),
    K0812_VL_MARKER_ENA(812),
    K0814_16B_TRAP_QUERY_ENA(814),
    K0815_PORT_MODE(815),
    K0816_LOCAL_PORT_NUM(816),
    K0817_BUFFER_UNITS(817),
    K0818_VL15_INIT(818),
    K0819_VL15_CREDIT_RATE(819),
    K0820_CREDIT_ACK(820),
    K0821_BUFF_ALLOC(821),
    K0822_MISCELLANEOUS(822),
    K0823_CLIENT_REREGISTER(823),
    K0824_MULT_PKEY_TRAP(824),
    K0825_MULTICAST_MASK(825),
    K0826_COLLECT_MASK(826),
    K0827_PACKET_FORMAT(827),

    K0828_REC_PACKETS_RATE(828),
    K0829_TRAN_PACKETS_RATE(829),
    K0830_REC_DATA_RATE(830),
    K0831_TRAN_DATA_RATE(831),
    K0832_RATE_MEANING(832),

    K0833_TX_MULTICAST_PACKETS(833),
    K0834_RX_MULTICAST_PACKETS(834),
    K0835_SW_PORT_CONG(835),
    K0836_TX_WAIT(836),
    K0837_RX_FECN(837),
    K0838_RX_BECN(838),
    K0839_TX_TIME_CONG(839),
    K0840_TX_WASTED_BW(840),
    K0841_TX_WAIT_DATA(841),
    K0842_RX_BUBBLE(842),
    K0843_MARK_FECN(843),
    // K0844_LINK_ERR_REC(844),
    // K0845_LINK_DOWN(845),
    // K0846_LINK_QUAL_IND(846),

    // extra property text
    K0900_LED_ENABLED(900),

    K1000_TOPOLOGY(1000),
    K1001_TOPOLOGY_DESCRIPTION(1001),
    K1002_ZOOM_IN(1002),
    K1003_ZOOM_OUT(1003),
    K1004_FIT_WINDOW(1004),
    K1005_LAYOUT(1005),
    K1006_RESET(1006),
    K1007_UNDO(1007),
    K1008_REDO(1008),
    K1009_FORCE_DIRECTED(1009),
    K1010_FORCE_DIRECTED_DESCRIPTION(1010),
    K1011_HIERARCHICAL(1011),
    K1012_HIERARCHICAL_DESCRIPTION(1012),
    K1013_TREE_CIRCLE(1013),
    K1014_TREE_CIRCLE_DESCRIPTION(1014),
    K1015_TREE_SLASH(1015),
    K1016_TREE_SLASH_DESCRIPTION(1016),
    K1017_TREE_LINE(1017),
    K1018_TREE_LINE_DESCRIPTION(1018),
    K1019_EXPAND_ALL(1019),
    K1020_COLLAPSE_ALL(1020),
    K1021_RESOURCE_DETAILS(1021),
    K1022_RESOURCE_DETAILS_DESCRIPTION(1022),
    K1023_LINK_RESOURCE_DESCRIPTION(1023),
    K1024_NODE_RESOURCE(1024),
    K1025_NODE_RESOURCE_DESCRIPTION(1025),
    K1026_PORT_RESOURCE(1026),
    K1027_PORT_RESOURCE_DESCRIPTION(1027),
    K1028_ROUTE_RESOURCE(1028),
    K1029_ROUTE_RESOURCE_DESCRIPTION(1029),
    K1030_GROUP_NAME(1030),
    K1031_LINK_STATISTICS(1031),
    K1032_LINK_HEALTH(1032),
    K1033_TOP_OVERVIEW(1033),
    K1034_TOP_OVERVIEW_DESCRIPTION(1034),
    K1035_CONFIGURATION_PORT(1035),
    K1036_LAST_SUBNET_STATUS(1036),
    K1037_PRIORITY(1037),
    K1038_INITIAL_PRIORITY(1038),
    K1039_SM_STATE(1039),
    K1040_PORT_ERROR_ACTION(1040),
    K1041_PORT_ERROR_ACTIONS(1041),
    K1044_COMMAND_TITLE(1044),
    K1045_SEND(1045),
    K1046_CURRENT_TAB(1046),
    K1047_NEW_TAB(1047),
    K1048_CONSOLE_COMMAND_PROMPT(1048),
    K1049_PASSWORD(1049),
    K1050_CONSOLE_LOGIN(1050),
    K1051_LOCK(1051),
    K1052_UNLOCK(1052),
    K1053_SERVER_INFO(1053),
    K1054_LOCKED(1054),
    K1055_INSPECT(1055),
    K1056_FAST_FABRIC_ASSISTANT(1056),
    K1057_ADMIN(1057),
    K1058_ADMIN_DESCRIPTION(1058),
    K1059_SUDO(1059),
    K1060_OUTLINE(1060),
    K1061_ENLARGE(1061),
    K1062_SEL_RESOURCES(1062),
    K1063_TOP_OUTLINE(1063),
    K1064_SESSION_AUTHENTICATION(1064),
    K1065_ENTER_PASSWORD(1065),

    K1066_MTU_SERIES(1066),
    K1067_NUM_VL(1067),
    K1068_MTU(1068),
    K1069_HOQLIFE(1069),
    K1070_VLSTALL_SERIES(1070),
    K1071_QSFP_CABLE_INFO(1071),
    K1072_CABLE_ID(1072),
    K1073_CABLE_EXT_ID(1073),
    K1074_CABLE_CONNECTOR(1074),
    K1075_CABLE_NOMINAL_BR(1075),
    K1076_CABLE_SMF_LEN(1076),
    K1077_CABLE_OM3_LEN(1077),
    K1078_CABLE_OM2_LEN(1078),
    K1079_CABLE_OM1_LEN(1079),
    K1080_CABLE_COPPER_LEN(1080),
    K1081_CABLE_DEVICE_TECH(1081),
    K1082_CABLE_VENDOR_NAME(1082),
    K1083_CABLE_EXT_MODULE(1083),
    K1084_CABLE_VENDOR_OUI(1084),
    K1085_CABLE_VENDOR_PN(1085),
    K1086_CABLE_VENDOR_REV(1086),
    K1087_CABLE_OPTICAL_WL(1087),
    K1088_CABLE_MAXCASE_TEMP(1088),
    K1089_CABLE_CC_BASE(1089),
    K1090_CABLE_RX_OUT_AMP_PROG(1090),
    K1091_CABLE_RX_SQULECH_DIS_IMP(1091),
    K1092_CABLE_RX_OUT_DIS_CAP(1092),
    K1093_CABLE_TX_SQUELCH_DIS_IMP(1093),
    K1094_CABLE_TX_SQUELCH_IMP(1094),
    K1095_CABLE_MEM_PAGE02_PROV(1095),
    K1096_CABLE_MEM_PAGE01_PROV(1096),
    K1097_CABLE_TX_DIS_IMP(1097),
    K1098_CABLE_TX_FAULT_REP_IMP(1098),
    K1099_CABLE_LOS_REP_IMP(1099),
    K1100_CABLE_VENDOR_SN(1100),
    K1101_CABLE_DATA_CODE(1101),
    K1102_CABLE_LOT_CODE(1102),
    K1103_CABLE_CC_EXT(1103),

    K1104_SC2SL_MAPPING_TABLE(1104),
    K1105_SC(1105),
    K1106_SL(1106),
    K1107_SC2VLT_MAPPING_TABLE(1107),
    K1108_SC2VLNT_MAPPING_TABLE(1108),
    K1109_VLT(1109),
    K1110_VLNT(1110),
    K1111_LINK_DOWN_ERROR_LOG(1111),
    K1112_HOURS(1112),
    K1113_HISTORY_SCOPE(1113),
    K1114_CURRENT(1114),
    K1115_HISTORY_TYPE(1115),

    K1300_NEIGHBOR_LINKDOWN_REASON(1300),

    K1340_LINKDOWN_SPEED_POLICY(1340),
    K1341_LINKDOWN_WIDTH_POLICY(1341),
    K1349_LINKDOWN_DISCONNECTED(1349),
    K1350_LINKDOWN_LOCAL_MEDIA_NOT_INSTALLED(1350),
    K1351_LINKDOWN_NOT_INSTALLED(1351),
    K1352_LINKDOWN_CHASSIS_CONFIG(1352),
    K1354_LINKDOWN_END_TO_END_NOT_INSTALLED(1354),
    K1356_LINKDOWN_POWER_POLICY(1356),
    K1357_LINKDOWN_LINKSPEED_POLICY(1357),
    K1358_LINKDOWN_LINKWIDTH_POLICY(1358),
    K1360_LINKDOWN_SWITCH_MGMT(1360),
    K1361_LINKDOWN_SMA_DISABLED(1361),
    K1363_LINKDOWN_TRANSIENT(1363),

    K1440_LINKDOWN_SPEED_POLICY_DESC(1440),
    K1441_LINKDOWN_WIDTH_POLICY_DESC(1441),
    K1449_LINKDOWN_DISCONNECTED_DESC(1449),
    K1450_LINKDOWN_LOCAL_MEDIA_NOT_INSTALLED_DESC(1450),
    K1451_LINKDOWN_NOT_INSTALLED_DESC(1451),
    K1452_LINKDOWN_CHASSIS_CONFIG_DESC(1452),
    K1454_LINKDOWN_END_TO_END_NOT_INSTALLED_DESC(1454),
    K1456_LINKDOWN_POWER_POLICY_DESC(1456),
    K1457_LINKDOWN_LINKSPEED_POLICY_DESC(1457),
    K1458_LINKDOWN_LINKWIDTH_POLICY_DESC(1458),
    K1460_LINKDOWN_SWITCH_MGMT_DESC(1460),
    K1461_LINKDOWN_SMA_DISABLED_DESC(1461),
    K1463_LINKDOWN_TRANSIENT_DESC(1463),

    K1601_PORT_LINK_TX_ACTIVE(1601),
    K1602_PORT_LINK_RX_ACTIVE(1602),

    K1610_QUALITY_EXCELLENT(1610),
    K1611_QUALITY_VERY_GOOD(1611),
    K1612_QUALITY_GOOD(1612),
    K1613_QUALITY_POOR(1613),
    K1614_QUALITY_BAD(1614),
    K1615_QUALITY_NONE(1615),
    K1616_QUALITY_EXCELLENT_DESC(1616),
    K1617_QUALITY_VERY_GOOD_DESC(1617),
    K1618_QUALITY_GOOD_DESC(1618),
    K1619_QUALITY_POOR_DESC(1619),
    K1620_QUALITY_BAD_DESC(1620),
    K1621_QUALITY_NONE_DESC(1621),

    K1630_SEL_UTILIZATION_HIGH(1630),
    K1631_SEL_PACKET_RATE_HIGH(1631),
    K1632_SEL_UTILIZATION_LOW(1632),
    K1633_SEL_INTEGRITY_ERRORS_HIGH(1633),
    K1634_SEL_CONGESTION_ERRORS_HIGH(1634),
    K1635_SEL_SMA_CONGESTION_ERRORS_HIGH(1635),
    K1636_SEL_BUBBLE_ERRORS_HIGH(1636),
    K1637_SEL_SECURITY_ERRORS_HIGH(1637),
    K1638_SEL_ROUTING_ERRORS_HIGH(1638),

    K1750_NO_SPECIFIED_REASON(1750),
    K1751_OFFDIS_DISCONNECTED(1751),
    K1752_OFFDIS_LOCAL_MEDIA_NOT_INSTALLED(1752),
    K1753_OFFDIS_NOT_INSTALLED(1753),
    K1754_OFFDIS_CHASSIS_CONFIG(1754),
    K1755_OFFDIS_END_TO_END_NOT_INSTALLED(1755),
    K1756_OFFDIS_POWER_POLICY(1756),
    K1757_OFFDIS_LINKSPEED_POLICY(1757),
    K1758_OFFDIS_LINKWIDTH_POLICY(1758),
    K1759_OFFDIS_SWITCH_MGMT(1759),
    K1760_OFFDIS_SMA_DISABLED(1760),
    K1761_OFFDIS_TRANSIENT(1761),

    K1780_LINK_INIT_REASON(1780),
    K1781_LINK_INIT_LINKUP(1781),
    K1782_LINK_INIT_FLAPPING(1782),
    K1788_LINK_INIT_OUTSIDE_POLICY(1788),
    K1789_LINK_INIT_QUARANTINED(1789),
    K1790_LINK_INIT_INSUFIC_CAPABILITY(1790),

    K2000_CERT_CONF(2000),
    K2001_KEY_STORE(2001),
    K2002_TRUST_STORE(2002),
    K2003_SECURE_CONNECT(2003),
    K2004_CONNECTION(2004),

    K2063_OVERALL_SUMMARY(2063),
    K2064_TOP_SUMMARY(2064),
    K2065_TOP_ARCH(2065),
    K2066_SLOW_PORTS(2066),
    K2067_DEG_PORTS(2067),
    K2068_LINK_QUALITY(2068),
    K2069_UP_PORTS(2069),
    K2070_DOWN_PORTS(2070),
    K2071_OTHER_PORTS(2071),
    K2072_SLOW_UP_PORTS(2072),
    K2073_SLOW_DOWN_PORTS(2073),
    K2074_DEG_UP_PORTS(2074),
    K2075_DEG_DOWN_PORTS(2075),
    K2076_CONNECTED_HFIS(2076),
    K2077_NOT_IN_FABRIC(2077),
    K2078_DEVIE_SET(2078),
    K2080_PERFORMANCE_GROUP(2080),

    K2101_ADM_APPS(2101),
    K2102_ADM_APPS_DESC(2102),
    // K2103 is Available
    K2104_ADM_DGS_DESC(2104),
    K2105_ADM_VFS(2105),
    K2106_ADM_VFS_DESC(2106),
    K2107_ADM_CONSOLE(2107),
    K2108_ADM_CONSOLE_DESC(2108),
    K2109_ADM_LOG(2109),
    K2110_ADM_LOG_DESC(2110),
    K2111_NAME(2111),
    K2112_ATTRIBUTES(2112),
    K2113_ID(2113),
    K2114_MIN(2114),
    K2115_MAX(2115),
    K2116_MASK(2116),
    K2117_SEL_ATTR_TYPE(2117),
    K2120_VALIDATION_TYPE(2120),
    K2121_ISSUES(2121),
    K2122_SUGGESTION(2122),
    K2123_QUICK_FIX(2123),
    K2124_NAME_CHECK(2124),
    K2125_VALUE_CHECK(2125),
    K2127_REF_CHECK(2127),
    K2128_SAVING(2128),
    K2129_VALIDATING(2129),
    K2130_REMOVING(2130),
    K2131_DEPLOY(2131),
    K2132_DEPLOY_RESTART(2132),
    K2133_RESOURCES_SELECTION(2133),
    K2134_DEVICES(2134),
    K2135_OPTIONS(2135),
    K2136_USE_GUID(2136),
    K2137_SELECT(2137),
    K2138_INCLUDE(2138),
    K2139_POLICIES(2139),
    K2140_MEMBERS(2140),
    K2141_QOS(2141),

    K3000_SELECT_ACTIONS(3000),
    K3001_DEFAULT_USER(3001),
    K3002_WARN(3002),
    K3003_ROLLING_FILE_APPENDER(3003),
    K3004_SELECT(3004),
    K3005_PREFERENCES(3005),
    K03006_FE_CONN_EST(3006),
    K3007_REFRESH_RATE(3007),
    K3008_TIME_WINDOW(3008),
    K3009_NUM_WORST_NODES(3009),

    K3010_SAVE(3010),
    K3011_PLUS(3011),
    K3012_MINUS(3012),
    K3013_SUBNETS(3013),
    K3014_RUN(3014),
    K3015_DEFAULT_PORT(3015),
    K3016_CREATION(3016),
    K3017_EXISTING(3017),
    K3018_UNKNOWN_SUBNET(3018),
    K3019_WELCOME_MESSAGE(3019),
    K3020_WELCOME_INSTRUCTIONS(3020),
    K3021_WELCOME_STATUS(3021),
    K3022_HOST_CONNECTIVITY(3022),
    K3023_ENTRY_VALIDATION(3023),
    K3024_DATABASE_UPDATE(3024),
    K3025_CONFIGURATION_COMPLETE(3025),
    K3026_SUCCESSFUL(3026),
    K3027_TEST_CONNECTION(3027),
    K3028_NOT_TESTED(3028),
    K3029_STOP_TEST(3029),
    K3030_TESTING(3030),
    K3031_PASS(3031),
    K3032_FAIL(3032),
    K3033_CONNECTION_TEST(3033),
    K3034_CONNECTION_TYPE(3034),
    K3035_HOST_ADDER(3035),
    K3036_ADD_NEW_HOST(3036),
    K3037_FE_CONNECTION(3037),
    K3038_SECURITY_INFO(3038),
    K3039_MASTER_SM(3039),
    K3040_OR(3040),
    K3041_SSL(3041),
    K3042_HOST_PORT_BLANK(3042),

    K3100_ABOUT_DIALOG(3100),
    K3101_COPYRIGHT(3101),
    K3102_THIRD_PARTY_LIBS(3102),

    K3200_EXCESS_BUFF_OVERRUNS_DESCRIPTION(3200),
    K3201_FM_CONFIG_ERR_DESCRIPTION(3201),
    K3202_LINK_DOWN_DESCRIPTION(3202),
    K3203_LINK_RECOVERIES_DESCRIPTION(3203),
    K3204_LINK_QUALITY_DESCRIPTION(3204),
    K3205_LOCAL_LINK_INTEG_ERR_DESCRIPTION(3205),
    K3206_RX_PORT_CONSTRAINT_DESCRIPTION(3206),
    K3207_RX_CUMULATIVE_DATA_DESCRIPTION(3207),
    K3208_RX_ERRORS_DESCRIPTION(3208),
    K3209_RX_CUMULATIVE_PACKETS_DESCRIPTION(3209),
    K3210_RX_REMOTE_PHY_ERRORS_DESCRIPTION(3210),
    K3211_REC_SW_REL_ERR_DESCRIPTION(3211),
    K3212_TX_PORT_CONSTRAINT_DESCRIPTION(3212),
    K3213_TX_CUMULATIVE_DATA_DESCRIPTION(3213),
    K3214_TX_DISCARDS_DESCRIPTION(3214),
    K3215_TX_CUMULATIVE_PACKETS_DESCRIPTION(3215),
    K3216_RX_MULTICAST_PACKETS(3216),
    K3217_TX_MULTICAST_PACKETS(3217),
    K3218_RX_PACKETS_RATE_DESCRIPTION(3218),
    K3219_RX_DATA_RATE_DESCRIPTION(3219),
    K3220_TX_PACKETS_RATE_DESCRIPTION(3220),
    K3221_TX_DATA_RATE_DESCRIPTION(3221),

    K3300_BPS_DESCRIPTION(3300),
    K3301_KBPS_DESCRIPTION(3301),
    K3302_MBPS_DESCRIPTION(3302),
    K3303_GBPS_DESCRIPTION(3303),
    K3304_PPS_DESCRIPTION(3304),
    K3305_KPPS_DESCRIPTION(3305),
    K3306_MPPS_DESCRIPTION(3306),
    K3307_GPPS_DESCRIPTION(3307),

    K99999_END_OF_CONSTANTS(99999);

    private static final String STL_CONSTANTS_BUNDLE =
            "com.intel.stl.ui.common.constants";

    private static final String STL_CONSTANTS_ENCODING = "UTF-8";

    private static final Control STL_CONTROL = new UTFControl(
            STL_CONSTANTS_ENCODING);

    private static final ResourceBundle STL_CONSTANTS = ResourceBundle
            .getBundle(STL_CONSTANTS_BUNDLE, STL_CONTROL);

    private static Logger log = LoggerFactory.getLogger(STLConstants.class);

    private final int id;

    private final String key;

    private STLConstants(int id) {
        this.id = id;
        this.key = String.format("K%04d", id);
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        try {
            return STL_CONSTANTS.getString(key);
        } catch (MissingResourceException mre) {
            String message = "Constant '" + key + "' not found!";
            log.error(message);
            return message;
        }
    }

    public String getValue(Object... arguments) {
        try {
            return MessageFormat
                    .format(STL_CONSTANTS.getString(key), arguments);
        } catch (MissingResourceException mre) {
            String message = "Constant '" + key + "' not found!";
            log.error(message);
            return message;
        }
    }
}
