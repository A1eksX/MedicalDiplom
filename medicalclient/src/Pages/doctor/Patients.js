import { useEffect, useState } from "react";
import { HttpNoData } from "../../Core/Core";
import MedicalBook from "../../Module/MedicalBook";
import stylesSession from './Session.module.css';

function Patients(props){

    const [jwt, setJwt] = useState(props.jwt);
    const [data, setData] = useState([]);
    const [visibleMedicalBook, setVisibleMedicalBook] = useState(false);
    const [currentMedicalBookId, setCurrentMedicalBookId] = useState(-1);

    useEffect(() => {
        
        async function DidMounth(){

            const response = await HttpNoData('/api/doctor/my/patient', 'GET', jwt);

            if(response.statusSuccessful)
            {
                setData(response.data);
            }else console.log(response.error);
        }

        DidMounth();
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

    const views = data.map(o => {

        return <div key={o.id} className={stylesSession.sessia}>
        <div className={stylesSession.patientSessia}>
            <strong>Пациент:</strong> {o.fullName}
        </div>
        <div className={stylesSession.commentSessia}>
                    <strong>Кол-во:</strong>  {o.count}
                </div>
        <div className={stylesSession.buttonsEvent}>
            <button className={stylesSession.buttonEvent} onClick={() => ShowMedicalBook(o.id)}>Мед. книжка</button>
        </div>
    </div>
    });

    return <div className={stylesSession.psevdoBody}>

    <div className={stylesSession.content}>
        <div className={stylesSession.contentSessians}>
            <div className={stylesSession.listSessians}>
                {views}
            </div>
        </div>
        {
                visibleMedicalBook ?
                    <div>
                        <MedicalBook jwt={jwt} isUserId={true} id={currentMedicalBookId} cancel={() => setCurrentMedicalBookId(-1)}/>
                    </div> : null
            }
    </div>
</div>;
}

export default Patients;