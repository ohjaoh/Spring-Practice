-- root에서 실행할 코드입니다.

-- 데이터베이스 확인
show databases;

-- testDB가 없으면 실행하여 생성
create database testDB;

-- 사용자 'tester'를 'localhost'에서 비밀번호 '1234'와 함께 생성합니다.
create user 'tester'@'localhost' identified by '1234';

-- 'tester' 사용자에게 'testDB' 데이터베이스에 대한 모든 권한을 부여합니다.
grant all privileges on testDB.* to 'tester'@'localhost';

-- 'tester' 사용자에게 모든 데이터베이스에 대해 테이블을 생성할 수 있는 권한을 부여합니다.
grant create on *.* to 'tester'@'localhost';

-- 변경된 권한 설정을 즉시 적용하기 위해 권한 정보를 새로 고침합니다.
flush privileges;

-- 모든 권한 취소:tester 사용자에게 부여된 모든 권한을 취소하려면, 다음 명령을 실행합니다
REVOKE ALL PRIVILEGES ON *.* FROM 'tester'@'localhost';
