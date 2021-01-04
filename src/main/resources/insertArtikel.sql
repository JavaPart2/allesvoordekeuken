/*insert into artikels(naam, aankoopprijs, verkoopprijs) values('test', 100, 120);*/
insert into artikels(naam, aankoopprijs, verkoopprijs, houdbaarheid, soort, artikelgroepid)
values('testfood', 100, 120, 7, 'F',
       (select id from artikelgroepen where naam = 'test'));
insert into artikels(naam, aankoopprijs, verkoopprijs, garantie, soort, artikelgroepid)
values('testnonfood', 100, 120, 30, 'NF',
       (select id from artikelgroepen where naam = 'test'));
insert into kortingen(artikelid, vanafAantal, percentage)
values ((select id from artikels where naam = 'testfood'), 1, 10);