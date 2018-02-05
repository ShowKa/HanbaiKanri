INSERT INTO m_busho
(code, busho_kubun, jigyo_kubun, name, record_id)
VALUES
('BS01', '01', '01', '部署01', 'BS01'),
('BS02', '99', '02', '部署02', 'BS02');

INSERT INTO m_busho_date
(busho_id, eigyo_date, record_id)
VALUES
('BS01', to_date('2017/08/20', 'yyyy/MM/dd'), 'BS01'),
('BS02', to_date('2017/08/20', 'yyyy/MM/dd'), 'BS02');

INSERT INTO m_kokyaku
(code, name, address, hanbai_kubun, kokyaku_kubun, shukan_busho_id, record_id)
VALUES
('KK01', 'aaaa', '左京区', '00', '01', 'BS01', 'KK01'),
('KK02', 'aaaa', '右京区', '00', '01', 'BS02', 'KK02'),
('KK03', 'bbbb', '上京区', '10', '01', 'BS02', 'KK03');

INSERT INTO m_nyukin_kake_info
(kokyaku_id, nyukin_hoho_kubun, nyukin_tsuki_kubun, shimebi, nyukin_date, record_id)
VALUES
('KK03', '00', '10', '20', '30', 'KK03');

INSERT INTO m_user
(id, username, password, record_id)
VALUES
(1, 'user01', 'pass', 'user01'),
(2, 'user02', 'pass', 'user02');

INSERT INTO m_shain
(code, name, shozoku_busho_id, record_id)
VALUES
('user01', '社員01', 'BS01', 'user01'),
('user02', '社員02', 'BS02', 'user02');

INSERT INTO m_shohin
(code, name, hyojun_tanka, record_id)
VALUES
('SH01', '商品SH01', 1000, 'SH01'),
('SH02', '商品SH02', 1001, 'SH02'),
('SH03', '商品SH03', 1002, 'SH03'),
;

INSERT INTO t_uriage
(kokyaku_id, denpyo_number, uriage_date, keijo_date, hanbai_kubun, shohizeiritsu, record_id)
VALUES
('KK01', '00001', to_date('2017/08/19', 'yyyy/MM/dd'), to_date('2017/08/19', 'yyyy/MM/dd'), '00', 0.08, 'KK01-00001'),
('KK01', '00002', to_date('2017/08/20', 'yyyy/MM/dd'), to_date('2017/08/20', 'yyyy/MM/dd'), '10', 0.08, 'KK01-00002');

INSERT INTO t_uriage_meisai
(uriage_id, meisai_number, shohin_id, hanbai_number, hanbai_tanka, record_id)
VALUES
('KK01-00001', 1, 'SH01', 5, 1000, 'KK01-00001-1'),
('KK01-00001', 2, 'SH02', 5, 1001, 'KK01-00001-2'),
('KK01-00002', 1, 'SH01', 10, 1000, 'KK01-00002-1'),
('KK01-00002', 2, 'SH02', 10, 1001, 'KK01-00002-2'),
;

INSERT INTO r_uriage
(uriage_id, uriage_date, keijo_date, hanbai_kubun, shohizeiritsu, record_id)
VALUES
('KK01-00001', to_date('2017/08/19', 'yyyy/MM/dd'), to_date('2017/08/19', 'yyyy/MM/dd'), '00', 0.08, 'KK01-00001-20170819'),
('KK01-00001', to_date('2017/08/19', 'yyyy/MM/dd'), to_date('2017/08/20', 'yyyy/MM/dd'), '00', 0.08, 'KK01-00001-20170820'),
('KK01-00002', to_date('2017/08/20', 'yyyy/MM/dd'), to_date('2017/08/20', 'yyyy/MM/dd'), '10', 0.08, 'KK01-00002-20170820'),
;

-- 商品在庫
INSERT INTO t_shohin_zaiko
(busho_id, eigyo_date, shohin_id, number, record_id)
VALUES
('BS01', to_date('2017/08/20', 'yyyy/MM/dd'), 'SH01', 100, 'BS01-20170820-SH01'),
('BS01', to_date('2017/08/20', 'yyyy/MM/dd'), 'SH02', 101, 'BS01-20170820-SH02'),
('BS01', to_date('2017/08/20', 'yyyy/MM/dd'), 'SH03', 102, 'BS01-20170820-SH03'),
('BS02', to_date('2017/08/20', 'yyyy/MM/dd'), 'SH01', 200, 'BS02-20170820-SH01'),
('BS02', to_date('2017/08/20', 'yyyy/MM/dd'), 'SH02', 201, 'BS02-20170820-SH02'),
('BS02', to_date('2017/08/20', 'yyyy/MM/dd'), 'SH03', 202, 'BS02-20170820-SH03'),
;

-- 商品移動
INSERT INTO t_shohin_ido
(id, busho_id, date, kubun, sagyo_timestamp, record_id)
VALUES
('r-001', 'BS01', to_date('2017/08/20', 'yyyy/MM/dd'), '10', CURRENT_TIMESTAMP(), 'r-001'),
('r-002', 'BS01', to_date('2017/08/20', 'yyyy/MM/dd'), '00', CURRENT_TIMESTAMP(), 'r-002'),
;

-- 商品移動明細
INSERT INTO t_shohin_ido_meisai
(shohin_ido_id, meisai_number, shohin_id, number, record_id)
VALUES
('r-001', 1, 'SH01', 10, 'r-001-1'),
('r-001', 2, 'SH02', 10, 'r-001-2'),
('r-002', 1, 'SH02',  5, 'r-002-1'),
('r-002', 2, 'SH03', 10, 'r-002-2'),
;