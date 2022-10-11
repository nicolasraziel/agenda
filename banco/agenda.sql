/**
 * Agenda de contatos
 * @author Nicolas Raziel 
 */
 
 -- Comando para verificar os bancos criados
 show databases;
 
 -- Comando para criar um novo banco de dados
 create database agenda;
 
 
 -- comando para selecionar um banco de dados 
 use agenda;
 
-- comando usado para excluir um banco de dados 
drop database nome_do_banco;

-- comando usado para criar uma tabela 
-- int (tipos de dados: números inteiros)
-- primary key (chave primária - identificador)
-- auto_increment (numeração automática
-- varchar (50) (tipos de dados String - 50 caracteres)
-- not null (campo obrigatório)
create table contatos(
	id int primary key auto_increment,
    nome varchar(50) not null,
    fone varchar(15) not null,
    email varchar(50)
);

-- verificar tabelas do banco de dados
show tables;

-- descrever a tabela
describe contatos;

