import React, { useEffect, useState } from "react";
import { HttpData, HttpNoData } from "../Core/Core";
import styles from '../Styles/Registration.module.css';

function Registration(props) {

    const [fullname, setFullname] = useState('');
    const [diplomnumber, setDiplomnumber] = useState('');
    const [password, setPassword] = useState('');
    const [medicalInstitutionId, setMedicalInstitutionId] = useState(0);
    const [specializationId, setSpecializationId] = useState(0);

    const [specialization, setSpecialization] = useState('');
    const [city, setCity] = useState('');

    const [medicalInstitutions, setMedicalInstitutions] = useState([]);
    const [specializations, setSpecializations] = useState([]);

    useEffect(() => {
        InstitutionsChange('');
        SpecializationChange('');
    }, []);

    const Send = async () => {

        console.log(specializationId);
        console.log(JSON.stringify(specializations));

        const responseCreate = await HttpData('/api/auth/registration', 'POST', null,
            {
                fullName: fullname,
                diplomaNumber: diplomnumber,
                password: password,
                specialization: specializations.find(o => +o.id === +specializationId).name,
                medicalInstitutionId: medicalInstitutionId
            });

        if (responseCreate.statusSuccessful) {
            const responseUser = await HttpNoData('/api/doctor/my', "GET", responseCreate.data.accessToken);

            if (responseUser.statusSuccessful) {
                props.login(responseUser.data, responseCreate.data.accessToken);

                if (responseUser.data.specialization == 'Регистратор')
                    window.location.href = 'registratura';
                else
                    window.location.href = 'doctor';
            }
            else console.log(responseUser.error);
        }
        else console.log(responseCreate.error);
    }

    const InstitutionsChange = async (value) => {

        setCity(value);

        const response = await HttpNoData('api/medicalinstitution?search=' + value, "GET", null);

        if (response.statusSuccessful) {
            const { data } = response;
            setMedicalInstitutions(data);

            if (data && data.length > 0)
                setMedicalInstitutionId(data[0].id);
        }
        else console.log(response.error);
    }

    const SpecializationChange = async (value) => {

        setSpecialization(value);

        const response = await HttpNoData('api/specialization?search=' + value, "GET", null);

        if (response.statusSuccessful) {
            const data = response.data.map((item, index) => { item.id = index; return item; });

            setSpecializations(data);

            if (data && data.length > 0)
                setSpecializationId(data[0].id);
        }
        else console.log(response.error);
    }

    return (<div className={styles.psevdoBody}>
        <div className={styles.customForm}>

            <div className={styles.boxContainer}>
                <label className={styles.titleField} htmlFor="fullname">ФИО:</label>
                <input className={styles.inputField} type="text" id="fullname" name="fullname" value={fullname} onChange={(e) => setFullname(e.target.value)} required /></div>

            <div className={styles.boxContainer}>
                <label className={styles.titleField} htmlFor="diplomnumber">Диплом:</label>
                <input className={styles.inputField} type="text" id="diplomnumber" name="diplomnumber" value={diplomnumber} onChange={(e) => setDiplomnumber(e.target.value)} required /></div>

            <div className={styles.boxContainer}>
                <label className={styles.titleField} htmlFor="password">Пароль:</label>
                <input className={styles.inputField} type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} required /></div>

            <div className={styles.boxContainer}>
                <label className={styles.titleField} htmlFor="specialization">Специализация:</label>
                <input className={styles.inputField} type="text" id="specialization" name="specialization" value={specialization} onChange={(e) => SpecializationChange(e.target.value)} /></div>

            <div className={styles.boxContainer}>
                <label className={styles.titleField}>Специализация:</label>
                <select className={styles.inputField} value={specializationId} onChange={(e) => setSpecializationId(+e.target.value)}>
                    {
                        specializations.map(item => <option key={item.id} value={item.id}>{item.name}</option>)
                    }
                </select>
            </div>

            <div className={styles.boxContainer}>
                <label className={styles.titleField} htmlFor="city">Город:</label>
                <input className={styles.inputField} type="text" value={city} onChange={(e) => InstitutionsChange(e.target.value)} required />
            </div>

            <div className={styles.boxContainer}>
                <label className={styles.titleField}>Больница:</label>
                <select className={styles.inputField} value={medicalInstitutionId} onChange={(e) => setMedicalInstitutionId(+e.target.value) || console.log(e.target.value)}>
                    {
                        medicalInstitutions.map(item => <option key={item.id} value={item.id}>({item.name}) {item.address}</option>)
                    }
                </select>
            </div>


            <button className={styles.buttonExport} onClick={Send}>Отправить заявку</button>
        </div>
    </div>);
}


export default Registration;