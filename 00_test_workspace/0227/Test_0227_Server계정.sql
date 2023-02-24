--CREATE USER WATER IDENTIFIED BY WATER;
--GRANT CONNECT, RESOURCE TO WATER;

--DROP TABLE WATER;
--DROP SEQUENCE SEQ_NO;

CREATE TABLE WATER(
    WATER_NO NUMBER NOT NULL CONSTRAINT WATER_NO_PK PRIMARY KEY,
    BRAND VARCHAR2(40) NOT NULL,
    PRICE NUMBER NOT NULL
);

CREATE SEQUENCE SEQ_NO
NOCACHE
NOCYCLE;

INSERT
  INTO WATER
  (
    WATER_NO
  , BRAND
  , PRICE
  )
  VALUES
  (
    SEQ_NO.NEXTVAL
  , '��ټ�'
  , 1000
  );
  
INSERT
  INTO WATER
  (
    WATER_NO
  , BRAND
  , PRICE
  )
  VALUES
  (
    SEQ_NO.NEXTVAL
  , '����'
  , 2000
  );
  
INSERT
  INTO WATER
  (
    WATER_NO
  , BRAND
  , PRICE
  )
  VALUES
  (
    SEQ_NO.NEXTVAL
  , '�����'
  , 3000
  );
  
COMMIT; -- �ʼ�!

SELECT * FROM WATER;