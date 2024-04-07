import React, { useEffect, useState } from "react";
import { HttpData, HttpNoData } from "../../Core/Core";
import styles from './Request.module.css';

function Request(props) {

    const [jwt, setJwt] = useState(props.jwt);

    const [passport, setPassport] = useState('');
    const [SNILS, setSNILS] = useState('');
    const [date, setDate] = useState(new Date());
    const [data, setData] = useState('');
    const [time, setTime] = useState('12:00');
    const [diplom, setDiplom] = useState(0);

    const [patient, setPatinet] = useState();
    const [searchDoctor, setSearchDoctor] = useState('');
    const [doctors, setDoctors] = useState([]);

    useEffect(() => {

        async function DidMounth() {

            const responseDoctors = await HttpNoData('/api/doctor', 'GET', jwt);

            if (responseDoctors.statusSuccessful) {
                setDoctors(responseDoctors.data);

                if (responseDoctors.data.length > 0)
                    setDiplom(responseDoctors.data[0].id);
            }
        }

        DidMounth();
    }, []);

    const SearchPatinet = async() => {

        const responseUser = await HttpNoData('/api/patient?passportData=' + passport + '&snils=' + SNILS, "GET", jwt);

        if (!responseUser.statusSuccessful)
            alert('Пользователь с такими данными не найден');
        else setPatinet(responseUser.data);
    }

    const Send = async () => {

        if(!patient)
        {
            alert('Пациент не найден');
            return;
        }

        const response = await HttpData('/api/reception', "POST", jwt, {
            dateTime: date + 'T' + time + ':00Z',
            data: data,
            doctorDiplomaNumber: diplom,
            patientId: patient.id
        });

        if (response.statusSuccessful) {
            setPassport('');
            setSNILS('');
            alert('Успешно зарегистрирован!');
        }
        else console.log(response.error);
    }

    return (
        <div className={styles.container}>
            <h1 className={styles.customH1}>Запись к врачу</h1>
            <div className={styles.customForm}>
                <label className={styles.customLabel} htmlFor="patientSearch">{
                    patient ? patient.fullName : 'Поиск пациента:'
                }</label>
                <input className={styles.customInput} value={passport} onChange={(e) => setPassport(e.target.value)} type="text" placeholder="Паспорт" required />
                <input className={styles.customInput} value={SNILS} onChange={(e) => setSNILS(e.target.value)} type="text" placeholder="СНИЛС" required />
                <button className={styles.customButton} onClick={SearchPatinet}>Поиск</button>

                <label className={styles.customLabel} htmlFor="appointmentDate">Дата записи:</label>
                <input className={styles.customInput} value={date} onChange={(e) => setDate(e.target.value)} type="date" id="appointmentDate" required />
                <input className={styles.customInput} value={time} onChange={(e) => setTime(e.target.value)} type="time" id="appointmentDate" required />

                <label className={styles.customLabel} htmlFor="doctor">Выберите врача:</label>
                <select className={styles.customSelect} id="doctor" name="doctor" value={diplom} onChange={(e) => setDiplom(e.target.value)}>
                    {
                        doctors.map(item => <option key={item.id} value={item.id}>
                            {item.fullName} ({item.specialization})
                        </option>) // await
                    }
                </select>

                <label className={styles.customLabel} htmlFor="comments">Комментарии:</label>
                <textarea className={styles.customTextarea} value={data} onChange={(e) => setData(e.target.value)} rows="4" placeholder="Дополнительные комментарии" />

                <button className={styles.customButton} onClick={Send} type="submit">Записать</button>
            </div>
        </div>);
}

export default Request;