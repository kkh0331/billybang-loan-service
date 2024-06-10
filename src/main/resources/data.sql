INSERT INTO providers (provider_name, img_url, representative_name, established_at, provider_size, provider_type, credit_level, employee_count, industry_detail) VALUES
('신한은행', 'https://www.shinhancard.com/pconts/company/images/contents/shc_symbol_ci.png', '정상혁', '1897-02-19', '대기업', '외감', '최상 (2024.04)', 13263, '국내은행');

INSERT INTO financial_statements (provider_id, year, sales_amount, business_profit, net_profit, total_liabilities, total_capital, total_assets) VALUES
(1, 2023, 35751798000000, 41471970000000, 30679910000000, 474966063000000, 33531213000000, 508497276000000),
(1, 2022, 33810874000000, 41627500000000, 30457320000000, 460814132000000, 31167260000000, 491981392000000),
(1, 2021, 22191669000000, 35867170000000, 24948940000000, 438199575000000, 29235638000000, 467435213000000);