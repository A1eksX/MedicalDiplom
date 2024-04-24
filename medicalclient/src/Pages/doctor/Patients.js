import { useEffect, useState } from "react";
import { HttpNoData } from "../../Core/Core";
import MedicalBook from "../../Module/MedicalBook";
import stylesSession from './Session.module.css';
import { Alert, Modal, Accordion } from 'react-bootstrap';

function Patients(props) {

    const [jwt, setJwt] = useState(props.jwt);
    const [data, setData] = useState();
    const [visibleMedicalBook, setVisibleMedicalBook] = useState(false);
    const [currentReceptions, setCurrentReceptions] = useState();
    const [currentMedicalBookId, setCurrentMedicalBookId] = useState(-1);

    useEffect(() => {

        async function DidMounth() {

            const response = await HttpNoData('/api/doctor/my/patient', 'GET', jwt);

            if (response.statusSuccessful)
                setData(response.data);
            else console.log(response.error);
        }

        DidMounth();
    }, []);

    const ShowReceptions = async (id) => {

        const response = await HttpNoData(`/api/patient/${id}/receptions`, 'GET', jwt);

        setCurrentReceptions(response.data);
    }

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

    return <div className={stylesSession.psevdoBody}>

        <div className={stylesSession.content}>
            <div className={stylesSession.contentSessians}>
                <div className={stylesSession.listSessians}>
                    {data && data.length > 0 ? data.map(o => <div key={o.id} className={stylesSession.sessia}>
                        <div className={stylesSession.patientSessia}>
                            <strong>Пациент:</strong> {o.fullName}
                        </div>
                        <div className={stylesSession.commentSessia}>
                            <strong>Кол-во:</strong>  {o.count}
                        </div>
                        <div className={stylesSession.buttonsEvent}>
                            <button className={stylesSession.buttonEvent} onClick={() => ShowMedicalBook(o.id)}>Мед. книжка</button>
                            <button className={stylesSession.buttonEvent} onClick={() => ShowReceptions(o.id)}>История посещений</button>
                        </div>
                    </div>) : <Alert key={'warning'} variant={"warning"}>
                        Пока записей нет.
                    </Alert>}
                </div>
            </div>
            {
                visibleMedicalBook ?
                    <div>
                        <MedicalBook jwt={jwt} isUserId={true} id={currentMedicalBookId} cancel={() => setCurrentMedicalBookId(-1)} />
                    </div> : null
            }
        </div>
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
    </div>;
}

export default Patients;