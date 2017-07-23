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
('KK03', 'bbbb', '上京区', '00', '01', 'BS02', 'KK03');