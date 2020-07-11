use how_to_get_app;

set foreign_key_checks=0;

truncate commercial_accounts;

insert into commercial_accounts values (1,"Flix Bus","84959379992","Rochester 15th Avenue 56 building","BUS",1),
(2,"Buster","4959379992","B.White Avenue 69 office","BUS",2),
(3,"East Coast Logistics","87712817171","Saint Petersburg Dvortsovaya Square 18","BUS,FLIGHT",3),
(4,"Barrier","859595956","Washington D.C. 4 Ave","BUS",4),
(5,"Hitchhiker","3141584998","New York Under Brooklyn Bridge","BUS",5),
(6,"Kris","2147156435","Myrtle Beach Yapoun Drive 1300","BUS",6),
(7,"YAGNI","87026464157","Miami Beach Sunrise Street 158","BUS",7),
(8,"Wizz Air","88005553535","Hampshire Oak street 32","FLIGHT",8),
(9,"Panam","1987654123","Boston MIT Trump Street 78 building","FLIGHT",9),
(10,"WCL","4955802187","Scranton The Office Pam Beasly avenue 1","FLIGHT",10),
(11,"Air Astana","1123581321","New York, Long Island Tax Free Area 028","FLIGHT",11),
(12,"S7","345589144233","Los Angeles Boulevard of Broken Dreams 7/5","FLIGHT",12),
(13,"Lufthansa","3776109871597","Delaware Seven Nation Army Association building 44","FLIGHT",13),
(14,"DRY","258441816765","Chrysler Building 32 floor","FLIGHT",14);

truncate users;

insert into users values
('1','$2a$10$aTUkLD.ZEKumK0k/ffrCvuO0n.mY1SCaEF3VCTeTxnTu3suGg6bOS','Flix Bus','Company','flixbus@flixbus.com','4','ROLE_USER,ROLE_PROVIDER'),
('2','$2a$10$eNTeZ4Ju7Qbt5L.rSiZR5ulCQp84CFrAPgTeGa1za71dPvrZO9on6','Buster','Company','buster@buster.com','5','ROLE_USER,ROLE_PROVIDER'),
('3','$2a$10$uwP6ZXisKrr94DQKYNFqtOC6rtVuk8PfTiKU/zKBfb83.N3e2aKlW','East Coast Logistics','Company','eal@eal.com','14','ROLE_USER,ROLE_PROVIDER'),
('4','$2a$10$aTUkLD.ZEKumK0k/ffrCvuO0n.mY1SCaEF3VCTeTxnTu3suGg6bOS','Barrier','Company','barrier@br.com','6','ROLE_USER,ROLE_PROVIDER'),
('5','$2a$10$eNTeZ4Ju7Qbt5L.rSiZR5ulCQp84CFrAPgTeGa1za71dPvrZO9on6','Hitchhiker','Company','hitchhiker@hh.com','8','ROLE_USER,ROLE_PROVIDER'),
('6','$2a$10$uwP6ZXisKrr94DQKYNFqtOC6rtVuk8PfTiKU/zKBfb83.N3e2aKlW','Kris','Company','kris@kris.com','17','ROLE_USER,ROLE_PROVIDER'),
('7','$2a$10$aTUkLD.ZEKumK0k/ffrCvuO0n.mY1SCaEF3VCTeTxnTu3suGg6bOS','YAGNI','Company','yagni@principles.com','12','ROLE_USER,ROLE_PROVIDER'),
('8','$2a$10$eNTeZ4Ju7Qbt5L.rSiZR5ulCQp84CFrAPgTeGa1za71dPvrZO9on6','Wizz Air','Company','wizzair@wa.com','11','ROLE_USER,ROLE_PROVIDER'),
('9','$2a$10$uwP6ZXisKrr94DQKYNFqtOC6rtVuk8PfTiKU/zKBfb83.N3e2aKlW','Panam','Company','panam@panam.com','14','ROLE_USER,ROLE_PROVIDER'),
('10','$2a$10$aTUkLD.ZEKumK0k/ffrCvuO0n.mY1SCaEF3VCTeTxnTu3suGg6bOS','WCL','Company','wcl@wcl.com','5','ROLE_USER,ROLE_PROVIDER'),
('11','$2a$10$eNTeZ4Ju7Qbt5L.rSiZR5ulCQp84CFrAPgTeGa1za71dPvrZO9on6','Air Astana','Company','airastana@aa.com','9','ROLE_USER,ROLE_PROVIDER'),
('12','$2a$10$uwP6ZXisKrr94DQKYNFqtOC6rtVuk8PfTiKU/zKBfb83.N3e2aKlW','S7','Company','s7@step.com','8','ROLE_USER,ROLE_PROVIDER'),
('13','$2a$10$aTUkLD.ZEKumK0k/ffrCvuO0n.mY1SCaEF3VCTeTxnTu3suGg6bOS','Lufthansa','Company','lufthansa@lft.com','7','ROLE_USER,ROLE_PROVIDER'),
('14','$2a$10$eNTeZ4Ju7Qbt5L.rSiZR5ulCQp84CFrAPgTeGa1za71dPvrZO9on6','DRY','Company','dry@principles.com','3','ROLE_USER,ROLE_PROVIDER');

truncate user_profiles;

insert into user_profiles values('1','1'),
('2','2'),
('3','3'),
('4','4'),
('5','5'),
('6','6'),
('7','7'),
('8','8'),
('9','9'),
('10','10'),
('11','11'),
('12','12'),
('13','13'),
('14','14');

set foreign_key_checks=1;