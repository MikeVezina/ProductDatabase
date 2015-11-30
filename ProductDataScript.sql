DROP TABLE IF EXISTS manufacturers;

CREATE TABLE manufacturers (
	manufacturerID INTEGER NOT NULL PRIMARY KEY,
	manufacturerName STRING
);

-- Insert all manufacturer
INSERT INTO "manufacturers" VALUES (1, 'Apple');
INSERT INTO "manufacturers" VALUES (2, 'Microsoft');
INSERT INTO "manufacturers" VALUES (3, 'Logitech');
INSERT INTO "manufacturers" VALUES (4, 'LG');
INSERT INTO "manufacturers" VALUES (5, 'Seagate');
INSERT INTO "manufacturers" VALUES (6, 'Bose');


DROP TABLE IF EXISTS products;

CREATE TABLE products (
	productID INTEGER NOT NULL,
	manufacturerID INTEGER NOT NULL,
	productName STRING,
	releaseDate STRING,
	FOREIGN KEY(manufacturerID) REFERENCES manufacturers(manufacturerID),
	PRIMARY KEY(productID, manufacturerID)
);

-- Insert all Apple Products
INSERT INTO "products" VALUES(1, 1, "iPad Pro", "November 11, 2015");
INSERT INTO "products" VALUES(2, 1, "iPad Mini", "September 9, 2015");
INSERT INTO "products" VALUES(3, 1, "iPad Air 2", "October 22 2015");
INSERT INTO "products" VALUES(4, 1, "iPhone 6S", "September 25, 2015");
INSERT INTO "products" VALUES(5, 1, "iPhone 6S Plus", "October 22 2015");


-- Insert all Microsoft Products
INSERT INTO "products" VALUES(1, 2, "Surface 3", "May 5, 2015");
INSERT INTO "products" VALUES(2, 2, "Surface Book", "October 26, 2015");
INSERT INTO "products" VALUES(3, 2, "Surface Pro 4", "October 26, 2015");


-- Insert all Logitech Products
INSERT INTO "products" VALUES(1, 3, "M500 Corded Mouse", "2013");
INSERT INTO "products" VALUES(2, 3, "K360 Wireless Keyboard", "2012");
INSERT INTO "products" VALUES(3, 3, "MK550 Wireless Wave Combo", "2011");

-- Insert all LG Products
INSERT INTO "products" VALUES(1, 4, "24 Inch IPS LED Monitor", "2011");
INSERT INTO "products" VALUES(2, 4, "22 Inch IPS LED Monitor", "2011");
INSERT INTO "products" VALUES(3, 4, "F60 Smartphone", "September 2014");

-- Insert all Seagate Products
INSERT INTO "products" VALUES(1, 5, "500GB Portable External HDD", "January 14, 2015");
INSERT INTO "products" VALUES(2, 5, "1TB Desktop External HDD", "2013");
INSERT INTO "products" VALUES(3, 5, "250GB Laptop Internal HDD", "2011");

-- Insert all Bose Products
INSERT INTO "products" VALUES(1, 6, "QuietComfort 25 Noise Cancelling Over-Ear Headphones", "September 3, 2014");
INSERT INTO "products" VALUES(2, 6, "QuietComfort 20 Noise Cancelling In-Ear Headphones", "August 2013");
INSERT INTO "products" VALUES(3, 6, "SoundSport In-Ear Headphones", "September 25, 2014");



DROP TABLE IF EXISTS retailers;

CREATE TABLE retailers (
	retailerID INTEGER NOT NULL,
	retailerName STRING,
	website STRING
);

-- Insert all retailers
INSERT INTO "retailers" VALUES(1, 'Amazon', 'www.amazon.ca');
INSERT INTO "retailers" VALUES(2, 'Best Buy', 'www.bestbuy.ca');
INSERT INTO "retailers" VALUES(3, 'Staples', 'www.staples.ca');


DROP TABLE IF EXISTS sold_by;

