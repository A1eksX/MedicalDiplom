import 'bootstrap/dist/css/bootstrap.min.css';
import React from "react";
import { Container, Nav, Navbar } from 'react-bootstrap';
import { Route, Routes } from 'react-router-dom';
import Doctors from './Doctors';
import PatientSearch from './PatientSearch';
import Request from "./Request";

function RegistrationNavBar(props) {

    return (
        <div>
            <Navbar bg="#000" expand="lg">
                <Container>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav>
                            <Nav.Link href="/registratura/request">Создать заявку</Nav.Link>
                            <Nav.Link href="/registratura/doctors">Список докторов</Nav.Link>
                            <Nav.Link href="/registratura/patientsSearch">Поиск пациента</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
                <Routes>
                    <Route path='/*' element={<Request jwt={props.jwt}/>} />
                    <Route path='/request' element={<Request jwt={props.jwt}/>} />
                    <Route path='/doctors' element={<Doctors jwt={props.jwt}/>} />
                    <Route path='/patientsSearch' element={<PatientSearch jwt={props.jwt}/>} />
                </Routes>
        </div>
    );
}

export default RegistrationNavBar;