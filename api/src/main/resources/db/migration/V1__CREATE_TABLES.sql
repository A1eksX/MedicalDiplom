create table hibernate_sequence (
    next_val bigint
) engine=MyISAM;

insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );

create table disease (
    id bigint not null auto_increment,
    diagnosis varchar(255),
    rezeptura varchar(255),
    treatment_end date,
    treatment_start date,
    medical_book_id bigint,
    primary key (id)
) engine=MyISAM;

CREATE TABLE doctor (
    diploma_number bigint not null,
    password varchar(64) not null,
    full_name varchar(64) not null,
    access_during_working_period varchar(64),
    hash_code_password varchar(64),
    specialization_name varchar(64) NOT NULL,
    medical_institution_id BIGINT NOT NULL,
    PRIMARY KEY (diploma_number)
) engine=MyISAM;

create table medical_book (
    id bigint not null auto_increment,
    rh_factor bit not null,
    allergy varchar(255),
    graft_certificate varchar(255),
    group_blood integer not null,
    patient_id bigint,
    primary key (id)
) engine=MyISAM;

CREATE TABLE medical_institution (
    id BIGINT not null AUTO_INCREMENT,
    address varchar(255) not null,
    name varchar(255),
    PRIMARY KEY (id)
) engine=MyISAM;

create table patient (
    id bigint not null auto_increment,
    full_name varchar(255) not null,
    passport_data varchar(16) not null,
    snils varchar(16) not null,
    medical_book_id bigint,
    primary key (id)
) engine=MyISAM;

create table procedure_entity (
    id bigint not null auto_increment,
    count_drugs integer not null,
    drugs varchar(255),
    name varchar(255),
    disease_id bigint,
    primary key (id)
) engine=MyISAM;

create table reception (
   id bigint not null auto_increment,
    data varchar(255),
    date_time date,
    status integer,
    doctor_diploma_number bigint,
    patient_id bigint,
    primary key (id)
) engine=MyISAM;

CREATE TABLE specialization (
    name varchar(64) not null,
    PRIMARY KEY (name)
) engine=MyISAM;

create table disease_procedures (
   disease_entity_id bigint not null,
    procedures_id bigint not null
) engine=MyISAM;

create table doctor_history_reception (
   doctor_entity_diploma_number bigint not null,
    history_reception_id bigint not null
) engine=MyISAM;

create table medical_book_history_disease (
   medical_book_entity_id bigint not null,
    history_disease_id bigint not null
) engine=MyISAM;

create table medical_institution_doctors (
   medical_institution_entity_id bigint not null,
    doctors_diploma_number bigint not null
) engine=MyISAM;

create table patient_history_reception (
   patient_entity_id bigint not null,
    history_reception_id bigint not null
) engine=MyISAM;

create table specialization_doctor (
    doctor_diploma_number bigint not null,
    specialization_entity_name varchar(64) not null
) engine=MyISAM;
