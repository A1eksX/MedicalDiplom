import React, { useEffect, useState } from "react";
import { HttpNoData } from "../../Core/Core";
import MedicalBook from "../../Module/MedicalBook";
import styles from './Session.module.css';

function History(props) {

    const [Sessions, setSessions] = useState([]);

    const [currentMedicalBookId, setCurrentMedicalBookId] = useState(-1);
    const [jwt, setJwt] = useState(props.jwt);
    const [visibleMedicalBook, setVisibleMedicalBook] = useState(false);

    useEffect(() => {

        async function DidMount() {

            const response = await HttpNoData('/api/reception/history', 'GET', jwt);

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

    const sessionsView = Sessions.length == 0 ? 'Тут пока пусто' : Sessions.map(sessia =>
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
                    <strong>Статус:</strong> {
                        sessia.status === 'Completed' ?
                        "Успешно завершен":
                        "Отменен"
                    }
                </div>

                    <div className={styles.patientSessia}>
                    <strong>Пациент:</strong> {sessia.fullName}
                </div>

                <div className={styles.commentSessia}>
                    <strong>Примечания:</strong>  {sessia.data}
                </div>
                <div className={styles.buttonsEvent}>
                    <button className={styles.buttonEvent} onClick={() => ShowMedicalBook(sessia.patientId)}>Мед. книжка</button>
                </div>
            </div>);

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
                        <MedicalBook jwt={jwt} isUserId={true} id={currentMedicalBookId} cancel={() => setCurrentMedicalBookId(-1)}/>
                    </div> : null
            }
        </div>
    </div>);
};

export default History;