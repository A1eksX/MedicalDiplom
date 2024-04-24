import React, { useState } from "react";
import { BsCardText } from 'react-icons/bs';
import { FaPassport } from 'react-icons/fa';
import { LuSearchX } from 'react-icons/lu';
import { HttpNoData } from '../../Core/Core.js';
import MedicalBook from "../../Module/MedicalBook.js";
import styles from './PatientSearch.module.css';
import { Alert, Modal, Accordion } from 'react-bootstrap';

function PatientSearch(props) {

    const [patients, setPatients] = useState();
    const [jwt, setJwt] = useState(props.jwt);
    const [passportInput, setPassportInput] = useState('');
    const [snilsInput, setsnilsInput] = useState('');
    const [currentMedicalId, setCurrentMedicalId] = useState(-1);
    const [isMedicalBook, setIsMedicalBook] = useState(false);
    const [currentReceptions, setCurrentReceptions] = useState();

    const ShowReceptions = async (id) => {

        const response = await HttpNoData(`/api/patient/${id}/receptions`, 'GET', jwt)

        setCurrentReceptions(response.data);

    }

    const PatientSearch = async(passport, snils) =>  {

            const response = await HttpNoData('/api/patient/search?passportData='+passport+'&snils=' + snils, 'GET', jwt);
            if (response.statusSuccessful)
                setPatients(response.data);
            else console.log(response.error);
        }

    console.log(JSON.stringify(currentReceptions));
    return <div className={styles.psevdoBody}>
        <div className={styles.searchPanel}>
            <div className={styles.itemPanel}>
                <label className={styles.customLabel}>Паспорт</label>
                <input className={styles.input} type='number' value={passportInput} onChange={(e) => setPassportInput(e.target.value)}/>
            </div>
            <div className={styles.itemPanel}>
                <label className={styles.customLabel}>СНИЛС</label>
                <input className={styles.input} type='number' value={snilsInput} onChange={(e) => setsnilsInput(e.target.value)}/>
            </div>
            <div className={styles.itemPanel}>
                <button className={styles.buttonSearch} onClick={() => PatientSearch(passportInput, snilsInput)}>Поиск</button>
            </div>
        </div>

        <div className={styles.patients}>
            {patients == null || patients.length <= 0 ? <div className={styles.notPatients}>
                <LuSearchX className={styles.iconSearch} />
                <label className={styles.textNotPatient}>Поиск не увенчался успехом</label>
            </div> :
                 patients.map(patient =>
                    <div key={patient.id} className={styles.patient}>
                        <div className={styles.fieldPatient}>{patient.fullname}</div>
                        <div className={styles.fieldPatient}><FaPassport /> {patient.snils}</div>
                        <div className={styles.fieldPatient}><BsCardText /> {patient.passportData}</div>
                        <div className={styles.fieldPatient}><button className={styles.eventButton} onClick={() => {ShowReceptions(patient.id)}}>История посещений</button></div>
                        <div className={styles.fieldPatient}><button className={styles.eventButton} onClick={() =>{ setCurrentMedicalId(patient.medicalBookId); setIsMedicalBook(true); }}>Мед. книжка</button></div>
                    </div>
                )}
        </div>
        {
            isMedicalBook ? <div className={styles.medicalBook}>
            <MedicalBook jwt={jwt} id={currentMedicalId} cancel={() => setIsMedicalBook(false)}/>
            </div>
            :
            null
        }
        {
            currentReceptions ? <Modal
                show={currentReceptions != null}
                onHide={() => setCurrentReceptions(null)}
                dialogClassName="modal-90w"
                aria-labelledby="example-custom-modal-styling-title">
                <Modal.Header closeButton>
                    <Modal.Title id="example-custom-modal-styling-title">
                        Список посещений
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {
                        currentReceptions.length > 0 ?
                            <Accordion>{
                                currentReceptions.map(o =>
                                    <Accordion.Item eventKey={o.id} key={o.id}>
                                        <Accordion.Header>{o.dateTime.replace('T', ' ')}</Accordion.Header>
                                        <Accordion.Body>
                                            {o.data}
                                            <br/>
                                            <strong>
                                                ({o.doctor.specialization}) {o.doctor.fullName}
                                                <br/>
                                                {o.doctor.medicalInstitutionModel.name}, {o.doctor.medicalInstitutionModel.address}
                                            </strong>
                                        </Accordion.Body>
                                    </Accordion.Item>)
                            }
                            </Accordion>
                            :  <Alert key={'warning'} variant={"warning"}>
                            Пока записей нет.
                        </Alert>    
                    }
                </Modal.Body>
            </Modal> :
                null
        }
    </div>

}

export default PatientSearch;