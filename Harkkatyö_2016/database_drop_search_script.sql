select 'drop table ' || name || ';' from sqlite_master
    where type = 'table';
select 'drop index ' || name || ';' from sqlite_master
    where type = 'index';
