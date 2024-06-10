Create table test(
		id varchar(30) not null,
		name varchar(30) not null,
		password varchar(30) not null,
		email varchar(30),
		reg_date date
		);
	-- Insert into table명 (컬럼명.....) values (value,...)
    
    insert into test (id,name,password, email)
    values('ohja','오자현','1111', 'ohja1004@naver.com');
    
    -- 조회 
    Select * from Users;
    
    show databases;
    
    create database create_db_test;

show databases;