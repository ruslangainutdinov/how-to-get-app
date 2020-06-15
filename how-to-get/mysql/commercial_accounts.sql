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
(10,"East Coast Logistics","4955802187","Scranton The Office Pam Beasly avenue 1","FLIGHT",10),
(11,"Air Astana","1123581321","New York, Long Island Tax Free Area 028","FLIGHT",11),
(12,"S7","345589144233","Los Angeles Boulevard of Broken Dreams 7/5","FLIGHT",12),
(13,"Lufthansa","3776109871597","Delaware Seven Nation Army Association building 44","FLIGHT",13),
(14,"DRY","258441816765","Chrysler Building 32 floor","FLIGHT",14);

set foreign_key_checks=1;