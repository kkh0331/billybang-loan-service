INSERT INTO providers (provider_name, img_url, representative_name, established_at, provider_size, provider_type, credit_level, employee_count, industry_detail) VALUES
('더미은행1', 'https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png', '정상혁', '1897-02-19', '대기업', '외감', '최상 (2024.04)', 13263, '국내은행'),
('더미은행2', 'https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png', '정상혁', '1897-02-19', '대기업', '외감', '최상 (2024.04)', 13263, '국내은행'),
('더미은행3', 'https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png', '정상혁', '1897-02-19', '대기업', '외감', '최상 (2024.04)', 13263, '국내은행'),
('더미은행4', 'https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png', '정상혁', '1897-02-19', '대기업', '외감', '최상 (2024.04)', 13263, '국내은행'),
('더미은행5', 'https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png', '정상혁', '1897-02-19', '대기업', '외감', '최상 (2024.04)', 13263, '국내은행');

INSERT INTO financial_statements (provider_id, year, sales_amount, business_profit, net_profit, total_liabilities, total_capital, total_assets) VALUES
(1, 2023, 35751798000000, 41471970000000, 30679910000000, 474966063000000, 33531213000000, 508497276000000),
(1, 2022, 33810874000000, 41627500000000, 30457320000000, 460814132000000, 31167260000000, 491981392000000),
(1, 2021, 22191669000000, 35867170000000, 24948940000000, 438199575000000, 29235638000000, 467435213000000),
(2, 2023, 35751798000000, 41471970000000, 30679910000000, 474966063000000, 33531213000000, 508497276000000),
(2, 2022, 33810874000000, 41627500000000, 30457320000000, 460814132000000, 31167260000000, 491981392000000),
(2, 2021, 22191669000000, 35867170000000, 24948940000000, 438199575000000, 29235638000000, 467435213000000);

INSERT INTO financial_indicators (provider_id, year, profit_score, stable_score, growth_score) VALUES
(5, 2023, 5.1, 9.0, 15.1),
(1, 2022, 5.2, 8.9, 15.2),
(4, 2023, 5.3, 8.8, 15.3),
(2, 2022, 5.4, 8.7, 15.4),
(3, 2023, 5.5, 8.6, 15.5),
(3, 2022, 5.6, 8.5, 15.6),
(2, 2023, 5.7, 8.4, 15.7),
(4, 2022, 5.8, 8.3, 15.8),
(1, 2023, 5.9, 8.2, 15.9),
(5, 2022, 6.0, 8.1, 16.0);