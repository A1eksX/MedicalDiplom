import React, { useState } from "react";
import { BsCardText } from 'react-icons/bs';
import { FaPassport } from 'react-icons/fa';
import { LuSearchX } from 'react-icons/lu';
import { HttpNoData } from '../../Core/Core.js';
import MedicalBook from "../../Module/MedicalBook.js";
import styles from './PatientSearch.module.css';

function PatientSearch(props) {

    const [patients, setPatients] = useState();
    const [jwt, setJwt] = useState(props.jwt);
    const [passportInput, setPassportInput] = useState('');
    const [snilsInput, setsnilsInput] = useState('');
    const [currentMedicalId, setCurrentMedicalId] = useState(-1);
    const [isMedicalBook, setIsMedicalBook] = useState(false);

    const PatientSearch = async(passport, snils) =>  {

            const response = await HttpNoData('/api/patient/search?passportData='+passport+'&snils=' + snils, 'GET', jwt);
            if (response.statusSuccessful)
                setPatients(response.data);
            else console.log(response.error);
        }

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
            {patients == null ? <div className={styles.notPatients}>
                <LuSearchX className={styles.iconSearch} />
                <label className={styles.textNotPatient}>Поиск не увенчался успехом</label>
            </div> :
                patients.map(patient =>
                    <div key={patient.id} className={styles.patient}>
                        <div className={styles.fieldPatient}>{patient.fullname}</div>
                        <div className={styles.fieldPatient}><FaPassport /> {patient.snils}</div>
                        <div className={styles.fieldPatient}><BsCardText /> {patient.passportData}</div>
                        <div className={styles.fieldPatient}><button className={styles.eventButton}>История посещений</button></div>
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
        
    </div>

}

export default PatientSearch;