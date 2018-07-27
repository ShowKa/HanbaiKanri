INSERT INTO m_busho
(code, busho_kubun, jigyo_kubun, name, record_id)
VALUES
('BS01', '01', '01', '部署01', 'r-BS01'),
('BS02', '99', '02', '部署02', 'r-BS02'),
;

INSERT INTO m_busho_date
(busho_id, eigyo_date, record_id)
VALUES
('r-BS01', to_date('2017/08/20', 'yyyy/MM/dd'), 'r-BS01'),
('r-BS02', to_date('2017/08/20', 'yyyy/MM/dd'), 'r-BS02'),
;

INSERT INTO m_kokyaku
(code, name, address, hanbai_kubun, kokyaku_kubun, shukan_busho_id, record_id)
VALUES
-- 販売区分=現金
('KK01', '顧客01', '左京区1丁目', '00', '01', 'r-BS01', 'r-KK01'),
-- 販売区分=掛売
('KK02', '顧客02', '上京区2丁目', '10', '01', 'r-BS02', 'r-KK02'),
;

INSERT INTO m_nyukin_kake_info
(kokyaku_id, nyukin_hoho_kubun, nyukin_tsuki_kubun, shimebi, nyukin_date, record_id)
VALUES
-- 入金方法=振込, 請求締日=20日, 入金=翌月30日
('r-KK02', '00', '10', '20', '30', 'r-KK02'),
;

INSERT INTO m_furikomi_irainin
(kokyaku_id, furikomi_irainin_name, record_id)
VALUES
-- 顧客02
('r-KK02', '顧客02振込依頼人1', 'r-KK02-1'),
('r-KK02', '顧客02振込依頼人2', 'r-KK02-2'),
;

INSERT INTO m_user
(id, username, password, record_id)
VALUES
(1, 'user01', 'pass', 'r-user01'),
(2, 'user02', 'pass', 'r-user02'),
;

INSERT INTO m_shain
(code, name, shozoku_busho_id, record_id)
VALUES
('user01', '社員01', 'r-BS01', 'r-user01'),
('user02', '社員02', 'r-BS02', 'r-user02'),
;

INSERT INTO m_shohin
(code, name, hyojun_tanka, record_id)
VALUES
('SH01', '商品01', 1000, 'r-SH01'),
('SH02', '商品02', 1001, 'r-SH02'),
('SH03', '商品03', 1002, 'r-SH03'),
;

INSERT INTO t_uriage
(kokyaku_id, denpyo_number, uriage_date, keijo_date, hanbai_kubun, shohizeiritsu, record_id)
VALUES
-- 顧客01, 販売区分=現金
('r-KK01', '00001', to_date('2017/08/19', 'yyyy/MM/dd'), to_date('2017/08/19', 'yyyy/MM/dd'), '00', 0.08, 'r-KK01-00001'),
-- 顧客02, 販売区分=掛売
('r-KK02', '00001', to_date('2017/08/20', 'yyyy/MM/dd'), to_date('2017/08/20', 'yyyy/MM/dd'), '10', 0.08, 'r-KK02-00001'),
('r-KK02', '00002', to_date('2017/08/20', 'yyyy/MM/dd'), to_date('2017/08/20', 'yyyy/MM/dd'), '10', 0.08, 'r-KK02-00002'),
;

INSERT INTO t_uriage_meisai
(uriage_id, meisai_number, shohin_id, hanbai_number, hanbai_tanka, record_id)
VALUES
-- 顧客01, 伝票番号=00001
('r-KK01-00001', 1, 'r-SH01', 5, 1000, 'r-KK01-00001-1'),
('r-KK01-00001', 2, 'r-SH02', 5, 1001, 'r-KK01-00001-2'),
-- 顧客02, 伝票番号=00001
('r-KK02-00001', 1, 'r-SH01', 10, 1000, 'r-KK02-00001-1'),
('r-KK02-00001', 2, 'r-SH02', 10, 1001, 'r-KK02-00001-2'),
-- 顧客02, 伝票番号=00002
('r-KK02-00002', 1, 'r-SH01', 10, 1000, 'r-KK02-00002-1'),
;

INSERT INTO r_uriage
(uriage_id, uriage_date, keijo_date, hanbai_kubun, shohizeiritsu, record_id)
VALUES
-- 顧客01, 伝票番号=00001（19日計上分 + 20日訂正計上分）, 販売区分=現金
('r-KK01-00001', to_date('2017/08/19', 'yyyy/MM/dd'), to_date('2017/08/19', 'yyyy/MM/dd'), '00', 0.08, 'r-KK01-00001-20170819'),
('r-KK01-00001', to_date('2017/08/19', 'yyyy/MM/dd'), to_date('2017/08/20', 'yyyy/MM/dd'), '00', 0.08, 'r-KK01-00001-20170820'),
-- 顧客02, 伝票番号=00001, 販売区分=掛売
('r-KK02-00001', to_date('2017/08/20', 'yyyy/MM/dd'), to_date('2017/08/20', 'yyyy/MM/dd'), '10', 0.08, 'r-KK02-00001-20170820'),
-- 顧客02, 伝票番号=00002, 販売区分=掛売
('r-KK02-00002', to_date('2017/08/20', 'yyyy/MM/dd'), to_date('2017/08/20', 'yyyy/MM/dd'), '10', 0.08, 'r-KK02-00002-20170820'),
;