CREATE TABLE sold_by (
	retailerID INTEGER NOT NULL,
	productID INTEGER NOT NULL,
	manufacturerID INTEGER NOT NULL,	
	price REAL,
	stock STRING,
	productURL STRING,
	productRating REAL,
	FOREIGN KEY(manufacturerID) REFERENCES manufacturers(manufacturerID),
	FOREIGN KEY(manufacturerID, productID) REFERENCES products(manufacturerID, productID),
	FOREIGN KEY(retailerID) REFERENCES retailers(retailerID),
	PRIMARY KEY(productID, manufacturerID, retailerID)
);

-- Products Sold By Amazon
INSERT INTO "sold_by" VALUES(1, 3, 1, 579.00, "In Stock", "http://www.amazon.ca/Apple-MH0W2LL-10-Inch-Retina-Display/dp/B00OTWOAAQ/ref=sr_1_2?s=pc&ie=UTF8&qid=1448821197&sr=1-2&keywords=iPad+Air+2", 5);
INSERT INTO "sold_by" VALUES(1, 2, 1, 399.97, "1 Remaining", "http://www.amazon.ca/Apple-MD531LL-Wi-Fi-White-Silver/dp/B00746W9F2/ref=sr_1_1?s=electronics&ie=UTF8&qid=1448821429&sr=1-1&keywords=iPad+Mini", 4.5);
INSERT INTO "sold_by" VALUES(1, 4, 1, 999.90, "In Stock", "http://www.amazon.ca/IPHONE-A1688-SILVER-FACTORY-UNLOCKED/dp/B015E8TKS0/ref=sr_1_3?s=electronics&ie=UTF8&qid=1448821562&sr=1-3&keywords=iPhone+6s", 5);
INSERT INTO "sold_by" VALUES(1, 1, 2, 529.99, "In Stock", "http://www.amazon.ca/Microsoft-Surface-64GB-Wireless-Display/dp/B015ZOMSZU/ref=sr_1_2?s=electronics&ie=UTF8&qid=1448821808&sr=1-2&keywords=Surface+3", 3.5);
INSERT INTO "sold_by" VALUES(1, 3, 2, 1320.99, "In Stock", "http://www.amazon.ca/Microsoft-Surface-Pro-i5-128GB/dp/B0166ZDO14/ref=sr_1_1?s=electronics&ie=UTF8&qid=1448822077&sr=1-1&keywords=Surface+Pro+4", 4);
INSERT INTO "sold_by" VALUES(1, 1, 3, 36.98, "In Stock", "http://www.amazon.ca/Logitech-910-001204-Corded-Mouse-M500/dp/B002B3YCQM/ref=sr_1_1?s=electronics&ie=UTF8&qid=1448822192&sr=1-1&keywords=m500+corded+mouse", 5);
INSERT INTO "sold_by" VALUES(1, 2, 3, 29.00, "1 Remaining", "http://www.amazon.ca/Logitech-Wireless-Keyboard-K360-920-004088/dp/B007PJ4PN2/ref=sr_1_1?s=electronics&ie=UTF8&qid=1448822233&sr=1-1&keywords=k360", 4.5);
INSERT INTO "sold_by" VALUES(1, 3, 3, 49.99, "In Stock", "http://www.amazon.ca/Logitech-Wireless-Combo-French-Layout/dp/B0040QMBAW/ref=sr_1_2?s=electronics&ie=UTF8&qid=1448822294&sr=1-2&keywords=mk550", 5);
INSERT INTO "sold_by" VALUES(1, 2, 4, 135.99, "1 Remaining", "http://www.amazon.ca/LG-22M45D-B-21-5-Inch-LED-Lit-Monitors/dp/B00JH3DNRI/ref=sr_1_4?s=electronics&ie=UTF8&qid=1448822404&sr=1-4&keywords=lg+22+monitor", 3.5);
INSERT INTO "sold_by" VALUES(1, 1, 5, 135.99, "In Stock", "http://www.amazon.ca/Seagate-Expansion-Portable-External-STEA500400/dp/B00TKFE9TE/ref=sr_1_1?s=electronics&ie=UTF8&qid=1448822520&sr=1-1&keywords=seagate+expansion+500", 4.5);
INSERT INTO "sold_by" VALUES(1, 3, 5, 56.14, "In Stock", "http://www.amazon.ca/250GB-Inchs-Drive-Laptop-Notebook/dp/B0087HHBXM/ref=sr_1_1?s=electronics&ie=UTF8&qid=1448822603&sr=1-1&keywords=250+Laptop+Seagate", 5);
INSERT INTO "sold_by" VALUES(1, 1, 6, 329.00, "In Stock", "http://www.amazon.ca/Bose-QuietComfort-Acoustic-Cancelling-Headphones/dp/B00M1NEUKK/ref=sr_1_1?s=electronics&ie=UTF8&qid=1448822937&sr=1-1&keywords=Bose+QC+25", 4.5);
INSERT INTO "sold_by" VALUES(1, 2, 6, 329.00, "In Stock", "http://www.amazon.ca/Bose-QuietComfort-Acoustic-Cancelling-Headphones/dp/B00X9KV0HU/ref=sr_1_1?s=electronics&ie=UTF8&qid=1448822980&sr=1-1&keywords=Bose+QC+20", 4.5);
INSERT INTO "sold_by" VALUES(1, 3, 6, 89.00, "In Stock", "http://www.amazon.ca/Bose-SoundSport-In-Ear-Headphones-Charcoal/dp/B011IH5ZHG/ref=sr_1_2?s=electronics&ie=UTF8&qid=1448823005&sr=1-2&keywords=SoundSport", 4.5);








