INSERT INTO m_busho
(code, busho_kubun, jigyo_kubun, name, record_id)
VALUES
('BS01', '01', '01', '部署01', 'BS01'),
('BS02', '99', '02', '部署02', 'BS02');


INSERT INTO m_kokyaku
(code, name, address, hanbai_kubun, kokyaku_kubun, shukan_busho_id, record_id)
VALUES
('KK01', 'aaaa', '左京区', '00', '01', 'BS01', 'KK01'),
('KK02', 'aaaa', '右京区', '00', '01', 'BS02', 'KK02'),
('KK03', 'bbbb', '上京区', '10', '01', 'BS02', 'KK03');

INSERT INTO m_nyukin_kake_info
(kokyaku_id, nyukin_hoho_kubun, nyukin_tsuki_kubun, shimebi, nyukin_date, record_id)
VALUES
('KK03', '00', '01', '20', '30', 'KK03');

INSERT INTO m_user
(id, username, password, record_id)
VALUES
(1, 'user01', 'pass', 'user01');