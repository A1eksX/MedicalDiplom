import React, { useState } from "react";
import { HttpData, HttpNoData } from "../Core/Core";
import styles from '../Styles/Login.module.css';

function Login(props) {

    const [numberDiplom, setNumberDiplom] = useState('');
    const [password, setPassword] = useState('');

    const ClickLogin = async () => {
        const responseJwt = await HttpData('/api/auth/login', 'POST', null, { diplomaNumber: numberDiplom, password: password });

        if (responseJwt.statusSuccessful) {

            const responseUser = await HttpNoData('/api/doctor/my', 'GET', responseJwt.data.accessToken);

            if (responseUser.statusSuccessful) {
                props.login(responseUser.data, responseJwt.data.accessToken);

                if (responseUser.data.specialization == 'Регистратор')
                    window.location.href = 'registratura';
                else
                    window.location.href = 'doctor';
            }
            else console.log(responseUser.error);
        }
        else console.log(responseJwt.error);
    }

    return (
        <div className={styles.rootBody}>
            <div className={styles.customBody}>
                <div className={styles.login_container}>
                    <h2>Вход</h2>
                    <div className={styles.login_form}>
                        <div className={styles.form_group}>
                            <label className={styles.titleUsername} >Номер диплома:</label>
                            <input className={styles.inputUsername} onChange={(e) => { setNumberDiplom(e.target.value); }} type="text" id="username" name="username" value={numberDiplom} required />
                        </div>
                        <div className={styles.form_group}>
                            <label className={styles.titlePassword} >Пароль:</label>
                            <input className={styles.inputPassword} onChange={(e) => { setPassword(e.target.value); }} type="password" id="password" name="password" value={password} required />
                        </div>
                        <div className={styles.form_group}>
                            <button className={styles.loginButton} onClick={() => { ClickLogin(); }}>Войти</button>
                        </div>
                    </div>
                </div>
                <a className={styles.linkCreate} href="/registration">подать заявку</a>
            </div>
        </div>);
}

export default Login;