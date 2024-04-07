import React, { useEffect, useState } from "react";
import { HttpData, HttpNoData } from '../Core/Core.js';
import styles from './MedicalBook.module.css';

function MedicalBook(props) {

    const [jwt, setJwt] = useState(props.jwt);
    const [medicalBookId, setMedicalBookId] = useState(props.id);
    const [isUserId, setUserId] = useState(props.isUserId);
    const [medicalBook, setMedicalBook] = useState();

    useEffect(() => {

        async function DidMount() {


            const response = await HttpNoData(isUserId ? ('/api/patient/' + medicalBookId + '/medicalbook') : ('/api/medicalbook/' + medicalBookId), 'GET', jwt);

            if (response.statusSuccessful)
                setMedicalBook(response.data);
            else console.log(response.error);
        }

        DidMount();
    }, []);

    async function Delete(id) {


        if (!window.confirm('Вы действительно хотите удалить?'))
            return;

        const response = await HttpNoData('/api/disease/' + id, 'DELETE', jwt);

        if (response.statusSuccessful) {
            const diseas = [...medicalBook.historyDisease];

            const filters = diseas.filter(o => o.id != id);

            const newMedicalBook = { ...medicalBook };

            newMedicalBook.historyDisease = filters;

            setMedicalBook(newMedicalBook);

        } else console.log(response.error);
    }

    async function Create() {

        const dateStart = window.prompt('Начало болезни', new Date().toJSON())
        const dateEnd = window.prompt('Конец болезни', new Date().toJSON())
        const data = window.prompt('Диагноз');
        const rezeptura = window.prompt('Рецептура');

        if (!window.confirm('Начало: ' + dateStart + "\nКонец: " + dateEnd + '\nДиагноз: ' + data + '\nРецептура: ' + rezeptura))
            return;

        let model = {
            "medicalBookId": medicalBook.id,
            "diagnosis": data,
            "treatmentStart": dateStart,
            "treatmentEnd": dateEnd,
            "rezeptura": rezeptura
        };

        const response = await HttpData('/api/disease', 'POST', jwt, model);

        if (response.statusSuccessful) {
            const diseas = [...medicalBook.historyDisease];

            const filters = [{
                id: response.data,
                ...model,
                procedures: [],
            },
            ...diseas];

            const newMedicalBook = { ...medicalBook };

            newMedicalBook.historyDisease = filters;

            setMedicalBook(newMedicalBook);
        } else console.log(response.error);
    }

    function Component(props) {

        return <div className={styles.component}>
            <label className={styles.title}>{props.title}</label>
            <div className={styles.value}>{props.value}</div>
        </div>;
    }

    const UpdateValueProcedure = async(id, count) => {

        const response = await HttpNoData('/api/procedure/'+id+'?count='+count, 'PATCH', jwt);

        return response.statusSuccessful;
    }

    const DeleteProcedure = async(diseaseId, id) => {

        const response = await HttpNoData('/api/procedure/' + id, 'DELETE', jwt);

        if(response.statusSuccessful){
            const diseas = [...medicalBook.historyDisease];

            const index = diseas.findIndex(o => o.id == diseaseId);
            const diseasOne = {...diseas[index]};

            const updateFilter = diseasOne.procedures.filter(o => o.id != id);

            diseasOne.procedures = [...updateFilter];

            diseas[index] = diseasOne;

            const newMedicalBook = { ...medicalBook };

            newMedicalBook.historyDisease = diseas;

            setMedicalBook(newMedicalBook);
        }else console.log(response.error);
    }

    const CreateProcedure = async(diseaseId) => {

        const drugs = window.prompt('Препараты')
        const name = window.prompt('Название')

        if (!window.confirm('Препараты: ' + drugs + "\nНазвание: " + name))
            return;

        let model = {
            drugs: drugs,
            name: name,
            disease_id: diseaseId,
            count: 0
        };

        const response = await HttpData('/api/procedure', 'POST', jwt, model);

        if(response.statusSuccessful)
        {
            const diseas = [...medicalBook.historyDisease];

            const index = diseas.findIndex(o => o.id == diseaseId);
            const diseasOne = {...diseas[index]};

            const updateFilter = [ {...model, id: response.data},...diseasOne.procedures];

            diseasOne.procedures = [...updateFilter];

            diseas[index] = diseasOne;

            const newMedicalBook = { ...medicalBook };

            newMedicalBook.historyDisease = diseas;

            setMedicalBook(newMedicalBook);
        } else console.log(response.error);
    }

    if (medicalBook == null)
        return <div>Загрузка</div>
    
    const StateProcedureActive = ({item, diseaseId}) => {

        const [blockItem, setBlockItem] = useState(item);

        const updateValue = () => {

            const newValue = window.prompt("Новое значение", item.count);

            const isValue = UpdateValueProcedure(item.id, newValue);

            if(isValue){
                let updateItem = { ...blockItem };
                updateItem.count = newValue;
                setBlockItem(updateItem);
            }
        }
        
        return <div className={styles.procedure}>
        <div>
            <strong>
                {"Название: "}
            </strong>
            {blockItem.name}
        </div>
        <div>
            <strong>
                {"Препараты: "}
            </strong>
            {blockItem.drugs}
        </div>
        <div>
            <strong>
                {"Кол-во: "}
            </strong>
            {blockItem.count}
        </div>
        <div className={styles.buttonsOnDiagnos}>
            <button className={styles.buttonOnDiagnos} onClick={updateValue}>Изменить</button>
            <button className={styles.buttonOnDiagnos} onClick={() => DeleteProcedure(diseaseId, blockItem.id)}>Удалить</button>
        </div>
    </div>;
    }

    const StateProcedure = ({ item }) => {

        const [isProcedure, setIsProcedure] = useState(false);

        return <div className={styles.diagnos}>
            <label className={styles.diagnosName}>{new Date(item.treatmentStart).toLocaleDateString()} - {new Date(item.treatmentEnd).toLocaleDateString()}</label>
            <label className={styles.diagnosName}><strong>Диагноз:</strong> {item.diagnosis}</label>
            <label className={styles.diagnosName}><strong>Рецептура:</strong> {item.rezeptura}</label>

            <div className={styles.buttonsOnDiagnos}>
                <button className={styles.buttonOnDiagnos} onClick={() => setIsProcedure(!isProcedure)}>{
                    isProcedure ? "Скрыть процедуры" : "Просмотреть процедуры"
                }</button>
                <button className={styles.buttonOnDiagnos} onClick={() => Delete(item.id)}>Удалить диагноз</button>
            </div>

            {
                isProcedure ?
                    <div className={styles.procedures}>
                        { item.procedures.map(o => <StateProcedureActive key={o.id} diseaseId={item.id} item={o}/>) }
                        <button className={styles.createDiagnos} onClick={() => CreateProcedure(item.id)}>Добавить</button>
                    </div>
                    : null}
        </div>
    };

    const disease = medicalBook.historyDisease.map(item => <StateProcedure key={item.id} item={item} />);

    return <div className={styles.psevdoBody}>
        <div className={styles.book}>
            <Component title='Прививочный сертификат' value={medicalBook.graftCertificate} />
            <Component title='Группа крови' value={medicalBook.groupBlood} />
            <Component title='Резус фактор' value={medicalBook.rhFactor ? 'Положительная' : 'Отрицательная'} />
            <Component title='Аллергия' value={medicalBook.allergy} />
            <Component title='Диагнозы' value={
                <div className={styles.diagnosis}>{disease}</div>}
            />
            <div className={styles.buttonsOnDiagnos}>
                <button className={styles.createDiagnos} onClick={Create}>Добавить диагноз</button>
                <button className={styles.cancel} onClick={() => props.cancel()}>Закрыть</button>
            </div>
        </div>
    </div>;
}

export default MedicalBook;