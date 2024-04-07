INSERT INTO doctor (diploma_number, medical_institution_id, password, full_name, specialization_name)
    VALUES(1234, 1, '1234','test doctor','Регистратор'),
        (2, 2, 'password','Смирнов Марк Романович','Фтизиатр'),
        (3, 3, 'password','Козлова Амина Ивановна','Ревматолог');

INSERT INTO specialization (name)
    VALUES('Регистратор'),
        ('Фтизиатр'),
        ('Ревматолог'),
        ('Медсестра (медбрат)'),
        ('ЛОР (отоларинголог)'),
        ('Травматолог (Травматолог-ортопед)');

INSERT INTO medical_institution (id,name,address)
    VALUES(1, 'МедПроф', 'Новая улица, 9, Люберцы, Московская область, 140002'),
        (2, 'Институт здоровья','Комсомольский проспект, 11Б, Люберцы, Московская область, 140013'),
        (3, 'Ниармедик','проспект Маршала Жукова, 38к1, Москва');

INSERT INTO patient (full_name,passport_data,snils,medical_book_id)
    VALUES('Филатова Виктория Давидовна', 123, 123,1),
        ('Тихонов Николай Даниилович', 321, 321,2),
        ('Степанова Милана Матвеевна', 456, 456,3),
        ('Денисова Элина Львовна', 654,654,4);

INSERT INTO medical_book (rh_factor,graft_certificate,group_blood,patient_id)
    VALUES(1, '121dfa23', 1, 1),
        (0, '1sdae2dAD', 2, 2),
        (1, '1sdsavrvsfv', 4, 3),
        (1, 'faffdfe',3, 4);

INSERT INTO disease (id,diagnosis,rezeptura, medical_book_id, treatment_start,treatment_end)
    VALUES(1, 'Хронический HР-ассоциированный поверхностный пангастрит умеренной активности, с повышенной секрецией, фаза обострения', 'rezeptura', 1, '2023-12-12', '2023-12-26'),
        (2, 'Пневмония, вызванная Str. pneumoniae, с локализацией в S 9,10 правого легкого, нетяжелая. ДН I', 'rezeptura', 1, '2024-01-02', '2024-02-03'),
        (3, 'Пневмония, вызванная Str. pneumoniae, с локализацией в S 9,10 правого легкого, нетяжелая. ДН I', 'rezeptura', 1, '2024-01-02', '2024-01-12'),
        (4, 'Пневмония, вызванная Str. pneumoniae, с локализацией в S 9,10 правого легкого, нетяжелая. ДН I', 'rezeptura', 1, '2024-01-06', '2024-01-27'),
        (5, 'Пневмония, вызванная Str. pneumoniae, с локализацией в S 9,10 правого легкого, нетяжелая. ДН I', 'rezeptura', 1, '2024-01-06', '2024-01-27');

INSERT INTO procedure_entity (id,name,drugs, count_drugs, disease_id)
    VALUES(1, 'сОЛЕВЫЕ ВАНЫ1', 'Декаметилендиметилметоксикарбонилметиламмоний', 2, 1),
        (2, 'сОЛЕВЫЕ ВАНЫ2', 'Декаметилендиметилметоксикарбонилметиламмоний', 3, 2),
        (3, 'сОЛЕВЫЕ ВАНЫ3', 'Декаметилендиметилметоксикарбонилметиламмоний',2, 3),
        (4, 'сОЛЕВЫЕ ВАНЫ5', 'Декаметилендиметилметоксикарбонилметиламмоний',2, 3),
        (5, 'сОЛЕВЫЕ ВАНЫ5', 'Декаметилендиметилметоксикарбонилметиламмоний',16, 4);

INSERT INTO disease_procedures (disease_entity_id,procedures_id)
    VALUES(1, 1),
        (2, 2),
        (3, 3),
        (4, 4),
        (5, 5);

INSERT INTO medical_book_history_disease (medical_book_entity_id,history_disease_id)
    VALUES(1, 1),
        (1, 2),
        (1, 3),
        (1, 4),
        (1, 5);

INSERT INTO reception (data,date_time,status,doctor_diploma_number,patient_id)
    VALUES('data1', '2024-01-02',0, 1234, 1),
        ('data2', '2024-01-02', 1, 1234,2),
        ('data3', '2024-01-02', 2, 1234,3),
        ('data4', '2024-01-02', 0, 1234,4);