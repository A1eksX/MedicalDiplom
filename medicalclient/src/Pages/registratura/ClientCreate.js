import React, { useState } from "react";
import styles from "./ClientCreate.module.css";
import { Button, Form } from "react-bootstrap";
import { HttpData } from "../../Core/Core";

function ClientCreate({ jwt }) {

    const [fullName, setFullName] = useState('');
    const [passport, setPassport] = useState('');
    const [SNILS, setSNILS] = useState('');
    const [graftCertificate, setGraftCertificate] = useState('');
    const [allergy, setAllergy] = useState('');
    const [groupBlood, setGroupBlood] = useState('');
    const [rhFactor, setRHFactor] = useState(false);

    const create = async() => {

        const response = await HttpData('/api/patient', 'POST', jwt, {
            fullName: fullName,
            passportData: passport,
            snils: SNILS,
            allergy: allergy,
            graftCertificate: graftCertificate,
            groupBlood: groupBlood,
            rhFactor: rhFactor
        });

        if(response.statusSuccessful)
        {
            setFullName('');
            setAllergy('');
            setPassport('');
            setSNILS('');
            setGraftCertificate('');
            setGroupBlood('');
            setRHFactor('');
        } 
        else window.alert('Пациент с такими данными уже существует');
    }

    return <div className={styles.main}>
        <div className={styles.content}>
            <Form>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>ФИО</Form.Label>
                    <Form.Control type="text" value={fullName} onChange={e => setFullName(e.target.value)} placeholder="ФИО" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicPassport">
                    <Form.Label>Паспорт</Form.Label>
                    <Form.Control type="text" value={passport} onChange={e => setPassport(e.target.value)} placeholder="серия и номер" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicSNILS">
                    <Form.Label>СНИЛС</Form.Label>
                    <Form.Control type="text" value={SNILS} onChange={e => setSNILS(e.target.value)} placeholder="номер СНИЛСа" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicCertificate">
                    <Form.Label>Прививочный сертификат</Form.Label>
                    <Form.Control type="text" value={graftCertificate} onChange={e => setGraftCertificate(e.target.value)} placeholder="сертификат"/>
                </Form.Group>
                <Form.Group className="mb-3" controlId="ControlAllergy">
                    <Form.Label>Аллергии</Form.Label>
                    <Form.Control as="textarea" value={allergy} onChange={e => setAllergy(e.target.value)} rows={3} placeholder="Перечислите все аллергии" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicBlood">
                    <Form.Label>Группа крови</Form.Label>
                    <Form.Control type="number" value={groupBlood} onChange={e => setGroupBlood(e.target.value <= 0 ? 0 : e.target.value)} placeholder="число"/>
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicRHFactor">
                    <Form.Label>RH фактор</Form.Label>
                    <Form.Check type="checkbox" value={rhFactor} onChange={e => setRHFactor(e.target.value)} label="Положительный"/>
                </Form.Group>
                <Button variant="primary" onClick={() => create()} type="submit">
                    Создать
                </Button>
            </Form>
        </div>
    </div>
}

export default ClientCreate;