-- Products Sold By Best Buy
INSERT INTO "sold_by" VALUES(2, 1, 1, 1049.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/apple-apple-ipad-pro-32gb-with-wi-fi-gold-ml0h2cl-a/10397272.aspx?path=680455cb8803f79979ac85aa0d750405en02", 3);
INSERT INTO "sold_by" VALUES(2, 2, 1, 424.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/apple-apple-ipad-mini-4-16gb-with-wi-fi-gold-mk6l2cl-a/10389493.aspx?path=fcd945ec1f6529ce7c320cabfe384f52en02", 5);
INSERT INTO "sold_by" VALUES(2, 3, 1, 1049.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/apple-apple-ipad-air-2-16gb-with-wi-fi-gold-mh0w2cl-a/10303902.aspx?path=73c21de05177084fa2010c02e2cddef9en02", 4.5);
INSERT INTO "sold_by" VALUES(2, 4, 1, 349.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/apple-bell-apple-iphone-6s-16gb-smartphone-space-grey-2-year-agreement/10392663.aspx?path=088e61de6e3191672146b27cfff7e993en02", 1);
INSERT INTO "sold_by" VALUES(2, 5, 1, 529.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/apple-telus-apple-iphone-6s-plus-16gb-smartphone-rose-gold-2-year-agreement-mku52vc-a/10392424.aspx?path=2025c1ca5feb8d5a61a5c2cf4e26db70en02", 5);
INSERT INTO "sold_by" VALUES(2, 1, 2, 499.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/microsoft-microsoft-surface-3-10-8-64gb-windows-10-tablet-with-quad-core-intel-processor-silver-7g5-00001/10367214.aspx?path=fe6069e0703bb73e2e0a5fc80760264aen02", 5);
INSERT INTO "sold_by" VALUES(2, 2, 2, 1949.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/microsoft-microsoft-surface-book-13-5-convertible-laptop-silver-intel-core-i5-6300u-128gb-ssd-8gb-ram-eng-cr9-00001/10394939.aspx?path=267a12707aa40b5ea5ae033b66a2ba95en02", 3);
INSERT INTO "sold_by" VALUES(2, 3, 2, 1279.99, "Out of Stock", "http://www.bestbuy.ca/en-CA/product/microsoft-microsoft-surface-pro-4-12-3-128gb-windows-10-tablet-with-6th-gen-intel-core-i5-6300u-silver-cr5-00001/10394996.aspx?path=40a6ede375c49377964dd91d328f4074en02", 4);
INSERT INTO "sold_by" VALUES(2, 1, 3, 44.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/logitech-logitech-corded-mouse-m500-910-001201/10131890.aspx?path=036dc53fc2f6e49d615f4c66184ac449en02", 3.5);
INSERT INTO "sold_by" VALUES(2, 2, 3, 39.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/logitech-logitech-wireless-keyboard-k360-black-920-004088/10201435.aspx?path=816f743b2adaaf6a1f1a9a32b3377a70en02", 4.5);
INSERT INTO "sold_by" VALUES(2, 3, 3, 49.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/logitech-logitech-wireless-wave-laser-keyboard-mouse-combo-mk550-920-002555/10153464.aspx?path=b19722f10fb5f7241e1e06fd0b5e6eb1en02", 4);
INSERT INTO "sold_by" VALUES(2, 1, 4, 219.99, "In Stock", "http://www.bestbuy.ca/en-ca/product/lg-electronics-lg-24-60hz-widescreen-led-ips-monitor-with-5ms-response-time-24mp67hq-p-aus-black-24mp67hq-p-aus/10359704.aspx?path=720c5ed3fd9568e550b90e56ad7cfb56en02", 4.5);
INSERT INTO "sold_by" VALUES(2, 2, 4, 199.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/lg-electronics-lg-22-60hz-widescreen-led-ips-monitor-with-5ms-response-time-22mp67hq-p-aus-black-22mp67hq-p-aus/10359705.aspx?path=6c47322ead9399cfcda6955d7fd3efc1en02", 3.5);
INSERT INTO "sold_by" VALUES(2, 3, 5, 86.98, "In Stock", "http://www.bestbuy.ca/en-ca/product/seagate-seagate-sshd-500gb-2-5-5400rpm-sata-laptop-internal-hard-drive-st500lm000-st500lm000/10278366.aspx?path=e88717ce8a084477b635b7d6ec0633caen02", 4);
INSERT INTO "sold_by" VALUES(2, 1, 6, 329.99, "In Stock", "http://www.bestbuy.ca/en-ca/product/bose-bose-quietcomfort-25-over-ear-noise-cancelling-headphones-apple-black-qc-25-headphones-blk/10322912.aspx?path=d05e8983561fb0138f233174397ac856en02", 4.5);
INSERT INTO "sold_by" VALUES(2, 2, 6, 329.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/bose-bose-quietcomfort-20-in-ear-noise-cancelling-headphones-with-mic-apple-black-qc20-mfi-black/10381289.aspx?path=dccd34b49f7a4151976a7b4e8e9482e3en02", 4);
INSERT INTO "sold_by" VALUES(2, 3, 6, 89.99, "In Stock", "http://www.bestbuy.ca/en-CA/product/bose-bose-soundsport-in-ear-sport-headphones-charcoal-soundsport-black/10389933.aspx?path=fe76f9e366bb7c5cecc447ec9c6e274cen02", 4.5);



-- Products Sold By Staples

INSERT INTO "sold_by" VALUES(3, 1, 1, 1049.99, "In Stock", "http://www.staples.ca/en/Apple-iPad-Pro-ML0G2CL-A-12-9-A9X-Chip-32GB-Wi-Fi-Silver/product_1952522_2-CA_1_20001", 0);
INSERT INTO "sold_by" VALUES(3, 2, 1, 439.00, "In Stock", "http://www.staples.ca/en/Apple-iPad-mini-4-7-9-A8-Chip-16GB-Wi-Fi-Space-Grey/product_1917381_2-CA_1_20001", 5);
INSERT INTO "sold_by" VALUES(3, 3, 1, 479.00, "In Stock", "http://www.staples.ca/en/Apple-iPad-Air-2-MH0W2CL-A-9-7-A8X-Chip-16GB-Wi-Fi-Gold/product_1416186_2-CA_1_20001", 5);
INSERT INTO "sold_by" VALUES(3, 1, 2, 499.00, "Out of Stock", "http://www.staples.ca/en/Microsoft-Surface-3-Convertible-Laptop-Tablet-2-In-1-10-8-Quad-core-Intel-Atom-x7-Z8700-2GB-RAM-64GB-HDD-Windows-8-1/product_1597876_2-CA_1_20001", 4);
INSERT INTO "sold_by" VALUES(3, 2, 2, 1949.00, "Out of Stock", "http://www.staples.ca/en/Microsoft-Surface-Book-13-5-Intel-Core-i5-128GB-SSD-8GB-RAM-Windows-10-Pro-English/product_1934321_2-CA_1_20001", 0);
INSERT INTO "sold_by" VALUES(3, 3, 2, 499, "In Stock", "http://www.staples.ca/en/Microsoft-Surface-Pro-4-Convertible-Tablet-12-3-Intel-Core-m3-128GB-SSD-4GB-RAM-Windows-10-Pro-Bilingual/product_1913824_2-CA_1_20001", 0);
INSERT INTO "sold_by" VALUES(3, 1, 3, 44.75, "In Stock", "http://www.staples.ca/en/Logitech-Corded-M500-Laser-Mouse/product_846447_2-CA_1_20001", 4.5);
INSERT INTO "sold_by" VALUES(3, 2, 3, 19.98, "In Stock", "http://www.staples.ca/en/Logitech-Wireless-Keyboard-K360-English-Glossy-Black/product_934512_2-CA_1_20001", 4.5);
INSERT INTO "sold_by" VALUES(3, 3, 3, 99.86, "In Stock", "http://www.staples.ca/en/Logitech-Wireless-Wave-Combo-MK550-English/product_898131_2-CA_1_20001", 4.5);
INSERT INTO "sold_by" VALUES(3, 1, 4, 149.96, "In Stock", "http://www.staples.ca/en/LG-24MP57-P-23-8-IPS-LED-Monitor/product_1561133_2-CA_1_20001", 4.5);
INSERT INTO "sold_by" VALUES(3, 2, 4, 169.96, "In Stock", "http://www.staples.ca/en/LG-22MP57HQ-21-5-IPS-LED-Monitor/product_1561131_2-CA_1_20001", 0);
INSERT INTO "sold_by" VALUES(3, 3, 4, 169.75, "In Stock", "http://www.staples.ca/en/LG-F60-Unlocked-Smartphone/product_1548422_2-CA_1_20001", 4);
INSERT INTO "sold_by" VALUES(3, 1, 5, 88.69, "In Stock", "http://www.staples.ca/en/Seagate-500GB-USB-3-0-Expansion-Portable-Drive-Black/product_1667726_2-CA_1_20001", 0);
INSERT INTO "sold_by" VALUES(3, 2, 5, 68.01, "In Stock", "http://www.staples.ca/en/Seagate-Desktop-HDD-1TB-3-5-SATA-6-0Gb-s-7200-RPM-Internal-Hard-Drive/product_184989_2-CA_1_20001", 0);
INSERT INTO "sold_by" VALUES(3, 1, 6, 329.95, "In Stock", "http://www.staples.ca/en/QuietComfort-25-Acoustic-Noise-Cancelling-Headphones-Black/product_1337759_2-CA_1_20001", 5);
INSERT INTO "sold_by" VALUES(3, 2, 6, 329.00, "In Stock", "http://www.staples.ca/en/Bose-QuietComfort-20-Acoustic-Noise-Cancelling-Headphones-for-Apple-Devices-White/product_1852957_2-CA_1_20001", 0);
INSERT INTO "sold_by" VALUES(3, 3, 6, 88.95, "In Stock", "http://www.staples.ca/en/Bose-SoundSport-In-Ear-Headphones-with-StayHear-Tips-Charcoal-Black/product_1852952_2-CA_1_20001", 0);
