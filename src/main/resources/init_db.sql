-- Выполнить от имени администратора PostgreSQL

-- Создаем базу данных, если она не существует
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'employee') THEN
        CREATE DATABASE employee;
    END IF;
END
$$;

-- Подключаемся к базе данных employee и создаем таблицы
\c employee

-- Таблицы будут созданы через database.sql 