INSERT INTO r_uriage_meisai
(uriage_id, meisai_number, shohin_id, hanbai_number, hanbai_tanka, record_id)
VALUES
-- 顧客01, 伝票番号=00001 19日計上分明細（訂正前）
('r-KK01-00001-20170819', 1, 'r-SH01', 4, 1000, 'r-KK01-00001-20170819-1'),
('r-KK01-00001-20170819', 2, 'r-SH02', 4, 1001, 'r-KK01-00001-20170819-2'),
-- 顧客01, 伝票番号=00001 19日計上分明細（訂正後）
('r-KK01-00001-20170820', 1, 'r-SH01', 5, 1000, 'r-KK01-00001-20170820-1'),
('r-KK01-00001-20170820', 2, 'r-SH02', 5, 1001, 'r-KK01-00001-20170820-2'),
-- 顧客02, 伝票番号=00001
('r-KK02-00001-20170820', 1, 'r-SH01', 10, 1000, 'r-KK02-00001-20170820-1'),
('r-KK02-00001-20170820', 2, 'r-SH02', 10, 1001, 'r-KK02-00001-20170820-2'),
-- 顧客02, 伝票番号=00002
('r-KK02-00002-20170820', 1, 'r-SH01', 10, 1000, 'r-KK02-00002-20170820-2'),
;

-- 売上計上
INSERT INTO r_uriage_keijo
(uriage_rireki_id, busho_id, record_id)
VALUES
('r-KK01-00001-20170819', 'r-BS01', 'r-KK01-00001-20170819'),
;

-- 売掛
INSERT INTO t_urikake
(uriage_id, kingaku, nyukin_yotei_date, record_id)
VALUES
-- 翌月9/30入金予定
('r-KK02-00001', 21610, to_date('2017/09/30', 'yyyy/MM/dd') ,'r-KK02-00001'),
('r-KK02-00002', 10800, to_date('2017/09/30', 'yyyy/MM/dd') ,'r-KK02-00002'),
;

-- 入金
INSERT INTO t_nyukin
(id, kokyaku_id, busho_id, date, nyukin_hoho_kubun, kingaku, record_id)
VALUES
('r-001', 'r-KK02', 'r-BS02', to_date('2017/08/20', 'yyyy/MM/dd'), '10', 21610, 'r-001'),
('r-002', 'r-KK02', 'r-BS02', to_date('2017/08/20', 'yyyy/MM/dd'), '10', 10800, 'r-002'),
;

-- 消込
INSERT INTO t_keshikomi
(id, date, timestamp, kingaku, nyukin_id, urikake_id, record_id)
VALUES
('r-001', to_date('2017/08/20', 'yyyy/MM/dd'), to_date('2017/08/20', 'yyyy/MM/dd'), 1000,'r-001', 'r-KK02-00001', 'r-001'),
('r-002', to_date('2017/08/20', 'yyyy/MM/dd'), to_date('2017/08/20', 'yyyy/MM/dd'), 1000,'r-002', 'r-KK02-00001', 'r-002'),
;
-- 商品在庫
INSERT INTO t_shohin_zaiko
(busho_id, eigyo_date, shohin_id, number, record_id)
VALUES
('r-BS01', to_date('2017/08/20', 'yyyy/MM/dd'), 'r-SH01', 100, 'r-BS01-20170820-SH01'),
('r-BS01', to_date('2017/08/20', 'yyyy/MM/dd'), 'r-SH02', 101, 'r-BS01-20170820-SH02'),
('r-BS01', to_date('2017/08/20', 'yyyy/MM/dd'), 'r-SH03', 102, 'r-BS01-20170820-SH03'),
('r-BS02', to_date('2017/08/20', 'yyyy/MM/dd'), 'r-SH01', 200, 'r-BS02-20170820-SH01'),
('r-BS02', to_date('2017/08/20', 'yyyy/MM/dd'), 'r-SH02', 201, 'r-BS02-20170820-SH02'),
('r-BS02', to_date('2017/08/20', 'yyyy/MM/dd'), 'r-SH03', 202, 'r-BS02-20170820-SH03'),
;

-- 商品移動
INSERT INTO t_shohin_ido
(id, busho_id, date, kubun, sagyo_timestamp, record_id)
VALUES
-- 売上
('r-001', 'r-BS01', to_date('2017/08/20', 'yyyy/MM/dd'), '10', CURRENT_TIMESTAMP(), 'r-001'),
-- 入荷
('r-002', 'r-BS01', to_date('2017/08/20', 'yyyy/MM/dd'), '00', CURRENT_TIMESTAMP(), 'r-002'),
;

-- 商品移動明細
INSERT INTO t_shohin_ido_meisai
(shohin_ido_id, meisai_number, shohin_id, number, record_id)
VALUES
('r-001', 1, 'r-SH01', 10, 'r-001-1'),
('r-001', 2, 'r-SH02', 10, 'r-001-2'),
('r-002', 1, 'r-SH02',  5, 'r-002-1'),
('r-002', 2, 'r-SH03', 10, 'r-002-2'),
;