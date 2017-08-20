INSERT INTO m_busho
(code, busho_kubun, jigyo_kubun, name, record_id)
VALUES
('BS01', '00', '00', '部署01', 'BS01'),
('BS02', '00', '00', '部署02', 'BS02');


INSERT INTO m_kokyaku
(code, name, address, hanbai_kubun, kokyaku_kubun, shukan_busho_id, record_id)
VALUES
('KK01', 'aaaa', '左京区', '00', '01', 'BS01', 'KK01'),
('KK02', 'aaaa', '右京区', '00', '01', 'BS02', 'KK02'),
('KK03', 'bbbb', '上京区', '01', '01', 'BS02', 'KK03');

INSERT INTO m_nyukin_kake_info
(kokyaku_id, nyukin_hoho_kubun, nyukin_tsuki_kubun, shimebi, nyukin_date, record_id)
VALUES
('KK03', '00', '01', '20', '30', 'KK01');

INSERT INTO m_shohin
(code, name, standard_price, record_id)
VALUES
('SH01', '商品SH01', 1000, 'SH01'),
('SH02', '商品SH02', 1001, 'SH02');

INSERT INTO t_uriage
(kokyaku_id, denpyo_number, uriage_date, hanbai_kubun, record_id)
VALUES
('KK01', '00001', to_date('2017/08/20', 'yyyy/MM/dd'), '00', 'KK01-00001'),
('KK01', '00002', to_date('2017/08/20', 'yyyy/MM/dd'), '10', 'KK01-00002');
