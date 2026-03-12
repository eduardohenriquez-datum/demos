CREATE TABLE IF NOT EXISTS empleados (
    id              VARCHAR(20)    PRIMARY KEY,
    nombre          VARCHAR(100)   NOT NULL,
    departamento    VARCHAR(100)   NOT NULL,
    tipo            VARCHAR(20)    NOT NULL,
    salario_base    DOUBLE,
    prestaciones    DOUBLE,
    tarifa_hora     DOUBLE,
    horas_trabajadas INT,
    bono            DOUBLE
);
