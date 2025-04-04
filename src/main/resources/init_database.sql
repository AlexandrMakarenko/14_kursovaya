-- Этот скрипт нужно выполнить от имени администратора PostgreSQL

-- Проверяем и создаем базу данных, если она не существует
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'employee') THEN
        CREATE DATABASE employee;
    END IF;
END
$$;

-- Замечание:
-- 1. Этот скрипт нужно запустить отдельно перед выполнением database.sql
-- 2. Для запуска: psql -U postgres -f init_database.sql
-- 3. После этого можно выполнить: psql -U postgres -d employee -f database.sql 