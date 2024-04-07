import React, { useEffect, useState } from "react";
import { HttpNoData } from '../../Core/Core.js';
import styles from './Doctors.module.css';

function Doctors(props) {

    const [doctors, setDoctors] = useState([]);
    const [currentDoctorId, setCurrentDoctorId] = useState(-1);
    const [sessians, setSessians] = useState([]);
    const [currentDelete, setCurrentDelete] = useState(-1);

    const [jwt, setJwt]= useState(props.jwt);

    useEffect(() => {

        async function DidMounth() {

            const response = await HttpNoData('/api/doctor', "GET", jwt);

            if(response.statusSuccessful){
                setDoctors(response.data);
                

                if(response.data.length > 0)
                    setDoctor(response.data[0].id);
            }
        }

        DidMounth();
    }, []);

    const setDoctor = async(id) => {

        if(id == currentDoctorId)
            return;

        setCurrentDoctorId(id);

        const response = await HttpNoData('/api/doctor/'+id+'/reception', "GET", jwt);

        if(response.statusSuccessful)
            setSessians(response.data);
    }

    const deleteSessia = async (id) => {
        
        const response = await HttpNoData('/api/reception/' + id, 'DELETE', jwt);

        if(response.statusSuccessful)
            setSessians([...sessians.filter(item => item.id != id)]);
    }

    const doctorsView = doctors.map(doctor =>
        <div key={doctor.id} onClick={() => setDoctor(doctor.id)} className={styles.doctor}>
            {doctor.fullName}
            <strong>{doctor.specialization}</strong>
        </div>);

    const sessianView = sessians == null ? 'Выберите доктора' : (sessians.length == 0 ? 'Тут пока пусто' : sessians.map(sessia =>
        currentDelete != sessia.id ?
            <div key={sessia.id} className={styles.sessia} onClick={() => setCurrentDelete(sessia.id)}>
                <div className={styles.dateSessia}>
                    {new Date(sessia.dateTime).toLocaleDateString('ru-RU', {
                        weekday: 'long',
                        year: 'numeric',
                        month: 'long',
                        day: 'numeric',
                        hour: 'numeric',
                        minute: 'numeric'
                    })}
                </div>

                <div className={styles.patientSessia}>
                    <strong>Пациент:</strong> {sessia.patient.fullName}
                </div>

                <div className={styles.commentSessia}>
                    <strong>Примечания:</strong>  {sessia.data}
                </div>
            </div>
            :
            <div key={sessia.id} className={styles.boxEvent}>
                <button className={styles.buttonDelete} onClick={() => deleteSessia(sessia.id)}>Удалить</button>
                <button className={styles.buttonCancel} onClick={() => setCurrentDelete(-1)}>Отмена</button>
            </div>
    ));

    return (<div className={styles.psevdoBody}>

        <div className={styles.content}>
            <div className={styles.contentDoctors}>
                <div className={styles.listDoctors}>
                    {doctorsView}
                </div>
            </div>

            <div className={styles.contentSessians}>
                <div className={styles.listSessians}>
                    {sessianView}
                </div>
            </div>
        </div>
    </div>);
}

export default Doctors