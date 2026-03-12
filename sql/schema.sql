-- ─────────────────────────────────────────────────────────
-- Schema: Sistema de Empleados
-- Compatible con Oracle Database y H2 (modo Oracle)
-- ─────────────────────────────────────────────────────────

-- Tabla única para todos los tipos de empleado (Table-per-Hierarchy)
-- La columna TIPO distingue qué subclase representa cada fila.
-- Columnas no aplicables a un tipo quedan en NULL.

CREATE TABLE IF NOT EXISTS empleados (
    id               VARCHAR(10)    PRIMARY KEY,
    nombre           VARCHAR(100)   NOT NULL,
    departamento     VARCHAR(50)    NOT NULL,
    tipo             VARCHAR(20)    NOT NULL,   -- TIEMPO_COMPLETO | CONTRATISTA | GERENTE
    salario_base     DECIMAL(10,2),             -- EmpleadoTiempoCompleto, Gerente
    prestaciones     DECIMAL(5,4),              -- EmpleadoTiempoCompleto, Gerente (ej: 0.3500)
    tarifa_hora      DECIMAL(10,2),             -- EmpleadoContratista
    horas_trabajadas INTEGER,                   -- EmpleadoContratista
    bono             DECIMAL(10,2)              -- Gerente
);

-- ─────────────────────────────────────────────────────────
-- Datos de ejemplo (opcional — el menú también los crea)
-- ─────────────────────────────────────────────────────────
-- INSERT INTO empleados VALUES ('E001','Ana García','Desarrollo','TIEMPO_COMPLETO',3000.00,0.35,NULL,NULL,NULL);
-- INSERT INTO empleados VALUES ('C001','María López','QA','CONTRATISTA',NULL,NULL,50.00,120,NULL);
-- INSERT INTO empleados VALUES ('G001','Carlos Ruiz','Desarrollo','GERENTE',5000.00,0.35,NULL,NULL,1500.00);
