import React from "react";
import doctorIcon from '../images/doctor.png';
import styles from '../Styles/Home.module.css';

function Home() {

    return (
        <div className={styles.psevdoBody}>
            <div className={styles.content}>
            <img width={300} height={300} src={doctorIcon} />
            <label className={styles.contentText}>Данный проект разработан с целью решить проблему, связанную с привязкой пациентов к другим медицинским учреждениям.</label>
            <button className={styles.buttonStart} onClick={() => {window.location.href+='login'}}>начать</button>
            </div>
        </div>
    );
}

export default Home;