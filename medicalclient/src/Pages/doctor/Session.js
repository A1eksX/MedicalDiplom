import React, { useEffect, useState } from "react";
import { HttpNoData } from "../../Core/Core";
import MedicalBook from "../../Module/MedicalBook";
import styles from './Session.module.css';

function Session(props) {

    const [Sessions, setSessions] = useState([]);

    const [currentDelete, setCurrentDelete] = useState(-1);
    const [jwt, setJwt] = useState(props.jwt);
    const [visibleMedicalBook, setVisibleMedicalBook] = useState(false);
    const [currentMedicalBookId, setCurrentMedicalBookId] = useState(-1);

    useEffect(() => {

        async function DidMount() {

            const response = await HttpNoData('/api/reception', 'GET', jwt);

            if (response.statusSuccessful)
                setSessions(response.data);
            else console.log(response.error);
        }

        DidMount();
    }, []);

    const ShowMedicalBook = (id) => {

        if (currentMedicalBookId == id) {
            setCurrentMedicalBookId(-1);
            setVisibleMedicalBook(false);
        }
        else {
            setCurrentMedicalBookId(id);
            setVisibleMedicalBook(true);
        }
    }

    const cancelSessia = async (id) => {

        const response = await HttpNoData('/api/reception/' + id + "?isCancelled=true" , 'PATCH', jwt);

        if (response.statusSuccessful)
            setSessions([...Sessions.filter(item => item.id != id)]);
    }

    const CompliteSessia = async (id) => {

        const response = await HttpNoData('/api/reception/' + id + "?isCancelled=false&data=" + window.prompt(''), 'PATCH', jwt);

        if (response.statusSuccessful)
            setSessions([...Sessions.filter(item => item.id != id)]);
    }

    const sessionsView = Sessions.length <= 0 ? 'Сегодня ничего нет' : (Sessions.length == 0 ? 'Тут пока пусто' : Sessions.map(sessia =>
        currentDelete != sessia.id ?
            <div key={sessia.id} className={styles.sessia}>
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
                <div className={styles.buttonsEvent}>
                    <button className={styles.buttonEvent} onClick={() => CompliteSessia(sessia.id)}>Принять пациента</button>
                    <button className={styles.buttonEvent} onClick={() => ShowMedicalBook(sessia.patient.medicalBookId)}>Мед. книжка</button>
                    <button className={styles.buttonEvent} onClick={() => setCurrentDelete(sessia.id)}>Отменить</button>
                </div>
            </div>
            :
            <div key={sessia.id} className={styles.boxEvent}>
                <button className={styles.buttonDelete} onClick={() => cancelSessia(sessia.id)}>Отклонить</button>
                <button className={styles.buttonCancel} onClick={() => setCurrentDelete(-1)}>Отмена</button>
            </div>
    ));

    return (<div className={styles.psevdoBody}>

        <div className={styles.content}>
            <div className={styles.contentSessians}>
                <div className={styles.listSessians}>
                    {sessionsView}
                </div>
            </div>
            {
                visibleMedicalBook ?
                    <div>
                        <MedicalBook jwt={jwt} id={currentMedicalBookId} cancel={() => setCurrentMedicalBookId(-1)}/>
                    </div> : null
            }
        </div>
    </div>);
};

export default Session;